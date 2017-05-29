package quickcheck

import common._

import org.scalacheck._
import Arbitrary._
import Gen._
import Prop._

abstract class QuickCheckHeap extends Properties("Heap") with IntHeap {

  lazy val genHeap: Gen[H] = oneOf(
     const(empty),
      for {
        x <- arbitrary[Int]
        h <- oneOf(const(empty), genHeap)
      } yield insert(x, h)
  )
  implicit lazy val arbHeap: Arbitrary[H] = Arbitrary(genHeap)
  
  property("gen1") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(insert(m, h)) == m
  }
  
  property("empty1") = forAll { (a: Int) =>
    isEmpty(insert(a, empty)) != true
  }

  property("meld1") = forAll { (h: H) =>
    meld(h, empty) == h && meld(empty, h) == h
  }
  
  property("meld2") = forAll { (h: H) =>
    val m = if (isEmpty(h)) 0 else findMin(h)
    findMin(meld(h, insert(m, empty))) == m
  }
  
  property("findMin1") = forAll { (a: Int) =>
    val h = insert(a, empty)
    findMin(h) == a
  }
  
  property("findMin2") = forAll { (h1: H, h2: H) =>
    if (isEmpty(h1) && isEmpty(h2)) 
      true 
    else {
      val m = if (isEmpty(h1)) 
        findMin(h2) 
      else {
        val m1 = findMin(h1)
        if (isEmpty(h2)) 
          m1 
        else {
          val m2 = findMin(h2)
          if (m1 < m2) m1 else m2
        }
      }
      findMin(meld(h1, h2)) == m
    }
  }
  
  property("delMin1") = forAll { (a: Int) =>
    deleteMin(insert(a, empty)) == empty
  }
  
  property("order1") = forAll { (h: H) =>
    def toList(h1: H): List[Int] = 
      if (isEmpty(h1))
        Nil
      else
        findMin(h1) :: toList(deleteMin(h1))
        
    toList(h) == toList(h).sorted
  } 
  
  property("order2") = forAll { (h1: H, h2: H)=>
    def toList(h: H): List[Int] = 
      if (isEmpty(h))
        Nil
      else
        findMin(h) :: toList(deleteMin(h))
        
    toList(meld(h1, h2)) == (toList(h1) ++ toList(h2)).sorted
  } 
}
