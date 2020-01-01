package com.cpyh.Thread;

import com.cpyh.Disk.Disk;
import com.cpyh.Disk.ServiceDisk;
import com.cpyh.Memory.MmDisk;

import java.util.HashMap;
import java.util.Map;

public class DeleteDataThread implements Runnable{
    private Disk[]disks;
    private int startNum;
    private int endNum;
    private int Thread_id;
    public DeleteDataThread(int Thread_id,Disk[]disks,int StartNum,int endNum){
        this.Thread_id=Thread_id;
        this.disks=disks;
        this.startNum=StartNum;
        this.endNum=endNum;
    }
    @Override
    public void run() {
        ServiceDisk serviceDisk=new ServiceDisk();
        serviceDisk.deleteData(startNum,endNum,disks);
    }
}
