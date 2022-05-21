package com.company.view;

import com.company.dao.StuDao;
import com.company.dao.TchDao;
import com.company.schedules.Stu;
import com.company.schedules.Tch;

import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.sql.SQLException;
import java.util.List;
import java.util.Objects;

public class TchInfo {
    public static void main(String[] args) {

        new TchInfoFrame();
    }
}

class TchInfoFrame extends JFrame implements ActionListener{
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
    JTextField addSexT;
    JTextField addDeptT;
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
    JTextField deleteSexT;
    JTextField deleteDeptT;
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
    JTextField updateSexT;
    JTextField updateDeptT;
    //按钮
    JButton updateB;

    /*search子菜单---------------------------------------------------------*/
    //show all
    JMenuItem showAll;
    //根据sname进行查找的item选项
    JMenuItem searchBySnameI;
    //根据sno进行查找的item选项
    JMenuItem searchBySnoI;
    //自定义混合查询
    JMenuItem searchByCustomizeI;

    /*根据Sname进行search的 Panel*/
    JPanel searchBySnameP;
    //滚动显示表格的panel
    JPanel searchBySnameTablePanel;
    JTextField searchBySnameT;
    JButton searchBySnameB;

    /*根据sno进行search的panel*/
    JPanel searchBySnoP;
    //滚动显示表格的panel
    JPanel searchBySnoTablePanel;
    JTextField searchBySnoT;
    JButton searchBySnoB;

    /*customize混合search的panel*/
    JPanel searchByCustomizeP;
    //滚动显示表格的panel
    JPanel searchByCustomizeTablePanel;
    //文本框
    JTextField searchByCustomizeSnoT;
    JTextField searchByCustomizeSnameT;
    JTextField searchByCustomizeAgeT;
    JTextField searchByCustomizeSexT;
    JTextField searchByCustomizeDeptT;
    //按钮
    JButton searchByCustomizeB;

    /*quite 子菜单---------------------------------------------------------*/
    JMenuItem quiteYes;

    /*主 panel*/
    JPanel contentPanel;

    TchInfoFrame(){

        setTitle("Teacher Information");
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
        searchBySnoI = new JMenuItem("by no");
        searchBySnoI.addActionListener(this);
        searchBySnameI = new JMenuItem("by name");
        searchBySnameI.addActionListener(this);
        searchByCustomizeI=new JMenuItem("customize");
        searchByCustomizeI.addActionListener(this);
        //子菜单添加到一级菜单
        search.add(showAll);
        search.add(searchBySnoI);
        search.add(searchBySnameI);
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
            addSnameT=new JTextField(11);
            addAgeT=new JTextField(10);
            addSexT=new JTextField(10);
            addDeptT=new JTextField(10);
            addB=new JButton("submit");
            Dimension preferredSize = new Dimension(175,17);
            addB.setPreferredSize(preferredSize);//设置按钮大小

            addP=allComponents(addSnoT, addSnameT,addAgeT,addSexT,addDeptT,addB);

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
                    String sex=addSexT.getText();
                    String dept= addDeptT.getText();

                    Tch tch=new Tch();
                    if(!Objects.equals(sno, "")) tch.setTno(sno);
                    if(!Objects.equals(sname, "")) tch.setTname(sname);
                    if(!Objects.equals(age, "")){
                        int ageReplace = Integer.parseInt(age);
                        tch.setTage(ageReplace);
                    }
                    if(!Objects.equals(sex, "")) tch.setSex(sex);
                    if(!Objects.equals(dept, "")) tch.setTdept(dept);

                    TchDao tchDao =new TchDao();
                    boolean isDone = false;
                    try {
                        isDone = tchDao.add(tch);
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
                        String wrongMessage=ex.toString().substring(48,80);
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
            deleteSexT=new JTextField(10);
            deleteDeptT=new JTextField(10);
            deleteB=new JButton("submit");
            Dimension preferredSize = new Dimension(175,17);
            deleteB.setPreferredSize(preferredSize);//设置按钮大小

            deleteP=allComponents(deleteSnoT, deleteSnameT,deleteAgeT,deleteSexT,deleteDeptT,deleteB);

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
                    String sex=deleteSexT.getText();
                    String dept= deleteDeptT.getText();

                    Tch tch=new Tch();
                    if(!Objects.equals(sno, "")) tch.setTno(sno);
                    if(!Objects.equals(sname, "")) tch.setTname(sname);
                    if(!Objects.equals(age, "")){
                        int ageReplace = Integer.parseInt(age);
                        tch.setTage(ageReplace);
                    }
                    if(!Objects.equals(sex, "")) tch.setSex(sex);
                    if(!Objects.equals(dept, "")) tch.setTdept(dept);

                    TchDao tchDao =new TchDao();
                    int lines = 0;
                    try {
                        lines = tchDao.delete(sno, sname, age, sex, dept);
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
                        String wrongMessage = ex.toString().substring(48,80);
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
            updateSnoT=new JTextField(28);
            updateSnameT=new JTextField(11);
            updateAgeT=new JTextField(10);
            updateSexT=new JTextField(10);
            updateDeptT=new JTextField(10);
            updateB=new JButton("submit");
            Dimension preferredSize = new Dimension(333,17);
            updateB.setPreferredSize(preferredSize);//设置按钮大小

            updateP=allComponentsUpdate(updateSnoT, updateSnameT,updateAgeT,updateSexT,updateDeptT,updateB);


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
                    String sex=updateSexT.getText();
                    String dept= updateDeptT.getText();

                    Tch tch=new Tch();
                    if(!Objects.equals(sno, "")) tch.setTno(sno);
                    if(!Objects.equals(sname, "")) tch.setTname(sname);
                    if(!Objects.equals(age, "")){
                        int ageReplace = Integer.parseInt(age);
                        tch.setTage(ageReplace);
                    }
                    if(!Objects.equals(sex, "")) tch.setSex(sex);
                    if(!Objects.equals(dept, "")) tch.setTdept(dept);

                    TchDao tchDao =new TchDao();
                    int lines = 0;
                    try {
                        lines = tchDao.update(tch);
                        JLabel updateMessageL=new JLabel("update successful", JLabel.CENTER);
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

        //根据 sname 查找
        if(e.getSource()==searchBySnameI){
            /*显示查找页面*/

            /*根据 sname 查找的 panel*/
            /*初始化*/
            searchBySnameP=new JPanel();
            //显示文本框与按钮的panel
            JPanel searchBySnameP1=new JPanel();
            JLabel searchBySnameL=new JLabel("name:");
            searchBySnameT=new JTextField(10);
            searchBySnameB=new JButton("submit");
            searchBySnameP1.add(searchBySnameL);
            searchBySnameP1.add(searchBySnameT);
            searchBySnameP1.add(searchBySnameB);
            searchBySnameP.add(searchBySnameP1);
            //滚动显示表格的panel
            searchBySnameTablePanel=new JPanel();

            //显示文本框和提交按钮
            setContentPane(searchBySnameP);
            //重新布置组件
            revalidate();

            /*信息提交与按钮相应*/
            searchBySnameB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent be) {
                    //得到文本信息，查询，返回数据
                    String sname=searchBySnameT.getText();
                    TchDao tchDao=new TchDao();
                    List<Tch> tchs = tchDao.searchByName(sname);

                    /*绘制数据*/
                    //表格数据
                    Object[][] data=new Object[tchs.size()][5];
                    for(int i=0;i<tchs.size();++i){
                        data[i][0]=tchs.get(i).getTno();
                        data[i][1]=tchs.get(i).getTname();
                        data[i][2]=tchs.get(i).getTage();
                        data[i][3]=tchs.get(i).getSex();
                        data[i][4]=tchs.get(i).getTdept();
                    }
                    // 表头（列名）
                    String[] columnNames = {"no", "name", "age", "sex", "dept"};
                    // 创建一个表格，指定 表头 和 所有行数据
                    JTable table = new JTable(data, columnNames);
                    // 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
                    table.setPreferredScrollableViewportSize(new Dimension(400, 400));
                    // 把 表格 放到 滚动面板 中（表头将自动添加到滚动面板顶部）
                    JScrollPane scrollPane = new JScrollPane(table);
                    //先清空之前绘制的table
                    searchBySnameP.remove(searchBySnameTablePanel);
                    searchBySnameTablePanel.removeAll();
                    // 添加 滚动面板 到 内容面板
                    searchBySnameTablePanel.add(scrollPane);
                    searchBySnameP.add(searchBySnameTablePanel);
                    //重新绘制根据名字进行查找的面板
                    setContentPane(searchBySnameP);
                    //重新布置组件
                    revalidate();

                }

            });

        }

        //根据 sno 查找
        if(e.getSource()==searchBySnoI){

            /*显示查找页面*/

            /*根据sno查找的 panel */

            /*sno panel 的初始化*/
            //考虑到页面的切换会保留上一次残留的组件信息，所以每次都进行初始化
            searchBySnoP=new JPanel();
            //显示文本框与按钮的panel
            JPanel searchBySnoP1=new JPanel();
            JLabel searchBySnoL=new JLabel("no:");
            searchBySnoT=new JTextField(10);
            searchBySnoB=new JButton("submit");
            searchBySnoP1.add(searchBySnoL);
            searchBySnoP1.add(searchBySnoT);
            searchBySnoP1.add(searchBySnoB);
            searchBySnoP.add(searchBySnoP1);
            //滚动显示表格的panel
            searchBySnoTablePanel=new JPanel();

            //显示文本框和提交按钮
            setContentPane(searchBySnoP);
            //重新布置组件
            revalidate();

            /*信息提交与按钮相应*/
            searchBySnoB.addActionListener(new ActionListener() {
                public void actionPerformed(ActionEvent be) {
                    //得到文本信息，查询，返回数据
                    String sno=searchBySnoT.getText();
                    TchDao tchDao=new TchDao();
                    List<Tch> tchs = tchDao.searchBySno(sno);

                    /*绘制数据*/
                    //表格数据
                    Object[][] data=new Object[tchs.size()][5];
                    for(int i=0;i<tchs.size();++i){
                        data[i][0]=tchs.get(i).getTno();
                        data[i][1]=tchs.get(i).getTname();
                        data[i][2]=tchs.get(i).getTage();
                        data[i][3]=tchs.get(i).getSex();
                        data[i][4]=tchs.get(i).getTdept();
                    }
                    // 表头（列名）
                    String[] columnNames = {"no", "name", "age", "sex", "dept"};
                    // 创建一个表格，指定 表头 和 所有行数据
                    JTable table = new JTable(data, columnNames);
                    // 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
                    table.setPreferredScrollableViewportSize(new Dimension(400, 400));
                    // 把 表格 放到 滚动面板 中（表头将自动添加到滚动面板顶部）
                    JScrollPane scrollPane = new JScrollPane(table);
                    //先清空之前绘制的table
                    searchBySnoP.remove(searchBySnoTablePanel);
                    searchBySnoTablePanel.removeAll();
                    // 添加 滚动面板 到 内容面板
                    searchBySnoTablePanel.add(scrollPane);
                    searchBySnoP.add(searchBySnoTablePanel);
                    //重新绘制根据名字进行查找的面板
                    setContentPane(searchBySnoP);
                    //重新布置组件
                    revalidate();

                }

            });

        }

        //自定义混合查询
        if(e.getSource()==searchByCustomizeI){
            /*customize panel 的初始化*/
            //考虑到页面的切换会保留上一次残留的组件信息，所以每次都进行初始化

            //先new对象后传参，函数中的对象是不会保存下来的，返回的是空指针
            searchByCustomizeSnoT=new JTextField(10);
            searchByCustomizeSnameT=new JTextField(11);
            searchByCustomizeAgeT=new JTextField(10);
            searchByCustomizeSexT=new JTextField(10);
            searchByCustomizeDeptT=new JTextField(10);
            searchByCustomizeB=new JButton("submit");
            Dimension preferredSize = new Dimension(175,17);
            searchByCustomizeB.setPreferredSize(preferredSize);//设置按钮大小
            //构建 searchByCustomizeP
            searchByCustomizeP=allComponents(searchByCustomizeSnoT,searchByCustomizeSnameT,searchByCustomizeAgeT,searchByCustomizeSexT,searchByCustomizeDeptT,searchByCustomizeB);

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
                    String sex=searchByCustomizeSexT.getText();
                    String dept= searchByCustomizeDeptT.getText();

                    TchDao tchDao=new TchDao();
                    List<Tch> tchs=tchDao.searchByCustomize(sno,sname,age,sex,dept);

                    /*绘制数据*/
                    //表格数据

                    Object[][] data=new Object[tchs.size()][5];
                    for(int i=0;i<tchs.size();++i){
                        data[i][0]=tchs.get(i).getTno();
                        data[i][1]=tchs.get(i).getTname();
                        data[i][2]=tchs.get(i).getTage();
                        data[i][3]=tchs.get(i).getSex();
                        data[i][4]=tchs.get(i).getTdept();
                    }
                    // 表头（列名）
                    String[] columnNames = {"no", "name", "age", "sex", "dept"};
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
    public JPanel allComponents(JTextField snoT, JTextField snameT, JTextField ageT, JTextField sexT, JTextField deptT, JButton addB){
        JPanel mainP=new JPanel();

        //p1
        JPanel p1=new JPanel();
        JLabel snoL=new JLabel("no:");
        //snoT=new JTextField(10);
        p1.add(snoL);
        p1.add(snoT);
        //p2
        JPanel p2=new JPanel();
        JLabel snameL=new JLabel("name:");
        //snameT=new JTextField(11);
        p2.add(snameL);
        p2.add(snameT);
        //p3
        JPanel p3=new JPanel();
        JLabel ageL=new JLabel("age:");
        //ageT=new JTextField(10);
        p3.add(ageL);
        p3.add(ageT);
        //p4
        JPanel p4=new JPanel();
        JLabel sexL=new JLabel("   sex(f/m):");
        //sexT=new JTextField(10);
        p4.add(sexL);
        p4.add(sexT);
        //p5
        JPanel p5=new JPanel();
        JLabel deptL=new JLabel("dept:");
        //deptT=new JTextField(10);
        p5.add(deptL);
        p5.add(deptT);
        //p6
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
        //gridBag 自动根据 gridx，gridy，gridwidth，gridheight，这几个属性来规划自身的网格布局
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
        gridBag.setConstraints(p4,c);
        c.gridx=0;
        c.gridy=2;
        c.gridwidth=2;
        c.gridheight=1;
        gridBag.setConstraints(p5,c);
        c.gridx=2;
        c.gridy=2;
        c.gridwidth=2;
        c.gridheight=1;
        gridBag.setConstraints(p6,c);

        /* 根据 gridBag 规划后的 组件 添加到 内容面板 */
        gridBagPanel.add(p1);
        gridBagPanel.add(p2);
        gridBagPanel.add(p3);
        gridBagPanel.add(p4);
        gridBagPanel.add(p5);
        gridBagPanel.add(p6);

        mainP.add(gridBagPanel);

        return mainP;

    }

    public JPanel allComponentsUpdate(JTextField snoT, JTextField snameT, JTextField ageT, JTextField sexT, JTextField deptT, JButton addB){
        JPanel mainP=new JPanel();

        //p1
        JPanel p1=new JPanel();
        JLabel snoL=new JLabel("no:");
        //snoT=new JTextField(10);
        p1.add(snoL);
        p1.add(snoT);
        //p2
        JPanel p2=new JPanel();
        JLabel snameL=new JLabel("name:");
        //snameT=new JTextField(11);
        p2.add(snameL);
        p2.add(snameT);
        //p3
        JPanel p3=new JPanel();
        JLabel ageL=new JLabel("age:");
        //ageT=new JTextField(10);
        p3.add(ageL);
        p3.add(ageT);
        //p4
        JPanel p4=new JPanel();
        JLabel sexL=new JLabel("   sex(f/m):");
        //sexT=new JTextField(10);
        p4.add(sexL);
        p4.add(sexT);
        //p5
        JPanel p5=new JPanel();
        JLabel deptL=new JLabel("dept:");
        //deptT=new JTextField(10);
        p5.add(deptL);
        p5.add(deptT);
        //p6
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
        //gridBag 自动根据 gridx，gridy，gridwidth，gridheight，这几个属性来规划自身的网格布局

        c.gridx=0;
        c.gridy=0;
        c.gridwidth=4;
        c.gridheight=1;
        gridBag.setConstraints(p1,c);

        c.gridx=0;
        c.gridy=1;
        c.gridwidth=2;
        c.gridheight=1;
        gridBag.setConstraints(p2,c);

        c.gridx=2;
        c.gridy=1;
        c.gridwidth=2;
        c.gridheight=1;
        gridBag.setConstraints(p3,c);

        c.gridx=0;
        c.gridy=2;
        c.gridwidth=2;
        c.gridheight=1;
        gridBag.setConstraints(p4,c);

        c.gridx=2;
        c.gridy=2;
        c.gridwidth=2;
        c.gridheight=1;
        gridBag.setConstraints(p5,c);

        c.gridx=0;
        c.gridy=3;
        c.gridwidth=4;
        c.gridheight=1;
        gridBag.setConstraints(p6,c);

        /* 根据 gridBag 规划后的 组件 添加到 内容面板 */
        gridBagPanel.add(p1);
        gridBagPanel.add(p2);
        gridBagPanel.add(p3);
        gridBagPanel.add(p4);
        gridBagPanel.add(p5);
        gridBagPanel.add(p6);

        mainP.add(gridBagPanel);

        return mainP;
    }

    public void showAllStuInfo(){
        TchDao tchDao=new TchDao();
        List<Tch> tchs=tchDao.getAllList();

        /*绘制数据*/
        //表格数据

        Object[][] data=new Object[tchs.size()][5];
        for(int i=0;i<tchs.size();++i){
            data[i][0]=tchs.get(i).getTno();
            data[i][1]=tchs.get(i).getTname();
            data[i][2]=tchs.get(i).getTage();
            data[i][3]=tchs.get(i).getSex();
            data[i][4]=tchs.get(i).getTdept();
        }

        // 表头（列名）
        String[] columnNames = {"no", "name", "age", "sex", "dept"};
        // 创建一个表格，指定 表头 和 所有行数据
        JTable table = new JTable(data, columnNames);
        // 设置滚动面板视口大小（超过该大小的行数据，需要拖动滚动条才能看到）
        table.setPreferredScrollableViewportSize(new Dimension(400, 400));
        // 把 表格 放到 滚动面板 中（表头将自动添加到滚动面板顶部）
        JScrollPane scrollPane = new JScrollPane(table);
        //放置到主面板
        JLabel l=new JLabel("these are all teachers' Information");
        JPanel p=new JPanel();
        //为panel设置边框
        p.setBorder(BorderFactory.createEmptyBorder(10,0,10,0));
        p.add(l);
        contentPanel.add(p);
        contentPanel.add(scrollPane);
    }
}

