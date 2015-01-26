# NSMC: A Native MongoDB Connector for Apache Spark

This is a native connector for reading and writing MongoDB collections
directly from Apache Spark. In Spark, the data from MongoDB is represented as an
RDD[MongoDBObject].

Fore more details about this project, check this [blog post](http://www.river-of-bytes.com/2015/01/nsmc-native-mongodb-connector-for.html).

# Current Limitations

- No Java or Python API bindings
- Can only read (no updates)
- Only tested with the following configurations:
 - MongoDB: 2.6
 - Scala: 2.10
 - Spark: 1.1.0
 - Casbah 2.7
- Not tested with MongoDB's hash-based partitioning
- Known to leak resources quite badly

# Other Warnings

- The APIs will probably change several times before an official release

# Release Status

Not yet released. A snapshot is available at Sonatype --s ee "Getting Started" below.

# Licensing

See the top-level LICENSE file

# Getting Started

Add the following to your build.sbt for the latest _stable_ release:

    scalaVersion := "2.10.4" // any 2.10 is OK -- support for 2.11 coming soon

    libraryDependencies += "org.apache.spark" %% "spark-core" % "1.1.0"

    libraryDependencies += "com.github.spirom" %% "spark-mongodb-connector" % "0.3.0"

To use the latest snapshot release:

    scalaVersion := "2.10.4" // any 2.10 is OK -- support for 2.11 coming soon

    resolvers +=
        "SonatypeOSSSnapshots" at "https://oss.sonatype.org/content/repositories/snapshots"

    libraryDependencies += "org.apache.spark" %% "spark-core" % "1.1.0"

    libraryDependencies += "com.github.spirom" %% "spark-mongodb-connector" % "0.2-SNAPSHOT"

In your code, you need to add:

    import nsmc._

    import com.mongodb.casbah.commons.MongoDBObject

Then to actually read from a collection:

    val conf = new SparkConf()
        .setAppName("My MongoApp").setMaster("local[4]")
        .set("nsmc.connection.host", "myMongoHost")
        .set("nsmc.connection.port", "myMongoPort")
        .set("nsmc.user", "yourUsernameHere")
        .set("nsmc.password", "yourPasswordHere")
    val sc = newSparkContext(conf)
    val data: MongoRDD[MongoDBObject] =
        sc.mongoCollection[MongoDBObject]("myDB", "myCollection")


