package com.example.server.demo;

import com.example.server.demo.net.Server;
import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@MapperScan("com.example.server.demo.protocol.mapper")
//@ComponentScan
public class ServerApplication {

	public static void main(String[] args) throws InterruptedException {
		SpringApplication.run(ServerApplication.class, args);
		Server server = new Server();
		server.connect();
	}

}
