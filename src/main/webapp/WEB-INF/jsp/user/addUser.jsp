<%@ page language="java" contentType="text/html; charset=UTF-8"
    pageEncoding="UTF-8"%>
<%@taglib uri="http://java.sun.com/jsp/jstl/core" prefix="c" %>
<c:set var="ctxPath" value="${pageContext.request.contextPath}"/>

<!DOCTYPE html>
<html>
<head>
  <meta charset="utf-8">
  <title>添加用户</title>
  <meta name="renderer" content="webkit">
  <meta http-equiv="X-UA-Compatible" content="IE=edge,chrome=1">
  <meta name="viewport" content="width=device-width, initial-scale=1, maximum-scale=1">
  <link rel="stylesheet" href="${ctxPath}/static/layui/css/layui.css"  media="all">
</head>
<style>
	.layui-form-selectup dl {
		top: 42px;
		bottom: unset;
	}
</style>
<body>
   
<fieldset class="layui-elem-field layui-field-title" 
	style="margin-top: 20px;">
  <legend>新增用户页</legend>
</fieldset>


<script src="${ctxPath}/static/js/jquery-1.11.1.min.js"></script>
<form id="userForm" class="layui-form" 
	action="" 
	method="post">
	
	<div class="layui-form-item">
    	<label class="layui-form-label">用户名称</label>
		<div class="layui-input-block" style="width:450px;">
			<input type="text" id="username"
				 name="username" autocomplete="off"
				 placeholder="请输入用户名"
			class="layui-input">
		</div>
	</div>

	<div class="layui-form-item">
		<label class="layui-form-label">密码</label>
		<div class="layui-input-block" style="width:450px;">
			<input type="text" id="password"
				   name="password" autocomplete="off"
				   placeholder="请输入密码"
				   class="layui-input">
		</div>
	</div>
	
	<div class="layui-form-item">
    	<label class="layui-form-label">用户别名</label>
		<div class="layui-input-block" style="width:450px;">
			<input type="text" id="nickName"
				 name="nickName" autocomplete="off"
				 placeholder="请输入用户别名"
			class="layui-input">
		</div>
	</div>
<div class="layui-form-item">
		<label class="layui-form-label">邮箱</label>
		<div class="layui-input-block" style="width:450px;">
			<input type="text" id="email"
				   name="email" autocomplete="off"
				   placeholder="请输入邮箱"
				   class="layui-input">
		</div>
	</div>

	<div class="layui-form-item">
		<div class="layui-inline">
			<label class="layui-form-label" style="width:100px;">部门</label>
			<div class="layui-input-inline">
				<select name="deptId" id="deptId">
				</select>
			</div>
		</div>
		<div class="layui-inline">
			<label class="layui-form-label" style="width:100px;">角色</label>
			<div class="layui-input-inline">
				<select name="role_id" id="role_id">
				</select>
			</div>
		</div>
	</div>

	<div class="layui-form-item">
    	<div class="layui-input-block">
      		<button class="layui-btn" type="button"
      			onclick="goSubmit();" >
      			创建用户
      		</button>
			<button type="reset" class="layui-btn layui-btn-primary">
				重置表单
			</button>
		</div>
	</div>

</form>

<script src="${ctxPath}/static/layui/layui.all.js" charset="utf-8"></script>
<script src="${ctxPath}/static/js/data.js" charset="utf-8"></script>
<script>
//{ps}初始化一下
$(showSelect);
function showSelect() {
    //返回数值的名称，第一个选择语句的名称，id值
    var deptArr = ['deptData','请选择部门','#deptId'];
    var roleArr = ['roleData','请选择角色','#role_id'];
    var arr = [deptArr,roleArr];
    //url,arr
    getSelectDate('${ctxPath}/User/getSelectData',arr);
}

//{ps}刷新页面
function reloadPage(){
    window.location = '${ctxPath}/User/viewList';
}

//{ps}设置我要抓取的表单数据
var items = [
     "username", "password", "nickName",
     "deptId" ,"role_id", "email"
];

//{ps} 抓取表单数据
function pickData(){
        //{1}定义一个对象, 存放所有数据。
        var user = "";
		user += "add,";
        for (var i=0;i<items.length;i++){
            //{2}获取表单项 id
            var id = items[i];
            var val = $("#"+id).val();
            if (i+1 != items.length){
                val += ",";
			}
            user += val;
        }
        return user;
}
//{1}提交用户信息
function goSubmit(){
    var user = pickData();

    console.log(user);
	//{ps}提交  /User/saveUser
	$.ajax(
		{
			url:'${ctxPath}/User/save',
			type:'post',
			data: {
			    user:user
			},
			dataType:'json',
			success:function( resp ){
				console.log( resp );
				var ret = resp["result"];
				if (ret=="success"){
                    //{1} 成功就提示成功， 跳转到 /User/viewList
				    alert("新增用户成功");
                    reloadPage();
				} else {
                    //{2} 失败就提示失败。
                    alert("新增用户失败,服务器内部错误");
				}
			}
		}
	);
}
</script>
