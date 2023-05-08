<%@ page language="java" import="java.util.*"  contentType="text/html;charset=gb2312"%> 
<%@ page import="com.chengxusheji.domain.CheckResult" %>
<%@ page import="com.chengxusheji.domain.Student" %>
<%@ page import="com.chengxusheji.domain.Teacher" %>
<%@ page import="com.chengxusheji.domain.ItemInfo" %>
<%@ page import="com.chengxusheji.domain.ResultItem" %>
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

%>
<HTML><HEAD><TITLE>�鿴���˽��</TITLE>
<STYLE type=text/css>
body{margin:0px; font-size:12px; background-image:url(<%=basePath%>images/bg.jpg); background-position:bottom; background-repeat:repeat-x; background-color:#A2D5F0;}
.STYLE1 {color: #ECE9D8}
.label {font-style.:italic; }
.errorLabel {font-style.:italic;  color:red; }
.errorMessage {font-weight:bold; color:red; }
</STYLE>
 <script src="<%=basePath %>calendar.js"></script>
</HEAD>
<BODY><br/><br/>
<s:fielderror cssStyle="color:red" />
<TABLE align="center" height="100%" cellSpacing=0 cellPadding=0 width="80%" border=0>
  <TBODY>
  <TR>
    <TD align="left" vAlign=top ><s:form action="" method="post" onsubmit="return checkForm();" enctype="multipart/form-data" name="form1">
<table width='100%' cellspacing='1' cellpadding='3'  class="tablewidth">
  <tr>
    <td width=30%>��¼���:</td>
    <td width=70%><%=checkResult.getResultId() %></td>
  </tr>

  <tr>
    <td width=30%>���۵�ѧ��:</td>
    <td width=70%>
      <%=checkResult.getStudentObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>��������ʦ:</td>
    <td width=70%>
      <%=checkResult.getTeacherObj().getName() %>
    </td>
  </tr>

  <tr>
    <td width=30%>�����۵�ָ��:</td>
    <td width=70%>
      <%=checkResult.getItemObj().getItemTitle() %>
    </td>
  </tr>

  <tr>
    <td width=30%>���۽��:</td>
    <td width=70%>
      <%=checkResult.getResultObj().getResultItemText() %>
    </td>
  </tr>

  <tr>
      <td colspan="4" align="center">
        <input type="button" value="����" onclick="history.back();"/>
      </td>
    </tr>

</table>
</s:form>
   </TD></TR>
  </TBODY>
</TABLE>
</BODY>
</HTML>
