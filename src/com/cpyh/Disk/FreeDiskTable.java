package com.cpyh.Disk;

public class FreeDiskTable {
    private int startNum;
    private int size;
    private int id;



    public FreeDiskTable(int startNum,int size,int id){
        this.startNum=startNum;
        this.size=size;
        this.id=id;
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

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }
}
