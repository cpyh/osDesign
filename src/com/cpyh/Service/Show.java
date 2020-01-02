package com.cpyh.Service;

import com.cpyh.Catalogue.FCB;
import com.cpyh.Disk.Disk;
import com.cpyh.Disk.FreeDiskTable;
import com.cpyh.Disk.ServiceDisk;

import java.util.Map;

public class Show {
    ServiceDisk serviceDisk=new ServiceDisk();
    public void showUserandFilesandDiskTable(Map<String, FCB>totalUser, Map<String, FCB> totalFiles, Map<Integer, FreeDiskTable>DiskTables) {
        totalUser.forEach((stringkey, valueFCB) -> {
            System.out.println(stringkey + "   " + valueFCB.toString());
        });
        //查看文件表
        System.out.println(totalFiles.size());
        totalFiles.forEach((stringkey, valueFCB) -> {
            System.out.println(stringkey + " " + valueFCB.toString());
        });
        //查看空闲表
        DiskTables.forEach((Integerkey, freeDiskTable) -> {
            int endNum = freeDiskTable.getStartNum() + freeDiskTable.getSize();
            System.out.println(
                    Integerkey
                            + ":"
                            + freeDiskTable.getStartNum()
                            + "==========" + endNum);
        });
    }
    public void showFilesofnowUser(String nowUser, Map<String, FCB> totalFiles){
        System.out.println("\t文件名\t文件类型\t大小\t创建时间");
        totalFiles.forEach((stringkey, valueFCB) -> {
            if(valueFCB.getFather().getName().equals(nowUser)){
                System.out.println("\t"+valueFCB.getName()+"\t"+valueFCB.getType()+"\t\t"+valueFCB.getSize()*4+"B"+"\t\t"+valueFCB.getDate());
            }
        });
    }
    public void showDiskTable(Map<Integer,FreeDiskTable>DiskTables){
        DiskTables.forEach((Integerkey, freeDiskTable) -> {
            int endNum = freeDiskTable.getStartNum() + freeDiskTable.getSize()-1;
            System.out.println("\t"+
                    Integerkey
                            + ":"
                            + freeDiskTable.getStartNum()
                            + "==========" + endNum);
        });
    }
    public void showDisk(Disk[]disks){
        serviceDisk.showDisk(disks);
    }
    public void showUser(Map<String,FCB> totalUser){
        System.out.printf("\t当前存在的用户：");
        totalUser.forEach((stringkey, valueFCB) -> {
            System.out.printf("\t"+valueFCB.getName()+" ");
        });
        System.out.println();
    }
}
