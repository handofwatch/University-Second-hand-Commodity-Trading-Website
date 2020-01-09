package seproject.website.goods.user.web.servlet;

import java.io.IOException;
import java.net.URLEncoder;
import java.util.HashMap;
import java.util.Map;
import javax.servlet.ServletException;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import cn.itcast.commons.CommonUtils;
import seproject.website.goods.user.domain.User;
import seproject.website.goods.user.service.UserService;
import seproject.website.goods.user.service.exception.UserException;
import cn.itcast.servlet.BaseServlet;

public class UserServlet extends BaseServlet {
	private UserService userService = new UserService();

	public String ajaxValidateLoginname(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

//		1. 获取用户名

		String loginname = req.getParameter("loginname");

//		2. 通过service得到校验结果
		boolean b = userService.ajaxValidateLoginname(loginname);

//		3. 发给客户端
		resp.getWriter().print(b);
		return null;
	}

	public String ajaxValidateEmail(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		String email = req.getParameter("email");

		boolean b = userService.ajaxValidateEmail(email);

		resp.getWriter().print(b);
		return null;
	}

	public String ajaxValidateVerifyCode(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		1. 获取输入框中的验证码

		String verifyCode = req.getParameter("verifyCode");

//		2. 获取图片上真实的校验码
		String vcode = (String) req.getSession().getAttribute("vCode");


//		3. 进行忽略大小写比较，得到结果
		boolean b = verifyCode.equalsIgnoreCase(vcode);

//		4. 发送给前端
		resp.getWriter().print(b);
		return null;
	}

	public String regist(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);

		Map<String,String> errors = validateRegist(formUser, req.getSession());
		if(errors.size() > 0) {
			req.setAttribute("form", formUser);
			req.setAttribute("errors", errors);
			return "f:/jsps/user/regist.jsp";
		}

		userService.regist(formUser);

		req.setAttribute("code", "success");
		req.setAttribute("msg", "注册功能，请马上到邮箱激活！");
		return "f:/jsps/msg.jsp";
	}
	

//	 注册校验
//	 对表单的字段进行逐个校验，如果有错误，使用当前字段名称为key，错误信息为value，保存到map中
//	 返回map
	private Map<String,String> validateRegist(User formUser, HttpSession session) {
		Map<String,String> errors = new HashMap<String,String>();

//		1. 校验登录名
		String loginname = formUser.getLoginname();
		if(loginname == null || loginname.trim().isEmpty()) {
			errors.put("loginname", "用户名不能为空！");
		} else if(loginname.length() < 3 || loginname.length() > 20) {
			errors.put("loginname", "用户名长度必须在3~20之间！");
		} else if(!userService.ajaxValidateLoginname(loginname)) {
			errors.put("loginname", "用户名已被注册！");
		}

//		2. 校验登录密码
		String loginpass = formUser.getLoginpass();
		if(loginpass == null || loginpass.trim().isEmpty()) {
			errors.put("loginpass", "密码不能为空！");
		} else if(loginpass.length() < 3 || loginpass.length() > 20) {
			errors.put("loginpass", "密码长度必须在3~20之间！");
		}

//		3. 确认密码校验
		String reloginpass = formUser.getReloginpass();
		if(reloginpass == null || reloginpass.trim().isEmpty()) {
			errors.put("reloginpass", "确认密码不能为空！");
		} else if(!reloginpass.equals(loginpass)) {
			errors.put("reloginpass", "两次输入不一致！");
		}

		//		4. 校验email
		String email = formUser.getEmail();
		if(email == null || email.trim().isEmpty()) {
			errors.put("email", "Email不能为空！");
		} else if(!email.matches("^([a-zA-Z0-9_-])+@([a-zA-Z0-9_-])+((\\.[a-zA-Z0-9_-]{2,3}){1,2})$")) {
			errors.put("email", "Email格式错误！");
		} else if(!userService.ajaxValidateEmail(email)) {
			errors.put("email", "Email已被注册！");
		}
		

//		5. 验证码校验
		if(!validateVerifiCode(formUser, session)) {
			errors.put("verifyCode", "验证码错误！");
		}

		return errors;
	}

	//激活功能
	public String activation(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		//获取激活码
		String code = req.getParameter("activationCode");
		try {
			userService.activatioin(code);
			req.setAttribute("code", "success");//通知msg.jsp显示对号
			req.setAttribute("msg", "恭喜，激活成功，请马上登录！");
		} catch (UserException e) {
			// 说明service抛出了异常
			req.setAttribute("msg", e.getMessage());
			req.setAttribute("code", "error");//通知msg.jsp显示X
		}
		return "f:/jsps/msg.jsp";
	}

	//修改密码
	public String updatePassword(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {

		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);
		User user = (User)req.getSession().getAttribute("sessionUser");
		if(user == null) {
			req.setAttribute("msg", "您还没有登录！");
			return "f:/jsps/user/login.jsp";
		}
		else if(!validateUpPass(req)){
			req.setAttribute("msg", "您两次输入的密码不一致");
			return "r:/index.jsp";
		}
		else if(!validateVerifiCode(formUser, req.getSession())) {
			req.setAttribute("form", formUser);
			req.setAttribute("msg", "您的验证码不正确");
			return "r:/index.jsp";
		}
		
		try {
			userService.updatePassword(user.getUid(), formUser.getNewpass(), 
					formUser.getLoginpass());
			req.setAttribute("msg", "修改密码成功");
			req.setAttribute("code", "success");
			return "f:/jsps/msg.jsp";
		} catch (UserException e) {
			req.setAttribute("msg", e.getMessage());//保存异常信息到request
			req.setAttribute("user", formUser);//为了回显
			return "f:/jsps/msg.jsp";
		}
	}

	//校验两次登陆密码是否一致
	private boolean validateUpPass(HttpServletRequest req){
		var bool = true;
		String newpass =req.getParameter("newpass");
		String reloginpass = req.getParameter("reloginpass");
		if(!newpass.equals(reloginpass)){
			bool = false;
		}

		return bool;
	}

	//退出账号
	public String quit(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
		req.getSession().invalidate();
		return "r:/jsps/user/login.jsp";
	}

	public String login(HttpServletRequest req, HttpServletResponse resp)
			throws ServletException, IOException {
//		1. 封装表单数据到user:先把前端数据放进一个map里，然后把map使用commonutils的toBean方法封装到实体类里
		User formUser = CommonUtils.toBean(req.getParameterMap(), User.class);

//		2. 校验验证码
		if(!validateVerifiCode(formUser, req.getSession())) {
			req.setAttribute("form", formUser);
			req.setAttribute("errors", "您的验证码不正确");
			return "f:/jsps/user/login.jsp";
		}

//		3. 调用userService#login()方法
		User user = userService.login(formUser);

//		4. 开始校验登陆表单相关信息
		if(user == null) {
			req.setAttribute("msg", "用户名或密码错误！");
			req.setAttribute("user", formUser);
			return "f:/jsps/user/login.jsp";
		} else {
			if(!user.isStatus()) {
				req.setAttribute("msg", "您还没有激活！");
				req.setAttribute("user", formUser);
				return "f:/jsps/user/login.jsp";				
			} else {
				// 保存用户到session
				req.getSession().setAttribute("sessionUser", user);
				// 获取用户名保存到cookie中
				String loginname = user.getLoginname();
				loginname = URLEncoder.encode(loginname, "utf-8");
				Cookie cookie = new Cookie("loginname", loginname);
				cookie.setMaxAge(60 * 60 * 24 * 10);//保存10天
				resp.addCookie(cookie);
				return "r:/index.jsp";//重定向到主页
			}
		}
	}

	//	  登录校验方法，用来校验激活码是否正确
	private boolean validateVerifiCode(User formUser, HttpSession session) {
		boolean bool = true;
		String verifyCode = formUser.getVerifyCode();
		String vcode = (String) session.getAttribute("vCode");
		if(!verifyCode.equalsIgnoreCase(vcode)) {
			bool = false;
		}
		return bool;
	}
}
