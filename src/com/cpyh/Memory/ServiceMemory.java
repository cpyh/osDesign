package com.cpyh.Memory;

import com.cpyh.Catalogue.FCB;

public class ServiceMemory {

    //初始化内存
    public MmDisk[] initMemory(){
        MmDisk [] mmdisks=new MmDisk[16];//一共16个内存块
        for (int i=0;i<mmdisks.length;i++) {//初始化16个内存块，由于是全局置换，所以不考虑分区问题。
            mmdisks[i]=new MmDisk();
            mmdisks[i].setTheFileName(null);
            mmdisks[i].setTheFileName(null);
            mmdisks[i].setFileDiskId(-1);
        }
        return mmdisks;
    }


    //固定调页申请
    public void allocationMemoryOnce(int ThreadNumber,MmDisk[]mmdisks){
        for(int i=0;i<16;i++){
            if(mmdisks[i].getId_Thread()==0){
                mmdisks[i].setId_Thread(ThreadNumber);
            }
        }
    }
    //线程结束时候 回收内存块
    public void reMemory(int ThreadNumber,MmDisk[]mmdisks){
        for(int i=0;i<16;i++){
            mmdisks[i].setId_Thread(0);
        }
    }
}
