<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="thirdPage.jsp">
		<table>
			<tr>
				<td>姓名：</td>
				<td><input type="text" name="name"></td>
			</tr>
			<tr>
				<td>性别：</td>
				<td><input type="radio" id="gender_male" value="male"
					name="gender" checked><label for="gender_male">男</label> <input
					type="radio" id="gender_female" value="female" name="gender"
					checked><label for="gender_male">女</label></td>
			</tr>
			<tr>
				<td>报道编号：</td>
				<td><input type="text" name="registrationNumber"></td>
			</tr>
			<%
			String idNumber = request.getParameter("idNumber");
			%>
			<tr>
				<td>身份证号码：</td>
				<td><input type="text" name="idNumber" value="<%=idNumber%>"
					disabled=disabled " /></td>
			</tr>
			<tr>
				<td>毕业学校：</td>
				<td><select name="university">
						<option value="第一中学">第一中学</option>
						<option value="第二中学">第二中学</option>
						<option value="第三中学">第三中学</option>
				</select></td>
			</tr>
			<tr>
				<td>所学专业：</td>
				<td><select name="major">
						<option value="计算机应用技术">计算机应用技术</option>
						<option value="计算机网络管理">计算机网络管理</option>
						<option value="计算机科学与技术">计算机科学与技术</option>
				</select></td>
			</tr>
			<tr>
				<td>所学专业：</td>
				<td><select name="major">
						<option value="计算机应用技术">计算机应用技术</option>
						<option value="计算机网络管理">计算机网络管理</option>
						<option value="计算机科学与技术">计算机科学与技术</option>
				</select></td>
			</tr>
			<tr>
				<td>学历层次：</td>
				<td><select name="education">
						<option value="大专">大专</option>
						<option value="高中及以下">高中及以下</option>
				</select></td>
			</tr>

			<tr>
				<td>就业情况：</td>
				<td><select name="employment">
						<option value="已就业">已就业</option>
						<option value="暂未就业">暂未就业</option>
						<option value="创业">创业</option>
						<option value="升学">升学</option>
				</select></td>
			</tr>

			<tr>
				<td colspan="2"><input type="submit" value="确认报道"></td>
			</tr>
		</table>
	</form>

</body>
</html>