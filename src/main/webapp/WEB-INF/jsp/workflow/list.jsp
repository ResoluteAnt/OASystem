<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>
<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<title>流程列表</title>
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
		#tbl td:nth-child(1){ width:80px; }
		#tbl td:nth-child(2){ width:350px; }
		input[readonly]{
			background:#DDD;
			color:#333; border:1px solid #666;
		}
		div.layui-layer-title{
			background:#666; color:#EEE;
			font-size:15px;
		}

		label.layui-form-label{
			width:60px;
			padding-left:5px;
			padding-right:10px;
		}
		.layui-form-item .layui-inline {
			margin-right:0px;
		}
	</style>
</head>
<body>


<!-- 搜索模块 -->
<form class="layui-form">
	<div class="layui-form-item" style="margin-bottom:5px;">
		<div class="layui-inline">
			<label class="layui-form-label">类型</label>
			<div class="layui-input-inline">
				<input type="text" name="frmProcCategory" id="frmProcCategory"
					   autocomplete="off" class="layui-input">
			</div>
		</div>

		<div class="layui-inline">
			<label class="layui-form-label">说明</label>
			<div class="layui-input-inline">
				<input type="text" name="frmNote" id="frmNote"
					   autocomplete="off" class="layui-input">
			</div>
		</div>
	</div>
	<div class="layui-form-item">
		<button type="button"
				class="layui-btn" onclick="testSearch();"
				style="margin-left:10px;">立即搜索</button>
		<button type="button"
				class="layui-btn layui-btn-normal" onclick="reDate();"
				style="margin-left:10px;">重置</button>
	</div>
</form>

<script src="${ctxPath}/static/js/jquery-1.11.1.min.js"></script>
<script src="${ctxPath}/static/js/form.js"></script>
<table class="layui-hide" id="demo" lay-filter="com.gec.test">
</table>

<script type="text/html" id="barDemo">
	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script src="${ctxPath}/static/layui/layui.all.js"></script>
<script src="${ctxPath}/static/layui/layui.js?t=1554901098009" charset="utf-8">
</script>
<!-- 分页这块内容 -->
<script>
    layui.config({
        version: '1554901098009' //为了更新 js 缓存，可忽略
    });

    layui.use(
        ['laydate', 'laypage', 'layer', 'table', 'carousel',
			'upload', 'element', 'slider'],
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
                ,height: 510
                ,url: '${ctxPath}/WorkFlow/jsonList' //数据接口
                ,title: '用户列表'
                ,page: true //开启分页
                ,totalRow: false //开启合计行
                ,id: 'userTbl'
                ,cols: [[     //表头
                    {type: 'checkbox', fixed: 'left'}
                    ,{field:'id', title:'ID', width:125, fixed:'left'}
                    ,{field:'deplomentId', title:'布署ID', width:125, fixed:'left'}
                    ,{field:'procDefId', title:'流程定义ID', width:115, fixed:'left'}
                    ,{field:'procCategory', title:'类型', width:125, fixed:'left'}
                    ,{field:'version', title:'版本', width:125, fixed:'left'}
                    ,{field:'procKey', title:'KEY', width:125, fixed:'left'}
                    ,{field:'note', title:'备注', width:125, fixed:'left'}
                    ,{field:'createDate', title:'创建日期', width:125, fixed:'left'}
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

            //{ps} 监听行工具事件
            //注：tool 是工具条事件名，com.gec.test 是 table 原始容器的属性 lay-filter="对应的值"
            table.on('tool(com.gec.test)', function(obj){
                var data = obj.data     //获得当前行数据
                    , layEvent = obj.event; //获得 lay-event 对应的值
                if(layEvent === 'detail'){
                    layer.msg('查看操作');
                } else if(layEvent === 'del'){
                    layer.confirm('真的删除流程么', function(index){
                        layer.msg('你点击了删除流程..');
                        delUser( data['id'] );
                    });
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


<script src="${ctxPath}/static/js/data.js"></script>
<!-- {ps} 编写我们的 js 代码! -->
<script>

    /*+------------------------------[搜索]------------------------------------+*/
    //{ps}要搜索的字段。。
    var schFlds = [
        {key:'procCategory',id:'#frmProcCategory'},
        {key:'note',id:'#frmNote'}
    ];
    //{1}实现搜索功能
	var table;
    function testSearch(){
        //{1}更新两个下拉列表的值。[加 # 号]
        //--[待补充]--
        //{2}创建一个条件对象 [传给后端的]
        var searchValue = {};   //花括号表示
        //{3}迭代  要搜索的字段
        for( var i=0; i<schFlds.length; i++ ){
            var key = schFlds[i].key;
            var itemId = schFlds[i].id;    //表单项的id
            var val = $( itemId ).val();   //取表单项值 。
            if( val!="" ){
                //{4}使用 键值法来设置对象的 key, value
                //{4}设置到条件中 cond
                searchValue[ key ] = val;
            }
        }
        if( table ){
            table.where = {};
		}
        //{3}提交数据到后台, 重新渲染表格。
        //   底层使用 ajax 请求 url
        layui.table.reload(
            'userTbl',       /* 表格 id */
            {
                url:'${ctxPath}/WorkFlow/jsonList?ran=14325234',  /* 数据接口 */
                where:searchValue,  /* 搜索条件  */
                page:{
                    curr:1   /* 默认第 1 页 */
                },
				done:function(){
                    table = this;
                    console.log( "+---------------------------------+" );
                    console.log( this );
					console.log( "+---------------------------------+" );
				}
            }
        );
    }
    /*+----------------------------------------------------------------------+*/
    //{ps}刷新功能
    function reloadPage( op ){
        window.location = '${ctxPath}/WorkFlow/viewProcList?op='+ op;
    }
    //{2}根据id删除用户
    function delUser( userId ){
        $.ajax({
            url:'${ctxPath}/WorkFlow/delete',
            type: 'post',
            data:{id:userId},
            dataType:'json',
            success:function (respTxt) {
                var ret = respTxt['result'];
                if (ret=='success'){
                    layer.msg('删除成功');
                    //{ps}设置一个定时器，1秒后刷新
                    setTimeout(
                        function(){
                            reloadPage('edit');
                        }, 1000 );
                }else {
                    layer.msg(respTxt['cause']);
                }
            }
        })
    }
</script>



</body>
</html>        
