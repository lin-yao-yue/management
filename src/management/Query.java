package management;
import java.sql.*;

/*查找类*/

public class Query {
    Object a[][]=null;
    String b[]=null;
    String tableName="";
    //列数
    int columns;
    //行数
    int rows;


    public Query(){
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver");
        }
        catch(ClassNotFoundException e){
            System.out.println(e);
        }
    }


    //获得整个表的数据
    public Object[][] getRecord(){//求表格的内容
        a=null;
        b=null;
        Connection con;
        Statement sql;
        ResultSet rs;
        try{
            String url,userName,userPwd;
            url="jdbc:sqlserver://localhost:1433;DatabaseName=management";
            userName="sa";
            userPwd="sa";
            con=DriverManager.getConnection(url,userName,userPwd);
            //获得的列数
            columnCnt();
            //获得行数
            getRowCnt();
            a=new Object[rows][columns];
            sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            //查询结果是可迭代的二维字符串
            rs=sql.executeQuery("select * from "+tableName);
            int m=0;
            while(rs.next()){
                for(int k=1;k<=columns;k++){
                    a[m][k-1]=rs.getString(k);
                }
                m++;
            }
            con.close();
        }
        catch(SQLException e){
            System.out.println("请输入正确的表名"+e);
        }
        return a;
    }

    //条件查询，

    //该表行数
    public void getRowCnt(){//求表内容有多少行
        Connection con;
        Statement sql;
        ResultSet rs;
        try{
            String url,userName,userPwd;
            url="jdbc:sqlserver://localhost:1433;DatabaseName=management";
            userName="sa";
            userPwd="sa";
            con=DriverManager.getConnection(url,userName,userPwd);
            //创建sql语言
            sql=con.createStatement(ResultSet.TYPE_SCROLL_SENSITIVE,ResultSet.CONCUR_READ_ONLY);
            rs=sql.executeQuery("select * from "+tableName);
            //获取最后一条数据的信息
            rs.last();
            //最后一条数据的行数便是总行数
            rows=rs.getRow();

        }
        catch(SQLException exp){
            System.out.println(""+exp);
        }
    }

    //该表每一列的名称
    public String[] getField(){
        Connection con;
        try{
            String url,userName,userPwd;
            url="jdbc:sqlserver://localhost:1433;DatabaseName=management";
            userName="sa";
            userPwd="sa";
            con=DriverManager.getConnection(url,userName,userPwd);
            DatabaseMetaData metadata=con.getMetaData();
            //获取该表的列信息
            ResultSet rs1=metadata.getColumns(null, null, tableName, null);
            //获得列数，防止对表修改后没有及时更新列数
            columnCnt();
            b=new String[columns];
            int k=0;
            while(rs1.next()){
                //列信息中 4 存储列名
                b[k]=rs1.getString(4);
                k++;
            }
            con.close();
        }
        catch(SQLException e){
            System.out.println(e);
        }
        return b;
    }

    public void setTableName(String s){//初试表名
        tableName=s.trim();
    }

    //求该表的列数
    public void columnCnt(){
        Connection con;
        PreparedStatement sql;
        ResultSet rs;
        try{
            String url,userName,userPwd;
            url="jdbc:sqlserver://localhost:1433;DatabaseName=management";
            userName="sa";
            userPwd="sa";
            con=DriverManager.getConnection(url,userName,userPwd);
            //该实例的方法可以获取数据库的信息
            DatabaseMetaData metadata=con.getMetaData();
            //获得某个表的列信息
            ResultSet rs1=metadata.getColumns(null, null, tableName, null);
            columns=0;
            while(rs1.next())
                columns++;
        }
        catch(SQLException e){
            System.out.println(e);
        }
    }
}
