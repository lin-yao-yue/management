package com.company.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.company.schedules.*;

public class TchDao extends BaseDao {

    //add
    public boolean add(Tch teacher) throws SQLException {
        String sql = "insert into tch values(?,?,?,?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        //给第几个问号赋值
        preparedStatement.setString(1, teacher.getTno());
        preparedStatement.setString(2, teacher.getTname());
        preparedStatement.setInt(3,teacher.getTage());
        preparedStatement.setString(4,teacher.getSex());
        preparedStatement.setString(5,teacher.getTdept());

        //executeUpdate()
        //可以执行执行 INSERT，UPDATE，DELETE，CREATE TABLE 和 DROP TABLE
        //返回值是一个整数（int），指示受影响的行数
        if(preparedStatement.executeUpdate() > 0)return true;

        return false;
    }

    //根据 sno 删除指定学生
    public int delete(String sno, String sname, String age, String sex, String dept) throws SQLException {
        String snoReplace="%",snameReplace="%",sexReplace="%",deptReplace="%";
        int ageReplace;
        //当文本框中的内容为空时，返回""
        //如何在java中实现字符串的匹配？？？？如果使用like就只能用通配符？？？？？
        if(!Objects.equals(sno, "")) snoReplace=sno;
        if(!Objects.equals(sname, "")) snameReplace=sname;
        if(!Objects.equals(sex, "")) sexReplace=sex;
        if(!Objects.equals(dept, "")) deptReplace=dept;

        int lines=0;

        Statement statement = con.createStatement();
        String sql="";
        //使用equals, 而不是==
        //如果age为空
        if(Objects.equals(age, "")){
            //like字符串匹配查询，如果不想使用模糊查询，只是用确定查询，只能使用以下方式
            sql="delete from tch where tno like '"+snoReplace+"' and tname like '"+snameReplace+"' and sex like '"+sexReplace+"' and tdept like'"+deptReplace+"'";
        }
        else{
            ageReplace=Integer.parseInt(age);
            sql="delete from tch where tno like '"+snoReplace+"' and tname like '"+snameReplace+"' and tage='"+ageReplace+"' and sex like '"+sexReplace+"' and tdept like'"+deptReplace+"'";
        }

        lines = statement.executeUpdate(sql);

        return lines;
    }

    //sno 作为索引找到修改学生进行修改
    public int update(Tch teacher) throws SQLException {
        String sql = "update tch set tname=?, tage=?,sex=?,tdept=? where tno=?";

        int lines=0;

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, teacher.getTname());
        preparedStatement.setInt(2, teacher.getTage());
        preparedStatement.setString(3, teacher.getSex());
        preparedStatement.setString(4, teacher.getTdept());
        preparedStatement.setString(5, teacher.getTno());
        lines = preparedStatement.executeUpdate();

        return lines;
    }

    //根据 sname 查找学生
    public List<Tch> searchByName(String name){
        String sql = "select * from tch where tname=?";
        List<Tch> retList = new ArrayList<Tch>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Tch t=new Tch();
                t.setTno(rs.getString("tno"));
                t.setTname(rs.getString("tname"));
                t.setTage(rs.getInt("tage"));
                t.setSex(rs.getString("sex"));
                t.setTdept(rs.getString("tdept"));
                retList.add(t);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return retList;
    }

    //根据 sno 查找学生
    public List<Tch> searchBySno(String tno){
        String sql = "select * from tch where tno=?";
        List<Tch> retList = new ArrayList<Tch>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, tno);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Tch s=new Tch();
                s.setTno(rs.getString("tno"));
                s.setTname(rs.getString("tname"));
                s.setTage(rs.getInt("tage"));
                s.setSex(rs.getString("sex"));
                s.setTdept(rs.getString("tdept"));
                retList.add(s);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return retList;
    }

    //混合查询
    public List<Tch> searchByCustomize(String sno, String sname, String age, String sex, String dept){
        String snoReplace="%",snameReplace="%",sexReplace="%",deptReplace="%";
        int ageReplace;
        //当文本框中的内容为空时，返回""
        //如何在java中实现字符串的匹配？？？？如果使用like就只能用通配符？？？？？
        if(!Objects.equals(sno, "")) snoReplace=sno;
        if(!Objects.equals(sname, "")) snameReplace=sname;
        if(!Objects.equals(sex, "")) sexReplace=sex;
        if(!Objects.equals(dept, "")) deptReplace=dept;
        List<Tch> retList = new ArrayList<Tch>();

        try {
            Statement statement = con.createStatement();
            String sql="";
            //使用equals, 而不是==
            //如果age为空
            if(Objects.equals(age, "")){
                //like字符串匹配查询，如果不想使用模糊查询，只是用确定查询，只能使用以下方式
                sql="select * from tch where tno like '"+snoReplace+"' and tname like '"+snameReplace+"' and sex like '"+sexReplace+"' and tdept like'"+deptReplace+"'";
            }
            else{
                ageReplace=Integer.parseInt(age);
                sql="select * from tch where tno like '"+snoReplace+"' and tname like '"+snameReplace+"' and tage='"+ageReplace+"' and sex like '"+sexReplace+"' and tdept like'"+deptReplace+"'";
            }



            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Tch s=new Tch();
                s.setTno(rs.getString("tno"));
                s.setTname(rs.getString("tname"));
                s.setTage(rs.getInt("tage"));
                s.setSex(rs.getString("sex"));
                s.setTdept(rs.getString("tdept"));
                retList.add(s);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return retList;
    }

    //返回所有元组
    public List<Tch> getAllList(){
        List<Tch> retList = new ArrayList<Tch>();
        try{
            Statement sql;
            ResultSet rs;
            sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //查询结果是可迭代的二维字符串
            rs = sql.executeQuery("select * from tch");
            while(rs.next()){
                Tch s=new Tch();
                s.setTno(rs.getString("tno"));
                s.setTname(rs.getString("tname"));
                s.setTage(rs.getInt("tage"));
                s.setSex(rs.getString("sex"));
                s.setTdept(rs.getString("tdept"));
                retList.add(s);
            }
        }
        catch(SQLException e){
            System.out.println("请输入正确的表名"+e);
        }

        return retList;
    }



}

