package observatory

import com.sksamuel.scrimage.{Image, Pixel}
import com.sksamuel.scrimage.ScaleMethod.{BSpline, Bicubic, Bilinear, FastScale, Lanczos3}

import observatory.Visualization._
import observatory.Interaction._

/**
  * 5th milestone: value-added information visualization
  */
object Visualization2 {

  /**
    * @param x X coordinate between 0 and 1
    * @param y Y coordinate between 0 and 1
    * @param d00 Top-left value
    * @param d01 Bottom-left value
    * @param d10 Top-right value
    * @param d11 Bottom-right value
    * @return A guess of the value at (x, y) based on the four known values, using bilinear interpolation
    *         See https://en.wikipedia.org/wiki/Bilinear_interpolation#Unit_Square
    */
  def bilinearInterpolation(
    x: Double,
    y: Double,
    d00: Double,
    d01: Double,
    d10: Double,
    d11: Double
  ): Double = {
    d00 * (1 - x) * (1 - y) + 
    d01 * (1- x) * y +
    d10 * x * (1 - y) +
    d11 * x * y
  }

  /**
    * @param grid Grid to visualize
    * @param colors Color scale to use
    * @param zoom Zoom level of the tile to visualize
    * @param x X value of the tile to visualize
    * @param y Y value of the tile to visualize
    * @return The image of the tile at (x, y, zoom) showing the grid using the given color scale
    */
  def visualizeGrid(
    grid: (Int, Int) => Double,
    colors: Iterable[(Double, Color)],
    zoom: Int,
    x: Int,
    y: Int
  ): Image = {
    val size = 256 //!128
    //val sizeImage = 256
    val alpha = 127
    val n = 1 << zoom
    val origin = tileLocation(zoom, x, y)
//    println(s"visualizeGrid: origin=$origin, z=$zoom, x=$x, y=$y")
    val lonStep = 360d / n / size
    val latStep = 170.1022d / n / size
    val pixels = for {
      y0 <- 0 until size
      x0 <- 0 until size
      lat = origin.lat - latStep * y0
      lon = origin.lon + lonStep * x0
      lat0 = math.floor(lat).toInt
      lon0 = math.floor(lon).toInt
      d00 = grid(lat0, lon0)
      d01 = grid(lat0 + 1, lon0)
      d10 = grid(lat0, lon0 + 1)
      d11 = grid(lat0 + 1, lon0 + 1)
      t = bilinearInterpolation(lon - lon0, lat - lat0, d00, d01, d10, d11)
      c = interpolateColor(colors, t)
    } yield Pixel(c.red, c.green, c.blue, alpha)
    Image(size, size, pixels.toArray) //.scale(2, FastScale)
    //Image(sizeImage, sizeImage, pixels.toArray).scaleTo(sizeImage, sizeImage, FastScale)
  }

}
