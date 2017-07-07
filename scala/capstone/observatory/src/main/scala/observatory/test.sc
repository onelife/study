package observatory

import org.apache.log4j.{Level, Logger}
import observatory.Extraction._
import observatory.Visualization._
import observatory.Interaction._
import observatory.Manipulation._
import observatory.Visualization2._
import observatory.Interaction2._

object test {
  math.floor(-1.9)                          //> res0: Double = -2.0
  val loc = for {
		y <- 0 until 4
		x <- 0 until 4
	} yield tileLocation(2, x, y)             //> loc  : scala.collection.immutable.IndexedSeq[observatory.Location] = Vector(
                                                  //| Location(85.0511,-180.0), Location(85.0511,-90.0), Location(85.0511,0.0), Lo
                                                  //| cation(85.0511,90.0), Location(42.52555,-180.0), Location(42.52555,-90.0), L
                                                  //| ocation(42.52555,0.0), Location(42.52555,90.0), Location(0.0,-180.0), Locati
                                                  //| on(0.0,-90.0), Location(0.0,0.0), Location(0.0,90.0), Location(-42.525549999
                                                  //| 999996,-180.0), Location(-42.525549999999996,-90.0), Location(-42.5255499999
                                                  //| 99996,0.0), Location(-42.525549999999996,90.0))
	
	for (i <- loc) println(i)                 //> Location(85.0511,-180.0)
                                                  //| Location(85.0511,-90.0)
                                                  //| Location(85.0511,0.0)
                                                  //| Location(85.0511,90.0)
                                                  //| Location(42.52555,-180.0)
                                                  //| Location(42.52555,-90.0)
                                                  //| Location(42.52555,0.0)
                                                  //| Location(42.52555,90.0)
                                                  //| Location(0.0,-180.0)
                                                  //| Location(0.0,-90.0)
                                                  //| Location(0.0,0.0)
                                                  //| Location(0.0,90.0)
                                                  //| Location(-42.525549999999996,-180.0)
                                                  //| Location(-42.525549999999996,-90.0)
                                                  //| Location(-42.525549999999996,0.0)
                                                  //| Location(-42.525549999999996,90.0)
}