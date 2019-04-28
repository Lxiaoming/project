package com.casd.service;

import org.springframework.stereotype.Component;

@Component("springTest")
public class SpringTest {


	// private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();  
	 
	
	  // private Logger log = LogManager.getLogger(SpringTest.class);//日志文件
	   
		
/*private static ProcessEngine processEngine = ProcessEngines.getDefaultProcessEngine();  
	    private static  IdentityService  identityService = processEngine.getIdentityService();  
	    private static RuntimeService runtimeService = processEngine.getRuntimeService();  
	        private static TaskService taskService = processEngine.getTaskService();  
	        private static RepositoryService repositoryService = processEngine.getRepositoryService();  
	        private static HistoryService historyService = processEngine.getHistoryService();  
	
	
	//使用配置文件 创建23张表
	public void createTable() {
		 ProcessEngineConfiguration processEngineConfiguration= ProcessEngineConfiguration.createProcessEngineConfigurationFromResource("activiti.cfg.xml");
		 ProcessEngine processEngine=processEngineConfiguration.buildProcessEngine();
		 System.out.println("processEngine:"+processEngine);
		 System.out.println("创建23张表");
	}
	
	
	    //部署流程定义
	  public  void  deploymentProcessDefinition_zip() throws FileNotFoundException {
		
			
			
		 Deployment deployment= processEngine.getRepositoryService()  //与相关定义部署相关Service
					  .createDeployment()        //创建部署对象
					  .name("请假流程")  //部署名称
					  .addClasspathResource("leave_flow.bpmn")//从一个classpath的资源中加载，一次只能加载一个文件
					  .addClasspathResource("leale_flow.png")//从一个classpath的资源中加载，一次只能加载一个文件
					  .deploy();//完成部署
		  
		  System.out.println(deployment.getId());
		
		 
		  //InputStream in = this.getClass().getClassLoader().getResourceAsStream("activiti.zip");
		  File file = new File("E:/Demo/casd/WebRoot/res/activiti_biz/purchase_payment.zip");
		  InputStream in = new FileInputStream(file);

	        ZipInputStream zipInputStream = new ZipInputStream(in);
	        Deployment deployment = processEngine.getRepositoryService()// 与流程定义和部署对象相关的Service
	                .createDeployment()// 创建一个部署对象
	                .name("采购申请")// 添加部署的名称
	                .addZipInputStream(zipInputStream)// 指定zip格式的文件完成部署
	                .deploy();// 完成部署
	        System.out.println("部署ID：" + deployment.getId());//
	        System.out.println("部署名称：" + deployment.getName());//
		  
		
	}
	
	  
	  
	  //启动流程
	  public void startProcessInstance() {
		  //流程定义的key
		  String  processDefinitioKey="Leave_flow";
		 ProcessInstance pi= processEngine.getRuntimeService()   //与正再执行的流程实例和执行对象相关的Service
		  .startProcessInstanceByKey(processDefinitioKey);//使用流程定义的Key启动流程实例，key对应 bpmn文件中的属性ID，使用key值启动，默认是按照最新版本的流程定义启动
		 System.out.println(pi.getId()); //流程实例ID
		 System.out.println(pi.getProcessDefinitionId());//流程定义ID
		  
		
	}
	  
	  
	 //查询当前人的个人任务
	  
	  public void findMyProsonalTask() {
		  String asString="admin";
		List<Task>  list= processEngine.getTaskService()//与正在执行的任务管理相关的Service
		               .createTaskQuery()//创建任务查询对象
		              		*//**查询条件 （where部分）*//* 
		               .taskAssignee(asString)//指定个人任务查询，指定办理人
		               // .taskCandidateUser(arg0)//组任务的办理人查询
		               //.processDefinitionId(arg0)//使用流程定义ID查询
		              // .processInstanceId(arg0)//使用流程实例Id
		               //.executionId(arg0)//使用执行对象ID查询
		               //.count();
		               .list();
		  System.out.println("##############");
		  if (list!=null&&list.size()>0){ 	
			  for (Task task:list) {
				  System.out.println("任务ID："+task.getId());
				  System.out.println("任务名称："+task.getName());
				  System.out.println("任务创建时间："+task.getCreateTime());
				  System.out.println("任务的办理人："+task.getAssignee());
				  System.out.println("流程实例ID："+task.getProcessInstanceId());
				  System.out.println("执行对像ID："+task.getExecutionId());
				  System.out.println("流程定义ID："+task.getProcessDefinitionId());
				  System.out.println("##############");
				  
			}
			 
		}
		  
		     
	}
	  
	  //完成我的任务
	  public void   completeMyProsonalTask() {
		  //任务ID
		    String taskId="5004";
		  processEngine.getTaskService()//与正在执行的任务管理相关的Service
		  .complete(taskId);
		  System.out.println("完成任务：任务ID"+taskId);
		  
	  }
	  
	  
	  //查询流程定义
	  public void  findProcessDefinition() {
		List<ProcessDefinition> list= processEngine.getRepositoryService() //与正在执行的任务管理相关的Service
		               .createProcessDefinitionQuery() //创建一个流程定义的查询
		               *//**指定查询条件   where 条件*//*
		              // .deploymentId(de)//使用部署对象ID查询
		              //.processDefinitionId(arg0) //使用流程定义ID查询
		                //.processDefinitionKey(arg0)//使用流程定义的Key查询
		               //.processDefinitionNameLike(arg0) //使用流程定义的名称模糊查询 like
		               *//** 排序*//*
		                .orderByProcessDefinitionVersion().asc()//按版本的升序排序
		               .orderByProcessDefinitionName().desc()//按流程定义的名称降序排序
		                .list();
		               *//** 返回结果集*//*
		                 //.list();//返回一个集合列表， 封装流程定义
		               //.singleResult();//返回唯一结果集合
		                //.count();//返回结果数量
		               //.listPage(arg0, arg1);//分页查询
		
		
		  if (list!=null &&list.size()>0) {
			  for (ProcessDefinition pd:list) {
				  System.out.println("流程定义ID："+pd.getId());//流程定义的Key+版本+随机生成数
				  System.out.println("流程定义的名称："+pd.getName());//对应bpmn文件中的name属性值
				  System.out.println("流程定义的Key："+pd.getKey());//对应bpmn文件中的id属性值
				  System.out.println("流程定义的版本："+pd.getVersion());//当流程定义的key值相同的情况下，版本审计，默认1
				  System.out.println("资源名称bpmn文件："+pd.getResourceName());
				  System.out.println("资源名称png文件："+pd.getDiagramResourceName());
				  System.out.println("部署对象ID："+pd.getDeploymentId());
				  System.out.println("#########################");
				  
				
			}
			
		}
		
	}
	  *//**删除流程定义*//*
	  public void deleteProcessDefinition() {
		  //使用部署ID，完成删除
		  String deploymentId="1";
		  
		 *//** 不带级联的删除
		   只能删除没有启动的流程，如果流程启动，就会抛出异常*//*
		  processEngine.getRepositoryService()
		               .deleteDeployment(deploymentId);
		  
		  *//**带级联的删除
		   *   不管流程是否启动，都可以删除
		   * *//*
		  processEngine.getRepositoryService()
		               .deleteDeployment(deploymentId, true);
		System.out.println("删除成功");
	} 
	  
	  *//** 查看流程图
	 * @throws IOException *//*
	  public void viewPic() throws IOException {
		  *//**将生成的图片放到文件夹下*//*
		  String  deploymentId="2501";
		  
		  //流程图片的资源名称
		List<String> list= processEngine.getRepositoryService() //与正在执行的任务管理相关的Service
		               .getDeploymentResourceNames(deploymentId); 
		String resourceName="";
            if(list!=null && list.size()>0){
            	for (String name:list) {
					if (name.indexOf(".png")>=0) {
						resourceName=name;
					}
				}
            }
            
            //获取图片的输入流
            InputStream in=processEngine.getRepositoryService()//与正在执行的任务管理相关的Service
            		       .getResourceAsStream(deploymentId, resourceName);
            
            
            //将图片生成到D盘的目录下
            File file=new File("D:/resourceName/"+resourceName);
            //将输入流的图片写到D盘下
            FileUtils.copyInputStreamToFile(in, file);
            
            
	}
	  
	  
	  *//** 附加功能，查询最新版本的流程定义*//*
	  public void findLastVersionProcessDefinition() {
		  
		        List<ProcessDefinition> list=   processEngine.getRepositoryService()//与正在执行的任务管理相关的Service
		           .createProcessDefinitionQuery()
		           .orderByProcessDefinitionVersion().asc()//使用流程定义的版本升序排序
		           .list();
		        
		        *//**
		         * Map<String, ProcessDefinition>
		         * map集合的key:流程定义的key
		         * map集合的value:流程定义的对象
		         * map集合的特点：当map集合的key相同的情况下，后一次的值将替换前一次的值
		         * *//*
		        Map<String, ProcessDefinition> map=new LinkedHashMap<String, ProcessDefinition>();
		           if (list!=null&&list.size()>0) {
					   for (ProcessDefinition pd:list) {
						   map.put(pd.getKey(), pd);
						
					}
		        	   
				}
		           
		           List<ProcessDefinition> pdList=new ArrayList<ProcessDefinition>(map.values());
		       	
		 		  if (pdList!=null &&pdList.size()>0) {
		 			  for (ProcessDefinition pd:pdList) {
		 				  System.out.println("流程定义ID："+pd.getId());//流程定义的Key+版本+随机生成数
		 				  System.out.println("流程定义的名称："+pd.getName());//对应bpmn文件中的name属性值
		 				  System.out.println("流程定义的Key："+pd.getKey());//对应bpmn文件中的id属性值
		 				  System.out.println("流程定义的版本："+pd.getVersion());//当流程定义的key值相同的情况下，版本审计，默认1
		 				  System.out.println("资源名称bpmn文件："+pd.getResourceName());
		 				  System.out.println("资源名称png文件："+pd.getDiagramResourceName());
		 				  System.out.println("部署对象ID："+pd.getDeploymentId());
		 				  System.out.println("#########################");
		 				  
		 				
		 			}
		 			
		 		}
		           
		
	}
	   *//**
	    * 附加功能，删除流程定义（删除key相同的所用不同版本的流程定义）
	    * *//*
	  
	  public void deleteProcessDefinitionByKey() {
		  //流程定义的Key
		  String processDefinitionKey="myProcess";
		  //先使用流程定义的key查询流程定义，查询出所有的版本
		    List<ProcessDefinition> list=  processEngine.getRepositoryService()//与正在执行的任务管理相关的Service
		                   .createProcessDefinitionQuery()
		                   .processDefinitionKey(processDefinitionKey)//使用流程定义的Key查询
		                   .list();
		  
		    //遍历，获取每个流程定义的部署ID
		    if (list!=null&&list.size()>0) {
		    	for (ProcessDefinition pd:list) {
		    		//获取部署ID
			    	String deploymentId=pd.getDeploymentId();
			    	processEngine.getRepositoryService()
			    	             .deleteDeployment(deploymentId, true);
					
				}
				
			}
		
	}
	  
	  
	  *//**查询流程状态 　（判断流程正在执行，还是结束）*//*
	  public void isProcessEnd(){
		  String tsakId="12504";
		ProcessInstance pi=  processEngine.getRuntimeService()//表示正在执行流程实例和执行
		               .createProcessInstanceQuery()//创建流程实例
		               .processInstanceId(tsakId)//使用流程实例ID查询
		               .singleResult();
		if (pi==null) {
			System.out.println("流程已经结束");
		}else {
			System.out.println("流程没有结束");
		}
		
	}
	  
	  *//***
	   * 查询历史任务
	   * 
	   * *//*
	  public void findHistoryTask() {
		  String taskAssignee="admin";
		  
		List<HistoricTaskInstance> list= processEngine.getHistoryService()//与历史数据表相关的的Service
		               .createHistoricTaskInstanceQuery()//创建历史任务实例查询
		               .taskAssignee(taskAssignee)
		               .list();
		      
		  if (list!=null &&list.size()>0) {
			     
			  for (HistoricTaskInstance hti:list) {
				
			}
		}
		  
		
	}
	  
	  
	  *//**查询历史流程实例*//*
	  public void findHistoryProcessInstance() {
		  String procesInstanceId="12501";
		  processEngine.getHistoryService()//与历史数据表相关的的Service
		               .createHistoricProcessInstanceQuery()//创建历史流程实例查询
		               .processInstanceId("")//使用流程实例ID查询
		               .singleResult();
		  
	}
	  
	     *//**设置流程变量*//*
	  public void setVariables() {
		
	        }   
	  *//**获取流程变量*//*
	  public void getVariables() {
			
		}   
	  
	  
	  *//**模拟设置和获取流程变量场景*//*
	  public void setAndGetVariables() {
		  *//** 与流程实例，执行对象*//*
		  RuntimeService runtimeService =  processEngine.getRuntimeService();
		  
		  *//**与任务(正在执行)*//*
		  TaskService taskService=processEngine.getTaskService();
		  
		  *//** 设置流程变量*//*
		 // runtimeService.setVariable(arg0, arg1, arg2);//表示使用执行对象ID，和流程变量的名称，设置流程变量的值（一次只能设置一个值）
		//runtimeService.setVariables(arg0, arg1);//表示使用执行对象ID，和map集合设置流程变量，map集合的key就是流程的变量名称，map的value就是流程的变量的值（一次可以设置多个）
		 //  taskService.setVariable(arg0, arg1, arg2);//表示使用执行对象ID，和流程变量的名称，设置流程变量的值（一次只能设置一个值）
		 // taskService.setVariables(arg0, arg1);//表示使用执行对象ID，和map集合设置流程变量，map集合的key就是流程的变量名称，map的value就是流程的变量的值（一次可以设置多个）
	   // runtimeService.startProcessInstanceByKey(arg0, arg1);//启动流程实例的同时，可以设置流程变量，用map集合
	  
	  
	  }
	  
	  //获取当前任务完成之后的连线名称
	  public List<String> lianxList() {
		  List<String> list=new ArrayList<String>();
		  //使用任务id查询任务对象
		  String taskId="7530";
		  Task task=taskService.createTaskQuery().taskId(taskId)
				  .singleResult();
		  //获取流程定义id
		  String processDefinitionId=task.getProcessDefinitionId();
		  //查询ProcessDefinitionEntity对象
		  ProcessDefinitionEntity processDefinitionEntity=(ProcessDefinitionEntity) repositoryService.getProcessDefinition(processDefinitionId);
	     //使用任务对象Task获取流程实例id
		  String processInstanceId  =task.getProcessInstanceId();
		  //使用实例id，查询正在执行的执行对象表，返回流程实例对象
		  ProcessInstance pi=runtimeService.createProcessInstanceQuery()
				  .processInstanceId(processInstanceId).singleResult();
		  //获取当前活动id
		  String actitityId=pi.getActivityId();
		   //获取当前的活动
		 ActivityImpl activityImpl= processDefinitionEntity.findActivity(actitityId);
		//获取当前活动完成后的连线名称
		 List<PvmTransition> pvmList=activityImpl.getOutgoingTransitions();
		 if (pvmList!=null&&pvmList.size()>0) {
			 for (PvmTransition pvm:pvmList) {
				 String name=(String) pvm.getProperty("name");
				 if (org.apache.commons.lang3.StringUtils.isNotBlank(name)) {
					list.add(name);
				}else {
					list.add("默认提交");
				}
				
			}
			
		}
		  return list;
		
	}
	  
	  
	  
	  public String getBusinessObjId(String taskId) {
		  taskId="7534";
				  
	        //1  获取任务对象
	        Task task  =  taskService.createTaskQuery().taskId(taskId).singleResult();
	        
	        //2  通过任务对象获取流程实例
	        ProcessInstance pi = runtimeService.createProcessInstanceQuery().processInstanceId(task.getProcessInstanceId()).singleResult();
	        //3 通过流程实例获取“业务键”
	        String businessKey = pi.getBusinessKey();
	        //4 拆分业务键，拆分成“业务对象名称”和“业务对象ID”的数组 	
	        // a=b  LeaveBill.1
	        String objId = null;
	        if(org.apache.commons.lang3.StringUtils.isNotBlank(businessKey)){
	            objId = businessKey;
	            System.out.println(objId);
	        }
	        return objId;
	    }
	  
	  
	  public void startProcesses(String bizId,String name,String processDefinitioKey)throws Exception{   
	    	Map<String, Object> vars=new HashMap<String, Object>();
	 	    vars.put("username", "admin");//设置第一个人审批人为申请人 
	 	    
	    ProcessInstance pi = runtimeService.startProcessInstanceByKey("Purchase_payment","1118",vars);//流程图id，业务表id  
	      
	    }
	  
	public static void main(String[] args) throws IOException {
		
			
		SpringTest test=new SpringTest();
		//test.lianxList();
		//test.getBusinessObjId("7501");
	   test.createTable();
	   test.deploymentProcessDefinition_zip();   //定义部署
	
		//test.startProcessInstance();//启动流程实例
		
		
	//test.findMyProsonalTask();
		//test.completeMyProsonalTask();
		//test.findProcessDefinition();  //查询流程定义
		//test.deleteProcessDefinition();//删除流程定义

	    // test.viewPic();// 查看流程图
			//test.deleteProcessDefinitionByKey();
	//	test.isProcessEnd();//查询流程是否结束
	}
	*/
}
