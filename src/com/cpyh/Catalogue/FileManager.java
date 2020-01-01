package com.cpyh.Catalogue;

import com.cpyh.Disk.Disk;
import com.cpyh.Disk.ServiceDisk;
import com.cpyh.Catalogue.FCB;

import java.util.HashMap;
import java.util.Map;

/**
 * 文件相关的操作
 */
public class FileManager {

    public Map<String, FCB> InitTotalUser(){
        Map<String,FCB> totalUser=new HashMap<String,FCB>();
        FCB root=new FCB("root",0);
        totalUser.put("root",root);
        return totalUser;
    }
    public Map<String, FCB> InitTotalFiles(){
        Map<String,FCB> totalFiles=new HashMap<String,FCB>();
        return totalFiles;
    }

    ServiceDisk serviceDisk=new ServiceDisk();
    //连续组织方式,应该规整到初始化中去，暂时先放着
    //private FCB root=new FCB("root",1);//定义一个根目录root，或者说初始用户root
    //private FCB nowCatalog=root;//用于保存当前用户，强行使用单级目录实现二级目录，有时间就改，没时间算了
    //private String nowUser;


    public String createUser(String nowUser,String name,int StartNum,Map<String,FCB>totalUser){
        if(nowUser.equals("root")){
            FCB newUser=new FCB(name,StartNum);
            totalUser.put(name,newUser);
            return "over";
        }else{
            return "error";
        }
    }
    public void createFile(String nowUser,String Filename,String type,int startNum,int size,Map<String,FCB>totalUser,Map<String,FCB>totalFiles){
        FCB newFile=new FCB(Filename,type,startNum,size);
        newFile.setFather(totalUser.get(nowUser));//最后再规整到方法里面吧。。有点乱
        totalFiles.put(Filename,newFile);
    }
    public String deleteUser(String nowUser,String name,Disk[]disks,Map<String,FCB>totalFiles,Map<String,FCB>totalUser){
        if(nowUser.equals("root")){
            totalFiles.forEach((Stringkey,fcb)->{
                //删除用户目录下所有的文件,tempResult获取暂时的执行结果
                String tempResult=deleteFiles(fcb.getFather().getName(),Stringkey,disks,totalUser,totalFiles);
            });
            totalUser.remove(name);//删除MFD中的用户记录项
            return "over";
        }
        else
            return "error";
    }
    public String deleteFiles(String nowUser, String Filename, Disk[]disks,Map<String,FCB>totalUser,Map<String,FCB>totalFiles){//需要先删除在磁盘中的数据项
        FCB tmpFile=totalFiles.get(Filename);
        if(nowUser.equals(tmpFile.getFather().getName())){
            serviceDisk.deleteData(tmpFile.getStartNum(),tmpFile.getStartNum()+tmpFile.getSize(),disks);
            totalFiles.remove(Filename);
            return "over";
        }else
            return "error";
    }
}
