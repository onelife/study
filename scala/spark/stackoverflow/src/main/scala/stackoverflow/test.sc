package stackoverflow

import java.io.File
import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.SparkContext._
import org.apache.spark.rdd.RDD
import annotation.tailrec
import scala.reflect.ClassTag

object test  extends stackoverflow.StackOverflow {
	System.setProperty("hadoop.home.dir", "C:/Users/Gang/workspace64/spark")
                                                  //> res0: String = null
  val conf: SparkConf = new SparkConf().setMaster("local").setAppName("StackOverflow")
                                                  //> conf  : org.apache.spark.SparkConf = org.apache.spark.SparkConf@5fdba6f9
  val sc: SparkContext = new SparkContext(conf)   //> Using Spark's default log4j profile: org/apache/spark/log4j-defaults.propert
                                                  //| ies
                                                  //| 17/06/21 22:24:06 INFO SparkContext: Running Spark version 2.1.0
                                                  //| 17/06/21 22:24:07 WARN NativeCodeLoader: Unable to load native-hadoop librar
                                                  //| y for your platform... using builtin-java classes where applicable
                                                  //| 17/06/21 22:24:07 INFO SecurityManager: Changing view acls to: Gang
                                                  //| 17/06/21 22:24:07 INFO SecurityManager: Changing modify acls to: Gang
                                                  //| 17/06/21 22:24:07 INFO SecurityManager: Changing view acls groups to: 
                                                  //| 17/06/21 22:24:07 INFO SecurityManager: Changing modify acls groups to: 
                                                  //| 17/06/21 22:24:07 INFO SecurityManager: SecurityManager: authentication disa
                                                  //| bled; ui acls disabled; users  with view permissions: Set(Gang); groups with
                                                  //|  view permissions: Set(); users  with modify permissions: Set(Gang); groups 
                                                  //| with modify permissions: Set()
                                                  //| 17/06/21 22:24:08 INFO Utils: Successfully started service 'sparkDriver' on 
                                                  //| port 50497.
                                                  //| 17/06/21 22:24:08 INFO SparkEnv: Registering MapOutputTracker
                                                  //| 17/06/21 22:24:08 INFO SparkEnv: Registering BlockManagerMaster
                                                  //| 17/06/21 22:24:08 INFO BlockManagerMasterEndpoint: Using org.apache.spark.st
                                                  //| orage.DefaultTopologyMapper for getting topology information
                                                  //| 17/06/21 22:24:08 INFO BlockManagerMasterEndpoint: BlockManagerMasterEndpoin
                                                  //| t up
                                                  //| 17/06/21 22:24:08 INFO DiskBlockManager: Created local directory at C:\Users
                                                  //| \Gang\AppData\Local\Temp\blockmgr-808976ac-abc0-4fbd-abd7-34275c2f7a77
                                                  //| 17/06/21 22:24:08 INFO MemoryStore: MemoryStore started with capacity 900.6 
                                                  //| MB
                                                  //| 17/06/21 22:24:08 INFO SparkEnv: Registering OutputCommitCoordinator
                                                  //| 17/06/21 22:24:08 INFO Utils: Successfully started service 'SparkUI' on port
                                                  //|  4040.
                                                  //| 17/06/21 22:24:08 INFO SparkUI: Bound SparkUI to 0.0.0.0, and started at htt
                                                  //| p://192.168.10.21:4040
                                                  //| 17/06/21 22:24:09 INFO Executor: Starting executor ID driver on host localho
                                                  //| st
                                                  //| 17/06/21 22:24:09 INFO Utils: Successfully started service 'org.apache.spark
                                                  //| .network.netty.NettyBlockTransferService' on port 50506.
                                                  //| 17/06/21 22:24:09 INFO NettyBlockTransferService: Server created on 192.168.
                                                  //| 10.21:50506
                                                  //| 17/06/21 22:24:09 INFO BlockManager: Using org.apache.spark.storage.RandomBl
                                                  //| ockReplicationPolicy for block replication policy
                                                  //| 17/06/21 22:24:09 INFO BlockManagerMaster: Registering BlockManager BlockMan
                                                  //| agerId(driver, 192.168.10.21, 50506, None)
                                                  //| 17/06/21 22:24:09 INFO BlockManagerMasterEndpoint: Registering block manager
                                                  //|  192.168.10.21:50506 with 900.6 MB RAM, BlockManagerId(driver, 192.168.10.21
                                                  //| , 50506, None)
                                                  //| 17/06/21 22:24:09 INFO BlockManagerMaster: Registered BlockManager BlockMana
                                                  //| gerId(driver, 192.168.10.21, 50506, None)
                                                  //| 17/06/21 22:24:09 INFO BlockManager: Initialized BlockManager: BlockManagerI
                                                  //| d(driver, 192.168.10.21, 50506, None)
                                                  //| sc  : org.apache.spark.SparkContext = org.apache.spark.SparkContext@5003041b
                                                  //| 
  sc.setLogLevel("WARN")
 	
 	val vectors = sc.parallelize(List( (450000, 39),(500000, 31),(150000,1),(150000,10),(500000, 55),(150000,2) ,(150000,22)))
                                                  //> vectors  : org.apache.spark.rdd.RDD[(Int, Int)] = ParallelCollectionRDD[0] a
                                                  //| t parallelize at stackoverflow.test.scala:17
	val means = Array((500000, 13),(150000,10))
                                                  //> means  : Array[(Int, Int)] = Array((500000,13), (150000,10))
	var results: Array[(String, Double, Int, Int)] = stackoverflow.StackOverflow.clusterResults(means, vectors)
                                                  //> results  : Array[(String, Double, Int, Int)] = Array((Python,100.0,4,6), (Sc
                                                  //| ala,66.66666666666666,3,39))
	stackoverflow.StackOverflow.printResults(results)
                                                  //> Resulting clusters:
                                                  //|   Score  Dominant language (%percent)  Questions
                                                  //| ================================================
                                                  //|       6  Python            (100.0%)            4
                                                  //|      39  Scala             (66.7 %)            3
	println(results(0))                       //> (Python,100.0,4,6)
	println(results(1))                       //> (Scala,66.66666666666666,3,39)
	
	/*val resource = this.getClass.getClassLoader.getResource("stackoverflow/stackoverflow.csv")
	val lines   = sc.textFile(new File(resource.toURI).getPath)
	val raw     = rawPostings(lines)
	val grouped = groupedPostings(raw)

	val scored  = scoredPostings(grouped).sample(true, 0.01, 0)
	val vectors = vectorPostings(scored)
	//assert(vectors.count() == 2121822, "Incorrect number of vectors: " + vectors.count())
	
	val means   = kmeans(sampleVectors(vectors), vectors, debug = true)
	val results = clusterResults(means, vectors)
	printResults(results)*/
}