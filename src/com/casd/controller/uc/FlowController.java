package com.casd.controller.uc;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import net.sf.json.JSONArray;

import org.activiti.engine.TaskService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.util.StringUtils;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.servlet.ModelAndView;

import com.casd.controller.web.Ref;
import com.casd.entity.flow.Flow;
import com.casd.entity.flow.FlowNode;
import com.casd.entity.uc.User;
import com.casd.service.flow.FlowService;
import com.casd.service.uc.UserService;


@Controller
@RequestMapping("/admin")
public class FlowController {
	
	
	@Autowired
	private FlowService flowService;
	

	@Autowired
	private TaskService taskService;
	@Autowired
	private UserService userService;
	
	/**
	 * 流程基础资料
	*/
	
	@RequestMapping(value="/flowList" , method = RequestMethod.GET)
	public ModelAndView flowList() {
		ModelAndView mv=new ModelAndView();
		mv.setViewName("uc/flowList");
		return mv;
	}
	
	
	@RequestMapping(value="/flowLists" , method = RequestMethod.GET)
	@ResponseBody
	public Map<String, Object> flowList(HttpServletRequest request) throws Exception {
		Ref<Integer> record = new Ref<Integer>();
		
		Integer pageIndex=Integer.parseInt(request.getParameter("page"));
		pageIndex = pageIndex==null ? 1: pageIndex;
		pageIndex=pageIndex-1;
		int pageSize = Integer.parseInt(request.getParameter("limit"));		
		

		StringBuffer sbf=new StringBuffer();
		sbf.append(" flow_table where 1=1 ");
		
		String searchName=request.getParameter("flow_name");
		if (StringUtils.hasText(searchName)) {
			sbf.append(" and flow_name like'%"+searchName+"%'");
		}
		

		List<Map<String, Object>> data=flowService.flowList(pageIndex, pageSize, record, sbf.toString());
		
		 JSONArray jsonArray=JSONArray.fromObject(data);
		  Map<String, Object> result = new HashMap<String, Object>();
		    result.put("code", 0);
		    result.put("msg", "");
		    result.put("count", record.getValue());
		    result.put("data", jsonArray);
		return result;
		
	}
	
	//新增/修改界面
	@RequestMapping(value = "/flowNew", method = RequestMethod.GET)
	@ResponseBody
	public ModelAndView flowNew(HttpServletRequest request)
			throws Exception {
		ModelAndView mv = new ModelAndView();
		Map<String, Object> map=new HashMap<String, Object>();
		String flow_id = request.getParameter("flow_id");
		Flow flow=new Flow();
		if (flow_id != null) {
		    map.put("what", flow_id);
			flow = flowService.findFlowByID(map);
			mv.addObject("flow", flow);
		}
		mv.setViewName("uc/flowNew");
		return mv;
	}
	//初始化审核基础资料界面
	@RequestMapping(value = "/flowNew", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> flowNew(Model model, HttpServletRequest request)
			throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		Map<String, Object> json = new HashMap<String, Object>();
		String flow_id = request.getParameter("flow_id");
		List<Map<String, Object>> flowNode=new ArrayList<Map<String,Object>>();
		if (flow_id != null) {
		    map.put("what", flow_id);
			flowNode=flowService.findFlowNode(map);
		}
		json.put("rows", flowNode);
		return json;
	}
	
	/**
	 * 审核基础资料新增修改界面保存按钮
	*/
	@RequestMapping(value="/saveFlow" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveFlow(Flow flow) throws Exception {
		Map<String, Object> json = new HashMap<String, Object>();
		
		try {
			flowService.submitFlowHead(flow);
			json.put("Success",true);
			json.put("Msg", "修改成功！");
	    } catch (Exception e) {
		    e.printStackTrace();
			json.put("Msg", "操作失败！");
			json.put("Success", false);
	   }
		return json;
	}
	
	/**
	 *方法说明：删除节点
	 *   flowNew.jsp
	*/
	@RequestMapping(value="/delectNode" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> delectNode(HttpServletRequest request) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		Map<String, Object> json=new HashMap<String, Object>();
		try {
		String node_Id =request.getParameter("cid");
	
		map.put("what",node_Id);  
		flowService.delectNode(map);
		} catch (Exception e) {
			json.put("msg", "操作有误，删除失败！");
			e.printStackTrace();
		}
		return json;
	}
	
	/**
	 * 删除流程同时删除节点
	*/
	@RequestMapping(value="/deleFlow" , method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> deleFlow(String flowid) throws Exception {
		Map<String, Object> map=new HashMap<String, Object>();
		Map<String, Object> json=new HashMap<String, Object>();
		try {
			map.put("what",flowid);        
			flowService.deleFlow(map);
			json.put("Success",true);
			json.put("Msg", "删除成功！");
	    } catch (Exception e) {
		    e.printStackTrace();
			json.put("Msg", "删除失败！");
			json.put("Success", false);
	   }
	return json;
	
	}
	
	
	

	/**
	 *  方法说明：添加流程节点
	 *         flowNew.jsp
	 * */
	@RequestMapping(value = "/saveFlowNode", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> saveFlowNode(HttpServletRequest request) {
		
		Map<String, Object> josn=new  HashMap<String, Object>();
		
		try {
			
		String node_id=request.getParameter("node_id");
		String flow_id=request.getParameter("flow_id");
		String node_name=request.getParameter("node_name");

	    String rowData=request.getParameter("ids");
		rowData = rowData.substring(1);
		String[] ids = rowData.split("]");
		FlowNode flowNode=new FlowNode();
		flowNode.setFlow_node_id(Integer.parseInt(node_id));
		flowNode.setFlow_node_name(node_name);
		flowNode.setOrders(Integer.parseInt(request.getParameter("orderid")));
		flowNode.setFlow_node_parentID(Integer.parseInt(flow_id));
		flowNode.setFlow_node_auditors(ids[0]);
	    flowService.submitFlow(flowNode);
		} catch (Exception e) {
			josn.put("msg", "操作有误!");
			
			e.printStackTrace();
		}
		return josn;
		
	}
	/**
	 *  方法说明：添加流程节点
	 *         flowNew.jsp
	 * */
	@RequestMapping(value = "/editFlowUser", method = RequestMethod.POST)
	@ResponseBody
	public Map<String, Object> editFlowUser(HttpServletRequest request) {
		
		Map<String, Object> josn=new  HashMap<String, Object>();
		
		try {
			String node_auditors=request.getParameter("node_auditors");
			String fields = " userid,username";
			StringBuffer sbf=new StringBuffer();
			sbf.append(" uc_user where userid in("
					+ node_auditors + ")");
			List<Map<String, Object>> listMap = userService.queryId(fields,
					sbf.toString());
			josn.put("listMap", listMap);
		} catch (Exception e) {
			josn.put("msg", "操作有误!");
			
			e.printStackTrace();
		}
		return josn;
		
	}
	
}
