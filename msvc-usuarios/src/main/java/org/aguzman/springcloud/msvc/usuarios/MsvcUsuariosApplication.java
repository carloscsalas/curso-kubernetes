package org.aguzman.springcloud.msvc.usuarios;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**Para habilitar la implementacion de spring cloud starter kubernetes*/
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication
public class MsvcUsuariosApplication {

	public static void main(String[] args) {
		SpringApplication.run(MsvcUsuariosApplication.class, args);
	}

}
