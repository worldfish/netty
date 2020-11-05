package com.example.client.demo.net;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ClientStartThread extends Thread {
    protected final Logger logger = LoggerFactory.getLogger(this.getClass());

    private String ip = "172.20.1.206";
    private int port = 6668;

    public ClientStartThread(String ip,int port){
        this.ip = ip;
        this.port = port;
    }

    @Override
    public void run() {
        logger.info("主备通信连接 目标地址:"+this.ip+":"+this.port + " 正在连接。。。");
        try {
            //new Client.connectServer();
        } catch (Exception e) {
            logger.error("主备通信连接 启动异常："+e.getStackTrace().toString());
            e.printStackTrace();
        }
    }

}
