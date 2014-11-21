package com.helloqidi.dao;

import java.util.List;

import com.helloqidi.model.PageBean;
import com.helloqidi.model.Student;

public interface StudentDao {

	public List<Student> studentList(PageBean pageBean,Student student,String bbirthday,String ebirthday)throws Exception;
	
	public int studentCount(Student student,String bbirthday,String ebirthday)throws Exception;
	
	public int studentDelete(String delIds)throws Exception;
	
	public int studentSave(Student student)throws Exception;
	
	public boolean getStudentByGradeId(String gradeId)throws Exception;
}
