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
	     <select id="manage_contractapprove_company">     
	       <option value="">请公司</option>
	       <option value="1">建设公司</option>
	       <option value="2">科技公司</option>
	       <option value="3">加盟合作</option>

	     </select>
      </div> 
       <div class="layui-input-inline">
        <input type="text" class="layui-input" id="manage_contractapprove_name" placeholder="项目名称">
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
 <div class="layui-btn-group">
    <button class="layui-btn layui-btn-sm" lay-event="btnAdd">增加</button>
    <button class="layui-btn layui-btn-sm" lay-event="btnEdit">编辑</button>
    <button class="layui-btn layui-btn-sm" lay-event="delete">删除</button>
  </div>
</script>
<script type="text/html" id="barDemo">
 <a class="layui-btn layui-btn-xs" lay-event="view">查看</a>
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
         ,url:'contractapproveLists.do'
         ,toolbar: '#toolbarDemo'
         ,totalRow: true
    	 ,cols: [[ //标题栏
    	             {type:'checkbox', fixed: 'left',width:60}
    	            ,{field: 'manage_contractapprove_name', title: '项目名称', width: 150}
    	            ,{field: 'manage_contractapprove_firstParty', title: '发包方（甲方）', width: 150}
    	            ,{field: 'manage_contractapprove_address', title: '项目地址', width: 150}
    	            ,{field: 'manage_contractapprove_startTime', title: '合同开始时间', width: 100,}
    	            ,{field: 'manage_contractapprove_endTime',title: '合同结束时间', width: 100,}    	         
    	            ,{field: 'manage_contractapprove_amount', title: '合同金额', width: 80}
    	            ,{field: 'manage_contractapprove_attachAddress', title: '附件地址', width: 80}
    	            ,{field: 'manage_contractapprove_remark', title: '备注'}
    	            ,{field: 'manage_status', title: '状态', width: 80, templet:'<div>{{operate_status(d.manage_status)}}</div>'}	           
    	            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:175}
    	         
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height: 650

  });
    laydate.render({
         elem: '#hr_attend_date'
        ,format: 'yyyy'
      });
    
    
    //监听单元格事件
    table.on('tool(demo)', function(obj){
      var data = obj.data;
      
      switch (obj.event) {
		case 'view':
			location.href = "contractapproveNew.do?" + $.param({
				'manage_contractapprove_id':+data.manage_contractapprove_id,
		       	'type':'view',
			}); 
		
			break;

		default:
			break;
		}

    });
    
  //头工具栏事件
    table.on('toolbar(demo)', function(obj){
      var checkStatus = table.checkStatus('testReload')
         ,data = checkStatus.data;
      switch(obj.event){
      //新增
        case 'btnAdd':
        	location.href = "contractapproveNew.do?manage_contractapprove_id=";
        break;
        //编辑
        case 'btnEdit':
			if(data.length==0){
				layer.alert("请选择要修改行！");
				return ;
			}else if(data.length>1){
				layer.alert("只能选中一行！");
				return ;
			}else if(data[0].manage_status!=0){
				layer.alert("审核中不可修改！");
				return ;
			 }else{
				location.href = "contractapproveNew.do?manage_contractapprove_id="+data[0].manage_contractapprove_id+"&type='update'";
			}
        break;
        case 'delete':
        	//删除处理
    			if(data.length==0){
    				layer.alert("请选择要删除行！");
    			}else if(data.length>1){
    				layer.alert("只能选中一行！");
    			}else{
    				var manage_contractapprove_id = data[0].manage_contractapprove_id;
    				
    				 layer.confirm('确定删除吗？', {
   	        		  btn: ['确定', '取消'] //可以无限个按钮
   	        		 ,btn1: function(index, layero){
    						$.ajax({
    							type : 'POST',
    							url : 'delete_Contractapprove.do',
    							data : {
    								'manage_contractapprove_id' : manage_contractapprove_id
    							},
    							success : function(data) {
    	
    								if(data.Success){
   									 layer.msg(data.Msg, {icon: 6, time: 1000,shade:[0.3]}, function () {
   		 	                                location.reload();
   		 	                            });
   							         	}else{
   									
   									  layer.msg(data.Msg, {icon: 5, time: 3000,shade:[0.3]});
   							     	}  
    					    	}
    						  });
    					  }
    				  });
    	         	}
            break;  	
          };
         }); 

       var $ = layui.$, active = {
    		 reload: function(){ //验证是否全选
    		    	var manage_contractapprove_name=$("#manage_contractapprove_name").val();
    		     	var manage_contractapprove_company=$("#manage_contractapprove_company").val();
    		    
    		    	   table.reload('testReload', {
     			    	 page: {
     			    		   curr: 1 //重新从第 1 页开始
     			    	 },where: {
     			    		'manage_contractapprove_name':manage_contractapprove_name,
     			    		'manage_contractapprove_company':manage_contractapprove_company

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
		
		
	//	
	function operate_status(value){

            if (value == 0) {		
				return "数据已录入";
			} else if (value == 1) {
				  return '<span style="color: #F581B1;">审核中</span>';
			} else if (value ==3) {
				return  '<span style="color: #00CD00;">审核通过</span>';
			}
				
		}	

		
</script>

</body>
</html>