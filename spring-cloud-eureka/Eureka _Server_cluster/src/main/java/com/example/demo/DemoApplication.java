package com.example.demo;

import javafx.application.Application;
import org.apache.commons.logging.Log;
import org.apache.commons.logging.LogFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.web.servlet.support.SpringBootServletInitializer;
import org.springframework.cloud.netflix.eureka.server.EnableEurekaServer;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.core.env.Environment;

import java.util.Arrays;

@EnableEurekaServer
@SpringBootApplication
public class DemoApplication extends SpringBootServletInitializer {

    @Autowired
    private Environment env;

    private final Log logger = LogFactory.getLog(this.getClass());

    @Override
    protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
        return application.sources(Application.class);
    }


    public static void main(String[] args) {
        SpringApplication.run(DemoApplication.class, args);
    }

    @Bean
    public CommandLineRunner commandLineRunner(ApplicationContext ctx) {
        return args -> {

            logger.debug("Let's inspect the beans provided by Spring Boot:");

            String[] beanNames = ctx.getBeanDefinitionNames();
            Arrays.sort(beanNames);
            for (String beanName : beanNames) {
                logger.debug(beanName);
            }

            logger.info(String.format(
                    "\n----------------------------------------------------------\n\t"
                            + "Application '%s' is running! Access URLs:\n\t" + "Local: \t\thttp://localhost:%s\n\t"
                            + "The following profiles are active: %s\n"
                            + "----------------------------------------------------------",
                    env.getProperty("spring.application.name"), env.getProperty("server.port"),
                    env.getProperty("spring.profiles.active")));
        };
    }

}
