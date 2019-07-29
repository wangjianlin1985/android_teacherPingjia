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

    /*界面层需要查询的属性: 被评价的指标*/
    private ItemInfo itemObj;
    public void setItemObj(ItemInfo itemObj) {
        this.itemObj = itemObj;
    }
    public ItemInfo getItemObj() {
        return this.itemObj;
    }

    /*界面层需要查询的属性: 评价结果*/
    private ResultItem resultObj;
    public void setResultObj(ResultItem resultObj) {
        this.resultObj = resultObj;
    }
    public ResultItem getResultObj() {
        return this.resultObj;
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
    @Resource ItemInfoDAO itemInfoDAO;
    @Resource ResultItemDAO resultItemDAO;
    @Resource CheckResultDAO checkResultDAO;

    /*待操作的CheckResult对象*/
    private CheckResult checkResult;
    public void setCheckResult(CheckResult checkResult) {
        this.checkResult = checkResult;
    }
    public CheckResult getCheckResult() {
        return this.checkResult;
    }

    /*跳转到添加CheckResult视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        /*查询所有的Student信息*/
        List<Student> studentList = studentDAO.QueryAllStudentInfo();
        ctx.put("studentList", studentList);
        /*查询所有的Teacher信息*/
        List<Teacher> teacherList = teacherDAO.QueryAllTeacherInfo();
        ctx.put("teacherList", teacherList);
        /*查询所有的ItemInfo信息*/
        List<ItemInfo> itemInfoList = itemInfoDAO.QueryAllItemInfoInfo();
        ctx.put("itemInfoList", itemInfoList);
        /*查询所有的ResultItem信息*/
        List<ResultItem> resultItemList = resultItemDAO.QueryAllResultItemInfo();
        ctx.put("resultItemList", resultItemList);
        return "add_view";
    }

    /*添加CheckResult信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("CheckResult添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CheckResult添加失败!"));
            return "error";
        }
    }

    /*查询CheckResult信息*/
    public String QueryCheckResult() {
        if(currentPage == 0) currentPage = 1;
        List<CheckResult> checkResultList = checkResultDAO.QueryCheckResultInfo(studentObj, teacherObj, itemObj, resultObj, currentPage);
        /*计算总的页数和总的记录数*/
        checkResultDAO.CalculateTotalPageAndRecordNumber(studentObj, teacherObj, itemObj, resultObj);
        /*获取到总的页码数目*/
        totalPage = checkResultDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*后台导出到excel*/
    public String QueryCheckResultOutputToExcel() { 
        List<CheckResult> checkResultList = checkResultDAO.QueryCheckResultInfo(studentObj,teacherObj,itemObj,resultObj);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "CheckResult信息记录"; 
        String[] headers = { "评价的学生","被评价老师","被评价的指标","评价结果"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"CheckResult.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询CheckResult信息*/
    public String FrontQueryCheckResult() {
        if(currentPage == 0) currentPage = 1;
        List<CheckResult> checkResultList = checkResultDAO.QueryCheckResultInfo(studentObj, teacherObj, itemObj, resultObj, currentPage);
        /*计算总的页数和总的记录数*/
        checkResultDAO.CalculateTotalPageAndRecordNumber(studentObj, teacherObj, itemObj, resultObj);
        /*获取到总的页码数目*/
        totalPage = checkResultDAO.getTotalPage();
        /*当前查询条件下总记录数*/
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

    /*查询要修改的CheckResult信息*/
    public String ModifyCheckResultQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键resultId获取CheckResult对象*/
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

    /*查询要修改的CheckResult信息*/
    public String FrontShowCheckResultQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键resultId获取CheckResult对象*/
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

    /*更新修改CheckResult信息*/
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
            ctx.put("message",  java.net.URLEncoder.encode("CheckResult信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CheckResult信息更新失败!"));
            return "error";
       }
   }

    /*删除CheckResult信息*/
    public String DeleteCheckResult() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            checkResultDAO.DeleteCheckResult(resultId);
            ctx.put("message",  java.net.URLEncoder.encode("CheckResult删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("CheckResult删除失败!"));
            return "error";
        }
    }

}
