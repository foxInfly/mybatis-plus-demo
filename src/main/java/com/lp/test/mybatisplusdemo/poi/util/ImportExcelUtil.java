package com.lp.test.mybatisplusdemo.poi.util;

import lombok.extern.slf4j.Slf4j;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.poifs.filesystem.POIFSFileSystem;

import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.Map;

/**
 * 读取并解析excel
 *
 * @author lp
 * @since 2020-12-13 20:00:01
 */
@Slf4j
public class ImportExcelUtil {

    private POIFSFileSystem fs;
    private HSSFWorkbook wb;
    private HSSFSheet sheet;
    private HSSFRow row;

    /**
     * 读取Excel表格表头的内容
     *
     * @param is excel输入流
     * @return String 表头内容的数组
     */
    public String[] readExcelTitle(InputStream is) {
        //1.将excel的inputStream输入流转换成WorkBook对象
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }

        sheet = wb.getSheetAt(0);//表1

        row = sheet.getRow(0);//表1的第一行

        int colNum = row.getPhysicalNumberOfCells();// 标题总列数
        log.info("标题总列数:" + colNum);
        String[] title = new String[colNum];
        for (int i = 0; i < colNum; i++) {
            title[i] = getCellFormatValue(row.getCell((short) i));
        }
        return title;
    }

    /**
     * 读取Excel数据内容
     *
     * @param is excel输入流
     * @return Map 包含单元格数据内容的Map对象
     */
    public Map<Integer, String> readExcelContent(InputStream is) {
        Map<Integer, String> content = new HashMap<>();
        StringBuilder str = new StringBuilder();
        //1.将excel的inputStream输入流转换成WorkBook对象
        try {
            fs = new POIFSFileSystem(is);
            wb = new HSSFWorkbook(fs);
        } catch (IOException e) {
            e.printStackTrace();
        }
        sheet = wb.getSheetAt(0);//表1

        int rowNum = sheet.getLastRowNum();//最后有数据的行数

        row = sheet.getRow(2);//表1的第2行；由于第0行和第一行已经合并了  在这里索引从2开始
        int colNum = row.getPhysicalNumberOfCells();//当前行总列数

        // 正文内容应该从第二行开始,第一行为表头的标题
        for (int i = 2; i <= rowNum; i++) {
            row = sheet.getRow(i);
            int j = 0;
            while (j < colNum) {
                str.append(getCellFormatValue(row.getCell((short) j)).trim()).append("-");
                j++;
            }
            content.put(i, str.toString());
            str = new StringBuilder();
        }
        return content;
    }

    /**
     * 获取单元格数据内容为字符串类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getStringCellValue(HSSFCell cell) {
        String strCell = "";
        switch (cell.getCellTypeEnum()) {
            case STRING:
                strCell = cell.getStringCellValue();
                break;
            case NUMERIC:
                strCell = String.valueOf(cell.getNumericCellValue());
                break;
            case BOOLEAN:
                strCell = String.valueOf(cell.getBooleanCellValue());
                break;
            case BLANK:
                break;
            default:
                break;
        }
        return strCell;
    }

    /**
     * 获取单元格数据内容为日期类型的数据
     *
     * @param cell Excel单元格
     * @return String 单元格数据内容
     */
    private String getDateCellValue(HSSFCell cell) {
        String result = "";
        try {
            String strCell = "";
            switch (cell.getCellTypeEnum()) {
                case STRING:
                    String date = getStringCellValue(cell);
                    result = date.replaceAll("[年月]", "-").replace("日", "").trim();
                    break;
                case NUMERIC:
                    Calendar cal = Calendar.getInstance();
                    Date dateCellValue = cell.getDateCellValue();
                    result = (dateCellValue.getYear() + 1900) + "-" + (dateCellValue.getMonth() + 1) + "-" + dateCellValue.getDate();
                    break;
                case BLANK:
                    break;
                default:
                    break;
            }
            result = "";
        } catch (Exception e) {
            System.out.println("日期格式不正确!");
            e.printStackTrace();
        }
        return result;
    }

    /**
     * 根据HSSFCell类型设置数据
     *
     * @param cell 单元格
     * @return 单元格的内容
     */
    private String getCellFormatValue(HSSFCell cell) {
        String cellvalue = "";
        if (cell != null) {
            // 判断当前Cell的Type
            switch (cell.getCellTypeEnum()) {
                case NUMERIC:
                    //公式
                case FORMULA: {
                    // 判断当前的cell是否为Date
                    if (HSSFDateUtil.isCellDateFormatted(cell)) {
                        Date date = cell.getDateCellValue();
                        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
                        cellvalue = sdf.format(date);
                    }
                    // 如果是纯数字
                    else {
                        cellvalue = String.valueOf(cell.getNumericCellValue());
                    }
                    break;
                }
                case STRING:
                    cellvalue = cell.getRichStringCellValue().getString();
                    break;
                default:
                    cellvalue = " ";
            }
        }
        return cellvalue;

    }

    public static void main(String[] args) {
        try {
            // 对读取Excel表格标题测试
            InputStream is = new FileInputStream("d:\\test2.xls");
            ImportExcelUtil excelReader = new ImportExcelUtil();
            String[] title = excelReader.readExcelTitle(is);
            System.out.println("获得Excel表格的标题:");
            for (String s : title) {
                System.out.print(s + " ");
            }
            System.out.println();

            // 对读取Excel表格内容测试
            InputStream is2 = new FileInputStream("d:\\test2.xls");
            Map<Integer, String> map = excelReader.readExcelContent(is2);
            System.out.println("获得Excel表格的内容:");
            //这里由于xls合并了单元格需要对索引特殊处理
            for (int i = 2; i <= map.size() + 1; i++) {
                System.out.println(map.get(i));
            }

        } catch (FileNotFoundException e) {
            System.out.println("未找到指定路径的文件!");
            e.printStackTrace();
        }
    }

}
