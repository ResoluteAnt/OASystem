<%@ page language="java" contentType="text/html; charset=UTF-8"
		 pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
	<meta charset="utf-8">
	<meta name="viewport" content="width=device-width, user-scalable=no, initial-scale=1.0, maximum-scale=1.0, minimum-scale=1.0">
	<title>部门列表</title>
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
			<label class="layui-form-label">部门名称</label>
			<div class="layui-input-inline">
				<input type="text" name="frmDeptName" id="frmDeptName"
					   autocomplete="off" class="layui-input">
			</div>
		</div>

		<div class="layui-inline">
			<label class="layui-form-label">部门描述</label>
			<div class="layui-input-inline">
				<input type="text" name="frmDeptDesc" id="frmDeptDesc"
					   autocomplete="off" class="layui-input">
			</div>
		</div>
		<div class="layui-inline">
				<label class="layui-form-label" style="width:100px;">父级部门</label>
				<div class="layui-input-inline">
					<select name="frmSupDeptName" id="frmSupDeptName">
					</select>
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

<table class="layui-hide" id="demo" lay-filter="com.gec.test">
</table>

<script type="text/html" id="barDemo">
	<a class="layui-btn layui-btn-xs" lay-event="edit">编辑</a>
	<a class="layui-btn layui-btn-danger layui-btn-xs" lay-event="del">删除</a>
</script>

<script src="${ctxPath}/static/layui/layui.js?t=1554901098009" charset="utf-8">
</script>
<script src="${ctxPath}/static/js/form.js"></script>

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
                ,url: '${ctxPath}/Dept/jsonList' //数据接口
                ,title: '部门列表'
                ,page: true //开启分页
                ,totalRow: false //开启合计行
                ,id: 'userTbl'
                ,cols: [[     //表头
                    {type: 'checkbox', fixed: 'left'}
                    ,{field:'id', title:'部门ID', width:125, fixed:'left'}
                    ,{field:'parentId', title:'上级部门ID', width:125, fixed:'left'}
                    ,{field:'deptName', title:'部门名称', width:115, fixed:'left'}
                    ,{field:'supDeptName', title:'上级部门名称', width:125, fixed:'left'}
                    ,{field:'deptDesc', title:'部门说明', width:160, fixed:'left'}
                    ,{field:'level', title:'部门级别', width:110, fixed:'left'}
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
                    layer.confirm('真的删除部门么', function(index){
                        layer.msg('你点击了删除部门..');
                        delDept( data['id'] );
                    });
                } else if(layEvent === 'edit'){
                    layer.msg('你点击了编辑部门..');
                    editDept( data['id'] );
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

<!-- {ps} 编写我们的 js 代码! -->
<script src="${ctxPath}/static/js/data.js"></script>
<script>
    /*+-------------------------[初始化下拉列表数据]----------------------------+*/
    $(initArrData)
    //下拉菜单功能
    var dept_data = [];
    function initArrData(){
        $.ajax(
            {
                url: '${ctxPath}/Dept/getSelectData' ,
                type:'post',
                dataType:'json',
                success:function( respTxt ){
                    var ret = respTxt['result'];
                    if (ret=='success'){
                        //{1} 成功就提示成功， 跳转到 /User/viewList
                        var deptText = respTxt['deptData'];
                        var deptOp = {text:'请选择部门' ,val:""};
                        deptText.splice(0,0,deptOp);
                        dept_data = deptText;
                        var deptHtml = initSelect(dept_data);
                        $('#frmSupDeptName').append(deptHtml);
                        //{ps}记得渲染
                        layui.form.render();
                    }
                }
            }
        );
    }
    /*+----------------------------------------------------------------------+*/
    //{ps}要搜索的字段。。
    var schFlds = [
        {key:'deptName',id:'#frmDeptName'},
        {key:'deptDesc',id:'#frmDeptDesc'},
        {key:'parentId',id:'#frmSupDeptName'},
    ];
    //{1}实现搜索功能
    var table;
    function testSearch(){
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
        console.log("搜索条件...");
        console.log( searchValue );
        //{3}提交数据到后台, 重新渲染表格。
        //   底层使用 ajax 请求 url
        layui.table.reload(
            'userTbl',       /* 表格 id */
            {
                url:'${ctxPath}/Dept/jsonList',  /* 数据接口 */
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
    /*+-----------------------------------------------------------------------+*/
	//{ps}刷新功能
    function reloadPage( op ){
        window.location = '${ctxPath}/Dept/viewList?op='+ op;
    }
    //{1}编辑部门	-- 获得当前部门信息		/Dept/jsonInfo
    function editDept( deptId ){
        $.ajax({
            url:'${ctxPath}/Dept/jsonInfo',
            type: 'post',
            data:{id:deptId},
            dataType:'json',
            success:function (respTxt) {
                var ret = respTxt['result'];
                if (ret=='success'){
                    //{1}获取dept对象
                    var dept = respTxt['dept'];
                    console.log(dept);
                    gInputs[1]['options'] = dept_data;
                    //{2}生成表格
                    var tb1 = makeTable(gInputs,dept);
                    //{3}弹出对话框
                    showBox(tb1);
                }else {
                    layer.msg(respTxt['cause']);
                }
            }

        })
    }
    //{2}根据id删除部门
    function delDept( deptId ){
        $.ajax({
            url:'${ctxPath}/Dept/delete',
            type: 'post',
            data:{id:deptId},
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



    //{ps}用来生成表单
    //title:标题, name:表单项目名{实体类属性名相同}, readonly:只读, type:文本,下拉菜单,隐藏域
    var gInputs = [
        {title:"部门名称", name:"deptName", type:"text"},
        {title:"上级部门", name:"parentId", type:"select",options:dept_data},
        {title:"部门描述", name:"deptDesc", type:"text"},
        {name:"id", type:"hide"}
    ];

    function showBox( tbl ){
        layer.open({
            type: 1
            ,title: '编辑部门'
            ,closeBtn: false
            ,area: ['450px','395px']
            ,shade: 0
            ,id: 'LAY_layuipro'   //设定一个 id, 防止重复弹出
            ,btn: ['保存部门', '关闭对话框']
            ,btnAlign: 'c'
            ,moveType: 1          //拖拽模式, 0 或者 1
            ,content: tbl
            ,yes: function(){
                console.log("{DEBUG} 点击确认保存 ...");
                updateDept();
            }
            ,success: function( layero ){
                console.log("{DEBUG} success ...");
            }
        });
    }

    var items = [
        "deptName", "parentId", "deptDesc", "id"
    ];
    function pickData(){
        //{1}定义一个对象, 存放所有数据。
        var obj = {};
        for (var i=0;i<items.length;i++){
            //{2}获取表单项 id
            var id = items[i];
            var val = $("#"+id).val();
            //{3}填入obj对象
            obj[id] = val;
        }
        return obj;
    }
    //{ps}修改部门数据
    function updateDept() {
        var dept = pickData();
        console.log("修改数据查询结果。。。")
        console.log(dept);
        //{ps}提交  /Dept/save
        $.ajax(
            {
                url:'${ctxPath}/Dept/save',
                type:'post',
                data: pickData(),
                dataType:'json',
                success:function( resp ){
                    var ret = resp["result"];
                    if (ret=="success"){
                        //{1} 成功就提示成功， 跳转到 /Dept/viewList
                        alert("修改部门成功");
                        setTimeout(
                            function(){
                                reloadPage('edit');
                            }, 500 );
                    } else {
                        //{2} 失败就提示失败。
                        alert("修改部门失败,服务器内部错误");
                    }
                }
            }
        );
    }
</script>



</body>
</html>        
