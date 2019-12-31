package com.cpyh.Disk.service;

import com.cpyh.Disk.pojo.Disk;

public class ServiceDisk {
    //磁盘块分配，这个我猜应该是放到文件系统里面
    //读数据操作

    /**
     *
     * @param startNum
     * @param endNum
     * @param disk 磁盘数据应该设计成一个全局变量，直接在main函数里面去做，或者初始化
     * @return
     */
    public String getData(int startNum, int endNum, Disk []disk){
        String str="";
        for(int i = startNum;i<=endNum;i++){
            str+=disk[i].getData();
        }
        return str;
    }
    //初始化
    public Disk[] InitDisk(){
        Disk [] disk=new Disk[1024];
        return disk;
        //问题：如何用一个4KB的文件来保存磁盘信息？
    }
}
