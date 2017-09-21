<form id="pagerForm" method="post" action="${ctx}/sczy/academicJournal/config">
    <input type="hidden" name="pageNum" value="${page.currentPage!}"/>
    <input type="hidden" name="numPerPage" value="${page.numPerPage!}"/>
    <input type="hidden" name="orderBy" value="desc">
    <input type="hidden" name="orderField" value="lastUpdated">
    <input type="hidden" name="name" value="${academicJournalDO.name!}">
</form>

<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="${ctx}/sczy/academicJournal/config" class="required-validate" method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        期刊名称：<input type="text"  name="name" maxlength="20"
                                     value="${academicJournalDO.name!}"/>
                    </td>
                    <td>
                        <div class="buttonContent">
                            <button type="submit">查询</button>
                        </div>
                    </td>
                </tr>
            </table>
            <#--<div class="subBar">
                <ul>
                    <li>
                        <div class="buttonActive add">

                        </div>
                    </li>
                </ul>
            </div>-->
        </div>
    </form>
</div>
<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" href="${ctx}/sczy/academicJournal/add" target="navTab"><span>增加</span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="110">
        <thead>
        <tr align="center">
            <th width="10%"><b>序号</b></th>
            <th width="40%"><b>期刊名称</b></th>
            <th width="40%"><b>期刊链接</b></th>
            <th width="10%"><b>操作</b></th>
        </tr>
        </thead>
        <tbody>
        <#if academicJournalDOs??&&academicJournalDOs?size gt 0>
            <#list academicJournalDOs as academicJournalDO>
            <tr align="center">
                <td>${academicJournalDO_index?if_exists+page.pageNum*page.numPerPage+1}</td>
                <td>${academicJournalDO.name!}</td>
                <td><a href="${academicJournalDO.url!}" target="_blank">${academicJournalDO.url!}</a></td>
                <td>
                    <!-- 修改 -->
                    <a  style="color:blue" href="${ctx}/sczy/academicJournal/prepareUpdate?id=${academicJournalDO.id}" target="navTab" >修改</a>
                    <!-- 作废 -->
                    <a style="color:blue" href="${ctx}/sczy/academicJournal/delete?id=${academicJournalDO.id}" target="ajaxTodo" title="温馨提示：是否确认删除" >删除</a>
                </td>
            </tr>
            </#list>
        <#else>
        <tr>
            <td><font color="red">很抱歉，系统找不到您的记录，换个条件试试</font></td>
        </tr>
        </#if>
        </tbody>
    </table>
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
