package com.helloqidi.action;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;

import com.helloqidi.model.PageBean;
import com.helloqidi.model.Student;
import com.helloqidi.service.GradeService;
import com.helloqidi.service.StudentService;
import com.helloqidi.util.DateUtil;
import com.helloqidi.util.ResponseUtil;
import com.helloqidi.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Namespace("/")
@Action(value="student")
public class StudentAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;
	@Resource
	private StudentService studentService;
	@Resource
	private GradeService gradeService;
	
	private Student student;
	private String s_stuNo;
	private String s_stuName;
	private String s_sex;
	private String s_bbirthday;
	private String s_ebirthday;
	private String s_gradeId;
	private String page;
	private String rows;
	private String delIds;
	private String stuId;
	
	
	public Student getStudent() {
		return student;
	}


	public void setStudent(Student student) {
		this.student = student;
	}


	public String getS_stuNo() {
		return s_stuNo;
	}


	public void setS_stuNo(String s_stuNo) {
		this.s_stuNo = s_stuNo;
	}


	public String getS_stuName() {
		return s_stuName;
	}


	public void setS_stuName(String s_stuName) {
		this.s_stuName = s_stuName;
	}


	public String getS_sex() {
		return s_sex;
	}


	public void setS_sex(String s_sex) {
		this.s_sex = s_sex;
	}


	public String getS_bbirthday() {
		return s_bbirthday;
	}


	public void setS_bbirthday(String s_bbirthday) {
		this.s_bbirthday = s_bbirthday;
	}


	public String getS_ebirthday() {
		return s_ebirthday;
	}


	public void setS_ebirthday(String s_ebirthday) {
		this.s_ebirthday = s_ebirthday;
	}


	public String getS_gradeId() {
		return s_gradeId;
	}


	public void setS_gradeId(String s_gradeId) {
		this.s_gradeId = s_gradeId;
	}


	public String getPage() {
		return page;
	}


	public void setPage(String page) {
		this.page = page;
	}


	public String getRows() {
		return rows;
	}


	public void setRows(String rows) {
		this.rows = rows;
	}
	
	

	public String getDelIds() {
		return delIds;
	}


	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}
	
	
	public String getStuId() {
		return stuId;
	}


	public void setStuId(String stuId) {
		this.stuId = stuId;
	}


	@Override
	public String execute() throws Exception {
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		student=new Student();
		if(s_stuNo!=null){
			student.setStuNo(s_stuNo);
			student.setStuName(s_stuName);
			student.setSex(s_sex);
			if(StringUtil.isNotEmpty(s_gradeId)){
				student.setGradeId(Integer.parseInt(s_gradeId));
			}
		}
		try{
			JSONObject result=new JSONObject();
			List<Student> studentList=studentService.studentList(pageBean,student,s_bbirthday,s_ebirthday);
			JSONArray jsonArray=new JSONArray();
			for(int i=0;i<studentList.size();i++){
				Student student=(Student)studentList.get(i);
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("stuId", student.getStuId());
				jsonObject.put("stuNo", student.getStuNo());
				jsonObject.put("stuName", student.getStuName());
				jsonObject.put("sex", student.getSex());
				jsonObject.put("birthday", DateUtil.formatDate(student.getBirthday(), "yyyy-MM-dd"));
				jsonObject.put("gradeId", student.getGradeId());
				jsonObject.put("email", student.getEmail());
				jsonObject.put("stuDesc", student.getStuDesc());
				jsonObject.put("gradeName", gradeService.getGradeById(student.getGradeId()).getGradeName());
				jsonArray.add(jsonObject);
			}
			int total=studentService.studentCount(student,s_bbirthday,s_ebirthday);
			result.put("rows", jsonArray);
			result.put("total", total);
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String delete()throws Exception{
		try{
			JSONObject result=new JSONObject();
			int delNums=studentService.studentDelete(delIds);
			if(delNums>0){
				result.put("success", "true");
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "ɾ��ʧ��");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}

	public String save()throws Exception{
		if(StringUtil.isNotEmpty(stuId)){
			student.setStuId(Integer.parseInt(stuId));
		}
		try{
			int saveNums=0;
			JSONObject result=new JSONObject();
			saveNums=studentService.studentSave(student);
			if(saveNums>0){
				result.put("success", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "����ʧ��");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
