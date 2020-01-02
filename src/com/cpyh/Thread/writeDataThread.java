package com.cpyh.Thread;

import com.cpyh.Catalogue.FCB;
import com.cpyh.Disk.Disk;
import com.cpyh.Disk.FreeDiskTable;
import com.cpyh.Disk.ServiceDisk;
import com.cpyh.Disk.ServiceDiskTable;
import com.cpyh.Service.Show;
import com.sun.xml.internal.bind.v2.runtime.SwaRefAdapter;

import java.util.Map;


//该线程负责生成外存数据，
// 给定数据大小（按字节计算）、数据信息（英文字母）、存储目录、文件名后，
// 该线程调用磁盘管理中空闲磁盘管理功能，申请所需大小的外存块，如果盘块不够给出提示。
// 按照要求的数据组织方式，将数据存入磁盘块（按块分配磁盘），
// 并调用目录管理功能为其在目录中建立目录项，更改空闲盘块信息。

public class writeDataThread implements Runnable{
    private ServiceDisk serviceDisk= new ServiceDisk();
    private Disk[]disks;
    private String nowUser;
    //private int startNum;
    private int size;
    private String Data;
    private String Filename;
    private Map<String,FCB>totalUser;
    private Map<String,FCB> totalFiles;
    private Map<Integer, FreeDiskTable> DiskTables;
    private int Thread_id;

    public writeDataThread
            (int Thread_id,Disk[]disks,
             String nowUser, int size,String Data,String Filename,
             Map<String,FCB>totalUser,Map<String,FCB> totalFiles,
             Map<Integer, FreeDiskTable> DiskTables){
        this.Thread_id=Thread_id;
        this.disks=disks;
        this.nowUser=nowUser;
        //this.startNum=StartNum;
        this.size=size;
        this.Data=Data;
        this.Filename=Filename;
        this.totalUser=totalUser;
        this.totalFiles=totalFiles;
        this.DiskTables=DiskTables;
    }

    public Disk[] getDisks() {
        return disks;
    }

    public Map<String, FCB> getTotalUser() {
        return totalUser;
    }

    public Map<String, FCB> getTotalFiles() {
        return totalFiles;
    }

    public Map<Integer, FreeDiskTable> getDiskTables() {
        return DiskTables;
    }

    @Override
    public void run() {
        //直接调用
        ServiceDiskTable serviceDiskTable=new ServiceDiskTable();
        Show show = new Show();
        serviceDiskTable.allocation(nowUser,size,Filename,totalUser,totalFiles,DiskTables);
        serviceDisk.writeData(totalFiles.get(Filename).getStartNum(),Data,disks);
        show.showFilesofnowUser(nowUser,totalFiles);
        System.out.println("\t写入数据后的空闲区表");
        show.showDiskTable(DiskTables);
    }
}
