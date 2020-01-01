package com.cpyh.Thread;

import com.cpyh.Disk.Disk;
import com.cpyh.Disk.ServiceDisk;

public class ExecuteThread implements Runnable{
    private Disk[]disks;
    private int startNum;
    private int endNum;
    private int Thread_id;
    public ExecuteThread(int Thread_id,Disk[]disks,int StartNum,int endNum){
        this.Thread_id=Thread_id;
        this.disks=disks;
        this.startNum=StartNum;
        this.endNum=endNum;
    }
    @Override
    public void run() {

    }
}
