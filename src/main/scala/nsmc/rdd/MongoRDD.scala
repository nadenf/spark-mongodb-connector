package nsmc.rdd

import com.mongodb.casbah.Imports._
import nsmc.mongo.{CollectionConfig, MongoConnectorConf, MongoConnector}

import java.io.IOException

import nsmc.rdd.partitioner.{MongoRDDPartition, MongoRDDPartitioner}
import org.apache.spark.rdd.RDD
import org.apache.spark.{Partition, SparkContext, TaskContext}


import scala.language.existentials
import scala.reflect.ClassTag



  class MongoRDD[R] private[nsmc] (
                                             @transient sc: SparkContext,
                                             val connector: MongoConnector,
                                             val collectionConfig: CollectionConfig
                                         )(
                                             implicit
                                             ct : ClassTag[R])
    extends RDD[R](sc, Seq.empty)  {


    override def getPartitions: Array[Partition] = {
      val partitions = new MongoRDDPartitioner(collectionConfig).partitions()
      partitions
    }

    override def getPreferredLocations(split: Partition) =
      Seq()


    override def compute(split: Partition, context: TaskContext): Iterator[R] = {
      val mp = split.asInstanceOf[MongoRDDPartition]
      MongoConnector.getCollection(collectionConfig.databaseName, collectionConfig.collectionName, mp.interval).asInstanceOf[Iterator[R]]
    }

  }

  object MongoRDD {

  }