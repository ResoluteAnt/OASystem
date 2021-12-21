
/*+-------------------------[初始化下拉列表数据]------------------------+*/

/**
 * 获得生成下拉列表的html语句
 */
function initSelect(arr){
    var html = "";
    for(var i=0;i<arr.length;i++){
        var d = arr[i];
        html += "<option value=\""+ d['val'] +"\">"+
            d['text'] +"</option>";
    }
    return html;
}

/**
 *
 * @param url  url映射地址
 * @param arr  参数数组，用于存放数组的数组，
 *             你要弄多少个下拉列表你就整多少个数组
 */
function getSelectDate(url,arr) {
    //
    //返回数值的名称，第一个选择语句的名称，id值
    // resp[0],first[1],id
    $.ajax(
        {
            url: url ,
            type:'post',
            dataType:'json',
            success:function( respTxt ){
                //console.log( respTxt );
                var ret = respTxt['result'];
                if (ret=='success'){
                    //{1} 成功就提示成功， 跳转到 /User/viewList
                    for (var i = 0;i<arr.length;i++){
                        createSelect(respTxt[arr[i][0]] , arr[i][1] , arr[i][2] );
                    }
                }
            }
        }
    );
}

/**
 * 获得创建下拉列表
 * @param resp  返回数值的名称
 * @param first 第一个选择语句的名称
 * @param id    生成下拉列表的id值
 */
function createSelect(resp,first,id) {
    //first[1]
    var op = {text:first ,val:""};
    console.log( op );
    //{1}将op添加到第一位arr
    resp.splice(0,0,op);
    //{2}生成代码
    var html = initSelect(resp);
    //{ps}生成部门的下拉列表
    $( id ).append(html);
    layui.form.render();
}

/**
 * 获得参数的数组
 * url地址，0数值的名称和1第一个选择语句的数组
 */
// function getArrDate(url,arr) {
//     var retArr = [];
//     $.ajax(
//         {
//             url: url ,
//             type:'post',
//             dataType:'json',
//             async: false,
//             success:function( respTxt ){
//                 //console.log( respTxt );
//                 var ret = respTxt['result'];
//                 if (ret=='success'){
//                     //{1} 成功就提示成功， 跳转到 /User/viewList
//                     for (var i = 0;i<arr.length;i++){
//                         var text = respTxt[arr[i][0]];
//                         var op = {text:arr[i][1] ,val:""};
//                         text.splice(0,0,op);
//                         retArr.push(text);
//                     }
//                 }
//             }
//         }
//     );
//     //{ps}返回数组  第一个需要获得的数组，第二个需要获得的数组
//     return retArr;
// }
/*+----------------------------------------------------------------------+*/