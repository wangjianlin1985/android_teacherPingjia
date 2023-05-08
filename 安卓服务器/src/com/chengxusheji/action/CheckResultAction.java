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
import com.chengxusheji.dao.CheckResultDAO;
import com.chengxusheji.domain.CheckResult;
import com.chengxusheji.dao.StudentDAO;
import com.chengxusheji.domain.Student;
import com.chengxusheji.dao.TeacherDAO;
import com.chengxusheji.domain.Teacher;
import com.chengxusheji.dao.ItemInfoDAO;
import com.chengxusheji.domain.ItemInfo;
import com.chengxusheji.dao.ResultItemDAO;
import com.chengxusheji.domain.ResultItem;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class CheckResultAction extends BaseAction {

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

    /*�������Ҫ��ѯ������: �����۵�ָ��*/
    private ItemInfo itemObj;
    public void setItemObj(ItemInfo itemObj) {
        this.itemObj = itemObj;
    }
    public ItemInfo getItemObj() {
        return this.itemObj;
    }

    /*�������Ҫ��ѯ������: ���۽��*/
    private ResultItem resultObj;
    public void setResultObj(ResultItem resultObj) {
        this.resultObj = resultObj;
    }
    public ResultItem getResultObj() {
        return this.resultObj;
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
    @Resource ItemInfoDAO itemInfoDAO;
    @Resource ResultItemDAO resultItemDAO;
    @Resource CheckResultDAO checkResultDAO;

    /*��������CheckResult����*/
    private CheckResult checkResult;
    public void setCheckResult(CheckResult checkResult) {
        this.checkResult = checkResult;
    }
    public CheckResult getCheckResult() {
        return this.checkResult;
    }

    /*��ת�����CheckResult��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*��ѯ���е�Student��Ϣ*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        /*��ѯ���е�Teacher��Ϣ*/
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        /*��ѯ���е�ItemInfo��Ϣ*/
        List<ItemInfo> itemInfoList = itemInfoDAO.QueryAllItemInfoInfo();
        ctx.put("itemInfoList", itemInfoList);
        /*��ѯ���е�ResultItem��Ϣ*/
        List<ResultItem> resultItemList = resultItemDAO.QueryAllResultItemInfo();
        ctx.put("resultItemList", resultItemList);
        return "add_view";
    }

    /*���CheckResult��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddCheckResult() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentObj = studentDAO.GetStudentByStudentNumber(checkResult.getStudentObj().getStudentNumber());
            checkResult.setStudentObj(studentObj);
            Teacher teacherObj = teacherDAO.GetTeacherByTeacherNumber(checkResult.getTeacherObj().getTeacherNumber());
            checkResult.setTeacherObj(teacherObj);
            ItemInfo itemObj = itemInfoDAO.GetItemInfoByItemId(checkResult.getItemObj().getItemId());
            checkResult.setItemObj(itemObj);
            ResultItem resultObj = resultItemDAO.GetResultItemByResultItemId(checkResult.getResultObj().getResultItemId());
            checkResult.setResultObj(resultObj);
            checkResultDAO.AddCheckResult(checkResult);
            ctx.put("message",  java.net.URLEncoder.encode("CheckResult��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CheckResult���ʧ��!"));
            return "error";
        }
    }

    /*��ѯCheckResult��Ϣ*/
    public String QueryCheckResult() {
        if(currentPage == 0) currentPage = 1;
        List<CheckResult> checkResultList = checkResultDAO.QueryCheckResultInfo(studentObj, teacherObj, itemObj, resultObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        checkResultDAO.CalculateTotalPageAndRecordNumber(studentObj, teacherObj, itemObj, resultObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = checkResultDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = checkResultDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("checkResultList",  checkResultList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("studentObj", studentObj);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("teacherObj", teacherObj);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("itemObj", itemObj);
        List<ItemInfo> itemInfoList = itemInfoDAO.QueryAllItemInfoInfo();
        ctx.put("itemInfoList", itemInfoList);
        ctx.put("resultObj", resultObj);
        List<ResultItem> resultItemList = resultItemDAO.QueryAllResultItemInfo();
        ctx.put("resultItemList", resultItemList);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryCheckResultOutputToExcel() { 
        List<CheckResult> checkResultList = checkResultDAO.QueryCheckResultInfo(studentObj,teacherObj,itemObj,resultObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "CheckResult��Ϣ��¼"; 
        String[] headers = { "���۵�ѧ��","��������ʦ","�����۵�ָ��","���۽��"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<checkResultList.size();i++) {
        	CheckResult checkResult = checkResultList.get(i); 
        	dataset.add(new String[]{checkResult.getStudentObj().getName(),
checkResult.getTeacherObj().getName(),
checkResult.getItemObj().getItemTitle(),
checkResult.getResultObj().getResultItemText()
});
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
			response.setHeader("Content-disposition","attachment; filename="+"CheckResult.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯCheckResult��Ϣ*/
    public String FrontQueryCheckResult() {
        if(currentPage == 0) currentPage = 1;
        List<CheckResult> checkResultList = checkResultDAO.QueryCheckResultInfo(studentObj, teacherObj, itemObj, resultObj, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        checkResultDAO.CalculateTotalPageAndRecordNumber(studentObj, teacherObj, itemObj, resultObj);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = checkResultDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = checkResultDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("checkResultList",  checkResultList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("studentObj", studentObj);
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        ctx.put("teacherObj", teacherObj);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        ctx.put("itemObj", itemObj);
        List<ItemInfo> itemInfoList = itemInfoDAO.QueryAllItemInfoInfo();
        ctx.put("itemInfoList", itemInfoList);
        ctx.put("resultObj", resultObj);
        List<ResultItem> resultItemList = resultItemDAO.QueryAllResultItemInfo();
        ctx.put("resultItemList", resultItemList);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�CheckResult��Ϣ*/
    public String ModifyCheckResultQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������resultId��ȡCheckResult����*/
        CheckResult checkResult = checkResultDAO.GetCheckResultByResultId(resultId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        List<ItemInfo> itemInfoList = itemInfoDAO.QueryAllItemInfoInfo();
        ctx.put("itemInfoList", itemInfoList);
        List<ResultItem> resultItemList = resultItemDAO.QueryAllResultItemInfo();
        ctx.put("resultItemList", resultItemList);
        ctx.put("checkResult",  checkResult);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�CheckResult��Ϣ*/
    public String FrontShowCheckResultQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������resultId��ȡCheckResult����*/
        CheckResult checkResult = checkResultDAO.GetCheckResultByResultId(resultId);

        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        List<ItemInfo> itemInfoList = itemInfoDAO.QueryAllItemInfoInfo();
        ctx.put("itemInfoList", itemInfoList);
        List<ResultItem> resultItemList = resultItemDAO.QueryAllResultItemInfo();
        ctx.put("resultItemList", resultItemList);
        ctx.put("checkResult",  checkResult);
        return "front_show_view";
    }

    /*�����޸�CheckResult��Ϣ*/
    public String ModifyCheckResult() {
        ActionContext ctx = ActionContext.getContext();
        try {
            Student studentObj = studentDAO.GetStudentByStudentNumber(checkResult.getStudentObj().getStudentNumber());
            checkResult.setStudentObj(studentObj);
            Teacher teacherObj = teacherDAO.GetTeacherByTeacherNumber(checkResult.getTeacherObj().getTeacherNumber());
            checkResult.setTeacherObj(teacherObj);
            ItemInfo itemObj = itemInfoDAO.GetItemInfoByItemId(checkResult.getItemObj().getItemId());
            checkResult.setItemObj(itemObj);
            ResultItem resultObj = resultItemDAO.GetResultItemByResultItemId(checkResult.getResultObj().getResultItemId());
            checkResult.setResultObj(resultObj);
            checkResultDAO.UpdateCheckResult(checkResult);
            ctx.put("message",  java.net.URLEncoder.encode("CheckResult��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CheckResult��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��CheckResult��Ϣ*/
    public String DeleteCheckResult() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            checkResultDAO.DeleteCheckResult(resultId);
            ctx.put("message",  java.net.URLEncoder.encode("CheckResultɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CheckResultɾ��ʧ��!"));
            return "error";
        }
    }

}
