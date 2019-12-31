package com.cpyh;

import com.cpyh.Disk.Disk;

public class Main {

    public static void main(String[] args) {
	Disk disk0=new Disk();
	char []data=new char[4];
	data="abcd".toCharArray();
	disk0.setData(data);
        System.out.println(disk0.getData());
    }
}
