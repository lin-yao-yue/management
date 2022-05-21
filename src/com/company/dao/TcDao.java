package com.company.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.company.schedules.*;

public class TcDao extends BaseDao {

    //add
    public boolean add(Tc tc) throws SQLException {
        String sql = "insert into tc values(?,?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        //给第几个问号赋值
        preparedStatement.setString(1, tc.getTno());
        preparedStatement.setString(2, tc.getCno());
        preparedStatement.setString(3,tc.getClassroom());

        //executeUpdate()
        //可以执行执行 INSERT，UPDATE，DELETE，CREATE TABLE 和 DROP TABLE
        //返回值是一个整数（int），指示受影响的行数
        if(preparedStatement.executeUpdate() > 0)return true;

        return false;
    }

    //delete
    public int delete(String sno, String cno, String grade) throws SQLException {
        String snoReplace="%",cnoReplace="%",gradeReplace="%";

        //当文本框中的内容为空时，返回""
        //如何在java中实现字符串的匹配？？？？如果使用like就只能用通配符？？？？？
        if(!Objects.equals(sno, "")) snoReplace=sno;
        if(!Objects.equals(cno, "")) cnoReplace=cno;
        if(!Objects.equals(grade, "")) gradeReplace=grade;

        int lines=0;

        Statement statement = con.createStatement();
        String sql="";
        sql="delete from tc where tno like '"+snoReplace+"' and cno like '"+cnoReplace+"' and classroom like '"+gradeReplace+"'";

        lines = statement.executeUpdate(sql);

        return lines;
    }

    //sno 作为索引找到修改学生进行修改
    public int update(Tc sc) throws SQLException {
        String sql = "update tc set classroom=? where tno=? and cno=?";

        int lines=0;

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1,sc.getClassroom());
        preparedStatement.setString(2, sc.getTno());
        preparedStatement.setString(3, sc.getCno());

        lines = preparedStatement.executeUpdate();

        return lines;
    }

    //混合查询
    public List<Tc> searchByCustomize(String sno, String cno, String grade){
        String snoReplace="%",cnoReplace="%",gradeReplace="%";
        int ageReplace;
        //当文本框中的内容为空时，返回""
        //如何在java中实现字符串的匹配？？？？如果使用like就只能用通配符？？？？？
        if(!Objects.equals(sno, "")) snoReplace=sno;
        if(!Objects.equals(cno, "")) cnoReplace=cno;
        if(!Objects.equals(grade, "")) gradeReplace=grade;

        List<Tc> retList = new ArrayList<Tc>();

        try {
            Statement statement = con.createStatement();
            String sql="";

            sql="select * from tc where tno like '"+snoReplace+"' and cno like '"+cnoReplace+"' and classroom like '"+gradeReplace+"'";

            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Tc sc=new Tc();
                sc.setTno(rs.getString("tno"));
                sc.setCno(rs.getString("cno"));
                sc.setClassroom(rs.getString("classroom"));
                retList.add(sc);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return retList;
    }

    //返回所有元组
    public List<Tc> getAllList(){
        List<Tc> retList = new ArrayList<Tc>();
        try{
            Statement sql;
            ResultSet rs;
            sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //查询结果是可迭代的二维字符串
            rs = sql.executeQuery("select * from tc");
            while(rs.next()){
                Tc sc=new Tc();
                sc.setTno(rs.getString("tno"));
                sc.setCno(rs.getString("cno"));
                sc.setClassroom(rs.getString("classroom"));
                retList.add(sc);
            }
        }
        catch(SQLException e){
            System.out.println("请输入正确的表名"+e);
        }

        return retList;
    }



}


