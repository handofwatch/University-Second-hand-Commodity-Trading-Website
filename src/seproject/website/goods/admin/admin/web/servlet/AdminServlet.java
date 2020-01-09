package seproject.website.goods.admin.admin.web.servlet;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cn.itcast.commons.CommonUtils;
import seproject.website.goods.admin.admin.domain.Admin;
import seproject.website.goods.admin.admin.service.AdminService;
import cn.itcast.servlet.BaseServlet;

public class AdminServlet extends BaseServlet {
	private AdminService adminService = new AdminService();

	public String ajaxValidateLoginname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String loginname = req.getParameter("loginname");

		boolean b = adminService.ValidateLoginname(loginname);

		resp.getWriter().print(b);
		return null;
	}

	public String login(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Admin form = CommonUtils.toBean(req.getParameterMap(), Admin.class);
		Admin admin = adminService.login(form);
		if(admin == null) {
			req.setAttribute("msg", "用户名或密码错误！");
			return "/adminjsps/login.jsp";
		}
		req.getSession().setAttribute("admin", admin);
		return "r:/adminjsps/admin/index.jsp";
	}

	public String regist(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		Admin  formAdmin = CommonUtils.toBean(req.getParameterMap(), Admin.class);

		Map<String,String> errors = validateRegist(formAdmin, req.getSession());
		if(errors.size() > 0) {
			req.setAttribute("form", formAdmin);
			req.setAttribute("errors", errors);
			return "f:/adminjsps/addNewadmin.jsp";
		}

		adminService.regist(formAdmin);

		req.setAttribute("code", "success");
		req.setAttribute("msg", "成功添加管理员");
		return "f:/adminjsps/admin/msg.jsp";
	}

	private Map<String,String> validateRegist(Admin formAdmin, HttpSession session) {
		Map<String,String> errors = new HashMap<String,String>();

		String loginname = formAdmin.getAdminname();
		if(loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		} else if(loginname.length() < 3 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在3~20之间！");
		} else if(!adminService.ValidateLoginname(loginname)) {
			errors.put("loginname", "用户名已被注册！");
		}

		String loginpass = formAdmin.getAdminpwd();
		if(loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空！");
		} else if(loginpass.length() < 3 || loginpass.length() > 20) {
			errors.put("loginpass", "密码长度必须在3~20之间！");
		}

		String reloginpass = formAdmin.getReloginpass();
		if(reloginpass == null || reloginpass.trim().isEmpty()) {
			errors.put("reloginpass", "确认密码不能为空！");
		} else if(!reloginpass.equals(loginpass)) {
			errors.put("reloginpass", "两次输入不一致！");
		}

		return errors;
	}

	//退出账号
	public String quit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getSession().invalidate();
		return "f:/adminjsps/login.jsp";
	}
}
