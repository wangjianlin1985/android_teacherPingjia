<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ page import="java.text.DateFormat" %>
<%@ page import="java.text.SimpleDateFormat" %>
<%@ taglib prefix="s" uri="/struts-tags"%>
<%
    String path = request.getContextPath();
    String basePath = request.getScheme()+"://"+request.getServerName()+":"+request.getServerPort()+path+"/";
    Student student = (Student)request.getAttribute("student");

    String username=(String)session.getAttribute("username");
    if(username==null){
        response.getWriter().println("<script>top.location.href='" + basePath + "login/login_view.action';</script>");
    }
%>
<HTML><HEAD><TITLE>修改学生信息</TITLE>
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
    var studentNumber = document.getElementById("student.studentNumber").value;
    if(studentNumber=="") {
        alert('请输入学生用户名!');
        return false;
    }
    var password = document.getElementById("student.password").value;
    if(password=="") {
        alert('请输入学生密码!');
        return false;
    }
    var name = document.getElementById("student.name").value;
    if(name=="") {
        alert('请输入学生姓名!');
        return false;
    }
    var sex = document.getElementById("student.sex").value;
    if(sex=="") {
        alert('请输入学生性别!');
        return false;
    }
    var telephone = document.getElementById("student.telephone").value;
    if(telephone=="") {
        alert('请输入联系电话!');
        return false;
    }
    var qq = document.getElementById("student.qq").value;
    if(qq=="") {
        alert('请输入联系qq!');
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
    <TD align="left" vAlign=top ><s:form action="Student/Student_ModifyStudent.action" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3' class="tablewidth">
  <tr>
    <td width=30%>学生用户名:</td>
    <td width=70%><input id="student.studentNumber" name="student.studentNumber" type="text" value="<%=student.getStudentNumber() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>学生密码:</td>
    <td width=70%><input id="student.password" name="student.password" type="text" size="20" value='<%=student.getPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>学生姓名:</td>
    <td width=70%><input id="student.name" name="student.name" type="text" size="20" value='<%=student.getName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>学生性别:</td>
    <td width=70%><input id="student.sex" name="student.sex" type="text" size="4" value='<%=student.getSex() %>'/></td>
  </tr>

  <tr>
    <td width=30%>学生年龄:</td>
    <td width=70%><input id="student.age" name="student.age" type="text" size="8" value='<%=student.getAge() %>'/></td>
  </tr>

  <tr>
    <td width=30%>联系电话:</td>
    <td width=70%><input id="student.telephone" name="student.telephone" type="text" size="20" value='<%=student.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>联系qq:</td>
    <td width=70%><input id="student.qq" name="student.qq" type="text" size="20" value='<%=student.getQq() %>'/></td>
  </tr>

  <tr>
    <td width=30%>家庭地址:</td>
    <td width=70%><textarea id="student.address" name="student.address" rows=5 cols=50><%=student.getAddress() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>附加信息:</td>
    <td width=70%><textarea id="student.memo" name="student.memo" rows=5 cols=50><%=student.getMemo() %></textarea></td>
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
