package sks.training.java;

import com.datastax.driver.core.*;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import com.datastax.driver.core.querybuilder.QueryBuilder;

import static com.datastax.driver.core.querybuilder.QueryBuilder.eq;

/**
 * Created by sureya on 21/2/15.
 */


/*
create Key-space as described in the README

Lets create the following  table using CQL command line:

Cassandra 101 :

    * Keep the cassandra service alive, if you want to see results in your code.
    * All tables must have at-least one primary-key
    * As u can guess, there can be multiple primary keys as well.
    * By default, You can only query based on primary keys
    * That's pretty much everything you need to know right now.



    CREATE TABLE student (
    roll_number varchar PRIMARY KEY,
    Name text,
    ClassName text,
    Department text,
    Score int
    );


    If you want the code to create table using Java API , See

        * CassandraConnector.createTable(...);


 */





public class BeginnerLevelCode {
    static Cluster cluster = Cluster
            .builder()
            .addContactPoint("127.0.0.1")
            .withLoadBalancingPolicy(
                    new TokenAwarePolicy(new DCAwareRoundRobinPolicy()))
            .withRetryPolicy(DefaultRetryPolicy.INSTANCE)
            .withProtocolVersion(ProtocolVersion.V3)
            .build();

    private static Session session = null;



    public static void retriveAll(String TableName) {
        session = cluster.connect("sampleKeySpace");

        try {
            Statement select = QueryBuilder.select().all().from("sampleKeySpace", TableName);
            // I can only assume you to be intelligent enough to change the keyspace name.

            ResultSet results = session.execute(select);

            for(Row row :results){
                System.out.println(row.getString("Name")); //Everything inside { " " } is CASE-SENSITIVE column names.
                System.out.println(row.getString("ClassName"));
                System.out.println(row.getString("Department"));
                System.out.println(row.getInt("Score"));
            }

            session.close();
            // It would be best practice to always shut things up once you are done with it.

        } catch (Exception exp) {
            System.out.println(exp.getMessage());
        }
    }


    public static void updateTable(){
        try{

            session = cluster.connect("sampleKeySpace");
            PreparedStatement statement = session.prepare("INSERT INTO student (roll_number, Name, ClassName, Department,Score)VALUES (?,?,?,?,?);");
            BoundStatement boundStatement = new BoundStatement(statement);
            session.execute(boundStatement.bind("CAS101","JackAss","CSE-A","CSE",10167));
        }catch (Exception ex) {
            System.out.println(ex.getMessage());
        }
    }


    public static void retriveByColumn(String columnName, String columnValue,String TableName){

        /*
        By default this function will throw an error. Because, you have only "roll_number" as primary key

        Hence,

        [Valid : ] SELECT * FROM student WHERE roll_number = 101;

        [Invalid : ]  SELECT * FROM student WHERE Name='Jacob';

        To execute the command open CQLSH and type ,

            *  CREATE INDEX name ON student (Name);

               By doing so we can execute this function without any issues and also any other columns other than primary keys can be indexed.
         */

        try{

            session = cluster.connect("sampleKeySpace");


            if (columnName.contentEquals("Score")){
                Statement select = QueryBuilder.select().all().from("sampleKeySpace", TableName)
                        .where(eq(columnName,Integer.valueOf(columnValue)));

                ResultSet result = session.execute(select);

                for(Row row : result){

                    System.out.println(row.getString("Name"));
                    System.out.println(row.getString("ClassName"));
                    System.out.println(row.getString("Department"));
                    //System.out.println(row.getInt("Score"));
                }
            }

            else {
                Statement select = QueryBuilder.select().all().from("sampleKeySpace", TableName)
                        .where(eq(columnName, columnValue));
                ResultSet result = session.execute(select);

                for(Row row : result){

                    System.out.println(row.getString("Name"));
                    System.out.println(row.getString("ClassName"));
                    System.out.println(row.getString("Department"));
                    System.out.println(row.getInt("Score"));

                }
            }


        }catch (Exception exp){
            System.out.println(exp.getMessage());
        }
    }








    }
