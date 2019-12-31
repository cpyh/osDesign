package com.cpyh.Disk.pojo;

public class FCB {
    private String name;//文件名
    private String type;//文件类型
    private int attr;//用于识别是文件还是目录
    private int startNum;//在FAT表中起始位置
    private int size;//计划使用盘块数来代替
    private FCB father = null;//上级目录

    public String getName() {
        return name;
    }
    public void setName(String name) {
        this.name = name;
    }
    public String getType() {
        return type;
    }
    public void setType(String type) {
        this.type = type;
    }
    public int getAttr() {
        return attr;
    }
    public void setAttr(int attr) {
        this.attr = attr;
    }

    public int getStartNum() {
        return startNum;
    }
    public void setStartNum(int startNum) {
        this.startNum = startNum;
    }

    public int getSize() {
        return size;
    }

    public void setSize(int size) {
        this.size = size;
    }

    public FCB getFather() {
        return father;
    }

    public void setFather(FCB father) {
        this.father = father;
    }
}
