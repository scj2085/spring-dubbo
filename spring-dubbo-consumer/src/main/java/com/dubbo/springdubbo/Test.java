package com.dubbo.springdubbo;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.stereotype.Service;

import com.dubbo.springdubbo.PermissionService;

@Service
public class Test implements InitializingBean{

	@Autowired
	private PermissionService permissionService;
	
	public void afterPropertiesSet() throws Exception {
		/**
		 * 客户端并发启动两个线程，检测第二个线程执行过程是否还能访问到第一个线程访问到的provider。
		 * 如果 第二个线程访问返回No available Provider
		 * 异常信息，则代表provider已经下线..实现了consumer端的优雅停机
		 */
		Thread firstCallThread = new Thread(new Runnable() {
			public void run() {
				firstCall();
			}
		});
		firstCallThread.start();
		// 暂停1秒钟，看还能不能访问到原来ip的服务者
		Thread.sleep(1000);
		Thread secondCallThread = new Thread(new Runnable() {
			public void run() {
				secondCall();
			}
		});
		secondCallThread.start();
		
	}
	
	// 第一次调用
		public void firstCall() {
			System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " first call  start ");
			String response = permissionService.find();
			System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " first call response ======================================: "
					+ response);
		}

		// 第二次调用
		public void secondCall() {
			System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " second call  start ");
			String response = null;
			try {
				response = permissionService.find();
			} catch (Exception e) {
				System.out.println("异常信息:" + e.getMessage());
				response = " 第二次访问异常 ,异常信息：" + e.getMessage();
			}
			System.out.println(new SimpleDateFormat("[yyyy-MM-dd HH:mm:ss]").format(new Date()) + " second call response : "
					+ response);
		}

		public void setProviderDemoService(PermissionService permissionService) {
			this.permissionService = permissionService;
		}
}
