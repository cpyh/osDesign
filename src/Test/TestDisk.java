package Test;

import com.cpyh.Disk.Disk;
import com.cpyh.Disk.FreeDiskTable;
import com.cpyh.Disk.ServiceDisk;
import com.cpyh.Disk.ServiceDiskTable;
import com.sun.glass.ui.Size;
import org.junit.Test;

import java.util.Map;

public class TestDisk {
    ServiceDisk serviceDisk=new ServiceDisk();
    ServiceDiskTable serviceDiskTable=new ServiceDiskTable();
    private Disk[] initDisk(){
        //初始化磁盘块
        Disk[] disks=new Disk[1024];
        for(int i=0;i<1024;i++){
            disks[i]=new Disk();
        }
        return disks;
    }
    @Test
    public void TestshowDisk(){
        Disk []disks=initDisk();
        serviceDisk.showDisk(disks);
    }
    @Test
    public void TestDiskTable(){
        Map<Integer, FreeDiskTable> SwapDiskTable=serviceDiskTable.initSwapDisk();
        SwapDiskTable.forEach((Integerkey,freeDiskTable)->{
            int endNum=freeDiskTable.getStartNum()+freeDiskTable.getSize();
            System.out.println(
                    Integerkey
                            +":"
                            +freeDiskTable.getStartNum()
                            +"=========="+endNum);
        });
    }

    @Test
    public void TestwriteDisk(){
        Disk[]disks=initDisk();
        serviceDisk.writeData(0,"abcdefg",disks);
        for(int i=0;i<10;i++){
            System.out.println(disks[i].getData()+"\t"+disks[i].isUsed());
        }
    }
}
