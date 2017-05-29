package kmeans.fun

import scala.annotation.tailrec
import scala.collection._
import scala.util.Random
import org.scalameter._
import common._

object test {
  val points: GenSeq[kmeans.Point] = IndexedSeq() //> points  : scala.collection.GenSeq[kmeans.Point] = Vector()
	val means: GenSeq[kmeans.Point] = IndexedSeq()
                                                  //> means  : scala.collection.GenSeq[kmeans.Point] = Vector()
	val expected = GenMap[kmeans.Point, GenSeq[kmeans.Point]]()
                                                  //> expected  : scala.collection.GenMap[kmeans.Point,scala.collection.GenSeq[kme
                                                  //| ans.Point]] = Map()
	val km = new kmeans.KMeans()              //> km  : kmeans.KMeans = kmeans.KMeans@4e04a765
	km.classify(points, means)                //> res0: scala.collection.GenMap[kmeans.Point,scala.collection.GenSeq[kmeans.Po
                                                  //| int]] = Map()
}