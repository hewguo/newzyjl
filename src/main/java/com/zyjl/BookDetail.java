package com.zyjl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.lowagie.text.BadElementException;
import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.PageSize;
import com.lowagie.text.pdf.PdfWriter;
import com.zyjl.ui.BookVolume;
import com.zyjl.ui.LinkLabel;
import com.zyjl.ui.SingleBook;
import com.zyjl.util.NewUtil;
//import com.zyjl.util.Util;

import javax.imageio.ImageIO;
import javax.swing.*;
import javax.swing.filechooser.FileNameExtensionFilter;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URL;
import java.util.List;

public class BookDetail {
    public JPanel rootPanel;
    private JLabel labelInfo;
    private JPanel buttonPanel;
    private JPanel downloadPanel;
    private JScrollPane scrollPanel;
    private JPanel detailPanel;
    private JPanel statusPanel;
    private JLabel lbStatus;
    private String bookid;//图书id
    private String uuid="";
    private String strBookInfo;
    private List<SingleBook> books=null;
    private ButtonGroup group;
    private String bookanswer;
    private JSONArray urlArray;
    private String defaultFilename="";

    /**
     * 初始化
     * @param bookid
     */
    public BookDetail(String bookid,String uuid){
        this.bookid=bookid;
        this.uuid=uuid;
        strBookInfo= NewUtil.getBookDetail(bookid);

        showLabel();

        toBooksArray();
        //System.out.println(strBookInfo);
        //========================
        group = new ButtonGroup();
        int defaultSelected=books.get(0).getYear();
        for(SingleBook book:books){
            addRadioButton(""+book.getYear(),book.getYear(),defaultSelected);
        }
        //System.out.println("----11--");

    }

    public void showLabel(){
        JSONObject jsonObject= JSONObject.parseObject(strBookInfo);
        int retCode=jsonObject.getIntValue("code");
        if(retCode==0){
            //显示图书信息
            JSONObject bookinfo=jsonObject.getJSONObject("result").getJSONObject("info");
            String bookname=bookinfo.getString("bookname");
            String cover=bookinfo.getString("cover");
            String grade_name=bookinfo.getString("grade_name");
            String subject_name=bookinfo.getString("subject_name");
            String version_name=bookinfo.getString("version_name");
            String text="<html>"+bookname+"<br/><br/><br/>"+
                    "<font color='red'>"+subject_name+"</font>"+
                    "<font color='green'>"+grade_name+"</font>"+
                    "<font color='blue'>"+version_name+"</font>"+"</html>";

            //defaultFilename=bookname+"_"+grade_name+"_"+subject_name+"_"+version_name+".pdf";
            defaultFilename=bookname+".pdf";

            //设置JLabel图片
            Image img=null;
            try {
                img= ImageIO.read(new URL(cover));
            } catch (IOException e) {
                e.printStackTrace();
            }
            //

            labelInfo.setIcon(new ImageIcon(img));
            labelInfo.setText(text);
        }

    }

    public void toBooksArray(){
        JSONObject jsonObject= JSONObject.parseObject(strBookInfo);
        int retCode=jsonObject.getIntValue("code");
        if(retCode==0){
            JSONArray jsonBooks=jsonObject.getJSONObject("result").getJSONArray("books");
            books= JSONArray.parseArray(jsonBooks.toString(), SingleBook.class);
//            for(SingleBook book:books){
//                System.out.println(book.getYear()+"===="+book.getVolumes());
//                //String temp=""+book.getVolumes();
//
//                List<BookVolume> bookVolumes=book.getVolumes();
//                for(int i=0;i<bookVolumes.size();i++){
//
//                    System.out.println(bookVolumes.get(i).toString());
//                }
//            }
        }
    }

    //添加按钮
    public void addRadioButton(String name,final int year,int defaultSelect){
        boolean selected=year==defaultSelect;
        selected=false;
        JRadioButton button=new JRadioButton(name,selected);
        button.setActionCommand(name);//设置name即为actionCommand
        group.add(button);
        //解决在添加任何组件时，JPanel给出运行时空指针异常
        buttonPanel.setLayout(new FlowLayout( FlowLayout.LEFT,10,5));
        buttonPanel.add(button);
        //添加button响应事件
        button.addActionListener(new ActionListener() {
            @Override
            public void actionPerformed(ActionEvent e) {
                // 1.通过eActionCommand
                String year = e.getActionCommand();
                //清空组件
                downloadPanel.removeAll();
                for(SingleBook book:books){
                    if(year.equals(""+book.getYear())){
                        List<BookVolume> bookVolumes=book.getVolumes();
                        for(BookVolume bookVolume:bookVolumes){
                            JButton btnDownload=new JButton(bookVolume.getVolumes_name()+"|"+bookVolume.getId());
                            downloadPanel.setLayout(new FlowLayout(FlowLayout.LEFT,10,5));
                            downloadPanel.repaint();
                            downloadPanel.add(btnDownload);
                            downloadPanel.revalidate();
                            btnDownload.addActionListener(new ActionListener() {
                                @Override
                                public void actionPerformed(ActionEvent e) {
                                    //
                                    String id=((JButton)e.getSource()).getText();

                                    String[] temps=id.split("\\|");
                                    //System.out.println(temps[1]);
                                    //=========
                                    bookanswer=NewUtil.getAnswer(temps[1]);
                                    //System.out.println(bookanswer);
                                    new Thread(new Runnable() {
                                        @Override
                                        public void run() {
                                            showDetailPanel(bookanswer);
                                        }
                                    }).start();
                                    //showDetailPanel(bookanswer);
                                }
                            });
                        }
                        break;
                    }
                }
            }
        });
    }
    //添加书的详情页
    public void showDetailPanel(String strBookDetail){
        detailPanel.removeAll();
        detailPanel.setLayout(new BoxLayout(detailPanel,BoxLayout.Y_AXIS));
        detailPanel.repaint();


        JSONObject bookDetail= JSON.parseObject(strBookDetail);
        int code=bookDetail.getIntValue("code");
        if(code==0){
            urlArray=bookDetail.getJSONObject("result").getJSONArray("answer");

            JButton btnDownload=new JButton("打包pdf下载");
            btnDownload.addActionListener(new ActionListener() {
                @Override
                public void actionPerformed(ActionEvent e) {
                    //打包下载
                    // 创建文件选择器
                    JFileChooser chooser = new JFileChooser();
                    chooser.setDialogTitle("保存文件");//自定义选择框标题
                    chooser.setSelectedFile(new File(defaultFilename));//设置默认文件名
                    chooser.setFileFilter(new FileNameExtensionFilter("PDF", new String[]{"pdf"}));

                    // 打开文件保存对话框
                    //int option = chooser.showSaveDialog(null);
                    int option = chooser.showDialog(null, "保存文件");
                    //这行代码取代showOpenDialog和showSaveDialog
                    // 处理文件保存操作
                    if (option == JFileChooser.APPROVE_OPTION) {
                        lbStatus.setText("开始下载（共"+urlArray.size()+"页）...");
                        File file = chooser.getSelectedFile();
                        // 第一步：创建一个document对象。
                        com.lowagie.text.Rectangle rectPageSize = new com.lowagie.text.Rectangle(PageSize.A4);
                        // A4纸张
                        Document document = new Document(rectPageSize, 40, 40, 40, 40);
                        // 上、下、左、右间距
                        //Document document = new Document();
                        document.setMargins(0, 0, 0, 0);
                        // 第二步：
                        // 创建一个PdfWriter实例，
                        try {
                            PdfWriter.getInstance(document, new FileOutputStream(file));
                        } catch (DocumentException ex) {
                            ex.printStackTrace();
                        } catch (FileNotFoundException ex) {
                            ex.printStackTrace();
                        }
                        // 第三步：打开文档。
                        document.open();
                        //==================

                        //==================
                        // 第四步：在文档中增加图片。
                        for(int i=0;i<urlArray.size();i++){
                            String url=urlArray.getString(i);
                            com.lowagie.text.Image img= null;

                            //设置JLabel图片
                            try {
                                img= com.lowagie.text.Image.getInstance(url);
                                img.setAlignment(com.lowagie.text.Image.ALIGN_CENTER);

                                img.scaleAbsolute(PageSize.A4.getWidth()-80, PageSize.A4.getHeight()-80);// 直接设定显示尺寸
                            } catch (IOException ex) {
                                ex.printStackTrace();
                            } catch (BadElementException ex) {
                                ex.printStackTrace();
                            }
                            document.setPageSize(new com.lowagie.text.Rectangle(PageSize.A4.getWidth()-80, PageSize.A4.getHeight()-80));
                            document.newPage();
                            //线程同步显示label
                            //==========
                            final String temp="已完成第"+(i+1)+"页,共"+urlArray.size()+"页";
                            SwingUtilities.invokeLater(new Runnable() {
                                @Override
                                public void run() {
                                    lbStatus.setText(temp);
                                    lbStatus.updateUI();
                                    statusPanel.revalidate();
                                }
                            });
                            System.out.println(temp);

                            //lbStatus.setText("已完成第"+(i+1)+"页,共"+urlArray.size()+"页");
                            try {
                                document.add(img);
                            } catch (DocumentException ex) {
                                ex.printStackTrace();
                            }
                        }
                        document.close();

                        lbStatus.setText("下载完成，文件为："+file.getAbsolutePath());
                        JOptionPane.showMessageDialog(null, "成功导出pdf文件", "提示", JOptionPane.INFORMATION_MESSAGE);
                    }

                }
            });
            detailPanel.add(btnDownload);


            for(int i=0;i<urlArray.size();i++){
                String name="第"+(i+1)+"页";
                String url=urlArray.getString(i);
                LinkLabel linkLabel=new LinkLabel(name,url);
                detailPanel.add(linkLabel);

            }

            detailPanel.revalidate();

        }
    }

}
