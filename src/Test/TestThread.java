package Test;

import com.cpyh.Catalogue.FCB;
import com.cpyh.Catalogue.FileManager;
import com.cpyh.Disk.Disk;
import com.cpyh.Disk.FreeDiskTable;
import com.cpyh.Disk.ServiceDisk;
import com.cpyh.Disk.ServiceDiskTable;
import com.cpyh.Memory.MmDisk;
import com.cpyh.Memory.ServiceMemory;
import com.cpyh.Service.Login;
import com.cpyh.Thread.DeleteDataThread;
import com.cpyh.Thread.ExecuteThread;
import com.cpyh.Thread.writeDataThread;
import org.junit.Test;

import java.io.File;
import java.util.Map;

public class TestThread {
    FileManager fileManager=new FileManager();
    ServiceDiskTable serviceDiskTable=new ServiceDiskTable();
    ServiceDisk serviceDisk=new ServiceDisk();
    ServiceMemory serviceMemory = new ServiceMemory();
    //初始化磁盘块
    private Disk[] initDisk(){
        Disk[] disks=new Disk[1024];
        for(int i=0;i<1024;i++){
            disks[i]=new Disk();
        }
        return disks;
    }
    //初始化内存

    //用户目录
    private Map<String, FCB> totalUser(){
        Map<String, FCB> totalUser=fileManager.InitTotalUser();
        return totalUser;
    }
    //文件目录
    private Map<String, FCB> totalFiles(){
        Map<String, FCB> totalFiles=fileManager.InitTotalFiles();
        return totalFiles;
    }
    //交换空闲盘表
    private Map<Integer,FreeDiskTable>SwapDiskTable(){
        Map<Integer, FreeDiskTable> SwapDiskTable=serviceDiskTable.initSwapDisk();
        return SwapDiskTable;
    }
    //文件区空闲盘表
    private Map<Integer,FreeDiskTable>UserDiskTable(){
        Map<Integer, FreeDiskTable> UserDiskTable=serviceDiskTable.initUserDisk();
        return UserDiskTable;
    }

    @Test
    public void TestThread(){
        Disk[]disks=initDisk();
        Map<String,FCB>totalUser=totalUser();
        Map<String,FCB>totalFiles=totalFiles();
        Map<Integer,FreeDiskTable>SwapDiskTable=SwapDiskTable();
        Map<Integer,FreeDiskTable>UserDiskTable=UserDiskTable();
        String nowUser=new Login("root").getNowUser();
        writeDataThread testThread1=new writeDataThread(0,disks,nowUser,2,"abcdefgh","Test1",totalUser,totalFiles,UserDiskTable);
        testThread1.run();

        writeDataThread testThread2=new writeDataThread(0,disks,nowUser,2,"abcdefgh","Test2",totalUser,totalFiles,UserDiskTable);
        testThread2.run();
        writeDataThread testThread3=new writeDataThread(0,disks,nowUser,2,"abcdefgh","Test3",totalUser,totalFiles,UserDiskTable);
        testThread3.run();

        DeleteDataThread testdelThread1=new DeleteDataThread(0,disks,nowUser,"Test2",totalUser,totalFiles,UserDiskTable);
        testdelThread1.run();
        UserDiskTable=testdelThread1.getDiskTables();
//        UserDiskTable=serviceDiskTable.recovery("Test2",totalFiles,UserDiskTable);
//        fileManager.deleteFiles(nowUser,"Test2",disks,totalUser,totalFiles);
        UserDiskTable=serviceDiskTable.recovery("Test3",totalFiles,UserDiskTable);
        fileManager.deleteFiles(nowUser,"Test3",disks,totalUser,totalFiles);
        //fileManager.deleteFiles(nowUser,"Test2",disks,totalUser,totalFiles);
        //查看用户表
        totalUser.forEach((stringkey,valueFCB)->{
            System.out.println(stringkey+"   "+valueFCB.toString());
        });
        //查看文件表
        System.out.println(totalFiles.size());
        totalFiles.forEach((stringkey,valueFCB)->{
            System.out.println(stringkey+" "+valueFCB.toString());
        });
        //查看空闲表
        UserDiskTable.forEach((Integerkey,freeDiskTable)->{
            int endNum=freeDiskTable.getStartNum()+freeDiskTable.getSize();
            System.out.println(
                    Integerkey
                            +":"
                            +freeDiskTable.getStartNum()
                            +"=========="+endNum);
        });
        //测试执行进程
        MmDisk[]mmdisks=serviceMemory.initMemory();
        new ExecuteThread(1,mmdisks,disks,"Test1",totalFiles).run();
        //查看内存状态
        //查看磁盘数据
        serviceDisk.showDisk(disks);
    }
}
