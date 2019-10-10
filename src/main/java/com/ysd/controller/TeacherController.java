package com.ysd.controller;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
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
import com.ysd.entity.Teachers;
import com.ysd.enums.DataEnum;
import com.ysd.form.TeacherForm;
import com.ysd.repository.SectionsRepository;
import com.ysd.service.TeacherService;
import com.ysd.util.ExportExcel;
import com.ysd.util.ImportExcel;
import com.ysd.util.PageUtil;

import net.sf.json.JSONObject;

@RestController
@RequestMapping("/teacher")
public class TeacherController {
	@Autowired
	private TeacherService teacherService;
	
	@Autowired
	private SectionsRepository sectionsRepository;
	
	final String TEACHER_FILENAME = "teacher.xlsx";// 设置文件名，根据业务需要替换成要下载的文件名
	final String[] TEACHER_TITLE= {"姓名","性别","备注","科室"};
	@GetMapping("/findTeacherAll")
	public PageUtil findTeacherAll(TeacherForm teacherForm
			,@RequestParam(value = "page",defaultValue = "1")Integer page
			,@RequestParam(value = "rows",defaultValue = "10")Integer rows) {
		return teacherService.findTeacherAll(teacherForm, page, rows);
	}
	
	@PostMapping("/insertAndEdit")
	public Map<String, Object> insertAndEdit(TeacherForm teacherForm) {
		return teacherService.insertAndEdit(teacherForm);
	}
	
	@GetMapping("/findTeachersById")
	public Teachers findTeachersById(Integer id) {
		return teacherService.findTeachersById(id);
	}

	@GetMapping("/deleteByid")
	public Map<String, Object> deleteByid(Integer id) {
		return teacherService.deleteByid(id);
	}
	
	@PostMapping("/addBatchDevice")
	public JSONObject addBatchDevice(@RequestParam(value = "file", required = false) MultipartFile file) {
		JSONObject json = new JSONObject();
		try {
			 json.put("state", "1");  // 成功
			 XSSFWorkbook workbook=new XSSFWorkbook(file.getInputStream());
	         XSSFSheet hssfSheet = workbook.getSheetAt(0);//根据名称获得指定Sheet对象
	         //存数据正确的数据
	         List<Teachers> teachers=new ArrayList<Teachers>();
	         //存数据不正确的数据
	         //List<Teachers> teachersError=new ArrayList<Teachers>();
	         for (Row row : hssfSheet) {
	        	 Teachers teacher=new Teachers();
	        	 Teachers teacherError=new Teachers();
	        	 
	        	 int rowNum = row.getRowNum();
	             if(rowNum == 0){//跳出第一行   一般第一行都是表头没有数据意义
	                 continue;
	             }
	             if(row.getCell(0)!=null){//第1列数据
	            	 teacher.setName(row.getCell(0).toString());
	             }
	             if(row.getCell(1)!=null){//第2列数据
	            	 teacher.setSex(row.getCell(1).toString());
	             }
	             if(row.getCell(2)!=null){//第3列数据
	            	 Sections sections = getSections(row.getCell(2).toString());
	            	 teacher.setSections(sections);
	             }
	             if(row.getCell(3)!=null){//第4列数据
	            	 teacher.setRemark(row.getCell(3).toString());
	             }
	             teachers.add(teacher);
	         }
	         Integer result = teacherService.saveTeachers(teachers);
	         json.put("msg", "成功添加--"+result+"--个老师");
	         return json;
		} catch (Exception e) {
			e.printStackTrace();
		}
		return json;
	}
	
	public Sections getSections(String sname) {
		return sectionsRepository.fintSectionsByid(sname);
	}
	
	@GetMapping("/downloadExcel")
	public void downloadExcel(HttpServletResponse response) throws Exception {
		ExportExcel exportExcel = new ExportExcel();
		List<Memberships> meList=new ArrayList<Memberships>();
		exportExcel.downloadExcel(TEACHER_FILENAME, TEACHER_TITLE, meList,sectionsRepository.findAll(),response);
	 }
	
	@GetMapping("/exportExcel")
	public void exportExcel(String id,HttpServletResponse response)throws Exception {
		String[] split = id.split(",");
		List<Teachers> list=new ArrayList<Teachers>();
		for(int i=0;i<split.length;i++) {
			Teachers teachers = teacherService.findTeachersById(Integer.valueOf(split[i]));
			list.add(teachers);
		}
		ExportExcel.exportTeacherExcel(TEACHER_FILENAME, TEACHER_TITLE, list, response);
	}
}
