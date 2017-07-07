package observatory

import java.time.LocalDate
import scala.io.Source

/**
  * 1st milestone: data extraction
  */
object Extraction {

  /**
    * @param year             Year number
    * @param stationsFile     Path of the stations resource file to use (e.g. "/stations.csv")
    * @param temperaturesFile Path of the temperatures resource file to use (e.g. "/1975.csv")
    * @return A sequence containing triplets (date, location, temperature)
    */
  def locateTemperatures(year: Int, stationsFile: String, temperaturesFile: String): Iterable[(LocalDate, Location, Double)] = {
    println("@@@")
    println(year)
    def getData(file: String) = Source.fromInputStream(getClass.getResourceAsStream(file)).getLines.map(_.split(","))

    val stations = getData(stationsFile)
                    .filter(_.length == 4)
                    .map(x => ((x(0), x(1)), Location(x(2).toDouble, x(3).toDouble))).toMap
    val temp = getData(temperaturesFile)
                    .filter(x => stations.contains((x(0), x(1))))
                    .map(x => (LocalDate.of(year, x(2).toInt, x(3).toInt), stations((x(0), x(1))), (x(4).toDouble -32) * 5 / 9))
    temp.toIterable
  }

  /**
    * @param records A sequence containing triplets (date, location, temperature)
    * @return A sequence containing, for each location, the average temperature over the year.
    */
  def locationYearlyAverageRecords(records: Iterable[(LocalDate, Location, Double)]): Iterable[(Location, Double)] = {
    records.groupBy(_._2).map(x => (x._1, x._2.map(_._3).sum / x._2.size))
  }

}
