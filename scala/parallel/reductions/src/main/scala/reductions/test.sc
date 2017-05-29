package reductions

object test {
	ParallelCountChange.parCountChange(300, List(5,10,20,50,100,200,500),
	ParallelCountChange.combinedThreshold(300, List(5,10,20,50,100,200,500)))
                                                  //> res0: Int = 1022
                                                  
  ParallelParenthesesBalancing.balance("(o_()".toArray)
                                                  //> res1: Boolean = false
	"123456".toArray.indexOf('3', 1)          //> res2: Int = 2
  ParallelParenthesesBalancing.parBalance("(())))(((())".toArray, 6)
                                                  //> res3: Boolean = false
  ParallelParenthesesBalancing.parBalance("((())((())))".toArray, 6)
                                                  //> res4: Boolean = true
  
  val output = new Array[Float](4)                //> output  : Array[Float] = Array(0.0, 0.0, 0.0, 0.0)
  LineOfSight.lineOfSight(Array[Float](0f, 1f, 8f, 15f), output)
  output                                          //> res5: Array[Float] = Array(0.0, 1.0, 4.0, 5.0)
  
  LineOfSight.upsweepSequential(Array[Float](0f, 1f, 8f, 15f), 1, 4)
                                                  //> res6: Float = 5.0
  LineOfSight.downsweepSequential(Array[Float](0f, 1f, 8f, 15f), output, 0f, 1, 4)
  output                                          //> res7: Array[Float] = Array(0.0, 1.0, 4.0, 5.0)
  
  LineOfSight.upsweep(Array[Float](0f, 1f, 8f, 15f), 1, 4, 2)
                                                  //> res8: reductions.LineOfSight.Tree = Node(Leaf(1,2,1.0),Leaf(2,4,5.0))
  LineOfSight.downsweep(Array[Float](0f, 1f, 8f, 15f), output, 0, LineOfSight.upsweep(Array[Float](0f, 1f, 8f, 9f), 1, 4, 2))
                                                  //> parallel
  output                                          //> res9: Array[Float] = Array(0.0, 1.0, 4.0, 5.0)
  
  
  val output2 = new Array[Float](12)              //> output2  : Array[Float] = Array(0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0, 0.0,
                                                  //|  0.0, 0.0, 0.0)
  LineOfSight.lineOfSight(Array[Float](0f, 1f, 8f, 9f, 9f, 9f, 9f, 9f, 9f, 9f, 9f, 999f), output2)
  output2                                         //> res10: Array[Float] = Array(0.0, 1.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.
                                                  //| 0, 4.0, 90.818184)
  LineOfSight.parLineOfSight(Array[Float](0f, 1f, 8f, 9f, 9f, 9f, 9f, 9f, 9f, 9f, 9f, 999f), output2, 6)
                                                  //> parallel
  output2                                         //> res11: Array[Float] = Array(0.0, 1.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.0, 4.
                                                  //| 0, 4.0, 90.818184)
}