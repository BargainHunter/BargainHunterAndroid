package com.bargainhunter.bargainhunterandroid.models.components;

import java.util.ArrayList;

public class ListParentItem {
    private String parentName;
    private ArrayList<ListChildItem> children;

    public ListParentItem(String parentName, ArrayList<ListChildItem> children) {
        this.parentName = parentName;
        this.children = children;
    }

    public String getParentName() {
        return parentName;
    }

    public void setParentName(String parentName) {
        this.parentName = parentName;
    }

    public ArrayList<ListChildItem> getChildren() {
        return children;
    }

    public void setChildren(ArrayList<ListChildItem> children) {
        this.children = children;
    }
}
