<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}" />

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
  <title>layui在线调试</title>
  <link rel="stylesheet" href="${ctxPath}/static/layui/css/layui.css?t=1554901098009" media="all">
  <style>
    body{margin: 10px;}
    .demo-carousel{height: 200px; line-height: 200px; text-align: center;}
  </style>
  
<style>
	#tbl{ margin-top:15px; margin-left:25px; }
	#tbl th{ height:45px; }
	#tbl td{
		font-weight:normal; height:45px;
		font-family:微软雅黑; font-size:17px;
	}
	#tbl [type='text']{
		height:28px; font-size:17px;
		text-indent:0.3em;
	}
	#tbl select { width:200px; height:32px; font-size:17px; }
	/*
	td:nth-child(1){ width:80px; }
	td:nth-child(2){ width:350px; }
	*/
</style>

<c:set var="msg" value="正在加载数据..." />

<c:if test="${param['op']=='1'}">
	<c:set var="msg" value="更新用户成功..." />
</c:if>

<c:if test="${param['op']=='2'}">
	<c:set var="msg" value="更新用户失败..." />
</c:if>



<script src="${ctxPath}/static/js/jquery-1.11.1.min.js"></script>
<script>
function makeInput( title, id, readonly, val ){
	readonly = (readonly) ? readonly : "";
	console.log( "{debug} val = "+ val );
	val = (val) ? val : "";
	return "<tr>"+
	"<td>"+ title +"</td>"+
	"<td><input type=\"text\" id=\""+ id +"\" name=\""+ id +
	"\" "+ readonly +" value=\""+ val +"\"/></td>"+
	"</tr>";
}
function makeSelect( title, id, data, val ){
	var html = "<tr><td>"+ title +"</td>";
	html += "<td><select id=\""+ id +"\" name=\""+ id +"\">";
	for( var i=0; i<data.length; i++ ){
		var d = data[i];
		var selected = ( val==d['val'] ) ? "selected" : "";
		html += "<option value=\""+ d['val'] +"\" "+ selected +">"+
			d['text'] +"</option>";
	}
	html += "</select></td></tr>";
	return html;
}

//"account",json['id']
function makeHide( id, val ){
	return "<input type=\"hidden\" id=\""+ id 
		+"\" value=\""+ val +"\"/>";
}
	layui.use('layer', function(){
		var $ = layui.jquery;
		var layer = layui.layer;
	});
	
	function delDeployment( _id ){
		alert( "id = "+ _id );
		$.ajax(
			{
				url:"${ctxPath}/WorkFlow/delDeploymenet",
				type:"post",
				data:{id:_id},
				dataType:"json",
				success:function( data ){
					if( data['ret']=='success' ){
						layer.msg( '删除流程成功。' );
						setTimeout( function(){
							window.location = '/WorkFlow/list'
						}, 2000 );
					}else{
						layer.msg( '删除流程失败。' );
					}	
				}		
			}	
		);
	}
</script>
</head>
<body>
 
<table class="layui-hide" id="demo" lay-filter="test"></table>
 
<script type="text/html" id="barDemo">
  <a class="layui-btn layui-btn-primary layui-btn-xs" lay-event="detail">
	查看详情
  </a> 
</script>
   
<script src="${ctxPath}/static/layui/layui.js?t=1554901098009"></script>
<script>
layui.config({
  version: '1554901098009' //为了更新 js 缓存，可忽略
});
 
layui.use(
	['laydate', 'laypage', 'layer', 'table', 'carousel', 'upload', 'element', 'slider'], 
	function(){
	  var laydate = layui.laydate //日期
	  ,laypage = layui.laypage    //分页
	  ,layer = layui.layer        //弹层
	  ,table = layui.table        //表格
	  ,carousel = layui.carousel //轮播
	  ,upload = layui.upload //上传
	  ,element = layui.element //元素操作
	  ,slider = layui.slider //滑块
  
  //向世界问个好
  layer.msg('${ msg }');
  
  //监听Tab切换
  element.on('tab(demo)', function(data){
    layer.tips('切换了 '+ data.index +'：'+ this.innerHTML, this, {
      tips: 1
    });
  });
  
  //执行一个 table 实例
  table.render({
    elem: '#demo'
    ,height: 580
    ,url: '${ctxPath}/Leave/jsonWaitMyApprove?assigneeId=${user.id}' //数据接口
    ,title: '待我审批请假表'
    ,page: true //开启分页
    ,toolbar: 'default' //开启工具栏，此处显示默认图标，可以自定义模板，详见文档
    ,totalRow: false //开启合计行
		
    ,cols: [[     //表头                  
       {type: 'checkbox', fixed: 'left'}	   
	  ,{field:'procInstId', title:'流程实例ID', width:125, fixed:'left'}
      ,{field:'taskId', title:'任务ID', width:125, fixed:'left'}
      ,{field:'initiatorName', title:'发起人', width:115, fixed:'left'}
      ,{field:'startDate', title:'开始日期', width:125, fixed:'left'}
      ,{field:'endDate', title:'结束日期', width:125, fixed:'left'}
      ,{field:'leaveType', title:'请假类型', width:115, fixed:'left'}
      ,{field:'createTime', title:'创建日期', width:125, fixed:'left'}
      ,{fixed: 'right', width: 145, align:'center', toolbar: '#barDemo'}
    ]]
  });
  
  //监听头工具栏事件
  table.on('toolbar(test)', function(obj){
    var checkStatus = table.checkStatus(obj.config.id)
    ,data = checkStatus.data; //获取选中的数据
    switch(obj.event){
      case 'add':
        layer.msg('添加');
      break;
      case 'update':
        if(data.length === 0){
          layer.msg('请选择一行');
        } else if(data.length > 1){
          layer.msg('只能同时编辑一个');
        } else {
          layer.alert('编辑 [id]：'+ checkStatus.data[0].id);
        }
      break;
      case 'delete':
        if(data.length === 0){
          layer.msg('请选择一行');
        } else {
          layer.msg('删除');
        }
      break;
    };
  });
  
  //监听行工具事件
  table.on('tool(test)', function(obj){ //注：tool 是工具条事件名，test 是 table 原始容器的属性 lay-filter="对应的值"
    var data = obj.data     //获得当前行数据
    ,layEvent = obj.event; //获得 lay-event 对应的值
    if(layEvent === 'detail'){
      layer.msg('查看操作 taskId:'+ data['taskId']);
      window.location = '${ctxPath}/Leave/taskDetail?taskId='+ data['taskId'];
    } else if(layEvent === 'del'){

    } else if(layEvent === 'edit'){
      
    }
  });
  
  //执行一个轮播实例
  carousel.render({
    elem: '#test1'
    ,width: '100%' //设置容器宽度
    ,height: 200
    ,arrow: 'none' //不显示箭头
    ,anim: 'fade' //切换动画方式
  });
  
  //将日期直接嵌套在指定容器中
  var dateIns = laydate.render({
    elem: '#laydateDemo'
    ,position: 'static'
    ,calendar: true //是否开启公历重要节日
    ,mark: { //标记重要日子
      '0-10-14': '生日'
      ,'2018-08-28': '新版'
      ,'2018-10-08': '神秘'
    } 
    ,done: function(value, date, endDate){
      if(date.year == 2017 && date.month == 11 && date.date == 30){
        dateIns.hint('一不小心就月底了呢');
      }
    }
    ,change: function(value, date, endDate){
      layer.msg(value)
    }
  });
  
  //分页
  laypage.render({
    elem: 'pageDemo' //分页容器的id
    ,count: 8        //总页数
    ,skin: '#1E9FFF' //自定义选中色值
    //,skip: true    //开启跳页
    ,jump: function(obj, first){
      if(!first){
        layer.msg('第'+ obj.curr +'页', {offset: 'b'});
        alert( "TTTTTTTTT" );
      }
    }
  });
  
  //上传
  upload.render({
    elem: '#uploadDemo'
    ,url: '' //上传接口
    ,done: function(res){
      console.log(res)
    }
  });
  
  slider.render({
    elem: '#sliderDemo'
    ,input: true //输入框
  });
  
  //底部信息
  //var footerTpl = lay('#footer')[0].innerHTML;
  //lay('#footer').html(layui.laytpl(footerTpl).render({}))
  //.removeClass('layui-hide');
});
</script>
</body>
</html>        

<!-- 
   //layer.closeAll();				
   //{ps} 配置一个透明的询问框
   //layer.msg( '系统提示: '+ msg, 
   //   {
   //	time: 20000
   //	,btnAlign: 'c'   //按钮居中
   //	,btn: '确认信息'
   //  }
   //); 
-->
        