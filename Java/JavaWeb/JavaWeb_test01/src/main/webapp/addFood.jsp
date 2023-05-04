<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core"%>
	
<!DOCTYPE html>
<html>
<head>
<meta charset="UTF-8">
<title>Insert title here</title>
</head>
<body>

	<form action="AddFondServlet" method="post">
		<!-- 本断面标题（分段标题） -->
		<div class="ItemBlock_Title">
		<h1>欢迎<a style="color:red">${sessionScope.username}</a>登录</h1>
			<img width="4" height="7" border="0"
				src="style/images/item_point.gif"> 菜品信息 &nbsp;

		</div>
		<!-- 本段表单字段 -->
		<div class="ItemBlockBorder">
			<div class="ItemBlock">
				<div class="ItemBlock2">
					<table cellpadding="0" cellpacing="0" class="mainjForm">
						<tr>
							<td width="80px">菜系</td>
							<td><select name="cuisine" style="width: 80px">
									<option value="1">清真</option>
									<option value="2">傣味</option>
									<option value="3">川菜</option>
							</select></td>
						</tr>
						<tr>
							<td width="80px">菜名</td>
							<td><input type="text" name="foodName" class="InputStyle"
								value="" /></td>
						</tr>
						<tr>
							<td>价格</td>
							<td><input type="text" name="price" class="InputStyle"
								value="" /></td>
						</tr>
						<tr>
							<td>会员价格</td>
							<td><input type="text" name="memberPrice" class="InputStyle"
								value="" /></td>

						</tr>
						<tr  colspan="2">
							<td><input type="submit" value="添加" name="introduce" class="TextareaStyle"/></td>
						</tr>
						
					</table>
				</div>
			</div>
		</div>
	</form>

</body>
</html>