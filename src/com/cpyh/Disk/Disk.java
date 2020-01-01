package com.cpyh.Disk;

public class Disk {
    private static int id=0;//磁盘块编号
    private String data;//磁盘块上的数据，最多保存4个字母
    private boolean Used;

    public Disk(){
        id++;
        this.Used=false;
    }
    public int getId() {
        return id;
    }

    public String getData() {
        return data;
    }
    public void setData(String data) {
        this.data = data;
    }

    public boolean isUsed() {
        return Used;
    }

    public void setUsed(boolean used) {
        Used = used;
    }
}
