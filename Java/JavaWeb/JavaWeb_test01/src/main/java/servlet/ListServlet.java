package servlet;

import java.io.IOException;
import java.util.List;

import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import dao.Tools;
import model.AddFond;

@WebServlet("/ListServlet")
public class ListServlet extends HttpServlet {
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
		// 获取到输入框的值
		String keyword = request.getParameter("keyword");
		System.out.println(keyword);

		
		// 定义全表查的sql
		String sql = "SELECT id , cuisine , food_name , price , member_price FROM add_fond " ;
		// 定义计数的sql
//		String countSql = "SELECT count(id) AS count from add_fond ";
		
		// 判断搜索菜名的输入框是否有值
		if (keyword != null && !keyword.isEmpty() ) {	
			sql += "WHERE food_name LIKE concat('%',?,'%')";
			List<AddFond> list = Tools.selectAddFondData(sql, keyword);
			request.setAttribute("list", list);
			
//			countSql += "WHERE food_name LIKE concat('%',?,'%')";
			int count = Tools.selectCountAddFondData(sql, keyword);
			request.setAttribute("count", count);
		} else {

			List<AddFond> list = Tools.selectAddFondData(sql);
			request.setAttribute("list", list);
			
			int count = Tools.selectCountAddFondData(sql);
			request.setAttribute("count", count);
		}

		// 登录成功，跳转到指定页面
		request.getRequestDispatcher("listFond.jsp").forward(request, response);
		
		
		
	}
}
