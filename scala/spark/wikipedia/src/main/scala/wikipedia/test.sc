package wikipedia
import WikipediaRanking._

object test {
	wikipedia.WikipediaData.filePath          //> file:/C:/Users/Gang/workspace64/spark/wikipedia/bin/
                                                  //| res0: String = C:\Users\Gang\workspace64\spark\wikipedia\bin\wikipedia\wikipe
                                                  //| dia.dat
  //val rdd = sc.parallelize(Seq(WikipediaArticle("title", "Java Jakarta")))
	//wikipedia.WikipediaRanking.occurrencesOfLang("Java", rdd)
	
    val langs = List("Scala", "Java", "Groovy", "Haskell", "Erlang")
                                                  //> langs  : List[String] = List(Scala, Java, Groovy, Haskell, Erlang)
    val articles = List(
        WikipediaArticle("1","Groovy is pretty interesting, and so is Erlang"),
        WikipediaArticle("2","Scala and Java run on the JVM"),
        WikipediaArticle("3","Scala is not purely functional"),
        WikipediaArticle("4","The cool kids like Haskell more than Java"),
        WikipediaArticle("5","Java is for enterprise developers")
      )                                           //> articles  : List[wikipedia.WikipediaArticle] = List(WikipediaArticle(1,Groov
                                                  //| y is pretty interesting, and so is Erlang), WikipediaArticle(2,Scala and Jav
                                                  //| a run on the JVM), WikipediaArticle(3,Scala is not purely functional), Wikip
                                                  //| ediaArticle(4,The cool kids like Haskell more than Java), WikipediaArticle(5
                                                  //| ,Java is for enterprise developers))
    val rdd = sc.parallelize(articles)            //> Using Spark's default log4j profile: org/apache/spark/log4j-defaults.propert
                                                  //| ies
                                                  //| 17/06/15 01:40:31 INFO SparkContext: Running Spark version 2.1.0
                                                  //| 17/06/15 01:40:31 WARN NativeCodeLoader: Unable to load native-hadoop librar
                                                  //| y for your platform... using builtin-java classes where applicable
                                                  //| 17/06/15 01:40:31 INFO SecurityManager: Changing view acls to: Gang
                                                  //| 17/06/15 01:40:31 INFO SecurityManager: Changing modify acls to: Gang
                                                  //| 17/06/15 01:40:31 INFO SecurityManager: Changing view acls groups to: 
                                                  //| 17/06/15 01:40:31 INFO SecurityManager: Changing modify acls groups to: 
                                                  //| 17/06/15 01:40:31 INFO SecurityManager: SecurityManager: authentication disa
                                                  //| bled; ui acls disabled; users  with view permissions: Set(Gang); groups with
                                                  //|  view permissions: Set(); users  with modify permissions: Set(Gang); groups 
                                                  //| with modify permissions: Set()
                                                  //| 17/06/15 01:40:32 INFO Utils: Successfully started service 'sparkDriver' on 
                                                  //| port 63230.
                                                  //| 17/0
                                                  //| Output exceeds cutoff limit.
    
    val ranked = rankLangs(langs, rdd)            //> 17/06/15 01:40:34 INFO SparkContext: Starting job: count at WikipediaRanking
                                                  //| .scala:33
                                                  //| 17/06/15 01:40:34 INFO DAGScheduler: Got job 0 (count at WikipediaRanking.sc
                                                  //| ala:33) with 1 output partitions
                                                  //| 17/06/15 01:40:34 INFO DAGScheduler: Final stage: ResultStage 0 (count at Wi
                                                  //| kipediaRanking.scala:33)
                                                  //| 17/06/15 01:40:34 INFO DAGScheduler: Parents of final stage: List()
                                                  //| 17/06/15 01:40:34 INFO DAGScheduler: Missing parents: List()
                                                  //| 17/06/15 01:40:34 INFO DAGScheduler: Submitting ResultStage 0 (MapPartitions
                                                  //| RDD[4] at filter at WikipediaRanking.scala:33), which has no missing parents
                                                  //| 
                                                  //| 17/06/15 01:40:34 INFO MemoryStore: Block broadcast_1 stored as values in me
                                                  //| mory (estimated size 1792.0 B, free 900.5 MB)
                                                  //| 17/06/15 01:40:34 INFO MemoryStore: Block broadcast_1_piece0 stored as bytes
                                                  //|  in memory (estimated size 1224.0 B, free 900.5 MB)
                                                  //| 17/06/15 01:40:34 INFO BlockManagerInfo: Added broadcast_1_piece0 in memory 
                                                  //| on 192.168.1
                                                  //| Output exceeds cutoff limit.
    val index = makeIndex(langs, rdd)             //> index  : org.apache.spark.rdd.RDD[(String, Iterable[wikipedia.WikipediaArtic
                                                  //| le])] = ShuffledRDD[10] at groupByKey at WikipediaRanking.scala:53
    val ranked2 = rankLangsUsingIndex(index)      //> 17/06/15 01:40:35 INFO SparkContext: Starting job: collect at WikipediaRanki
                                                  //| ng.scala:62
                                                  //| 17/06/15 01:40:35 INFO DAGScheduler: Registering RDD 9 (flatMap at Wikipedia
                                                  //| Ranking.scala:50)
                                                  //| 17/06/15 01:40:35 INFO DAGScheduler: Got job 5 (collect at WikipediaRanking.
                                                  //| scala:62) with 1 output partitions
                                                  //| 17/06/15 01:40:35 INFO DAGScheduler: Final stage: ResultStage 6 (collect at 
                                                  //| WikipediaRanking.scala:62)
                                                  //| 17/06/15 01:40:35 INFO DAGScheduler: Parents of final stage: List(ShuffleMap
                                                  //| Stage 5)
                                                  //| 17/06/15 01:40:35 INFO DAGScheduler: Missing parents: List(ShuffleMapStage 5
                                                  //| )
                                                  //| 17/06/15 01:40:35 INFO DAGScheduler: Submitting ShuffleMapStage 5 (MapPartit
                                                  //| ionsRDD[9] at flatMap at WikipediaRanking.scala:50), which has no missing pa
                                                  //| rents
                                                  //| 17/06/15 01:40:35 INFO MemoryStore: Block broadcast_6 stored as values in me
                                                  //| mory (estimated size 3.6 KB, free 900.4 MB)
                                                  //| 17/06/15 01:40:35 INFO MemoryStore: Block broadcast_6_piece0 stored as byt
                                                  //| Output exceeds cutoff limit.
    val ranked3 = rankLangsReduceByKey(langs, rdd)//> 17/06/15 01:40:35 INFO SparkContext: Starting job: collect at WikipediaRanki
                                                  //| ng.scala:75
                                                  //| 17/06/15 01:40:35 INFO DAGScheduler: Registering RDD 12 (flatMap at Wikipedi
                                                  //| aRanking.scala:72)
                                                  //| 17/06/15 01:40:35 INFO DAGScheduler: Got job 6 (collect at WikipediaRanking.
                                                  //| scala:75) with 1 output partitions
                                                  //| 17/06/15 01:40:35 INFO DAGScheduler: Final stage: ResultStage 8 (collect at 
                                                  //| WikipediaRanking.scala:75)
                                                  //| 17/06/15 01:40:35 INFO DAGScheduler: Parents of final stage: List(ShuffleMap
                                                  //| Stage 7)
                                                  //| 17/06/15 01:40:35 INFO DAGScheduler: Missing parents: List(ShuffleMapStage 7
                                                  //| )
                                                  //| 17/06/15 01:40:35 INFO DAGScheduler: Submitting ShuffleMapStage 7 (MapPartit
                                                  //| ionsRDD[12] at flatMap at WikipediaRanking.scala:72), which has no missing p
                                                  //| arents
                                                  //| 17/06/15 01:40:35 INFO MemoryStore: Block broadcast_8 stored as values in me
                                                  //| mory (estimated size 3.2 KB, free 900.4 MB)
                                                  //| 17/06/15 01:40:35 INFO MemoryStore: Block broadcast_8_piece0 stored as b
                                                  //| Output exceeds cutoff limit.
    
    ranked                                        //> res1: List[(String, Int)] = List((Java,3), (Scala,2), (Groovy,1), (Haskell,1
                                                  //| ), (Erlang,1))
    ranked2                                       //> res2: List[(String, Int)] = List((Java,3), (Scala,2), (Haskell,1), (Groovy,1
                                                  //| ), (Erlang,1))
    ranked3                                       //> res3: List[(String, Int)] = List((Java,3), (Scala,2), (Haskell,1), (Groovy,1
                                                  //| ), (Erlang,1))
}