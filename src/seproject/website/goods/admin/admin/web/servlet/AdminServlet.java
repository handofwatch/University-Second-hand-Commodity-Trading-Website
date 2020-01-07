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
import seproject.website.goods.user.domain.User;

public class AdminServlet extends BaseServlet {
	private AdminService adminService = new AdminService();

	public String ajaxValidateLoginname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 获取用户名
		 */
		String loginname = req.getParameter("loginname");
		/*
		 * 2. 通过service得到校验结果
		 */
		boolean b = adminService.ValidateLoginname(loginname);
		/*
		 * 3. 发给客户端
		 */
		resp.getWriter().print(b);
		return null;
	}
	
	/**
	 * 登录功能
	 * @param req
	 * @param resp
	 * @return
	 * @throws ServletException
	 * @throws IOException
	 */
	public String login(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		/*
		 * 1. 封装表单数据到Admin
		 */
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
		/*
		 * 1. 封装表单数据到User对象
		 */
		Admin  formAdmin = CommonUtils.toBean(req.getParameterMap(), Admin.class);
		/*
		 * 2. 校验之, 如果校验失败，保存错误信息，返回到regist.jsp显示
		 */
		Map<String,String> errors = validateRegist(formAdmin, req.getSession());
		if(errors.size() > 0) {
			req.setAttribute("form", formAdmin);
			req.setAttribute("errors", errors);
			return "f:/adminjsps/addNewadmin.jsp";
		}
		/*
		 * 3. 使用service完成业务
		 */
		adminService.regist(formAdmin);
		/*
		 * 4. 保存成功信息，转发到msg.jsp显示！
		 */
		req.setAttribute("code", "success");
		req.setAttribute("msg", "成功添加管理员");
		return "f:/adminjsps/admin/msg.jsp";
	}

	/*
	 * 注册校验
	 * 对表单的字段进行逐个校验，如果有错误，使用当前字段名称为key，错误信息为value，保存到map中
	 * 返回map
	 */
	private Map<String,String> validateRegist(Admin formAdmin, HttpSession session) {
		Map<String,String> errors = new HashMap<String,String>();
		/*
		 * 1. 校验登录名
		 */
		String loginname = formAdmin.getAdminname();
		if(loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		} else if(loginname.length() < 3 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在3~20之间！");
		} else if(!adminService.ValidateLoginname(loginname)) {
			errors.put("loginname", "用户名已被注册！");
		}

		/*
		 * 2. 校验登录密码
		 */
		String loginpass = formAdmin.getAdminpwd();
		if(loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空！");
		} else if(loginpass.length() < 3 || loginpass.length() > 20) {
			errors.put("loginpass", "密码长度必须在3~20之间！");
		}

		/*
		 * 3. 确认密码校验
		 */
		String reloginpass = formAdmin.getReloginpass();
		if(reloginpass == null || reloginpass.trim().isEmpty()) {
			errors.put("reloginpass", "确认密码不能为空！");
		} else if(!reloginpass.equals(loginpass)) {
			errors.put("reloginpass", "两次输入不一致！");
		}

		return errors;
	}
}
