package management;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu{
    public static void main(String[] args) {

        new ChoicesFrame();
    }
}

class ChoicesFrame extends JFrame implements ActionListener {
    JButton stu,tch,course,sc,tc,quite;

    ChoicesFrame(){
        stu=new JButton("学生信息");
        stu.addActionListener(this);
        //设置居中显示
        stu.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        tch =new JButton("老师信息");
        tch.addActionListener(this);
        tch.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        course=new JButton("课程信息");
        course.addActionListener(this);
        course.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        sc=new JButton("学生选课情况");
        sc.addActionListener(this);
        sc.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        tc=new JButton("老师授课情况");
        tc.addActionListener(this);
        tc.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        quite=new JButton("退出登录");
        quite.addActionListener(this);
        quite.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        Box b = new Box(BoxLayout.Y_AXIS);
        b.add(Box.createVerticalGlue());
        b.add(stu);
        b.add(Box.createVerticalGlue());
        b.add(tch);
        b.add(Box.createVerticalGlue());
        b.add(course);
        b.add(Box.createVerticalGlue());
        b.add(sc);
        b.add(Box.createVerticalGlue());
        b.add(tc);
        b.add(Box.createVerticalGlue());
        b.add(quite);
        b.add(Box.createVerticalGlue());

        add(b);
        setLocationRelativeTo(null);
        setBounds(700,250,450,550);
        setTitle("welcome");
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setVisible(true);

    }


    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource()==stu){
            this.dispose();
            new loginFrame();
        }
        else if(e.getSource()==tch){

        }
        else if(e.getSource()==course){

        }
        else if(e.getSource()==sc){

        }
        else if(e.getSource()==tc){

        }
        else if(e.getSource()==quite){
            //this.dispose();
            new loginFrame();
        }

    }
}
