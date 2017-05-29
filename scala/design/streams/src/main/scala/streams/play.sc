package streams

object play {
  val v = Vector(Vector('a', 'b', 'c'), Vector('d', 'a', 'b'), Vector('x', 'y', 'z'))
                                                  //> v  : scala.collection.immutable.Vector[scala.collection.immutable.Vector[Cha
                                                  //| r]] = Vector(Vector(a, b, c), Vector(d, a, b), Vector(x, y, z))
  val num = 'd'                                   //> num  : Char = d

  v.map(_ indexOf num).zipWithIndex.dropWhile(_._1 == -1).head.swap
                                                  //> res0: (Int, Int) = (1,0)
  
//  Bloxorz.InfiniteLevel.solution
  
  Bloxorz.Level1.solution                         //> List((Block(Pos(4,7),Pos(4,7)),List(Down, Right, Right, Right, Down, Right, 
                                                  //| Right)), (Block(Pos(4,7),Pos(4,7)),List(Right, Down, Down, Right, Right, Dow
                                                  //| n, Right)), (Block(Pos(4,7),Pos(4,7)),List(Right, Down, Right, Right, Down, 
                                                  //| Down, Right)))
                                                  //| 0
                                                  //| res1: List[streams.Bloxorz.Level1.Move] = List(Right, Right, Down, Right, Ri
                                                  //| ght, Right, Down)
}