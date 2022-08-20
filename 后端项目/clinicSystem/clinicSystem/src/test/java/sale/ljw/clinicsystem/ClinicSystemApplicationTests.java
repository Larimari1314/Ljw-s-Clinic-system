package sale.ljw.clinicsystem;

import io.lettuce.core.ScriptOutputType;
import org.junit.jupiter.api.Test;
import org.springframework.boot.test.context.SpringBootTest;

@SpringBootTest
class ClinicSystemApplicationTests {

    @Test
    void contextLoads() {
        String servletPath="/DEDEDED/DEDEDED/DEDEEDE";
        servletPath=servletPath.substring(0,servletPath.lastIndexOf('/'));
        System.out.println(servletPath);
    }

}
