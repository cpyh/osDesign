package com.cpyh.Memory;

import com.cpyh.Catalogue.FCB;
import com.cpyh.Disk.Disk;

public class ServiceMemory {

    //初始化内存
    public MmDisk[] initMemory(){
        MmDisk [] mmdisks=new MmDisk[16];//一共16个内存块
        for (int i=0;i<mmdisks.length;i++) {//初始化16个内存块，由于是全局置换，所以不考虑分区问题。
            mmdisks[i]=new MmDisk();
            mmdisks[i].setTheFileName(null);
            //mmdisks[i].setTheFileName(null);
            mmdisks[i].setFileDiskId(-1);
        }
        return mmdisks;
    }


    //固定调页申请,并将数据存入内存块
    public void allocationMemoryOnce(int Thread_ID, MmDisk[]mmdisks, Disk[]disks,FCB Files,int doneNumber){
        boolean successFlag=false;
        for(int i=0;i<16;i++){
            if(mmdisks[i].getId_Thread()==0){
                mmdisks[i].setId_Thread(Thread_ID);
                mmdisks[i].setData(disks[Files.getStartNum()+doneNumber].getData());
                mmdisks[i].setFileDiskId(Files.getStartNum()+doneNumber);//把内存数据和物理块地址建立了一个联系，我也不知道这样要干嘛
                mmdisks[i].setTheFileName(Files.getName());
                successFlag=true;
                break;
            }
        }
        //如果没有空闲内存，将暂时占用的内存块调出内存，放到对换区
        if(!successFlag){
            //这里使用FIFO算法调出内存中的数据
            MmDisk tmpMmDisk=new MmDisk();
            tmpMmDisk=mmdisks[0];
            for(int j=1;j<16;j++){
                if(mmdisks[j].getTime()>tmpMmDisk.getTime())
                    tmpMmDisk=mmdisks[j];
            }
            mmdisks[tmpMmDisk.getId()].setTime(0);
            mmdisks[tmpMmDisk.getId()].setId_Thread(Thread_ID);
            //这个内存块调到对换区
            for(int i=0;i<124;i++){
                if(!disks[i].isUsed()){
                    disks[i].setData(tmpMmDisk.getData());
                    disks[i].setUsed(true);
                }
            }
        }
    }
    //线程结束时候 回收内存块
    public void reMemory(int ThreadNumber,MmDisk[]mmdisks){
        for(int i=0;i<16;i++){
            if(mmdisks[i].getId_Thread()==ThreadNumber){
                mmdisks[i].setId_Thread(0);
                mmdisks[i].setTime(-1);//将使用时间重置为0
            }
        }
    }

    //展示内存中的信息
    public void showMemory(MmDisk[]mmdisks){
        for(int i=0;i<16;i++){
            System.out.println(mmdisks[i].toString());
        }
    }

    //更新每个MmDisk的使用时间
    public void updateMmTime(MmDisk[]mmdisks){
        for(int i=0;i<16;i++){
            if(mmdisks[i].getId_Thread()!=0){
                mmdisks[i].setTime(mmdisks[i].getTime()+1);
            }
        }
    }
}
