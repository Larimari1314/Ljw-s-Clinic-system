package sale.ljw.clinic_administrator_background_system.service.doctor.Impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
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
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.DigestUtils;
import sale.ljw.clinic_administrator_background_system.dao.doctor.DoctorInformationDao;
import sale.ljw.clinic_administrator_background_system.pojo.code.Code;
import sale.ljw.clinic_administrator_background_system.pojo.code.Message;
import sale.ljw.clinic_administrator_background_system.pojo.code.WebMessage;
import sale.ljw.clinic_administrator_background_system.pojo.doctor.Department;
import sale.ljw.clinic_administrator_background_system.pojo.doctor.Doctor_information;
import sale.ljw.clinic_administrator_background_system.service.doctor.DoctorInformationService;
import sale.ljw.clinic_administrator_background_system.utils.export.ExportDataUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.*;

@Service
public class DoctorInformationServiceImpl implements DoctorInformationService {
    @Autowired
    private DoctorInformationDao doctorDao;


    @Override
    public String findAllDoctor(Integer page) {
//        PageHelper pageHelper=new PageHelper(doctorDao.findAllDoctor());
        PageHelper.startPage(page, 20);
        ArrayList<Doctor_information> allDoctor = doctorDao.findAllDoctor();
        PageInfo pageInfo = new PageInfo(allDoctor);
        return JSON.toJSONString(pageInfo, SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String findByNameDoctor(String doctorName, Integer page) {
        doctorName = "%" + doctorName + "%";
        PageHelper.startPage(page, 20);
        ArrayList<Doctor_information> byNameDoctor = doctorDao.findByNameDoctor(doctorName);
        PageInfo pageInfo = new PageInfo(byNameDoctor);
        return JSON.toJSONString(pageInfo,SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String removeDoctorById(String id) {
        if (doctorDao.removeDoctorById(id) == 0) {
            return "error";
        } else {
            return "success";
        }
    }

    @Override
    public String batchRemoveDoctor(String ids) {
        ArrayList<String> id = new ArrayList<>(Arrays.asList(ids.split(",")));
        if (doctorDao.batchRemoveDoctor(id) == 0) {
            return "error";
        } else {
            return "success";
        }
    }

    /**
     * 添加患者
     * 操作：1、将数据库密码进行加密处理
     * 2、分配一个UUID值作为id
     * 3、若webName为空则赋值为doctorName值，
     * 4、avatarUrl(头像)设置默认地址
     * 5、时间转化成YYYY-MM-dd
     * 6、根据生日计算出年龄
     *
     * @param doctor_information
     * @return
     */
    @Override
    public String addOneDoctor(Doctor_information doctor_information) {
        //使用spring自带的MD5加密算法加密
        String newPassword = DigestUtils.md5DigestAsHex(doctor_information.getDoctorPassword().getBytes(StandardCharsets.UTF_8));
        doctor_information.setDoctorPassword(newPassword);
        //uuid
        doctor_information.setId(UUID.randomUUID().toString());

        doctor_information.setAvatarUrl("https://raw.githubusercontent.com/taylorchen709/markdown-images/master/vueadmin/user.png");
        //时间转化
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(doctor_information.getDoctorBirth());
        Date doctorBirth = null;
        try {
            doctorBirth = simpleDateFormat.parse(format);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        doctor_information.setDoctorBirth(doctorBirth);
        //计算年龄
        doctor_information.setDoctorAge(new Date().getYear() - doctorBirth.getYear());
        if (doctorDao.addOneDoctor(doctor_information) != 0) {
            return "success";
        }
        return "error";
    }

    @Override
    public String editDoctor(Doctor_information doctor_information) {
        if (doctorDao.editDoctor(doctor_information) != 0) {
            return "success";
        } else {
            return "error";
        }
    }

    /**
     * 将获取到的全部数据写入xlsx中
     *
     * @param url
     * @return
     */
    @Override
    public ByteArrayOutputStream doctorExportData(String url) {
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
            ArrayList<Doctor_information> allDoctor = doctorDao.findAllDoctor();
            //将allDoctor写入数据中
            XSSFSheet sheet = map.get(xb);
            int row = 3;
            XSSFRow row_3 = null;
            for (Doctor_information doctor_information : allDoctor) {
                row_3 = sheet.createRow(row);
                XSSFCell cell_3_1 = row_3.createCell(1);
                cell_3_1.setCellStyle(cellStyle);
                cell_3_1.setCellValue(doctor_information.getId());
                cell_3_1.setCellType(CellType.STRING);
                XSSFCell cell_3_2 = row_3.createCell(2);
                cell_3_2.setCellStyle(cellStyle);
                cell_3_2.setCellValue(doctor_information.getDoctorPhone());
                cell_3_2.setCellType(CellType.STRING);
                XSSFCell cell_3_3 = row_3.createCell(3);
                cell_3_3.setCellStyle(cellStyle);
                cell_3_3.setCellValue(doctor_information.getDoctorName());
                cell_3_3.setCellType(CellType.STRING);
                XSSFCell cell_3_4 = row_3.createCell(4);
                cell_3_4.setCellStyle(cellStyle);
                cell_3_4.setCellValue(doctor_information.getDoctorPassword());
                cell_3_4.setCellType(CellType.STRING);
                XSSFCell cell_3_5 = row_3.createCell(5);
                cell_3_5.setCellStyle(cellStyle);
                cell_3_5.setCellValue(doctor_information.getAvatarUrl());
                cell_3_5.setCellType(CellType.STRING);
                XSSFCell cell_3_6 = row_3.createCell(6);
                cell_3_6.setCellStyle(cellStyle);
                cell_3_6.setCellValue(doctor_information.getSalary());
                cell_3_6.setCellType(CellType.STRING);
                XSSFCell cell_3_7 = row_3.createCell(7);
                cell_3_7.setCellStyle(cellStyle);
                cell_3_7.setCellValue(doctor_information.getDoctorAge());
                cell_3_7.setCellType(CellType.STRING);
                XSSFCell cell_3_8 = row_3.createCell(8);
                cell_3_8.setCellStyle(cellStyle);
                cell_3_8.setCellValue(doctor_information.getDoctorSex());
                cell_3_8.setCellType(CellType.STRING);
                XSSFCell cell_3_10 = row_3.createCell(9);
                cell_3_10.setCellStyle(cellStyle);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date doctorBirth = doctor_information.getDoctorBirth();
                String format = simpleDateFormat.format(doctorBirth);
                cell_3_10.setCellValue(format);
                XSSFCell cell_3_11 = row_3.createCell(10);
                cell_3_11.setCellStyle(cellStyle);
                cell_3_11.setCellValue(doctor_information.getDoctorAddr());
                XSSFCell cell_3_12 = row_3.createCell(11);
                cell_3_12.setCellStyle(cellStyle);
                cell_3_12.setCellValue(doctor_information.getDepartment().getId());
                cell_3_12.setCellType(CellType.STRING);
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
    public ByteArrayOutputStream doctorTemplateDownload(String url) {
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

    /**
     * 1、导入报表信息至数据库
     * 2、传递是否成功参数
     *
     * @param fileName
     * @return
     */
    @Override
    public Message analyseFile(String fileName) {
        String path = System.getProperty("user.dir");
        File file = new File(path + "\\src\\main\\webapp\\static\\" + fileName + ".xlsx");
        XSSFWorkbook xssfWorkbook = null;
        try {
            xssfWorkbook = new XSSFWorkbook(file);
        } catch (IOException | InvalidFormatException e) {
            throw new RuntimeException(e);
        }
        //获取工作谱对象
        XSSFSheet sheetAt = xssfWorkbook.getSheetAt(0);
        int newRow = 3;
        ArrayList<Doctor_information> doctorInformationList = new ArrayList<>();
        while (true) {
            Doctor_information doctorInformation = new Doctor_information();
            XSSFRow new_row = sheetAt.getRow(newRow);
            try {
                String rawValue = new_row.getCell(1).getRawValue();
                if (!StringUtils.isNotBlank(rawValue) || rawValue.equals("")) {
                    break;
                }
                doctorInformation.setId(rawValue);
                doctorInformation.setDoctorPhone(new_row.getCell(2).getStringCellValue());
                doctorInformation.setDoctorName(new_row.getCell(3).getStringCellValue());
                doctorInformation.setDoctorPassword(new_row.getCell(4).getStringCellValue());
                doctorInformation.setAvatarUrl(new_row.getCell(5).getStringCellValue());
                doctorInformation.setSalary(new_row.getCell(6).getStringCellValue());
                doctorInformation.setDoctorAge(Integer.parseInt(new_row.getCell(7).getStringCellValue()));
                doctorInformation.setDoctorSex(new_row.getCell(8).getStringCellValue());
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                simpleDateFormat.parse(new_row.getCell(10).getRawValue())
                doctorInformation.setDoctorBirth(new_row.getCell(9).getDateCellValue());
                doctorInformation.setDoctorAddr(new_row.getCell(10).getStringCellValue());
                Department department = new Department();
                department.setId(new_row.getCell(11).getStringCellValue());
                doctorInformation.setDepartment(department);
                doctorInformationList.add(doctorInformation);
                newRow++;
            } catch (NullPointerException e) {
                break;
                //抱空指针异常表明数据读取完，跳出循环即可
            } catch (Exception e) {
//                e.printStackTrace();
                String hintMessage = "出现错误！错误原因可能是表格中的格式异常导致，请检查表格中的数据格式并稍后重试！！";
                return new Message(Code.known_error_code, hintMessage);
            }
        }
        //删除源文件
        if(!file.delete()){
            file.delete();
        }
        try {
            //至此我们获取完表单中的全部数据
//          System.out.println(doctorInformationList.toString());
            doctorDao.addListDoctor(doctorInformationList);
        } catch (Exception e) {
//            e.printStackTrace();
            String hintMessage = "出现错误！错误原因可能是表格中的数据已经存在，请检查表格中的数据并稍后重试";
            return new Message(Code.known_error_code, hintMessage);
        }
        String hintMessage = "导入成功~";
        return new Message(Code.success_code, "success", hintMessage);
    }

    @Override
    public String findDoctorByDepartment(String did) {
        try {
            WebMessage webMessage=new WebMessage(Code.success_code,"发送成功",doctorDao.findDoctorByDepartment(did));
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        }catch (Exception e){
            System.out.println(e.getMessage());
            WebMessage webMessage=new WebMessage("获取数据失败", "");
            return JSON.toJSONString(webMessage);
        }
    }

    @Override
    public String findByNameList(String did, String name) {
        name="%"+name+"%";
        try{
            ArrayList<Doctor_information> doctorList = doctorDao.findByNameList(did, name);
            WebMessage webMessage=new WebMessage(Code.success_code,"",doctorList);
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        }catch (Exception e){
            e.printStackTrace();
            WebMessage webMessage=new WebMessage("出现错误请稍后重试","");
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        }
    }
}
