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

    /*�������Ҫ��ѯ������: ���۵�ѧ��*/
    private Student studentObj;
    public void setStudentObj(Student studentObj) {
        this.studentObj = studentObj;
    }
    public Student getStudentObj() {
        return this.studentObj;
    }

    /*�������Ҫ��ѯ������: ��������ʦ*/
    private Teacher teacherObj;
    public void setTeacherObj(Teacher teacherObj) {
        this.teacherObj = teacherObj;
    }
    public Teacher getTeacherObj() {
        return this.teacherObj;
    }

    /*��ǰ�ڼ�ҳ*/
    private int currentPage;
    public void setCurrentPage(int currentPage) {
        this.currentPage = currentPage;
    }
    public int getCurrentPage() {
        return currentPage;
    }

    /*һ������ҳ*/
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

    /*��ǰ��ѯ���ܼ�¼��Ŀ*/
    private int recordNumber;
    public void setRecordNumber(int recordNumber) {
        this.recordNumber = recordNumber;
    }
    public int getRecordNumber() {
        return recordNumber;
    }

    /*ҵ������*/
    @Resource StudentDAO studentDAO;
    @Resource TeacherDAO teacherDAO;
    @Resource QuestionResultDAO questionResultDAO;

    /*��������QuestionResult����*/
    private QuestionResult questionResult;
    public void setQuestionResult(QuestionResult questionResult) {
        this.questionResult = questionResult;
    }
    public QuestionResult getQuestionResult() {
        return this.questionResult;
    }

    /*��ת�����QuestionResult��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Student��Ϣ*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        /*��ѯ���е�Teacher��Ϣ*/
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        return "add_view";
    }

    /*���QuestionResult��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddQuestionResult() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentObj = studentDAO.GetStudentByStudentNumber(questionResult.getStudentObj().getStudentNumber());
            questionResult.setStudentObj(studentObj);
            Teacher teacherObj = teacherDAO.GetTeacherByTeacherNumber(questionResult.getTeacherObj().getTeacherNumber());
            questionResult.setTeacherObj(teacherObj);
            questionResultDAO.AddQuestionResult(questionResult);
            ctx.put("message",  java.net.URLEncoder.encode("QuestionResult��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("QuestionResult���ʧ��!"));
            return "error";
        }
    }

    /*��ѯQuestionResult��Ϣ*/
    public String QueryQuestionResult() {
        if(currentPage == 0) currentPage = 1;
        List<QuestionResult> questionResultList = questionResultDAO.QueryQuestionResultInfo(studentObj, teacherObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        questionResultDAO.CalculateTotalPageAndRecordNumber(studentObj, teacherObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = questionResultDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��̨������excel*/
    public String QueryQuestionResultOutputToExcel() { 
        List<QuestionResult> questionResultList = questionResultDAO.QueryQuestionResultInfo(studentObj,teacherObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "QuestionResult��Ϣ��¼"; 
        String[] headers = { "���۵�ѧ��","��������ʦ","�ʾ�ش�"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"QuestionResult.xls");//filename�����ص�xls���������������Ӣ�� 
			response.setContentType("application/msexcel;charset=UTF-8");//�������� 
			response.setHeader("Pragma","No-cache");//����ͷ 
			response.setHeader("Cache-Control","no-cache");//����ͷ 
			response.setDateHeader("Expires", 0);//��������ͷ  
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
    /*ǰ̨��ѯQuestionResult��Ϣ*/
    public String FrontQueryQuestionResult() {
        if(currentPage == 0) currentPage = 1;
        List<QuestionResult> questionResultList = questionResultDAO.QueryQuestionResultInfo(studentObj, teacherObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        questionResultDAO.CalculateTotalPageAndRecordNumber(studentObj, teacherObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = questionResultDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
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

    /*��ѯҪ�޸ĵ�QuestionResult��Ϣ*/
    public String ModifyQuestionResultQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������resultId��ȡQuestionResult����*/
        QuestionResult questionResult = questionResultDAO.GetQuestionResultByResultId(resultId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("questionResult",  questionResult);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�QuestionResult��Ϣ*/
    public String FrontShowQuestionResultQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������resultId��ȡQuestionResult����*/
        QuestionResult questionResult = questionResultDAO.GetQuestionResultByResultId(resultId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("questionResult",  questionResult);
        return "front_show_view";
    }

    /*�����޸�QuestionResult��Ϣ*/
    public String ModifyQuestionResult() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentObj = studentDAO.GetStudentByStudentNumber(questionResult.getStudentObj().getStudentNumber());
            questionResult.setStudentObj(studentObj);
            Teacher teacherObj = teacherDAO.GetTeacherByTeacherNumber(questionResult.getTeacherObj().getTeacherNumber());
            questionResult.setTeacherObj(teacherObj);
            questionResultDAO.UpdateQuestionResult(questionResult);
            ctx.put("message",  java.net.URLEncoder.encode("QuestionResult��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("QuestionResult��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��QuestionResult��Ϣ*/
    public String DeleteQuestionResult() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            questionResultDAO.DeleteQuestionResult(resultId);
            ctx.put("message",  java.net.URLEncoder.encode("QuestionResultɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("QuestionResultɾ��ʧ��!"));
            return "error";
        }
    }

}
