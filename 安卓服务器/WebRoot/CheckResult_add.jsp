<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%>
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ page import="com.chengxusheji.domain.Teacher" %>
<%@ page import="com.chengxusheji.domain.ItemInfo" %>
<%@ page import="com.chengxusheji.domain.ResultItem" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //获取所有的Student信息
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
    //获取所有的Teacher信息
    List<Teacher> teacherList = (List<Teacher>)request.getAttribute("teacherList");
    //获取所有的ItemInfo信息
    List<ItemInfo> itemInfoList = (List<ItemInfo>)request.getAttribute("itemInfoList");
    //获取所有的ResultItem信息
    List<ResultItem> resultItemList = (List<ResultItem>)request.getAttribute("resultItemList");
    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>添加考核结果</TITLE> 
<STYLE type=text/css>
BODY {
    	MARGIN-LEFT: 0px; BACKGROUND-COLOR: #ffffff
}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
<script language="javascript">
/*验证表单*/
function checkForm() {
    return true; 
}
 </script>
</HEAD>

<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top >
    <s:form action="CheckResult/CheckResult_AddCheckResult.action" method="post" id="checkResultAddForm" onsubmit="return checkForm();"  enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">

  <tr>
    <td width=30%>评价的学生:</td>
    <td width=70%>
      <select name="checkResult.studentObj.studentNumber">
      <%
        for(Student student:studentList) {
      %>
          <option value='<%=student.getStudentNumber() %>'><%=student.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>被评价老师:</td>
    <td width=70%>
      <select name="checkResult.teacherObj.teacherNumber">
      <%
        for(Teacher teacher:teacherList) {
      %>
          <option value='<%=teacher.getTeacherNumber() %>'><%=teacher.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>被评价的指标:</td>
    <td width=70%>
      <select name="checkResult.itemObj.itemId">
      <%
        for(ItemInfo itemInfo:itemInfoList) {
      %>
          <option value='<%=itemInfo.getItemId() %>'><%=itemInfo.getItemTitle() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>评价结果:</td>
    <td width=70%>
      <select name="checkResult.resultObj.resultItemId">
      <%
        for(ResultItem resultItem:resultItemList) {
      %>
          <option value='<%=resultItem.getResultItemId() %>'><%=resultItem.getResultItemText() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='保存' >
        &nbsp;&nbsp;
        <input type="reset" value='重写' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
