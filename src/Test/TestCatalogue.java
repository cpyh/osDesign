package Test;

import com.cpyh.Catalogue.FCB;
import com.cpyh.Catalogue.FileManager;
import com.cpyh.Disk.Disk;
import com.cpyh.Disk.FreeDiskTable;
import com.cpyh.Disk.ServiceDiskTable;
import org.junit.Test;

import java.nio.file.attribute.UserDefinedFileAttributeView;
import java.util.Map;

public class TestCatalogue {
    FileManager fileManager=new FileManager();
    ServiceDiskTable serviceDiskTable=new ServiceDiskTable();
    private Disk[] initDisk(){
        //初始化磁盘块
        Disk[] disks=new Disk[1024];
        for(int i=0;i<1024;i++){
            disks[i]=new Disk();
        }
        return disks;
    }

    @Test//测试目录初始化功能
    public void TestFilesystem(){
        Disk[]disks=initDisk();

        Map<String, FCB> totalUser=fileManager.InitTotalUser();
        Map<String,FCB> totalFiles=fileManager.InitTotalFiles();
        totalUser.forEach((stringkey,valueFCB)->{
            System.out.println(stringkey+"   "+valueFCB.toString());
        });
    }

    @Test//测试创建文件和删除文件功能
    public void TestCreateFile(){
        Disk[]disks=initDisk();
        Map<String, FCB> totalUser=fileManager.InitTotalUser();
        Map<String,FCB> totalFiles=fileManager.InitTotalFiles();
        String nowUser="root";
        fileManager.createFile(nowUser,"test1","文件",10,20,totalUser,totalFiles);
        System.out.println(totalFiles.size());
        totalFiles.forEach((stringkey,valueFCB)->{
            System.out.println(stringkey+" "+valueFCB.toString());
        });
        fileManager.deleteFiles(nowUser,"test1",disks,totalUser,totalFiles);
        System.out.println(totalFiles.size());
        totalFiles.forEach((stringkey,valueFCB)->{
            System.out.println(stringkey+" "+valueFCB.toString());
        });
    }

    //测试创建用户和删除用户功能
    @Test
    public void TestcreateUser(){
        Disk[]disks=initDisk();
        Map<String, FCB> totalUser=fileManager.InitTotalUser();
        Map<String,FCB> totalFiles=fileManager.InitTotalFiles();
        Map<Integer, FreeDiskTable>UserDiskTable=serviceDiskTable.initUserDisk();
        String nowUser="root";
        fileManager.createUser(nowUser,"TestUser",2,totalUser);
        System.out.println(totalUser.size());
        totalUser.forEach((stringkey,valueFCB)->{
            System.out.println(stringkey+"   "+valueFCB.toString());
        });
        fileManager.deleteUser(nowUser,"TestUser",disks,totalFiles,totalUser, UserDiskTable);
        System.out.println(totalUser.size());
        totalUser.forEach((stringkey,valueFCB)->{
            System.out.println(stringkey+"   "+valueFCB.toString());
        });
    }
}
