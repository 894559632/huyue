<!DOCTYPE html PUBLIC "-//W3C//DTD XHTML 1.0 Transitional//EN" "http://www.w3.org/TR/xhtml1/DTD/xhtml1-transitional.dtd">
<html xmlns="http://www.w3.org/1999/xhtml">
<head>
<meta http-equiv="Content-Type" content="text/html; charset=UTF-8">
<link href="/mag/style/idialog.css" rel="stylesheet" id="lhgdialoglink">
<title>内容类别</title>
<script type="text/javascript" src="/mag/js/jquery-1.10.2.min.js"></script>
<script type="text/javascript" src="/mag/js/lhgdialog.js"></script>
<script type="text/javascript" src="/mag/js/layout.js"></script>
<link href="/mag/style/style.css" rel="stylesheet" type="text/css">
</head>

<body class="mainbody">
<form name="form1" method="post" action="/Verwalter/product/category/list" id="form1">
<div>
<input type="hidden" name="__EVENTTARGET" id="__EVENTTARGET" value="${__EVENTTARGET!""}" />
<input type="hidden" name="__EVENTARGUMENT" id="__EVENTARGUMENT" value="${__EVENTARGUMENT!""}" />
<input type="hidden" name="__VIEWSTATE" id="__VIEWSTATE" value="${__VIEWSTATE!""}" />
</div>

<script type="text/javascript">
var theForm = document.forms['form1'];
if (!theForm) {
    theForm = document.form1;
}
function __doPostBack(eventTarget, eventArgument) {
    if (!theForm.onsubmit || (theForm.onsubmit() != false)) {
        theForm.__EVENTTARGET.value = eventTarget;
        theForm.__EVENTARGUMENT.value = eventArgument;
        theForm.submit();
    }
}

//$(function () {
  //  $("#isEnable").click(function () { changeEnable(); });   //确认订单
//})；

    //隐藏分类
    function changeEnable(id) {
        var dialog = $.dialog.confirm('确认后将修改该类别显示状态，确认要继续吗？', function () {
            var postData = { "id": id};
            //发送AJAX请求
            sendAjaxUrl(dialog, postData, "/Verwalter/product/category/param/edit");
            return false;
        });
    }

//发送AJAX请求
    function sendAjaxUrl(winObj, postData, sendUrl) {
        $.ajax({
            type: "post",
            url: sendUrl,
            data: postData,
            dataType: "json",
            error: function (XMLHttpRequest, textStatus, errorThrown) {
                $.dialog.alert('尝试发送失败，错误信息：' + errorThrown, function () { }, winObj);
            },
            success: function (data) {
                if (data.code == 0) {
                    winObj.close();
                    $.dialog.tips(data.msg, 2, '32X32/succ.png', function () { location.reload(); }); //刷新页面
                } else {
                    $.dialog.alert('错误提示：' + data.message, function () { }, winObj);
                }
            }
        });
    }

</script>

<!--导航栏-->
<div class="location">
  <a href="javascript:history.back(-1);" class="back"><i></i><span>返回上一页</span></a>
  <a href="/Verwalter/center" class="home"><i></i><span>首页</span></a>
  <i class="arrow"></i
  <span>分类列表</span>
</div>
<!--/导航栏-->

<!--工具栏-->
<div class="toolbar-wrap">
  <div id="floatHead" class="toolbar">
    <div class="l-list">
      <ul class="icon-list">
        <li><a class="add" href="/Verwalter/product/category/edit"><i></i><span>新增</span></a></li>
        <li><a id="btnSave" class="save" href="javascript:__doPostBack('btnSave','')"><i></i><span>保存</span></a></li>
        <li><a class="all" href="javascript:;" onclick="checkAll(this);"><i></i><span>全选</span></a></li>
        <li><a onclick="return ExePostBack('btnDelete');" id="btnDelete" class="del" href="javascript:__doPostBack('btnDelete','')"><i></i><span>删除</span></a></li>
      </ul>
      
      <div class="menu-list">
        <div class="rule-single-select">
            <select name="categoryId" onchange="javascript:setTimeout(__doPostBack('changeCategory', ''), 0)">
                <option <#if !categoryId??>selected="selected"</#if> value="">批量移动到</option>
                <#if product_category_list??>
                    <#list product_category_list as c>
                        <#if c.layerCount?? && c.layerCount < 3>
                            <option value="${c.id!""}" <#if categoryId?? && c.id==categoryId></#if> ><#if c.layerCount?? && c.layerCount gt 1><#list 1..(c.layerCount-1) as a>　</#list>├ </#if>${c.title!""}</option>
                        </#if>
                    </#list>
                </#if>
            </select>
        </div>
      </div>
      
    </div>
        
    <div class="r-list">
          <input name="keywords" type="text" class="keyword">
          <a id="lbtnSearch" class="btn-search" href="javascript:__doPostBack('btnSearch','')">查询</a>
    </div>
  </div>
</div>
<!--/工具栏-->

<!--列表-->

<table width="100%" border="0" cellspacing="0" cellpadding="0" class="ltable">
    <tbody><tr class="odd_bg">
        <th width="6%">选择</th>
        <th align="left" width="6%">编号</th>
        <th align="left">类别名称</th>
        <th align="left" width="12%">调用别名</th>
        <th align="left" width="12%">排序</th>
        <th align="left">显示/隐藏</th>
        <th width="12%">操作</th>
    </tr>

    <#if category_list??>
    <#list category_list as cat>
        <tr>
            <td align="center">
                <span class="checkall" style="vertical-align:middle;">
                    <input id="rptList_ctl01_chkId" type="checkbox" name="listChkId" value="${cat_index}">
                </span>
                <input type="hidden" name="listId" id="listId" value="${cat.id?c}">
            </td>
            <td>${cat.id?c}</td>
            <td>
                <#if cat?? && cat.layerCount gt 1>
                    <span style="display:inline-block;width:${(cat.layerCount-2)*24}px;"></span>
                    <span class="folder-line"></span>
                </#if>
                <span class="folder-open"></span>
                <a href="/Verwalter/product/category/edit?id=${cat.id?c}">${cat.title!""}</a>
            </td>
            <td>${cat.callIndex!""}</td>
            <td><input name="listSortId" type="text" value="${cat.sortId?c}" class="sort" onkeydown="return checkNumber(event);"></td>
            <td><a href="javascript:;" id="isEnable" onclick="changeEnable(${cat.id?c})"><#if cat.isEnable?? && cat.isEnable>显示<#else>隐藏</#if></a></td>
            <td align="center">
                <a href="/Verwalter/product/category/edit?id=${cat.id?c}&sub=1">添加子类</a>
                <a href="/Verwalter/product/category/edit?id=${cat.id?c}">修改</a>
            </td>
        </tr>
    </#list>
    </#if>
    
    </tbody>
</table>

<!--/列表-->

<!--内容底部-->
<div class="line20"></div>
<div class="pagelist">
</div>
<!--/内容底部-->

</form>

</body></html>