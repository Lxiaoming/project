//驳回
		function rejects(){		
			var taskid = $("#taskid").val();
			var options = $("#options").val();
			if (options == '') {
				$.messager.alert("提示","意见不能为空!");
				return false;
			}
			$.ajax({
				type : "POST",
				url : "rejects.do",
				data : {
					'taskid' : taskid,
					'options' : options,
				},
				success : function(data) {
					
				
					if (data.Success) {
                	$.messager.alert("提示", data.Msg, "info", function() {
								   location.href="findTaskList.do";
								});		

					} else {
						$.messager.alert("提示", data.Msg,
						"error");	
					}
				
				}
			});
		
		}