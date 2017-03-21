package forcomp
import Anagrams._

object week6 {
  class Poly(terms0: Map[Int, Double]) {
    def this(bindings: (Int, Double)*) = this(bindings.toMap)
    val terms = terms0 withDefaultValue 0.0
    
    def +(other: Poly) = new Poly(terms ++ (other.terms map adjust))
    def adjust(term: (Int, Double)): (Int, Double) = {
      val (exp, coeff) = term
      exp -> (coeff + terms(exp))
      /* terms get exp match {
      	case Some(coeff1) => exp -> (coeff + coeff1)
      	case None => exp -> coeff
      } */
    }

    override def toString =
      (for ((exp, coeff) <- terms.toList.sorted.reverse)
        yield coeff + "x^" + exp) mkString " + "
  }
  val p1 = new Poly(1 -> 2.0, 3 -> 4.0, 5 -> 6.2) //> p1  : forcomp.week6.Poly = 6.2x^5 + 4.0x^3 + 2.0x^1
  val p2 = new Poly(Map(0 -> 3.0, 3 -> 7.0))      //> p2  : forcomp.week6.Poly = 7.0x^3 + 3.0x^0
  
  p1 + p2                                         //> res0: forcomp.week6.Poly = 6.2x^5 + 11.0x^3 + 2.0x^1 + 3.0x^0
  p1.terms ++ p2.terms                            //> res1: scala.collection.immutable.Map[Int,Double] = Map(1 -> 2.0, 3 -> 7.0, 5
                                                  //|  -> 6.2, 0 -> 3.0)
  val x = p2.terms map p1.adjust                  //> x  : scala.collection.immutable.Map[Int,Double] = Map(0 -> 3.0, 3 -> 11.0)
  p1.terms ++ x                                   //> res2: scala.collection.immutable.Map[Int,Double] = Map(1 -> 2.0, 3 -> 11.0, 
                                                  //| 5 -> 6.2, 0 -> 3.0)
  
  /* ---------- */

 	val s: Word = "ABCABCD"                   //> s  : forcomp.Anagrams.Word = ABCABCD
 	//s.toLowerCase.toList.groupBy(x => x).toList.map(x => (x._1, x._2.length)).sorted
	wordOccurrences(s)                        //> ../forcomp/linuxwords.txt
                                                  //| res3: forcomp.Anagrams.Occurrences = List((a,2), (b,2), (c,2), (d,1))
	
	val p: Sentence = List(s, "XYXXYZ")       //> p  : forcomp.Anagrams.Sentence = List(ABCABCD, XYXXYZ)
	p.map(wordOccurrences)                    //> res4: List[forcomp.Anagrams.Occurrences] = List(List((a,2), (b,2), (c,2), (
                                                  //| d,1)), List((x,3), (y,2), (z,1)))
	sentenceOccurrences(p)                    //> res5: forcomp.Anagrams.Occurrences = List((a,2), (b,2), (c,2), (d,1), (x,3)
                                                  //| , (y,2), (z,1))
                                                  
  val dictionary: List[Word] = loadDictionary     //> ../forcomp/linuxwords.txt
                                                  //| dictionary  : List[forcomp.Anagrams.Word] = List(Aarhus, Aaron, Ababa, abac
                                                  //| k, abaft, abandon, abandoned, abandoning, abandonment, abandons, abase, aba
                                                  //| sed, abasement, abasements, abases, abash, abashed, abashes, abashing, abas
                                                  //| ing, abate, abated, abatement, abatements, abater, abates, abating, Abba, a
                                                  //| bbe, abbey, abbeys, abbot, abbots, Abbott, abbreviate, abbreviated, abbrevi
                                                  //| ates, abbreviating, abbreviation, abbreviations, Abby, abdomen, abdomens, a
                                                  //| bdominal, abduct, abducted, abduction, abductions, abductor, abductors, abd
                                                  //| ucts, Abe, abed, Abel, Abelian, Abelson, Aberdeen, Abernathy, aberrant, abe
                                                  //| rration, aberrations, abet, abets, abetted, abetter, abetting, abeyance, ab
                                                  //| hor, abhorred, abhorrent, abhorrer, abhorring, abhors, abide, abided, abide
                                                  //| s, abiding, Abidjan, Abigail, Abilene, abilities, ability, abject, abjectio
                                                  //| n, abjections, abjectly, abjectness, abjure, abjured, abjures, abjuring,
                                                  //| Output exceeds cutoff limit.
  dictionary.length                               //> res6: Int = 45374
  dictionary.groupBy(x => wordOccurrences(x))     //> res7: scala.collection.immutable.Map[forcomp.Anagrams.Occurrences,List[forc
                                                  //| omp.Anagrams.Word]] = Map(List((e,1), (i,1), (l,1), (r,1), (t,2)) -> List(l
                                                  //| itter), List((a,1), (d,1), (e,1), (g,2), (l,1), (r,1)) -> List(gargled), Li
                                                  //| st((a,1), (e,1), (h,1), (i,1), (k,1), (n,1), (s,3)) -> List(shakiness), Lis
                                                  //| t((e,2), (g,1), (n,1)) -> List(gene), List((a,2), (n,1), (t,1), (y,1)) -> L
                                                  //| ist(Tanya), List((a,1), (d,1), (e,2), (h,1), (m,1), (n,2), (o,1), (s,3)) ->
                                                  //|  List(handsomeness), List((a,2), (c,1), (e,2), (k,1), (l,1), (m,1), (p,1), 
                                                  //| (r,1), (t,1)) -> List(marketplace), List((a,1), (i,1), (l,2), (s,1), (v,1))
                                                  //|  -> List(villas), List((d,2), (e,1), (h,2), (n,1), (r,1), (t,1), (u,1)) -> 
                                                  //| List(hundredth), List((a,3), (b,1), (c,1), (h,1), (i,2), (l,1), (o,1), (p,2
                                                  //| ), (r,1), (t,1), (y,1)) -> List(approachability), List((d,1), (e,2), (l,1),
                                                  //|  (s,1), (t,2)) -> List(settled), List((a,1), (g,1), (i,3), (l,1), (n,2), (t
                                                  //| ,1), (z,1)) -> List(Lat
                                                  //| Output exceeds cutoff limit.
  
	val t = List(('a', 2), ('b', 2))          //> t  : List[(Char, Int)] = List((a,2), (b,2))
	val y = combinations(t)                   //> y  : List[forcomp.Anagrams.Occurrences] = List(List(), List((b,1)), List((b
                                                  //| ,2)), List((a,1)), List((a,1), (b,1)), List((a,1), (b,2)), List((a,2)), Lis
                                                  //| t((a,2), (b,1)), List((a,2), (b,2)))
	y.length                                  //> res8: Int = 9

  Map('1' -> 1, '2' -> 2).updated('1', 3)         //> res9: scala.collection.immutable.Map[Char,Int] = Map(1 -> 3, 2 -> 2)
	
	val r = List(('a', 1), ('d', 1), ('l', 1), ('r', 1))
                                                  //> r  : List[(Char, Int)] = List((a,1), (d,1), (l,1), (r,1))
	val q = List(('r', 1))                    //> q  : List[(Char, Int)] = List((r,1))
	q.foldLeft(r)((ps, p) => ps.map(z => if (z._1 == p._1) (p._1, z._2 - p._2) else z)).filter(z => z._2 != 0)
                                                  //> res10: List[(Char, Int)] = List((a,1), (d,1), (l,1))
	r.toMap.apply('a')                        //> res11: Int = 1
	
	q.foldLeft(r.toMap)((ps, p) => ps.updated(p._1, ps.apply(p._1) - p._2)).toList
                                                  //> res12: List[(Char, Int)] = List((a,1), (d,1), (l,1), (r,0))
	subtract(r, q)                            //> res13: forcomp.Anagrams.Occurrences = List((a,1), (d,1), (l,1))
	
	wordAnagrams("AS")                        //> res14: List[forcomp.Anagrams.Word] = List(as)
	List(List()).isEmpty                      //> res15: Boolean = false
	
	dictionaryByOccurrences(List(('a', 1), ('e', 1), ('t', 1)))
                                                  //> res16: List[forcomp.Anagrams.Word] = List(ate, eat, tea)
	dictionaryByOccurrences(List(('e', 1), ('n', 1)))
                                                  //> res17: List[forcomp.Anagrams.Word] = List(en)
	
	
	List("Yes", "man").mkString("")           //> res18: String = Yesman
	
	var w = "123"                             //> w  : String = 123
	for {
		x <- 0 to w.length
		//y <- w.take(x)
	} yield {println(w.take(x));w.take(x)}    //> 
                                                  //| 1
                                                  //| 12
                                                  //| 123
                                                  //| res19: scala.collection.immutable.IndexedSeq[String] = Vector("", 1, 12, 12
                                                  //| 3)
	
	var occu0 = sentenceOccurrences(List("Linux", "rulez"))
                                                  //> occu0  : forcomp.Anagrams.Occurrences = List((e,1), (i,1), (l,2), (n,1), (r
                                                  //| ,1), (u,2), (x,1), (z,1))
	
	//(for {
	//	comb <- combinations(occu0) if !(dictionaryByOccurrences(comb).isEmpty)
	//}yield comb).mkString("\n")
	
	//(for {
	//	comb <- combinations(occu0)
	//	word <- dictionaryByOccurrences(comb)
	//}yield word).mkString("\n")

	def test(occu: Occurrences): List[Sentence] =
	{
		//println("1")
		//println(occu)
		if (occu.isEmpty) List(List())
		else for {
			occu_a <- combinations(occu) if !(dictionaryByOccurrences(occu_a).isEmpty)
			word_a <- dictionaryByOccurrences(occu_a)
			occu_b <- test(subtract(occu, occu_a))
		} yield {word_a :: occu_b}
  }                                               //> test: (occu: forcomp.Anagrams.Occurrences)List[forcomp.Anagrams.Sentence]
	//test(occu0).mkString("\n")
	
	sentenceAnagrams(List("Linux", "rulez")).mkString("\n")
                                                  //> res20: String = List(Zulu, Lin, Rex)
                                                  //| List(Zulu, nil, Rex)
                                                  //| List(Zulu, Rex, Lin)
                                                  //| List(Zulu, Rex, nil)
                                                  //| List(null, Uzi, Rex)
                                                  //| List(null, Rex, Uzi)
                                                  //| List(Uzi, null, Rex)
                                                  //| List(Uzi, Rex, null)
                                                  //| List(Lin, Zulu, Rex)
                                                  //| List(Lin, Rex, Zulu)
                                                  //| List(nil, Zulu, Rex)
                                                  //| List(nil, Rex, Zulu)
                                                  //| List(Linux, rulez)
                                                  //| List(Rex, Zulu, Lin)
                                                  //| List(Rex, Zulu, nil)
                                                  //| List(Rex, null, Uzi)
                                                  //| List(Rex, Uzi, null)
                                                  //| List(Rex, Lin, Zulu)
                                                  //| List(Rex, nil, Zulu)
                                                  //| List(rulez, Linux)
}