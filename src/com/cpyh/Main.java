package com.cpyh;

import com.cpyh.Catalogue.FCB;
import com.cpyh.Catalogue.FileManager;
import com.cpyh.Disk.Disk;
import com.cpyh.Disk.FreeDiskTable;
import com.cpyh.Disk.ServiceDisk;
import com.cpyh.Disk.ServiceDiskTable;
import com.cpyh.Memory.MmDisk;
import com.cpyh.Memory.ServiceMemory;
import com.cpyh.Service.Login;
import com.cpyh.Service.Show;
import com.cpyh.Thread.DeleteDataThread;
import com.cpyh.Thread.ExecuteThread;
import com.cpyh.Thread.ThreadunLock;
import com.cpyh.Thread.writeDataThread;
import org.junit.Test;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Scanner;
import java.util.WeakHashMap;


public class Main {
	public static void main(String[] args) throws IOException {
		ServiceDisk serviceDisk = new ServiceDisk();
		ServiceDiskTable serviceDiskTable=new ServiceDiskTable();
		ServiceMemory serviceMemory=new ServiceMemory();
		FileManager fileManager=new FileManager();
		Show show = new Show();
		ThreadunLock threadunLock = new ThreadunLock();

		Login login = new Login("root");
		System.out.println("请等待初始化");
		Disk[] disks=serviceDisk.InitDisk();
		System.out.println("\t磁盘初始化完毕");
		Map<Integer, FreeDiskTable> SwapDiskTable=serviceDiskTable.initSwapDisk();
		Map<Integer,FreeDiskTable> UserDiskTable=serviceDiskTable.initUserDisk();
		System.out.println("\t磁盘空闲分区表初始化完毕");
		MmDisk[]mmdisks=serviceMemory.initMemory();
		System.out.println("\t内存初始化完毕");
		Map<String,FCB>totalUser=fileManager.InitTotalUser();
		Map<String,FCB>totalFiles=fileManager.InitTotalFiles();
		System.out.println("\t目录初始化完成");
		System.out.println("初始化完成");
		System.out.println("欢迎使用虚拟操作系统||"+"默认登录用户："+login.getNowUser());
		//初始化一个全局变量用来保存开出来的线程
		Map<Integer,String>ThreadMap=new HashMap<>();

		Scanner scanner0 = new Scanner(System.in);
		Scanner scanner1 = new Scanner(System.in);
		Scanner scanner2 = new Scanner(System.in);
		Scanner scanner3 = new Scanner(System.in);
		int cnt=1;
		while(true) {
			System.out.println("请选择操作：\n\t1-数据生成线程\n\t2-执行线程\n\t3-数据删除线程\n\t4-查看磁盘数据\n\t5-切换用户");
			System.out.printf(login.getNowUser()+"/>");
			int operation=scanner0.nextInt();
			switch (operation) {
				case 1:
					System.out.println("\t您正在使用数据生成线程");
					System.out.println("\t当前线程id为" + cnt);
					System.out.println("\t请输入要创建的文件大小（字节）");
					System.out.printf(login.getNowUser()+"/>");
					int size = scanner1.nextInt();
					System.out.println("\t请输入要创建的文件的具体数据（字母串）");
					System.out.printf(login.getNowUser()+"/>");
					String Data = scanner2.nextLine();
					System.out.println("\t请输入要创建的文件的文件名");
					System.out.printf(login.getNowUser()+"/>");
					String Filename = scanner3.nextLine();
					writeDataThread tmpwriteDataThread= new writeDataThread(cnt,disks,login.getNowUser(),size,Data,Filename,totalUser,totalFiles,UserDiskTable);
					tmpwriteDataThread.run();
					//ThreadMap=threadunLock.ThreadAdd(cnt,tmpwriteDataThread.getThread_name(),ThreadMap);
					cnt++;
					break;
				case 2:
					System.out.println("\t您正在使用执行线程");
					show.showFilesofnowUser(login.getNowUser(),totalFiles);
					System.out.println("请选择要调入内存的文件");
					System.out.printf(login.getNowUser()+"/>");
					String filename = scanner2.nextLine();
					System.out.println("\t您选择的文件是"+filename);
					ExecuteThread tmpThread = new ExecuteThread(cnt,mmdisks,disks,filename,totalFiles,ThreadMap);
					tmpThread.run();
					ThreadMap=threadunLock.ThreadAdd(cnt,tmpThread.getThread_name(),ThreadMap);
					cnt++;
					break;
				case 3:
					System.out.println("\t您正在使用数据删除线程");
					show.showFilesofnowUser(login.getNowUser(),totalFiles);
					System.out.println("\t请选择要删除的文件");
					System.out.printf(login.getNowUser()+"/>");
					String Delfilename = scanner3.nextLine();
					System.out.println("\t您选择的文件是"+Delfilename);
					DeleteDataThread tmpDThread = new DeleteDataThread(cnt,disks,login.getNowUser(),Delfilename,totalUser,totalFiles,UserDiskTable);
					tmpDThread.run();
					break;
				case 4:
					show.showDisk(disks);
					break;
				case 5:
					String newUser;
					show.showUser(totalUser);
					System.out.println("\t请输入目标用户");
					System.out.printf(login.getNowUser()+"/>");
					newUser=scanner2.nextLine();
					if(!totalUser.containsKey(newUser)) {
						System.out.println("\t该用户不存在,是否创建新用户,yes/no");
						System.out.printf(login.getNowUser()+"/>");
						String flag = scanner2.nextLine();
						if (flag.equals("yes")) {
							String Username;
							Username = newUser;
							fileManager.createUser("root", Username, 0, totalUser);
							login.changeUser(Username,totalUser);
						}else{
							System.out.println("\t切换用户失败");
						}
					}else{
						login.changeUser(newUser,totalUser);
					}
					break;
				default:
					System.out.println("\t无该选项，请重新输入");
			}
		}
	}
}
