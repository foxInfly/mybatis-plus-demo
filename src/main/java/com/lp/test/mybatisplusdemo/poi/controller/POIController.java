package com.lp.test.mybatisplusdemo.poi.controller;

import com.lp.test.mybatisplusdemo.poi.domain.po.TodayCount;
import com.lp.test.mybatisplusdemo.poi.service.TodayCountService;
import com.lp.test.mybatisplusdemo.poi.util.ExcalDownloadUtil;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.*;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import javax.annotation.Resource;
import javax.servlet.ServletOutputStream;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.FileOutputStream;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.List;

@RestController
@RequestMapping("/POI")
public class POIController {

    @Resource
    private TodayCountService todayCountService;

    @RequestMapping("/createExcel")
    public void createExcel(HttpServletRequest request, HttpServletResponse response) {
        //0.创建工作簿对象
        //HSSFWorkbook wb = new HSSFWorkbook();//生成xls格式的excel
        XSSFWorkbook wb = new XSSFWorkbook();//生成xlsx格式的excel`

        //1.创建工作表对象
        XSSFSheet sheets = wb.createSheet("九九乘法表");
        //2.创建行
        for (int i = 1; i <= 9; i++) {
            XSSFRow row = sheets.createRow(i - 1);
            //3.创建单元格
            for (int j = 1; j <= i; j++) {
                XSSFCell cell = row.createCell(j - 1);
                //4.给单元格赋值
                cell.setCellValue(i + "*" + j + "=" + i * j);
            }

        }

     /*  try {
           FileOutputStream fileOutputStream = new FileOutputStream("d:\\test.xlsx");
           try {
               wb.write(fileOutputStream);
           } catch (IOException e) {
               e.printStackTrace();
           }
       } catch (FileNotFoundException e) {
           e.printStackTrace();
       }*/
        try {
            // 一个流两个头
//            两个头：1.文件的打开方式 默认是 inline（浏览器直接打开） 我们需要的是以附件方式下载 attachment
//                   2.文件的mime类型  常见的文件类型可以不写 word Excel TXT
            String newExcel = "aaa";
            ServletOutputStream outputStream = response.getOutputStream();

            response.setHeader("Content-Disposition", "attachment; filename=" + newExcel + ".xls");
            wb.write(outputStream);
            outputStream.close();
            wb.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }


    /**导出excel
     * @description
     * @author lp
     * @since 2020/12/13 16:24
     **/
    @RequestMapping("/exportExcel")
    public void exportExcel(HttpServletResponse response,Integer typeNumber) throws IOException {
        HSSFWorkbook wb = new HSSFWorkbook();
        creatSheet(wb,typeNumber);
        response.setContentType("application/binary;charset=UTF-8");
        ServletOutputStream out=response.getOutputStream();
        response.setHeader("Content-Disposition", "attachment;fileName=" + URLEncoder.encode("111.xls", "UTF-8"));

        wb.write(out);
        out.flush();
        out.close();
    }

    /**
     * @description
     * @author lp
     * @since 2020/12/13 16:25
     **/
    private void creatSheet(HSSFWorkbook wb, Integer typeNumber){
        List<TodayCount> todayCount = todayCountService.list();
        HSSFWorkbook sheets = ExcalDownloadUtil.creatSheet(wb, todayCount);
    }


    public static void main(String[] args) throws IOException {
        //0.工作簿
        XSSFWorkbook wb = new XSSFWorkbook();//新建一个工作簿，生成xls格式的excel
        FileOutputStream fout=new FileOutputStream("G:\\project\\Idea\\demo\\mybatis-plus-demo\\src\\main\\java\\com\\lp\\test\\mybatisplusdemo\\poi\\controller\\poi.xls");
        //第一行的字体
        XSSFFont font0 = wb.createFont();
        font0.setBold(true);
        font0.setColor(IndexedColors.ORANGE.getIndex());
        font0.setFontHeightInPoints((short) 13);

        //其他行的字体
        XSSFFont font1 = wb.createFont();
        font1.setFontHeightInPoints((short) 13);

        //第一行的样式
        XSSFCellStyle cellStyle0 = wb.createCellStyle();
        cellStyle0.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle0.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        cellStyle0.setBorderBottom(BorderStyle.MEDIUM);
        cellStyle0.setBorderLeft(BorderStyle.MEDIUM);
        cellStyle0.setBorderRight(BorderStyle.MEDIUM);
        cellStyle0.setBorderTop(BorderStyle.MEDIUM);//中号
        cellStyle0.setFont(font0);
        cellStyle0.setFillForegroundColor(IndexedColors.BLUE1.getIndex());
        cellStyle0.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //其他行的样式
        XSSFCellStyle cellStyle1 = wb.createCellStyle();
        cellStyle1.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle1.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        cellStyle1.setBorderBottom(BorderStyle.MEDIUM_DASHED);
        cellStyle1.setBorderLeft(BorderStyle.MEDIUM_DASHED);
        cellStyle1.setBorderRight(BorderStyle.MEDIUM_DASHED);//中号虚线
        cellStyle1.setBorderTop(BorderStyle.MEDIUM_DASHED);
        cellStyle1.setFont(font1);


        //1.表
        Sheet sheet= wb.createSheet("第一个sheet页");//创建一个sheet页
        sheet.setColumnWidth((short) 3, (short) 8000);// 单位

        //2.0第一行
        Row row0=sheet.createRow(0); // 创建一个行 第一行
        row0.setHeight((short) 425);
        //设置单元格
        Cell cell00 = row0.createCell(0);
        cell00.setCellValue("ID");
        cell00.setCellStyle(cellStyle0);
        Cell cell01 = row0.createCell(1);
        cell01.setCellValue("name");
        cell01.setCellStyle(cellStyle0);
        Cell cell02 = row0.createCell(2);
        cell02.setCellValue("age");
        cell02.setCellStyle(cellStyle0);
        Cell cell03 = row0.createCell(3);
        cell03.setCellValue("birthday");
        cell03.setCellStyle(cellStyle0);
        Cell cell04 = row0.createCell(4);
        cell04.setCellValue("isDel");
        cell04.setCellStyle(cellStyle0);
        Cell cell05 = row0.createCell(5);
        cell05.setCellValue("desc");
        cell05.setCellStyle(cellStyle0);

        //2.0第二行
        Row row1=sheet.createRow(1); // 创建一个行 第一行
        row1.setHeight((short) 425);
        //设置单元格
        Cell cell10 = row1.createCell(0);
        cell10.setCellValue("123");
        cell10.setCellStyle(cellStyle1);
        Cell cell11 = row1.createCell(1);
        cell11.setCellValue("lp");
        cell11.setCellStyle(cellStyle1);
        Cell cell12 = row1.createCell(2);
        cell12.setCellValue("18");
        cell12.setCellStyle(cellStyle1);
        Cell cell13 = row1.createCell(3);
        cell13.setCellValue("20112-12-21");
        cell13.setCellStyle(cellStyle1);
        Cell cell14 = row1.createCell(4);
        cell14.setCellValue("true");
        cell14.setCellStyle(cellStyle1);
        Cell cell15 = row1.createCell(5);
        cell15.setCellValue("pp");
        cell15.setCellStyle(cellStyle1);

        //2.0第三行
        Row row2=sheet.createRow(2); // 创建一个行 第一行
        row2.setHeight((short) 425);
        //设置单元格
        Cell cell20 = row2.createCell(0);
        cell20.setCellValue("253");
        cell20.setCellStyle(cellStyle1);
        Cell cell21 = row2.createCell(1);
        cell21.setCellValue("qiong");
        cell21.setCellStyle(cellStyle1);
        Cell cell22 = row2.createCell(2);
        cell22.setCellValue("16");
        cell22.setCellStyle(cellStyle1);
        Cell cell23 = row2.createCell(3);
        cell23.setCellValue("20112-12-21");
        cell23.setCellStyle(cellStyle1);
        Cell cell24 = row2.createCell(4);
        cell24.setCellValue("true");
        cell24.setCellStyle(cellStyle1);
        Cell cell25 = row2.createCell(5);
        cell25.setCellValue("qq");
        cell25.setCellStyle(cellStyle1);


        wb.write(fout);//输出
        fout.close();

    }
}
