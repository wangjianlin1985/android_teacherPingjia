<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Teacher" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Teacher teacher = (Teacher)request.getAttribute("teacher");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>�޸Ľ�ʦ��Ϣ</TITLE>
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
    var teacherNumber = document.getElementById("teacher.teacherNumber").value;
    if(teacherNumber=="") {
        alert('�������ʦ�û���!');
        return false;
    }
    var password = document.getElementById("teacher.password").value;
    if(password=="") {
        alert('�������ʦ����!');
        return false;
    }
    var name = document.getElementById("teacher.name").value;
    if(name=="") {
        alert('�������ʦ����!');
        return false;
    }
    var sex = document.getElementById("teacher.sex").value;
    if(sex=="") {
        alert('�������ʦ�Ա�!');
        return false;
    }
    var email = document.getElementById("teacher.email").value;
    if(email=="") {
        alert('�����������ַ!');
        return false;
    }
    return true; 
}
 </script>
</HEAD>
<BODY background="<%=basePath %>images/adminBg.jpg">
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="Teacher/Teacher_ModifyTeacher.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>��ʦ�û���:</td>
    <td width=70%><input id="teacher.teacherNumber" name="teacher.teacherNumber" type="text" value="<%=teacher.getTeacherNumber() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>��ʦ����:</td>
    <td width=70%><input id="teacher.password" name="teacher.password" type="text" size="20" value='<%=teacher.getPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ʦ����:</td>
    <td width=70%><input id="teacher.name" name="teacher.name" type="text" size="20" value='<%=teacher.getName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ʦ�Ա�:</td>
    <td width=70%><input id="teacher.sex" name="teacher.sex" type="text" size="20" value='<%=teacher.getSex() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ʦ����:</td>
    <td width=70%><input id="teacher.age" name="teacher.age" type="text" size="8" value='<%=teacher.getAge() %>'/></td>
  </tr>

  <tr>
    <td width=30%>���ڿγ�:</td>
    <td width=70%><input id="teacher.courseName" name="teacher.courseName" type="text" size="20" value='<%=teacher.getCourseName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="teacher.telephone" name="teacher.telephone" type="text" size="20" value='<%=teacher.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>�����ַ:</td>
    <td width=70%><input id="teacher.email" name="teacher.email" type="text" size="20" value='<%=teacher.getEmail() %>'/></td>
  </tr>

  <tr>
    <td width=30%>������Ϣ:</td>
    <td width=70%><textarea id="teacher.memo" name="teacher.memo" rows=5 cols=50><%=teacher.getMemo() %></textarea></td>
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
