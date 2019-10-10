package com.ysd.controller;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFCell;
import org.apache.poi.xssf.usermodel.XSSFRow;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.ysd.entity.Memberships;
import com.ysd.entity.Sections;
import com.ysd.entity.Students;
import com.ysd.form.StudentForm;
import com.ysd.repository.MembershipsRepository;
import com.ysd.service.StudentService;
import com.ysd.util.ExportExcel;
import com.ysd.util.PageUtil;

import net.sf.json.JSONObject;

@RestController
@RequestMapping("/student")
public class StudentController {
	@Autowired
	private StudentService studentService;
	
	@Autowired
	private MembershipsRepository membershipsRepository;
	
	final String[]  STUDENT_TITLE= {"姓名","性别","学号","备注","专业"};
	final String STUDENT_FILENAME = "student.xlsx";
	
	@GetMapping("/findStudentAll")
	public PageUtil findStudentAll(StudentForm studentForm
			,@RequestParam(value = "page",defaultValue = "1") Integer page
			,@RequestParam(value = "rows",defaultValue = "10")Integer rows) {
		return studentService.findStudentAll(studentForm, page, rows);
	}
	
	@PostMapping("/insertAndEdit")
	public Map<String, Object> insertAndEdit(StudentForm studentForm) {
		return studentService.insertAndEdit(studentForm);
	}
	
	@GetMapping("/findStudentById")
	public Students findStudentById(Integer id) {
		return studentService.findStudentById(id);
	}

	@GetMapping("/deleteByid")
	public Map<String, Object> deleteByid(Integer id) {
		return studentService.deleteByid(id);
	}
	
	@PostMapping("/addBatchDevice")
	public JSONObject addBatchDevice(@RequestParam(value = "file", required = false) MultipartFile file) throws Exception{
		JSONObject json = new JSONObject();
		try {
			json.put("state", "1");  // 成功
            XSSFWorkbook workbook=new XSSFWorkbook(file.getInputStream());
            XSSFSheet hssfSheet = workbook.getSheetAt(0);//根据名称获得指定Sheet对象
            
            List<Students> stuList=new ArrayList<Students>();
            for (Row row : hssfSheet) {
            	Students students=new Students();
            	int rowNum = row.getRowNum();
            	if(rowNum == 0){//跳出第一行   一般第一行都是表头没有数据意义
                    continue;
                }
            	if(row.getCell(0)!=null){//第1列数据
            		students.setName(row.getCell(0).toString());
                }
            	if(row.getCell(1)!=null){//第2列数据
            		students.setSex(row.getCell(1).toString());
                }
            	if(row.getCell(2)!=null){//第3列数据
            		String string = row.getCell(2).toString();
            		string=string.substring(0,string.length()-2);
            		students.setStuNo(string);
                }
            	if(row.getCell(3)!=null){//第4列数据
            		students.setRemark(row.getCell(3).toString());
                }
            	
            	if(row.getCell(4)!=null){//第5列数据
            		String specialty = row.getCell(4).toString();
            		Memberships memberships = membershipsRepository.findDepartmentBySpecialty(specialty);
            		students.setMemberships(memberships);
                }
            	stuList.add(students);
            }
            List<Students> list = queryExcelList(stuList);
            if(null !=list && list.size()>0) {
            	String result="";
            	for(int i=0;i<list.size();i++) {// 重复数据拼接返回
            		result+="'"+list.get(i).getStuNo()+"',<br/>";
            	}
            	result = result.substring(0,result.length()-1);
            	json.put("state", "0");  // 学号已经存在，不可重复上传
            	json.put("exsitCertNums", result);  // 
            	return json;
            }
            Integer result = studentService.saveStudentAll(stuList);
            json.put("msg", "成功添加--"+result+"--个学生");
            return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 查询学号重复的数据
	 * @param sList
	 * @return
	 */
	public List<Students> queryExcelList(List<Students> sList){
		List<Students> list=new ArrayList<Students>();
    	for(int i=0;i<sList.size();i++) {// 查询是否有重复
    		String string = sList.get(i).getStuNo();
    		Students stuNo = studentService.findStudentByStuNo(string);
    		if(stuNo!=null) {
    			list.add(sList.get(i));
    		}
    	}
    	return list;
	}
	
	
	@GetMapping("/downloadExcel")
	public void downloadExcel(HttpServletResponse response) throws Exception {
		ExportExcel exportExcel = new ExportExcel();
		List<Sections> sectionsList= new ArrayList<Sections>();
		exportExcel.downloadExcel(STUDENT_FILENAME, STUDENT_TITLE,membershipsRepository.findAll(),sectionsList, response);
	 }
	
	@GetMapping("/exportExcel")
	public void exportExcel(String id,HttpServletResponse response)throws Exception {
		String[] split = id.split(",");
		List<Students> list=new ArrayList<Students>();
		for(int i=0;i<split.length;i++) {
			Students students = studentService.findStudentById(Integer.valueOf(split[i]));
			list.add(students);
		}
		ExportExcel.exportStudentExcel(STUDENT_FILENAME,STUDENT_TITLE, list, response);
	}
	
}
