package com.company.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.company.schedules.*;

public class StuDao extends BaseDao {

    //增加没有 sno 的 Stu 元组
    public boolean addStudent(Stu student) throws SQLException {
        /**
         private String sno;
         private String sname;
         private int age;
         private String sex;
         private String sdept;*/
        String sql = "insert into stu values(?,?,?,?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        //给第几个问号赋值
        preparedStatement.setString(1, student.getSno());
        preparedStatement.setString(2, student.getSname());
        preparedStatement.setInt(3,student.getAge());
        preparedStatement.setString(4,student.getSex());
        preparedStatement.setString(5,student.getSdept());

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
            sql="delete from stu where sno like '"+snoReplace+"' and sname like '"+snameReplace+"' and sex like '"+sexReplace+"' and sdept like'"+deptReplace+"'";
        }
        else{
            ageReplace=Integer.parseInt(age);
            sql="delete from stu where sno like '"+snoReplace+"' and sname like '"+snameReplace+"' and age='"+ageReplace+"' and sex like '"+sexReplace+"' and sdept like'"+deptReplace+"'";
        }

        lines = statement.executeUpdate(sql);

        return lines;
    }

    //sno 作为索引找到修改学生进行修改
    public int update(Stu student) throws SQLException {
        /**
         private String sno;
         private String sname;
         private int age;
         private String sex;
         private String sdept;*/

        String sql = "update stu set sname=?, age=?,sex=?,sdept=? where sno=?";

        int lines=0;

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, student.getSname());
        preparedStatement.setInt(2, student.getAge());
        preparedStatement.setString(3, student.getSex());
        preparedStatement.setString(4, student.getSdept());
        preparedStatement.setString(5, student.getSno());
        lines = preparedStatement.executeUpdate();

        return lines;
    }

    //根据 sname 查找学生
    public List<Stu> searchByName(String name){
        String sql = "select * from stu where sname=?";
        List<Stu> retList = new ArrayList<Stu>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Stu s=new Stu();
                s.setSno(rs.getString("sno"));
                s.setSname(rs.getString("sname"));
                s.setAge(rs.getInt("age"));
                s.setSex(rs.getString("sex"));
                s.setSdept(rs.getString("sdept"));
                retList.add(s);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return retList;
    }

    //根据 sno 查找学生
    public List<Stu> searchBySno(String sno){
        String sql = "select * from stu where sno=?";
        List<Stu> retList = new ArrayList<Stu>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, sno);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Stu s=new Stu();
                s.setSno(rs.getString("sno"));
                s.setSname(rs.getString("sname"));
                s.setAge(rs.getInt("age"));
                s.setSex(rs.getString("sex"));
                s.setSdept(rs.getString("sdept"));
                retList.add(s);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return retList;
    }

    //混合查询
    public List<Stu> searchByCustomize(String sno, String sname, String age, String sex, String dept){
        String snoReplace="%",snameReplace="%",sexReplace="%",deptReplace="%";
        int ageReplace;
        //当文本框中的内容为空时，返回""
        //如何在java中实现字符串的匹配？？？？如果使用like就只能用通配符？？？？？
        if(!Objects.equals(sno, "")) snoReplace=sno;
        if(!Objects.equals(sname, "")) snameReplace=sname;
        if(!Objects.equals(sex, "")) sexReplace=sex;
        if(!Objects.equals(dept, "")) deptReplace=dept;
        List<Stu> retList = new ArrayList<Stu>();

        try {
            Statement statement = con.createStatement();
            String sql="";
            //使用equals, 而不是==
            //如果age为空
            if(Objects.equals(age, "")){
                //like字符串匹配查询，如果不想使用模糊查询，只是用确定查询，只能使用以下方式
                sql="select * from stu where sno like '"+snoReplace+"' and sname like '"+snameReplace+"' and sex like '"+sexReplace+"' and sdept like'"+deptReplace+"'";
            }
            else{
                ageReplace=Integer.parseInt(age);
                sql="select * from stu where sno like '"+snoReplace+"' and sname like '"+snameReplace+"' and age='"+ageReplace+"' and sex like '"+sexReplace+"' and sdept like'"+deptReplace+"'";
            }



            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Stu s=new Stu();
                s.setSno(rs.getString("sno"));
                s.setSname(rs.getString("sname"));
                s.setAge(rs.getInt("age"));
                s.setSex(rs.getString("sex"));
                s.setSdept(rs.getString("sdept"));
                retList.add(s);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return retList;
    }

    //返回所有元组
    public List<Stu> getStudentList(){
        List<Stu> retList = new ArrayList<Stu>();
        try{
            Statement sql;
            ResultSet rs;
            sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //查询结果是可迭代的二维字符串
            rs = sql.executeQuery("select * from Stu");
            while(rs.next()){
                /**
                private String sno;
                private String sname;
                private int age;
                private String sex;
                private String sdept;*/
                Stu s=new Stu();
                s.setSno(rs.getString("sno"));
                s.setSname(rs.getString("sname"));
                s.setAge(rs.getInt("age"));
                s.setSex(rs.getString("sex"));
                s.setSdept(rs.getString("sdept"));
                retList.add(s);
            }
        }
        catch(SQLException e){
            System.out.println("请输入正确的表名"+e);
        }

        return retList;
    }



}
