package com.mobileclient.domain;

import java.io.Serializable;

public class ProductClass implements Serializable {
    /*�����*/
    private int classId;
    public int getClassId() {
        return classId;
    }
    public void setClassId(int classId) {
        this.classId = classId;
    }

    /*�������*/
    private String className;
    public String getClassName() {
        return className;
    }
    public void setClassName(String className) {
        this.className = className;
    }

}