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

    private int timeId;
    public void setTimeId(int timeId) {
        this.timeId = timeId;
    }
    public int getTimeId() {
        return timeId;
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
    @Resource TimeSetDAO timeSetDAO;

    /*��������TimeSet����*/
    private TimeSet timeSet;
    public void setTimeSet(TimeSet timeSet) {
        this.timeSet = timeSet;
    }
    public TimeSet getTimeSet() {
        return this.timeSet;
    }

    /*��ת�����TimeSet��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���TimeSet��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddTimeSet() {
        ActionContext ctx = ActionContext.getContext();
        try {
            timeSetDAO.AddTimeSet(timeSet);
            ctx.put("message",  java.net.URLEncoder.encode("TimeSet��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TimeSet���ʧ��!"));
            return "error";
        }
    }

    /*��ѯTimeSet��Ϣ*/
    public String QueryTimeSet() {
        if(currentPage == 0) currentPage = 1;
        List<TimeSet> timeSetList = timeSetDAO.QueryTimeSetInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        timeSetDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = timeSetDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = timeSetDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("timeSetList",  timeSetList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryTimeSetOutputToExcel() { 
        List<TimeSet> timeSetList = timeSetDAO.QueryTimeSetInfo();
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "TimeSet��Ϣ��¼"; 
        String[] headers = { "��¼���","��ʼ����","��������"};
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
		HttpServletResponse response = null;//����һ��HttpServletResponse���� 
		OutputStream out = null;//����һ����������� 
		try { 
			response = ServletActionContext.getResponse();//��ʼ��HttpServletResponse���� 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"TimeSet.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯTimeSet��Ϣ*/
    public String FrontQueryTimeSet() {
        if(currentPage == 0) currentPage = 1;
        List<TimeSet> timeSetList = timeSetDAO.QueryTimeSetInfo(currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        timeSetDAO.CalculateTotalPageAndRecordNumber();
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = timeSetDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = timeSetDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("timeSetList",  timeSetList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�TimeSet��Ϣ*/
    public String ModifyTimeSetQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������timeId��ȡTimeSet����*/
        TimeSet timeSet = timeSetDAO.GetTimeSetByTimeId(timeId);

        ctx.put("timeSet",  timeSet);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�TimeSet��Ϣ*/
    public String FrontShowTimeSetQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������timeId��ȡTimeSet����*/
        TimeSet timeSet = timeSetDAO.GetTimeSetByTimeId(timeId);

        ctx.put("timeSet",  timeSet);
        return "front_show_view";
    }

    /*�����޸�TimeSet��Ϣ*/
    public String ModifyTimeSet() {
        ActionContext ctx = ActionContext.getContext();
        try {
            timeSetDAO.UpdateTimeSet(timeSet);
            ctx.put("message",  java.net.URLEncoder.encode("TimeSet��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TimeSet��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��TimeSet��Ϣ*/
    public String DeleteTimeSet() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            timeSetDAO.DeleteTimeSet(timeId);
            ctx.put("message",  java.net.URLEncoder.encode("TimeSetɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("TimeSetɾ��ʧ��!"));
            return "error";
        }
    }

}
