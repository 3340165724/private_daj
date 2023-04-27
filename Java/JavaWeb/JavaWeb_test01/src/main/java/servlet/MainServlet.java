package servlet;

import java.io.IOException;
import java.util.List;
import javax.servlet.ServletException;
import javax.servlet.annotation.WebServlet;
import javax.servlet.http.HttpServlet;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import dao.Tools;
import model.AddFond;
import model.User;

@WebServlet("/MainServlet")
public class MainServlet extends HttpServlet {
	private static final long serialVersionUID = 1L;

	protected void doGet(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		this.doPost(request, response);
	}

	protected void doPost(HttpServletRequest request, HttpServletResponse response)
			throws ServletException, IOException {
		System.out.println("连通了");

		// 获取login.jsp页面内输入框中的内容
		String userName = request.getParameter("userName");
		String password = request.getParameter("password");

		if (!userName.isEmpty() && !password.isEmpty() && userName != null && password != null) {
			// 创建实体类user
			User user = new User();
			// 定义sql语句
			String userSQL = "SELECT id , user_name, `password` , admin FROM user where user_name=? and password=? ";
//			Tools tools = new Tools();
			Tools.selectUserData(userSQL, userName, password);
			if (userName != null) {
//				String userPassword = user.getPassword();
				
				// 创建Session对象
				HttpSession session = request.getSession();
				// 存储数据
				session.setAttribute("userName", userName);

				// 定义全表查的sql
				String sql = "SELECT id , cuisine , food_name , price , member_price FROM add_fond ";

				List<AddFond> list = Tools.selectAddFondData(sql);
				request.setAttribute("list", list);

				// 定义计数的sql
				String countSql = "SELECT count(id) from add_fond";

				int count = Tools.selectCountAddFondData(sql);
				request.setAttribute("count", count);

				// 登录成功，跳转到指定页面
				request.getRequestDispatcher("listFond.jsp").forward(request, response);
			}else {
				// 登录失败，跳转到指定页面
				request.getRequestDispatcher("login.jsp").forward(request, response);
			}
		}else {
			// 登录失败，跳转到指定页面
			request.getRequestDispatcher("login.jsp").forward(request, response);
		}	
	}

	public static void main(String[] args) {
		Tools t = new Tools();
		String sql = "SELECT id , cuisine , food_name , price , member_price FROM add_fond ";
		List<AddFond> list = t.selectAddFondData(sql);
		list.forEach(o -> System.out.println(o.getCuisine()));

		// 定义计数的sql
		String countSql = "SELECT count(id) from add_fond";
		int count = Tools.selectCountAddFondData(sql);
		System.out.println(count);

	}
}
