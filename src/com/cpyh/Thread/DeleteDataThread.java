package com.cpyh.Thread;

import com.cpyh.Catalogue.FCB;
import com.cpyh.Catalogue.FileManager;
import com.cpyh.Disk.Disk;
import com.cpyh.Disk.FreeDiskTable;
import com.cpyh.Disk.ServiceDisk;
import com.cpyh.Disk.ServiceDiskTable;
import com.cpyh.Service.Show;

import java.util.Map;

public class DeleteDataThread implements Runnable{
    private ServiceDisk serviceDisk= new ServiceDisk();
    private ServiceDiskTable serviceDiskTable=new ServiceDiskTable();
    private FileManager fileManager=new FileManager();
    private Show show=new Show();
    private Disk[]disks;
    private String nowUser;
    //private int startNum;
    private int size;
    private String Data;
    private String Filename;
    private Map<String, FCB>totalUser;
    private Map<String,FCB> totalFiles;
    private Map<Integer, FreeDiskTable> DiskTables;
    private int Thread_id;

    public DeleteDataThread
            (int Thread_id,Disk[]disks,
             String nowUser,String Filename,
             Map<String,FCB>totalUser,Map<String,FCB> totalFiles,
             Map<Integer, FreeDiskTable> DiskTables){
        this.Thread_id=Thread_id;
        this.disks=disks;
        this.nowUser=nowUser;
        //this.size=size;
        //this.Data=Data;
        this.Filename=Filename;
        this.totalUser=totalUser;
        this.totalFiles=totalFiles;
        this.DiskTables=DiskTables;
    }
    //删除数据线程：
    // 该线程调用删除目录管理中文件删除功能删除数据（内存中文件不能删除）。
    // 并回收外存空间，更新空闲盘块信息。
    @Override
    public void run() {
        DiskTables=serviceDiskTable.recovery(Filename,totalFiles,DiskTables);//更新空闲盘块信息
        fileManager.deleteFiles(nowUser,Filename,disks,totalUser,totalFiles);
        System.out.println("\t删除文件数据后的空闲区表");
        show.showDiskTable(DiskTables);
    }
    public Map<Integer, FreeDiskTable> getDiskTables(){
        return DiskTables;
    }
}
