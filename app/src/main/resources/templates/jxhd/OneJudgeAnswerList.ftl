<form id="pagerForm" method="post" action="${ctx}/jxhd/studentAns">
    <input type="hidden" name="pageNum" value="${page.currentPage!}"/>
    <input type="hidden" name="numPerPage" value="${page.numPerPage!}"/>
    <input type="hidden" name="orderBy" value="desc">
    <input type="hidden" name="orderField" value="lastUpdated">
</form>

<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="${ctx}/jxhd/studentAnsInOneQuestion" class="required-validate" method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        姓名：<input type="text"  name="name" maxlength="20"
                                  value="${jxhdStudentAnswerDO.name!}"/>
                    </td>
                    <td>
                        <div class="buttonContent">
                            <button type="submit">查询</button>
                        </div>
                    </td>
                </tr>
            </table>
        </div>
    </form>
</div>
<div class="pageContent">

    <table class="table" width="100%" layoutH="85">
        <thead>
        <tr align="center">
            <#--<th width="50"><b>序号</b></th>-->
            <th width="50"><b>题号</b></th>
            <th width="120"><b>学号</b></th>
            <th width="120"><b>姓名</b></th>
            <th width="120"><b>答案</b></th>
                <th width="120"><b>正确答案</b></th>
            <th width="120"><b>结果</b></th>

        </tr>
        </thead>
        <tbody>
        <#if jxhdStudentAnswerDOs??&&jxhdStudentAnswerDOs?size gt 0>
            <#list jxhdStudentAnswerDOs as jxhdStudentAnswerDO>
            <tr align="center">

                <#--<td>${jxhdStudentAnswerDO_index?if_exists+page.pageNum*page.numPerPage+1}</td>-->
                <td>${jxhdStudentAnswerDO.getPaperId()!}</td>
                <td>${jxhdStudentAnswerDO.getUserno()!}</td>
                <td>${jxhdStudentAnswerDO.getUsername()!}</td>
                <td>${jxhdStudentAnswerDO.getAnswer()!}</td>
                <td>${jxhdStudentAnswerDO.getCorrectAnswer()!}</td>
                <#if (jxhdStudentAnswerDO.getCorrect()!)==1>
                    <td>正确</td>
                <#else >
                    <td style="color: red">错误</td>
                </#if>

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
    <div class="panelBar">
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
    </div>
</div>
<div class="top"></div>

<script language="JavaScript">
    jQuery(document).ready(function()
    {
        setTimeout('myrefresh()',30000); //指定1秒刷新一次
    });
    function myrefresh(){
        $.getJSON("${ctx}/jxhd/studentAns",function () {

        });
    }
</script>
