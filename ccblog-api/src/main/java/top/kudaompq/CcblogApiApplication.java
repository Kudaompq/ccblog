package top.kudaompq;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@EnableTransactionManagement
@SpringBootApplication
public class CcblogApiApplication {

    public static void main(String[] args) {
        SpringApplication.run(CcblogApiApplication.class, args);
    }

}
