package patmat

object week5 {
	def flatten(xs: List[Any]): List[Any] = xs match {
		case List() => List()
		case y :: ys => println(y); y match {
			case List() => flatten(ys)
			case z :: zs => List(z) ::: flatten(zs) ::: flatten(ys)
			case default => y :: flatten(ys)
		}
	}                                         //> flatten: (xs: List[Any])List[Any]
	
	flatten(List(List(1, 1), 2, List(3, List(5, 8))))
                                                  //> List(1, 1)
                                                  //| 1
                                                  //| 2
                                                  //| List(3, List(5, 8))
                                                  //| List(5, 8)
                                                  //| 8
                                                  //| res0: List[Any] = List(1, 1, 2, 3, 5, 8)

	def msort(xs: List[Int]): List[Int] = {
		val n = xs.length / 2
		if (n == 0) xs
		else {
			def merge(xs: List[Int], ys: List[Int]): List[Int] = (xs, ys) match {
				case (Nil, ys) => ys
				case (xs, Nil) => xs
				case (x :: xs1, y :: ys1) =>
					if (x < y) x :: merge(xs1, ys)
					else y :: merge(xs, ys1)
			}
			
			val (fst, snd) = xs splitAt n
			merge(msort(fst), msort(snd))
		}
	}                                         //> msort: (xs: List[Int])List[Int]
	
	val nums = List(2, -4, 5, 7, 1)           //> nums  : List[Int] = List(2, -4, 5, 7, 1)
	msort(nums)                               //> res1: List[Int] = List(-4, 1, 2, 5, 7)
	
	(nums foldLeft 0) (_ + _)                 //> res2: Int = 11
	Nil :: Nil                                //> res3: List[scala.collection.immutable.Nil.type] = List(List())
	def reverse[T](xs: List[T]): List[T] = (xs foldLeft List[T]())((ys, y) => y :: ys)
                                                  //> reverse: [T](xs: List[T])List[T]
	reverse(Nil)                              //> res4: List[Nothing] = List()
	reverse(List(1))                          //> res5: List[Int] = List(1)
	reverse(List(1, 2))                       //> res6: List[Int] = List(2, 1)
	
	def mapFun[T, U](xs: List[T], f: T => U): List[U] =
  	(xs foldRight List[U]())(f(_) :: _)       //> mapFun: [T, U](xs: List[T], f: T => U)List[U]

	def lengthFun[T](xs: List[T]): Int =
	  (xs foldRight 0)((ys, y) => 1 + y)      //> lengthFun: [T](xs: List[T])Int
	
	mapFun[Int, Int](nums, x => x*2)          //> res7: List[Int] = List(4, -8, 10, 14, 2)
	lengthFun(nums)                           //> res8: Int = 5
	
	def isPrime(n: Int) = (2 until n) forall (n % _ !=0)
                                                  //> isPrime: (n: Int)Boolean
	
	var w = 5                                 //> w  : Int = 5
	(1 until w) flatMap (i => (1 until i) map (j => (i, j))) filter (p => isPrime(p._1 + p._2))
                                                  //> res9: scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((2,1), (3,
                                                  //| 2), (4,1), (4,3))
	((1 until w) map (i => (1 until i) map (j => (i, j))) flatten) filter (p => isPrime(p._1 + p._2))
                                                  //> res10: scala.collection.immutable.IndexedSeq[(Int, Int)] = Vector((2,1), (3
                                                  //| ,2), (4,1), (4,3))
	Map("1"->1, "2"->2).toList                //> res11: List[(String, Int)] = List((1,1), (2,2))
}