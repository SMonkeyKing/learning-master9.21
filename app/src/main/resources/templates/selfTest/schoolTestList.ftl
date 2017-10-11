<form id="pagerForm" method="post" action="${ctx}/schoolTest/config">
    <input type="hidden" name="pageNum" value="${page.currentPage!}"/>
    <input type="hidden" name="numPerPage" value="${page.numPerPage!}"/>
    <input type="hidden" name="orderBy" value="desc">
    <input type="hidden" name="orderField" value="lastUpdated">
    <input type="hidden" name="name" value="${courseWare.name!}">
</form>

<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="${ctx}/schoolTest/config?typeid=${typeid!}" class="required-validate" method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        名称：<input type="text"  name="name" maxlength="20"
                                     value="${courseWare.name!}"/>
                    </td>
                    <td>
                        <div class="buttonContent">
                            <button type="submit">查询</button>
                        </div>
                        <#--<div class="buttonActive add">
                            <div class="buttonContent">
                                <button type="submit">查询</button>
                            </div>
                        </div>-->

                    </td>
                </tr>
            </table>

        </div>
    </form>
</div>
<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" href="${ctx}/schoolTest/add?typeid=${typeid}" target="navTab"><span>增加</span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="85">
        <thead>
        <tr align="center">
            <th width="10%"><b>序号</b></th>
            <th width="40%"><b>名称</b></th>
            <th width="15%"><b>选择题正确率</b></th>
            <th width="15%"><b>上传时间</b></th>
            <th width="15%"><b>测试</b></th>
            <#if (role<2)>
            <th width="20%"><b>操作</b></th>
            </#if>
        </tr>
        </thead>
        <tbody>
        <#if courseWares??&&courseWares?size gt 0>
            <#list courseWares as courseWare>
            <tr align="center">
                <td>${courseWare_index?if_exists+page.pageNum*page.numPerPage+1}</td>
                <td><a href="${courseWare.url!}">${courseWare.name!}</a></td>
                <td><a href="${ctx}/schoolTest/getRate?paperid=${courseWare.id!}" target="navTab">查看正确率</a></td>
                <td>${courseWare.dateCreated!}</td>
                <td>
                    <a style="color:red" href="${ctx}/syncTest/doTest?syncTestId=${courseWare.id!}" target="navTab">开始测试</a>
                </td>
                <td>
                    <#if (role<2)>
                    <!-- 下载 -->
                    <a style="color:blue" href="${courseWare.url!}">下载</a>

                    <!-- 修改 -->
                    <a  style="color:blue" href="${ctx}/courseWare/prepareUpdate?id=${courseWare.id}" target="navTab">修改</a>
                    <!-- 作废 -->
                    <a style="color:blue" href="${ctx}/courseWare/delete?id=${courseWare.id}" target="ajaxTodo" title="温馨提示：是否确认删除" >删除</a>
                    </#if>
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
