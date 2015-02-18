package sks.training.java;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Set;

/**
 * Created by sureya on 18/2/15.
 */
public class MainClass {

    public static void main( String[] args )
    {
        String tableName = "users";
        HashMap<String,String> columDefenitions = new HashMap<String, String>();
        columDefenitions.put("id","varchar");
        columDefenitions.put("name","text");
        columDefenitions.put("email","varchar");

        ArrayList<String> primaryKey = new ArrayList<String>();
        primaryKey.add("id");


        Set<String> set = new HashSet<String>(primaryKey);

        CassandraConnector.createTable(tableName, "dsp", columDefenitions, set);
        // replace "dsp" with your key-space name.

    }
}
