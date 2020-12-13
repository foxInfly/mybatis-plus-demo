package com.lp.test.mybatisplusdemo.poi.util;

import com.lp.test.mybatisplusdemo.poi.domain.po.TodayCount;
import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.ss.util.CellRangeAddress;

import java.util.List;


/**
 * @description export excel util class
 * @author lp
 * @since 2020/12/13 16:55
 **/
public class ExportExcalUtil {
    public static HSSFWorkbook creatSheet(HSSFWorkbook wb, List<TodayCount> todayCount){
        //0.建立新的sheet对象（excel的表单xls）
        HSSFSheet sheet = wb.createSheet();

        //1.新建单元格样式
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中
        cellStyle.setWrapText(true);//自动换行

        //2.设置列宽
        for(int i = 0 ; i<=21;i++){
            if (i==0){
                sheet.setColumnWidth((short) i, (short) 4500);
            }else{
                sheet.setColumnWidth((short) i, (short) 3400);
            }
        }

        //3.第一行
        HSSFRow row0=sheet.createRow(0);
        row0.setHeight((short) ((short) 30*20));//设置行高

        HSSFCell cellTitle=row0.createCell(0); //创建单元格
        HSSFCellStyle styleTitle = creatStyle(wb, "黑体", 20,HorizontalAlignment.CENTER,false,true);//设置单元格样式
        cellTitle.setCellStyle(styleTitle);
        cellTitle.setCellValue("今日各单位清单数量情况汇总表");//设置单元格内容
        sheet.addMergedRegion(new CellRangeAddress(0,0,0,21));//合并单元格CellRangeAddress构造参数依次表示起始行，截至行，起始列， 截至列

        //第二行
        // poi做多行合并，一定需要先绘制单元格，然后写入数据，最后合并，不然各种坑啊,切记啊政务小组的伙伴们
        HSSFRow row1 =sheet.createRow(1);
        row1.setHeight((short) 300);//设置行高
        HSSFCell cell1_0 = row1.createCell(0);//列
        cell1_0.setCellValue("区化");
        cell1_0.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_1 = row1.createCell(1);
        cell1_1.setCellValue("单位名称");
        cell1_1.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_2 = row1.createCell(2);
        cell1_2.setCellValue("总目录数量");
        cell1_2.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_3 = row1.createCell(3);
        sheet.addMergedRegion(new CellRangeAddress(1,1,2,3));
        cell1_3.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_4 = row1.createCell(4);
        cell1_4.setCellValue("目录认领数量");
        cell1_4.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_5 = row1.createCell(5);
        sheet.addMergedRegion(new CellRangeAddress(1,1,4,5));
        cell1_5.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_6 = row1.createCell(6);
        cell1_6.setCellValue("实施清单编制数量");
        cell1_6.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_7 = row1.createCell(7);
        sheet.addMergedRegion(new CellRangeAddress(1,1,6,7));
        cell1_7.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_8 = row1.createCell(8);
        cell1_8.setCellValue("实施清单审核数量");
        cell1_8.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_9 = row1.createCell(9);
        sheet.addMergedRegion(new CellRangeAddress(1,1,8,9));
        cell1_9.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_10 = row1.createCell(10);
        cell1_10.setCellValue("实施清单发布数量");
        cell1_10.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_11 = row1.createCell(11);
        sheet.addMergedRegion(new CellRangeAddress(1,1,10,11));
        cell1_11.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_12 = row1.createCell(12);
        cell1_12.setCellValue("办理项数量");
        cell1_12.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_13 = row1.createCell(13);
        sheet.addMergedRegion(new CellRangeAddress(1,1,12,13));
        cell1_13.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_14 = row1.createCell(14);
        cell1_14.setCellValue("事项情形化数量");
        cell1_14.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_15 = row1.createCell(15);
        sheet.addMergedRegion(new CellRangeAddress(1,1,14,15));
        cell1_15.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_16 = row1.createCell(16);
        cell1_16.setCellValue("高频事项数量");
        cell1_16.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_17 = row1.createCell(17);
        sheet.addMergedRegion(new CellRangeAddress(1,1,16,17));
        cell1_17.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_18 = row1.createCell(18);
        cell1_18.setCellValue("最多跑一次事项数量");
        cell1_18.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_19 = row1.createCell(19);
        sheet.addMergedRegion(new CellRangeAddress(1,1,18,19));
        cell1_19.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_20 = row1.createCell(20);
        cell1_20.setCellValue("不见面审批事项数量");
        cell1_20.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell1_21 = row1.createCell(21);
        sheet.addMergedRegion(new CellRangeAddress(1,1,20,21));
        cell1_21.setCellStyle(cellStyle(wb));//背景色

        //第三行
        HSSFRow row2 =sheet.createRow(2);
        row2.setHeight((short) 500);//设置行高
        HSSFCell cell2_0 = row2.createCell(0);
        sheet.addMergedRegion(new CellRangeAddress(1,2,0,0));
        HSSFCellStyle cellStyle2 = wb.createCellStyle();//新建单元格样式
        //边框
        cellStyle2.setBorderBottom(BorderStyle.MEDIUM); //下边框
        cellStyle2.setBorderLeft(BorderStyle.MEDIUM);//左边框
        cellStyle2.setBorderTop(BorderStyle.MEDIUM);//上边框
        cellStyle2.setBorderRight(BorderStyle.MEDIUM);//右边框
        cell2_0.setCellStyle(cellStyle2);//背景色

        HSSFCell cell2_1 = row2.createCell(1);
        sheet.addMergedRegion(new CellRangeAddress(1,2,1,1));
        HSSFCellStyle cellStyle1 = wb.createCellStyle();//新建单元格样式
        //边框
        cellStyle1.setBorderBottom(BorderStyle.MEDIUM); //下边框
        cellStyle1.setBorderLeft(BorderStyle.MEDIUM);//左边框
        cellStyle1.setBorderTop(BorderStyle.MEDIUM);//上边框
        cellStyle1.setBorderRight(BorderStyle.MEDIUM);//右边框
        cell2_1.setCellStyle(cellStyle1);//背景色

        HSSFCell cell2_2 = row2.createCell(2);
        cell2_2.setCellValue("行政权力类数量");
        cell2_2.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell2_3 = row2.createCell(3);
        cell2_3.setCellValue("总-公共服务数量");
        cell2_3.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell2_4 = row2.createCell(4);
        cell2_4.setCellValue("总-行政权力类数量");
        cell2_4.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell2_5 = row2.createCell(5);
        cell2_5.setCellValue("公共服务数量");
        cell2_5.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell2_6 = row2.createCell(6);
        cell2_6.setCellValue("行政权力类数量");
        cell2_6.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell2_7 = row2.createCell(7);
        cell2_7.setCellValue("公共服务数量");
        cell2_7.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell2_8 = row2.createCell(8);
        cell2_8.setCellValue("行政权力类数量");
        cell2_8.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell2_9 = row2.createCell(9);
        cell2_9.setCellValue("公共服务数量");
        cell2_9.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell2_10 = row2.createCell(10);
        cell2_10.setCellValue("行政权力类数量");
        cell2_10.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell2_11 = row2.createCell(11);
        cell2_11.setCellValue("公共服务数量");
        cell2_11.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell2_12 = row2.createCell(12);
        cell2_12.setCellValue("行政权力类数量");
        cell2_12.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell2_13 = row2.createCell(13);
        cell2_13.setCellValue("公共服务数量");
        cell2_13.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell2_14 = row2.createCell(14);
        cell2_14.setCellValue("行政权力类数量");
        cell2_14.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell2_15 = row2.createCell(15);
        cell2_15.setCellValue("公共服务数量");
        cell2_15.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell2_16 = row2.createCell(16);
        cell2_16.setCellValue("行政权力类数量");
        cell2_16.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell2_17 = row2.createCell(17);
        cell2_17.setCellValue("公共服务数量");
        cell2_17.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell2_18 = row2.createCell(18);
        cell2_18.setCellValue("行政权力类数量");
        cell2_18.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell2_19 = row2.createCell(19);
        cell2_19.setCellValue("公共服务数量");
        cell2_19.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell2_20 = row2.createCell(20);
        cell2_20.setCellValue("行政权力类数量");
        cell2_20.setCellStyle(cellStyle(wb));//背景色

        HSSFCell cell2_21 = row2.createCell(21);
        cell2_21.setCellValue("公共服务数量");
        cell2_21.setCellStyle(cellStyle(wb));//背景色

        //第四行起
        HSSFCellStyle styleContent = creatStyle(wb, "宋体", 12,HorizontalAlignment.CENTER,true,false);
        int rowNumber = 3;
        for (TodayCount tc : todayCount) {
            if(tc != null) {
                HSSFRow row3 = sheet.createRow(rowNumber);

                HSSFCell cell3_0 = row3.createCell(0);
                cell3_0.setCellValue(tc.getAreaCode());
                cell3_0.setCellStyle(styleContent);
                //
                HSSFCell cell3_1 = row3.createCell(1);
                cell3_1.setCellValue(tc.getDeptName());
                cell3_1.setCellStyle(styleContent);
                //
                HSSFCell cell3_2 = row3.createCell(2);
                if(!"".equals(tc.getTotalExecutivePower()) && tc.getTotalExecutivePower() !=null) cell3_2.setCellValue(tc.getTotalExecutivePower());// 总目录;
                cell3_2.setCellStyle(styleContent);
                //
                HSSFCell cell3_3 = row3.createCell(3);
                cell3_3.setCellStyle(styleContent);

                HSSFCell cell3_4 = row3.createCell(4);
                if(!"".equals(tc.getDirectoryExecutivePower()) && tc.getDirectoryExecutivePower() !=null)
                cell3_4.setCellValue(tc.getDirectoryExecutivePower());//已认领
                cell3_4.setCellStyle(styleContent);
                //
                HSSFCell cell3_5 = row3.createCell(5);
                cell3_5.setCellStyle(styleContent);
                //
                HSSFCell cell3_6 = row3.createCell(6);
                if(!"".equals(tc.getListRuelExecutivePower()) && tc.getListRuelExecutivePower() !=null)
                cell3_6.setCellValue(tc.getListRuelExecutivePower());// 编制
                cell3_6.setCellStyle(styleContent);
                //
                HSSFCell cell3_7 = row3.createCell(7);
                cell3_7.setCellStyle(styleContent);
                //
                HSSFCell cell3_8 = row3.createCell(8);
                if(!"".equals(tc.getListCheckExecutivePower()) && tc.getListCheckExecutivePower() !=null)
                cell3_8.setCellValue(tc.getListCheckExecutivePower());// 已审核;
                cell3_8.setCellStyle(styleContent);
                //
                HSSFCell cell3_9 = row3.createCell(9);
                cell3_9.setCellStyle(styleContent);
                //
                HSSFCell cell3_10 = row3.createCell(10);
                if(!"".equals(tc.getListPublishExecutivePower()) && tc.getListPublishExecutivePower() !=null)
                cell3_10.setCellValue(tc.getListPublishExecutivePower());// 已发布;
                cell3_10.setCellStyle(styleContent);
                //
                HSSFCell cell3_11 = row3.createCell(11);
                cell3_11.setCellStyle(styleContent);
                //
                HSSFCell cell3_12 = row3.createCell(12);
                if(!"".equals(tc.getDealExecutivePower()) && tc.getDealExecutivePower() !=null)
                cell3_12.setCellValue(tc.getDealExecutivePower());// 办理项;
                cell3_12.setCellStyle(styleContent);
                //
                HSSFCell cell3_13 = row3.createCell(13);
                cell3_13.setCellStyle(styleContent);

                HSSFCell cell3_14 = row3.createCell(14);// 情形化;
                if(!"".equals(tc.getItemExecutivePower()) && tc.getItemExecutivePower() !=null)
                cell3_14.setCellValue(tc.getItemExecutivePower());
                cell3_14.setCellStyle(styleContent);
                //
                HSSFCell cell3_15 = row3.createCell(15);
                cell3_15.setCellStyle(styleContent);
                //
                HSSFCell cell3_16 = row3.createCell(16);
                cell3_16.setCellStyle(styleContent);
                //
                HSSFCell cell3_17 = row3.createCell(17);
                cell3_17.setCellStyle(styleContent);
                //
                HSSFCell cell3_18 = row3.createCell(18);
                cell3_18.setCellStyle(styleContent);
                //
                HSSFCell cell3_19 = row3.createCell(19);
                cell3_19.setCellStyle(styleContent);

                HSSFCell cell3_20 = row3.createCell(20);
                cell3_20.setCellStyle(styleContent);

                HSSFCell cell3_21 = row3.createCell(21);
                cell3_21.setCellStyle(styleContent);
                rowNumber++;
            }
        }
        //最后一行
//        HSSFRow rowLast =sheet.createRow(rowNumber);
//        rowLast.setHeight((short) ((short) 22*20));
//        HSSFCell cellLast = rowLast.createCell(0);
//        HSSFCellStyle styleLast = creatStyle(wb, "宋体", 12, HSSFCellStyle.ALIGN_CENTER,false,false);
//        cellLast.setCellStyle(styleContent1);
//        cellLast.setCellValue("备注");
//        sheet.addMergedRegion(new CellRangeAddress(rowNumber,rowNumber,0,18));
        return wb;
    }

    /**
     * 样式工具类，可设置字体，边框，等
     * @param wb 工作簿
     * @param fontName 字体名称
     * @param fontHeightInPoints 字体大小
     * @param alignment 水平对齐方式
     * @param border 是否有边框
     * @param boldweight 字体是否加粗
     * @return HSSFCellStyle
     */
    public static HSSFCellStyle creatStyle(HSSFWorkbook wb, String fontName, int fontHeightInPoints,HorizontalAlignment alignment,boolean border,boolean boldweight){
        //1.设置内容字体
        HSSFFont fontContent = wb.createFont();
        if (boldweight)fontContent.setBold(true); // 字体加粗
        fontContent.setFontName(fontName);//字体名
        fontContent.setFontHeightInPoints((short) fontHeightInPoints);//字体大小

        //2.创建内容样式
        HSSFCellStyle styleContent = wb.createCellStyle();
        styleContent.setFont(fontContent);//设置字体
        styleContent.setWrapText(true);//设置是否换行
        styleContent.setAlignment(alignment);//水平对齐方式
        styleContent.setVerticalAlignment(VerticalAlignment.CENTER);//垂直对齐方式
        if (border){
            styleContent.setBorderBottom(BorderStyle.THIN);//下边框宽度
            styleContent.setBottomBorderColor(IndexedColors.BLUE1.getIndex());//下部边框颜色
            styleContent.setBorderLeft(BorderStyle.THIN);//左边框宽度
            styleContent.setLeftBorderColor(IndexedColors.BLUE1.getIndex());//左边框颜色
            styleContent.setBorderRight(BorderStyle.THIN);//右边框宽度
            styleContent.setRightBorderColor(IndexedColors.BLUE1.getIndex());//右边框颜色
            styleContent.setBorderTop(BorderStyle.THIN);//上边框宽度
            styleContent.setTopBorderColor(IndexedColors.BLUE1.getIndex());//上边框颜色
        }
        return styleContent;
    }

    /**
     * 设置单元格样式，边框，居中
     * @param wb 工作簿
     * @return HSSFCellStyle
     */
    public static HSSFCellStyle cellStyle(HSSFWorkbook wb){
        HSSFCellStyle cellStyle = wb.createCellStyle();//新建单元格样式
//        cellStyle.setWrapText(true);//自动换行
        //设置背景颜色
        cellStyle.setFillForegroundColor(IndexedColors.SKY_BLUE.getIndex());// 设置背景色(short) 13
        cellStyle.setFillPattern(FillPatternType.SOLID_FOREGROUND);
        //边框
        cellStyle.setBorderBottom(BorderStyle.MEDIUM); //下边框
        cellStyle.setBorderLeft(BorderStyle.MEDIUM);//左边框
        cellStyle.setBorderTop(BorderStyle.MEDIUM);//上边框
        cellStyle.setBorderRight(BorderStyle.MEDIUM);//右边框
        //居中
        cellStyle.setAlignment(HorizontalAlignment.CENTER);//水平居中
        cellStyle.setVerticalAlignment(VerticalAlignment.CENTER);//垂直居中

        return cellStyle;
    }

}