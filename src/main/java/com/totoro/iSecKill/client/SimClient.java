package com.totoro.iSecKill.client;

import com.totoro.iSecKill.net.CreateOrder;

public class SimClient {

	public static void main(String[] args) {
        new Thread(new CreateOrder("0060000001845")).start();
    }
}
