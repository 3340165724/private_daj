package servlet;

import java.io.IOException;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Tools;

@WebServlet("/AddFondServlet")
public class AddFondServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		// 字符集
		request.setCharacterEncoding("UTF-8");
		response.setCharacterEncoding("UTF-8");
		// 获取到输入框内容
		String cuisine = request.getParameter("cuisine");
		if(cuisine.equals(1)) {
			cuisine = "清真";
		}else if(cuisine.equals(2)) {
			cuisine = "傣味";
		}else if(cuisine.equals(3)){
			cuisine = "川菜";
		}
		String foodName = request.getParameter("foodName");
		String price = request.getParameter("price");
		String memberPrice = request.getParameter("memberPrice");
		System.out.println("连通了");
		
		
		// 定义sql
		String sql = "insert into add_fond(cuisine,food_name,price,member_price) value(?,?,?,?)";
		// 调用添加数据的方法
		Tools.updateData(sql, cuisine, foodName, Double.valueOf(price), Double.valueOf(memberPrice));

		// 登录成功，跳转到指定页面
		request.getRequestDispatcher("addFood.jsp").forward(request, response);

	}

}
