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
<HTML><HEAD><TITLE>�޸�ѧ����Ϣ</TITLE>
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
    var studentNumber = document.getElementById("student.studentNumber").value;
    if(studentNumber=="") {
        alert('������ѧ���û���!');
        return false;
    }
    var password = document.getElementById("student.password").value;
    if(password=="") {
        alert('������ѧ������!');
        return false;
    }
    var name = document.getElementById("student.name").value;
    if(name=="") {
        alert('������ѧ������!');
        return false;
    }
    var sex = document.getElementById("student.sex").value;
    if(sex=="") {
        alert('������ѧ���Ա�!');
        return false;
    }
    var telephone = document.getElementById("student.telephone").value;
    if(telephone=="") {
        alert('��������ϵ�绰!');
        return false;
    }
    var qq = document.getElementById("student.qq").value;
    if(qq=="") {
        alert('��������ϵqq!');
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
    <td width=30%>ѧ���û���:</td>
    <td width=70%><input id="student.studentNumber" name="student.studentNumber" type="text" value="<%=student.getStudentNumber() %>" readOnly /></td>
  </tr>

  <tr>
    <td width=30%>ѧ������:</td>
    <td width=70%><input id="student.password" name="student.password" type="text" size="20" value='<%=student.getPassword() %>'/></td>
  </tr>

  <tr>
    <td width=30%>ѧ������:</td>
    <td width=70%><input id="student.name" name="student.name" type="text" size="20" value='<%=student.getName() %>'/></td>
  </tr>

  <tr>
    <td width=30%>ѧ���Ա�:</td>
    <td width=70%><input id="student.sex" name="student.sex" type="text" size="4" value='<%=student.getSex() %>'/></td>
  </tr>

  <tr>
    <td width=30%>ѧ������:</td>
    <td width=70%><input id="student.age" name="student.age" type="text" size="8" value='<%=student.getAge() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ϵ�绰:</td>
    <td width=70%><input id="student.telephone" name="student.telephone" type="text" size="20" value='<%=student.getTelephone() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ϵqq:</td>
    <td width=70%><input id="student.qq" name="student.qq" type="text" size="20" value='<%=student.getQq() %>'/></td>
  </tr>

  <tr>
    <td width=30%>��ͥ��ַ:</td>
    <td width=70%><textarea id="student.address" name="student.address" rows=5 cols=50><%=student.getAddress() %></textarea></td>
  </tr>

  <tr>
    <td width=30%>������Ϣ:</td>
    <td width=70%><textarea id="student.memo" name="student.memo" rows=5 cols=50><%=student.getMemo() %></textarea></td>
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
