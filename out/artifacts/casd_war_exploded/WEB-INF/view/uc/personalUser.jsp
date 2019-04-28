<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <title></title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
     <link rel="stylesheet" href="<%=request.getContextPath()%>/res/layui/css/layui.css?HJ_v=<%=Math.random()%>"  media="all">
	<script src="<%=request.getContextPath()%>/res/layui/layui.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
	<script src="${CASD_PATH}/res/layui/format/dateformat.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/res/layui/jquery.min.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
  <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
  <style type="text/css">
 .layui-table td, .layui-table th{
      font-size: 13px !important;
      color: #3a3a3a;
  }
  /*设置隔行颜色  */
   table tr:nth-child(2n){
   background-color:#FFF0F5;
   }
  .layui-input, .layui-select, .layui-textarea{
      height: 35px !important;
  }
  </style>
</head>
<body> 
<div class="demoTable" style="margin:20px 10px 10px 10px">
 <div class="layui-form">
  <div class="layui-form-item">
    <div class="layui-inline">
    </div>
  </div>
</div>
 
</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="LAY_table_user" lay-filter="demo"></table> 
  </div>   
  
  <!-- 表格查看按钮 -->
<script type="text/html" id="barDemo">
  <div class="layui-btn-group">
  	<a class="layui-btn layui-btn-xs" lay-event="look">查看</a>
 </div>
</script>



<script>
/* document.body.parentNode.style.overflow ="hidden";//禁止纵向滚动条 */
layui.use(['table','laypage','layer','laydate','jquery'], function(){
	 var table = layui.table
	   ,$ = layui.$ 
	   ,laypage = layui.laypage
	   ,layer = layui.layer
	   ,laydate = layui.laydate;
  
 //方法级渲染
  table.render({
    elem: '#LAY_table_user'
    ,url: 'personalUser.do'
    ,cols: [[
       {field:'user_num', title:'工号',width:60}
      ,{field:'username', title: '姓名',width:120}
      ,{field:'phone_number', title: '电话号码', width:120,}
      ,{field:'sex', title: '性别', width:120, }
      ,{field:'email', title: '邮箱 ', width:170,}
      ,{field:'company_name', title: '公司',width:200,}
      ,{field:'center_name', title: '中心 ', width:200,}
      ,{field:'department_name', title: '部门 ', width:200,}
      ,{field: 'psn', title: '操作',toolbar:'#barDemo', width:160}
      ]]
    ,id: 'testReload'
    ,page: true
    ,limit:15
    ,height: 750
  });
  
       var $ = layui.$, active = {
		    reload: function(){	
		    	   var hr_templatel_type = $('#hr_templatel_type').val();
		    	//执行重载
		      table.reload('testReload', {
		        page: {
		          curr: 1 //重新从第 1 页开始
		        }
		        ,where: {
		         'hr_templatel_type':hr_templatel_type
		      
		        }
		        
		      });
		    }
		  };
  
		 //监听工具条 table  lay-filter="demo"
		 table.on('tool(demo)', function(obj){
		   var data = obj.data;
		    //查看
		     if(obj.event == 'look'){
		    	 location.href = "personalRecords.do?" + $.param({
						'cid' : data.userid, //获取用户id
						'department' : data.department,
						'save':'save'
					});
		     }
		     });

		  //绑定搜索点击事件
		  $('.demoTable .layui-btn').on('click', function(){
		    var type = $(this).data('type');
		    active[type] ? active[type].call(this) : '';
		  });
		
		});
				
</script>

</body>
</html>