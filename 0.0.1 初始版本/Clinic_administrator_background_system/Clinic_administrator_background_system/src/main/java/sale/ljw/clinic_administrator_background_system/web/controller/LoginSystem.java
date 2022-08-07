package sale.ljw.clinic_administrator_background_system.web.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.AdminLogin;
import sale.ljw.clinic_administrator_background_system.service.login.AdminLoginService;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

@Controller
@RequestMapping("/login")
public class LoginSystem {
    @Autowired
    private AdminLoginService adminLoginService;

/*    @PostMapping("/addAdmin")
    public String addAdmin(AdminLogin adminLogin) {
        return adminLoginService.addAmin(adminLogin);
    }*/

    @GetMapping("/getImgUrl")
    @ResponseBody
    public String getImgUrl(String adminUserName) {
        return adminLoginService.getImgUrl(adminUserName);
    }

    @PostMapping("/adminLogin")
    @ResponseBody
    public String adminLogin(@RequestBody AdminLogin adminLogin, HttpServletResponse resp, HttpServletRequest req) {
        return adminLoginService.adminLogin(adminLogin, resp, req);
    }

/*    @RequestMapping ("/insufficientPermissions")
    public String insufficientPermissions() {
        System.out.println(111111);
        return "login.html";
    }*/
    @GetMapping("/loginOut")
    public void loginOut(HttpServletResponse response,HttpServletRequest request){
        Cookie cookie=new Cookie("_web_admin", "");
        cookie.setPath("/");
        response.addCookie(cookie);
    }
}
