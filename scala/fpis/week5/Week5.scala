object Week5 {

	def init[T](xs: List[T]): List[T] = xs match {
		case List() => throw new Error("init of empty list")
		case List(x) => List()
		case y :: ys => y :: init(ys)
	}

	// 내가 푼 것
	def removeAt[T](n: Int, xs: List[T]): List[T] = xs match {
		case List() => xs
		case x :: xs if n == 0 => xs
		case x :: xs => List(x) ++ removeAt(n-1, xs)
	}

	// 정답
	def removeAt2[T](n: Int, xs: List[T]) = (xs take n) ::: (xs drop n + 1)

	def flatten(xs: List[Any]): List[Any] = xs match {
		case List() => List()
		case x :: xs => {
			x match {
				case y :: ys => y :: flatten(ys) ++ flatten(xs)
				case _ => x :: flatten(xs)
			}
		}
	}
}
