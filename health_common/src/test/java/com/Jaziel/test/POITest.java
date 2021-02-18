package com.Jaziel.test;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.io.IOException;

/**
 * @author 王杰
 * @date 2021/2/4 9:59
 */
public class POITest {
    @Test
    public void POITest1() {
        try {
            // 获取指定文件资源，生成一个Excel对象（工作簿）
            XSSFWorkbook sheets = new XSSFWorkbook(new FileInputStream(new File("e:\\Google\\poi.xlsx")));
            // 获得Excel文件中第一个sheet
            XSSFSheet sheetAt = sheets.getSheetAt(0);
            for (Row row : sheetAt) {
                for (Cell cell : row) {
                    System.out.println(cell.getStringCellValue());
                }
            }
            // 关闭资源
            sheets.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void POITest2() {
        try {
            // 获取指定文件资源，生成一个Excel对象（工作簿）
            XSSFWorkbook sheets = new XSSFWorkbook(new FileInputStream(new File("e:\\Google\\poi.xlsx")));
            // 获得Excel文件中第一个sheet
            XSSFSheet sheetAt = sheets.getSheetAt(0);
            // 获得最后的行号
            int lastRowNum = sheetAt.getLastRowNum();
            for (int i = 0; i <= lastRowNum; i++) {
                // 根据行号获得每行的数据
                XSSFRow row = sheetAt.getRow(i);
                // 获得每行有多少列
                short lastCellNum = row.getLastCellNum();
                for (int j = 0; j < lastCellNum; j++) {
                    XSSFCell cell = row.getCell(j);
                    System.out.println(cell.getStringCellValue());
                }
            }
            // 关闭资源
            sheets.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    @Test
    public void POITest3() {
        try {
            //在内存中创建一个Excel文件
            XSSFWorkbook book = new XSSFWorkbook();
            //
            XSSFSheet sheet = book.createSheet("产值博客");
            XSSFRow row = sheet.createRow(0);
            row.createCell(0).setCellValue("姓名");
            row.createCell(1).setCellValue("性别");
            //通过输出流将workbook对象下载到磁盘
            FileOutputStream fileOutputStream = new FileOutputStream(new File("e:\\Google\\hello.xlsx"));
            book.write(fileOutputStream);
            fileOutputStream.flush();
            fileOutputStream.close();
            // 关闭资源
            book.close();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
