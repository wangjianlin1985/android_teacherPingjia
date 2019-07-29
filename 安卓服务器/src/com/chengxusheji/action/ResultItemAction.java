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
import com.chengxusheji.dao.ResultItemDAO;
import com.chengxusheji.domain.ResultItem;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ResultItemAction extends BaseAction {

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

    private int resultItemId;
    public void setResultItemId(int resultItemId) {
        this.resultItemId = resultItemId;
    }
    public int getResultItemId() {
        return resultItemId;
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
    @Resource ResultItemDAO resultItemDAO;

    /*待操作的ResultItem对象*/
    private ResultItem resultItem;
    public void setResultItem(ResultItem resultItem) {
        this.resultItem = resultItem;
    }
    public ResultItem getResultItem() {
        return this.resultItem;
    }

    /*跳转到添加ResultItem视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加ResultItem信息*/
    @SuppressWarnings("deprecation")
    public String AddResultItem() {
        ActionContext ctx = ActionContext.getContext();
        try {
            resultItemDAO.AddResultItem(resultItem);
            ctx.put("message",  java.net.URLEncoder.encode("ResultItem添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ResultItem添加失败!"));
            return "error";
        }
    }

    /*查询ResultItem信息*/
    public String QueryResultItem() {
        if(currentPage == 0) currentPage = 1;
        List<ResultItem> resultItemList = resultItemDAO.QueryResultItemInfo(currentPage);
        /*计算总的页数和总的记录数*/
        resultItemDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = resultItemDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = resultItemDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("resultItemList",  resultItemList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryResultItemOutputToExcel() { 
        List<ResultItem> resultItemList = resultItemDAO.QueryResultItemInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ResultItem信息记录"; 
        String[] headers = { "记录编号","结果描述"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<resultItemList.size();i++) {
        	ResultItem resultItem = resultItemList.get(i); 
        	dataset.add(new String[]{resultItem.getResultItemId() + "",resultItem.getResultItemText()});
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
			response.setHeader("Content-disposition","attachment; filename="+"ResultItem.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询ResultItem信息*/
    public String FrontQueryResultItem() {
        if(currentPage == 0) currentPage = 1;
        List<ResultItem> resultItemList = resultItemDAO.QueryResultItemInfo(currentPage);
        /*计算总的页数和总的记录数*/
        resultItemDAO.CalculateTotalPageAndRecordNumber();
        /*获取到总的页码数目*/
        totalPage = resultItemDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = resultItemDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("resultItemList",  resultItemList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*查询要修改的ResultItem信息*/
    public String ModifyResultItemQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键resultItemId获取ResultItem对象*/
        ResultItem resultItem = resultItemDAO.GetResultItemByResultItemId(resultItemId);

        ctx.put("resultItem",  resultItem);
        return "modify_view";
    }

    /*查询要修改的ResultItem信息*/
    public String FrontShowResultItemQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键resultItemId获取ResultItem对象*/
        ResultItem resultItem = resultItemDAO.GetResultItemByResultItemId(resultItemId);

        ctx.put("resultItem",  resultItem);
        return "front_show_view";
    }

    /*更新修改ResultItem信息*/
    public String ModifyResultItem() {
        ActionContext ctx = ActionContext.getContext();
        try {
            resultItemDAO.UpdateResultItem(resultItem);
            ctx.put("message",  java.net.URLEncoder.encode("ResultItem信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ResultItem信息更新失败!"));
            return "error";
       }
   }

    /*删除ResultItem信息*/
    public String DeleteResultItem() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            resultItemDAO.DeleteResultItem(resultItemId);
            ctx.put("message",  java.net.URLEncoder.encode("ResultItem删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ResultItem删除失败!"));
            return "error";
        }
    }

}
