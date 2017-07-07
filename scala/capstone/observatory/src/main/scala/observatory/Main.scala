package observatory

import org.apache.log4j.{Level, Logger}
import observatory.Extraction._
import observatory.Visualization._
import observatory.Interaction._
import observatory.Manipulation._
import observatory.Visualization2._
import observatory.Interaction2._


object Main extends App {
  Logger.getLogger("org.apache.spark").setLevel(Level.WARN)
  
  val colorTemp = List(
    ( 60d, Color(255, 255, 255)),
    ( 32d, Color(255, 0,   0  )),
    ( 12d, Color(255, 255, 0  )),
    (  0d, Color(0,   255, 255)),
    (-15d, Color(0,   0,   255)),
    (-27d, Color(255, 0,   255)),
    (-50d, Color(33,  0,   107)),
    (-60d, Color(0,   0,   0  )))
    
  val colorsDevi = List(
    (  7d, Color(0,   0,   0  )),
    (  4d, Color(255, 0,   0  )),
    (  2d, Color(255, 255, 0  )),
    (  0d, Color(255, 255, 255)),
    ( -2d, Color(0,   255, 255)),
    ( -7d, Color(0,   0,   255)))
  
  val yearlyData = for {
    year <- 2015 to 2015
    yearData = locateTemperatures(
        year,
        "/stations.csv",
        s"/$year.csv"
        )
  } yield (year, locationYearlyAverageRecords(yearData))
  
  def generateImage(year: Int, zoom: Int, x: Int, y: Int, data: Iterable[(Location, Double)]) = {
    //if (zoom == 2) {
      val img = tile(data, colorTemp, zoom, x, y)
      val dirName = s"target/temperatures/$year/$zoom/"
      val fileName = s"$x-$y.png"
      val dir = new java.io.File(dirName)
      if (!dir.exists())
        dir.mkdirs()
      img.output(new java.io.File(dirName + fileName))
      println(dirName + fileName)
    //}
  }
  generateTiles[Iterable[(Location, Double)]](yearlyData, generateImage)
  
  val yearlyData2 = for {
    year <- 1975 to 1989
    yearData = locateTemperatures(
        year,
        "/stations.csv",
        s"/$year.csv"
        )
  } yield locationYearlyAverageRecords(yearData)
  
  def generateImage2(year: Int, zoom: Int, x: Int, y: Int, data: Iterable[(Location, Double)]) = {
    val img = visualizeGrid(
        deviation(data, average(yearlyData2)),
        colorsDevi,
        zoom, x, y
        )
    val dirName = s"target/deviations/$year/$zoom/"
    val fileName = s"$x-$y.png"
    val dir = new java.io.File(dirName)
    if (!dir.exists())
      dir.mkdirs()
    img.output(new java.io.File(dirName + fileName))
    println(dirName + fileName)
  }
  generateTiles[Iterable[(Location, Double)]](yearlyData, generateImage2)
  
}
