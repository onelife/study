package scalashop

object test {
    val w = 3                                     //> w  : Int = 3
    val h = 3                                     //> h  : Int = 3
    val src = new Img(w, h)                       //> src  : scalashop.Img = scalashop.package$Img@96532d6
    val dst = new Img(w, h)                       //> dst  : scalashop.Img = scalashop.package$Img@3796751b
    src(0, 0) = 0; src(1, 0) = 1; src(2, 0) = 2; //src(3, 0) = 9
    src(0, 1) = 3; src(1, 1) = 4; src(2, 1) = 5; //src(3, 1) = 10
    src(0, 2) = 6; src(1, 2) = 7; src(2, 2) = 8; //src(3, 2) = 11

    VerticalBoxBlur.blur(src, dst, 0, 3, 2)
    VerticalBoxBlur.parBlur(src, dst, 4, 1)       //> List((0,1), (1,2), (2,3))
    
    
    val ps0 = (0 until 10+1 by 2)                 //> ps0  : scala.collection.immutable.Range = Range(0, 2, 4, 6, 8, 10)
    val ps = ps0 :+ 11                            //> ps  : scala.collection.immutable.IndexedSeq[Int] = Vector(0, 2, 4, 6, 8, 10,
                                                  //|  11)
    ps.contains(11)                               //> res0: Boolean = true
    val rs = ps.zip(ps.tail)                      //> rs  : scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((0,2), (2,4
                                                  //| ), (4,6), (6,8), (8,10), (10,11))
    val m: Int = 10                               //> m  : Int = 10
    m /100                                        //> res1: Int = 0
}