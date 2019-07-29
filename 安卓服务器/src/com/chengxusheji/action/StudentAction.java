package com.chengxusheji.action;

import java.io.File;
import java.io.IOException;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import org.apache.struts2.ServletActionContext;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Controller;
import java.util.List;
import javax.annotation.Resource;
import javax.servlet.http.HttpServletResponse;
import com.opensymphony.xwork2.ActionContext;
import com.chengxusheji.dao.StudentDAO;
import com.chengxusheji.domain.Student;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class StudentAction extends BaseAction {

    /*界面层需要查询的属性: 学生用户名*/
    private String studentNumber;
    public void setStudentNumber(String studentNumber) {
        this.studentNumber = studentNumber;
    }
    public String getStudentNumber() {
        return this.studentNumber;
    }

    /*界面层需要查询的属性: 学生姓名*/
    private String name;
    public void setName(String name) {
        this.name = name;
    }
    public String getName() {
        return this.name;
    }

    /*界面层需要查询的属性: 学生性别*/
    private String sex;
    public void setSex(String sex) {
        this.sex = sex;
    }
    public String getSex() {
        return this.sex;
    }

    /*界面层需要查询的属性: 联系电话*/
    private String telephone;
    public void setTelephone(String telephone) {
        this.telephone = telephone;
    }
    public String getTelephone() {
        return this.telephone;
    }

    /*界面层需要查询的属性: 联系qq*/
    private String qq;
    public void setQq(String qq) {
        this.qq = qq;
    }
    public String getQq() {
        return this.qq;
    }

    /*当前第几页*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*一共多少页*/
    private int totalPage;
    public void setTotalPage(int totalPage) {
        this.totalPage = totalPage;
    }
    public int getTotalPage() {
        return totalPage;
    }

    /*当前查询的总记录数目*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*业务层对象*/
    @Resource StudentDAO studentDAO;

    /*待操作的Student对象*/
    private Student student;
    public void setStudent(Student student) {
        this.student = student;
    }
    public Student getStudent() {
        return this.student;
    }

    /*跳转到添加Student视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加Student信息*/
    @SuppressWarnings("deprecation")
    public String AddStudent() {
        ActionContext ctx = ActionContext.getContext();
        /*验证学生用户名是否已经存在*/
        String studentNumber = student.getStudentNumber();
        Student db_student = studentDAO.GetStudentByStudentNumber(studentNumber);
        if(null != db_student) {
            ctx.put("error",  java.net.URLEncoder.encode("该学生用户名已经存在!"));
            return "error";
        }
        try {
            studentDAO.AddStudent(student);
            ctx.put("message",  java.net.URLEncoder.encode("Student添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Student添加失败!"));
            return "error";
        }
    }

    /*查询Student信息*/
    public String QueryStudent() {
        if(currentPage == 0) currentPage = 1;
        if(studentNumber == null) studentNumber = "";
        if(name == null) name = "";
        if(sex == null) sex = "";
        if(telephone == null) telephone = "";
        if(qq == null) qq = "";
        List<Student> studentList = studentDAO.QueryStudentInfo(studentNumber, name, sex, telephone, qq, currentPage);
        /*计算总的页数和总的记录数*/
        studentDAO.CalculateTotalPageAndRecordNumber(studentNumber, name, sex, telephone, qq);
        /*获取到总的页码数目*/
        totalPage = studentDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = studentDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("studentList",  studentList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("studentNumber", studentNumber);
        ctx.put("name", name);
        ctx.put("sex", sex);
        ctx.put("telephone", telephone);
        ctx.put("qq", qq);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryStudentOutputToExcel() { 
        if(studentNumber == null) studentNumber = "";
        if(name == null) name = "";
        if(sex == null) sex = "";
        if(telephone == null) telephone = "";
        if(qq == null) qq = "";
        List<Student> studentList = studentDAO.QueryStudentInfo(studentNumber,name,sex,telephone,qq);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "Student信息记录"; 
        String[] headers = { "学生用户名","学生姓名","学生性别","学生年龄","联系电话","联系qq"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<studentList.size();i++) {
        	Student student = studentList.get(i); 
        	dataset.add(new String[]{student.getStudentNumber(),student.getName(),student.getSex(),student.getAge() + "",student.getTelephone(),student.getQq()});
        }
        /*
        OutputStream out = null;
		try {
			out = new FileOutputStream("C://output.xls");
			ex.exportExcel(title,headers, dataset, out);
		    out.close();
		} catch (Exception e) {
			e.printStackTrace();
		}
		*/
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"Student.xls");//filename是下载的xls的名，建议最好用英文 
			response.setContentType("application/msexcel;charset=UTF-8");//设置类型 
			response.setHeader("Pragma","No-cache");//设置头 
			response.setHeader("Cache-Control","no-cache");//设置头 
			response.setDateHeader("Expires", 0);//设置日期头  
			String rootPath = ServletActionContext.getServletContext().getRealPath("/");
			ex.exportExcel(rootPath,title,headers, dataset, out);
			out.flush();
		} catch (IOException e) { 
			e.printStackTrace(); 
		}finally{
			try{
				if(out!=null){ 
					out.close(); 
				}
			}catch(IOException e){ 
				e.printStackTrace(); 
			} 
		}
		return null;
    }
    /*前台查询Student信息*/
    public String FrontQueryStudent() {
        if(currentPage == 0) currentPage = 1;
        if(studentNumber == null) studentNumber = "";
        if(name == null) name = "";
        if(sex == null) sex = "";
        if(telephone == null) telephone = "";
        if(qq == null) qq = "";
        List<Student> studentList = studentDAO.QueryStudentInfo(studentNumber, name, sex, telephone, qq, currentPage);
        /*计算总的页数和总的记录数*/
        studentDAO.CalculateTotalPageAndRecordNumber(studentNumber, name, sex, telephone, qq);
        /*获取到总的页码数目*/
        totalPage = studentDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = studentDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("studentList",  studentList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("studentNumber", studentNumber);
        ctx.put("name", name);
        ctx.put("sex", sex);
        ctx.put("telephone", telephone);
        ctx.put("qq", qq);
        return "front_query_view";
    }

    /*查询要修改的Student信息*/
    public String ModifyStudentQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键studentNumber获取Student对象*/
        Student student = studentDAO.GetStudentByStudentNumber(studentNumber);

        ctx.put("student",  student);
        return "modify_view";
    }

    /*查询要修改的Student信息*/
    public String FrontShowStudentQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键studentNumber获取Student对象*/
        Student student = studentDAO.GetStudentByStudentNumber(studentNumber);

        ctx.put("student",  student);
        return "front_show_view";
    }

    /*更新修改Student信息*/
    public String ModifyStudent() {
        ActionContext ctx = ActionContext.getContext();
        try {
            studentDAO.UpdateStudent(student);
            ctx.put("message",  java.net.URLEncoder.encode("Student信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Student信息更新失败!"));
            return "error";
       }
   }

    /*删除Student信息*/
    public String DeleteStudent() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            studentDAO.DeleteStudent(studentNumber);
            ctx.put("message",  java.net.URLEncoder.encode("Student删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("Student删除失败!"));
            return "error";
        }
    }

}
