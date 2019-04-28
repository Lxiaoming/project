<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<%@ taglib uri="PowerCheck" prefix="shop"%>
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
        <input type="text" class="layui-input"  id="searchId" placeholder="菜单编号">
      </div> 
        <div class="layui-input-inline">
        <input type="text" class="layui-input" id="searchName" placeholder="菜单名">
      </div> 
      <div class="layui-input-inline">
        <input type="text" class="layui-input" id="parentid" placeholder="父级菜单">
      </div> 
      <button class="layui-btn" data-type="reload" style="margin-left: 10px;height: 37px">搜索</button>
    </div>
  </div>
</div>
 <div class="layui-btn-group demoTable">
 <shop:permission code="NEW_MENU">
  <button class="layui-btn layui-btn-sm" data-type="btnAdd">新增</button>
  </shop:permission>
</div>
</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="lay_table_user" lay-filter="demo"></table>
</div>   

 <!-- 需要弹出的添加员工界面 -->
        <div class="layui-row" id="fromdiv" style="display: none;">
        <div style="margin: 10px 0"></div>
            <div class="layui-col-md10">
                <form class="layui-form" id="addEmployeeForm">
                    <div class="layui-form-item" style="display:none">
                        <label class="layui-form-label">菜单编号：</label>
                        <div class="layui-input-block">
                            <input type="text"  name="id" id="id" class="layui-input">
                        </div>
                    </div>
                     <div class="layui-form-item">
                        <label class="layui-form-label">菜单名</label>
                        <div class="layui-input-block">
                           <input type="text" name="menu_name" id="menu_name" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">父菜单编号</label>
                        <div class="layui-input-block">
                           <input type="number" name="parent_id" id="parent_id" class="layui-input">
                        </div>
                    </div>
                      <div class="layui-form-item">
                        <label class="layui-form-label">菜单url</label>
                        <div class="layui-input-block">
                          <input type="text" name="menu_url" id="menu_url" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">菜单顺序</label>
                        <div class="layui-input-block">
                           <input type="number"name="order" id="order" class="layui-input">
                        </div>
                    </div>
                    
                     <div class="layui-form-item">
                        <label class="layui-form-label">权限编号 </label>
                        <div class="layui-input-block">
                           <input type="text" name="program_code" id="program_code" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
					   <label class="layui-form-label">是否菜单</label>
					   <div class="layui-input-block">
					     <input type="radio" name="ismenu" id="ismenu1" value="1" title="是" checked>
					     <input type="radio" name="ismenu" id="ismenu2"value="2" title="否">
					   </div>
					 </div>
                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <button type="button" class="layui-btn layui-btn-normal" onclick="btnSave();">提交</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>

<script type="text/html" id="barDemo">
 <div class="layui-btn-group">
  <a class="layui-btn layui-btn-xs" lay-event="btnEdit">编辑</a>
  <a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="deleMenu">删除</a>
  </div>
</script>  
<script>
/* document.body.parentNode.style.overflow ="hidden";//禁止纵向滚动条 */
layui.use(['table','laypage','layer','form','jquery'], function(){
	 var table = layui.table
	   ,$ = layui.$ 
	   ,laypage = layui.laypage
	   ,layer = layui.layer
	   ,form = layui.form
	 
    //方法级渲染
    table.render({
    elem: '#lay_table_user'
         ,url:'menuLists.do'
    	 ,cols: [[ //标题栏
    	         {field:'id', title:'菜单编号',width: 70}  
   	            ,{field: 'menu_name', title: '菜单名', width: 150}
   	            ,{field: 'parent_id', title: '父级菜单编号', width: 100,align:'center'}
   	            ,{field: 'menu_url', title: '菜单url',width: 200}
   	            ,{field: 'order', title: '菜单顺序', width: 100,align:'center'}
   	            ,{field: 'ismenu', title: '是否菜单项', width: 80,align:'center',templet:'<div>{{is_formatter(d.ismenu)}}</div>'}
   	            ,{field: 'program_code', title: '权限编号'} 
   	            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:105}
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height: 750
  });
  
     //监听工具条
       table.on('tool(demo)', function(obj){
         var data = obj.data;
         if(obj.event === 'btnEdit'){
        	 layer.open({
		    	  type: 1,
		          area: ['40%'],
		          shadeClose: false, //开启遮罩关闭
		          content:$("#fromdiv"),
		    	}); 
        	 //赋值编辑表单
        	 document.getElementById("addEmployeeForm").reset();
        	 $("#id").attr("value", data.id);
        	 $("#menu_name").attr("value", data.menu_name);
	 			$("#parent_id").attr("value",data.parent_id);
	 			$("#menu_url").attr("value", data.menu_url);
	 			$("#order").attr("value", data.order);
	 			$("#program_code").val(data.program_code);
	 			$("input[name=ismenu][value=1]").attr("checked", data.ismenu == 1 ? true : false);
	 	        $("input[name=ismenu][value=2]").attr("checked", data.ismenu == 2 ? true : false);
	 	         form.render(); //更新全部       
	 	         
	 	      //删除菜单   
         }else if(obj.event === 'deleMenu'){    	
        	 layer.confirm('确定删除吗？', {
          		  btn: ['确定', '取消'] //可以无限个按钮
          		 ,btn1: function(index, layero){
        	 $.ajax({
					type : 'POST',
					url : 'deleMenu.do',
					data : {
						'id' :data.id
					},
					success : function(data) {
						if (data.Success) {
							 layer.alert(data.Msg, {icon: 6, time: 1500,shade:[0.3]}, function () {
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
    			   
    			   layer.open({
    			    	  type: 1,
    			          title:"添加员工",
    			          area:["40%"],
    			          content:$("#fromdiv"),
    			    	});
    			   $("#addEmployeeForm").find('input[type=text],select,input[type=hidden],input[type=number]').each(function() {
    				           $(this).val('');
    				       });
    			
    		    },reload: function(){ //验证是否全选
    		    	   var searchId=$('#searchId').val();
    		    	   var searchName=$('#searchName').val();
    		    	   var parentid=$('#parentid').val();
    		    	   table.reload('testReload', {
     			    	 page: {
     			    		   curr: 1 //重新从第 1 页开始
     			    	 },where: {
     			    		 'searchId':searchId
     			    		,'searchName':searchName
     			    		,'parent_id':parentid
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
		
		//格式化菜单
		function is_formatter(value) {
			if (value == 1) {
				  return '<span style="color: #F581B1;">是</span>';	
			} else if (value == 2) {
				return "否";
		
			}
		}
	    //新增菜单和编辑菜单
		function btnSave(){
		  $.ajax({
				type : 'POST',
				url : 'addMenu.do',
				data : 	$("#addEmployeeForm").serialize(),
				success : function(data) {
					if (data == "") {
						 layer.msg("操作成功", {icon: 6, time: 1500,shade:[0.3]}, function () {
							 location.reload();
					   });
					} else {
						 layer.msg("操作失败!", {icon: 5, time: 1000,shade:[0.3]});
					}
				   
					}
				})
	    	}
		
</script>

</body>
</html>