package com.cpyh.Catalogue;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件的目录项为FCB，使用subMap文件保存所有的目录项，作为一个目录哈希表
 */
public class FCB {//FCB 既可以是文件也可以是用户，当为用户的时候，才会用到subMap即UFD，否则该结构不存在
    public Map<String, FCB> subMap = new HashMap<String, FCB>();//UFD
    private String name;//文件名
    private String type;//文件类型
    private int attr;//用于识别是文件还是目录
    private int startNum;//在FAT表中起始位置
    private int size;//计划使用盘块数来代替
    private FCB father = null;//上级目录，课设要求是二级目录，所以该上级目录就是MFD

    public FCB(String name,String type,int startNum,int size){
        this.name=name;
        this.type=type;
        this.attr=2;
        this.startNum=startNum;
        this.size=size;
    }

    public FCB(String name,int startNum){//创建用户
        this.name=name;
        this.attr=3;
        this.startNum=startNum;
        this.type=" ";
        this.size=1;
    }

    public Map<String, FCB> getSubMap() {
        return subMap;
    }
    public void setSubMap(Map<String, FCB> subMap) {
        this.subMap = subMap;
    }
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
