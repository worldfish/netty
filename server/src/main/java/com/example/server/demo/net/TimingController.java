package com.example.server.demo.net;

import org.springframework.scheduling.annotation.Scheduled;

public class TimingController {


    @Scheduled(cron = "0/1 * * * * ? ")
    public void send(){

    }



}
