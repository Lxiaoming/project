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
   .layui-table-cell .layui-form-checkbox[lay-skin=primary]{
       top: 1px !important;
   }
   .layui-table-cell{
   padding:0 5px !important;
   }
  </style>
</head>
<body> 
<div class="demoTable" style="margin:20px 10px 10px 10px">
	 <div class="layui-form">
	  <div class="layui-form-item">
	    <div class="layui-inline">
	    
	      <div class="layui-input-inline">
	        <input type="text" class="layui-input" name="firmYear" id="firmYear" placeholder="年份">
	      </div> 
	      <div class="layui-input-inline">
	        <input type="text" class="layui-input" name="constuct_project_dep_name" id="constuct_project_dep_name" placeholder="项目">
	      </div> 
	 
	
	      <button class="layui-btn" id="btnSearch"  data-type="reload" style="margin-left: 10px;height: 37px">搜索</button>
	    </div>
	  </div>
	</div>
</div>

 <div style="margin: 10px 10px">
  <table class="layui-hide" id="lay_table_user" lay-filter="demo"></table>
</div> 

<script type="text/html" id="toolbarDemo">
   
</script>
<script type="text/html" id="barDemo">
 <a class="layui-btn layui-btn-xs" lay-event="detail">班组列表</a>
</script>
              
<script>
/* document.body.parentNode.style.overflow ="hidden";//禁止纵向滚动条 */
layui.use(['table','form','laypage','layer','laydate','jquery'], function(){
	 var table = layui.table
	   ,$ = layui.$ 
	   ,form = layui.form
	   ,laypage = layui.laypage
	   ,layer = layui.layer
	   ,laydate = layui.laydate;
    //方法级渲染
    table.render({
    elem: '#lay_table_user'
         ,url:'firmLaborCostPros.do?projectDep=' + ${projectDep}
         ,toolbar: '#toolbarDemo'
         ,totalRow: true
    	 ,cols: [[ //标题栏
    	           
    	            {field: 'construct_project_name', title: '项目', width: 150}
    	            ,{field: 'construct_project_leader', title: '项目负责人', width: 80,totalRowText: '合计'}
    	            ,{field: 'construct_project_workTeam_price', title: '单价', width: 70,}    	         
    	            ,{field: 'jan', title: '1月', width: 80,totalRow: true}
    	            ,{field: 'feb', title: '2月', width: 80,totalRow: true}
    	            ,{field: 'mar', title: '3月', width: 80,totalRow: true}
    	            ,{field: 'apr', title: '4月', width: 80,totalRow: true}
    	            ,{field: 'may', title: '5月', width: 80,totalRow: true}
    	            ,{field: 'jun', title: '6月', width: 80,totalRow: true}
    	            ,{field: 'jul', title: '7月', width: 80,totalRow: true}
    	            ,{field: 'aug', title: '8月', width: 80,totalRow: true}
    	            ,{field: 'sep', title: '9月', width: 80,totalRow: true}
    	            ,{field: 'oct', title: '10月', width: 80,totalRow: true}
    	            ,{field: 'nov', title: '11月', width: 80,totalRow: true}
    	            ,{field: 'december', title: '12月', width: 80,totalRow: true}
    	            ,{field: 'totalLaborCost', title: '累计付款', width: 100,totalRow: true}
    	            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:100}
    	         
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height: 650
  });
    laydate.render({
        elem: '#firmYear', //指定元素
        type:'year'
      });
    
       var $ = layui.$, active = {
    		 reload: function(){ //验证是否全选
    		    	var firmYear=$("#firmYear").val();
    		    	var constuct_project_dep_name=$("#constuct_project_dep_name").val();
    		    	   table.reload('testReload', {
     			    	 page: {
     			    		   curr: 1 //重新从第 1 页开始
     			    	 },where: {
     			    		'firmYear':firmYear,
     			    		'constuct_project_dep_name':constuct_project_dep_name
     			    	 } //设定异步数据接口的额外参数
     			    	 
     			      });
    		      }
    		  };
       
       	//绑定搜索点击事件
	    $('.demoTable .layui-btn').on('click', function(){
	      var type = $(this).data('type');
	      active[type] ? active[type].call(this) : '';
	    });
	    //监听工具条
	    table.on('tool(demo)', function(obj){
	      var data = obj.data;
	      switch (obj.event) {
	         //查看
		  case 'detail':
			location.href = "firmLaborCostTeam.do?" + $.param({
				'projectId':data.construct_project_id

			   });
			break;
		  default:
			break;
		  }
	    });
	});
		
</script>

</body>
</html>