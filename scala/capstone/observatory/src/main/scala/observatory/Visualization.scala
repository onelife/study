package observatory

import com.sksamuel.scrimage.{Image, Pixel}

/**
  * 2nd milestone: basic visualization
  */
object Visualization {

  /**
    * @param temperatures Known temperatures: pairs containing a location and the temperature at this location
    * @param location Location where to predict the temperature
    * @return The predicted temperature at `location`
    */
  def predictTemperature(temperatures: Iterable[(Location, Double)], location: Location): Double = {
    val earthRadius = 6371000d
    val minAngle = 1000d / earthRadius
    val p = 2d
//    if (location.lon % 20 == 0)
//      println(location)
    
    def centralAngle(x: Location, y: Location): Double = {
      val xLat = math.toRadians(x.lat)
      val yLat = math.toRadians(y.lat)
      val xLon = math.toRadians(x.lon)
      val yLon = math.toRadians(y.lon)
      math.acos(
          math.sin(xLat) * math.sin(yLat) + 
          math.cos(xLat) * math.cos(yLat) * 
          math.cos(math.abs(xLon - yLon))
          )
    }
    
    def interpolat(d: Iterable[(Double, Double)]): Double =
      d.map(x => x._2 / math.pow(x._1, p)).sum / d.map(x => 1d / math.pow(x._1, p)).sum
      
    val distances = temperatures.map(x => (centralAngle(x._1, location), x._2))
    val minDistance = distances.minBy(_._1)
    
    if (minDistance._1 < minAngle)
      minDistance._2
    else 
      interpolat(distances)
  }

  /**
    * @param points Pairs containing a value and its associated color
    * @param value The value to interpolate
    * @return The color that corresponds to `value`, according to the color scale defined by `points`
    */
  def interpolateColor(points: Iterable[(Double, Color)], value: Double): Color = {
    def linearInterpolate(dx: Double, y0: Int, y1: Int): Int = (y0 * (1 - dx) + y1 * dx).round.toInt
    val sortedDelta = points.map(x => (1d / (value - x._1), x._1, x._2)).toList.sortBy(_._1)
    val lastIndex = sortedDelta.size - 1
    
    if (sortedDelta(0)._1.isInfinity)
      sortedDelta(0)._3
    else if (sortedDelta(lastIndex)._1.isInfinity)
      sortedDelta(lastIndex)._3
    else if (sortedDelta(lastIndex)._1 > 0)
      if (sortedDelta(0)._1 > 0)
        sortedDelta(lastIndex)._3
      else {
        val dx = (value - sortedDelta(lastIndex)._2) / (sortedDelta(0)._2 - sortedDelta(lastIndex)._2)
        val y0 = sortedDelta(lastIndex)._3
        val y1 = sortedDelta(0)._3
        val r = linearInterpolate(dx, y0.red, y1.red)
        val g = linearInterpolate(dx, y0.green, y1.green)
        val b = linearInterpolate(dx, y0.blue, y1.blue)
        //System.out.println((value, "=>", (r, g, b)))
        Color(r, g, b)
      }
    else
      sortedDelta(0)._3
  }

  /**
    * @param temperatures Known temperatures
    * @param colors Color scale
    * @return A 360×180 image where each pixel shows the predicted temperature at its location
    */
  def visualize(temperatures: Iterable[(Location, Double)], colors: Iterable[(Double, Color)]): Image = {
    val alpha = 255 //127
    val pixels = for {
      y <- 0 until 180
      x <- 0 until 360
      t = predictTemperature(temperatures, Location(90 - y, x - 180))
      c = interpolateColor(colors, t)
    } yield Pixel(c.red, c.green, c.blue, alpha)
    Image(360, 180, pixels.toArray)
  }

}

