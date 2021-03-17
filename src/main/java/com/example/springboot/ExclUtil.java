package com.example.springboot;

import org.apache.poi.hssf.usermodel.HSSFCellStyle;
import org.apache.poi.hssf.util.HSSFColor;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.streaming.SXSSFWorkbook;

import java.io.File;
import java.io.FileOutputStream;
import java.lang.reflect.Field;
import java.text.DateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.List;

public class ExclUtil {

    /**
     * 导出Excel
     * @创建人 liangry
     * @param columnCNNames
     * @param objects
     * @param exclName
     * @param filePath
     * @throws Exception
     */
    private void export(String[] columnCNNames, List<Object> objects,String exclName,String filePath) throws Exception {
        SXSSFWorkbook wb = new SXSSFWorkbook(1000);
        wb.setCompressTempFiles(true);
        Sheet sheet = wb.createSheet("Sheet1");
        Font font = wb.createFont();
        font.setFontName("宋体");
        CellStyle style = wb.createCellStyle();
        style.setFont(font);
        style.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        style.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);

        CellStyle dateStyle = wb.createCellStyle();
        DataFormat format= wb.createDataFormat();
        dateStyle.setDataFormat(format.getFormat("yyyy-MM-dd"));
        CellStyle styleHead = wb.createCellStyle();
        styleHead.setFont(font);
        styleHead.setAlignment(HSSFCellStyle.ALIGN_CENTER);
        styleHead.setVerticalAlignment(HSSFCellStyle.VERTICAL_CENTER);
        styleHead.setFillForegroundColor(HSSFColor.LIGHT_GREEN.index);
        styleHead.setFillPattern(HSSFCellStyle.SOLID_FOREGROUND);
        styleHead.setFillBackgroundColor(HSSFColor.LIGHT_GREEN.index);
        styleHead.setBorderBottom(HSSFCellStyle.BORDER_THIN);
        styleHead.setBorderLeft(HSSFCellStyle.BORDER_THIN);
        styleHead.setBorderRight(HSSFCellStyle.BORDER_THIN);
        styleHead.setBorderTop(HSSFCellStyle.BORDER_THIN);
        // 表头
        Row rowHeaderTitle = sheet.createRow(0);
        rowHeaderTitle.setHeightInPoints(30);
        for (int i = 0; i < columnCNNames.length; i++) {
            Cell cell = rowHeaderTitle.createCell((short) i);
            cell.setCellValue(columnCNNames[i].toString());
            cell.setCellStyle(styleHead);
        }


        Field[] fields=null;
        int i=1;
        for(Object obj:objects){
            Row row = sheet.createRow(i + 1);
            fields=obj.getClass().getDeclaredFields();
            int j=0;
            for(Field f:fields){
                Cell cell = row.createCell(0);
                f.setAccessible(true);
                Object va=f.get(obj);
                if(va==null){
                    va="";
                }
                if (f.getGenericType().toString().equals("int") || f.getGenericType().toString().equals("class java.lang.Integer")){
                    cell.setCellValue(va.toString());
                }else if (f.getGenericType().toString().equals("Double") || f.getGenericType().toString().equals("class java.lang.Double")){
                    cell.setCellValue(NumberUtil.toDouble(va.toString(), 0));
                }else if (f.getGenericType().toString().equals("Date") || f.getGenericType().toString().equals("class java.lang.Double")){
                    DateFormat df = new java.text.SimpleDateFormat("yyyy-MM-dd");
                    cell.setCellValue(df.format(va.toString()));
                }else {
                    cell.setCellValue(va.toString());
                }

                j++;
            }
            i++;
        }
        //输出Excel
        Calendar calendar1=Calendar.getInstance();
        calendar1.setTime(new Date());
        String fileName=exclName+".xlsx";
        File file= FileUtil.createFile(filePath);
        FileOutputStream outputStream=new FileOutputStream(filePath);
        wb.write(outputStream);
        outputStream.close();

    }
}
