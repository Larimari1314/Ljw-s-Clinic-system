package sale.ljw.clinic_administrator_background_system;

import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.format.annotation.DateTimeFormat;
import sale.ljw.clinic_administrator_background_system.pojo.patient.Patient_information;
import sale.ljw.clinic_administrator_background_system.service.clinic.Impl.AppointmentTimeServiceImpl;
import sale.ljw.clinic_administrator_background_system.utils.export.ExportDataUtils;
import sale.ljw.clinic_administrator_background_system.utils.javaDocUtils.Doclet;
import sale.ljw.clinic_administrator_background_system.utils.javaDocUtils.FildEntry;
import sale.ljw.clinic_administrator_background_system.utils.javaDocUtils.ModelClassDocVO;
import sale.ljw.clinic_administrator_background_system.utils.timeUtils.TimeFormatSeparate;

import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.*;

@SpringBootTest
class ClinicAdministratorBackgroundSystemApplicationTests {

    @Test
    void contextLoads() throws ParseException {
        DateFormat simpleDateFormat = new SimpleDateFormat("yyyy-MM-dd");
        Date parse = simpleDateFormat.parse("2022-2-3");
        System.out.println(parse.toString());
    }

    @Test
    public void POITestWrite() throws IOException {
        //1、获取到对应的excel工作文件
        XSSFWorkbook xb = new XSSFWorkbook();
        //2、创建工作表
        XSSFSheet sheet = xb.createSheet();
        //写入数据
        //3、创建工作表中的行
        XSSFRow row = sheet.createRow(1);
        //4、根据行创建列
        XSSFCell cell = row.createCell(1);
        //在列中写数据
        cell.setCellValue("测试一下单元格");
        //创建一个文件对象
        File file = new File("test.xlsx");
        OutputStream os = new FileOutputStream(file);
        xb.write(os);
        xb.close();
        os.close();
    }

    @Test
    public void POITestRead() throws IOException {
        //1、获取需要读取的文件工作蒲对象
        XSSFWorkbook wb = new XSSFWorkbook("test.xlsx");
        //2、获取工作表
        Sheet sheetAt = wb.getSheetAt(0);
        //3、获取行
        Row row = sheetAt.getRow(1);
        //4、获取列
        Cell cell = row.getCell(1);
        //5、根据数据类型获取数据
        String data = cell.getStringCellValue();
        System.out.println(data);
        wb.close();
    }

    @Test
    public void POIProjectTest() throws IOException {
        XSSFWorkbook xb = new XSSFWorkbook();
        XSSFSheet sheet = xb.createSheet();
        sheet.setColumnWidth(4, 100);
        //合并单元格
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, 12));
        XSSFRow row_1 = sheet.createRow(1);
        XSSFCell cell_1_11 = row_1.createCell(1);
        CellStyle cellStyle = xb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cell_1_11.setCellStyle(cellStyle);
        cell_1_11.setCellValue("在线患者导出信息");
        //转化成byte数据
        ByteArrayOutputStream os = new ByteArrayOutputStream();
        xb.write(os);
        xb.close();
//        return os;
        HttpServletResponse response=null;
        //设置对外输出类型-为文件xlsx类型
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        //获取产生相应的流对象
        ServletOutputStream outputStream = response.getOutputStream();
        //将数据从原始字节流对象提取出来写入到servlet对应的输出流中
        os.writeTo(outputStream);
        outputStream.flush();
    }

    @Test
    public void TestClass() {
        Field[] declaredFields = Patient_information.class.getDeclaredFields();
        for (Field declaredField : declaredFields) {
            System.out.println(declaredField.getName());
        }
    }

    @Test
    public void javaDocTest() throws IOException, InvalidFormatException {
        String path = System.getProperty("user.dir");
        File file = new File(path + "\\src\\main\\webapp\\static\\test.xlsx");
        XSSFWorkbook xssfWorkbook = new XSSFWorkbook(file);
        OutputStream os=new FileOutputStream(file);
        xssfWorkbook.write(os);
        os.close();
        xssfWorkbook.close();
    }
    @Test
    public void className() throws IOException {
      /*  ArrayList<Patient_information> list=new ArrayList<>();
        ExportDataUtils exportDataUtils=new ExportDataUtils();
        XSSFWorkbook sheets = exportDataUtils.excelTemplate("F:\\GraduationProject\\Backstage\\Clinic_administrator_background_system\\Clinic_administrator_background_system\\src\\main\\java\\sale\\ljw\\clinic_administrator_background_system\\pojo\\patient\\Patient_information.java");
        File file=new File("test.xlsx");
        OutputStream os=new FileOutputStream(file);
        sheets.write(os);
        os.close();
        sheets.close();*/
        try {
            Map<XSSFWorkbook, XSSFSheet> map = ExportDataUtils.excelTemplate("F:\\GraduationProject\\Backstage\\Clinic_administrator_background_system\\Clinic_administrator_background_system\\src\\main\\java\\sale\\ljw\\clinic_administrator_background_system\\pojo\\patient\\Patient_information.java");
            Set<XSSFWorkbook> xssfWorkbooks = map.keySet();
            XSSFWorkbook xb = null;
            for (XSSFWorkbook xssfWorkbook : xssfWorkbooks) {
                xb=xssfWorkbook;
            }
            CellStyle cellStyle = xb.createCellStyle();
            cellStyle.setAlignment(HorizontalAlignment.CENTER);
            cellStyle.setBorderBottom(BorderStyle.THIN);
            cellStyle.setBorderLeft(BorderStyle.THIN);
            cellStyle.setBorderRight(BorderStyle.THIN);
            cellStyle.setBorderTop(BorderStyle.THIN);
            ArrayList<Patient_information> list=new ArrayList<>();
            Patient_information pat=new Patient_information();
            pat.setId("111");
            pat.setAvatarUrl("2323");
            list.add(pat);
            //将allPatient写入数据中
            XSSFSheet sheet = map.get(xb);
            int row = 3;
            XSSFRow row_3 = null;
            for (Patient_information patient_information : list) {
                row_3 = sheet.createRow(3);
                row_3.createCell(1).setCellValue(patient_information.getId());
                row_3.createCell(2).setCellValue(patient_information.getPatientName());
                row_3.createCell(3).setCellValue(patient_information.getWebName());
                row_3.createCell(4).setCellValue(patient_information.getPatientPhone());
                row_3.createCell(5).setCellValue(patient_information.getPatientEmail());
                row_3.createCell(6).setCellValue(patient_information.getIdentityNumber());
                row_3.createCell(7).setCellValue(patient_information.getPassword());
                row_3.createCell(8).setCellValue(patient_information.getAvatarUrl());
                row_3.createCell(9).setCellValue(patient_information.getPatientSex());
                row_3.createCell(10).setCellValue(patient_information.getPatientBirth());
                row_3.createCell(11).setCellValue(patient_information.getPatientAddr());
                row_3.createCell(12).setCellValue(patient_information.getPatientAge());
                row++;
            }
            File file=new File("test.xlsx");
            OutputStream os=new FileOutputStream(file);
            xb.write(os);
            os.close();
            xb.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
    @Test
    public void SimpleDateFormatTest() throws ParseException {
        SimpleDateFormat simpleDateFormat=new SimpleDateFormat("yyyy-MM-dd");
        simpleDateFormat.parse("1355");
    }

    @Test
    public void TimeString() throws ParseException {
        /*String s="8:00";
        String a="19:00";
        int z=30;
        List<String> intervalTimeList = TimeFormatSeparate.getIntervalTimeList(s, a, z);
        System.out.println(intervalTimeList.toString());*/
        AppointmentTimeServiceImpl appointmentTimeService=new AppointmentTimeServiceImpl();
        System.out.println(appointmentTimeService.findAllTime());
    }

    @Autowired
    private RedisTemplate redisTemplate;

    @Test
    public void redisText() {
        redisTemplate.boundValueOps("d85e7a75-0712-46aa-babc-e4ffdc174705").set("shanghai");
        System.out.println((String) redisTemplate.boundValueOps("d85e7a75-0712-46aa-babc-e4ffdc174705").get());
    }
}
