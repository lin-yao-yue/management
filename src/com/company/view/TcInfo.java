package com.company.view;

import com.company.dao.ScDao;
import com.company.dao.StuDao;
import com.company.dao.TcDao;
import com.company.dao.TchDao;
import com.company.schedules.Sc;
import com.company.schedules.Stu;
import com.company.schedules.Tc;
import com.company.schedules.Tch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class TcInfo {
    public static void main(String[] args) {

        new TcInfoFrame();
    }
}

class TcInfoFrame extends JFrame implements ActionListener{
    /*add-----------------------------------------------------------------*/
    JMenu add;
    JMenuItem addI;
    JPanel addP;
    //显示添加信息是否成功
    JPanel addMessagePanel;
    //文本框
    JTextField addSnoT;
    JTextField addSnameT;
    JTextField addAgeT;
    //按钮
    JButton addB;

    /*delete---------------------------------------------------------------*/
    JMenu delete;
    JMenuItem deleteI;
    JPanel deleteP;
    //显示删除信息是否成功
    JPanel deleteMessagePanel;
    //文本框
    JTextField deleteSnoT;
    JTextField deleteSnameT;
    JTextField deleteAgeT;
    //按钮
    JButton deleteB;

    /*update---------------------------------------------------------------*/
    JMenu update;
    JMenuItem updateI;
    JPanel updateP;
    //显示删除信息是否成功
    JPanel updateMessagePanel;
    //文本框
    JTextField updateSnoT;
    JTextField updateSnameT;
    JTextField updateAgeT;
    //按钮
    JButton updateB;

    /*search子菜单---------------------------------------------------------*/
    //show all
    JMenuItem showAll;
    //自定义混合查询
    JMenuItem searchByCustomizeI;

    /*customize混合search的panel*/
    JPanel searchByCustomizeP;
    //滚动显示表格的panel
    JPanel searchByCustomizeTablePanel;
    //文本框
    JTextField searchByCustomizeSnoT;
    JTextField searchByCustomizeSnameT;
    JTextField searchByCustomizeAgeT;
    //按钮
    JButton searchByCustomizeB;

    /*quite 子菜单---------------------------------------------------------*/
    JMenuItem quiteYes;

    /*主 panel*/
    JPanel contentPanel;

    TcInfoFrame(){

        setTitle("TC Information");
        setSize(500, 600);
        //frame 和 frame 中的组件居中显示
        setLocationRelativeTo(null);
        setDefaultCloseOperation(WindowConstants.EXIT_ON_CLOSE);

        //菜单栏
        JMenuBar menuBar = new JMenuBar();
        //一级菜单
        add = new JMenu("add");
        delete = new JMenu("delete");
        update = new JMenu("update");
        JMenu search = new JMenu("search");
        JMenu quite =new JMenu("quite");
        // 一级菜单添加到菜单栏
        menuBar.add(add);
        menuBar.add(delete);
        menuBar.add(update);
        menuBar.add(search);
        menuBar.add(quite);

        /*add--------------------------------------------------------------*/
        addI=new JMenuItem("yes");
        addI.addActionListener(this);
        add.add(addI);

        /*delete-----------------------------------------------------------*/
        deleteI=new JMenuItem("yes");
        deleteI.addActionListener(this);
        delete.add(deleteI);

        /*update------------------------------------------------------------*/
        updateI = new JMenuItem("yes");
        updateI.addActionListener(this);
        update.add(updateI);

        /*search 的子菜单---------------------------------------------------*/
        showAll=new JMenuItem("show all");
        showAll.addActionListener(this);
        searchByCustomizeI=new JMenuItem("customize");
        searchByCustomizeI.addActionListener(this);
        //子菜单添加到一级菜单
        search.add(showAll);
        search.add(searchByCustomizeI);

        /*quite 的子菜单----------------------------------------------------*/
        quiteYes = new JMenuItem("yes");
        quiteYes.addActionListener(this);
        quite.add(quiteYes);


        /*主panel，显示所有学生信息------------------------------------------*/
        contentPanel=new JPanel();
        showAllStuInfo();
        setContentPane(contentPanel);


        //把菜单栏设置到窗口
        setJMenuBar(menuBar);
        setVisible(true);

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        //add
        if(e.getSource()==addI){
            /*customize panel 的初始化*/
            //考虑到页面的切换会保留上一次残留的组件信息，所以每次都进行初始化

            addSnoT=new JTextField(10);
            addSnameT=new JTextField(10);
            addAgeT=new JTextField(10);
            addB=new JButton("submit");
            Dimension preferredSize = new Dimension(175,17);
            addB.setPreferredSize(preferredSize);//设置按钮大小

            addP=allComponents(addSnoT, addSnameT, addAgeT ,addB);

            //添加结果信息的panel
            addMessagePanel=new JPanel();
            addMessagePanel.setPreferredSize(new Dimension(300, 300));

            //显示文本框和提交按钮
            //searchByCustomizeP的作用是覆盖整个frame，使得新增的panel按照顺序将排列在上方
            //直接放到frame上的组件，可以伸展的将覆盖整个空间，不能伸展的将居中显示
            setContentPane(addP);
            //重新布置组件
            revalidate();

            addB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent be){
                    //得到文本信息，查询，返回数据
                    String sno=addSnoT.getText();
                    String sname=addSnameT.getText();
                    String age=addAgeT.getText();

                    Tc tc=new Tc();
                    if(!Objects.equals(sno, "")) tc.setTno(sno);
                    if(!Objects.equals(sname, "")) tc.setCno(sname);
                    if(!Objects.equals(age, ""))tc.setClassroom(age);

                    TcDao tcDao =new TcDao();
                    boolean isDone = false;
                    try {
                        isDone = tcDao.add(tc);
                        JLabel addMessageL=new JLabel("successful", JLabel.CENTER);
                        //先清除再添加
                        addP.remove(addMessagePanel);
                        addMessagePanel.removeAll();
                        addMessagePanel.add(addMessageL);
                        addP.add(addMessagePanel);
                        //重新绘制根据名字进行查找的面板
                        setContentPane(addP);
                        //重新布置组件
                        revalidate();

                    } catch (SQLException ex) {
                        JLabel addMessageL1=new JLabel("unSuccessful");
                        String wrongMessage=ex.toString();
                        JLabel addMessageL2=new JLabel(wrongMessage);
                        JPanel p1=new JPanel();
                        p1.setPreferredSize(new Dimension(300, 30));
                        JPanel p2=new JPanel();
                        p2.setPreferredSize(new Dimension(300, 30));
                        p1.add(addMessageL1);
                        p2.add(addMessageL2);
                        //先清除再添加
                        addP.remove(addMessagePanel);
                        addMessagePanel.removeAll();
                        addMessagePanel.add(p1);
                        addMessagePanel.add(p2);
                        addP.add(addMessagePanel);
                        //重新绘制根据名字进行查找的面板
                        setContentPane(addP);
                        //重新布置组件
                        revalidate();
                        ex.printStackTrace();
                    }
                }
            });

        }

        //delete
        if(e.getSource()==deleteI){
            deleteSnoT=new JTextField(10);
            deleteSnameT=new JTextField(11);
            deleteAgeT=new JTextField(10);
            deleteB=new JButton("submit");
            Dimension preferredSize = new Dimension(175,17);
            deleteB.setPreferredSize(preferredSize);//设置按钮大小

            deleteP=allComponents(deleteSnoT, deleteSnameT,deleteAgeT,deleteB);

            //删除结果信息的panel
            deleteMessagePanel=new JPanel();
            deleteMessagePanel.setPreferredSize(new Dimension(300, 300));

            //显示文本框和提交按钮
            //searchByCustomizeP的作用是覆盖整个frame，使得新增的panel按照顺序将排列在上方
            //直接放到frame上的组件，可以伸展的将覆盖整个空间，不能伸展的将居中显示
            setContentPane(deleteP);
            //重新布置组件
            revalidate();

            deleteB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent be){
                    //得到文本信息，查询，返回数据
                    String sno=deleteSnoT.getText();
                    String sname=deleteSnameT.getText();
                    String age=deleteAgeT.getText();

                    Tc tc=new Tc();
                    if(!Objects.equals(sno, "")) tc.setTno(sno);
                    if(!Objects.equals(sname, "")) tc.setCno(sname);
                    if(!Objects.equals(age, ""))tc.setClassroom(age);

                    TcDao tcDao =new TcDao();
                    int lines = 0;
                    try {
                        lines = tcDao.delete(sno, sname, age);
                        JLabel deleteMessageL=new JLabel(lines+""+" lines has been deleted", JLabel.CENTER);
                        //先清除再添加
                        deleteP.remove(deleteMessagePanel);
                        deleteMessagePanel.removeAll();
                        deleteMessagePanel.add(deleteMessageL);
                        deleteP.add(deleteMessagePanel);
                        //重新绘制根据名字进行查找的面板
                        setContentPane(deleteP);
                        //重新布置组件
                        revalidate();
                    } catch (SQLException ex) {
                        JLabel deleteMessageL1=new JLabel("unSuccessful");
                        //出错信息
                        String wrongMessage = ex.toString();
                        JLabel deleteMessageL2=new JLabel(wrongMessage);
                        JPanel p1=new JPanel();
                        p1.setPreferredSize(new Dimension(300, 30));
                        JPanel p2=new JPanel();
                        p2.setPreferredSize(new Dimension(300, 30));
                        p1.add(deleteMessageL1);
                        p2.add(deleteMessageL2);
                        //先清除再添加
                        deleteP.remove(deleteMessagePanel);
                        deleteMessagePanel.removeAll();
                        deleteMessagePanel.add(p1);
                        deleteMessagePanel.add(p2);
                        deleteP.add(deleteMessagePanel);
                        //重新绘制根据名字进行查找的面板
                        setContentPane(deleteP);
                        //重新布置组件
                        revalidate();
                        ex.printStackTrace();
                    }
                }
            });
        }

        //update
        if(e.getSource()==updateI){
            updateSnoT=new JTextField(10);
            updateSnameT=new JTextField(10);
            updateAgeT=new JTextField(10);
            updateB=new JButton("submit");
            Dimension preferredSize = new Dimension(175,17);
            updateB.setPreferredSize(preferredSize);//设置按钮大小

            updateP=allComponents(updateSnoT, updateSnameT,updateAgeT,updateB);


            //删除结果信息的panel
            updateMessagePanel=new JPanel();
            updateMessagePanel.setPreferredSize(new Dimension(300, 300));

            //显示文本框和提交按钮
            //searchByCustomizeP的作用是覆盖整个frame，使得新增的panel按照顺序将排列在上方
            //直接放到frame上的组件，可以伸展的将覆盖整个空间，不能伸展的将居中显示
            setContentPane(updateP);
            //重新布置组件
            revalidate();

            updateB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent be){
                    //得到文本信息，查询，返回数据
                    String sno=updateSnoT.getText();
                    String sname=updateSnameT.getText();
                    String age=updateAgeT.getText();

                    Tc tc=new Tc();
                    if(!Objects.equals(sno, "")) tc.setTno(sno);
                    if(!Objects.equals(sname, "")) tc.setCno(sname);
                    if(!Objects.equals(age, ""))tc.setClassroom(age);

                    TcDao tcDao =new TcDao();
                    int lines = 0;
                    try {
                        lines = tcDao.update(tc);
                        JLabel updateMessageL=new JLabel(lines+""+" lines has been updated", JLabel.CENTER);
                        //先清除再添加
                        updateP.remove(updateMessagePanel);
                        updateMessagePanel.removeAll();
                        updateMessagePanel.add(updateMessageL);
                        updateP.add(updateMessagePanel);
                        //重新绘制根据名字进行查找的面板
                        setContentPane(updateP);
                        //重新布置组件
                        revalidate();
                    } catch (SQLException ex) {
                        JLabel deleteMessageL1=new JLabel("unSuccessful");
                        //出错信息
                        String wrongMessage = ex.toString();
                        JLabel deleteMessageL2=new JLabel(wrongMessage);
                        JPanel p1=new JPanel();
                        p1.setPreferredSize(new Dimension(300, 30));
                        JPanel p2=new JPanel();
                        p2.setPreferredSize(new Dimension(300, 30));
                        p1.add(deleteMessageL1);
                        p2.add(deleteMessageL2);
                        //先清除再添加
                        updateP.remove(updateMessagePanel);
                        updateMessagePanel.removeAll();
                        updateMessagePanel.add(p1);
                        updateMessagePanel.add(p2);
                        updateP.add(updateMessagePanel);
                        //重新绘制根据名字进行查找的面板
                        setContentPane(updateP);
                        //重新布置组件
                        revalidate();
                        ex.printStackTrace();
                    }
                }
            });
        }

        //show all
        if(e.getSource()==showAll){
            contentPanel.removeAll();
            //重新绘制contentPanel
            showAllStuInfo();
            //添加到frame
            setContentPane(contentPanel);
            //重新布置组件
            revalidate();
        }

        //自定义混合查询
        if(e.getSource()==searchByCustomizeI){
            /*customize panel 的初始化*/
            //考虑到页面的切换会保留上一次残留的组件信息，所以每次都进行初始化

            //先new对象后传参，函数中的对象是不会保存下来的，返回的是空指针
            searchByCustomizeSnoT=new JTextField(10);
            searchByCustomizeSnameT=new JTextField(11);
            searchByCustomizeAgeT=new JTextField(10);
            searchByCustomizeB=new JButton("submit");
            Dimension preferredSize = new Dimension(175,17);
            searchByCustomizeB.setPreferredSize(preferredSize);//设置按钮大小
            //构建 searchByCustomizeP
            searchByCustomizeP=allComponents(searchByCustomizeSnoT,searchByCustomizeSnameT,searchByCustomizeAgeT,searchByCustomizeB);

            //滚动显示表格的panel
            searchByCustomizeTablePanel=new JPanel();

            //显示文本框和提交按钮
            //searchByCustomizeP的作用是覆盖整个frame，使得新增的panel按照顺序将排列在上方
            //直接放到frame上的组件，可以伸展的将覆盖整个空间，不能伸展的将居中显示
            setContentPane(searchByCustomizeP);
            //重新布置组件
            revalidate();

            /*信息提交与按钮相应*/
            searchByCustomizeB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent be) {
                    //得到文本信息，查询，返回数据
                    String sno=searchByCustomizeSnoT.getText();
                    String sname=searchByCustomizeSnameT.getText();
                    String age=searchByCustomizeAgeT.getText();

                    TcDao tcDao=new TcDao();
                    List<Tc> tc=tcDao.searchByCustomize(sno,sname,age);

                    /*绘制数据*/
                    //表格数据

                    Object[][] data=new Object[tc.size()][5];
                    for(int i=0;i<tc.size();++i){
                        data[i][0]=tc.get(i).getTno();
                        data[i][1]=tc.get(i).getCno();
                        data[i][2]=tc.get(i).getClassroom();
                    }
                    // 表头（列名）
                    String[] columnNames = {"tno", "cno", "classroom"};
                    // 创建一个表格，指定 表头 和 所有行数据
                    JTable table = new JTable(data, columnNames);
                    // 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
                    table.setPreferredScrollableViewportSize(new Dimension(400, 360));
                    // 把 表格 放到 滚动面板 中（表头将自动添加到滚动面板顶部）
                    JScrollPane scrollPane = new JScrollPane(table);
                    //先清空之前绘制的table
                    searchByCustomizeP.remove(searchByCustomizeTablePanel);
                    searchByCustomizeTablePanel.removeAll();
                    // 添加 滚动面板 到 内容面板
                    searchByCustomizeTablePanel.add(scrollPane);
                    searchByCustomizeP.add(searchByCustomizeTablePanel);
                    //重新绘制根据名字进行查找的面板
                    setContentPane(searchByCustomizeP);
                    //重新布置组件
                    revalidate();
                }

            });
        }

        //quite
        if(e.getSource()==quiteYes){
            this.dispose();
            new MenuFrame();
        }
    }

    //返回的是大panel套小panel，小panel套GridBagLayout
    public JPanel allComponents(JTextField snoT, JTextField snameT, JTextField ageT, JButton addB){
        JPanel mainP=new JPanel();

        //p1
        JPanel p1=new JPanel();
        JLabel snoL=new JLabel("tno:");
        //snoT=new JTextField(10);
        p1.add(snoL);
        p1.add(snoT);
        //p2
        JPanel p2=new JPanel();
        JLabel snameL=new JLabel("cno:");
        //snameT=new JTextField(11);
        p2.add(snameL);
        p2.add(snameT);
        //p3
        JPanel p3=new JPanel();
        JLabel ageL=new JLabel("classroom:");
        //ageT=new JTextField(10);
        p3.add(ageL);
        p3.add(ageT);
        //p4
        JPanel p6=new JPanel();
        //addB=new JButton("submit");
        //Dimension preferredSize = new Dimension(175,17);
        //addB.setPreferredSize(preferredSize);//设置按钮大小
        p6.add(addB);

        // 使用网格袋布局
        GridBagLayout gridBag = new GridBagLayout();    // 布局管理器
        GridBagConstraints c = new GridBagConstraints();                    // 约束
        JPanel gridBagPanel = new JPanel(gridBag);

        /* 添加 组件 和 约束 到 布局管理器 */
        c.gridx=0;
        c.gridy=0;
        c.gridwidth=2;
        c.gridheight=1;
        gridBag.setConstraints(p1,c);
        c.gridx=2;
        c.gridy=0;
        c.gridwidth=2;
        c.gridheight=1;
        gridBag.setConstraints(p2,c);
        c.gridx=0;
        c.gridy=1;
        c.gridwidth=2;
        c.gridheight=1;
        gridBag.setConstraints(p3,c);
        c.gridx=2;
        c.gridy=1;
        c.gridwidth=2;
        c.gridheight=1;
        gridBag.setConstraints(p6,c);

        /* 根据 gridBag 规划后的 组件 添加到 内容面板 */
        gridBagPanel.add(p1);
        gridBagPanel.add(p2);
        gridBagPanel.add(p3);
        gridBagPanel.add(p6);

        mainP.add(gridBagPanel);

        return mainP;

    }

    public void showAllStuInfo(){
        TcDao tcDao=new TcDao();
        List<Tc> tc=tcDao.getAllList();

        /*绘制数据*/
        //表格数据

        Object[][] data=new Object[tc.size()][5];
        for(int i=0;i<tc.size();++i){
            data[i][0]=tc.get(i).getTno();
            data[i][1]=tc.get(i).getCno();
            data[i][2]=tc.get(i).getClassroom();
        }

        // 表头（列名）
        String[] columnNames = {"tno", "cno", "classroom"};
        // 创建一个表格，指定 表头 和 所有行数据
        JTable table = new JTable(data, columnNames);
        // 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
        table.setPreferredScrollableViewportSize(new Dimension(400, 400));
        // 把 表格 放到 滚动面板 中（表头将自动添加到滚动面板顶部）
        JScrollPane scrollPane = new JScrollPane(table);
        //放置到主面板
        JLabel l=new JLabel("these are all tc Information");
        JPanel p=new JPanel();
        //为panel设置边框
        p.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        p.add(l);
        contentPanel.add(p);
        contentPanel.add(scrollPane);
    }
}



