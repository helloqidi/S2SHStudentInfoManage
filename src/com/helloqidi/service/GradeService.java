package com.helloqidi.service;

import java.util.List;


import com.helloqidi.model.Grade;
import com.helloqidi.model.PageBean;

public interface GradeService {

	public List<Grade> gradeList(PageBean pageBean,Grade grade)throws Exception;
	
	public int gradeCount(Grade grade)throws Exception;
	
	public int gradeDelete(String delIds)throws Exception;
	
	public int gradeSave(Grade grade)throws Exception;
	
	public Grade getGradeById(int gradeId)throws Exception;
}
