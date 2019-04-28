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
    <label class="layui-form-label" style="margin-left: -40px">项目名称</label>
     <div class="layui-input-inline">
        <input type="text" class="layui-input" name="construct_project_name" id="construct_project_name" >
     </div> 
    <div class="layui-input-inline">
      <select id="construct_purchase_status" name="construct_purchase_status">
            <option value="">采购状态</option>
	        <option value="0">保存</option>
			<option value="1">项目经理提交</option>
			<option value="2">经营部审核</option>
			<option value="3">总经理审核</option>
			<option value="4">采购核对单价</option>
			<option value="5">财务经理审批</option>
			<option value="6">董事会审核</option>
			<option value="7">下单</option>
			<option value="8">配货</option>
			<option value="9">签收</option>
			<option value="10">财务复核价格及数量</option>
			<option value="11">申请请款</option>
			<option value="12">通知财务审核</option>
			<option value="13">待付款</option>
      </select>
    </div>
    <div class="layui-input-inline">
     <button class="layui-btn" data-type="reload" style="margin-left: 10px;height: 35px">搜索</button>
    </div>
  </div>
</div>
</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="lay_table_user" lay-filter="demo"></table>
</div>   
<script type="text/html" id="barDemo">
 <div class="layui-btn-group">
  <a class="layui-btn layui-btn-xs" lay-event="handle">查看</a>
 
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
    elem: '#lay_table_user'
         ,url:'matPurReports.do'
         ,totalRow: true
    	 ,cols: [[ //标题栏
   	          //  ,{field: 'field_personnel_id', title:'ID',hidden:'hidden',width: 80}
   	             {field: 'construct_purchase_id',title:'编号',width:70}
   	            ,{field: 'construct_purchase_creatTime', title: '简单时间', width: 135,totalRowText: '合计'}
   	            ,{field: 'construct_project_name', title: '项目名称', width: 150,}
   	            ,{field: 'construct_project_leader', title: '项目经理', width: 80}
   	            ,{field: 'construct_purchase_supplier', title: '供应商', width: 115}
   	            ,{field: 'construct_purchase_materialSerName', title: '采购分类', width: 150}
   	            ,{field: 'total', title: '采购合计', width:70,totalRow: true}
   	            ,{field: 'construct_purchase_status', title: '采购状态', width: 135
   	            ,templet:'<div>{{status_formatter(d.construct_purchase_status)}}</div>'}  
   	            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:115}
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height: 650
  });
  
     //监听工具条
       table.on('tool(demo)', function(obj){
         var data = obj.data;
         //查看
         if(obj.event === 'handle'){
        		location.href = "Purchase_payment.do?" + $.param({
					'bizId' : data.construct_purchase_id, 
				});
        	
          }
       });
       
       var $ = layui.$, active = {
    		   reload: function(){ //重载
    		    	   var construct_project_name=$('#construct_project_name').val();
    		    	   var construct_purchase_status=$('#construct_purchase_status').val();   		    	
    		    	   table.reload('testReload', {
     			    	 page: {
     			    		   curr: 1 //重新从第 1 页开始
     			    	 },where: {
     			    		 'construct_project_name':construct_project_name
     			    		,'construct_purchase_status':construct_purchase_status
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
		

		//格式化采购状态
		function status_formatter(value) {
			switch (value) {
			case 0:
				return "保存";
				break;
			case 1:
				return "项目经理提交";
				break;
			case 2:
				return "经营部审核";
				break;
			case 3:
				return "总经理审核";
				break;
			case 4:
				return "采购核对单价";
				break;
			case 5:
				return "财务经理审批";
				break;
			case 6:
				return "董事会审核";
				break;
			case 7:
				return "下单";
				break;
			case 8:
				return "配货";
				break;
			case 9:
				return "签收";
				break;
			case 10:
				return "财务复核价格及数量";
				break;
			case 11:
				return "申请请款";
				break;
			case 12:
				return "通知财务审核";
				break;
			case 13:
				return "待付款";
				break;		
			default:
				break;
			}
			
		}

		
</script>

</body>
</html>