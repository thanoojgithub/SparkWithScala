package com

import org.apache.spark.SparkConf
import org.apache.spark.SparkContext
import org.apache.spark.rdd.RDD.rddToPairRDDFunctions
import java.util.Date
import java.util.Calendar

object WordCountOne {
  def main(args: Array[String]): Unit = {
    val sparkConf = new SparkConf();
    sparkConf.setMaster("spark://thanoojubuntu-Inspiron-3521:7077")
    sparkConf.setAppName("SparkWithScalaWordCount")
    val sc = new SparkContext(sparkConf)
    val textFile = sc.textFile("hdfs://localhost:9000/user/hduser/hive/inputs/wc")
    val wordCounts = textFile.flatMap(line => line.split(" "))
                 .map(word => (word, 1))
                 .reduceByKey(_ + _)
    wordCounts.saveAsTextFile("hdfs://localhost:9000/user/hduser/hive/outputs/wc_"+Calendar.getInstance.getTimeInMillis)
    sc.stop()
  }
}