package com.example.server.demo;

import com.example.server.demo.net.Server;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class ServerApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ServerApplication.class, args);
		Server server = new Server();
		server.connect();
	}

}
