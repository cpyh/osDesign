package com.cpyh.Thread;

import com.cpyh.Catalogue.FCB;
import com.cpyh.Disk.Disk;
import com.cpyh.Memory.MmDisk;
import com.cpyh.Memory.ServiceMemory;

import java.util.Map;

public class ExecuteThread implements Runnable{
    private Disk[]disks;
    private MmDisk[]mmdisks;
    private String Filename;
    private Map<String, FCB>totalFiles;
    private int Thread_id;
    public ExecuteThread(int Thread_id,Disk[]disks,int StartNum,int endNum){
        this.Thread_id=Thread_id;
        this.disks=disks;
    }
    @Override
    public void run() {
        //选择目录中的文件，执行线程将文件数据从外存调入内存， Filename
        // 为此，首先需要调用内存管理的空闲空间管理功能，为该进程申请4块空闲内存，  MmDisks
        // 如果没有足够内存则给出提示，  加个判断条件
        // 然后根据目录中文件存储信息将文件数据从外存读入内存， 一块一块申请，加上延迟
        // 此间如果4块内存不够存放文件信息，需要进行换页（选择的换页策略见分组要求），  FIFO策略，全局置换
        // 换出的页面存放到磁盘兑换区。  丢过去完事了，不改了
        // 允许同时运行多个执行线程。文件数据在内存块的分布通过线程的页表（模拟）进行记录。  暂时不会
        ServiceMemory serviceMemory=new ServiceMemory();
        FCB tmpFCB=totalFiles.get(Filename);
        int tmpStartNum=tmpFCB.getStartNum();
        int tmpdoneNumber=0;
        while(tmpdoneNumber<tmpFCB.getSize()) {
            serviceMemory.allocationMemoryOnce(Thread_id, mmdisks, disks, tmpFCB, 0);
        }
    }
}
