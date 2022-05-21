package com.company.view;

import javax.swing.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;

public class Menu{
    public static void main(String[] args) {

        new MenuFrame();
    }
}

class MenuFrame extends JFrame implements ActionListener {
    JButton stu,tch,course,sc,tc,quite;

    MenuFrame(){
        stu=new JButton("Student Information");
        stu.addActionListener(this);
        //设置居中显示
        stu.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        tch =new JButton("Teacher Information");
        tch.addActionListener(this);
        tch.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        course=new JButton("Course Information");
        course.addActionListener(this);
        course.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        sc=new JButton("Student Course");
        sc.addActionListener(this);
        sc.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        tc=new JButton("Teacher Course");
        tc.addActionListener(this);
        tc.setAlignmentX(JComponent.CENTER_ALIGNMENT);

        quite=new JButton("quite");
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
            new StuInfoFrame();
        }
        else if(e.getSource()==tch){
            this.dispose();
            new TchInfoFrame();
        }
        else if(e.getSource()==course){
            this.dispose();
            new CourseInfoFrame();
        }
        else if(e.getSource()==sc){
            this.dispose();
            new ScInfoFrame();
        }
        else if(e.getSource()==tc){
            this.dispose();
            new TcInfoFrame();
        }
        else if(e.getSource()==quite){
            this.dispose();
            new loginFrame();
        }

    }
}
