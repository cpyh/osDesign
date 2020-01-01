package Test;

import com.cpyh.Catalogue.FCB;
import com.cpyh.Catalogue.FileManager;
import com.cpyh.Disk.Disk;
import com.cpyh.Disk.FreeDiskTable;
import com.cpyh.Disk.ServiceDisk;
import com.cpyh.Disk.ServiceDiskTable;
import com.cpyh.Memory.MmDisk;
import com.cpyh.Memory.ServiceMemory;
import com.cpyh.Thread.ExecuteThread;
import org.junit.Test;

import java.util.Map;

public class TestExecuteThread {
    private FileManager fileManager = new FileManager();
    private ServiceDiskTable serviceDiskTable=new ServiceDiskTable();
    private ServiceMemory serviceMemory=new ServiceMemory();
    private ServiceDisk serviceDisk=new ServiceDisk();
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
    private Map<Integer, FreeDiskTable>SwapDiskTable(){
        Map<Integer, FreeDiskTable> SwapDiskTable=serviceDiskTable.initSwapDisk();
        return SwapDiskTable;
    }
    //文件区空闲盘表
    private Map<Integer,FreeDiskTable>UserDiskTable(){
        Map<Integer, FreeDiskTable> UserDiskTable=serviceDiskTable.initUserDisk();
        return UserDiskTable;
    }
    @Test
    public void TestExecuteThread(){
        MmDisk[]mmdisks=serviceMemory.initMemory();
        Disk [] disks=initDisk();

        //new ExecuteThread(0,mmdisks,disks,"Test2",totalFiles);
    }
}
