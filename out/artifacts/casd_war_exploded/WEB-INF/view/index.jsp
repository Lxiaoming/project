<%@ page language="java" contentType="text/html; charset=UTF-8"
	pageEncoding="UTF-8"%>

<%@ taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c"%>
<!DOCTYPE html PUBLIC "-//W3C//DTD HTML 4.01 Transitional//EN" "http://www.w3.org/TR/html4/loose.dtd">
<html>

<title>首页</title>

<link
	href="<%=request.getContextPath()%>/res/jquery-easyui/themes/icon.css"
	rel="stylesheet" />
<link
	href="<%=request.getContextPath()%>/res/jquery-easyui/themes/default/easyui.css"
	rel="stylesheet" />
<script
	src="<%=request.getContextPath()%>/res/jquery-easyui/jquery.min.js"></script>
<script
	src="<%=request.getContextPath()%>/res/jquery-easyui/jquery.easyui.min.js"></script>
<script
	src="<%=request.getContextPath()%>/res/jquery-easyui/locale/easyui-lang-zh_CN.js"></script>
<style>
#RightAccordion div {
	font-size: 15px;
	
}

 .accordion-body {
   height: 100% ! important;
}
 /*
.panel-body {
   height: 30px ! important;
}  */


#RightAccordion div ul {
	
	
}



/* .tree-folder-open{
	background: none;
}

.tree-folder {
  background: none;
} */

 .tree-collapsed {
  background: none;
}
.tree-collapsed-hover {
  background: none;
} 

.tree-expanded{
	background: none;
}

/* .tree-file {
background: none;

} */

.tree-node {
	height: 25px;

}

.tree-title {
	font-size: 15px;
	line-height: 28px;
}


#div_leftmenu div ul li {
	list-style-type: none;
	background-color: #DFE2E3;
	text-align: center;
	margin-bottom: 2px;
}

#div_leftmenu div ul li:last-of-type {

	margin-bottom: 0;

}
.accordion-header{
	height:32px !important;
}
.panel-title{
line-height:28px !important;
}



#menu {
	font-size: 12px;
	font-weight: bolder;
}

#menu li {
	list-style-image: none;
	list-style-type: none;
	background-color: #999999;
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #000000;
	float: left;
}

#menu li a {
	color: #FFFFFF;
	text-decoration: none;
	margin: 0px;
	padding-top: 8px;
	display: block; /* 作为一个块 */
	padding-right: 50px; /* 设置块的属性 */
	padding-bottom: 8px;
	padding-left: 50px;
}

#menu li a:hover {
	background-color: #0099CC;
}

#newMenu {
	font-size: 12px;
	font-weight: bolder;
}

#newMenu li {
	list-style-image: none;
	list-style-type: none;
	background-color: #87CEFA;
	border-right-width: 1px;
	border-right-style: solid;
	border-right-color: #000000;
	float: left;
}

#newMenu li a {
	color: #FFFFFF;
	text-decoration: none;
	margin: 0px;
	padding-top: 8px;
	display: block; /* 作为一个块 */
	padding-right: 50px; /* 设置块的属性 */
	padding-bottom: 30px;
	padding-left: 50px;
	font-size: 18px;
}

#newMenu li a:hover {
	background-color: #0099CC;
}


#drawer,#finance,#supervision {
	position: absolute;
	width: 200px;
	height: 100%;
	color: #fff;
	transition: all linear 0.5s;
	z-index: 1;
}

#drawer.hide,#finance.hide,#supervision.hide {
	left: -250px;
	background: rgba(0, 0, 0, 0.2);
}

#drawer.show,#finance.show,#supervision.show {
	left: 250px;
	background: rgba(232,232,232,0.9);
}



#secondMenu,#financeMenu,#supervisionMenu {
	margin:0px; list-style:none; padding:0px;
}

#secondMenu li,#financeMenu li,#supervisionMenu li {
	float:left; height:40px; line-height:40px; width:200px; text-align:center; background:#F3F3F3; margin-right:2px;
}

#secondMenu li a,#financeMenu li a,#supervisionMenu li a {
    
   display:block; text-decoration:none ; font-size:12px ;  line-height:40px; width:200px;color: #000000;font-weight: bold
}



</style>
</head>
<body class="easyui-layout" style="font-size: 15px;">
	<form id="form1" runat="server">
		<!--左侧-->
		<div data-options="region:'west',title:''"
			style="width: 250px; background-color: 	#87CEFA; font-size: inherit;">

			<div id="div_welcome"
				style="margin: 15px 0 5px 0; text-align: center;">欢迎${loginUser.username==null?"游客":loginUser.username}
			</div>

			<div style="text-align:right;">
				<a style="font-size:30px;" class="easyui-linkbutton"
					onclick="update()" plain="true">修改密码</a> <a style="font-size:30px;"
					href="<%=request.getContextPath()%>/login.do"
					class="easyui-linkbutton" plain="true">注销</a>
			</div>
			
			
			<div id="RightAccordion" class="easyui-accordion" >
			</div>
		</div>
		
		<!--右侧-->
		<div data-options="region:'center'" style="padding: 1px;">
			<div id="tt" data-options="region:'center'" class="easyui-tabs "
				style="height: 100%; width: 100%;position:absolute;z-index:0">

				<div title="欢迎使用" closable="true" style="padding:10px;  position:relative;">
					<div
						style="text-align: center;background: #eee ;width: 100%;height:100%; display: table;">

						<div style="font-weight:bold;font-size:16px;">
							<ul style="list-style-type:none;">
								<li style="text-align:left;">待办任务&nbsp;<a style="color: red"
									onclick="findTaskList();">${count}</a></li>
							</ul>
						</div>

						<div style="position:absolute;top:50%;left:50%;transform:translate(-50%,-50%);">
							<img style="display: inline-block;margin:auto;vertical-align:middle" alt=" " 
							src="../res/images/backgrounds/logo1.png" width=500px;
							height=500px;>
						</div>
					</div>
				</div>
			</div>
		</div>
	</form>




	<script type="text/javascript">
		
		function update() {

			addTab("修改密码", "editUser.do?cid=" + ${loginUser.userid});

		}

		function findTaskList() {
			addTab("待办任务", "findTaskList.do");

		}
		
		function addTab(title, url) {
			if ($('#tt').tabs('exists', title)) {
				$('#tt').tabs('select', title);
			} else {
				var content = '<iframe scrolling="auto" frameborder="0"  src="'
						+ url
						+ '" style="width:100%;height:100%;"></iframe>';
				$('#tt').tabs('add', {
					title : title,
					content : content,
					closable : true
				});
			}
			
		}

		
		$(function() {
            jQuery("#RightAccordion").accordion({ 
                fillSpace:true,
                fit:true,
                border:false,
                animate:false  
            });
            $.post("menuTree.do", { "id": "0" }, //获取第一层目录
               function (data) {
                   $.each(data, function (i, e) {//循环创建手风琴的项
                       var id = e.id;
                       $('#RightAccordion').accordion('add', {
                           title: e.text,
                           content: "<ul id='tree"+id+"' ></ul>",
                           selected: true,
                           //iconCls:e.iconCls//e.Icon
                       });
                       $.parser.parse();
                       $.post("menuTree.do?id="+id,  function(data) {//循环创建树的项
                           $("#tree" + id).tree({
                               data: data,
                               /* onBeforeExpand:function(node){  
                                   $("#tree" + id).tree('options').url = "menuTree.do?id=" + node.id;
                               },    */
                               onClick : function(node){  
                                   if (node.state == 'closed'){  
                                       $(this).tree('expand', node.target);  
                                   }else if (node.state == 'open'){  
                                       $(this).tree('collapse', node.target);  
                                   }
                                   if(node.attributes!=undefined){
                                       var tabTitle = node.text;
                                       var url = node.attributes.url;
                                       addTab(tabTitle,url);
                                   }
                                 }
                           });
                       }, 'json');
                   });
               }, "json");
            
            
        });
		
		
		
	</script>

</body>
</html>