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
<%-- 	<script src="<%=request.getContextPath()%>/res/layui/jquery-3.3.1.min.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script> --%>
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
   
      <div class="layui-input-inline">
        <input type="text" class="layui-input">
      </div> 
      <button class="layui-btn" data-type="reload" style="margin-left: 10px">搜索</button>
    </div>
  </div>
</div>
 
</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="LAY_table_user" lay-filter="demo"></table> 
  </div>   
  
  <!-- 表格查看按钮 -->
<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-xs" lay-event="settleConstructList" >项目列表</a>
</script>
<!--计算未支付  -->
<script type="text/html" id="sexTpl">
  {{#  if(d.payedTotal !=null){ }}
  	  <span style="color: #F581B1;">{{ parseFloat(d.total) - parseFloat(d.payedTotal) }}</span>
  {{#  } }}
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
         ,url:'build_settlements.do'
    	 ,cols: [[ //标题栏
            {field: 'construct_supplier_id', title: '供应商编码', width: 100, sort: true}
           ,{field: 'construct_supplier_name', title: '供应商名称', width: 250}
           ,{field: 'construct_supplier_addr', title: '供应商地址', width: 150}
           ,{field: 'construct_supplier_tel', title: '供应商联系方式', width: 150}
           ,{field: 'total', title: '共欠款', width: 120}
           ,{field: 'payedTotal', title: '已支付', width: 120}
           ,{field: 'owe', title: '未支付', width: 120,templet: '#sexTpl'}
           ,{field: 'psn', title: '操作',toolbar:'#barDemo', width:120}
    	 ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height: 750
  });
  

       var $ = layui.$, active = {
		    reload: function(){

		      //执行重载
		      table.reload('testReload', {
		        page: {
		          curr: 1 //重新从第 1 页开始
		        }
		        ,where: {
		      
		        }
		        
		      });
		    }
		  };
  
		 //监听工具条 table  lay-filter="demo"
		 table.on('tool(demo)', function(obj){
		   var data = obj.data;
		    //办理任务
		     if(obj.event == 'settleConstructList'){
		    	 location.href = "settleConstructList.do?" + $.param({
						'construct_supplier_id' : data.construct_supplier_id, 
						'construct_supplier_name' : data.construct_supplier_name, 
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