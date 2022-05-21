package com.company.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.company.schedules.*;

public class ScDao extends BaseDao {

    //add
    public boolean add(Sc sc) throws SQLException {
        String sql = "insert into sc values(?,?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        //给第几个问号赋值
        preparedStatement.setString(1, sc.getSno());
        preparedStatement.setString(2, sc.getCno());
        preparedStatement.setString(3,sc.getGrade());

        //executeUpdate()
        //可以执行执行 INSERT，UPDATE，DELETE，CREATE TABLE 和 DROP TABLE
        //返回值是一个整数（int），指示受影响的行数
        if(preparedStatement.executeUpdate() > 0)return true;

        return false;
    }

    //delete
    public int delete(String sno, String cno, String grade) throws SQLException {
        String snoReplace="%",cnoReplace="%",gradeReplace="%";
        int ageReplace;
        //当文本框中的内容为空时，返回""
        //如何在java中实现字符串的匹配？？？？如果使用like就只能用通配符？？？？？
        if(!Objects.equals(sno, "")) snoReplace=sno;
        if(!Objects.equals(cno, "")) cnoReplace=cno;
        if(!Objects.equals(grade, "")) gradeReplace=grade;

        int lines=0;

        Statement statement = con.createStatement();
        String sql="";
        sql="delete from sc where sno like '"+snoReplace+"' and cno like '"+cnoReplace+"' and grade like '"+gradeReplace+"'";

        lines = statement.executeUpdate(sql);

        return lines;
    }

    //sno 作为索引找到修改学生进行修改
    public int update(Sc sc) throws SQLException {
        String sql = "update sc set grade=? where sno=? and cno=?";

        int lines=0;

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1,sc.getGrade());
        preparedStatement.setString(2, sc.getSno());
        preparedStatement.setString(3, sc.getCno());

        lines = preparedStatement.executeUpdate();

        return lines;
    }

    //混合查询
    public List<Sc> searchByCustomize(String sno, String cno, String grade){
        String snoReplace="%",cnoReplace="%",gradeReplace="%";
        int ageReplace;
        //当文本框中的内容为空时，返回""
        //如何在java中实现字符串的匹配？？？？如果使用like就只能用通配符？？？？？
        if(!Objects.equals(sno, "")) snoReplace=sno;
        if(!Objects.equals(cno, "")) cnoReplace=cno;
        if(!Objects.equals(grade, "")) gradeReplace=grade;

        List<Sc> retList = new ArrayList<Sc>();

        try {
            Statement statement = con.createStatement();
            String sql="";

            sql="select * from sc where sno like '"+snoReplace+"' and cno like '"+cnoReplace+"' and grade like '"+gradeReplace+"'";

            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Sc sc=new Sc();
                sc.setSno(rs.getString("sno"));
                sc.setCno(rs.getString("cno"));
                sc.setGrade(rs.getString("grade"));
                retList.add(sc);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return retList;
    }

    //返回所有元组
    public List<Sc> getAllList(){
        List<Sc> retList = new ArrayList<Sc>();
        try{
            Statement sql;
            ResultSet rs;
            sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //查询结果是可迭代的二维字符串
            rs = sql.executeQuery("select * from sc");
            while(rs.next()){
                Sc sc=new Sc();
                sc.setSno(rs.getString("sno"));
                sc.setCno(rs.getString("cno"));
                sc.setGrade(rs.getString("grade"));
                retList.add(sc);
            }
        }
        catch(SQLException e){
            System.out.println("请输入正确的表名"+e);
        }

        return retList;
    }



}


