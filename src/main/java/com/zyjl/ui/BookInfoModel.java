package com.zyjl.ui;

import com.zyjl.util.BookInfo;
import com.zyjl.util.NewBookInfo;

import javax.swing.*;
import java.util.List;

public class BookInfoModel extends AbstractListModel {

    List<NewBookInfo> bookInfoList;

    public BookInfoModel(List<NewBookInfo> bookInfoList) {
        this.bookInfoList = bookInfoList;
    }

    public List<NewBookInfo> getBookInfoList() {
        return bookInfoList;
    }

    @Override
    public int getSize() {
        return bookInfoList.size();
    }

    @Override
    public Object getElementAt(int index) {
        return bookInfoList.get(index);
    }
}
