# Simple-CURD-operations-in-Cassandra-


A simple introductory tutorials on Cassandra NoSql database.

The examples provided, makes use of DataStax APi for JAVA

**Configurations used are : [cqlsh 5.0.1 | Cassandra 2.1.2 | CQL spec 3.2.0 | Native protocol v3]**__

For Installing Cassandra 

Link :  http://www.apache.org/dyn/closer.cgi?path=/cassandra/2.1.3/apache-cassandra-2.1.2-bin.tar.gz

Dependencies Used: 

<dependency>
    <groupId>com.datastax.cassandra</groupId>
    <artifactId>cassandra-driver-core</artifactId>
    <version>2.1.4</version>
</dependency>

<dependency>
    <groupId>com.datastax.cassandra</groupId>
    <artifactId>cassandra-driver-mapping</artifactId>
    <version>2.1.4</version>
</dependency>


Starting Cassandra Service:

> Open  Terminal :
> [Step 1] : Download and extract Cassandra from above link:

> [Step 2] : cd /path/to/extracted/folder

> [Step 3] : bin/cassandra -f 

> [Step 4] : In another terminal type : ./bin/cqlsh

> [Step 5] : CREATE KEYSPACE YourKeySpace
  WITH REPLICATION = { 'class' : 'SimpleStrategy', 'replication_factor' : 3 };

> [Step 6] : Type : <use  YourKeySpace>


Note: Creating keyspace is a onetime operation, once you have created the keyspace you can just starting using it. For creating new keyspaces you can follow same steps as mentioned above.


I. CREATING SIMPLE TABLES


1. Simple  CURD operations 
### CREATE TABLE student_details(
student_id varchar PRIMARY KEY,
class_name text,
department text,

);

