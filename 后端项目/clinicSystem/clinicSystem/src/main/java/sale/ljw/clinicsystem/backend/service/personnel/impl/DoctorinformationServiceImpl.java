package sale.ljw.clinicsystem.backend.service.personnel.impl;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.serializer.SerializerFeature;
import com.baomidou.mybatisplus.core.conditions.Wrapper;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.conditions.update.UpdateWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import jdk.nashorn.internal.ir.IfNode;
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
import sale.ljw.clinicsystem.backend.dao.basic.DoctordutyMapper;
import sale.ljw.clinicsystem.backend.dao.order.ReserveMapper;
import sale.ljw.clinicsystem.backend.dao.personnel.DoctorinformationMapper;
import sale.ljw.clinicsystem.backend.dao.personnel.DoctorloginMapper;
import sale.ljw.clinicsystem.backend.form.personnel.admin.AddDoctorInformationByAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.admin.DeleteIdsBYAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.admin.EditDoctorInformation;
import sale.ljw.clinicsystem.backend.form.personnel.admin.FindDoctorByAdmin;
import sale.ljw.clinicsystem.backend.form.personnel.doctor.EditDoctorInformationByDoctor;
import sale.ljw.clinicsystem.backend.pojo.basic.Doctorduty;
import sale.ljw.clinicsystem.backend.pojo.order.Reserve;
import sale.ljw.clinicsystem.backend.pojo.personnel.Doctorinformation;
import sale.ljw.clinicsystem.backend.pojo.personnel.Doctorlogin;
import sale.ljw.clinicsystem.backend.service.personnel.DoctorinformationService;
import sale.ljw.clinicsystem.backend.service.personnel.DoctorloginService;
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
 * @description 针对表【doctorInformation】的数据库操作Service实现
 * @createDate 2022-08-07 16:00:54
 */
@Service
public class DoctorinformationServiceImpl extends ServiceImpl<DoctorinformationMapper, Doctorinformation>
        implements DoctorinformationService {
    @Autowired
    private DoctorinformationMapper doctorinformationMapper;

    @Autowired
    private DoctorloginMapper doctorloginMapper;
    @Autowired
    private DoctorloginService doctorloginService;

    @Autowired
    private ReserveMapper reserveMapper;
    @Autowired
    private DoctordutyMapper doctordutyMapper;

    @Override
    public String findAllDoctor(FindDoctorByAdmin doctor) {
        PageHelper.startPage(doctor.getPage(), 20);
        ArrayList<Map<String, Objects>> allDoctor = doctorinformationMapper.findAllDoctor(doctor);
        PageInfo pageInfo = new PageInfo(allDoctor);
        return JSON.toJSONString(ResponseResult.getSuccessResult(pageInfo), SerializerFeature.DisableCircularReferenceDetect);
    }

    @Override
    public String editDoctor(EditDoctorInformation doctorInformation, MultipartFile multipartFileUpload) {
        QueryWrapper<Doctorinformation> queryWrapperName = new QueryWrapper<>();
        queryWrapperName.eq("name", doctorInformation.getName());
        //当存在名称数据
        Map<String, Object> map = getMap(queryWrapperName);
        if (map != null && !map.get("id").equals(doctorInformation.getId())) {
            //返回相同名称的错误码 403
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        if (multipartFileUpload != null) {
            //判断用户头像是否上传
            String path = System.getProperty("user.dir");
            File newFile = new File(path + "\\src\\main\\webapp\\Img\\user\\" + doctorInformation.getId());
            newFile.mkdirs();
            File files = new File(newFile + "\\" + doctorInformation.getId() + ".jpg");
            try {
                multipartFileUpload.transferTo(files);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            doctorInformation.setAvatar("http://localhost:8000/clinic/Img/user/" + doctorInformation.getId() + "/" + doctorInformation.getId() + ".jpg");
        }
        UpdateWrapper<Doctorinformation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", doctorInformation.getId()).
                set("address", doctorInformation.getAddress()).
                set("avatar", doctorInformation.getAvatar()).
                set("birthday", doctorInformation.getBirthdayCoding()).
                set("did", doctorInformation.getDid()).
                set("name", doctorInformation.getName()).
                set("registereId", doctorInformation.getRegistereId()).
                set("age", new Date().getYear() - doctorInformation.getBirthdayCoding().getYear()).
                set("sex", doctorInformation.getSexCoding());
        boolean update = update(updateWrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    /*此处有三个表的插入，需添加事务控制*/
    @Transactional
    @Override
    public String addDoctorInformation(AddDoctorInformationByAdmin doctorInformationByAdmin) {
        //首先判断新增数据是否有相似数据
        QueryWrapper<Doctorinformation> queryWrapperNumber = new QueryWrapper<>();
        queryWrapperNumber.eq("identityNumber", doctorInformationByAdmin.getIdentityNumber());
        //当存在数据
        if (getMap(queryWrapperNumber) != null) {
            //返回相同身份证号的错误码 402
            return JSON.toJSONString(ResponseResult.getErrorResult("C402"));
        }
        QueryWrapper<Doctorinformation> queryWrapperName = new QueryWrapper<>();
        queryWrapperName.eq("name", doctorInformationByAdmin.getName());
        //当存在名称数据
        if (getMap(queryWrapperName) != null) {
            //返回相同名称的错误码 403
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        //数据通过验证，此时添加数据到数据库
        //添加医生信息，首先添加登陆表中
        //为用户创建id
        String doctorId = UUID.randomUUID().toString();
        //存储到登陆表
        int insert = doctorloginMapper.insert(new Doctorlogin(doctorId, doctorInformationByAdmin.getName(), null, null, DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)), 2));
        if (insert != 0) {
            //登陆表中存在数据，添加数据到医生信息表中
            Doctorinformation doctorinformation = new Doctorinformation();
            BeanUtils.copyProperties(doctorInformationByAdmin, doctorinformation);
            doctorinformation.setIdentitynumber(doctorInformationByAdmin.getIdentityNumber());
            doctorinformation.setRegistereid(doctorInformationByAdmin.getRegistereId());
            doctorinformation.setId(doctorId);
            doctorinformation.setAge(new Date().getYear() - doctorInformationByAdmin.getBirthday().getYear());
            boolean save = save(doctorinformation);
            if (save) {
                //存储值班信息
                Doctorduty doctorduty = new Doctorduty();
                doctorduty.setId(doctorId);
                doctorduty.setDutyid(doctorInformationByAdmin.getDuty());
                int insert1 = doctordutyMapper.insert(doctorduty);
                if (insert1 != 0) {
                    return JSON.toJSONString(ResponseResult.getSuccessResult("C200"));
                }
            }
        }
        return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
    }

    /*删除涉及两个表的删除增加*/
    @Transactional
    @Override
    public String deleteByIdsInDoctorInformation(DeleteIdsBYAdmin ids) {
        //根据id删除首先判断预约表中是否存在相关数据
        ArrayList<Map<String, Object>> reserves = reserveMapper.findByDoctorIds(ids.getIds());
        if (reserves.size()!=0){
            return JSON.toJSONString(ResponseResult.getErrorResult("C402"));
        }
        //预约表中不存在医生，执行删除医生
        //逻辑删除顺序：医生信息表——登录表权限关闭
        //逻辑删除删除信息表即可，将权限改为0
        int doctorInformationResult = doctorinformationMapper.deleteBatchIds(ids.getIds());
        boolean result = true;
        if (doctorInformationResult != 0) {
            //修改登录状态
            for (String id : ids.getIds()) {
                int update = doctorloginMapper.updateByIdToPermission(id);
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
    public ByteArrayOutputStream doctorTemplateDownload(HttpServletResponse response) {
        response.setContentType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet;charset=utf-8");
        String fileName = new String((UUID.randomUUID().toString() + ".xlsx").getBytes(StandardCharsets.UTF_8), StandardCharsets.ISO_8859_1);
        //设置文件名
        response.setCharacterEncoding(StandardCharsets.UTF_8.name());
        response.addHeader("Content-Disposition", "attachment;fileName=" + fileName);
        response.setHeader("Access-Control-Expose-Headers", "Content-Disposition");
        //获取产生相应的流对象
        String url = System.getProperty("user.dir") + "\\src\\main\\java\\sale\\ljw\\clinicsystem\\backend\\pojo\\personnel\\Doctorinformation.java";
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
        String url = System.getProperty("user.dir") + "\\src\\main\\java\\sale\\ljw\\clinicsystem\\backend\\pojo\\personnel\\Doctorinformation.java";
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
            List<Doctorinformation> doctorInformation = list();
            //将allDoctor写入数据中
            XSSFSheet sheet = map.get(xb);
            int row = 3;
            XSSFRow row_3 = null;
            for (Doctorinformation doctor : doctorInformation) {
                row_3 = sheet.createRow(row);
                XSSFCell cell_3_1 = row_3.createCell(1);
                cell_3_1.setCellStyle(cellStyle);
                cell_3_1.setCellValue(doctor.getId());
                cell_3_1.setCellType(CellType.STRING);
                XSSFCell cell_3_2 = row_3.createCell(2);
                cell_3_2.setCellStyle(cellStyle);
                cell_3_2.setCellValue(doctor.getAvatar());
                cell_3_2.setCellType(CellType.STRING);
                XSSFCell cell_3_3 = row_3.createCell(3);
                cell_3_3.setCellStyle(cellStyle);
                cell_3_3.setCellValue(doctor.getName());
                cell_3_3.setCellType(CellType.STRING);
                XSSFCell cell_3_4 = row_3.createCell(4);
                cell_3_4.setCellStyle(cellStyle);
                cell_3_4.setCellValue(doctor.getAge());
                cell_3_4.setCellType(CellType.STRING);
                XSSFCell cell_3_5 = row_3.createCell(5);
                cell_3_5.setCellStyle(cellStyle);
                cell_3_5.setCellValue(doctor.getBirthday());
                cell_3_5.setCellType(CellType.STRING);
                XSSFCell cell_3_6 = row_3.createCell(6);
                cell_3_6.setCellStyle(cellStyle);
                cell_3_6.setCellValue(doctor.getSex());
                cell_3_6.setCellType(CellType.STRING);
                XSSFCell cell_3_7 = row_3.createCell(7);
                cell_3_7.setCellStyle(cellStyle);
                cell_3_7.setCellValue(doctor.getIdentitynumber());
                cell_3_7.setCellType(CellType.STRING);
                XSSFCell cell_3_8 = row_3.createCell(8);
                cell_3_8.setCellStyle(cellStyle);
                cell_3_8.setCellValue(doctor.getDid());
                cell_3_8.setCellType(CellType.STRING);
                XSSFCell cell_3_9 = row_3.createCell(9);
                cell_3_9.setCellStyle(cellStyle);
                cell_3_9.setCellValue(doctor.getSalary());
                cell_3_9.setCellType(CellType.STRING);
                XSSFCell cell_3_10 = row_3.createCell(10);
                cell_3_10.setCellStyle(cellStyle);
                cell_3_10.setCellValue(doctor.getRegistereid());
                cell_3_10.setCellType(CellType.STRING);
                XSSFCell cell_3_11 = row_3.createCell(11);
                cell_3_11.setCellStyle(cellStyle);
                cell_3_11.setCellValue(doctor.getAddress());
                cell_3_11.setCellType(CellType.STRING);
                XSSFCell cell_3_12 = row_3.createCell(12);
                cell_3_12.setCellStyle(cellStyle);
                cell_3_12.setCellValue(doctor.getDoctordelete());
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
    @Transactional
    @Override
    public String analyseFile(MultipartFile multipartFileUpload) {
        String path = System.getProperty("user.dir");
        String fileName = UUID.randomUUID().toString();
        File newFile = new File(path + "\\src\\main\\webapp\\static\\" + fileName + ".xlsx");
        try {
            multipartFileUpload.transferTo(newFile);
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
        ArrayList<Doctorinformation> doctorInformation = new ArrayList<>();
        ArrayList<Doctorlogin> doctorlogins=new ArrayList<>();
        while (true) {
            Doctorinformation doctorinformation = new Doctorinformation();
            Doctorlogin doctorlogin=new Doctorlogin();
            XSSFRow new_row = sheetAt.getRow(newRow);
            try {
                new_row.getCell(1).setCellType(CellType.STRING);
                String rawValue = new_row.getCell(1).getStringCellValue();
                if (!StringUtils.isNotBlank(rawValue) || rawValue.equals("")) {
                    break;
                }
                doctorinformation.setId(rawValue);
                doctorlogin.setId(rawValue);
                doctorlogin.setPassword(DigestUtils.md5DigestAsHex("123456".getBytes(StandardCharsets.UTF_8)));
                new_row.getCell(2).setCellType(CellType.STRING);
                doctorinformation.setAvatar(new_row.getCell(2).getStringCellValue());
                new_row.getCell(3).setCellType(CellType.STRING);
                doctorinformation.setName(new_row.getCell(3).getStringCellValue());
                doctorlogin.setLoginname(new_row.getCell(3).getStringCellValue());
                doctorlogin.setPermission(2);
                new_row.getCell(4).setCellType(CellType.STRING);
                doctorinformation.setAge(Integer.parseInt(new_row.getCell(4).getStringCellValue()));
                doctorinformation.setBirthday(new_row.getCell(5).getDateCellValue());
                new_row.getCell(6).setCellType(CellType.STRING);
                doctorinformation.setSex(new_row.getCell(6).getStringCellValue());
                new_row.getCell(7).setCellType(CellType.STRING);
                doctorinformation.setIdentitynumber(new_row.getCell(7).getStringCellValue());
                new_row.getCell(8).setCellType(CellType.STRING);
                doctorinformation.setDid(new_row.getCell(8).getStringCellValue());
                new_row.getCell(9).setCellType(CellType.STRING);
                doctorinformation.setSalary(Double.parseDouble(new_row.getCell(9).getStringCellValue()));
                new_row.getCell(10).setCellType(CellType.STRING);
                doctorinformation.setRegistereid(new_row.getCell(10).getStringCellValue());
                new_row.getCell(11).setCellType(CellType.STRING);
                doctorinformation.setAddress(new_row.getCell(11).getStringCellValue());
                new_row.getCell(12).setCellType(CellType.STRING);
                doctorinformation.setDoctordelete(Integer.parseInt(new_row.getCell(12).getStringCellValue()));
                doctorInformation.add(doctorinformation);
                doctorlogins.add(doctorlogin);
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
            boolean result = doctorloginService.saveBatch(doctorlogins);
            if(result){
                saveBatch(doctorInformation);
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
    public String mainGetNumber() {
        return JSON.toJSONString(ResponseResult.getSuccessResult(doctorinformationMapper.mainGetNumber()));
    }

    @Override
    public String getValueNumber() {
        Map<String, Object> allValues = doctorinformationMapper.findValueNumber();
        Set<Map.Entry<String, Object>> entrySet = allValues.entrySet();
        Iterator<Map.Entry<String, Object>> it = entrySet.iterator();
        List<ArrayList<Object>> arrayLists=new ArrayList<>();
        ArrayList<Object> arrayName=new ArrayList<>();
        ArrayList<Object> arrayNumber=new ArrayList<>();
        while(it.hasNext()) {
            Map.Entry<String, Object> entry = it.next();
            arrayName.add(entry.getKey());
            if (entry.getValue()!=null){
                arrayNumber.add( entry.getValue());
            }
        }
        arrayLists.add(arrayName);
        arrayLists.add(arrayNumber);
        return JSON.toJSONString(arrayLists);
    }

    @Override
    public String getDayMedicine() {
        List<Map<String, Object>> dayMedicine = doctorinformationMapper.getDayMedicine();
        List<ArrayList<String>> arrayLists=new ArrayList<>();
        ArrayList<String> arrayDay=new ArrayList<>();
        ArrayList<String> arrayNumber=new ArrayList<>();
        dayMedicine.stream().forEach(s->{
            arrayDay.add(s.get("days").toString());
            arrayNumber.add( s.get("COUNT").toString());
        });
        String[] strings = new String[2];
        arrayLists.add(arrayDay);
        arrayLists.add(arrayNumber);
        return JSON.toJSONString(arrayLists);
    }

    @Override
    public String getStateStatistics() {
        return JSON.toJSONString(doctorinformationMapper.getStateStatistics());
    }

    @Override
    public String getRegisDoctor() {
        List<Map<String, Object>> regisDoctor = doctorinformationMapper.getRegisDoctor();
//        System.out.println(regisDoctor);
        return JSON.toJSONString(regisDoctor);
    }

    @Override
    public String getDeleteData() {
        List<Map<String, Object>> deleteData = doctorinformationMapper.getDeleteData();
        return JSON.toJSONString(ResponseResult.getSuccessResult(deleteData));
    }
    @Transactional
    @Override
    public String deleteById(String id) {
        //删除医生数据首先检查医生数据是否在预约表中，若存在则禁止删除
        QueryWrapper<Reserve> queryWrapper=new QueryWrapper<>();
        queryWrapper.eq("doctorId",id);
        if(reserveMapper.selectList(queryWrapper).size()!=0){
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        //在值班表中删除医生值班信息
        if(doctordutyMapper.deleteById(id)==0){
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        //在医生信息表中删除医生信息
        Doctorinformation doctorinformation = doctorinformationMapper.selectDeleteDataById(id);
        System.out.println(doctorinformation);
        //判断头像是否是默认头像
        if(!doctorinformation.getAvatar().contains("default")){
            String path = System.getProperty("user.dir");
            File newFile = new File(path + "\\src\\main\\webapp\\Img\\user\\" + id+"\\" + id + ".jpg");
            //删除不了也没啥关系，不得影响主任务的执行，只好转化脏数据，所以不对结果进行判定
            newFile.delete();
        }
        if(doctorinformationMapper.permanentDeleteById(id)==0){
            return JSON.toJSONString(ResponseResult.getErrorResult("C402"));
        }
        //登陆表中删除医生信息
        if(doctorloginMapper.deleteById(id)==0){
            return JSON.toJSONString(ResponseResult.getErrorResult("C401"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult("C200"));
    }

    @Override
    public String recoveryData(String id) {
        //判断值班表中是否还存在数据
        if(doctordutyMapper.selectById(id)==null){
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        //判断登陆表中是否存在数据
        if(doctorloginMapper.selectById(id)==null){
            return JSON.toJSONString(ResponseResult.getErrorResult("C402"));
        }
        //恢复数据
        if(doctorinformationMapper.recoveryData(id)==0){
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(null));
    }

    @Override
    public String editDoctorByDoctor(EditDoctorInformationByDoctor doctorInformation, MultipartFile multipartFile) {
        QueryWrapper<Doctorinformation> queryWrapperName = new QueryWrapper<>();
        queryWrapperName.eq("name", doctorInformation.getName());
        //当存在名称数据
        Map<String, Object> map = getMap(queryWrapperName);
        if (map != null && !map.get("id").equals(doctorInformation.getId())) {
            //返回相同名称的错误码 403
            return JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        if (multipartFile != null) {
            //判断用户头像是否上传
            String path = System.getProperty("user.dir");
            File newFile = new File(path + "\\src\\main\\webapp\\Img\\user\\" + doctorInformation.getId());
            newFile.mkdirs();
            File files = new File(newFile + "\\" + doctorInformation.getId() + ".jpg");
            try {
                multipartFile.transferTo(files);
            } catch (IOException e) {
                throw new RuntimeException(e);
            }
            doctorInformation.setAvatar("http://localhost:8000/clinic/Img/user/" + doctorInformation.getId() + "/" + doctorInformation.getId() + ".jpg");
        }
        UpdateWrapper<Doctorinformation> updateWrapper = new UpdateWrapper<>();
        updateWrapper.eq("id", doctorInformation.getId()).
                set("address", doctorInformation.getAddress()).
                set("avatar", doctorInformation.getAvatar()).
                set("birthday", doctorInformation.getBirthday()).
                set("name", doctorInformation.getName()).
                set("age", new Date().getYear() - doctorInformation.getBirthday().getYear()).
                set("sex", doctorInformation.getSex());
        boolean update = update(updateWrapper);
        if (update) {
            return JSON.toJSONString(ResponseResult.getSuccessResult(null));
        } else {
            return JSON.toJSONString(ResponseResult.getErrorResult("C405"));
        }
    }

    @Override
    public String getDoctorListByDepartment(String departmentId) {
        List<Map<String, Object>> doctorListByDepartment = doctorinformationMapper.getDoctorListByDepartment(departmentId);
        if(doctorListByDepartment==null){
            //科室无人
            JSON.toJSONString(ResponseResult.getErrorResult("C403"));
        }
        return JSON.toJSONString(ResponseResult.getSuccessResult(doctorListByDepartment));
    }
}




