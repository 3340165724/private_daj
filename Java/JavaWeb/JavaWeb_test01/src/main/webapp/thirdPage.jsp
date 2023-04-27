<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="forthPage.jsp">
		<%
		String name = request.getParameter("name");
		String gender = request.getParameter("gender");
		String registrationNumber = request.getParameter("registrationNumber");
		String university = request.getParameter("university");
		String major = request.getParameter("major");
		String education = request.getParameter("education");
		String employment = request.getParameter("employment");
		%>
		<table>
			<tr>
				<td>姓名：</td>
				<td><span><%=name%></span></td>
			</tr>
			<tr>
				<td>性别：</td>
				<td><span><%=gender%></span></td>
			</tr>
			<tr>
				<td>报到证编号：</td>
				<td><span><%=registrationNumber%></span></td>
			</tr>
			<tr>
				<td>身份证号码：</td>
				<td><span></span></td>
			</tr>
			<tr>
				<td>毕业学校：</td>
				<td><span><%=university%></span></td>
			</tr>
			<tr>
				<td>所学专业：</td>
				<td><span><%=major%></span></td>
			</tr>
			<tr>
				<td>学历层次：</td>
				<td><span><%=education%></span></td>
			</tr>
			<tr>
				<td>就业情况：</td>
				<td><span><%=employment%></span></td>
			</tr>
			<tr>
				<td colspan="2"><input type="submit" value="确认信息">
					<button type="button">返回修改</button></td>
			</tr>
		</table>
	</form>

</body>
</html>