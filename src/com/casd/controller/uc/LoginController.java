package com.casd.controller.uc;

import java.io.IOException;
import java.io.PrintWriter;
import java.util.HashMap;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;

import com.itextpdf.text.log.SysoCounter;
import net.sf.json.JSONObject;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casd.entity.uc.User;
import com.casd.service.SessionListener;
import com.casd.service.uc.UserService;


@Controller
public class LoginController {		
	@Autowired
	private UserService userService;
	@RequestMapping("/login")
	 public ModelAndView login() {
		ModelAndView model=new ModelAndView();
	
		model.addObject("title", "诚安时代");
		model.setViewName("login");
		return model;
		
	}
	
	
	
	
	/**
	 * 用户登录验证
	 * 
	 * */
	@RequestMapping(value="/Logins" , method = RequestMethod.GET)
	@ResponseBody
	public Map<String,Object> login(HttpServletRequest request,HttpServletResponse response,Model model) throws IOException {
		String username = request.getParameter("username");

		//username = new String(username.getBytes("iso-8859-1"),"UTF-8");
		String password = request.getParameter("password");
		String j_captcha=request.getParameter("j_captcha");//获取验证码
		JSONObject jsonObject=new JSONObject();  
		//验证码校验
		HttpSession session=request.getSession();
		String urlpath="/casd/";
		 session.setAttribute("CASD_PATH",urlpath);	
		String  verification=(String) session.getAttribute("admin_code");
		if(verification!=null&&!verification.equals(j_captcha)){
            jsonObject.put("ver_msg", "验证码错误");  
            PrintWriter out=response.getWriter();  
            out.println(jsonObject);  
            out.close();  
		}
		//账号密码校验
		Map<String, Object> map=new HashMap<String, Object>();
		String  fields= "*";
		StringBuilder sbf=new StringBuilder();
		if (StringUtils.hasText(username)) {
		sbf.append(" uc_user where username = '"+username+"' and `password` = '"+password+"' and `status` <> 2 ");
	     map.put("fields", fields);
	     map.put("where", sbf.toString());
	  
	     User  login = (User) userService.login(map);
	     session.setAttribute("loginUser", login);
	     
	     if(login==null){
	    	 jsonObject.put("us_msg", "账号密码错误");  
	         PrintWriter out=response.getWriter();  
	         out.println(jsonObject);  
	         out.close();
	     }else {
	    	 //禁止同一账号在两台电脑同时登陆
	    	 SessionListener.SESSIONID_USER.put(login.getUserid()+"", request.getSession().getId());
	    	 
	     }
		}
		return null;
	}

/*
		@RequestMapping(value="/admin/checkUserOnline",method = RequestMethod.POST)
		@ResponseBody
		public void checkUserOnline(HttpServletRequest request,HttpServletResponse response) throws IOException{
		
		String string= request.getSession().getAttribute("msg").toString();
		
		
			
			 PrintWriter out = response.getWriter();
			  System.out.println(string);
		     out.print(string);
		   
		
	    
		}
*/
}
