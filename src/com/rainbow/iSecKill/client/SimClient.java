package com.rainbow.iSecKill.client;

import org.apache.log4j.Logger;

import com.rainbow.iSecKill.net.ApacheHttpInvoker;
import com.rainbow.iSecKill.net.Taobao;

public class SimClient {
	private static final Logger logger = Logger.getLogger(SimClient.class);
	
	ApacheHttpInvoker invoker = new ApacheHttpInvoker();
	
	public static void main(String[] args) {
		Taobao taobao = new Taobao("luckyboy1102", "asdf");
		System.out.println(taobao.login());
	}
}
