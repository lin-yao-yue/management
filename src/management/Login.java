package management;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.*;

public class Login {

    public static void main(String[] args) {

        new loginFrame();
    }
}

//继承 JFrame 编写 login 的 frame 框架
class loginFrame extends JFrame implements ActionListener{
    //box 用于容纳其他组件，有横向和纵向两种
    Box box1,box2,baseBox;
    //标签，以特定对齐方式显示信息
    JLabel userName,userPwd;
    //文本框，可指定列数，字体相关信息
    JTextField nameField;
    //密码框
    JPasswordField pwdField;
    //登陆,注册
    JButton button1,button2;
    //选项卡面板，可包含多个选项卡，每个选项卡可包含组件
    JTabbedPane choose;
    //userid,pwd,button,GridBagLayout
    JPanel panel1,panel2,panel3,base;

    loginFrame(){
        //初始化
        setBackground(Color.white);
        userName=new JLabel("name",JLabel.CENTER);
        userPwd=new JLabel("pwd",JLabel.CENTER);
        nameField=new JTextField(10);
        pwdField=new JPasswordField(10);
        button1=new JButton("long in");
        button2=new JButton("sign up");
        //id
        panel1=new JPanel();
        panel1.add(userName);
        panel1.add(nameField);
        //pwd
        panel2=new JPanel();
        panel2.add(userPwd);
        panel2.add(pwdField);
        //button
        panel3=new JPanel();
        panel3.add(button1);
        panel3.add(button2);
        //对以上panel布局
        base=new JPanel();
        //网格布局
        base.setLayout( new GridLayout(3, 1) );
        base.add(panel1);
        base.add(panel2);
        base.add(panel3);
        //对frame整体布局
        setLayout( new GridBagLayout() );
        add( new JPanel(), new GridBagConstraints(0,0,1,1,0.5,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0),
                0,0));
        add( new JPanel(), new GridBagConstraints(0,1,1,1,0.5,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0),
                0,0));
        add( base , new GridBagConstraints(1,1,1,1,0,0,
                GridBagConstraints.CENTER,
                GridBagConstraints.NONE,
                new Insets(0, 0, 0, 0),
                0,0));
        add( new JPanel(), new GridBagConstraints(2,1,1,1,0.5,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0),
                0,0));
        add( new JPanel(), new GridBagConstraints(0,2,1,1,0.5,0.5,
                GridBagConstraints.CENTER,
                GridBagConstraints.BOTH,
                new Insets(0, 0, 0, 0),
                0,0));

        //触发事件
        button1.addActionListener(this);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);
        //窗口位置和宽高
        setBounds(650,300,600,375);
        setTitle("管理系统");
        //校验参数
        validate();
    }


    public void actionPerformed(ActionEvent e){
        String name,pwd;
        name=nameField.getText();
        pwd=pwdField.getText();
        try{
            Class.forName("com.microsoft.sqlserver.jdbc.SQLServerDriver"); //加载数据库驱动

        }
        catch(ClassNotFoundException ex){
            System.out.println(ex);
        }
        try{
            Connection con;
            Statement sql;
            ResultSet rs;
            String url,userName,userPwd;
            // 连接数据库的语句
            url="jdbc:sqlserver://localhost:1433;DatabaseName=management";

            userName="sa";
            userPwd="sa";
            con=DriverManager.getConnection(url,userName,userPwd);
            sql=con.createStatement();
            rs=sql.executeQuery("select * from login where name ='"+name+"' and pwd='"+pwd+"'");//对应自己数据库建的表填写
            int q=0;
            while(rs.next()){
                q++;
            }
            if(q>0){
                JOptionPane.showMessageDialog(this, "登陆成功！","消息对话框",JOptionPane.WARNING_MESSAGE);
                this.dispose();
                new Menu();

            }
            else
                JOptionPane.showMessageDialog(this, "账号或者密码错误!","消息对话框",JOptionPane.WARNING_MESSAGE);
        }
        catch(SQLException exp){
            System.out.println(exp);
        }

    }
}