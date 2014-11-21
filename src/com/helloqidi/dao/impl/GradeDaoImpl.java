package com.helloqidi.dao.impl;

import java.math.BigInteger;
import java.util.List;

import javax.annotation.Resource;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Repository;

import com.helloqidi.dao.GradeDao;
import com.helloqidi.model.Grade;
import com.helloqidi.model.PageBean;
import com.helloqidi.util.StringUtil;

@Repository
public class GradeDaoImpl implements GradeDao{

	private SessionFactory sessionFactory;
	
	@SuppressWarnings("unchecked")
	public List<Grade> gradeList(PageBean pageBean,Grade grade)throws Exception{
		List<Grade> gradeList=null;
		Session session=this.getSession();
		StringBuffer sb=new StringBuffer("from Grade g");
		if(grade!=null && StringUtil.isNotEmpty(grade.getGradeName())){
			sb.append(" and g.gradeName like '%"+grade.getGradeName()+"%'");
		}
		Query query=session.createQuery(sb.toString().replaceFirst("and", "where"));
		if(pageBean!=null){
			query.setFirstResult(pageBean.getStart());
			query.setMaxResults(pageBean.getRows());
		}
		gradeList=(List<Grade>)query.list();
		return gradeList;
	}
	
	public int gradeCount(Grade grade)throws Exception{
		StringBuffer sb=new StringBuffer("select count(*) as total from t_grade");
		if(StringUtil.isNotEmpty(grade.getGradeName())){
			sb.append(" and gradeName like '%"+grade.getGradeName()+"%'");
		}
		Session session=this.getSession();
		Query query=session.createSQLQuery(sb.toString().replaceFirst("and", "where"));
		return ((BigInteger)query.uniqueResult()).intValue();
	}
	
	/**
	 * delete from tableName where field in (1,3,5)
	 * @param con
	 * @param delIds
	 * @return
	 * @throws Exception
	 */
	public int gradeDelete(String delIds)throws Exception{
		Session session=this.getSession();
		Query query=session.createSQLQuery("delete from t_grade where id in("+delIds+")");
		int count=query.executeUpdate();
		return count;
	}
	
	public int gradeSave(Grade grade)throws Exception{
		Session session=this.getSession();
		session.merge(grade);
		return 1;
	}
	
	public Grade getGradeById(int gradeId)throws Exception{
		Session session=this.getSession();
		Grade grade=(Grade) session.get(Grade.class, gradeId);
		return grade;
	}

	@Resource
	@Autowired
	public void setSessionFactory(SessionFactory sessionFactory) {
		this.sessionFactory = sessionFactory;
	}
	
	public Session getSession(){
		return sessionFactory.getCurrentSession();
	}
}
