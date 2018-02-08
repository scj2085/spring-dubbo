package com.dubbo.springdubbo.provider;

import java.text.SimpleDateFormat;

import java.util.Date;

import com.dubbo.springdubbo.PermissionService;



public class PermissionServiceImpl implements PermissionService{

	public String find() {
		System.err.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " method is called.");
		Thread exitsThread = new Thread(new Runnable() {
			public void run() {
				System.err.println("111-3" + Thread.currentThread().getName());
				System.exit(0);// 此命令相当于 kill pid,当调用System.exit(0)时，杀死了整个进程，这时候活动所占的资源也会被释放
			}
		});
		System.err.println("111-1" + Thread.currentThread().getName());
		System.err.println("111-2" + exitsThread.currentThread().getName());
		exitsThread.start();
		System.err.println("222" + exitsThread.currentThread().getName());
		try {
			Thread.sleep(5000);// 模拟一个执行时间较长的过程，dubbo默认停机等待10秒
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		System.err.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " thread has weakup!!! ");
		System.err.println("333" + exitsThread.currentThread().getName());
		System.err.println("333" + Thread.currentThread().getName());
		return " hello Word ";
	}

}
