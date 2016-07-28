package calculator

sealed abstract class Expr

final case class Literal(v: Double) extends Expr

final case class Ref(name: String) extends Expr

final case class Plus(a: Expr, b: Expr) extends Expr

final case class Minus(a: Expr, b: Expr) extends Expr

final case class Times(a: Expr, b: Expr) extends Expr

final case class Divide(a: Expr, b: Expr) extends Expr

object Calculator {
  val CYCLE_REF_CACHE: Set[String] = Set.empty

  def computeValues(namedExpressions: Map[String, Signal[Expr]]): Map[String, Signal[Double]] = {
    namedExpressions.mapValues(expr => Signal {
      eval(expr(), namedExpressions)
    })
  }

  def eval(expr: Expr, references: Map[String, Signal[Expr]]): Double = {
    expr match {
      case Literal(x) => x
      case Ref(x) =>
        if (isCyclicRef(expr, references)) Double.NaN
        else eval(references.get(x).get(), references)
      case Plus(a, b) =>
        if (isCyclicRef(a, references)) Double.NaN
        else if (isCyclicRef(b, references)) Double.NaN
        else eval(a, references) + eval(b, references)
      case Minus(a, b) =>
        if (isCyclicRef(a, references)) Double.NaN
        else if (isCyclicRef(b, references)) Double.NaN
        else eval(a, references) - eval(b, references)
      case Times(a, b) =>
        if (isCyclicRef(a, references)) Double.NaN
        else if (isCyclicRef(b, references)) Double.NaN
        else eval(a, references) * eval(b, references)
      case Divide(a, b) =>
        if (isCyclicRef(a, references)) Double.NaN
        else if (isCyclicRef(b, references)) Double.NaN
        else eval(a, references) / eval(b, references)
    }
  }

  /** Get the Expr for a referenced variables.
    * If the variable is not known, returns a literal NaN.
    */
  private def getReferenceExpr(name: String, references: Map[String, Signal[Expr]]) = {
    references.get(name).fold[Expr] {
      Literal(Double.NaN)
    } {
      exprSignal =>
        exprSignal()
    }
  }

  private def isCyclicRef(expr: Expr, references: Map[String, Signal[Expr]]): Boolean = {

    def loop(expr: Expr, references: Map[String, Signal[Expr]], candidates: Set[String]): Boolean = expr match {
      case Literal(x) => false
      case Ref(x) =>
        if (candidates.exists(candidate => candidate eq x)) true
        else loop(getReferenceExpr(x, references), references, candidates + x)
      case Plus(a, b) => loop(a, references, candidates) || loop(b, references, candidates)
      case Minus(a, b) => loop(a, references, candidates) || loop(b, references, candidates)
      case Times(a, b) => loop(a, references, candidates) || loop(b, references, candidates)
      case Divide(a, b) => loop(a, references, candidates) || loop(b, references, candidates)
    }

    loop(expr, references, Set.empty)
  }
}
