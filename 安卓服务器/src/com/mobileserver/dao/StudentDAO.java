package com.mobileserver.dao;

import java.sql.ResultSet;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;

import com.mobileserver.domain.Student;
import com.mobileserver.util.DB;

public class StudentDAO {

	public List<Student> QueryStudent(String studentNumber,String name,String sex,String telephone,String qq) {
		List<Student> studentList = new ArrayList<Student>();
		DB db = new DB();
		String sql = "select * from Student where 1=1";
		if (!studentNumber.equals(""))
			sql += " and studentNumber like '%" + studentNumber + "%'";
		if (!name.equals(""))
			sql += " and name like '%" + name + "%'";
		if (!sex.equals(""))
			sql += " and sex like '%" + sex + "%'";
		if (!telephone.equals(""))
			sql += " and telephone like '%" + telephone + "%'";
		if (!qq.equals(""))
			sql += " and qq like '%" + qq + "%'";
		try {
			ResultSet rs = db.executeQuery(sql);
			while (rs.next()) {
				Student student = new Student();
				student.setStudentNumber(rs.getString("studentNumber"));
				student.setPassword(rs.getString("password"));
				student.setName(rs.getString("name"));
				student.setSex(rs.getString("sex"));
				student.setAge(rs.getInt("age"));
				student.setTelephone(rs.getString("telephone"));
				student.setQq(rs.getString("qq"));
				student.setAddress(rs.getString("address"));
				student.setMemo(rs.getString("memo"));
				studentList.add(student);
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return studentList;
	}
	/* ����ѧ����Ϣ���󣬽���ѧ����Ϣ�����ҵ�� */
	public String AddStudent(Student student) {
		DB db = new DB();
		String result = "";
		try {
			/* ����sqlִ�в�����ѧ����Ϣ */
			String sqlString = "insert into Student(studentNumber,password,name,sex,age,telephone,qq,address,memo) values (";
			sqlString += "'" + student.getStudentNumber() + "',";
			sqlString += "'" + student.getPassword() + "',";
			sqlString += "'" + student.getName() + "',";
			sqlString += "'" + student.getSex() + "',";
			sqlString += student.getAge() + ",";
			sqlString += "'" + student.getTelephone() + "',";
			sqlString += "'" + student.getQq() + "',";
			sqlString += "'" + student.getAddress() + "',";
			sqlString += "'" + student.getMemo() + "'";
			sqlString += ")";
			db.executeUpdate(sqlString);
			result = "ѧ����Ϣ��ӳɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧ����Ϣ���ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
	/* ɾ��ѧ����Ϣ */
	public String DeleteStudent(String studentNumber) {
		DB db = new DB();
		String result = "";
		try {
			String sqlString = "delete from Student where studentNumber='" + studentNumber + "'";
			db.executeUpdate(sqlString);
			result = "ѧ����Ϣɾ���ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧ����Ϣɾ��ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}

	/* ����ѧ���û�����ȡ��ѧ����Ϣ */
	public Student GetStudent(String studentNumber) {
		Student student = null;
		DB db = new DB();
		String sql = "select * from Student where studentNumber='" + studentNumber + "'";
		try {
			ResultSet rs = db.executeQuery(sql);
			if (rs.next()) {
				student = new Student();
				student.setStudentNumber(rs.getString("studentNumber"));
				student.setPassword(rs.getString("password"));
				student.setName(rs.getString("name"));
				student.setSex(rs.getString("sex"));
				student.setAge(rs.getInt("age"));
				student.setTelephone(rs.getString("telephone"));
				student.setQq(rs.getString("qq"));
				student.setAddress(rs.getString("address"));
				student.setMemo(rs.getString("memo"));
			}
		} catch (Exception e) {
			e.printStackTrace();
		} finally {
			db.all_close();
		}
		return student;
	}
	/* ����ѧ����Ϣ */
	public String UpdateStudent(Student student) {
		DB db = new DB();
		String result = "";
		try {
			String sql = "update Student set ";
			sql += "password='" + student.getPassword() + "',";
			sql += "name='" + student.getName() + "',";
			sql += "sex='" + student.getSex() + "',";
			sql += "age=" + student.getAge() + ",";
			sql += "telephone='" + student.getTelephone() + "',";
			sql += "qq='" + student.getQq() + "',";
			sql += "address='" + student.getAddress() + "',";
			sql += "memo='" + student.getMemo() + "'";
			sql += " where studentNumber='" + student.getStudentNumber() + "'";
			db.executeUpdate(sql);
			result = "ѧ����Ϣ���³ɹ�!";
		} catch (Exception e) {
			e.printStackTrace();
			result = "ѧ����Ϣ����ʧ��";
		} finally {
			db.all_close();
		}
		return result;
	}
}
