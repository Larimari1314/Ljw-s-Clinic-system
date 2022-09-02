package sale.ljw.clinicsystem.backend.service.personnel.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import org.apache.commons.lang3.StringUtils;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.CellType;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.BeanUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.dao.order.ReserveMapper;
import sale.ljw.clinicsystem.backend.dao.personnel.PatientinformationMapper;
import sale.ljw.clinicsystem.backend.dao.personnel.PatientloginMapper;
import sale.ljw.clinicsystem.backend.form.personnel.admin.AddPatientInformationByAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.admin.DeleteIdsBYAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.admin.EditPatientInformation;
import sale.ljw.clinicsystem.backend.form.personnel.admin.FindPatientByAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.patient.EditPatientInformationByPatient;
import sale.ljw.clinicsystem.backend.form.personnel.patient.RegisterAccountPatient;
import sale.ljw.clinicsystem.backend.pojo.order.Reserve;
import sale.ljw.clinicsystem.backend.pojo.personnel.Doctorinformation;
import sale.ljw.clinicsystem.backend.pojo.personnel.Patientinformation;
import sale.ljw.clinicsystem.backend.pojo.personnel.Patientlogin;
import sale.ljw.clinicsystem.backend.service.personnel.PatientinformationService;
import sale.ljw.clinicsystem.backend.service.personnel.PatientloginService;
import sale.ljw.clinicsystem.common.http.ResponseResult;
import sale.ljw.clinicsystem.common.sercurity.utils.export.ExportDataUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
 * @author 86155
 * @description 针对表【patientInformation】的数据库操作Service实现
 * @createDate 2022-08-07 16:02:23
 */
@Service
public class PatientinformationServiceImpl extends ServiceImpl<PatientinformationMapper, Patientinformation>
        implements PatientinformationService {
    @Autowired
    private PatientinformationMapper patientinformationMapper;
    @Autowired
    private PatientloginMapper patientloginMapper;
    @Autowired
    private PatientloginService patientloginService;

    @Autowired
    private ReserveMapper reserveMapper;

    @Override
    public String findAllPatient(FindPatientByAdmin patient) {
        PageHelper.startPage(patient.getPage(), 20);
        ArrayList<Map<String, Object>> allDoctor = patientinformationMapper.findAllPatient(patient);
        PageInfo pageInfo = new PageInfo(allDoctor);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo), SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String editPatient(EditPatientInformation patientinformation, MultipartFile multipartFileUpload) {
        QueryWrapper<Patientinformation> queryWrapperName = new QueryWrapper<>();
        queryWrapperName.eq("name", patientinformation.getName());
        //当存在名称数据
        Map<String, Object> map = getMap(queryWrapperName);
        if (map != null && !map.get("id").equals(patientinformation.getId())) {
            //返回相同名称的错误码 403
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        if (multipartFileUpload != null) {
            //判断用户头像是否上传
            String path = System.getProperty("user.dir");
            File newFile = new File(path + "\\src\\main\\webapp\\Img\\patient\\" + patientinformation.getId());
            newFile.mkdirs();
            File files = new File(newFile + "\\" + patientinformation.getId() + ".jpg");
            try {
                multipartFileUpload.transferTo(files);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            patientinformation.setAvatar("http://localhost:8000/clinic/Img/patient/" + patientinformation.getId() + "/" + patientinformation.getId() + ".jpg");
        }
        UpdateWrapper<Patientinformation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", patientinformation.getId()).
                set("address", patientinformation.getAddress()).
                set("avatar", patientinformation.getAvatar()).
                set("birthday", patientinformation.getBirthdayCoding()).
                set("name", patientinformation.getName()).
                set("age", new Date().getYear() - patientinformation.getBirthdayCoding().getYear()).
                set("sex", patientinformation.getSexCoding());
        boolean update = update(updateWrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Transactional
    @Override
    public String addPatientInformation(AddPatientInformationByAdmin patientInformation) {
        //首先判断新增数据是否有相似数据
        QueryWrapper<Patientinformation> queryWrapperNumber = new QueryWrapper<>();
        queryWrapperNumber.eq("identityNumber", patientInformation.getIdentityNumber());
        //当存在数据
        if (getMap(queryWrapperNumber) != null) {
            //返回相同身份证号的错误码 402
            return JSON.toJSONString(ResponseResult.getErrorResult("C402"));
        }
        QueryWrapper<Patientinformation> queryWrapperName = new QueryWrapper<>();
        queryWrapperName.eq("name", patientInformation.getName());
        //当存在名称数据
        if (getMap(queryWrapperName) != null) {
            //返回相同名称的错误码 403
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        //数据通过验证，此时添加数据到数据库
        //添加患者信息，首先添加登陆表中
        //为用户创建id
        String doctorId = UUID.randomUUID().toString();
        //存储到登陆表
        int insert = patientloginMapper.insert(new Patientlogin(doctorId, patientInformation.getName(), null, null, DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)), 3));
        if (insert != 0) {
            //登陆表中存在数据，添加数据到患者信息表中
            Patientinformation patientinformation = new Patientinformation();
            BeanUtils.copyProperties(patientInformation, patientinformation);
            patientinformation.setIdentitynumber(patientInformation.getIdentityNumber());
            patientinformation.setId(doctorId);
            patientinformation.setAge(new Date().getYear() - patientInformation.getBirthday().getYear());
            boolean save = save(patientinformation);
            if (save) {
                return JSON.toJSONString(ResponseResult.getSuccessResult("C200"));
            }
        }
        return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
    }

    @Override
    public String deleteByIdsPatientInformation(DeleteIdsBYAdmin ids) {
        //根据id删除首先判断预约表中是否存在相关数据
        ArrayList<Map<String, Object>> reserves = reserveMapper.findByPatientIds(ids.getIds());
        if (reserves.size() != 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C402"));
        }
        //预约表中不存在患者，执行删除患者
        //逻辑删除顺序：医生信息表——登录表权限关闭
        //逻辑删除删除信息表即可，将权限改为0
        int patientInformationResult = patientinformationMapper.deleteBatchIds(ids.getIds());
        boolean result = true;
        if (patientInformationResult != 0) {
            //修改登录状态
            for (String id : ids.getIds()) {
                int update = patientloginMapper.updateByIdToPermission(id);
                if (update == 0) {
                    result = false;
                }
            }
        }
        if (result) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C200"));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public ByteArrayOutputStream patientTemplateDownload(HttpServletResponse response) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = new String((UUID.randomUUID().toString() + ".xlsx").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        //设置文件名
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //获取产生相应的流对象
        String url = System.getProperty("user.dir") + "\\src\\main\\java\\sale\\ljw\\clinicsystem\\backend\\pojo\\personnel\\Patientinformation.java";
        ByteArrayOutputStream ios = new ByteArrayOutputStream();
        try {
            Map<XSSFWorkbook, XSSFSheet> map = ExportDataUtils.excelTemplate(url);
            Set<XSSFWorkbook> xssfWorkbooks = map.keySet();
            XSSFWorkbook xb = null;
            for (XSSFWorkbook xssfWorkbook : xssfWorkbooks) {
                xb = xssfWorkbook;
            }
            CellStyle cellStyle = xb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            //将allDoctor写入数据中
            XSSFSheet sheet = map.get(xb);
            xb.write(ios);
            xb.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ios;
    }

    @Override
    public ByteArrayOutputStream exportData(HttpServletResponse response) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = new String((UUID.randomUUID().toString() + ".xlsx").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        //设置文件名
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //获取产生相应的流对象
        String url = System.getProperty("user.dir") + "\\src\\main\\java\\sale\\ljw\\clinicsystem\\backend\\pojo\\personnel\\Patientinformation.java";
        ByteArrayOutputStream ios = new ByteArrayOutputStream();
        try {
            Map<XSSFWorkbook, XSSFSheet> map = ExportDataUtils.excelTemplate(url);
            Set<XSSFWorkbook> xssfWorkbooks = map.keySet();
            XSSFWorkbook xb = null;
            for (XSSFWorkbook xssfWorkbook : xssfWorkbooks) {
                xb = xssfWorkbook;
            }
            CellStyle cellStyle = xb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            List<Patientinformation> patientinformation = list();
            //将allDoctor写入数据中
            XSSFSheet sheet = map.get(xb);
            int row = 3;
            XSSFRow row_3 = null;
            for (Patientinformation patient : patientinformation) {
                row_3 = sheet.createRow(row);
                XSSFCell cell_3_1 = row_3.createCell(1);
                cell_3_1.setCellStyle(cellStyle);
                cell_3_1.setCellValue(patient.getId());
                cell_3_1.setCellType(CellType.STRING);
                XSSFCell cell_3_2 = row_3.createCell(2);
                cell_3_2.setCellStyle(cellStyle);
                cell_3_2.setCellValue(patient.getAvatar());
                cell_3_2.setCellType(CellType.STRING);
                XSSFCell cell_3_3 = row_3.createCell(3);
                cell_3_3.setCellStyle(cellStyle);
                cell_3_3.setCellValue(patient.getName());
                cell_3_3.setCellType(CellType.STRING);
                XSSFCell cell_3_4 = row_3.createCell(4);
                cell_3_4.setCellStyle(cellStyle);
                cell_3_4.setCellValue(patient.getAge());
                cell_3_4.setCellType(CellType.STRING);
                XSSFCell cell_3_5 = row_3.createCell(5);
                cell_3_5.setCellStyle(cellStyle);
                cell_3_5.setCellValue(patient.getBirthday());
                cell_3_5.setCellType(CellType.STRING);
                XSSFCell cell_3_6 = row_3.createCell(6);
                cell_3_6.setCellStyle(cellStyle);
                cell_3_6.setCellValue(patient.getSex());
                cell_3_6.setCellType(CellType.STRING);
                XSSFCell cell_3_7 = row_3.createCell(7);
                cell_3_7.setCellStyle(cellStyle);
                cell_3_7.setCellValue(patient.getIdentitynumber());
                cell_3_7.setCellType(CellType.STRING);
                XSSFCell cell_3_8 = row_3.createCell(8);
                cell_3_8.setCellStyle(cellStyle);
                cell_3_8.setCellValue(patient.getAddress());
                cell_3_8.setCellType(CellType.STRING);
                XSSFCell cell_3_9 = row_3.createCell(9);
                cell_3_9.setCellStyle(cellStyle);
                cell_3_9.setCellValue(patient.getDoctordelete());
                cell_3_9.setCellType(CellType.STRING);
                row++;
            }
            xb.write(ios);
            xb.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return ios;
    }

    @Override
    public String analyseFile(MultipartFile multipartFileUpload) {
        String path = System.getProperty("user.dir");
        String fileName = UUID.randomUUID().toString();
        File newFile = new File(path + "\\src\\main\\webapp\\static\\" + fileName + ".xlsx");
        try {
            multipartFileUpload.transferTo(newFile);
        } catch (IOException e) {
            throw new RuntimeException(e);
        } catch (NullPointerException e) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(newFile);
        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        }
        //获取工作谱对象
        XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
        int newRow = 3;
        ArrayList<Patientinformation> patientinformations = new ArrayList<>();
        ArrayList<Patientlogin> patientlogins = new ArrayList<>();
        while (true) {
            Patientinformation patientinformation = new Patientinformation();
            Patientlogin patientlogin = new Patientlogin();
            XSSFRow new_row = sheetAt.getRow(newRow);
            try {
                new_row.getCell(1).setCellType(CellType.STRING);
                String rawValue = new_row.getCell(1).getStringCellValue();
                if (!StringUtils.isNotBlank(rawValue) || rawValue.equals("")) {
                    break;
                }
                patientinformation.setId(rawValue);
                patientlogin.setId(rawValue);
                patientlogin.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)));
                new_row.getCell(2).setCellType(CellType.STRING);
                patientinformation.setAvatar(new_row.getCell(2).getStringCellValue());
                new_row.getCell(3).setCellType(CellType.STRING);
                patientinformation.setName(new_row.getCell(3).getStringCellValue());
                patientlogin.setLoginname(new_row.getCell(3).getStringCellValue());
                patientlogin.setPermission(2);
                new_row.getCell(4).setCellType(CellType.STRING);
                patientinformation.setAge(Integer.parseInt(new_row.getCell(4).getStringCellValue()));
                patientinformation.setBirthday(new_row.getCell(5).getDateCellValue());
                new_row.getCell(6).setCellType(CellType.STRING);
                patientinformation.setSex(new_row.getCell(6).getStringCellValue());
                new_row.getCell(7).setCellType(CellType.STRING);
                patientinformation.setIdentitynumber(new_row.getCell(7).getStringCellValue());
                new_row.getCell(8).setCellType(CellType.STRING);
                patientinformation.setAddress(new_row.getCell(8).getStringCellValue());
                new_row.getCell(9).setCellType(CellType.STRING);
                patientinformation.setDoctordelete(Integer.parseInt(new_row.getCell(9).getStringCellValue()));
                patientinformations.add(patientinformation);
                patientlogins.add(patientlogin);
                newRow++;
            } catch (NullPointerException e) {
                break;
                //抱空指针异常表明数据读取完，跳出循环即可
            } catch (Exception e) {
                e.printStackTrace();
                return JSON.toJSONString(ResponseResult.getErrorResult("C500"));
            } finally {
                //删除源文件
                if (!newFile.delete()) {
                    newFile.delete();
                }
            }
        }
        try {
            //保存数据
            boolean result = patientloginService.saveBatch(patientlogins);
            if (result) {
                saveBatch(patientinformations);
            } else {
                return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
            }
        } catch (Exception e) {
            e.printStackTrace();
            //原因分析为数据可能已存在
            return JSON.toJSONString(ResponseResult.getErrorResult("C501"));
        }
        //上述没报错误就是导入成功了
        return JSON.toJSONString(ResponseResult.getSuccessResult(null, "C200", null));
    }

    @Override
    public String getDeleteData() {
        ArrayList<Map<String, Object>> deleteData = patientinformationMapper.getDeleteData();
        return JSON.toJSONString(ResponseResult.getSuccessResult(deleteData));
    }

    @Override
    public String deleteById(String id) {
        //在预约表中查找患者是否存在
        QueryWrapper<Reserve> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("patientId", id);
        if (reserveMapper.selectList(queryWrapper).size() != 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        //删除用户信息，并删除头像文件
        Patientinformation patientinformation = patientinformationMapper.selectDeleteDataById(id);
        if (!patientinformation.getAvatar().contains("default")) {
            String path = System.getProperty("user.dir");
            File newFile = new File(path + "\\src\\main\\webapp\\Img\\patient\\" + id + "\\" + id + ".jpg");
            //删除不了也没啥关系，不得影响主任务的执行，只好转化脏数据，所以不对结果进行判定
            newFile.delete();
        }
        if (patientinformationMapper.permanentDeleteById(id) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }
        //删除用户注册信息
        if (patientloginMapper.deleteById(id) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null));
    }

    @Override
    public String recoveryData(String id) {
        if (patientinformationMapper.recoveryData(id) == 0) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null));
    }

    @Override
    public String editPatientByPatient(EditPatientInformationByPatient patientInformation, MultipartFile multipartFile) {
        QueryWrapper<Patientinformation> queryWrapperName = new QueryWrapper<>();
        queryWrapperName.eq("name", patientInformation.getName());
        //当存在名称数据
        Map<String, Object> map = getMap(queryWrapperName);
        if (map != null && !map.get("id").equals(patientInformation.getId())) {
            //返回相同名称的错误码 403
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        if (multipartFile != null) {
            //判断用户头像是否上传
            String path = System.getProperty("user.dir");
            File newFile = new File(path + "\\src\\main\\webapp\\Img\\patient\\" + patientInformation.getId());
            newFile.mkdirs();
            File files = new File(newFile + "\\" + patientInformation.getId() + ".jpg");
            try {
                multipartFile.transferTo(files);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            patientInformation.setAvatar("http://localhost:8000/clinic/Img/patient/" + patientInformation.getId() + "/" + patientInformation.getId() + ".jpg");
        }
        UpdateWrapper<Patientinformation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", patientInformation.getId()).
                set("address", patientInformation.getAddress()).
                set("avatar", patientInformation.getAvatar()).
                set("birthday", patientInformation.getBirthday()).
                set("name", patientInformation.getName()).
                set("age", new Date().getYear() - patientInformation.getBirthday().getYear()).
                set("sex", patientInformation.getSex());
        boolean update = update(updateWrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }
    @Transactional
    @Override
    public String registerAccountByPatient(RegisterAccountPatient patient) {
        //判断登录名是否重复
        QueryWrapper<Patientlogin> queryWrapperLoginName=new QueryWrapper<>();
        queryWrapperLoginName.eq("loginName",patient.getLoginname());
        List<Patientlogin> patientloginsName = patientloginMapper.selectList(queryWrapperLoginName);
        if(patientloginsName.size()!=0){
            return JSON.toJSONString(ResponseResult.getErrorResult("C403-1"));
        }
        //判断邮箱是否重复
        QueryWrapper<Patientlogin> queryWrapperLoginEmail=new QueryWrapper<>();
        queryWrapperLoginEmail.eq("loginEmail",patient.getLoginemail());
        List<Patientlogin> patientloginsEmail = patientloginMapper.selectList(queryWrapperLoginEmail);
        if(patientloginsEmail.size()!=0){
            return JSON.toJSONString(ResponseResult.getErrorResult("C403-2"));
        }
        //判断电话是否重复
        QueryWrapper<Patientlogin> queryWrapperPhone=new QueryWrapper<>();
        queryWrapperPhone.eq("loginPhone",patient.getLoginphone());
        List<Patientlogin> patientloginsPhone = patientloginMapper.selectList(queryWrapperPhone);
        if(patientloginsPhone.size()!=0){
            return JSON.toJSONString(ResponseResult.getErrorResult("C403-3"));
        }
        //判断用户名是否重复
        QueryWrapper<Patientinformation> queryWrapperUsername=new QueryWrapper<>();
        queryWrapperUsername.eq("name",patient.getName());
        List<Patientinformation> patientloginsUsername = patientinformationMapper.selectList(queryWrapperUsername);
        if(patientloginsUsername.size()!=0){
            return JSON.toJSONString(ResponseResult.getErrorResult("C403-4"));
        }
        //存储用户登录信息
        Patientlogin patientlogin=new Patientlogin();
        //设置id
        String resultId = UUID.randomUUID().toString();
        patientlogin.setId(resultId);
        //设置权限 3
        patientlogin.setPermission(3);
        BeanUtils.copyProperties(patient,patientlogin);
        patientlogin.setPassword(DigestUtils.md5DigestAsHex(patient.getPassword().getBytes(StandardCharsets.UTF_8)));
        int insert = patientloginMapper.insert(patientlogin);
        if(insert==0){
            return JSON.toJSONString(ResponseResult.getErrorResult("C405-1"));
        }
        //存储用户基本信息
        Patientinformation patientinformation=new Patientinformation();
        patientinformation.setId(resultId);
        patientinformation.setAge(new Date().getYear()-patient.getBirthday().getYear());
        BeanUtils.copyProperties(patient,patientinformation);
        int insert1 = patientinformationMapper.insert(patientinformation);
        if(insert1==0){
            return JSON.toJSONString(ResponseResult.getErrorResult("C405-2"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null));
    }

}




