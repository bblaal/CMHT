package com.db.utils;

import java.sql.Connection;
import java.sql.DriverManager;
import com.cmht.utility.UtilityOperation;
public class Database {
    // MySQL DB connection parameters
    private static final String driverName = "com.mysql.cj.jdbc.Driver";
    private static final String dbName = "jdbc:mysql://localhost:3306/Champ";
    private static final String dbUserName = "root";
    private static final String dbPwd = "root1234";
    public static Connection connectDb(){

        try{

            Class.forName(driverName);

            return DriverManager.getConnection(dbName, dbUserName, dbPwd);
        }catch(Exception e){
            UtilityOperation utilityOperation = new UtilityOperation();
            utilityOperation.showMessage("Error in  - " + e.getMessage(), "Alert!!!");
            e.printStackTrace();
        }
        return null;
    }
}
