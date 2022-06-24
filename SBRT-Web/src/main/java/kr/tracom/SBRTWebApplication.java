package kr.tracom;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.scheduling.annotation.EnableScheduling;

@SpringBootApplication
@EnableDiscoveryClient
@EnableScheduling
public class SBRTWebApplication {

	public static void main(String[] args) {
		//SpringApplication.run(SBRTWebApplication.class, args);
		
        SpringApplication application = new SpringApplication(SBRTWebApplication.class);
        application.addListeners(new ApplicationPidFileWriter());
        application.run(args);
		
	}
}
