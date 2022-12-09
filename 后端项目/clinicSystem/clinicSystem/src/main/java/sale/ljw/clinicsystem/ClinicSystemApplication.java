package sale.ljw.clinicsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.PropertySource;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("sale.ljw.clinicsystem.backend.dao")
@PropertySource(value={"classpath:/token.properties"})
//开启定时任务
@EnableScheduling
public class ClinicSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicSystemApplication.class, args);
    }

}
