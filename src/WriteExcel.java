import org.apache.poi.hssf.usermodel.*;
import org.apache.poi.ss.usermodel.CreationHelper;

import java.io.FileOutputStream;
import java.util.Date;

/**
 * Created by 38147 on 2017/11/2.
 */
public class WriteExcel {
    public static void main(String[] args) throws Exception{

        //创建一个工作簿 即excel文件,再在该文件中创建一个sheet
        HSSFWorkbook wb=new HSSFWorkbook();
        HSSFSheet sheet=wb.createSheet("第一个sheet");

        //在sheet中创建一行
        HSSFRow row=sheet.createRow(0);

        //在该行写入各种类型的数据
        row.createCell(0).setCellValue(true);
        row.createCell(1).setCellValue("钟林森");
        row.createCell(2).setCellValue(23);

        //设置保留两位小数
        HSSFCell cell=row.createCell(3);
        cell.setCellValue(6000);
        HSSFCellStyle cellStyle = wb.createCellStyle();
        cellStyle.setDataFormat(HSSFDataFormat.getBuiltinFormat("0.00"));
        cell.setCellStyle(cellStyle);

        //在写入 日期格式的 数据需要进行特殊处理(这是一种 简单的处理方式)
        CreationHelper createHelper=wb.getCreationHelper();
        HSSFCellStyle style=wb.createCellStyle();
        style.setDataFormat(createHelper.createDataFormat().getFormat("yyyy-MM-dd"));

        cell=row.createCell(4);
        cell.setCellValue(new Date());
        cell.setCellStyle(style);

        //最后写回磁盘
        FileOutputStream out=new FileOutputStream("E:\\excel写数据.xls");
        wb.write(out);
        out.close();

        System.out.println("写完了!");
    }
}