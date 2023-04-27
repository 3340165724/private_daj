 <%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>

<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
<style>
table tbody tr:hover {
	color: white;
	background-color: black;
}
</style>
</head>
<body>
	<h2>列表</h2>
	<h1>
		欢迎<a style="color: red">${sessionScope.userName}</a>登录
	</h1>

	<fieldset>
		<legend>搜素</legend>
		<form action="ListServlet" method="post" >
			菜名： <input type="text" name="keyword"> 
			<input type="submit" value="查询">
			</from>
	</fieldset>


	<br>

	<div id="MainArea">
		<table class="MainArea_Count" align="left" cellspacing="0"
			cellpadding="0">
			<!-- 表头 -->
			<thead>
				<tr align="left" valign="middle" id="TableTitle">
					<th width="5%" align="center">编号</th>
					<th width="5%" align="center">菜系</th>
					<th width="15%" align="center">菜名</th>
					<th width="10%" align="center">价格</th>
					<th width="10%" align="center">会员价格</th>
					<th width="5%" align="center">操作</th>
				</tr>
			</thead>
			<!-- 显示数据列表 -->
			<tbody>
				<c:forEach items="${list}" var="fond">
					<tr>
						<td align="center">${fond.id }</td>
						<td align="center">${fond.cuisine }</td>
						<td align="center">${fond.foodName }</td>
						<td align="center">${fond.price }</td>
						<td align="center">${fond.memberPrice }</td>
						 
						<% // <td><a href="javascript:void(0);" onclick="del(${fond.id })">删除</a></td>%>
						<td><a href="DeleteServlet?id=${fond.id}" >删除</a></td>
					</tr>
				</c:forEach>

			</tbody>
		</table>
	</div>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<br>
	<h2>
		共有<a style="color: red">${count}</a>条
	</h2>
	<% /**
	<script type="text/javascript">
		function del(id) {
			if (confirm("确定删除该数据？")) {
				window.location.href = "delete?id=" + id;
	        }
		}
		function openwin() {  
			window.open ("add.jsp", "newwindow", "height=500, width=400, toolbar =no, menubar=no, scrollbars=no, resizable=no, location=no, status=no") //写成一行  
		}
	</script>
	*/ %>
</body>
</html>