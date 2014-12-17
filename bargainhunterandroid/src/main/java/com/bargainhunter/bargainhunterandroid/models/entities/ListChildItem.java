package com.bargainhunter.bargainhunterandroid.models.entities;

public class ListChildItem {
    private String childName;
    private boolean defaultValue;
    private Object object;

    public ListChildItem(String childName, boolean defaultValue, Object object) {
        this.childName = childName;
        this.defaultValue = defaultValue;
        this.object = object;
    }

    public String getChildName() {
        return childName;
    }

    public void setChildName(String childName) {
        this.childName = childName;
    }

    public boolean isDefaultValue() {
        return defaultValue;
    }

    public void setDefaultValue(boolean defaultValue) {
        this.defaultValue = defaultValue;
    }

    public Object getObject() {
        return object;
    }

    public void setObject(Object object) {
        this.object = object;
    }
}
