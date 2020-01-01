package com.cpyh.Disk;

import com.cpyh.Catalogue.FCB;
import com.cpyh.Catalogue.FileManager;

import javax.xml.ws.Service;
import java.util.HashMap;
import java.util.Iterator;
import java.util.Map;

/**
 * 磁盘的补充操作，拆分为兑换区和文件区。兑换区用来存放内存换出的文件，文件区用来存放数据
 */
public class ServiceDiskTable {
    FileManager fileManager=new FileManager();
    //初始化兑换区
    public Map<Integer,FreeDiskTable> initSwapDisk(){
        Map<Integer,FreeDiskTable> SwapDiskTable= new HashMap<Integer, FreeDiskTable>();
        FreeDiskTable SwapDisk=new FreeDiskTable(0,124,0);
        SwapDiskTable.put(0,SwapDisk);
        return SwapDiskTable;
    }
    //初始化文件区
    public Map<Integer,FreeDiskTable> initUserDisk(){
        Map<Integer,FreeDiskTable> UserDiskTable= new HashMap<Integer, FreeDiskTable>();
        FreeDiskTable UserDisk=new FreeDiskTable(0,900,0);
        UserDiskTable.put(0,UserDisk);
        return UserDiskTable;
    }

    //分配内存块
    //传入的是文件信息,文件表,不会是用户表

    //该线程负责生成外存数据，
    // 给定数据大小（按字节计算）、数据信息（英文字母）、存储目录、文件名后，
    // 该线程调用磁盘管理中空闲磁盘管理功能，申请所需大小的外存块，如果盘块不够给出提示。
    // 按照要求的数据组织方式，将数据存入磁盘块（按块分配磁盘），
    // 并调用目录管理功能为其在目录中建立目录项，更改空闲盘块信息。
    //这个函数可以进一步拆分成三个小的功能函数，晚点再搞
    public void allocation
    (Disk[]disks,String nowUser,int size,String Data,String Filename,Map<String, FCB>totalFiles,Map<Integer,FreeDiskTable> DiskTables){
        int tmpStartNum=0;
        for(Integer key:DiskTables.keySet()){
            FreeDiskTable freeDiskTable=DiskTables.get(key);
            if(freeDiskTable.getSize()>size) {
                FreeDiskTable tempFreeDisk = new FreeDiskTable(freeDiskTable.getStartNum() + size, freeDiskTable.getSize() - size, freeDiskTable.getId());
                DiskTables.remove(key);
                DiskTables.put(tempFreeDisk.getId(), tempFreeDisk);
                tmpStartNum=tempFreeDisk.getStartNum();
                break;
            }
            //否则抛出没有足够空间
        }
        fileManager.createFile(nowUser,Filename,"文件",tmpStartNum,size);
        //文件创建成功后，在该startNum开始处写数据
            ServiceDisk serviceDisk=new ServiceDisk();
            serviceDisk.writeData(tmpStartNum,Data,disks);
    }
    //回收

}
