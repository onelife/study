package observatory

import com.sksamuel.scrimage.{Image, Pixel}
import com.sksamuel.scrimage.ScaleMethod.{BSpline, Bicubic, Bilinear, FastScale, Lanczos3}

/**
  * 3rd milestone: interactive visualization
  */
object Interaction {

  /**
    * @param zoom Zoom level
    * @param x X coordinate
    * @param y Y coordinate
    * @return The latitude and longitude of the top-left corner of the tile, as per http://wiki.openstreetmap.org/wiki/Slippy_map_tilenames
    */
  def tileLocation(zoom: Int, x: Int, y: Int): Location = {
    val n = 1 << zoom
//    val lat = math.toDegrees(math.atan(math.sinh(math.Pi * (1 - 2d * y / n)))) 
//    val lon = x.toDouble / n * 360.0 - 180.0
    val lat = 85.0511d - 170.1022d / n * y
    val lon = 360d / n * x - 180
    Location(lat, lon)
  }

  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @param zoom Zoom level
    * @param x X coordinate
    * @param y Y coordinate
    * @return A 256×256 image showing the contents of the tile defined by `x`, `y` and `zooms`
    */
  def tile(temperatures: Iterable[(Location, Double)], colors: Iterable[(Double, Color)], zoom: Int, x: Int, y: Int): Image = {
//    println(s"tile: z=$zoom, x= $x, y=$y")
    import observatory.Visualization._
    val size = 256 //!128
    val alpha = 127
    val n = 1 << zoom
    val origin = tileLocation(zoom, x, y)
    val lonStep = 360d / n / size
    val latStep = 170.1022d / n / size
    val pixels = 
      for {
        y0 <- 0 until size
        x0 <- 0 until size
        t = predictTemperature(temperatures, Location(origin.lat - latStep * y0, origin.lon + lonStep * x0))
        c = interpolateColor(colors, t)
      } yield Pixel(c.red, c.green, c.blue, alpha)
    Image(size, size, pixels.toArray) //.scale(2, FastScale)
  }

  /**
    * Generates all the tiles for zoom levels 0 to 3 (included), for all the given years.
    * @param yearlyData Sequence of (year, data), where `data` is some data associated with
    *                   `year`. The type of `data` can be anything.
    * @param generateImage Function that generates an image given a year, a zoom level, the x and
    *                      y coordinates of the tile and the data to build the image from
    */
  def generateTiles[Data](
    yearlyData: Iterable[(Int, Data)],
    generateImage: (Int, Int, Int, Int, Data) => Unit
  ): Unit = {
//!    for {
//      (year, data) <- yearlyData
//      zoom <- 0 to 3
//      n = 1 << zoom
//      y <- 0 until n
//      x <- 0 until n
//    } yield generateImage(year, zoom, x, y, data)
//    ()
    for ((year, data) <- yearlyData)
      for (zoom <- 0 to 3) {
        val n = 1 << zoom
        for (y <- 0 until n)
          for (x <- 0 until n)
            generateImage(year, zoom, x, y, data)
      }
  }

}
