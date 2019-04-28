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
</head>
<body> 
<div class="demoTable" style="margin:20px 10px 10px 10px">
 <div class="layui-form">
  <div class="layui-form-item">
    <div class="layui-inline">
       <div class="layui-input-inline">
        <input type="text" class="layui-input" id="construct_Aparty_material_category" placeholder="材料类别">
      </div> 
      <div class="layui-input-inline">
        <input type="text" class="layui-input" id="construct_Aparty_material_name" placeholder="材料名称">
      </div> 
      <div class="layui-input-inline">
        <input type="text" class="layui-input" id="construct_Aparty_material_model" placeholder="材料规格">
      </div> 
      <button class="layui-btn" data-type="reload" style="margin-left: 10px;height: 37px">搜索</button>
    </div>
  </div>
</div>
</div>
 <div style="margin: 10px 10px">
  <table class="layui-hide" id="lay_table_user" lay-filter="lay_table_user"></table>
</div>   



<script>
/* document.body.parentNode.style.overflow ="hidden";//禁止纵向滚动条 */
var index = parent.layer.getFrameIndex(window.name); //获取窗口索引
layui.use(['table','laypage','layer','laydate','jquery'], function(){
	 var table = layui.table
	   ,$ = layui.$ 
	   ,laypage = layui.laypage
	   ,layer = layui.layer
	   ,laydate = layui.laydate;
    //方法级渲染
    table.render({
    elem: '#lay_table_user'
    	 ,url:'aPartyMaterialChecks.do?construct_project_id="+${construct_project_id}+"&index="+index+"&rows=20'
    	 ,cols: [[ //标题栏
    	          	{field: 'construct_Aparty_material_category', title: '材料类别', width: 90}
    	            ,{field: 'construct_Aparty_material_name', title: '材料名称', width: 95}
    	            ,{field: 'construct_Aparty_material_model', title: '型号规格', width: 200}
    	            ,{field: 'construct_Aparty_material_unit', title: '单位', width: 152,}
    	            ,{field: 'construct_Aparty_material_num', title: '合同量', width: 105}
    	            ,{field: 'construct_Aparty_material_remark', title: '备注', width: 110}
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height: 750
  });
  
  //监听行单击事件（单击事件为：rowDouble）
    table.on('rowDouble(lay_table_user)', function(obj){
    	
        var data = obj.data;
	    parent.setaPartyMateData(data); //子页面js事件
	    parent.layer.close(index); //关闭子窗口
	  
      
    });
       
    var $ = layui.$, active = {
		    reload: function(){
		    	var construct_Aparty_material_category = $('#construct_Aparty_material_category').val();
				var construct_Aparty_material_name = $('#construct_Aparty_material_name').val();
				var construct_Aparty_material_model = $('#construct_Aparty_material_model').val();
		      //执行重载
		      table.reload('testReload', {
		        page: {
		          curr: 1 //重新从第 1 页开始
		        }
		        ,where: {
		        	construct_Aparty_material_category : construct_Aparty_material_category,
					construct_Aparty_material_name : construct_Aparty_material_name,
					construct_Aparty_material_model : construct_Aparty_material_model
		        }
		        
		      });
		    }
		  };
  
	
	$('.demoTable .layui-btn').on('click', function(){
	    var type = $(this).data('type');
	    active[type] ? active[type].call(this) : '';
	 });
	
	});
</script>

</body>
</html>