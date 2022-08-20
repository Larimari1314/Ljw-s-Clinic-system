package sale.ljw.clinicsystem.backend.service.basic.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializeFilter;
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
import org.springframework.util.DigestUtils;
import org.springframework.web.multipart.MultipartFile;
import sale.ljw.clinicsystem.backend.dao.order.OrderdrugMapper;
import sale.ljw.clinicsystem.backend.form.basic.admin.AddDrugByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.DrugSwitchingByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.EditDrugByAdmin;
import sale.ljw.clinicsystem.backend.form.basic.admin.FindDrugByAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.admin.DeleteIdsBYAdmin;
import sale.ljw.clinicsystem.backend.pojo.basic.Drug;
import sale.ljw.clinicsystem.backend.pojo.order.Orderdrug;
import sale.ljw.clinicsystem.backend.pojo.personnel.Doctorinformation;
import sale.ljw.clinicsystem.backend.pojo.personnel.Doctorlogin;
import sale.ljw.clinicsystem.backend.service.basic.DrugService;
import sale.ljw.clinicsystem.backend.dao.basic.DrugMapper;
import org.springframework.stereotype.Service;
import sale.ljw.clinicsystem.common.http.ResponseResult;
import sale.ljw.clinicsystem.common.sercurity.utils.export.ExportDataUtils;

import javax.servlet.http.HttpServletResponse;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;
import java.io.Serializable;
import java.nio.charset.StandardCharsets;
import java.util.*;

/**
* @author 86155
* @description 针对表【drug】的数据库操作Service实现
* @createDate 2022-08-07 16:05:39
*/
@Service
public class DrugServiceImpl extends ServiceImpl<DrugMapper, Drug>
    implements DrugService{
    @Autowired
    private DrugMapper drugMapper;
    @Autowired
    private OrderdrugMapper orderdrugMapper;
    @Override
    public String findAllDrug(FindDrugByAdmin drug) {
        PageHelper.startPage(drug.getPage(),10);
        ArrayList<Map<String, Objects>> allByDrug = drugMapper.findAllByDrug(drug);
        PageInfo pageInfo=new PageInfo(allByDrug);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo), SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String switching(DrugSwitchingByAdmin switching) {
        UpdateWrapper<Drug> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", switching.getId())
                .set(switching.getEnable()==0, "enable", "1")
                .set(switching.getEnable()==1, "enable", "0");
        boolean update = update(updateWrapper);
        if(update){
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        }else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String editDrug(EditDrugByAdmin drug, MultipartFile multipartFile) {
        if(multipartFile!=null){
            String path = System.getProperty("user.dir");
            File newFile = new File(path + "\\src\\main\\webapp\\Img\\drug\\" + drug.getId());
            newFile.mkdirs();
            File files = new File(newFile + "\\" + drug.getId() + ".jpg");
            try {
                multipartFile.transferTo(files);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            drug.setCover("http://localhost:8000/clinic/Img/drug/" + drug.getId() + "/" + drug.getId() + ".jpg");
        }
        UpdateWrapper<Drug> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", drug.getId()).set("drugName", drug.getDrugName())
                        .set("cover",drug.getCover()).set("number",drug.getNumber())
                        .set("price", drug.getPrice()).set("specification", drug.getSpecification());
        boolean update = update(updateWrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String deleteDrugByIds(DeleteIdsBYAdmin ids) {
        //查看订单药品表中是否存在
        ArrayList<Map<String, Object>> byDrugIds = orderdrugMapper.findByDrugIds(ids.getIds());
        if(byDrugIds.size()!=0){
            return JSON.toJSONString(ResponseResult.getErrorResult("C402"));
        }
        int result = drugMapper.deleteBatchIds(ids.getIds());
        if(result!=0){
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String addDrug(AddDrugByAdmin drug,MultipartFile multipartFile) {
        String id = UUID.randomUUID().toString();
        if(multipartFile==null){
            return JSON.toJSONString(ResponseResult.getErrorResult("C404"));
        }else {
            String path = System.getProperty("user.dir");
            File newFile = new File(path + "\\src\\main\\webapp\\Img\\drug\\" + id);
            newFile.mkdirs();
            File files = new File(newFile + "\\" + id + ".jpg");
            try {
                multipartFile.transferTo(files);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            drug.setCover("http://localhost:8000/clinic/Img/drug/" + id + "/" + id + ".jpg");
        }
        Drug drugInsert=new Drug();
        BeanUtils.copyProperties(drug, drugInsert);
        drugInsert.setId(id);
        boolean save = save(drugInsert);
        if(save){
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }


    @Override
    public ByteArrayOutputStream drugTemplateDownload(HttpServletResponse response) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = new String((UUID.randomUUID().toString() + ".xlsx").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        //设置文件名
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //获取产生相应的流对象
        String url = System.getProperty("user.dir") + "\\src\\main\\java\\sale\\ljw\\clinicsystem\\backend\\pojo\\basic\\Drug.java";
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
        String url = System.getProperty("user.dir") + "\\src\\main\\java\\sale\\ljw\\clinicsystem\\backend\\pojo\\basic\\Drug.java";
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
            List<Drug> drugList = list();
            //将allDoctor写入数据中
            XSSFSheet sheet = map.get(xb);
            int row = 3;
            XSSFRow row_3 = null;
            for (Drug drug : drugList) {
                row_3 = sheet.createRow(row);
                XSSFCell cell_3_1 = row_3.createCell(1);
                cell_3_1.setCellStyle(cellStyle);
                cell_3_1.setCellValue(drug.getId());
                cell_3_1.setCellType(CellType.STRING);
                XSSFCell cell_3_2 = row_3.createCell(2);
                cell_3_2.setCellStyle(cellStyle);
                cell_3_2.setCellValue(drug.getCover());
                cell_3_2.setCellType(CellType.STRING);
                XSSFCell cell_3_3 = row_3.createCell(3);
                cell_3_3.setCellStyle(cellStyle);
                cell_3_3.setCellValue(drug.getDrugname());
                cell_3_3.setCellType(CellType.STRING);
                XSSFCell cell_3_4 = row_3.createCell(4);
                cell_3_4.setCellStyle(cellStyle);
                cell_3_4.setCellValue(drug.getSpecification());
                cell_3_4.setCellType(CellType.STRING);
                XSSFCell cell_3_5 = row_3.createCell(5);
                cell_3_5.setCellStyle(cellStyle);
                cell_3_5.setCellValue(drug.getNumber());
                cell_3_5.setCellType(CellType.STRING);
                XSSFCell cell_3_6 = row_3.createCell(6);
                cell_3_6.setCellStyle(cellStyle);
                cell_3_6.setCellValue(drug.getPrice());
                cell_3_6.setCellType(CellType.STRING);
                XSSFCell cell_3_7 = row_3.createCell(7);
                cell_3_7.setCellStyle(cellStyle);
                cell_3_7.setCellValue(drug.getDrugdelete());
                cell_3_7.setCellType(CellType.STRING);
                XSSFCell cell_3_8 = row_3.createCell(8);
                cell_3_8.setCellStyle(cellStyle);
                cell_3_8.setCellValue(drug.getVersion());
                cell_3_8.setCellType(CellType.STRING);
                XSSFCell cell_3_9 = row_3.createCell(9);
                cell_3_9.setCellStyle(cellStyle);
                cell_3_9.setCellValue(drug.getEnable());
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
    public String analyseFile(MultipartFile multipartFile) {
        String path = System.getProperty("user.dir");
        String fileName = UUID.randomUUID().toString();
        File newFile = new File(path + "\\src\\main\\webapp\\static\\" + fileName + ".xlsx");
        try {
            multipartFile.transferTo(newFile);
        } catch (IOException e) {
            return JSON.toJSONString(ResponseResult.getErrorResult("C500"));
        }catch (NullPointerException e){
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
        ArrayList<Drug> drugLists = new ArrayList<>();
        while (true) {
            Drug drug = new Drug();
            XSSFRow new_row = sheetAt.getRow(newRow);
            try {
                new_row.getCell(1).setCellType(CellType.STRING);
                String rawValue = new_row.getCell(1).getStringCellValue();
                if (!StringUtils.isNotBlank(rawValue) || rawValue.equals("")) {
                    break;
                }
                drug.setId(rawValue);
                new_row.getCell(2).setCellType(CellType.STRING);
                drug.setCover(new_row.getCell(2).getStringCellValue());
                new_row.getCell(3).setCellType(CellType.STRING);
                drug.setDrugname(new_row.getCell(3).getStringCellValue());
                new_row.getCell(4).setCellType(CellType.STRING);
                drug.setSpecification(new_row.getCell(4).getStringCellValue());
                new_row.getCell(5).setCellType(CellType.STRING);
                drug.setNumber(Integer.parseInt(new_row.getCell(5).getStringCellValue()));
                new_row.getCell(6).setCellType(CellType.STRING);
                drug.setPrice(Double.parseDouble(new_row.getCell(6).getStringCellValue()));
                new_row.getCell(7).setCellType(CellType.STRING);
                drug.setDrugdelete(Integer.parseInt(new_row.getCell(7).getStringCellValue()));
                new_row.getCell(8).setCellType(CellType.STRING);
                drug.setVersion(Integer.parseInt(new_row.getCell(8).getStringCellValue()));
                new_row.getCell(9).setCellType(CellType.STRING);
                drug.setEnable(Integer.parseInt(new_row.getCell(9).getStringCellValue()));
                drugLists.add(drug);
                newRow++;
            } catch (NullPointerException e) {
                break;
                //抱空指针异常表明数据读取完，跳出循环即可
            } catch (Exception e) {
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
            if( saveBatch(drugLists)){

            }else {
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
        ArrayList<Map<String, Objects>> deleteData = drugMapper.getDeleteData();
        return JSON.toJSONString(ResponseResult.getSuccessResult(deleteData));
    }

    @Override
    public String deleteById(String id) {
        //在订单表中查询是否存在药品
        QueryWrapper<Orderdrug> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("drugId", id);
        if(orderdrugMapper.selectList(queryWrapper).size()!=0){
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        //删除封面
        String path = System.getProperty("user.dir");
        File newFile = new File(path + "\\src\\main\\webapp\\Img\\drug\\" + id+"\\" + id + ".jpg");
        //删除不了也没啥关系，不得影响主任务的执行，只好转化脏数据，所以不对结果进行判定
        newFile.delete();
        if(drugMapper.permanentDeleteById(id)==0){
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null));
    }

    @Override
    public String recoveryData(String id) {
        if(drugMapper.recoveryData(id)==0){
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null));
    }

}




