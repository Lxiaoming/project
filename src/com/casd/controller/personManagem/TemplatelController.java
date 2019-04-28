package com.casd.controller.personManagem;

import java.io.BufferedInputStream;
import java.io.BufferedOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.HttpURLConnection;
import java.net.URL;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.controller.web.utils.DownloadUtil;
import com.casd.entity.hr.TemplateModel;
import com.casd.service.hr.TemplateService;

@Controller
@RequestMapping("/admin")
public class TemplatelController {

	@Autowired
	private TemplateService templateService;

	/**
	 * 模板列表
	 * @throws Exception 
	 * 
	 * */
	@RequestMapping(value = "/templatelList", method = RequestMethod.GET)
	public ModelAndView templatelList(Integer limit,Integer page,HttpServletRequest request) throws Exception {
		ModelAndView mv = new ModelAndView();

		Ref<Integer> record = new Ref<Integer>();
		String hr_templatel_type=request.getParameter("hr_templatel_type");
		limit=20;
	   	page = page==null ? 1: page;
	       page=page-1;

		String fields = "tpm.*";
		StringBuffer sbf = new StringBuffer();
		sbf.append(" hr_template_model tpm where 1=1");
		if (!StringUtils.isEmpty(hr_templatel_type)) {
			sbf.append(" and tpm.hr_templatel_type="+hr_templatel_type);
		}
		List<Map<String, Object>> ListData = templateService.templateData(
				page, limit, record, fields, sbf.toString());
		
		mv.addObject("ListData", ListData);
		mv.setViewName("personManagem/templatelList");
		return mv;
	}

	@RequestMapping(value = "/templatelLists", method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> templatelList(HttpServletRequest request)
			throws Exception {

		Ref<Integer> record = new Ref<Integer>();
		
		Integer page=Integer.parseInt(request.getParameter("page"));
		page = page==null ? 1: page;
	       page=page-1;
		int pageSize = Integer.parseInt(request.getParameter("limit"));
		String hr_templatel_type=request.getParameter("hr_templatel_type");

		String fields = "tpm.*";
		StringBuffer sbf = new StringBuffer();
		sbf.append(" hr_template_model tpm where 1=1");
		if (!StringUtils.isEmpty(hr_templatel_type)) {
			sbf.append(" and tpm.hr_templatel_type="+hr_templatel_type);
		}
		List<Map<String, Object>> ListData = templateService.templateData(
				page, pageSize, record, fields, sbf.toString());
		
		 JSONArray jsonArray=JSONArray.fromObject(ListData);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
		return result;
	}

	
	//保存上传
	@RequestMapping(value = "/saveTemplatel", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveTemplatel(TemplateModel template,String hr_template_path) throws IOException {
		Map<String, Object> json = new HashMap<String, Object>();
		try {
			SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd");//设置日期格式
			template.setHr_templatel_time(df.format(new Date()));
			templateService.updateTemplate(template);
    		json.put("Success", true);
			json.put("Msg", "上传成功");
			} catch (Exception e) {
				e.printStackTrace();
				json.put("Success", false);
				json.put("Msg", "上传失败");
			}
	     	return json;
	   }

	 /**
	  * 删除标准模板
	  * 
	  * */
	
	  @RequestMapping(value = "/deleteTemplate", method = RequestMethod.POST)
	  @ResponseBody
	  public Map<String, Object> deleteTemplate(String biz,String hr_template_path){
		  Map<String, Object> json=new HashMap<String, Object>();
		  try {
			
		    String where="";
		    where +=" where hr_templatel_id="+biz;
		    templateService.deleteTemplate(where);
		   
		      //更新时 文件存在就删除
			if (hr_template_path!=null&& !StringUtils.isEmpty(hr_template_path)){
			   String fileString=hr_template_path.substring(43); //旧的路径
		    	//文件存在时删除
		    	File file=new File("e:/file/casd/TemplateModel/"+fileString);
		    	if(file.exists()) {
		    		file.delete();
		    	}
		   }
	    		   
		    json.put("Success", true);
			json.put("Msg", "删除成功");
			} catch (Exception e) {
				e.printStackTrace();
				json.put("Success", false);
				json.put("Msg", "删除失败");
			}
		  return json;
	       }
          
	  @RequestMapping(value = "/downloadTemplate", method = RequestMethod.GET)
	  public  void catchPic(HttpServletResponse response,String hr_template_path) throws IOException{
		  
	        Map<String, String> urlByLoanid= new HashMap<String,String>();
	    //    Map<String, String> urlByLoanid = zcmQueryInfoService.queryUrlByLoanid(map);
	        BufferedOutputStream output = null;
            BufferedInputStream input = null;
	        try {
	            if(urlByLoanid!=null){
	                String wjurl=hr_template_path;
	               
	                //String wjurl = urlByLoanid.get("url");
	                int i = wjurl.lastIndexOf("/");
	                String fileName = wjurl.substring(i+1);
	                
	                URL url = new URL(wjurl);    
	                HttpURLConnection conn = (HttpURLConnection)url.openConnection();    
	                //设置超时间为3秒  
	                conn.setConnectTimeout(10*1000);  
	                //防止屏蔽程序抓取而返回403错误  
	                conn.setRequestProperty("User-Agent", "Mozilla/4.0 (compatible; MSIE 5.0; Windows NT; DigExt)");  
	                //得到输入流  
	                InputStream inputStream = conn.getInputStream();    
	                //获取自己数组  
	                byte[] bs = DownloadUtil.readInputStream(inputStream);    
	                response.setContentType("application/octet-stream;charset=ISO8859-1");
	                        output = new BufferedOutputStream(response.getOutputStream());
	                        // 中文文件名必须转码为 ISO8859-1,否则为乱码
	                        String fileNameDown = new String(fileName.getBytes(), "UTF-8");
	                        // 作为附件下载
	                        response.setHeader("Content-Disposition", "attachment;filename=" + fileNameDown);
	        
	                        output.write(bs);
	                        response.flushBuffer();
	            }  
	            } catch (Exception e) {
	                      e.printStackTrace();
	                      response.sendError(500, "下载失败,文件未存在");
	                    } // 用户可能取消了下载 流未关闭  用 finally关闭流
	                    finally {
	                      if (input != null)
	                            try {
	                                input.close();
	                            } catch (IOException e) {
	                                e.printStackTrace();
	                            }
	                      if (output != null)
	                            try {
	                                output.close();
	                            } catch (IOException e) {
	                                e.printStackTrace();
	                            }
	            }
	        
	}
	  
	    /**
	     * @param oldPath 旧文件路径
	     * @param newPath 新文件路径
	     * 
	     * **/
		@RequestMapping(value = "/uploadFile", method = RequestMethod.POST)
		@ResponseBody
	  public Map<String, Object> uploadFile(MultipartFile file,String oldPath,String newPath) throws IOException {
			  Map<String, Object> result = new HashMap<String, Object>();
  		
			InputStream inputStream=null;
			  //定义流
	        BufferedOutputStream stream =null;
			try {
				// 获取原始文件名
				String fileName = file.getOriginalFilename();
			
			    	if(StringUtils.isNotBlank(oldPath)){	    	
			    		oldPath=oldPath.substring(28);
			    	//文件存在时删除
			    	File file2=new File("e:/file/casd/"+oldPath);
				    	if(file2.exists()) {
				    		file2.delete();
				    	}
			    	}else {
			    		File file2=new File("e:/file/casd/"+newPath+fileName);
			    		if(file2.exists()) {
				    		file2.delete();
				    	}
					}
			    	
			    	      //获取文件下载路径流
		                 stream = new BufferedOutputStream(
		                        new FileOutputStream("e:/file/casd/"+newPath+fileName));
		                int length=0;
		                byte[] buffer = new byte[1024];
		                 inputStream = file.getInputStream();
		                //读取文件
		                while ((length = inputStream.read(buffer)) != -1) {
		                    stream.write(buffer, 0, length);
		                }
		                //刷新流
		                stream.flush();    
					
		                JSONArray jsonArray=new JSONArray();
		  			
		  			    result.put("code", 0);
		  			    result.put("msg", "");
		  			    result.put("data", jsonArray);
		                   
				} catch (Exception e) {
					e.printStackTrace();
				
				}finally{
				      //关闭流
					  if (inputStream != null){
						  inputStream.close();
					   }
					  if (stream != null) {
						  stream.close();
					  }
					
				}
		     
		return result;
		
	}
}
