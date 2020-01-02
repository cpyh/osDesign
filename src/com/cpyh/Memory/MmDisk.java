package com.cpyh.Memory;

import com.cpyh.Disk.Disk;

public class MmDisk {//一共16个MemoryDisk
    private static int id=-1;
    private String Data;
    private String theFileName;
    private int FileDiskId;
    private int id_Thread;
    private int time;


    @Override
    public String toString() {
        return "MmDisk{" +
                "Data='" + Data + '\'' +
                ", theFileName='" + theFileName + '\'' +
                ", FileDiskId=" + FileDiskId +
                ", id_Thread=" + id_Thread +
                ", time=" + time +
                '}';
    }

    public MmDisk(){
        id++;
        this.time=-1;
        this.id_Thread=0;
    }
    public String getTheFileName() {
        return theFileName;
    }

    public int getId_Thread() {
        return id_Thread;
    }

    public void setId_Thread(int id_Thread) {
        this.id_Thread = id_Thread;
    }

    public void setTheFileName(String theFileName) {
        this.theFileName = theFileName;
    }

    public int getFileDiskId() {
        return FileDiskId;
    }

    public void setFileDiskId(int fileDiskId) {
        FileDiskId = fileDiskId;
    }

    public int getId() {
        return id;
    }

    public String getData() {
        return Data;
    }

    public void setData(String data) {
        Data = data;
    }

    public int getTime() {
        return time;
    }

    public void setTime(int time) {
        this.time = time;
    }
}
