package sale.ljw.clinic_administrator_background_system.utils.export;


import org.apache.poi.ss.usermodel.BorderStyle;
import org.apache.poi.ss.usermodel.CellStyle;
import org.apache.poi.ss.usermodel.HorizontalAlignment;
import org.apache.poi.ss.util.CellRangeAddress;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import sale.ljw.clinic_administrator_background_system.utils.javaDocUtils.Doclet;
import sale.ljw.clinic_administrator_background_system.utils.javaDocUtils.FildEntry;
import sale.ljw.clinic_administrator_background_system.utils.javaDocUtils.ModelClassDocVO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

public class ExportDataUtils {
    /**
     * url传入全路径名，获取doc文档内容,生成模板
     *
     * @param url
     * @param <T>
     * @return
     * @throws IOException
     */
    public static  <T> Map<XSSFWorkbook,XSSFSheet> excelTemplate(String url) throws IOException {
        Doclet doclet = new Doclet(url);
        ModelClassDocVO exec = doclet.exec();
        ArrayList<String> headerDocument = new ArrayList<>();
        //获取文档名称
        for (FildEntry fildEntry : exec.getFildEntryList()) {
            headerDocument.add(fildEntry.getfExplain());
        }
        XSSFWorkbook xb = new XSSFWorkbook();
        XSSFSheet sheet = xb.createSheet(UUID.randomUUID().toString());
        //设置单元格样式
        CellStyle cellStyle = xb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);
        cellStyle.setBorderBottom(BorderStyle.THIN);
        cellStyle.setBorderLeft(BorderStyle.THIN);
        cellStyle.setBorderRight(BorderStyle.THIN);
        cellStyle.setBorderTop(BorderStyle.THIN);
        //根据数据内容合并单元格
        sheet.addMergedRegion(new CellRangeAddress(1, 1, 1, headerDocument.size()));
        //居中单元格
        //写入单元格副标题
        XSSFRow row_1 = sheet.createRow(1);
        int cell_1_1 = 1;
        for (int i = 0; i < headerDocument.size(); i++) {
            //设置标题信息
            XSSFCell cell = row_1.createCell(cell_1_1);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(exec.getModelCommentText());
            cell_1_1++;
        }
        int cell_2_1 = 1;
        XSSFRow row_2 = sheet.createRow(2);
        for (String headerName : headerDocument) {
            XSSFCell cell = row_2.createCell(cell_2_1);
            cell.setCellStyle(cellStyle);
            cell.setCellValue(headerName);
            cell_2_1++;
        }
        Map<XSSFWorkbook,XSSFSheet> map = new HashMap<>();
        map.put(xb,sheet);
        return map;
    }
}
