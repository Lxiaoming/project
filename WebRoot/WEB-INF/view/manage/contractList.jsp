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
        <input type="text" class="layui-input" name="manage_contract_num" id="manage_contract_num" placeholder="合同编号">
      </div> 
      <div class="layui-input-inline">
        <input type="text" class="layui-input" name="manage_contract_name" id="manage_contract_name" placeholder="项目名称">
      </div> 
      
      <div class="layui-input-inline">
        <input type="text" class="layui-input" name="manage_contract_firstParty" id="manage_contract_firstParty" placeholder="发包方">
      </div>
	
	  <div class="layui-input-inline">
        <input type="text" class="layui-input" name="manage_contract_startTime" id="manage_contract_startTime" placeholder="年份">  
      </div>
		
      <div class="layui-input-inline" style="display:none;">
        <input type="text" class="layui-input" name="manage_contract_company" id="manage_contract_company" placeholder="项目名称">
      </div> 
      <button class="layui-btn" id="btnSearch" data-type="reload" style="margin-left: 10px;height: 37px">搜索</button>
    </div>
  </div>
</div>
 <div class="layui-btn-group demoTable">
  <button class="layui-btn layui-btn-sm" data-type="btnAdd">新增</button>
  <button class="layui-btn layui-btn-sm" onclick="btnCompany(1)">建设公司</button>
  <button class="layui-btn layui-btn-sm" onclick="btnCompany(2)">科技公司</button>
  <button class="layui-btn layui-btn-sm" onclick="btnCompany(3)">加盟合作</button>
  <button class="layui-btn layui-btn-sm" onclick="btnCompany('')">诚安时代</button>

</div>
</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="lay_table_user" lay-filter="demo"></table>
</div>   
<script type="text/html" id="barDemo">
 <div class="layui-btn-group">
  <a class="layui-btn layui-btn-xs" lay-event="btnEdit">修改</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="delete">删除</a>
 
  </div>
</script>
              
<script>
/* document.body.parentNode.style.overflow ="hidden";//禁止纵向滚动条 */
  var index = 1; //获取窗口索引   
 // var index = parent.layer.getFrameIndex(window.name); //相当于   var index = 1; 获取窗口索引   
layui.use(['table','laypage','layer','laydate','jquery'], function(){
	 var table = layui.table
	   ,$ = layui.$ 
	   ,laypage = layui.laypage
	   ,layer = layui.layer
	   ,laydate = layui.laydate;
    //方法级渲染
    table.render({
    elem: '#lay_table_user'
         ,url:'contractLists.do'
    	 ,cols: [[ //标题栏
   	             {field: 'manage_contract_num',title:'合同编号', width: 120}
   	            ,{field: 'manage_contract_firstParty',title:'发包方(甲方)', width: 250}
   	            ,{field: 'manage_contract_name', title:'项目名称', width: 250}
   	            ,{field: 'manage_contract_startTime', title:'合同开始时间', width:90}
   	            ,{field: 'manage_contract_amount', title:'合同金额', width: 110}
   	            ,{field: 'manage_contract_visaAmount',title:'签证金额', width: 100,}
   	            ,{field: 'totalAmount',title:'合同总金额', width: 150,templet:'<div>{{totalAmount(d.manage_contract_amount,d.manage_contract_visaAmount)}}</div>'}
   	            ,{field: 'receiveAmount',title:'收款总金额', width: 100,}
   	            ,{field: 'noGatherAmount',title:'未收款金额', width: 100,templet:'<div>{{noGatherAmount(d)}}</div>'}
   	            ,{fixed: 'right', title:'操作',toolbar:'#barDemo', width:105}
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height: 750
  });
    
    layui.use('laydate', function(){
   	  var laydate = layui.laydate;
   	  
   	 //执行一个laydate实例
   	laydate.render({ 
   	    elem: '#manage_contract_startTime' //指定元素
   	    ,type: 'year'
   	  });
   	});  
  
     //监听工具条
       table.on('tool(demo)', function(obj){
         var data = obj.data;
         var manage_contract_id = data.manage_contract_id;
         if(obj.event === 'btnEdit'){
      		location.href = "contractNew.do?manage_contract_id="+manage_contract_id+"&type='update'";
      		//删除
          }else if(obj.event === 'delete'){    	
        	 layer.confirm('确定删除吗？', {
        		  btn: ['确定', '取消'] //可以无限个按钮
        		 ,btn1: function(index, layero){
        			 $.ajax({
 							type : 'POST',
 							url : 'delete_Contract.do',
 							data : {
 								'manage_contract_id' : manage_contract_id
 							},success : function(data) {
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
    		 //新增
    		   btnAdd: function(){
    			   location.href = "contractNew.do?manage_contract_id=";
    	         //重载
    		    },reload: function(){ //验证是否全选
    		    
    		    	   var manage_contract_num=$('#manage_contract_num').val();
    		    	   var manage_contract_name=$('#manage_contract_name').val(); 
    		    	   var manage_contract_firstParty=$('#manage_contract_firstParty').val();
    		    	   var manage_contract_startTime=$('#manage_contract_startTime').val();
    		    	   var manage_contract_company=$('#manage_contract_company').val();
    		    	   table.reload('testReload', {
     			    	 page: {
     			    		   curr: 1 //重新从第 1 页开始
     			    	 },where: {
     			    		 'manage_contract_num':manage_contract_num,
     			    		 'manage_contract_name':manage_contract_name,
     			    		 'manage_contract_firstParty':manage_contract_firstParty,
     			    		 'manage_contract_startTime':manage_contract_startTime,
     			    		 'manage_contract_company':manage_contract_company
     			    	 } //设定异步数据接口的额外参数
     			    	 
     			      });
    		      }
    		  }; 
       		  //绑定搜索点击事件
		  $('.demoTable .layui-btn').on('click', function(){
		    var type = $(this).data('type');
		    active[type] ? active[type].call(this) : '';
		  });
       		  
       		  
		  //监听行单击事件（单击事件为：rowDouble）
		  table.on('row(demo)', function(obj){
		    var data = obj.data;
		    parent.getData(data); //子页面js事件
		    parent.layer.close(index); //关闭子窗口
		  
		  });
		 
		  
		});
		
             //格式合同总金额
		function totalAmount(value1,value2) {
            	 
			return (parseFloat(value1==null?"0":value1) + parseFloat(value2==null?"0":value2)).toFixed(2);

		}
             
        //格式未收款金额
		function noGatherAmount(d) {
			var value1=d.manage_contract_amount;
			var value2=d.manage_contract_visaAmount;
			var receiveAmount=	d.receiveAmount==null?"0":d.receiveAmount;
			
			var onAmount=parseFloat(totalAmount(value1,value2)-receiveAmount).toFixed(2);
		
		    if(onAmount!=null && onAmount!=undefined){
		    	
		    	return onAmount;
		    }else{
		    	return 0;
		    }
			
		 }
        
        //点击公司查询事件
		function btnCompany(obj){
				$("#manage_contract_company").val(obj);

			//IE
			if (document.all) {
				document.getElementById("btnSearch").click();
			}
			// 其它浏览器
			else {
				var e = document.createEvent("MouseEvents");
				e.initEvent("click", true, true); //这里的click可以换成你想触发的行为
				document.getElementById("btnSearch").dispatchEvent(e); //这里的clickME可以换成你想触发行为的DOM结点
			}
		}

       
				
</script>

</body>
</html>