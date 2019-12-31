package com.cpyh.Disk;

import java.util.HashMap;
import java.util.Map;

public class ServiceDiskTable {
    public Map<Integer,FreeDiskTable> initSwapDisk(){
        Map<Integer,FreeDiskTable> SwapDiskTable= new HashMap<Integer, FreeDiskTable>();
        FreeDiskTable SwapDisk=new FreeDiskTable(0,124,0);
        FreeDiskTable UserDisk=new FreeDiskTable(124,900,124);
        SwapDiskTable.put(0,SwapDisk);
        return SwapDiskTable;
    }
    public Map<Integer,FreeDiskTable> initUserDisk(){
        Map<Integer,FreeDiskTable> UserDiskTable= new HashMap<Integer, FreeDiskTable>();
        FreeDiskTable UserDisk=new FreeDiskTable(0,900,0);
        UserDiskTable.put(0,UserDisk);
        return UserDiskTable;
    }

    //分配
    //回收

}
