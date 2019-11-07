package com.smallrain.wechat;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.servlet.ServletComponentScan;

@SpringBootApplication
@ServletComponentScan
public class SmallrainWechatApplication {

	public static void main(String[] args) {
		SpringApplication.run(SmallrainWechatApplication.class, args);
	}
}
