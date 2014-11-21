package com.helloqidi.service.impl;

import java.util.List;

import javax.annotation.Resource;

import org.springframework.stereotype.Service;

import com.helloqidi.dao.StudentDao;
import com.helloqidi.model.PageBean;
import com.helloqidi.model.Student;
import com.helloqidi.service.StudentService;

@Service
public class StudentServiceImpl implements StudentService{
	
	@Resource
	private StudentDao studentDao;
	
	@Override
	public List<Student> studentList(PageBean pageBean, Student student,
			String bbirthday, String ebirthday) throws Exception {
		return studentDao.studentList(pageBean, student, bbirthday, ebirthday);
	}

	@Override
	public int studentCount(Student student, String bbirthday, String ebirthday)
			throws Exception {
		return studentDao.studentCount(student, bbirthday, ebirthday);
	}

	@Override
	public int studentDelete(String delIds) throws Exception {
		return studentDao.studentDelete(delIds);
	}

	@Override
	public int studentSave(Student student) throws Exception {
		return studentDao.studentSave(student);
	}

	@Override
	public boolean getStudentByGradeId(String gradeId) throws Exception {
		return studentDao.getStudentByGradeId(gradeId);
	}

}
