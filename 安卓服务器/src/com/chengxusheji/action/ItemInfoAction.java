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

    /*界面层需要查询的属性: 指标标题*/
    private String itemTitle;
    public void setItemTitle(String itemTitle) {
        this.itemTitle = itemTitle;
    }
    public String getItemTitle() {
        return this.itemTitle;
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

    private int itemId;
    public void setItemId(int itemId) {
        this.itemId = itemId;
    }
    public int getItemId() {
        return itemId;
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
    @Resource ItemInfoDAO itemInfoDAO;

    /*待操作的ItemInfo对象*/
    private ItemInfo itemInfo;
    public void setItemInfo(ItemInfo itemInfo) {
        this.itemInfo = itemInfo;
    }
    public ItemInfo getItemInfo() {
        return this.itemInfo;
    }

    /*跳转到添加ItemInfo视图*/
    public String AddView() {
        ActionContext ctx = ActionContext.getContext();
        return "add_view";
    }

    /*添加ItemInfo信息*/
    @SuppressWarnings("deprecation")
    public String AddItemInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            itemInfoDAO.AddItemInfo(itemInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ItemInfo添加成功!"));
            return "add_success";
        } catch(FileTypeException ex) {
        	ctx.put("error",  java.net.URLEncoder.encode("图片文件格式不对!"));
            return "error";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ItemInfo添加失败!"));
            return "error";
        }
    }

    /*查询ItemInfo信息*/
    public String QueryItemInfo() {
        if(currentPage == 0) currentPage = 1;
        if(itemTitle == null) itemTitle = "";
        List<ItemInfo> itemInfoList = itemInfoDAO.QueryItemInfoInfo(itemTitle, currentPage);
        /*计算总的页数和总的记录数*/
        itemInfoDAO.CalculateTotalPageAndRecordNumber(itemTitle);
        /*获取到总的页码数目*/
        totalPage = itemInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = itemInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("itemInfoList",  itemInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("itemTitle", itemTitle);
        return "query_view";
    }

    /*后台导出到excel*/
    public String QueryItemInfoOutputToExcel() { 
        if(itemTitle == null) itemTitle = "";
        List<ItemInfo> itemInfoList = itemInfoDAO.QueryItemInfoInfo(itemTitle);
        ExportExcelUtil ex = new ExportExcelUtil();
        String title = "ItemInfo信息记录"; 
        String[] headers = { "指标标题","指标描述"};
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
		HttpServletResponse response = null;//创建一个HttpServletResponse对象 
		OutputStream out = null;//创建一个输出流对象 
		try { 
			response = ServletActionContext.getResponse();//初始化HttpServletResponse对象 
			out = response.getOutputStream();//
			response.setHeader("Content-disposition","attachment; filename="+"ItemInfo.xls");//filename是下载的xls的名，建议最好用英文 
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
    /*前台查询ItemInfo信息*/
    public String FrontQueryItemInfo() {
        if(currentPage == 0) currentPage = 1;
        if(itemTitle == null) itemTitle = "";
        List<ItemInfo> itemInfoList = itemInfoDAO.QueryItemInfoInfo(itemTitle, currentPage);
        /*计算总的页数和总的记录数*/
        itemInfoDAO.CalculateTotalPageAndRecordNumber(itemTitle);
        /*获取到总的页码数目*/
        totalPage = itemInfoDAO.getTotalPage();
        /*当前查询条件下总记录数*/
        recordNumber = itemInfoDAO.getRecordNumber();
        ActionContext ctx = ActionContext.getContext();
        ctx.put("itemInfoList",  itemInfoList);
        ctx.put("totalPage", totalPage);
        ctx.put("recordNumber", recordNumber);
        ctx.put("currentPage", currentPage);
        ctx.put("itemTitle", itemTitle);
        return "front_query_view";
    }

    /*查询要修改的ItemInfo信息*/
    public String ModifyItemInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键itemId获取ItemInfo对象*/
        ItemInfo itemInfo = itemInfoDAO.GetItemInfoByItemId(itemId);

        ctx.put("itemInfo",  itemInfo);
        return "modify_view";
    }

    /*查询要修改的ItemInfo信息*/
    public String FrontShowItemInfoQuery() {
        ActionContext ctx = ActionContext.getContext();
        /*根据主键itemId获取ItemInfo对象*/
        ItemInfo itemInfo = itemInfoDAO.GetItemInfoByItemId(itemId);

        ctx.put("itemInfo",  itemInfo);
        return "front_show_view";
    }

    /*更新修改ItemInfo信息*/
    public String ModifyItemInfo() {
        ActionContext ctx = ActionContext.getContext();
        try {
            itemInfoDAO.UpdateItemInfo(itemInfo);
            ctx.put("message",  java.net.URLEncoder.encode("ItemInfo信息更新成功!"));
            return "modify_success";
        } catch (Exception e) {
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ItemInfo信息更新失败!"));
            return "error";
       }
   }

    /*删除ItemInfo信息*/
    public String DeleteItemInfo() {
        ActionContext ctx = ActionContext.getContext();
        try { 
            itemInfoDAO.DeleteItemInfo(itemId);
            ctx.put("message",  java.net.URLEncoder.encode("ItemInfo删除成功!"));
            return "delete_success";
        } catch (Exception e) { 
            e.printStackTrace();
            ctx.put("error",  java.net.URLEncoder.encode("ItemInfo删除失败!"));
            return "error";
        }
    }

}
