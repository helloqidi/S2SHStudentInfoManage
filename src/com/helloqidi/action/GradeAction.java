package com.helloqidi.action;

import java.util.List;

import javax.annotation.Resource;

import net.sf.json.JSONArray;
import net.sf.json.JSONObject;

import org.apache.struts2.ServletActionContext;
import org.apache.struts2.convention.annotation.Action;
import org.apache.struts2.convention.annotation.Namespace;
import org.springframework.context.annotation.Scope;

import com.helloqidi.model.Grade;
import com.helloqidi.model.PageBean;
import com.helloqidi.service.GradeService;
import com.helloqidi.service.StudentService;
import com.helloqidi.util.ResponseUtil;
import com.helloqidi.util.StringUtil;
import com.opensymphony.xwork2.ActionSupport;

@Scope("prototype")
@Namespace("/")
@Action(value="grade")
public class GradeAction extends ActionSupport{
	
	/**
	 * 
	 */
	private static final long serialVersionUID = 1L;

	@Resource
	private GradeService gradeService;
	@Resource
	private StudentService studentService;
	
	private String page;
	private String rows;
	private String s_gradeName="";
	private Grade grade;
	private String delIds;
	private String id;
	
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


	public Grade getGrade() {
		return grade;
	}


	public void setGrade(Grade grade) {
		this.grade = grade;
	}


	public String getS_gradeName() {
		return s_gradeName;
	}


	public void setS_gradeName(String s_gradeName) {
		this.s_gradeName = s_gradeName;
	}

	
	public String getDelIds() {
		return delIds;
	}


	public void setDelIds(String delIds) {
		this.delIds = delIds;
	}

	

	public String getId() {
		return id;
	}


	public void setId(String id) {
		this.id = id;
	}

	
	@Override
	public String execute() throws Exception {
		PageBean pageBean=new PageBean(Integer.parseInt(page),Integer.parseInt(rows));
		try{
			if(grade==null){
				grade=new Grade();
			}
			grade.setGradeName(s_gradeName);
			JSONObject result=new JSONObject();
			List<Grade> gradeList=gradeService.gradeList(pageBean, grade);
			JSONArray jsonArray=new JSONArray();
			for(int i=0;i<gradeList.size();i++){
				Grade grade=(Grade)gradeList.get(i);
				JSONObject jsonObject=new JSONObject();
				jsonObject.put("id", grade.getId());
				jsonObject.put("gradeName", grade.getGradeName());
				jsonObject.put("gradeDesc", grade.getGradeDesc());
				jsonArray.add(jsonObject);
			}
			int total=gradeService.gradeCount(grade);
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
			String str[]=delIds.split(",");
			for(int i=0;i<str.length;i++){
				boolean f=studentService.getStudentByGradeId(str[i]);
				if(f){
					result.put("errorIndex", i);
					result.put("errorMsg", "班级下面有学生，不能删除！");
					ResponseUtil.write(ServletActionContext.getResponse(), result);
					return null;
				}
			}
			int delNums=gradeService.gradeDelete(delIds);
			if(delNums>0){
				result.put("success", "true");
				result.put("delNums", delNums);
			}else{
				result.put("errorMsg", "删除失败");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	public String save()throws Exception{
		if(StringUtil.isNotEmpty(id)){
			grade.setId(Integer.parseInt(id));
		}
		try{
			int saveNums=0;
			JSONObject result=new JSONObject();
			saveNums=gradeService.gradeSave(grade);
			if(saveNums>0){
				result.put("success", "true");
			}else{
				result.put("success", "true");
				result.put("errorMsg", "保存失败");
			}
			ResponseUtil.write(ServletActionContext.getResponse(), result);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
	@SuppressWarnings("static-access")
	public String gradeComboList()throws Exception{
		try{
			JSONArray jsonArray=new JSONArray();
			JSONObject jsonObject=new JSONObject();
			jsonObject.put("id", "");
			jsonObject.put("gradeName", "请选择...");
			jsonArray.add(jsonObject);
			jsonArray.addAll(jsonArray.fromObject(gradeService.gradeList(null,null)));
			ResponseUtil.write(ServletActionContext.getResponse(), jsonArray);
		}catch(Exception e){
			e.printStackTrace();
		}
		return null;
	}
	
}
