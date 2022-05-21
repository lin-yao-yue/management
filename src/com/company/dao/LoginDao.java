package com.company.dao;
import com.company.schedules.*;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

public class LoginDao extends BaseDao{

    public Login login(Login admin){
        String sql = "select * from login where name=? and pwd=?";
        Login adminReturn = null;
        try {
            PreparedStatement prst = con.prepareStatement(sql);
            prst.setString(1, admin.getName());
            prst.setString(2, admin.getPwd());
            //产生结果集
            ResultSet executeQuery = prst.executeQuery();
            if(executeQuery.next()){
                adminReturn = new Login();
                adminReturn.setId(executeQuery.getString("id"));
                adminReturn.setName(executeQuery.getString("name"));
                adminReturn.setPwd(executeQuery.getString("pwd"));
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
        return adminReturn;
    }

    //先根据id查找是否存在该admin
    public String editPassword(Login admin,String newPassword){
        String sql = "select * from login where id=? and password=?";
        PreparedStatement prst = null;
        String id = "";
        try {
            prst = con.prepareStatement(sql);
            prst.setString(1, admin.getId());
            prst.setString(2, admin.getPwd());
            ResultSet executeQuery = prst.executeQuery();
            if(!executeQuery.next()){
                String retString = "error password";
                return retString;
            }
            //找到对应admin
            id = executeQuery.getString("id");

        } catch (SQLException e1) {
            // TODO Auto-generated catch block
            e1.printStackTrace();
        }

        String retString = "修改失败";
        String sqlString = "update login set pwd = ? where id = ?";
        try {
            prst = con.prepareStatement(sqlString);
            prst.setString(1, newPassword);
            prst.setString(2, id);
            int rst = prst.executeUpdate();
            if(rst > 0){
                retString = "修改成功";
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return retString;
    }

}
