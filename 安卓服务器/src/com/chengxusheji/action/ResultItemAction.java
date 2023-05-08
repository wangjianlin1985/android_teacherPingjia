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

    private int resultItemId;
    public void setResultItemId(int resultItemId) {
        this.resultItemId = resultItemId;
    }
    public int getResultItemId() {
        return resultItemId;
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
    @Resource ResultItemDAO resultItemDAO;

    /*��������ResultItem����*/
    private ResultItem resultItem;
    public void setResultItem(ResultItem resultItem) {
        this.resultItem = resultItem;
    }
    public ResultItem getResultItem() {
        return this.resultItem;
    }

    /*��ת�����ResultItem��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���ResultItem��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddResultItem() {
        ActionContext ctx = ActionContext.getContext();
        try {
            resultItemDAO.AddResultItem(resultItem);
            ctx.put("message",  java.net.URLEncoder.encode("ResultItem��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ResultItem���ʧ��!"));
            return "error";
        }
    }

    /*��ѯResultItem��Ϣ*/
    public String QueryResultItem() {
        if(currentPage == 0) currentPage = 1;
        List<ResultItem> resultItemList = resultItemDAO.QueryResultItemInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        resultItemDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = resultItemDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = resultItemDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("resultItemList",  resultItemList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryResultItemOutputToExcel() { 
        List<ResultItem> resultItemList = resultItemDAO.QueryResultItemInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ResultItem��Ϣ��¼"; 
        String[] headers = { "��¼���","�������"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"ResultItem.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯResultItem��Ϣ*/
    public String FrontQueryResultItem() {
        if(currentPage == 0) currentPage = 1;
        List<ResultItem> resultItemList = resultItemDAO.QueryResultItemInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        resultItemDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = resultItemDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = resultItemDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("resultItemList",  resultItemList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�ResultItem��Ϣ*/
    public String ModifyResultItemQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������resultItemId��ȡResultItem����*/
        ResultItem resultItem = resultItemDAO.GetResultItemByResultItemId(resultItemId);

        ctx.put("resultItem",  resultItem);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�ResultItem��Ϣ*/
    public String FrontShowResultItemQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������resultItemId��ȡResultItem����*/
        ResultItem resultItem = resultItemDAO.GetResultItemByResultItemId(resultItemId);

        ctx.put("resultItem",  resultItem);
        return "front_show_view";
    }

    /*�����޸�ResultItem��Ϣ*/
    public String ModifyResultItem() {
        ActionContext ctx = ActionContext.getContext();
        try {
            resultItemDAO.UpdateResultItem(resultItem);
            ctx.put("message",  java.net.URLEncoder.encode("ResultItem��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ResultItem��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��ResultItem��Ϣ*/
    public String DeleteResultItem() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            resultItemDAO.DeleteResultItem(resultItemId);
            ctx.put("message",  java.net.URLEncoder.encode("ResultItemɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ResultItemɾ��ʧ��!"));
            return "error";
        }
    }

}
