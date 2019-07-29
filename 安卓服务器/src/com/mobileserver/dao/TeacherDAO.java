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
	/* 传入教师信息对象，进行教师信息的添加业务 */
	public String AddTeacher(Teacher teacher) {
		DB db = new DB();
		String result = "";
		try {
			/* 构建sql执行插入新教师信息 */
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
			result = "教师信息添加成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "教师信息添加失败";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* 删除教师信息 */
	public String DeleteTeacher(String teacherNumber) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Teacher where teacherNumber='" + teacherNumber + "'";
			db.executeUpdate(sqlString);
			result = "教师信息删除成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "教师信息删除失败";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* 根据教师用户名获取到教师信息 */
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
	/* 更新教师信息 */
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
			result = "教师信息更新成功!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "教师信息更新失败";
		} finally {
			db.all_close();
		}
		return result;
	}
}
