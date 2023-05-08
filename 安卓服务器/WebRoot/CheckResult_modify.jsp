<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.CheckResult" %>
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ page import="com.chengxusheji.domain.Teacher" %>
<%@ page import="com.chengxusheji.domain.ItemInfo" %>
<%@ page import="com.chengxusheji.domain.ResultItem" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    //��ȡ���е�Student��Ϣ
    List<Student> studentList = (List<Student>)request.getAttribute("studentList");
    //��ȡ���е�Teacher��Ϣ
    List<Teacher> teacherList = (List<Teacher>)request.getAttribute("teacherList");
    //��ȡ���е�ItemInfo��Ϣ
    List<ItemInfo> itemInfoList = (List<ItemInfo>)request.getAttribute("itemInfoList");
    //��ȡ���е�ResultItem��Ϣ
    List<ResultItem> resultItemList = (List<ResultItem>)request.getAttribute("resultItemList");
    CheckResult checkResult = (CheckResult)request.getAttribute("checkResult");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸Ŀ��˽��</TITLE>
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
/*��֤��*/
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
    <TD align="left" vAlign=top ><s:form action="CheckResult/CheckResult_ModifyCheckResult.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>��¼���:</td>
    <td width=70%><input id="checkResult.resultId" name="checkResult.resultId" type="text" value="<%=checkResult.getResultId() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>���۵�ѧ��:</td>
    <td width=70%>
      <select name="checkResult.studentObj.studentNumber">
      <%
        for(Student student:studentList) {
          String selected = "";
          if(student.getStudentNumber().equals(checkResult.getStudentObj().getStudentNumber()))
            selected = "selected";
      %>
          <option value='<%=student.getStudentNumber() %>' <%=selected %>><%=student.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>��������ʦ:</td>
    <td width=70%>
      <select name="checkResult.teacherObj.teacherNumber">
      <%
        for(Teacher teacher:teacherList) {
          String selected = "";
          if(teacher.getTeacherNumber().equals(checkResult.getTeacherObj().getTeacherNumber()))
            selected = "selected";
      %>
          <option value='<%=teacher.getTeacherNumber() %>' <%=selected %>><%=teacher.getName() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>�����۵�ָ��:</td>
    <td width=70%>
      <select name="checkResult.itemObj.itemId">
      <%
        for(ItemInfo itemInfo:itemInfoList) {
          String selected = "";
          if(itemInfo.getItemId() == checkResult.getItemObj().getItemId())
            selected = "selected";
      %>
          <option value='<%=itemInfo.getItemId() %>' <%=selected %>><%=itemInfo.getItemTitle() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr>
    <td width=30%>���۽��:</td>
    <td width=70%>
      <select name="checkResult.resultObj.resultItemId">
      <%
        for(ResultItem resultItem:resultItemList) {
          String selected = "";
          if(resultItem.getResultItemId() == checkResult.getResultObj().getResultItemId())
            selected = "selected";
      %>
          <option value='<%=resultItem.getResultItemId() %>' <%=selected %>><%=resultItem.getResultItemText() %></option>
      <%
        }
      %>
    </td>
  </tr>

  <tr bgcolor='#FFFFFF'>
      <td colspan="4" align="center">
        <input type='submit' name='button' value='����' >
        &nbsp;&nbsp;
        <input type="reset" value='��д' />
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
