package sale.ljw.clinicsystem;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.transaction.annotation.EnableTransactionManagement;

@SpringBootApplication
@EnableTransactionManagement
@MapperScan("sale.ljw.clinicsystem.backend.dao")
public class ClinicSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(ClinicSystemApplication.class, args);
    }

}
