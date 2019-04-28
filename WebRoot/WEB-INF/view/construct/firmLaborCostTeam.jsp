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
	        <input type="text" class="layui-input" name="firmYear" id="firmYear" placeholder="年份">
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
   
</script>
<script type="text/html" id="barDemo">
 <a class="layui-btn layui-btn-xs" lay-event="detail">打卡列表</a>
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
         ,url:'firmLaborCostTeams.do?projectId=' + ${projectId}
         ,toolbar: '#toolbarDemo'
         ,totalRow: true
    	 ,cols: [[ //标题栏
    	            {field: 'construct_project_name', title: '项目', width: 150}
    	            ,{field: 'construct_project_workTeam_category', title: '施工项目', width: 80,templet:'<div>{{category(d.construct_project_workTeam_category)}}</div>'}
    	            ,{field: 'username', title: '班组', width: 80}
    	            ,{field: 'construct_project_workTeam_price', title: '单价', width: 70,}    	         
    	            ,{field: 'jan', title: '1月', width: 80,totalRow: true}
    	            ,{field: 'feb', title: '2月', width: 80,totalRow: true}
    	            ,{field: 'mar', title: '3月', width: 80,totalRow: true}
    	            ,{field: 'apr', title: '4月', width: 80,totalRow: true}
    	            ,{field: 'may', title: '5月', width: 80,totalRow: true}
    	            ,{field: 'jun', title: '6月', width: 80,totalRow: true}
    	            ,{field: 'jul', title: '7月', width: 80,totalRow: true}
    	            ,{field: 'aug', title: '8月', width: 80,totalRow: true}
    	            ,{field: 'sep', title: '9月', width: 80,totalRow: true}
    	            ,{field: 'oct', title: '10月', width: 80,totalRow: true}
    	            ,{field: 'nov', title: '11月', width: 80,totalRow: true}
    	            ,{field: 'december', title: '12月', width: 80,totalRow: true}
    	            ,{field: 'totalLaborCost', title: '累计付款', width: 100,totalRow: true}
    	            ,{fixed: 'right', title:'操作', toolbar: '#barDemo', width:100}
    	         
                ]]
    ,id:'testReload'
    ,page: true
    ,limit:15
    ,height: 650
    ,done:function(res,curr,count){
	        layuiRowspan(['construct_project_name'],1);//支持数组
	        layuiRowspan("8",1,true);
	  }
  });
    laydate.render({
        elem: '#firmYear', //指定元素
        type:'year'
      });
    
       var $ = layui.$, active = {
    		 reload: function(){ //验证是否全选
    		    	var firmYear=$("#firmYear").val();
    		    	var constuct_project_dep_name=$("#constuct_project_dep_name").val();
    		    	   table.reload('testReload', {
     			    	 page: {
     			    		   curr: 1 //重新从第 1 页开始
     			    	 },where: {
     			    		'firmYear':firmYear,
     			    		'constuct_project_dep_name':constuct_project_dep_name
     			    	 } //设定异步数据接口的额外参数
     			    	 
     			      });
    		      }
    		  };
       
       	//绑定搜索点击事件
	    $('.demoTable .layui-btn').on('click', function(){
	      var type = $(this).data('type');
	      active[type] ? active[type].call(this) : '';
	    });
	    //监听工具条
	    table.on('tool(demo)', function(obj){
	      var data = obj.data;
	      switch (obj.event) {
	         //查看
		  case 'detail':
			location.href = "firmLaborCostPerson.do?" + $.param({
				'hr_attend_date' : data.hr_attend_date,
				'hr_attend_workTeamId' : data.hr_attend_workTeamId,
			   });
			break;
		  default:
			break;
		  }
	    });
		    
	});
		
	//格式化班组
	function category(value) {
		switch (value) {
		case 1:
			return "预埋"; 
			break;
		case 2:
			return "消防水"; 
			break;
		case 3:
			return "消防电"; 
			break;
		case 4:
			return "防排烟"; 
			break;
		case 5:
			return "消防水电"; 
			break;
		default:
			break;
		}
	
	}
		
	var execRowspan = function(fieldName,index,flag){
	  // 1为不冻结的情况，左侧列为冻结的情况
	  let fixedNode = index=="1"?$(".layui-table-body")[index - 1]:(index=="3"?$(".layui-table-fixed-r"):$(".layui-table-fixed-l"));
	  // 左侧导航栏不冻结的情况
	  let child = $(fixedNode).find("td");
	  let childFilterArr = [];
	  // 获取data-field属性为fieldName的td
	  for(var i = 0; i < child.length; i++){
	    if(child[i].getAttribute("data-field") == fieldName){
	      childFilterArr.push(child[i]);
	    }
	  }
	  // 获取td的个数和种类
	  let childFilterTextObj = {};
	  for(var i = 0; i < childFilterArr.length; i++){
	    let childText = flag?childFilterArr[i].innerHTML:childFilterArr[i].textContent;
	    if(childFilterTextObj[childText] == undefined){
	      childFilterTextObj[childText] = 1;
	    }else{
	      let num = childFilterTextObj[childText];
	      childFilterTextObj[childText] = num*1 + 1;
	    }
	  }
	  let canRowspan = true;
	  let maxNum;//以前列单元格为基础获取的最大合并数
	  let finalNextIndex;//获取其下第一个不合并单元格的index
	  let finalNextKey;//获取其下第一个不合并单元格的值
	  for(var i = 0; i < childFilterArr.length; i++){
	    (maxNum>9000||!maxNum)&&(maxNum = $(childFilterArr[i]).prev().attr("rowspan")&&fieldName!="8"?$(childFilterArr[i]).prev().attr("rowspan"):9999);
	    let key = flag?childFilterArr[i].innerHTML:childFilterArr[i].textContent;//获取下一个单元格的值
	    let nextIndex = i+1;
	    let tdNum = childFilterTextObj[key];
	    let curNum = maxNum<tdNum?maxNum:tdNum;
	    if(canRowspan){
	      for(var j =1;j<=curNum&&(i+j<childFilterArr.length);){//循环获取最终合并数及finalNext的index和key
	        finalNextKey = flag?childFilterArr[i+j].innerHTML:childFilterArr[i+j].textContent;
	        finalNextIndex = i+j;
	        if((key!=finalNextKey&&curNum>1)||maxNum == j){
	          canRowspan = true;
	          curNum = j;
	          break;
	        }
	        j++;
	        if((i+j)==childFilterArr.length){
	          finalNextKey=undefined;
	          finalNextIndex=i+j;
	          break;
	        }
	      }
	      childFilterArr[i].setAttribute("rowspan",curNum);
	      if($(childFilterArr[i]).find("div.rowspan").length>0){//设置td内的div.rowspan高度适应合并后的高度
	        $(childFilterArr[i]).find("div.rowspan").parent("div.layui-table-cell").addClass("rowspanParent");
	        $(childFilterArr[i]).find("div.layui-table-cell")[0].style.height= curNum*38-10 +"px";
	      }
	      canRowspan = false;
	    }else{
	      childFilterArr[i].style.display = "none";
	    }
	    if(--childFilterTextObj[key]==0|--maxNum==0|--curNum==0|(finalNextKey!=undefined&&nextIndex==finalNextIndex)){//||(finalNextKey!=undefined&&key!=finalNextKey)
	      canRowspan = true;
	    }
	  }
	}
	//合并数据表格行
	var layuiRowspan = function(fieldNameTmp,index,flag){
	  let fieldName = [];
	  if(typeof fieldNameTmp == "string"){
	    fieldName.push(fieldNameTmp);
	  }else{
	    fieldName = fieldName.concat(fieldNameTmp);
	  }
	  for(var i = 0;i<fieldName.length;i++){
	    execRowspan(fieldName[i],index,flag);
	  }
	}
	
</script>

</body>
</html>