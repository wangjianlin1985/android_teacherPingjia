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
import com.chengxusheji.dao.TimeSetDAO;
import com.chengxusheji.domain.TimeSet;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class TimeSetAction extends BaseAction {

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

    private int timeId;
    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }
    public int getTimeId() {
        return timeId;
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
    @Resource TimeSetDAO timeSetDAO;

    /*待操作的TimeSet对象*/
    private TimeSet timeSet;
    public void setTimeSet(TimeSet timeSet) {
        this.timeSet = timeSet;
    }
    public TimeSet getTimeSet() {
        return this.timeSet;
    }

    /*跳转到添加TimeSet视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加TimeSet信息*/
    @SuppressWarnings("deprecation")
    public String AddTimeSet() {
        ActionContext ctx = ActionContext.getContext();
        try {
            timeSetDAO.AddTimeSet(timeSet);
            ctx.put("message",  java.net.URLEncoder.encode("TimeSet添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TimeSet添加失败!"));
            return "error";
        }
    }

    /*查询TimeSet信息*/
    public String QueryTimeSet() {
        if(currentPage == 0) currentPage = 1;
        List<TimeSet> timeSetList = timeSetDAO.QueryTimeSetInfo(currentPage);
        /*计算总的页数和总的记录数*/
        timeSetDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = timeSetDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = timeSetDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("timeSetList",  timeSetList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryTimeSetOutputToExcel() { 
        List<TimeSet> timeSetList = timeSetDAO.QueryTimeSetInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "TimeSet信息记录"; 
        String[] headers = { "记录编号","开始日期","结束日期"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<timeSetList.size();i++) {
        	TimeSet timeSet = timeSetList.get(i); 
        	dataset.add(new String[]{timeSet.getTimeId() + "",new SimpleDateFormat("yyyy-MM-dd").format(timeSet.getStartDate()),new SimpleDateFormat("yyyy-MM-dd").format(timeSet.getEndDate())});
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
			response.setHeader("Content-disposition","attachment; filename="+"TimeSet.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询TimeSet信息*/
    public String FrontQueryTimeSet() {
        if(currentPage == 0) currentPage = 1;
        List<TimeSet> timeSetList = timeSetDAO.QueryTimeSetInfo(currentPage);
        /*计算总的页数和总的记录数*/
        timeSetDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = timeSetDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = timeSetDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("timeSetList",  timeSetList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的TimeSet信息*/
    public String ModifyTimeSetQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键timeId获取TimeSet对象*/
        TimeSet timeSet = timeSetDAO.GetTimeSetByTimeId(timeId);

        ctx.put("timeSet",  timeSet);
        return "modify_view";
    }

    /*查询要修改的TimeSet信息*/
    public String FrontShowTimeSetQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键timeId获取TimeSet对象*/
        TimeSet timeSet = timeSetDAO.GetTimeSetByTimeId(timeId);

        ctx.put("timeSet",  timeSet);
        return "front_show_view";
    }

    /*更新修改TimeSet信息*/
    public String ModifyTimeSet() {
        ActionContext ctx = ActionContext.getContext();
        try {
            timeSetDAO.UpdateTimeSet(timeSet);
            ctx.put("message",  java.net.URLEncoder.encode("TimeSet信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TimeSet信息更新失败!"));
            return "error";
       }
   }

    /*删除TimeSet信息*/
    public String DeleteTimeSet() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            timeSetDAO.DeleteTimeSet(timeId);
            ctx.put("message",  java.net.URLEncoder.encode("TimeSet删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TimeSet删除失败!"));
            return "error";
        }
    }

}
