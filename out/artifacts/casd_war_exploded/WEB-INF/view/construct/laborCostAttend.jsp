<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <title>layui</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="<%=request.getContextPath()%>/res/layui/css/layui.css"  media="all">
  <script src="<%=request.getContextPath()%>/res/layui/layui.js" charset="utf-8"></script>
</head>
<body>
 <div class="demoTable" style="margin:20px 10px 10px 10px">
	 <div class="layui-form">
	  <div class="layui-form-item">
	    <div class="layui-inline">
	      <div class="layui-input-inline">
	        <input type="text" class="layui-input" name="labor" id="labor" placeholder="打卡人">
	      </div> 
	      <div class="layui-input-inline">
	        <input type="text" class="layui-input" name="attend_date" id="attend_date" placeholder="yyyy-mm-dd">
	      </div> 
	 
	
	      <button class="layui-btn" id="btnSearch"  data-type="reload" style="margin-left: 10px;height: 37px">搜索</button>
	    </div>
	  </div>
	</div>
</div>
<table class="layui-hide" id="test" lay-filter="test"></table>
 
<script>

layui.use(['table','form','laypage','layer','laydate','jquery'], function(){
  var table = layui.table
  ,$ = layui.$ 
  ,form = layui.form
  ,laypage = layui.laypage
  ,layer = layui.layer
  ,laydate = layui.laydate;
  
  //引入时间插件
   laydate.render({
    elem: '#attend_date'
  });
  
  table.render({
    elem: '#test'
    ,url:'laborCostAttends.do?hr_attend_date=${hr_attend_date}&hr_attend_workTeamId=${hr_attend_workTeamId}'
    ,toolbar: 'true'
    ,title: '用户数据表'
    ,cols: [[
       {field:'construct_project_name', title:'项目', width:320,align:'center',templet: '<div>{{name_num(d)}}</div>'}
      ,{field:'construct_project_workTeam_category', title:'施工项目', width:100,align:'center',templet:'<div>{{category(d)}}</div>'}
      ,{field:'labor', title:'打卡人', width:80,align:'center'}
      ,{field:'hr_attend_date', title:'上班打卡日期', width:150,align:'center'}
      ,{field:'hr_attend_startWork', title:'上班打卡时间',width:150,align:'center'}
      ,{field:'hr_attend_knockOff', title:'下班打卡时间', width:150,align:'center'}
      ,{field:'hr_attend_workAddress', title:'上班打卡地址', width:220,align:'center'}
      ,{field:'hr_attend_offWorkAddress', title:'下班打卡地址', width:220,align:'center'}
      ,{field:'hr_attend_WTLength', title:'工作时长', width:120,align:'center'}
    ]]
    ,page: true
  });
  
  var $ = layui.$, active = {
		  reload: function(){ //验证是否全选
				var labor=$("#labor").val();
				var attend_date=$("#attend_date").val();
				table.reload('test', {
					page: {
						curr: 1 //重新从第 1 页开始
					},where: {
						'labor':labor,
						'attend_date':attend_date
					} //设定异步数据接口的额外参数
				});
			}
		
			  
	};
	//绑定搜索点击事件
	$('.demoTable .layui-btn').on('click', function(){
		var type = $(this).data('type');
		active[type] ? active[type].call(this) : '';
	});

});


	function name_num (d){
		return d.construct_project_name+d.manage_contract_num;
		
	}
	
	function category(d) {
		switch (d.construct_project_workTeam_category) {
		case 1:
			return "预埋";
			break;
		case 2:
			return "消防水";
			break;
		case 3:
			return "消防电";
			break;
		case 4:
			return "防排烟";
			break;
		case 5:
			return "消防水电";
			break;
		default:
			break;
		}
	}
</script>

</body>
</html>