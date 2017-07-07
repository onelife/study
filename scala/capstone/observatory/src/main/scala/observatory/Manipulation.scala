package observatory

import observatory.Visualization._

/**
  * 4th milestone: value-added information
  */
object Manipulation {

  /**
    * @param temperatures Known temperatures
    * @return A function that, given a latitude in [-89, 90] and a longitude in [-180, 179],
    *         returns the predicted temperature at this location
    */
  def makeGrid(temperatures: Iterable[(Location, Double)]): (Int, Int) => Double = {
//    def getTemperature(lat: Int, lon: Int): Double = 
//      predictTemperature(temperatures, Location(lat, lon))
//      
//    getTemperature
    val data = (for {
      lat <- -89 to 90
      lon <- -180 to 179
    } yield (lat, lon) -> predictTemperature(temperatures, Location(lat, lon))).toMap
    
    def getTemperature(lat: Int, lon: Int): Double = 
      data((lat, lon))
      
    getTemperature
  }

  /**
    * @param temperaturess Sequence of known temperatures over the years (each element of the collection
    *                      is a collection of pairs of location and temperature)
    * @return A function that, given a latitude and a longitude, returns the average temperature at this location
    */
  def average(temperaturess: Iterable[Iterable[(Location, Double)]]): (Int, Int) => Double = {
//    def getAverage(lat: Int, lon: Int): Double = 
//      temperaturess.map(makeGrid(_)).map(_(lat, lon)).sum.toDouble / temperaturess.size
//     
//    getAverage
    val averageData = temperaturess.map(makeGrid(_))
    val averageData2 = (for {
      lat <- -89 to 90
      lon <- -180 to 179
      data = averageData.map(_(lat, lon))
    } yield (lat, lon) -> data.sum / data.size).toMap
    
    def getAverage(lat: Int, lon: Int): Double = 
      averageData2((lat, lon))
      
    getAverage
  }

  /**
    * @param temperatures Known temperatures
    * @param normals A grid containing the “normal” temperatures
    * @return A grid containing the deviations compared to the normal temperatures
    */
  def deviation(temperatures: Iterable[(Location, Double)], normals: (Int, Int) => Double): (Int, Int) => Double = {
//    def getDeviation(lat: Int, lon: Int): Double = 
//      predictTemperature(temperatures, Location(lat, lon)) - normals(lat, lon)
//      
//    getDeviation
    val deviationData = (for {
      lat <- -89 to 90
      lon <- -180 to 179
    } yield (lat, lon) -> (predictTemperature(temperatures, Location(lat, lon)) - normals(lat, lon))).toMap
    
    def getDeviation(lat: Int, lon: Int): Double = 
      deviationData((lat, lon))
      
    getDeviation
  }


}

