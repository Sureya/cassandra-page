package sks.training.java;

import com.datastax.driver.core.Cluster;
import com.datastax.driver.core.ProtocolVersion;
import com.datastax.driver.core.Session;
import com.datastax.driver.core.policies.DCAwareRoundRobinPolicy;
import com.datastax.driver.core.policies.DefaultRetryPolicy;
import com.datastax.driver.core.policies.TokenAwarePolicy;
import org.slf4j.LoggerFactory;

import java.util.*;

/**
 * Hello world, is too mainstream  to start with!
 *
 */
public class CassandraConnector
{


    // Instance members

    static Cluster cluster = Cluster
            .builder()
            .addContactPoint("127.0.0.1")
            .withLoadBalancingPolicy(
                    new TokenAwarePolicy(new DCAwareRoundRobinPolicy()))
            .withRetryPolicy(DefaultRetryPolicy.INSTANCE)
            .withProtocolVersion(ProtocolVersion.V3)
            .build();

    static Session session = null;


    public static void createTable(String tableName, String keyspace, HashMap<String,String> columns, Set<String> primarykey){
        try{
//**********************************************************************************************
            // Validations of the data types and primary keys.
            if(tableName.contentEquals("") || tableName.contentEquals(" ") || keyspace.contentEquals("") | keyspace.contentEquals(" ")
                    || tableName==null ||keyspace==null || primarykey.size()==0
                    ){

                System.out.println("Improper parameters");
                System.exit(1);

            }

            Set<String> columnNames = columns.keySet();

            if(!columnNames.contains(primarykey)){
                System.out.println("Primary Key not contained in Column Names");
                System.exit(1);
            }

            String allowedTypes = "ascii,bigint,blob,boolean,counter,decimal,double,float,inet,int,list,map,set,text,timestamp,uuid,timeuuid,varchar,varint";
            String[] definedDataTypes = allowedTypes.split(",");
            Set<String> definedSet= new HashSet<String>(Arrays.asList(definedDataTypes));
            Set<String> givenSet = (Set<String>) columns.values();

            for(String type :givenSet){
                if(!definedSet.contains(type)){
                    System.out.println(type + "is not a valid data type");
                    System.exit(1);
                }
            }
//**********************************************************************************************
            //End of all Validations.
            session = cluster.connect(keyspace);
            String executeStatement = "CREATE TABLE "+tableName+"(";
            String columnDefentions = new String();
            Iterator map = columns.entrySet().iterator();
            while(map.hasNext()){
                Map.Entry pair = (Map.Entry)map.next();
                columnDefentions = columnDefentions +String.valueOf(pair.getKey())+" "+String.valueOf(pair.getValue()).toLowerCase()+", ";
            }

            columnDefentions = columnDefentions +"PRIMARY KEY (";

            for(String pKey: primarykey){
                columnDefentions = columnDefentions +pKey+", ";
            }

            columnDefentions =  columnDefentions.substring(0,columnDefentions.length()-2);

            columnDefentions = columnDefentions +")";

            executeStatement = executeStatement + columnDefentions +");";

            System.out.println(executeStatement);

            session.execute(executeStatement);

            System.out.println("Table Updated Suucessfully");
        }catch (Exception exception){
            System.out.println(exception.getMessage());
        }

    }


}
