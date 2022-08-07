package sale.ljw.clinic_administrator_background_system.service.clinic.Impl;

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
import sale.ljw.clinic_administrator_background_system.dao.clinic.DrugInformationDao;
import sale.ljw.clinic_administrator_background_system.pojo.clinic.Drug_information;
import sale.ljw.clinic_administrator_background_system.pojo.code.Code;
import sale.ljw.clinic_administrator_background_system.pojo.code.Message;
import sale.ljw.clinic_administrator_background_system.pojo.code.WebMessage;
import sale.ljw.clinic_administrator_background_system.service.clinic.DrugInformationService;
import sale.ljw.clinic_administrator_background_system.utils.export.ExportDataUtils;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Map;
import java.util.Set;

@Service
public class DrugInformationServiceImpl implements DrugInformationService {
    @Autowired
    private DrugInformationDao drugInformationDao;

    @Override
    public String findAllDrug(Integer page) {
        try {
            PageHelper.startPage(page, 3);
            ArrayList<Drug_information> allDrug = drugInformationDao.findAllDrug();
            PageInfo pageInfo = new PageInfo(allDrug);
            WebMessage webMessage = new WebMessage(Code.success_code, "", pageInfo);
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception e) {
            WebMessage webMessage = new WebMessage("查找全部失败", e.getMessage());
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    @Override
    public String findByNameDrug(Integer page, String drugName) {
        try {
            drugName = "%" + drugName + "%";
            PageHelper.startPage(page, 3);
            ArrayList<Drug_information> byNameDrug = drugInformationDao.findByNameDrug(drugName);
            PageInfo pageInfo = new PageInfo(byNameDrug);
            WebMessage webMessage = new WebMessage(Code.success_code, "", pageInfo);
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception e) {
            WebMessage webMessage = new WebMessage("出现错误，请刷新界面或尝试联系管理员", e.getMessage());
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    @Override
    public String addOneDrug(Drug_information drug_information,String fileName) {
        try {
            drug_information.setDrugImgUrl("http://localhost:8080/img/drug/"+drug_information.getId()+"\\"+drug_information.getId()+".jpg");
            WebMessage webMessage = new WebMessage(Code.success_code, "", drugInformationDao.addOneDrug(drug_information));
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception e) {
            WebMessage webMessage = new WebMessage("添加失败", e.getMessage());
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    @Override
    public String editDrug(Drug_information drug_information) {
        try {
            WebMessage webMessage = new WebMessage(Code.success_code, "针对"+drug_information.getDrugName()+"的药品信息修改成功", drugInformationDao.editDrug(drug_information));
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        } catch (Exception e) {
            WebMessage webMessage = new WebMessage("修改失败", e.getMessage());
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        }
    }

    /**
     * 将获取到的全部数据写入xlsx中
     *
     * @param url
     * @return
     */
    @Override
    public ByteArrayOutputStream DrugExportData(String url) {
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

            ArrayList<Drug_information> allDrug = drugInformationDao.findAllDrug();
            //将allDoctor写入数据中
            XSSFSheet sheet = map.get(xb);
            int row = 3;
            XSSFRow row_3 = null;
            for (Drug_information drug_information : allDrug) {
                row_3 = sheet.createRow(row);
                XSSFCell cell_3_1 = row_3.createCell(1);
                cell_3_1.setCellStyle(cellStyle);
                cell_3_1.setCellValue(drug_information.getId());
                cell_3_1.setCellType(CellType.STRING);
                XSSFCell cell_3_2 = row_3.createCell(2);
                cell_3_2.setCellStyle(cellStyle);
                cell_3_2.setCellValue(drug_information.getDrugName());
                cell_3_2.setCellType(CellType.STRING);
                XSSFCell cell_3_3 = row_3.createCell(3);
                cell_3_3.setCellStyle(cellStyle);
                cell_3_3.setCellValue(drug_information.getEffect());
                cell_3_3.setCellType(CellType.STRING);
                XSSFCell cell_3_4 = row_3.createCell(4);
                cell_3_4.setCellStyle(cellStyle);
                cell_3_4.setCellValue(drug_information.getOriginalPrice());
                XSSFCell cell_3_5 = row_3.createCell(5);
                cell_3_5.setCellStyle(cellStyle);
                cell_3_5.setCellValue(drug_information.getDrugImgUrl());
                cell_3_5.setCellType(CellType.STRING);
                XSSFCell cell_3_6 = row_3.createCell(6);
                cell_3_6.setCellStyle(cellStyle);
                cell_3_6.setCellValue(drug_information.getCurrentPrice());
                XSSFCell cell_3_7 = row_3.createCell(7);
                cell_3_7.setCellStyle(cellStyle);
                cell_3_7.setCellValue(drug_information.getQuantity());
                XSSFCell cell_3_8 = row_3.createCell(8);
                cell_3_8.setCellStyle(cellStyle);
                cell_3_8.setCellValue(drug_information.getExhibit());
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
    public ByteArrayOutputStream DrugTemplateDownload(String url) {
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
        ArrayList<Drug_information> drug_information_list = new ArrayList<>();
        while (true) {
            Drug_information drugInformation = new Drug_information();
            XSSFRow new_row = sheetAt.getRow(newRow);
            try {
                String rawValue = new_row.getCell(1).getRawValue();
                if (!StringUtils.isNotBlank(rawValue) || rawValue.equals("")) {
                    break;
                }
                drugInformation.setId(rawValue);
                drugInformation.setDrugName(new_row.getCell(2).getStringCellValue());
                drugInformation.setEffect(new_row.getCell(3).getStringCellValue());
                drugInformation.setOriginalPrice(new_row.getCell(4).getNumericCellValue());
                drugInformation.setDrugName(new_row.getCell(5).getStringCellValue());
                drugInformation.setCurrentPrice(new_row.getCell(6).getNumericCellValue());
                drugInformation.setQuantity((int) new_row.getCell(7).getNumericCellValue());
                drugInformation.setExhibit((int) new_row.getCell(8).getNumericCellValue());
                drug_information_list.add(drugInformation);
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
        if (!file.delete()) {
            file.delete();
        }
        try {
            //至此我们获取完表单中的全部数据
//          System.out.println(drugInformationList.toString());
//            doctorDao.addListDoctor(doctorInformationList);
        } catch (Exception e) {
//            e.printStackTrace();
            String hintMessage = "出现错误！错误原因可能是表格中的数据已经存在，请检查表格中的数据并稍后重试";
            return new Message(Code.known_error_code, hintMessage);
        }
        String hintMessage = "导入成功~";
        return new Message(Code.success_code, "success", hintMessage);
    }

    @Override
    public String exitDrugExhibit(Drug_information drug_information) {
        try {
            if(drugInformationDao.exitDrugExhibit(drug_information)==1){
                if(drug_information.getExhibit()==1){
                    WebMessage webMessage = new WebMessage(Code.success_code, "打开成功", "");
                    return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
                }else{
                    WebMessage webMessage = new WebMessage(Code.success_code, "关闭成功", "");
                    return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
                }
            }else {
                WebMessage webMessage = new WebMessage("关闭失败", "");
                return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
            }

        } catch (Exception e) {
            WebMessage webMessage = new WebMessage("关闭失败", e.getMessage());
            return JSON.toJSONString(webMessage, SerializerFeature.DisableCircularReferenceDetect);
        }
    }

}
