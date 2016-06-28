object Week6 {

  def combinations(occurrences: List[(Char, Int)]): List[List[(Char, Int)]] = {
    if (occurrences.isEmpty) List(Nil)
   else {
     for {
       xs <- combinations(occurrences.tail)
       i <- 0 to occurrences.head._2
     } yield if (i == 0) Nil else (occurrences.head._1, i) :: xs
   }
   }
}
