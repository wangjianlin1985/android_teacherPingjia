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
import com.chengxusheji.dao.ItemInfoDAO;
import com.chengxusheji.domain.ItemInfo;
import com.chengxusheji.utils.FileTypeException;
import com.chengxusheji.utils.ExportExcelUtil;

@Controller @Scope("prototype")
public class ItemInfoAction extends BaseAction {

    /*�������Ҫ��ѯ������: ָ�����*/
    private String itemTitle;
    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }
    public String getItemTitle() {
        return this.itemTitle;
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

    private int itemId;
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public int getItemId() {
        return itemId;
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
    @Resource ItemInfoDAO itemInfoDAO;

    /*��������ItemInfo����*/
    private ItemInfo itemInfo;
    public void setItemInfo(ItemInfo itemInfo) {
        this.itemInfo = itemInfo;
    }
    public ItemInfo getItemInfo() {
        return this.itemInfo;
    }

    /*��ת�����ItemInfo��ͼ*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*���ItemInfo��Ϣ*/
    @SuppressWarnings("deprecation")
    public String AddItemInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            itemInfoDAO.AddItemInfo(itemInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ItemInfo��ӳɹ�!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("ͼƬ�ļ���ʽ����!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ItemInfo���ʧ��!"));
            return "error";
        }
    }

    /*��ѯItemInfo��Ϣ*/
    public String QueryItemInfo() {
        if(currentPage == 0) currentPage = 1;
        if(itemTitle == null) itemTitle = "";
        List<ItemInfo> itemInfoList = itemInfoDAO.QueryItemInfoInfo(itemTitle, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        itemInfoDAO.CalculateTotalPageAndRecordNumber(itemTitle);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = itemInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = itemInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("itemInfoList",  itemInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("itemTitle", itemTitle);
        return "query_view";
    }

    /*��̨������excel*/
    public String QueryItemInfoOutputToExcel() { 
        if(itemTitle == null) itemTitle = "";
        List<ItemInfo> itemInfoList = itemInfoDAO.QueryItemInfoInfo(itemTitle);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ItemInfo��Ϣ��¼"; 
        String[] headers = { "ָ�����","ָ������"};
        List<String[]> dataset = new ArrayList<String[]>(); 
        for(int i=0;i<itemInfoList.size();i++) {
        	ItemInfo itemInfo = itemInfoList.get(i); 
        	dataset.add(new String[]{itemInfo.getItemTitle(),itemInfo.getItemDesc()});
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
			response.setHeader("Content-disposition","attachment; filename="+"ItemInfo.xls");//filename�����ص�xls���������������Ӣ�� 
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
    /*ǰ̨��ѯItemInfo��Ϣ*/
    public String FrontQueryItemInfo() {
        if(currentPage == 0) currentPage = 1;
        if(itemTitle == null) itemTitle = "";
        List<ItemInfo> itemInfoList = itemInfoDAO.QueryItemInfoInfo(itemTitle, currentPage);
        /*�����ܵ�ҳ�����ܵļ�¼��*/
        itemInfoDAO.CalculateTotalPageAndRecordNumber(itemTitle);
        /*��ȡ���ܵ�ҳ����Ŀ*/
        totalPage = itemInfoDAO.getTotalPage();
        /*��ǰ��ѯ�������ܼ�¼��*/
        recordNumber = itemInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("itemInfoList",  itemInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("itemTitle", itemTitle);
        return "front_query_view";
    }

    /*��ѯҪ�޸ĵ�ItemInfo��Ϣ*/
    public String ModifyItemInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������itemId��ȡItemInfo����*/
        ItemInfo itemInfo = itemInfoDAO.GetItemInfoByItemId(itemId);

        ctx.put("itemInfo",  itemInfo);
        return "modify_view";
    }

    /*��ѯҪ�޸ĵ�ItemInfo��Ϣ*/
    public String FrontShowItemInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*��������itemId��ȡItemInfo����*/
        ItemInfo itemInfo = itemInfoDAO.GetItemInfoByItemId(itemId);

        ctx.put("itemInfo",  itemInfo);
        return "front_show_view";
    }

    /*�����޸�ItemInfo��Ϣ*/
    public String ModifyItemInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            itemInfoDAO.UpdateItemInfo(itemInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ItemInfo��Ϣ���³ɹ�!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ItemInfo��Ϣ����ʧ��!"));
            return "error";
       }
   }

    /*ɾ��ItemInfo��Ϣ*/
    public String DeleteItemInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            itemInfoDAO.DeleteItemInfo(itemId);
            ctx.put("message",  java.net.URLEncoder.encode("ItemInfoɾ���ɹ�!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ItemInfoɾ��ʧ��!"));
            return "error";
        }
    }

}
