<%@page import="org.apache.jasper.tagplugins.jstl.core.ForEach"%>
<%@ page import="java.util.*"%>
<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>
	<a href="https://docs.openstack.org/">跳转到opstack官网</a>
	<h1>你好!</h1>
	<iframe width="100px" height="200px" src=""></iframe>
	<hr />

	<div class="neirong">
		<%!//方法
	List<String> getList() {
		//创建集合
		List<String> list = new ArrayList<>();
		//给集合赋值
		list.add("人物");
		list.add("油画");
		list.add("山水");
		list.add("书法");
		list.add("国画");
		return list;
	}%>
	
		<ul class="jiantou">
			<%
			List<String> list = getList();
			for (int i = 0; i < list.size(); i++) {
			%>
			<li><a href="#"><%=list.get(i)%></a></li>
			<%
			}
			%>
			
			<% for(int j = 0; j < 10; j++){%>
			<li><%=6666%></li>
			<%} %>	
		</ul>
		
		
	
		
	</div>
</body>
</html>