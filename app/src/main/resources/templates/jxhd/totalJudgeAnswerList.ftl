<form id="pagerForm" method="post" action="${ctx}/jxhd/studentAns">
    <input type="hidden" name="pageNum" value="${page.currentPage!}"/>
    <input type="hidden" name="numPerPage" value="${page.numPerPage!}"/>
    <input type="hidden" name="orderBy" value="desc">
    <#--<input type="hidden" name="orderField" value="lastUpdated">-->
</form>

<#--<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="${ctx}/jxhd/studentAns" class="required-validate" method="post">
        <div class="searchBar">

        </div>
    </form>
</div>-->
<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" href="${ctx}/jxhd/studentAns" target="navTab"><span>刷新列表</span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="85">
        <thead>
        <tr align="center">

            <th width="120"><b>题号</b></th>
            <th width="120"><b>选A人数</b></th>
            <th width="120"><b>选B人数</b></th>
            <th width="120"><b>选C人数</b></th>
            <th width="120"><b>选D人数</b></th>
            <th width="120"><b>答对人数</b></th>
            <th width="100"><b>正确率</b></th>
            <th width="100"><b>操作</b></th>
        </tr>
        </thead>
        <tbody>
        <#if judgeAnswerDOs??&&judgeAnswerDOs?size gt 0>
            <#list judgeAnswerDOs as judgeAnswerDO>
            <tr align="center">

                <td>${judgeAnswerDO.getPaper_id()!}</td>
                <td>${judgeAnswerDO.getAnswerA()!}</td>
                <td>${judgeAnswerDO.getAnswerB()!}</td>
                <td>${judgeAnswerDO.getAnswerC()!}</td>
                <td>${judgeAnswerDO.getAnswerD()!}</td>
                <td>${judgeAnswerDO.getCorrect()!}</td>
<#--
                <td>${judgeAnswerDO.correct?int}/(${judgeAnswerDO.answerA?int}+${judgeAnswerDO.answerB?int}+${judgeAnswerDO.answerC?int}+${judgeAnswerDO.answerD?int})</td>
-->
                <td>${judgeAnswerDO.getRate()!}</td>
            <#--<td><input type="checkbox" id="${jxhdQuestionDO.id}" name="paper"></td>-->
                <td><a style="color:blue" href="${ctx}/jxhd/studentAnsInOneQuestion?paperid=${judgeAnswerDO.getPaper_id()!}" target="navTab">查看</a></td>
            </tr>
            </#list>
        <#else>
        <tr>
            <td><font color="red">很抱歉，系统找不到您的记录，换个条件试试</font></td>
        </tr>
        </#if>
        </tbody>
    </table>

    </form>
    <#--<div class="panelBar">
        <div class="pages">
            <span>显示</span>
            <select class="combox" name="numPerPage" onchange="navTabPageBreak({numPerPage:this.value})">
            <#list page.numPerPages?keys as key>
                <option value="${page.numPerPages[key]}"
                        <#if (key==(page.numPerPage)?string)>selected="selected"</#if>>${key}</option>
            </#list>
            </select>
            <span>条，共${page.totalCount!}条</span>
        </div>

        <div class="pagination" targetType="navTab" totalCount="${page.totalCount!}" numPerPage="${page.numPerPage!}"
             currentPage="${(page.pageNum+1)!}"></div>
    </div>-->
</div>
<div class="top"></div>

<script language="JavaScript">
    /*jQuery(document).ready(function()
    {
        setTimeout('myrefresh()',30000); //指定1秒刷新一次
    });*/
    function myrefresh(){
        $.getJSON("${ctx}/jxhd/studentAns",function () {

        });
    }
</script>
