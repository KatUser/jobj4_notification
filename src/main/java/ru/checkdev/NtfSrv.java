package ru.checkdev;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.ApplicationPidFileWriter;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;

/**
 * Hello world!
 */
@SpringBootApplication
@EnableJpaRepositories(basePackages = "ru.checkdev.notification.repository")
public class NtfSrv {
    public static void main(String[] args) {
        SpringApplication application = new SpringApplication(NtfSrv.class);
        application.addListeners(new ApplicationPidFileWriter("./notification.pid"));
        application.run();
    }
}
