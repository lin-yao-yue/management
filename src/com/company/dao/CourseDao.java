package com.company.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import com.company.schedules.*;

public class CourseDao extends BaseDao {

    //add
    public boolean add(Course course) throws SQLException {
        String sql = "insert into course values(?,?,?,?,?)";
        PreparedStatement preparedStatement = con.prepareStatement(sql);
        //给第几个问号赋值
        preparedStatement.setString(1, course.getCno());
        preparedStatement.setString(2, course.getCname());
        preparedStatement.setInt(3,course.getStuCnts());
        preparedStatement.setString(4,course.getStudyTime());
        preparedStatement.setString(5,course.getCredit());

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
            sql="delete from course where cno like '"+snoReplace+"' and cname like '"+snameReplace+"' and studyTime like '"+sexReplace+"' and credit like'"+deptReplace+"'";
        }
        else{
            ageReplace=Integer.parseInt(age);
            sql="delete from course where cno like '"+snoReplace+"' and cname like '"+snameReplace+"' and stuCnts='"+ageReplace+"' and studyTime like '"+sexReplace+"' and credit like'"+deptReplace+"'";
        }

        lines = statement.executeUpdate(sql);

        return lines;
    }

    //sno 作为索引找到修改学生进行修改
    public int update(Course course) throws SQLException {
        String sql = "update course set cname=?, stuCnts=?,studyTime=?,credit=? where cno=?";

        int lines=0;

        PreparedStatement preparedStatement = con.prepareStatement(sql);
        preparedStatement.setString(1, course.getCname());
        preparedStatement.setInt(2, course.getStuCnts());
        preparedStatement.setString(3, course.getStudyTime());
        preparedStatement.setString(4, course.getCredit());
        preparedStatement.setString(5, course.getCno());
        lines = preparedStatement.executeUpdate();

        return lines;
    }

    //根据 sname 查找学生
    public List<Course> searchByName(String name){
        String sql = "select * from course where cname=?";
        List<Course> retList = new ArrayList<Course>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, name);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Course c=new Course();
                c.setCno(rs.getString("cno"));
                c.setCname(rs.getString("cname"));
                c.setStuCnts(rs.getInt("stuCnts"));
                c.setStudyTime(rs.getString("studyTime"));
                c.setCredit(rs.getString("credit"));
                retList.add(c);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return retList;
    }

    //根据 sno 查找学生
    public List<Course> searchByNo(String tno){
        String sql = "select * from course where cno=?";
        List<Course> retList = new ArrayList<Course>();
        try {
            PreparedStatement preparedStatement = con.prepareStatement(sql);
            preparedStatement.setString(1, tno);
            ResultSet rs = preparedStatement.executeQuery();
            while(rs.next()){
                Course c=new Course();
                c.setCno(rs.getString("cno"));
                c.setCname(rs.getString("cname"));
                c.setStuCnts(rs.getInt("stuCnts"));
                c.setStudyTime(rs.getString("studyTime"));
                c.setCredit(rs.getString("credit"));
                retList.add(c);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return retList;
    }

    //混合查询
    public List<Course> searchByCustomize(String sno, String sname, String age, String sex, String dept){
        String snoReplace="%",snameReplace="%",sexReplace="%",deptReplace="%";
        int ageReplace;
        //当文本框中的内容为空时，返回""
        //如何在java中实现字符串的匹配？？？？如果使用like就只能用通配符？？？？？
        if(!Objects.equals(sno, "")) snoReplace=sno;
        if(!Objects.equals(sname, "")) snameReplace=sname;
        if(!Objects.equals(sex, "")) sexReplace=sex;
        if(!Objects.equals(dept, "")) deptReplace=dept;
        List<Course> retList = new ArrayList<Course>();

        try {
            Statement statement = con.createStatement();
            String sql="";
            //使用equals, 而不是==
            //如果age为空
            if(Objects.equals(age, "")){
                //like字符串匹配查询，如果不想使用模糊查询，只是用确定查询，只能使用以下方式
                sql="select * from course where cno like '"+snoReplace+"' and cname like '"+snameReplace+"' and studyTime like '"+sexReplace+"' and credit like'"+deptReplace+"'";
            }
            else{
                ageReplace=Integer.parseInt(age);
                sql="select * from tch where cno like '"+snoReplace+"' and cname like '"+snameReplace+"' and stuCnts='"+ageReplace+"' and studyTime like '"+sexReplace+"' and credit like'"+deptReplace+"'";
            }


            ResultSet rs = statement.executeQuery(sql);
            while(rs.next()){
                Course c=new Course();
                c.setCno(rs.getString("cno"));
                c.setCname(rs.getString("cname"));
                c.setStuCnts(rs.getInt("stuCnts"));
                c.setStudyTime(rs.getString("studyTime"));
                c.setCredit(rs.getString("credit"));
                retList.add(c);
            }
        } catch (SQLException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }

        return retList;
    }

    //返回所有元组
    public List<Course> getAllList(){
        List<Course> retList = new ArrayList<Course>();
        try{
            Statement sql;
            ResultSet rs;
            sql = con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE, ResultSet.CONCUR_READ_ONLY);
            //查询结果是可迭代的二维字符串
            rs = sql.executeQuery("select * from course");
            while(rs.next()){
                Course c=new Course();
                c.setCno(rs.getString("cno"));
                c.setCname(rs.getString("cname"));
                c.setStuCnts(rs.getInt("stuCnts"));
                c.setStudyTime(rs.getString("studyTime"));
                c.setCredit(rs.getString("credit"));
                retList.add(c);
            }
        }
        catch(SQLException e){
            System.out.println("请输入正确的表名"+e);
        }

        return retList;
    }



}


