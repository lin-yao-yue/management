package com.company.dao;
import java.sql.*;

public class BaseDao {
    public Connection con;

    public BaseDao(){
        String url,userName,userPwd;
        url="jdbc:sqlserver://localhost:1433;DatabaseName=management";
        userName="sa";
        userPwd="sa";
        try{
            con = DriverManager.getConnection(url, userName, userPwd);
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }

}
