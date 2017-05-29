package barneshut

object test {
  Seq(1,2,3,4) :+ 5                               //> res0: Seq[Int] = List(1, 2, 3, 4, 5)
  2.5f.toInt                                      //> res1: Int = 2
  math.min(2,3)                                   //> res2: Int = 2
  Seq() :+ 1                                      //> res3: Seq[Int] = List(1)
}