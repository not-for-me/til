package funsets

object Main extends App {
  import FunSets._
//  println(contains(singletonSet(1), 1))

  val evenSet = union(union(singletonSet(2), singletonSet(4)), singletonSet(6))
  printSet(evenSet)
  printSet(map(evenSet, x => x/2))

//  val newSet: Set = x => 2 <= x && x <= 4
//  printSet(newSet)
//
//  printSet(map(newSet, x => x-1))
}
