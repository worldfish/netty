package com.example.client.demo;

import com.example.client.demo.net.Client;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ClientApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ClientApplication.class, args);
		Client client = new Client();
		client.connectServer();
	}

}
