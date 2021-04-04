package com.zyjl;

import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.zyjl.ui.BookInfoListCellRenderer;
import com.zyjl.ui.BookInfoModel;
import com.zyjl.ui.GroupbyModel;
import com.zyjl.util.BookInfo;
import com.zyjl.util.NewBookInfo;
import com.zyjl.util.NewUtil;
import com.zyjl.util.Util;

import javax.swing.*;
import javax.swing.event.ListSelectionEvent;
import javax.swing.event.ListSelectionListener;
import javax.swing.event.PopupMenuEvent;
import javax.swing.event.PopupMenuListener;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.ItemEvent;
import java.awt.event.ItemListener;
import java.util.ArrayList;

public class MainApp {
    private JTextField tfBookName;
    private JButton btnSearch;
    private JComboBox cbGrade;
    private JComboBox cbSubject;
    private JComboBox cbVersion;
    private JPanel mainPanel;
    private JPanel headPanel;
    private JPanel subPanel;
    private JList listBook;
    private JScrollPane scrollPane;

    private String uuid="";

    public MainApp(){
        //获取uuid
        uuid= Util.getUUID();
        //初始化列表
        java.util.List<NewBookInfo> listBookInfoEmpty =new ArrayList<NewBookInfo>();
        BookInfoModel bookInfoModel=new BookInfoModel(listBookInfoEmpty);
        listBook.setModel(bookInfoModel);
        //查询按钮响应
        btnSearch.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                String bookname=tfBookName.getText();
                if(bookname.trim().length()>0) {
                    //JOptionPane.showMessageDialog(null, bookname, "提示", JOptionPane.INFORMATION_MESSAGE);
                    //String booklist=Util.getBookList(bookname,uuid,"");
                    //System.out.println((booklist));
                    //fillListBook(booklist);

                    //app实现方式
                    String booklist= NewUtil.getBookList(bookname,"","1");
                    newFillListBook(booklist);

                }else {
                    JOptionPane.showMessageDialog(null, "不能为空！", "提示", JOptionPane.INFORMATION_MESSAGE);

                }

            }
        });

        //下拉框数据绑定
        //年级选择响应事件
        cbGrade.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                //点击下拉
                //System.out.println("下拉");
                cbGrade.removeAllItems();
                //获取年级数据
                String bookname=tfBookName.getText();
                if(bookname.trim().length()>0){
                    String strGradeInfo=NewUtil.getBookListGroup(bookname,"grade"+getSqlWhere(1));
                    java.util.List<GroupbyModel> groupbyModels=toGroupbyModel(strGradeInfo);
                    //填充数据项
                    GroupbyModel gm=new GroupbyModel();
                    gm.setId("0");
                    gm.setName("全部");
                    gm.setCount(0);
                    cbGrade.addItem(gm);
                    for(GroupbyModel groupbyModel:groupbyModels){
                        cbGrade.addItem(groupbyModel);
                    }
                }

            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {
                //System.out.println("收起");

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {
                //
                //System.out.println("取消");

            }
        });
        //年级选择事件
        cbGrade.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    filterSearch();
                }
            }
        });
        //学科数据绑定
        cbSubject.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                //点击下拉
                //System.out.println("下拉");
                cbSubject.removeAllItems();
                //获取年级数据
                String bookname=tfBookName.getText();
                if(bookname.trim().length()>0){
                    String strGradeInfo=NewUtil.getBookListGroup(bookname,"subject"+getSqlWhere(2));
                    //System.out.println(strGradeInfo);
                    java.util.List<GroupbyModel> groupbyModels=toGroupbyModel(strGradeInfo);
                    //填充数据项
                    GroupbyModel gm=new GroupbyModel();
                    gm.setId("0");
                    gm.setName("全部");
                    gm.setCount(0);
                    cbSubject.addItem(gm);
                    for(GroupbyModel groupbyModel:groupbyModels){
                        cbSubject.addItem(groupbyModel);
                    }
                }
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });
        //学科选择事件
        cbSubject.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    filterSearch();
                }
            }
        });
        //版本选择事件
        cbVersion.addPopupMenuListener(new PopupMenuListener() {
            @Override
            public void popupMenuWillBecomeVisible(PopupMenuEvent e) {
                //点击下拉
                //System.out.println("下拉");
                cbVersion.removeAllItems();
                //获取年级数据
                String bookname=tfBookName.getText();
                if(bookname.trim().length()>0){
                    String strGradeInfo=NewUtil.getBookListGroup(bookname,"version"+getSqlWhere(3));
                    //System.out.println(strGradeInfo);
                    java.util.List<GroupbyModel> groupbyModels=toGroupbyModel(strGradeInfo);
                    //填充数据项
                    GroupbyModel gm=new GroupbyModel();
                    gm.setId("0");
                    gm.setName("全部");
                    gm.setCount(0);
                    cbVersion.addItem(gm);
                    for(GroupbyModel groupbyModel:groupbyModels){
                        cbVersion.addItem(groupbyModel);
                    }
                }
            }

            @Override
            public void popupMenuWillBecomeInvisible(PopupMenuEvent e) {

            }

            @Override
            public void popupMenuCanceled(PopupMenuEvent e) {

            }
        });
        cbVersion.addItemListener(new ItemListener() {
            @Override
            public void itemStateChanged(ItemEvent e) {
                if(e.getStateChange() == ItemEvent.SELECTED){
                    filterSearch();
                }
            }
        });

        //列表点击事件,打开图书详细
        listBook.addListSelectionListener(new ListSelectionListener() {
            @Override
            public void valueChanged(ListSelectionEvent e) {
                if(!e.getValueIsAdjusting()) {//防止触发两次
                    String str = "";
                    int index = listBook.getSelectedIndex();
                    if (index >= 0) {
                        BookInfoModel bookInfoModel = (BookInfoModel) listBook.getModel();
//                    str = bookInfoModel.getBookInfoList().get(index).getBookname() + "\n" +
//                            bookInfoModel.getBookInfoList().get(index).getOnlyid();
//                    JOptionPane.showMessageDialog(null, str, null, JOptionPane.INFORMATION_MESSAGE);
                        String bookid = bookInfoModel.getBookInfoList().get(index).getOnlyid();
                        String bookname = bookInfoModel.getBookInfoList().get(index).getBookname();
                        //打开新的窗体
                        //=============================================================
                        // 得到显示器屏幕的宽高
                        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
                        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
                        int windowsWedth = 800;
                        int windowsHeight = 600;
                        JFrame detailFrame = new JFrame(bookname);
                        detailFrame.setContentPane(new BookDetail(bookid, uuid).rootPanel);
                        detailFrame.setDefaultCloseOperation(JFrame.DISPOSE_ON_CLOSE);
                        detailFrame.pack();
                        // 设置窗体位置和大小
                        detailFrame.setBounds((width - windowsWedth) / 2,
                                (height - windowsHeight) / 2, windowsWedth, windowsHeight);
                        detailFrame.setVisible(true);

                        //=============================================================
                    }
                }
            }
        });
    }

    //======================
    public java.util.List<BookInfo> toBookList(String str){
        //java.util.List<BookInfo> listBookInfo=new ArrayList<BookInfo>();

        JSONObject jsonObject= JSONObject.parseObject(str);

        return JSONArray.parseArray(jsonObject.getJSONArray("result").toString(),BookInfo.class);
    }

    public java.util.List<NewBookInfo> toNewBookList(String str){
        //java.util.List<BookInfo> listBookInfo=new ArrayList<BookInfo>();

        JSONObject jsonObject= JSONObject.parseObject(str);

        return  JSONArray.parseArray(jsonObject.getJSONObject("result").getJSONArray("lists").toString(),NewBookInfo.class);

    }

    public java.util.List<GroupbyModel> toGroupbyModel(String str){
        JSONObject jsonObject= JSONObject.parseObject(str);
        return JSONArray.parseArray(jsonObject.getJSONArray("result").toString(),GroupbyModel.class);
    }

    public String getSqlWhere(int flag){
        String strWhere="";
        if((cbGrade.getSelectedIndex()>0) &&(flag!=1)){
            String gradeId = ((GroupbyModel) cbGrade.getSelectedItem()).getId();
            strWhere+="&grade_id="+gradeId;
        }
        String subject="";
        if((cbSubject.getSelectedIndex()>0) &&(flag!=2)){
            subject=((GroupbyModel) cbSubject.getSelectedItem()).getId();
            strWhere+="&subject_id="+subject;
        }
        String version="";
        if((cbVersion.getSelectedIndex()>0) &&(flag!=3)){
            version=((GroupbyModel) cbVersion.getSelectedItem()).getId();
            strWhere+="&version_id="+version;
        }

        return strWhere;
    }

    public void filterSearch(){
        //
        String strWhere=getSqlWhere(0);
        //System.out.println("======"+strWhere);
        if(strWhere.trim().length()>0){
            String bookname=tfBookName.getText();
            String booklist=NewUtil.getBookList(bookname,strWhere,"1");
            //System.out.println(booklist);
            newFillListBook(booklist);
        }
    }

    /**
     * 填充列表
     */
//    public void fillListBook(String booklist){
//        java.util.List<BookInfo> listBookInfo=toBookList(booklist);
//
//        BookInfoModel bookInfoModel= (BookInfoModel) listBook.getModel();
//        //清空列表
//        bookInfoModel.getBookInfoList().clear();
//        //===========
//        if(listBookInfo==null||listBookInfo.size()<=0) {
//
//            JOptionPane.showMessageDialog(null, "未找到", "提示", JOptionPane.INFORMATION_MESSAGE);
//        }else {
//            //填充数据
//            bookInfoModel.getBookInfoList().addAll(listBookInfo);
//            listBook.setModel(bookInfoModel);
//            listBook.setCellRenderer(new BookInfoListCellRenderer());
//        }
//    }

    public void newFillListBook(String booklist){
        java.util.List<NewBookInfo> listBookInfo=toNewBookList(booklist);

        BookInfoModel bookInfoModel= (BookInfoModel) listBook.getModel();
        //清空列表
        bookInfoModel.getBookInfoList().clear();
        //===========
        if(listBookInfo==null||listBookInfo.size()<=0) {

            JOptionPane.showMessageDialog(null, "未找到", "提示", JOptionPane.INFORMATION_MESSAGE);
        }else {
            //填充数据
            bookInfoModel.getBookInfoList().addAll(listBookInfo);
            listBook.setModel(bookInfoModel);
            listBook.setCellRenderer(new BookInfoListCellRenderer());
        }
    }
    //=======================

    public static void main(String[] args) {
        // 得到显示器屏幕的宽高
//        int width = Toolkit.getDefaultToolkit().getScreenSize().width;
//        int height = Toolkit.getDefaultToolkit().getScreenSize().height;
//        int windowsWedth = 800;
//        int windowsHeight = 600;
        SwingUtilities.invokeLater(new Runnable() {
            @Override
            public void run() {
                int width = Toolkit.getDefaultToolkit().getScreenSize().width;
                int height = Toolkit.getDefaultToolkit().getScreenSize().height;
                int windowsWedth = 800;
                int windowsHeight = 600;

                JFrame frame = new JFrame("作业精灵下载");
                frame.setContentPane(new MainApp().mainPanel);
                frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
                frame.pack();

                // 设置窗体位置和大小
                frame.setBounds((width - windowsWedth) / 2,
                        (height - windowsHeight) / 2, windowsWedth, windowsHeight);

                frame.setVisible(true);
            }
        });
//        JFrame frame = new JFrame("作业精灵下载");
//        frame.setContentPane(new MainApp().mainPanel);
//        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
//        frame.pack();
//
//        // 设置窗体位置和大小
//        frame.setBounds((width - windowsWedth) / 2,
//                (height - windowsHeight) / 2, windowsWedth, windowsHeight);
//
//        frame.setVisible(true);
        //==================================
    }


}
