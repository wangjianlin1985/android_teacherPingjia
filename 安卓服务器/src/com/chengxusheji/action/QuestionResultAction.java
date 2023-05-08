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
import com.chengxusheji.dao.QuestionResultDAO;
import com.chengxusheji.domain.QuestionResult;
import com.chengxusheji.dao.StudentDAO;
import com.chengxusheji.domain.Student;
import com.chengxusheji.dao.TeacherDAO;
import com.chengxusheji.domain.Teacher;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class QuestionResultAction extends BaseAction {

    /*界面层需要查询的属性: 评价的学生*/
    private Student studentObj;
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }
    public Student getStudentObj() {
        return this.studentObj;
    }

    /*界面层需要查询的属性: 被评价老师*/
    private Teacher teacherObj;
    public void setTeacherObj(Teacher teacherObj) {
        this.teacherObj = teacherObj;
    }
    public Teacher getTeacherObj() {
        return this.teacherObj;
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

    private int resultId;
    public void setResultId(int resultId) {
        this.resultId = resultId;
    }
    public int getResultId() {
        return resultId;
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
    @Resource TeacherDAO teacherDAO;
    @Resource QuestionResultDAO questionResultDAO;

    /*待操作的QuestionResult对象*/
    private QuestionResult questionResult;
    public void setQuestionResult(QuestionResult questionResult) {
        this.questionResult = questionResult;
    }
    public QuestionResult getQuestionResult() {
        return this.questionResult;
    }

    /*跳转到添加QuestionResult视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Student信息*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        /*查询所有的Teacher信息*/
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "add_view";
    }

    /*添加QuestionResult信息*/
    @SuppressWarnings("deprecation")
    public String AddQuestionResult() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentObj = studentDAO.GetStudentByStudentNumber(questionResult.getStudentObj().getStudentNumber());
            questionResult.setStudentObj(studentObj);
            Teacher teacherObj = teacherDAO.GetTeacherByTeacherNumber(questionResult.getTeacherObj().getTeacherNumber());
            questionResult.setTeacherObj(teacherObj);
            questionResultDAO.AddQuestionResult(questionResult);
            ctx.put("message",  java.net.URLEncoder.encode("QuestionResult添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("QuestionResult添加失败!"));
            return "error";
        }
    }

    /*查询QuestionResult信息*/
    public String QueryQuestionResult() {
        if(currentPage == 0) currentPage = 1;
        List<QuestionResult> questionResultList = questionResultDAO.QueryQuestionResultInfo(studentObj, teacherObj, currentPage);
        /*计算总的页数和总的记录数*/
        questionResultDAO.CalculateTotalPageAndRecordNumber(studentObj, teacherObj);
        /*获取到总的页码数目*/
        totalPage = questionResultDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = questionResultDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("questionResultList",  questionResultList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("studentObj", studentObj);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("teacherObj", teacherObj);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryQuestionResultOutputToExcel() { 
        List<QuestionResult> questionResultList = questionResultDAO.QueryQuestionResultInfo(studentObj,teacherObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "QuestionResult信息记录"; 
        String[] headers = { "评价的学生","被评价老师","问卷回答"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<questionResultList.size();i++) {
        	QuestionResult questionResult = questionResultList.get(i); 
        	dataset.add(new String[]{questionResult.getStudentObj().getName(),
questionResult.getTeacherObj().getName(),
questionResult.getAnswer()});
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
			response.setHeader("Content-disposition","attachment; filename="+"QuestionResult.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询QuestionResult信息*/
    public String FrontQueryQuestionResult() {
        if(currentPage == 0) currentPage = 1;
        List<QuestionResult> questionResultList = questionResultDAO.QueryQuestionResultInfo(studentObj, teacherObj, currentPage);
        /*计算总的页数和总的记录数*/
        questionResultDAO.CalculateTotalPageAndRecordNumber(studentObj, teacherObj);
        /*获取到总的页码数目*/
        totalPage = questionResultDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = questionResultDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("questionResultList",  questionResultList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("studentObj", studentObj);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("teacherObj", teacherObj);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "front_query_view";
    }

    /*查询要修改的QuestionResult信息*/
    public String ModifyQuestionResultQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键resultId获取QuestionResult对象*/
        QuestionResult questionResult = questionResultDAO.GetQuestionResultByResultId(resultId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("questionResult",  questionResult);
        return "modify_view";
    }

    /*查询要修改的QuestionResult信息*/
    public String FrontShowQuestionResultQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键resultId获取QuestionResult对象*/
        QuestionResult questionResult = questionResultDAO.GetQuestionResultByResultId(resultId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("questionResult",  questionResult);
        return "front_show_view";
    }

    /*更新修改QuestionResult信息*/
    public String ModifyQuestionResult() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentObj = studentDAO.GetStudentByStudentNumber(questionResult.getStudentObj().getStudentNumber());
            questionResult.setStudentObj(studentObj);
            Teacher teacherObj = teacherDAO.GetTeacherByTeacherNumber(questionResult.getTeacherObj().getTeacherNumber());
            questionResult.setTeacherObj(teacherObj);
            questionResultDAO.UpdateQuestionResult(questionResult);
            ctx.put("message",  java.net.URLEncoder.encode("QuestionResult信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("QuestionResult信息更新失败!"));
            return "error";
       }
   }

    /*删除QuestionResult信息*/
    public String DeleteQuestionResult() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            questionResultDAO.DeleteQuestionResult(resultId);
            ctx.put("message",  java.net.URLEncoder.encode("QuestionResult删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("QuestionResult删除失败!"));
            return "error";
        }
    }

}
