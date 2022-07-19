package sale.ljw.clinic_administrator_background_system.service.patient.Impl;

import com.alibaba.fastjson.JSON;
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
import sale.ljw.clinic_administrator_background_system.dao.patient.PatientInformationDao;
import sale.ljw.clinic_administrator_background_system.pojo.code.Code;
import sale.ljw.clinic_administrator_background_system.pojo.code.Message;
import sale.ljw.clinic_administrator_background_system.pojo.patient.Patient_information;
import sale.ljw.clinic_administrator_background_system.service.patient.PatientInformationService;
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
public class PatientInformationServiceImpl implements PatientInformationService {
    @Autowired
    private PatientInformationDao patientDao;


    @Override
    public String findAllPatient(Integer page) {
//        PageHelper pageHelper=new PageHelper(patientDao.findAllPatient());
        PageHelper.startPage(page, 20);
        ArrayList<Patient_information> allPatient = patientDao.findAllPatient();
        PageInfo pageInfo = new PageInfo(allPatient);
        return JSON.toJSONString(pageInfo);
    }

    @Override
    public String findByNamePatient(String patientName, Integer page) {
        patientName = "%" + patientName + "%";
        PageHelper.startPage(page, 20);
        ArrayList<Patient_information> byNamePatient = patientDao.findByNamePatient(patientName);
        PageInfo pageInfo = new PageInfo(byNamePatient);
        return JSON.toJSONString(pageInfo);
    }

    @Override
    public String removePatientById(String id) {
        if (patientDao.removePatientById(id) == 0) {
            return "error";
        } else {
            return "success";
        }
    }

    @Override
    public String batchRemovePatient(String ids) {
        ArrayList<String> id = new ArrayList<>(Arrays.asList(ids.split(",")));
        if (patientDao.batchRemovePatient(id) == 0) {
            return "error";
        } else {
            return "success";
        }
    }

    /**
     * 添加患者
     * 操作：1、将数据库密码进行加密处理
     * 2、分配一个UUID值作为id
     * 3、若webName为空则赋值为patientName值，
     * 4、avatarUrl(头像)设置默认地址
     * 5、时间转化成YYYY-MM-dd
     * 6、根据生日计算出年龄
     *
     * @param patient_information
     * @return
     */
    @Override
    public String addOnePatient(Patient_information patient_information) {
        //使用spring自带的MD5加密算法加密
        String newPassword = DigestUtils.md5DigestAsHex(patient_information.getPassword().getBytes(StandardCharsets.UTF_8));
        patient_information.setPassword(newPassword);
        //uuid
        patient_information.setId(UUID.randomUUID().toString());
        //判断是否为空
        if (StringUtils.isNoneBlank(patient_information.getWebName())) {
            patient_information.setWebName(patient_information.getPatientName());
        }
        patient_information.setAvatarUrl("http://localhost:8080/img/avatar/default.jpg");
        //时间转化
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        String format = simpleDateFormat.format(patient_information.getPatientBirth());
        Date patientBirth = null;
        try {
            patientBirth = simpleDateFormat.parse(format);
        } catch (ParseException e) {
            throw new RuntimeException(e);
        }
        patient_information.setPatientBirth(patientBirth);
        //计算年龄
        patient_information.setPatientAge(new Date().getYear() - patientBirth.getYear());
        if (patientDao.addOnePatient(patient_information) != 0) {
            return "success";
        }
        return "error";
    }

    @Override
    public String editPatient(Patient_information patient_information) {
        if (patientDao.editPatient(patient_information) != 0) {
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
    public ByteArrayOutputStream patientExportData(String url) {
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
            ArrayList<Patient_information> allPatient = patientDao.findAllPatient();
            //将allPatient写入数据中
            XSSFSheet sheet = map.get(xb);
            int row = 3;
            XSSFRow row_3 = null;
            for (Patient_information patient_information : allPatient) {
                row_3 = sheet.createRow(row);
                XSSFCell cell_3_1 = row_3.createCell(1);
                cell_3_1.setCellStyle(cellStyle);
                cell_3_1.setCellValue(patient_information.getId());
                cell_3_1.setCellType(CellType.STRING);
                XSSFCell cell_3_2 = row_3.createCell(2);
                cell_3_2.setCellStyle(cellStyle);
                cell_3_2.setCellValue(patient_information.getPatientName());
                cell_3_2.setCellType(CellType.STRING);
                XSSFCell cell_3_3 = row_3.createCell(3);
                cell_3_3.setCellStyle(cellStyle);
                cell_3_3.setCellValue(patient_information.getWebName());
                cell_3_3.setCellType(CellType.STRING);
                ;
                XSSFCell cell_3_4 = row_3.createCell(4);
                cell_3_4.setCellStyle(cellStyle);
                cell_3_4.setCellValue(patient_information.getPatientPhone());
                cell_3_4.setCellType(CellType.STRING);
                XSSFCell cell_3_5 = row_3.createCell(5);
                cell_3_5.setCellStyle(cellStyle);
                cell_3_5.setCellValue(patient_information.getPatientEmail());
                cell_3_5.setCellType(CellType.STRING);
                XSSFCell cell_3_6 = row_3.createCell(6);
                cell_3_6.setCellStyle(cellStyle);
                cell_3_6.setCellValue(patient_information.getIdentityNumber());
                cell_3_6.setCellType(CellType.STRING);
                XSSFCell cell_3_7 = row_3.createCell(7);
                cell_3_7.setCellStyle(cellStyle);
                cell_3_7.setCellValue(patient_information.getPassword());
                cell_3_7.setCellType(CellType.STRING);
                XSSFCell cell_3_8 = row_3.createCell(8);
                cell_3_8.setCellStyle(cellStyle);
                cell_3_8.setCellValue(patient_information.getAvatarUrl());
                cell_3_8.setCellType(CellType.STRING);
                XSSFCell cell_3_9 = row_3.createCell(9);
                cell_3_9.setCellStyle(cellStyle);
                cell_3_9.setCellValue(patient_information.getPatientSex());
                cell_3_9.setCellType(CellType.STRING);
                XSSFCell cell_3_10 = row_3.createCell(10);
                cell_3_10.setCellStyle(cellStyle);
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
                Date patientBirth = patient_information.getPatientBirth();
                String format = simpleDateFormat.format(patientBirth);
                cell_3_10.setCellValue(format);
                XSSFCell cell_3_11 = row_3.createCell(11);
                cell_3_11.setCellStyle(cellStyle);
                cell_3_11.setCellValue(patient_information.getPatientAddr());
                cell_3_11.setCellType(CellType.STRING);
                XSSFCell cell_3_12 = row_3.createCell(12);
                cell_3_12.setCellStyle(cellStyle);
                cell_3_12.setCellValue(patient_information.getPatientAge());
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
    public ByteArrayOutputStream patientTemplateDownload(String url) {
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
            //将allPatient写入数据中
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
        ArrayList<Patient_information> patientInformationList = new ArrayList<>();
        while (true) {
            Patient_information patientInformation = new Patient_information();
            XSSFRow new_row = sheetAt.getRow(newRow);
            try {
                String rawValue = new_row.getCell(1).getRawValue();
                if (!StringUtils.isNotBlank(rawValue) || rawValue.equals("")) {
                    break;
                }
                patientInformation.setId(rawValue);
                patientInformation.setPatientName(new_row.getCell(2).getStringCellValue());
                patientInformation.setWebName(new_row.getCell(3).getStringCellValue());
                patientInformation.setPatientPhone(new_row.getCell(4).getStringCellValue());
                patientInformation.setPatientEmail(new_row.getCell(5).getStringCellValue());
                patientInformation.setIdentityNumber(new_row.getCell(6).getStringCellValue());
                patientInformation.setPassword(new_row.getCell(7).getStringCellValue());
                patientInformation.setAvatarUrl(new_row.getCell(8).getStringCellValue());
                patientInformation.setPatientSex(Integer.parseInt(new_row.getCell(9).getStringCellValue()));
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
//                simpleDateFormat.parse(new_row.getCell(10).getRawValue())
                patientInformation.setPatientBirth(new_row.getCell(10).getDateCellValue());
                patientInformation.setPatientAddr(new_row.getCell(11).getStringCellValue());
                patientInformation.setPatientAge(Integer.parseInt(new_row.getCell(12).getStringCellValue()));
                patientInformationList.add(patientInformation);
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
        file.delete();
        try {
            //至此我们获取完表单中的全部数据
//          System.out.println(patientInformationList.toString());
            patientDao.addListPatient(patientInformationList);
        } catch (Exception e) {
//            e.printStackTrace();
            String hintMessage = "出现错误！错误原因可能是表格中的数据已经存在，请检查表格中的数据并稍后重试";
            return new Message(Code.known_error_code, hintMessage);
        }
        String hintMessage = "导入成功~";
        return new Message(Code.success_code, "success", hintMessage);
    }
}
