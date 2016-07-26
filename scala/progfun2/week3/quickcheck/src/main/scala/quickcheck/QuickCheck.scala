package quickcheck

import org.scalacheck.Arbitrary._
import org.scalacheck.Gen._
import org.scalacheck.Prop._
import org.scalacheck._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = for {
    n <- arbitrary[Int]
    h <- oneOf(const(empty), genHeap)
  } yield insert(n, h)
  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)

  def isSorted(h: H): Boolean = {
    def loop(h: H, prev: Int) = {
      if (isEmpty(h)) true
      else {
        val min = findMin(h)
        if (min < prev) false

        else isSorted(deleteMin(h))
      }
    }

    loop(h, Int.MinValue)
  }

  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }

  property("find smallest from two elements") = forAll { (a: Int, b: Int) =>
    val h = insert(a, empty)
    if (a < b) {
      findMin(insert(b, h)) == a
    } else {
      findMin(insert(b, h)) == b
    }
  }

  property("insert one and delete, then result is empty") = forAll { (a: Int) =>
    val h = insert(a, empty)
    isEmpty(deleteMin(h))
  }

  property("sorted") = forAll { (h: H) => isSorted(h) }

  property("after melding and findMin") = forAll { (h1: H, h2: H) =>
    val m1 = findMin(h1)
    val m2 = findMin(h2)
    val min = findMin(meld(h1, h2))
    if (m1 < m2) min == m1
    else min == m2
  }

  property("after delete min isSorted") = forAll { (h: H) => isSorted(deleteMin(h)) }

  property("after melding isSorted") = forAll { (h1: H, h2: H) => isSorted(meld(h1, h2)) }

  property("mixed operation") = forAll { (h1: H, h2: H, a: Int) =>
    val newH1 = deleteMin(h1)
    val newH2 = insert(a, h2)
    isSorted(meld(newH1, newH2))
  }

  property("find Minimum add +1 and findMinimum") = forAll { (h: H) =>
    val m = findMin(h)
    deleteMin(h)
    findMin(insert(m+1, h)) == (m+1)
  }
}
