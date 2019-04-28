<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<html>
<head>
<title>首页</title>
  <meta charset="utf-8">
  <title></title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
     <link rel="stylesheet" href="<%=request.getContextPath()%>/res/layui/css/layui.css?HJ_v=<%=Math.random()%>"  media="all">
	<script src="<%=request.getContextPath()%>/res/layui/layui.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
	<script src="${CASD_PATH}/res/layui/format/dateformat.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
	<script src="<%=request.getContextPath()%>/res/layui/jquery.min.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
<style type="text/css">
</style>
</head>
<body style="background-color: #B0B0B0">
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;">
  <legend>外出表单</legend>
</fieldset>
  <div class="layui-row">
        <div style="margin: 10px 0"></div>
            <div class="layui-col-md10">
 
<form class="layui-form" id="jvForm"style="margin-left:30px;">
  <div class="layui-form-item" hidden="hidden">
    <div class="layui-inline">
      <label class="layui-form-label">用户编号</label>
      <div class="layui-input-inline">
        <input type="text" value="${userid}" name="field_personnel_userid""class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">单据编号</label>
      <div class="layui-input-inline">
           <input type="text" value="${field_personnel_id==undefined?0:field_personnel_id}" 
                 name="field_personnel_id" id="field_personnel_id" class="layui-input">
       </div>
     </div>
  </div>

  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">申请人</label>
      <div class="layui-input-inline">
        <input type="text" id="username" value="${username}"class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">公司</label>
      <div class="layui-input-inline">
        <input type="text" value="${company_name}" name="field_personnel_company" class="layui-input">
      </div>
    </div>
</div>
  
    <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">车牌号</label>
      <div class="layui-input-inline">
        <input type="text" value="${field_personnel_license}" name="field_personnel_license"class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">职位</label>
      <div class="layui-input-inline">
        <input type="text" value="${role_name}" name="field_personnel_rolename" class="layui-input">
      </div>
    </div>
  </div>
  
    <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">开始时间</label>
      <div class="layui-input-inline">
        <input type="text" value="${start_time}" id="start_time" name="start_time"class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">结束时间</label>
      <div class="layui-input-inline">
        <input type="text" value="${end_time}" id="end_time" name="end_time" class="layui-input">
      </div>
    </div>
  </div>
  
  <div class="layui-form-item">
    <div class="layui-inline">
      <label class="layui-form-label">驾驶员</label>
      <div class="layui-input-inline">
        <input type="text" value="${field_personnel_driver}" name="field_personnel_driver" class="layui-input">
      </div>
    </div>
    <div class="layui-inline">
      <label class="layui-form-label">是否用车</label>
      <div class="layui-input-inline">
      <input type="radio" name="field_personnel_car" value="1" title="否" checked="">
      <input type="radio" name="field_personnel_car" value="2" title="是">
      </div>
    </div>
  </div>
  
 <div class="layui-form-item">
    <label class="layui-form-label">外出地址</label>
    <div class="layui-input-block">
      <input type="text" name="field_personnel_place" value="${field_personnel_place}" style="width: 515px" class="layui-input">
    </div>
  </div>
  <div class="layui-form-item layui-form-text" style="width: 625px">
    <label class="layui-form-label">外出原因</label>
    <div class="layui-input-block">
      <textarea placeholder="请输入内容" name="field_personnel_cause" class="layui-textarea">${field_personnel_cause}</textarea>
    </div>
  </div>
  
 
  <div class="layui-form-item">
  <c:if test="${startForm !=''}">
       ${startForm} 
    </c:if>  
    <c:if test="${startForm ==''}">
     <label class="layui-form-label"></label>
     <div class="layui-input-inline"> 
       <button type="button" class="layui-btn" onclick="clearForm()">返回</button>
    </div>  
    </c:if>  
    </div>
   </form>
  </div>
</div>
<div style="margin-left:  90px;">
  
	<jsp:include page="../../view/checkBox/process-flow.jsp"/>
 
</div>
<fieldset class="layui-elem-field layui-field-title" style="margin-top: 20px;border-color:#BEBEBE;">
  <legend style="font-size: 14px">审核记录</legend>
  </fieldset>
<div class="layui-row">
	<table class="layui-table" lay-size="sm" id="demo"></table>
</div>
<!-- 注意：如果你直接复制所有代码到本地，上述js路径需要改成你本地的 -->
<script>
layui.use(['table','form','layer','layedit','laydate'], function(){
  var table = layui.table
  var form = layui.form
  ,layer = layui.layer
  ,layedit = layui.layedit
  ,laydate = layui.laydate;
  
//日期
  laydate.render({
    elem: '#start_time'
    ,type: 'datetime'
  });
  laydate.render({
    elem: '#end_time'
   ,type: 'datetime'
  });
  
  table.render({
		elem : '#demo'
		,height:300
		,cols : [[
		         {field:'',width:88,title:'步骤序号',align:'center',type:'numbers'}
		        ,{field:'name_',title:'步骤名称',width:150}
		        ,{field:'username',title:'相关人员',width:90,}
		        ,{field:'START_TIME_',title:'发生时间',width:160,templet:function(res){
		        	
		        return  dateFormat(res.START_TIME_.time, "yyyy-MM-dd HH:mm:ss");
		        	
		        }}
		        ,{field:'MESSAGE_',title:'审核意见',width:200,}
		        ,{field:'description_',title:'内容'}
		        ]]
	           ,data:${history}
	           ,even:true
   });

});



//提交单据
function submitForm(){
	   var userid=$("#userid").val();
	   if (userid == '') {
			 layer.msg("审核人不能为空", {icon: 5, time: 1500,shade:[0.3]});
			return;
		}
	   var username=$('#username').val();//申请人("username");
	   layer.confirm('确定提交吗？', {
 		  btn: ['确定', '取消'] //可以无限个按钮
 		 ,btn1: function(index, layero){
	      $.ajax({
			type : 'POST',
			url : 'addFieldPersonne.do?'+$("#jvForm").serialize(),
			data :{'userid':userid,'username':username},
			success : function(data) {
				if (data.Success) {
					 layer.msg(data.Msg, {icon: 6, time: 1000,shade:[0.3]}, function () {
						 location.href = "findFieldpList.do";
                    });
				} else {
					 layer.msg(data.Msg, {icon: 5, time: 1000,shade:[0.3]});
				}
				
		    }
         });}
	   })
     }
     
function clearForm(){
	location.href = "findFieldpList.do";
}  




//打开选择人
function openWindow(){
	 layer.open({
		  type: 2,
		  area: ['50%','50%'],
		  maxmin: true,
		  content: 'userListCheck2.do',
		  success:function(layero,index){
		
		  },end:function(data){
			
		  }
		});
}
	
//选择审核人赋值
function setUserName(data){
	  $("#userid").val(data[0].id);
	  $("#getUsername").val(data[0].name);
	  console.log(data)
	 
}

     
$(function(){
	//单选框赋值
	var car=${field_personnel_car==undefined?1:field_personnel_car};
	$("input[name='field_personnel_car'][value="+car+"]").attr("checked",true);
	
});

</script>

</body>
</html>