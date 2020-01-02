package com.cpyh.Thread;

import com.cpyh.Memory.MmDisk;
import com.cpyh.Memory.ServiceMemory;

import java.util.*;

public class ThreadunLock {
    //不太会线程池的东西，还是手动模拟一下子这样子
    public Map<Integer,String> ThreadAdd(int id,String name,Map<Integer,String>ThreadMap){
        ThreadMap.put(id,name);
        return ThreadMap;
    }
    public Map<Integer,String> ThreadOver(int id, Map<Integer,String>ThreadMap, MmDisk[]mmdisks){
        ServiceMemory serviceMemory= new ServiceMemory();
        ThreadMap.remove(id);
        serviceMemory.reMemory(id,mmdisks);
        return ThreadMap;
    }
    public Map<Integer,String> CheckThreadMapSize(int id,Map<Integer,String>ThreadMap,MmDisk[]mmdisks){
        if(ThreadMap.size()==3){
            //ThreadOver(id-2,ThreadMap,mmdisks);
        }
        return ThreadMap;
    }
}
