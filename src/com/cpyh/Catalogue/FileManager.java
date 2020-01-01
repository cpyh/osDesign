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
    public Map<String,FCB> totalUser=new HashMap<String , FCB>();//晚点调整到全局变量
    public Map<String,FCB> totalFiles=new HashMap<String,FCB>();//晚点调整到全局变量
    ServiceDisk serviceDisk=new ServiceDisk();
    //连续组织方式,应该规整到初始化中去，暂时先放着
    private FCB root=new FCB("root",1);//定义一个根目录root，或者说初始用户root
    private FCB nowCatalog=root;//用于保存当前用户，强行使用单级目录实现二级目录，有时间就改，没时间算了
    private String nowUser;


    private String createUser(String nowUser,String name,int StartNum){
        if(nowUser.equals("root")){
            FCB newUser=new FCB(name,StartNum);
            totalUser.put(name,newUser);
            return "over";
        }else{
            return "error";
        }
    }
    public void createFile(String nowUser,String Filename,String type,int startNum,int size){
        FCB newFile=new FCB(Filename,type,startNum,size);
        newFile.setFather(totalUser.get(nowUser));//最后再规整到方法里面吧。。有点乱
        totalFiles.put(Filename,newFile);
    }
    private String deleteUser(String nowUser,String name,Disk[]disks){
        if(nowUser.equals("root")){
            totalFiles.forEach((Stringkey,FCB)->{
                //删除用户目录下所有的文件,tempResult获取暂时的执行结果
                String tempResult=deleteFiles(FCB.getFather().getName(),Stringkey,disks);
            });
            totalUser.remove(name);//删除MFD中的用户记录项
            return "over";
        }
        else
            return "error";
    }
    private String deleteFiles(String theUserName, String Filename, Disk[]disks){//需要先删除在磁盘中的数据项
        FCB tmpFile=totalFiles.get(Filename);
        if(nowUser.equals(tmpFile.getFather().getName())){
            serviceDisk.deleteData(tmpFile.getStartNum(),tmpFile.getStartNum()+tmpFile.getSize(),disks);
            totalFiles.remove(Filename);
            return "over";
        }else
            return "error";
    }
}
