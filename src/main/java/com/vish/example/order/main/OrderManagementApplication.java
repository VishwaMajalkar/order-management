package com.vish.example.order.main;

import com.vish.example.order.beans.OrderBook;
import com.vish.example.order.config.ForceShutdown;
import org.apache.geode.cache.client.ClientRegionShortcut;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.web.embedded.tomcat.TomcatServletWebServerFactory;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.data.gemfire.config.annotation.ClientCacheApplication;
import org.springframework.data.gemfire.config.annotation.EnableEntityDefinedRegions;
import org.springframework.data.gemfire.repository.config.EnableGemfireRepositories;

/**
 * This Class is Main for this application
 * Created by Vishwanath on 16/03/2019.
 */
@SpringBootApplication(scanBasePackages = {"com.vish.example"})
@EnableGemfireRepositories(basePackages = {"com.vish.example.order.repository"})
@ClientCacheApplication
@EnableEntityDefinedRegions(basePackageClasses = OrderBook.class, clientRegionShortcut = ClientRegionShortcut.LOCAL)
public class OrderManagementApplication {

	private static final Logger log = LoggerFactory.getLogger(OrderManagementApplication.class);

	public static void main(String[] args) {
		SpringApplication.run(OrderManagementApplication.class, args);
		log.info("Application Started Successfully");
	}

	/**
	 * This Bean is required for shutdown hooks
	 * @return
	 */
	@Bean
	public ConfigurableServletWebServerFactory webServerFactory() {
		TomcatServletWebServerFactory factory = new TomcatServletWebServerFactory();
		factory.addConnectorCustomizers(new ForceShutdown());
		return factory;
	}
}
