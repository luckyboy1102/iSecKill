package com.rainbow.iSecKill.client;

import com.rainbow.iSecKill.net.RequestSender;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.nodes.Element;

import java.util.HashMap;
import java.util.Map;

public class SimClient {

	public static void main(String[] args) {
        String result = RequestSender.execute("http://210.28.81.11/zf/", null);
    }

}
