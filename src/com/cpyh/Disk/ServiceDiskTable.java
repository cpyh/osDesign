package com.cpyh.Disk;

import com.cpyh.Catalogue.FCB;
import com.cpyh.Catalogue.FileManager;
import org.junit.Test;

import javax.xml.ws.Service;
import java.io.File;
import java.io.FileNotFoundException;
import java.util.*;

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
    (String nowUser,int size,String Filename,Map<String, FCB>totalUser,Map<String, FCB>totalFiles,Map<Integer,FreeDiskTable> DiskTables){
        int tmpStartNum=0;
        int tmp = size%4;
        if(tmp!=0){
            size=size/4+1;
        }else
            size=size/4;
        for(Integer key:DiskTables.keySet()){
            FreeDiskTable freeDiskTable=DiskTables.get(key);
            if(freeDiskTable.getSize()>size) {
                FreeDiskTable tempFreeDisk = new FreeDiskTable(freeDiskTable.getStartNum() + size, freeDiskTable.getSize() - size, freeDiskTable.getId());
                DiskTables.remove(key);//删去原本的Disk表
                DiskTables.put(tempFreeDisk.getId(), tempFreeDisk);//放入新的空闲表
                tmpStartNum=freeDiskTable.getStartNum();
                break;
            }
            //否则抛出没有足够空间
        }
        fileManager.createFile(nowUser,Filename,"文件",tmpStartNum,size,totalUser,totalFiles);
    }
    //回收,更新空闲盘区表，当删除文件的时候，显然是需要解决这个问题的
    public Map<Integer, FreeDiskTable> recovery(String Filename, Map<String, FCB>totalFiles, Map<Integer,FreeDiskTable> DiskTables){
        //在文件从目录表中删除之前完成空闲区表的更新操作
        int FileStartNum=totalFiles.get(Filename).getStartNum();
        int FileEndNum=totalFiles.get(Filename).getStartNum()+totalFiles.get(Filename).getSize();
        //1.判断是否和前面的|后面的空闲区接壤 Front|After
        boolean Front=false,After=false;
        int keyFront=0,keyAfter=0,keyZero=0;
        for(Integer key:DiskTables.keySet()){
            FreeDiskTable freeDiskTable=DiskTables.get(key);
            if(freeDiskTable.getStartNum()+freeDiskTable.getSize()==FileStartNum) {
                //和前面接壤
                Front=true;
                keyFront=key;
            }
            if(freeDiskTable.getStartNum()==FileEndNum){
                //只和后面接壤
                After=true;
                keyAfter=key;
            }
        }
        if(Front&&After){
            //前后都接壤
            Map<Integer,FreeDiskTable>newDiskTables=new HashMap<Integer,FreeDiskTable>();
            for(int i=0;i<keyFront;i++){
                newDiskTables.put(i,DiskTables.get(i));
            }
            FreeDiskTable tmp=DiskTables.get(keyFront);
            tmp.setId(keyFront);
            tmp.setSize(tmp.getSize()+totalFiles.get(Filename).getSize()+DiskTables.get(keyAfter).getSize());
            newDiskTables.put(keyFront,tmp);
            for(int k=keyAfter+1;k<DiskTables.size();k++){
                newDiskTables.put(keyAfter,DiskTables.get(k));
            }
            Map<Integer,FreeDiskTable> resultMap=sortMapByKey(newDiskTables);
            return resultMap;
        }else if(Front&&!After){
            //只和前面接壤
            FreeDiskTable newFre = DiskTables.get(keyFront);
            newFre.setSize(DiskTables.get(keyFront).getSize()+totalFiles.get(Filename).getSize());
            DiskTables.remove(keyFront);
            DiskTables.put(keyFront,newFre);
            Map<Integer,FreeDiskTable> resultMap=sortMapByKey(DiskTables);
            return resultMap;
            //DiskTables.get(keyFront).setSize(DiskTables.get(keyFront).getSize()+totalFiles.get(Filename).getSize());
        }else if(!Front&&After){
            //只和后面接壤
            FreeDiskTable newFre = DiskTables.get(keyAfter);
            newFre.setStartNum(totalFiles.get(Filename).getStartNum());
            newFre.setSize(DiskTables.get(keyAfter).getSize()+totalFiles.get(Filename).getSize());
            DiskTables.remove(keyAfter);
            DiskTables.put(keyAfter,newFre);
            Map<Integer,FreeDiskTable> resultMap=sortMapByKey(DiskTables);
            return resultMap;
            //DiskTables.get(keyAfter).setStartNum(totalFiles.get(Filename).getStartNum());
            //DiskTables.get(keyAfter).setSize(DiskTables.get(keyAfter).getSize()+totalFiles.get(Filename).getSize());
        }else{
            //都不接壤
            for(int i=0;i<DiskTables.size();i++){
                if(DiskTables.get(i).getStartNum()<totalFiles.get(Filename).getStartNum()){
                    //找到离要删除的文件区最近的上一个空闲区
                    keyZero= i;
                    break;
                }else{
                    //没有找到离要删除的文件区最近的上一个空闲区，说明此时该文件区就是第一个
                    keyZero=-1;
                }
            }
            Map<Integer,FreeDiskTable>newDiskTables=new HashMap<Integer,FreeDiskTable>();
            for(int ss=0;ss<keyZero+1;ss++){
                newDiskTables.put(ss,DiskTables.get(ss));
            }
            FreeDiskTable newFre = new FreeDiskTable(totalFiles.get(Filename).getStartNum(),totalFiles.get(Filename).getSize(),keyZero+1);
            newDiskTables.put(keyZero+1,newFre);
            for(int j=keyZero+1;j<DiskTables.size();j++){
                FreeDiskTable tmpFree=DiskTables.get(j);
                tmpFree.setId(j+1);
                newDiskTables.put(tmpFree.getId(),tmpFree);
            }
            FreeDiskTable tmpDisk=new FreeDiskTable(FileStartNum,totalFiles.get(Filename).getSize(),keyZero+1);
            DiskTables.put(tmpDisk.getId(),tmpDisk);
            //Map<Integer,FreeDiskTable> resultMap=sortMapByKey(newDiskTables);
            //return resultMap;
            return newDiskTables;
        }
    }

    public Map<Integer,FreeDiskTable> sortMapByKey(Map<Integer,FreeDiskTable>map) {
        Map<Integer, FreeDiskTable> sortMap = new TreeMap<Integer, FreeDiskTable>(new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return o1.compareTo(o2);
            }
        });
        sortMap.putAll(map);
        return sortMap;
    }

}
