package com.cpyh.Thread;

import com.cpyh.Catalogue.FCB;
import com.cpyh.Disk.Disk;
import com.cpyh.Disk.FreeDiskTable;
import com.cpyh.Disk.ServiceDiskTable;

import java.util.Map;


//该线程负责生成外存数据，
// 给定数据大小（按字节计算）、数据信息（英文字母）、存储目录、文件名后，
// 该线程调用磁盘管理中空闲磁盘管理功能，申请所需大小的外存块，如果盘块不够给出提示。
// 按照要求的数据组织方式，将数据存入磁盘块（按块分配磁盘），
// 并调用目录管理功能为其在目录中建立目录项，更改空闲盘块信息。

public class writeDataThread implements Runnable{
    private Disk[]disks;
    private String nowUser;
    private int startNum;
    private int endNum;
    private int size;
    private String Data;
    private String Filename;
    private Map<String,FCB>totalUser;
    private Map<String, FCB> totalFiles;
    private Map<Integer, FreeDiskTable> DiskTables;
    private int Thread_id;
    public writeDataThread(int Thread_id,Disk[]disks,int StartNum,int endNum){
        this.Thread_id=Thread_id;
        this.disks=disks;
        this.startNum=StartNum;
        this.endNum=endNum;
    }
    @Override
    public void run() {
        //直接调用
        ServiceDiskTable serviceDiskTable=new ServiceDiskTable();
        serviceDiskTable.allocation(disks,nowUser,size,Data,Filename,totalUser,totalFiles,DiskTables);
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        //这个线程休眠应该放到执行线程去，或者把每个线程的各部分函数拆分，然后加入休眠
    }
}
