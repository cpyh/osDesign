package com.cpyh.Disk;

public class Disk {
    private int name;//磁盘块编号
    private String data;//磁盘块上的数据，最多保存4个字母
    private boolean Used;

    public int getName() {
        return name;
    }
    public void setName(int name) {
        this.name = name;
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
