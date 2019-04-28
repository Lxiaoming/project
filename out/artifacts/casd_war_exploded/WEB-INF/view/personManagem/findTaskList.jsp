<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>
<%@ taglib uri="PowerCheck" prefix="shop"%>
<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>
<head>
  <meta charset="utf-8">
  <title></title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
     <link rel="stylesheet" href="${CASD_PATH}/res/layui/css/layui.css?HJ_v=<%=Math.random()%>"  media="all">
	<script src="${CASD_PATH}/res/layui/layui.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
	<script src="${CASD_PATH}/res/layui/jquery.min.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
	<script src="${CASD_PATH}/res/layui/format/dateformat.js?HJ_v=<%=Math.random()%>" charset="utf-8"></script>
  <!-- 注意：如果你直接复制所有代码到本地，上述css路径需要改成你本地的 -->
  <style type="text/css">
 .layui-table td, .layui-table th{
      font-size: 13px !important;
  }
   table tr:nth-child(2n){
   background-color:#FFF0F5 !important;
   }
 /*  
  .layui-table-view .layui-table td,.layui-table-view .layui-table th {
	padding: 2px 0 !important;
	
} */
   .layui-table-cell .layui-form-checkbox[lay-skin=primary]{
       top: 1px !important;
   }
  th .layui-table-cell {
  text-align:center;
  padding:0 !important;
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
    <label class="layui-form-label" style="margin-left: -40px">流程类型</label>
    <div class="layui-input-inline">
      <select name="pdkey" id="pdkey">
        <option value="">请选类型</option>
        <c:forEach var="def" items="${deflist}">
             <option value="${def.KEY_}">${def.NAME_}</option>
         </c:forEach>
      </select>
    </div>
  <!-- 
    <div class="layui-input-inline">
      <input class="layui-input" id="applicant" name="applicant"  placeholder="请输入申请人"  autocomplete="off">
    </div> -->

    <div class="layui-input-inline">
     <button class="layui-btn" data-type="reload" style="margin-left: 10px;height: 35px">搜索</button>
         <shop:permission code="DELETE_LEAVE">
      <button class="layui-btn" style="margin-left: 10px;height: 35px" onclick="openversion();">删除</button>
      </shop:permission>
    </div>
  </div>
</div>
</div>
 <div style="margin: 10px 10px">
 <table class="layui-hide" id="LAY_table_user" lay-filter="demo"></table> 
  </div>   
  <div class="layui-row" id="fromdiv" style="display: none;">
        <div style="margin: 10px 0"></div>
            <div class="layui-col-md10">
                <form class="layui-form">
                    <div class="layui-form-item">
                        <label class="layui-form-label">版本号：</label>
                        <div class="layui-input-block">
                           <input type="text" id="deploymentId"  value="" class="layui-input">
                        </div>
                    </div>
           
                    <div class="layui-form-item">
                        <div class="layui-input-block">
                            <button type="button" class="layui-btn layui-btn-normal" onclick="delversion()">提交</button>
                        </div>
                    </div>
                </form>
            </div>
        </div>
  <!-- 待办任务 -->
 <script type="text/html" id="barDemo">
  {{#  if(d.ASSIGNEE_==undefined){ }}
     <a class="layui-btn layui-btn-sm" lay-event="taskclaim" style="background:red">签收</a>
  {{#  }  else{ }}
  <a class="layui-btn layui-btn-sm" lay-event="transact">办理</a>
  {{#  } }}
  <a class="layui-btn layui-btn-sm" lay-event="to_view">流程图</a>
</script>   

      
<script>
/* document.body.parentNode.style.overflow ="hidden";//禁止纵向滚动条 */

		
		
layui.use(['table','laypage','layer','jquery'], function(){
	 var table = layui.table
	   ,$ = layui.$ 
	   ,laypage = layui.laypage
	   ,layer = layui.layer;

  //方法级渲染
  table.render({
    elem: '#LAY_table_user'
    ,url: 'findTaskLists.do'
    ,cols: [[
       {field:'psc', title:'',checkbox: true,align:'center',width:30}
      ,{field:'ID_', title: '流水号',width:75}
      ,{field:'pdname', title: '流程类型 ', width:150, align:'center'}
      ,{field:'NAME_', title: '步骤名称', width:150,align:'center'}
      ,{field:'CREATE_TIME_', title: '任务创建时间',templet :function (row){
    	  var  date=new  Date(row.CREATE_TIME_.time); 
	      var time=date.Format("yyyy-MM-dd HH:mm:ss");
    	  return time;
      }, width:155, align:'center'}
      ,{field:'PROC_INST_ID_', title: '流程实例id',width:100,align:'center'}
      ,{field:'applicant', title: '申请人',width:150,align:'center'}
      ,{field:'illustrate', title: '单据描述', minWidth:150}
      ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:180,align:'center',}
      ]]
    ,id: 'testReload'
    ,even: true
  });
  
  
  var $ = layui.$, active = {
		    reload: function(){
		      var pdkey = $('#pdkey').val(); //获取下拉列表参数
		    
		      //执行重载
		      table.reload('testReload', {
		        where: {
		         'pdkey':pdkey
		        }
		      });
		    }
		  };
		  $('.demoTable .layui-btn').on('click', function(){
		    var type = $(this).data('type');
		    active[type] ? active[type].call(this) : '';
		  });
		  
		  //监听工具条 table  lay-filter="demo"
		  table.on('tool(demo)', function(obj){
		    var data = obj.data;
		     //办理任务
		      if(obj.event == 'transact'){
		    	var  url= data.FORM_KEY_
		    	if(url==undefined){
		    		url=data.ID_+".do";
		    	}
		    	
		    	 url=(data.BUSINESS_KEY_).replace(".","")+".do";
		    	
		  				location.href = url + "?" + $.param({
		  					'taskid' : data.ID_,
		  					'processInstanceId' : data.PROC_INST_ID_,
		  					'taskName' : data.NAME_,
		  				});

		  			} else if (obj.event == "taskclaim") {
		  				 var row = obj.data;
		  				//签收任务  
		  				   $.ajax({
		  					     url:'taskclaim.do',
		  					     type:'post',
		  					     data:{'taskid' : row.ID_,},
		  					 	success : function(data){
		  					 		if (data.Success) {
		  					 		//信息框-例2
		  					 		layer.msg(data.Msg, {time:1500,icon: 6,shade:0.3},function(){
		  					 			location.reload(true);
						                    });	  					 		
		  							} else {
		  								layer.msg(data.Msg, {time:0,icon: 5,btn: ['确定'],shade:0.3},function(){
			  					 		
							                    });	  
		  						  							}
		  					 	}
		  				   });

		  			} else if (obj.event == 'to_view') {
		  				
		  				location.href="personManagem_s.do?processInstanceId="+data.PROC_INST_ID_
		  		}
		
		  });
		});
		//打开弹框
		function openversion(){
			 layer.open({
		    	  type: 1,
		          title:"删除版本",
		          area:["20%"],
		          content:$("#fromdiv"),
		    	});
		}
		
		function delversion(){
			var deploymentId=$("#deploymentId").val();
		
			   $.ajax({
				     url:'delversion.do',
				     type:'post',
				     data:{'deploymentId' : deploymentId,},
				 	success : function(data){
				 		location.reload(true);
				 	}
				     
			   });
		
		}
</script>

</body>
</html>