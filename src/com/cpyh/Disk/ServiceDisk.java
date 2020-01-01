package com.cpyh.Disk;

import java.io.*;

public class ServiceDisk {
    //磁盘块分配，这个我猜应该是放到文件系统里面
    //读数据操作

    //初始化
    public Disk[] InitDisk()throws IOException{
        //需要创建一个4KB的txt文件，用于保存磁盘数据。
        ServiceDisk.createTxtFile("OsDisk");
        // 导入磁盘数据。这里要和Filesystem配合一下，晚点再做
        //需要考虑哪些部分是目录数据，哪些部分是文件数据，或者可以考虑文件数据不写到磁盘
        Disk [] disks=new Disk[1024];//一共定义1024个磁盘块
        for(int i=0;i<disks.length;i++){
            disks[i]=new Disk();
        }
        return disks;
        //问题：如何用一个4KB的文件来保存磁盘信息？ 方案：建立一个文件保存和读取
    }
    /**
     *
     * @param startNum
     * @param endNum
     * @param disks 磁盘数据应该设计成一个全局变量，直接在main函数里面去做，或者初始化
     * @return
     */
    public String getData(int startNum, int endNum, Disk []disks){//预计提供给读数据线程
        String str="";
        for(int i = startNum;i<=endNum;i++){
            str+=disks[i].getData();
        }
        return str;
    }
    //删除数据
    public void deleteData(int startNum,int endNum,Disk[]disks){
        for(int i=startNum;i<endNum;i++){
            disks[i].setData(null);
            disks[i].setUsed(false);
        }
    }

    //磁盘数据保存到txt文件，这一步要实现实时感觉有点困难。。要不直接在系统停止的时候一次性全写？
    public void strToTxt(Disk []disks){
        try {
            OutputStream out=new FileOutputStream("D:/OsDisk.txt",true);
            for(Disk disk:disks){
                out.write(disk.getData().getBytes());
                out.write('\r');
                out.write('\n');
            }
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    //创建磁盘txt文件
    public static boolean createTxtFile(String name) throws IOException{
        boolean flag =false;
        String filenameTemp="D:/"+name+".txt";
        File filename=new File(filenameTemp);
        if(!filename.exists()){
            filename.createNewFile();
            flag=true;
        }
        return flag;
    }

    //创建文件的时候使用，将传入的数据，保存在模拟的磁盘块数组中,用户区
    public void writeData(int StartNum,String Data,Disk[]disks){
        String tmpStr="";
        int cnt=0;
        int tmpdiskNum=StartNum;
        for(int i=0;i<Data.length();i++){
            char tmp=Data.charAt(i);
            tmpStr+=tmp;
            cnt++;
            if(cnt==4){
                disks[tmpdiskNum].setData(tmpStr);
                disks[tmpdiskNum].setUsed(true);
                tmpdiskNum++;
                tmpStr="";
                cnt=0;
            }
        }
        if(cnt>0){
            disks[tmpdiskNum].setData(tmpStr);
            disks[tmpdiskNum].setUsed(true);
        }
    }

    //展示磁盘信息
    public void showDisk(Disk[]disks){
        for(int i=0;i<32;i++){
            for(int j=0;j<32;j++){
                if(disks[i*32+j].isUsed())
                    System.out.printf(""+disks[i*32+j].getData()+" ");
                else
                    System.out.printf("####"+" ");
            }
            System.out.println();
        }
    }
}
