package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Teacher;
import com.mobileserver.util.DB;

public class TeacherDAO {

	public List<Teacher> QueryTeacher(String teacherNumber,String name,String sex,String courseName,String telephone) {
		List<Teacher> teacherList = new ArrayList<Teacher>();
		DB db = new DB();
		String sql = "select * from Teacher where 1=1";
		if (!teacherNumber.equals(""))
			sql += " and teacherNumber like '%" + teacherNumber + "%'";
		if (!name.equals(""))
			sql += " and name like '%" + name + "%'";
		if (!sex.equals(""))
			sql += " and sex like '%" + sex + "%'";
		if (!courseName.equals(""))
			sql += " and courseName like '%" + courseName + "%'";
		if (!telephone.equals(""))
			sql += " and telephone like '%" + telephone + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Teacher teacher = new Teacher();
				teacher.setTeacherNumber(rs.getString("teacherNumber"));
				teacher.setPassword(rs.getString("password"));
				teacher.setName(rs.getString("name"));
				teacher.setSex(rs.getString("sex"));
				teacher.setAge(rs.getInt("age"));
				teacher.setCourseName(rs.getString("courseName"));
				teacher.setTelephone(rs.getString("telephone"));
				teacher.setEmail(rs.getString("email"));
				teacher.setMemo(rs.getString("memo"));
				teacherList.add(teacher);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return teacherList;
	}
	/* �����ʦ��Ϣ���󣬽��н�ʦ��Ϣ�����ҵ�� */
	public String AddTeacher(Teacher teacher) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в����½�ʦ��Ϣ */
			String sqlString = "insert into Teacher(teacherNumber,password,name,sex,age,courseName,telephone,email,memo) values (";
			sqlString += "'" + teacher.getTeacherNumber() + "',";
			sqlString += "'" + teacher.getPassword() + "',";
			sqlString += "'" + teacher.getName() + "',";
			sqlString += "'" + teacher.getSex() + "',";
			sqlString += teacher.getAge() + ",";
			sqlString += "'" + teacher.getCourseName() + "',";
			sqlString += "'" + teacher.getTelephone() + "',";
			sqlString += "'" + teacher.getEmail() + "',";
			sqlString += "'" + teacher.getMemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "��ʦ��Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ʦ��Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ����ʦ��Ϣ */
	public String DeleteTeacher(String teacherNumber) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Teacher where teacherNumber='" + teacherNumber + "'";
			db.executeUpdate(sqlString);
			result = "��ʦ��Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ʦ��Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ���ݽ�ʦ�û�����ȡ����ʦ��Ϣ */
	public Teacher GetTeacher(String teacherNumber) {
		Teacher teacher = null;
		DB db = new DB();
		String sql = "select * from Teacher where teacherNumber='" + teacherNumber + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				teacher = new Teacher();
				teacher.setTeacherNumber(rs.getString("teacherNumber"));
				teacher.setPassword(rs.getString("password"));
				teacher.setName(rs.getString("name"));
				teacher.setSex(rs.getString("sex"));
				teacher.setAge(rs.getInt("age"));
				teacher.setCourseName(rs.getString("courseName"));
				teacher.setTelephone(rs.getString("telephone"));
				teacher.setEmail(rs.getString("email"));
				teacher.setMemo(rs.getString("memo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return teacher;
	}
	/* ���½�ʦ��Ϣ */
	public String UpdateTeacher(Teacher teacher) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Teacher set ";
			sql += "password='" + teacher.getPassword() + "',";
			sql += "name='" + teacher.getName() + "',";
			sql += "sex='" + teacher.getSex() + "',";
			sql += "age=" + teacher.getAge() + ",";
			sql += "courseName='" + teacher.getCourseName() + "',";
			sql += "telephone='" + teacher.getTelephone() + "',";
			sql += "email='" + teacher.getEmail() + "',";
			sql += "memo='" + teacher.getMemo() + "'";
			sql += " where teacherNumber='" + teacher.getTeacherNumber() + "'";
			db.executeUpdate(sql);
			result = "��ʦ��Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "��ʦ��Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
