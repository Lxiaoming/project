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
  </style>
</head>
<body> 
<div class="demoTable" style="margin:20px 10px 10px 10px">
 <div class="layui-form">
  <div class="layui-form-item">
    <div class="layui-inline">
       <div class="layui-input-inline">
        <input type="text" class="layui-input" name="costapp_company" id="costapp_company" placeholder="公司部门">
      </div> 
      <button class="layui-btn" data-type="reload" style="margin-left: 10px;height: 37px">搜索</button>
    </div>
  </div>
</div>
 <div class="layui-btn-group demoTable">
  <button class="layui-btn layui-btn-sm" data-type="btnAdd">新增</button>
  <button class="layui-btn layui-btn-sm" data-type="btnEdit">修改</button>
</div>
</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="lay_table_user" lay-filter="demo"></table>
</div>   

<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-xs" lay-event="view">查看</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="print">打印</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="btnDele">删除</a>
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
    elem: '#lay_table_user'
         ,url:'costappLists.do'
    	 ,cols: [[ //标题栏
    	             {type:'checkbox', fixed: 'left',width: 60}
    	            ,{field: 'costapp_id', title: 'ID', width: 95}
    	            ,{field: 'costapp_company', title: '公司/部门', width: 250}
    	            ,{field: 'costapp_appitem', title: '申请事项', width: 250}
    	            ,{field: 'costapp_application', title: '申请类型', width: 90}
    	            ,{field: 'costapp_time', title: '建单时间', width: 100,}
    	            ,{field: 'costapp_amount', title: '费用金额', width: 110}
    	            ,{field: 'costapp_status', title: '状态', width: 110,
    	            	templet:function(d){
    	            			return status_formatter(d.costapp_status);
    	            	}
    	            }
    	            /* ,{field: 'costapp_majoyview', title: '主办部门意见', width: 200}
    	            ,{field: 'costapp_managerview', title: '总经理意见', width: 200}
    	            ,{field: 'costapp_chairmanview', title: '董事长意见', width: 200}
    	            ,{field: 'costapp_financeview', title: '财务经理意见', width: 200}  */
    	            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:168}
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height: 750
  });
  
     //监听工具条
       table.on('tool(demo)', function(obj){
         var data = obj.data;
         if(obj.event === 'view'){
        	 location.href = "costappView.do?bizId="+data.costapp_id+"";
       
         } else if(obj.event === 'print'){
        	 location.href = "costappPrint.do?" + $.param({
			     'biz' : data.costapp_id, //业务编号
	 	});
         
         }else if(obj.event === 'btnDele'){    	
        	 layer.confirm('确定删除吗？', {
        		  btn: ['确定', '取消'] //可以无限个按钮
        		 ,btn1: function(index, layero){
        			 $.ajax({
 						type : 'POST',
 						url : 'delect_costapp.do',
 						data : {
 							'costapp_id':data.costapp_id
 						},
 						success : function(data) {
 							if (data.Success) {
 								 layer.msg(data.Msg, {icon: 6, time: 1000,shade:[0.3]}, function () {
 	                                location.reload();
 	                            });
 							} else {
 								 layer.msg(data.Msg, {icon: 5, time: 1000,shade:[0.3]});
 							}
 							}
 					});
        		  }
        		});
             }
       });
       
       var $ = layui.$, active = {
    		   btnAdd: function(){ //新增
    				location.href = "costappNew.do?costapp_id=";
    		    }
    		    ,btnEdit: function(){ //获取选中数目
    		    	 var checkStatus = table.checkStatus('testReload')
     			      ,data = checkStatus.data;
    		    	
    				if(data.length==0){
    					 layer.alert("请勾选要修改行！");
    				}else if(data.length>1){
    					 layer.alert("只能勾选中一行！");
    				}else{
    					location.href = "costappNew.do?costapp_id="+data[0].costapp_id;
    				}		    
    		    },reload: function(){ //验证是否全选
    		    	   var costapp_company=$('#costapp_company').val();
    		    	 
    		    	   table.reload('testReload', {
     			    	  url: 'costappLists.do'
     			    	 ,page: {
     			    		   curr: 1 //重新从第 1 页开始
     			    	 },where: {
     			    		 'costapp_company':costapp_company
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
		

		function status_formatter(value) {
			if (value == 1) {
				return "审批不通过";
			} else if (value == 3) {
				return "审批通过";
			} else if (value ==2) {
				return "审核中";
			}else if(value==0){
				return "初始录入";
			}
		}
		
	  	
		
</script>

</body>
</html>