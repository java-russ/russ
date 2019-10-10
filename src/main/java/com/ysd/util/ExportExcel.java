package com.ysd.util;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.nio.charset.StandardCharsets;
import java.util.ArrayList;
import java.util.List;

import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.util.CellRangeAddressList;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFDataValidation;
import org.apache.poi.xssf.usermodel.XSSFDataValidationConstraint;
import org.apache.poi.xssf.usermodel.XSSFDataValidationHelper;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import com.ysd.entity.Memberships;
import com.ysd.entity.Sections;
import com.ysd.entity.Students;
import com.ysd.entity.Teachers;

public class ExportExcel {
	/**
	 * 制作 excel模板
	 * @param fileName excel文件的默认文件名 title.length>3
	 * @param title excel文件的标题  String[] title= {"姓名","性别","备注","科室"};
	 * @param response HttpServletResponse
	 * @throws IOException
	 */
	
	public void downloadExcel(String fileName,String [] title,List<Memberships> listMemberships,List<Sections> listSections,HttpServletResponse response) throws IOException {
         int rows = 500;//数据总行数  
         int beginAndEnd=title.length>4 ? 4:3; //判断是老师标题or学生标题
         //生成下拉框内容
         List<String> str=new ArrayList<String>();
         if(beginAndEnd==4) {
        	 for(Memberships m : listMemberships) {
        		 str.add(m.getSpecialty());
        	 }
         }
         if(beginAndEnd==3) {
        	 for(Sections s : listSections) {
        		 str.add(s.getSname());
        	 }
         }
         String [] vehicles=new String[str.size()];
         str.toArray(vehicles);
         String [] vehiclesSex={"男","女"};
		 XSSFWorkbook workbook=new XSSFWorkbook();
		 XSSFSheet sheet = workbook.createSheet("title");
		 
		 XSSFDataValidationHelper dvHelper = new XSSFDataValidationHelper(sheet);//下拉框
		 XSSFDataValidationConstraint dvConstraint = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(vehicles);
		 XSSFDataValidationConstraint dvConstraint1 = (XSSFDataValidationConstraint) dvHelper.createExplicitListConstraint(vehiclesSex);
		 
		 
		 CellRangeAddressList addressList1 = new CellRangeAddressList(1, rows, 1, 1);//起始行,终止行,起始列，终止列
		 CellRangeAddressList addressList = new CellRangeAddressList(1, rows, beginAndEnd, beginAndEnd);//起始行,终止行,起始列，终止列
		 XSSFDataValidation validation =(XSSFDataValidation) dvHelper.createValidation(dvConstraint, addressList);
		 XSSFDataValidation validation1 =(XSSFDataValidation) dvHelper.createValidation(dvConstraint1, addressList1);
         sheet.addValidationData(validation);
         sheet.addValidationData(validation1);
		    
		 XSSFRow row1 = sheet.createRow(0);
		 XSSFCell cell = row1.createCell(0);
		 row1.setHeightInPoints(25);//设置高度
		 
		 for(int i=0;i<title.length;i++) {
			 cell=row1.createCell(i);
			 cell.setCellValue(title[i]);
		 }
		 ExportExcel.myExcel(workbook, response, fileName);
	}
	
	public static void exportTeacherExcel(String fileName,String [] title,List<Teachers> list,HttpServletResponse response)throws IOException {
		 XSSFWorkbook workbook=new XSSFWorkbook();
		 XSSFSheet sheet = workbook.createSheet("teacher");
		 
		 XSSFRow row0 = sheet.createRow(0);
		 XSSFCell cell0 = row0.createCell(0);
		 for(int i=0;i<title.length;i++) {
			 cell0=row0.createCell(i);
			 cell0.setCellValue(title[i]);
		 }
		 
		 for(int i=0;i<list.size();i++) {
			 XSSFRow row1 = sheet.createRow(i+1);
			 
			 XSSFCell cell = row1.createCell(0);
			 cell.setCellValue(list.get(i).getName());
			 
			 XSSFCell cell1 = row1.createCell(1);
			 cell1.setCellValue(list.get(i).getSex());
			 
			 XSSFCell cell2 = row1.createCell(2);
			 cell2.setCellValue(list.get(i).getRemark());
			 
			 XSSFCell cell3 = row1.createCell(3);
			 cell3.setCellValue(list.get(i).getSections().getSname());
			 
		 }
		 ExportExcel.myExcel(workbook, response, fileName);
	}
	
	/**
	 * 导出学生信息
	 * @param fileName
	 * @param title
	 * @param list
	 * @param response
	 * @throws IOException
	 */
	public static void exportStudentExcel(String fileName,String [] title,List<Students> list,HttpServletResponse response)throws IOException {
		 XSSFWorkbook workbook=new XSSFWorkbook();
		 XSSFSheet sheet = workbook.createSheet("student");
		 
		 XSSFRow row0 = sheet.createRow(0);
		 XSSFCell cell0 = row0.createCell(0);
		 for(int i=0;i<title.length;i++) {
			 cell0=row0.createCell(i);
			 cell0.setCellValue(title[i]);
		 }
		 
		 for(int i=0;i<list.size();i++) {
			 XSSFRow row1 = sheet.createRow(i+1);
			 
			 XSSFCell cell = row1.createCell(0);
			 cell.setCellValue(list.get(i).getName());
			 
			 XSSFCell cell1 = row1.createCell(1);
			 cell1.setCellValue(list.get(i).getSex());
			 
			 XSSFCell cell2 = row1.createCell(2);
			 cell2.setCellValue(list.get(i).getStuNo());
			 
			 XSSFCell cell3 = row1.createCell(3);
			 cell3.setCellValue(list.get(i).getRemark());
			 
			 XSSFCell cell4 = row1.createCell(4);
			 cell4.setCellValue(list.get(i).getMemberships().getSpecialty());
		 }
		 ExportExcel.myExcel(workbook, response, fileName);
	}
	
	/**
	 * 
	 * @param wb 创建XSSFWorkbook对象(excel的文档对象)
	 * @param res HttpServletResponse相应
	 * @param name 需要保存的文件名称
	 * @throws IOException 异常抛出
	 */
	public static void myExcel(XSSFWorkbook wb,HttpServletResponse res,String name) throws IOException {
		ByteArrayOutputStream fos = null;
		byte[] retArr = null;
		try {
			fos = new ByteArrayOutputStream();
			wb.write(fos);
			retArr = fos.toByteArray();
		} catch (FileNotFoundException e) {
			e.printStackTrace();
		} catch (IOException e) {
			e.printStackTrace();
		} finally {
			try {
				fos.close();
			} catch (IOException e) {
				 
				e.printStackTrace();
			}
		}
		OutputStream os = res.getOutputStream();
		try {
			res.reset();
			//res.setHeader("Content-Disposition", "attachment; filename="+name);//
			//res.setContentType("application/octet-stream;charset=ISO-8859-1");
			//避免中文乱码
			res.setHeader("Content-Disposition", "attachment;filename=" + new String(name.getBytes(), StandardCharsets.ISO_8859_1));
			res.setHeader("Connection", "close");
	        //设置传输的类型
			res.setHeader("Content-Type", "application/octet-stream");
			res.setHeader("Content-Transfer-Encoding", "chunked");
			res.setHeader("Access-Control-Allow-Origin", "*");
			res.setContentType("application/OCTET-STREAM");
			os.write(retArr);
			os.flush();
		} finally {
			if (os != null) {
				os.close();
			}
		}
	}
}
