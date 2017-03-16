package patmat

object test {
	//def times(chars: List[Char]): List[(Char, Int)] = chars.distinct.collect({case x => (x, chars.count(_ == x))})
	def times(chars: List[Char]): List[(Char, Int)] = chars.distinct.map(x => (x, chars.count(_ == x)))
                                                  //> times: (chars: List[Char])List[(Char, Int)]
	
	val s = "321987654321".toList             //> s  : List[Char] = List(3, 2, 1, 9, 8, 7, 6, 5, 4, 3, 2, 1)
	
	times(s)                                  //> res0: List[(Char, Int)] = List((3,2), (2,2), (1,2), (9,1), (8,1), (7,1), (6,
                                                  //| 1), (5,1), (4,1))
  s.sorted                                        //> res1: List[Char] = List(1, 1, 2, 2, 3, 3, 4, 5, 6, 7, 8, 9)
  0 :: s                                          //> res2: List[AnyVal] = List(0, 3, 2, 1, 9, 8, 7, 6, 5, 4, 3, 2, 1)
  List(0, 0, 0) ::: s                             //> res3: List[AnyVal] = List(0, 0, 0, 3, 2, 1, 9, 8, 7, 6, 5, 4, 3, 2, 1)
	
  val sample = 1 to 10                            //> sample  : scala.collection.immutable.Range.Inclusive = Range(1, 2, 3, 4, 5, 
                                                  //| 6, 7, 8, 9, 10)
  val isEven: PartialFunction[Int, String] = {
    case x if x % 2 == 0 => x + " is even"
  }                                               //> isEven  : PartialFunction[Int,String] = <function1>
  val evenNumbers = sample collect isEven         //> evenNumbers  : scala.collection.immutable.IndexedSeq[String] = Vector(2 is e
                                                  //| ven, 4 is even, 6 is even, 8 is even, 10 is even)
  println(evenNumbers(0))                         //> 2 is even

	val x = (1,2,3)                           //> x  : (Int, Int, Int) = (1,2,3)
	x._3                                      //> res4: Int = 3
	
	val y = "123456789"                       //> y  : String = 123456789
	y.contains("123")                         //> res5: Boolean = true
	s(s.length-1)                             //> res6: Char = 1
	
	val z = List(9,8,7,6,5,4,3,2,1)           //> z  : List[Int] = List(9, 8, 7, 6, 5, 4, 3, 2, 1)
	z.drop(2)                                 //> res7: List[Int] = List(7, 6, 5, 4, 3, 2, 1)
	"1"+"2"                                   //> res8: String("12") = 12
	
	var test = Huffman.decodedSecret          //> test  : List[Char] = List(h, u, f, f, m, a, n, e, s, t, c, o, o, l)
	Huffman.encode(Huffman.frenchCode)(test) == Huffman.secret
                                                  //> res9: Boolean = true
	Huffman.quickEncode(Huffman.frenchCode)(test) == Huffman.secret
                                                  //> res10: Boolean = true
	
	val leaflist = List(Huffman.Leaf('e', 1), Huffman.Leaf('t', 2), Huffman.Leaf('x', 4))
                                                  //> leaflist  : List[patmat.Huffman.Leaf] = List(Leaf(e,1), Leaf(t,2), Leaf(x,4)
                                                  //| )
	Huffman.combine(leaflist)                 //> res11: List[patmat.Huffman.CodeTree] = List(Fork(Leaf(e,1),Leaf(t,2),List(e,
                                                  //|  t),3), Leaf(x,4))
  
  val t1 = Huffman.Fork(Huffman.Leaf('a',2), Huffman.Leaf('b',3), List('a','b'), 5)
                                                  //> t1  : patmat.Huffman.Fork = Fork(Leaf(a,2),Leaf(b,3),List(a, b),5)
  Huffman.encode(t1)("abb".toList)                //> res12: List[patmat.Huffman.Bit] = List(0, 1, 1)
  Huffman.quickEncode(t1)("abb".toList)           //> res13: List[patmat.Huffman.Bit] = List(0, 1, 1)
  Huffman.decode(t1, List(1, 1, 0))               //> res14: List[Char] = List(b, b, a)

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
                                                  //| res15: List[Any] = List(1, 1, 2, 3, 5, 8)

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
	msort(nums)                               //> res16: List[Int] = List(-4, 1, 2, 5, 7)
	
	(nums foldLeft 0) (_ + _)                 //> res17: Int = 11
	Nil :: Nil                                //> res18: List[scala.collection.immutable.Nil.type] = List(List())
	def reverse[T](xs: List[T]): List[T] = (xs foldLeft List[T]())((ys, y) => y :: ys)
                                                  //> reverse: [T](xs: List[T])List[T]
	reverse(Nil)                              //> res19: List[Nothing] = List()
	reverse(List(1))                          //> res20: List[Int] = List(1)
	reverse(List(1, 2))                       //> res21: List[Int] = List(2, 1)
	
	def mapFun[T, U](xs: List[T], f: T => U): List[U] =
  	(xs foldRight List[U]())(f(_) :: _)       //> mapFun: [T, U](xs: List[T], f: T => U)List[U]

	def lengthFun[T](xs: List[T]): Int =
	  (xs foldRight 0)((ys, y) => 1 + y)      //> lengthFun: [T](xs: List[T])Int
	
	mapFun[Int, Int](nums, x => x*2)          //> res22: List[Int] = List(4, -8, 10, 14, 2)
	lengthFun(nums)                           //> res23: Int = 5
}