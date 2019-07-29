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
<HTML><HEAD><TITLE>修改教师信息</TITLE>
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
    var teacherNumber = document.getElementById("teacher.teacherNumber").value;
    if(teacherNumber=="") {
        alert('请输入教师用户名!');
        return false;
    }
    var password = document.getElementById("teacher.password").value;
    if(password=="") {
        alert('请输入教师密码!');
        return false;
    }
    var name = document.getElementById("teacher.name").value;
    if(name=="") {
        alert('请输入教师姓名!');
        return false;
    }
    var sex = document.getElementById("teacher.sex").value;
    if(sex=="") {
        alert('请输入教师性别!');
        return false;
    }
    var email = document.getElementById("teacher.email").value;
    if(email=="") {
        alert('请输入邮箱地址!');
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
    <td width=30%>教师用户名:</td>
    <td width=70%><input id="teacher.teacherNumber" name="teacher.teacherNumber" type="text" value="<%=teacher.getTeacherNumber() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>教师密码:</td>
    <td width=70%><input id="teacher.password" name="teacher.password" type="text" size="20" value='<%=teacher.getPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>教师姓名:</td>
    <td width=70%><input id="teacher.name" name="teacher.name" type="text" size="20" value='<%=teacher.getName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>教师性别:</td>
    <td width=70%><input id="teacher.sex" name="teacher.sex" type="text" size="20" value='<%=teacher.getSex() %>'/></td>
  </tr>

  <tr>
    <td width=30%>教师年龄:</td>
    <td width=70%><input id="teacher.age" name="teacher.age" type="text" size="8" value='<%=teacher.getAge() %>'/></td>
  </tr>

  <tr>
    <td width=30%>教授课程:</td>
    <td width=70%><input id="teacher.courseName" name="teacher.courseName" type="text" size="20" value='<%=teacher.getCourseName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><input id="teacher.telephone" name="teacher.telephone" type="text" size="20" value='<%=teacher.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>邮箱地址:</td>
    <td width=70%><input id="teacher.email" name="teacher.email" type="text" size="20" value='<%=teacher.getEmail() %>'/></td>
  </tr>

  <tr>
    <td width=30%>附加信息:</td>
    <td width=70%><textarea id="teacher.memo" name="teacher.memo" rows=5 cols=50><%=teacher.getMemo() %></textarea></td>
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
