<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>首页</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
	  <link rel="stylesheet" href="<%=request.getContextPath()%>/res/layui/css/layui.css"  media="all">
	  <script src="<%=request.getContextPath()%>/res/layui/layui.js" charset="utf-8"></script>
	  <script src="<%=request.getContextPath()%>/res/jquery-easyui/jquery.min.js" charset="utf-8"></script>

<style type="text/css">
</style>
</head>
<body class="layui-layout-body" style="width: 877px">         
  <div class="layui-header">
     <div>
     </div>
  </div>
  <div class="layui-boy">
<table class="layui-hide" id="test" lay-filter="test" style="width: 700px"></table>
 </div>
 <!-- 需要弹出的添加员工界面 -->
        <div class="layui-row" id="fromdiv" style="display: none;">
        <div style="margin: 10px 0"></div>
            <div class="layui-col-md10">
                <form class="layui-form" id="addEmployeeForm">
                    <div class="layui-form-item" style="display:none">
                        <label class="layui-form-label">用户id：</label>
                        <div class="layui-input-block">
                            <input type="text" value="${mpaList.userid}" name="user_id" class="layui-input">
                        </div>
                    </div>
                     <div class="layui-form-item">
                        <label class="layui-form-label">姓名：</label>
                        <div class="layui-input-block">
                           <input type="text" name="username" id="username" value="${mpaList.username}" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">职位：</label>
                        <div class="layui-input-block">
                           <input type="text"name="role_name" id="role_name" value="${mpaList.role_name}" readonly="readonly" class="layui-input">
                        </div>
                    </div>
                      <div class="layui-form-item">
                        <label class="layui-form-label">试用期：</label>
                        <div class="layui-input-block">
                          <input type="text" name="incorporation_date" id="date" value="${mpaList.incorporation_date}" placeholder="yyyy-MM-dd" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">~至：</label>
                        <div class="layui-input-block">
                           <input type="text"value="${mpaList.close_time}" name="close_time" id="date1"  placeholder="yyyy-MM-dd" class="layui-input">
                        </div>
                    </div>
                    
                     <div class="layui-form-item">
                        <label class="layui-form-label">部门：</label>
                        <div class="layui-input-block">
                           <input value="${mpaList.department_name}" name="bc_department" id="bc_department" class="layui-input">
                        </div>
                    </div>
                    <div class="layui-form-item">
                        <label class="layui-form-label">试用待遇：</label>
                        <div class="layui-input-block">
                           <input value="${mpaList.on_trial}" name="on_trial" id="on_trial" class="layui-input">
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
<script type="text/html" id="toolbarDemo">
  <div class="layui-btn-container">
    <button class="layui-btn layui-btn-sm" lay-event="getCheckData">添加</button>
  </div>
</script>

<script>
layui.use(['table','form', 'layedit', 'laydate'], function(){
  var table = layui.table
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate;
  
  //展示已知数据
  table.render({
    elem: '#test'
    ,height:400
    ,toolbar: '#toolbarDemo'
    ,cols: [[ //标题栏
       {field: 'bc_id', title: '转正编号', width: 120}
      ,{field: 'user_id', title: '用户编号', width: 120}
      ,{field: 'username', title: '用户名', width: 120}
      ,{field: 'role_name', title: '职位', width: 120}
      ,{field: 'on_trial', title: '试用期待遇', width: 120}
      ,{field: 'bc_status', title: '状态', width: 120,
    	  templet:function(d){
			return status_formatter(d.bc_status);
  		  }
      }
      ,{field: 'email', title: '邮箱', width: 150}
    ]]
    ,data: ${rows}.rows1.rows
    //,skin: 'line' //表格风格
    ,even: true
    //,page: true //是否显示分页
    //,limits: [5, 7, 10]
    //,limit: 5 //每页默认显示的数量
  });
  
  //日期
  laydate.render({
    elem: '#date'
  });
  laydate.render({
    elem: '#date1'
  });
  //头工具栏事件
  table.on('toolbar(test)', function(obj){
    var checkStatus = table.checkStatus(obj.config.id);
    layer.open({
    	  type: 1,
          title:"添加员工",
          area:["40%"],
          content:$("#fromdiv"),
    	});
    
  });
 
  });

//监听提交
function btnSave(){
	  $.ajax({
			type : 'POST',
			url : 'submitBecome.do',
			data : 	$("#addEmployeeForm").serialize(),
			success : function(data) {
				if(data.Success){
			
					layer.alert('您确定提交吗？', {
						btn: ['确定'] //按钮
						}, function(){
							location.reload(true);
						});
				//
			}else{
				layer.alert(data.Msg, {
					icon: 5,
					title: "提示"
					});
			    }
				
			} 
			});
  }

  function status_formatter(value){
	  console.log(value);
	  if (value == 0) {
			return "初始录入";
		} else if (value == 2) {
			return "转正申请中";
		} else if (value == 3) {
			return "已转正";
		}
  }

</script>

</body>
</html>