<form id="pagerForm" method="post" action="${ctx}/syncLearningPlan/config">
    <input type="hidden" name="pageNum" value="${page.currentPage!}"/>
    <input type="hidden" name="numPerPage" value="${page.numPerPage!}"/>
    <input type="hidden" name="orderBy" value="desc">
    <input type="hidden" name="orderField" value="dateCreated">
    <input type="hidden" name="name" value="${tbxaLearningPlanDO.name!}">
</form>

<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="${ctx}/syncLearningPlan/config?typeid=${typeid}" class="required-validate" method="post">
        <div class="searchBar">
            <input type="hidden" name="typeid" value="${typeid}">
            <table class="searchContent">
                <tr>
                    <td>
                        名称：<input type="text"  name="name" maxlength="20"
                                     value="${tbxaLearningPlanDO.name!}"/>
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
    <#if (role<2)>
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" href="${ctx}/syncLearningPlan/prepareAdd?typeid=${typeid}" target="navTab"><span>增加</span></a></li>
        </ul>
    </div>
    <table class="table" width="100%" layoutH="110">
    <#else >
    <table class="table" width="100%" layoutH="85">
    </#if>
        <thead>
        <tr align="center">
            <th width="50"><b>序号</b></th>
            <th width="180"><b>名称</b></th>
            <th width="70"><b>上传时间</b></th>
            <th width="150"><b>同步测试</b></th>
            <#if (role<2)>
            <th width="180"><b>操作</b></th>
            </#if>
        </tr>
        </thead>
        <tbody>
        <#if tbxaLearningPlanDOs??&&tbxaLearningPlanDOs?size gt 0>
            <#list tbxaLearningPlanDOs as tbxaLearningPlanDO>
            <tr align="center">
                <td>${tbxaLearningPlanDO_index?if_exists+page.pageNum*page.numPerPage+1}</td>
                <td><a href="${ctx}/office?path=${tbxaLearningPlanDO.name}">${tbxaLearningPlanDO.name!}</a></td>
                <td>${tbxaLearningPlanDO.dateCreated!}</td>

                <td>
                <#if (role<2)>
                    <a style="color:red" href="${ctx}/syncTestFromTK/config?pid=${tbxaLearningPlanDO.id}&typeid=${tbxaLearningPlanDO.typeid}" target="navTab">添加测试</a>
                </#if>
                    <a style="color:red" href="${ctx}/syncTestFromTK/list?typeid=${tbxaLearningPlanDO.id}" target="navTab">开始测试</a>
                </td>
                <#if (role<2)>
                <td>

                    <!-- 下载 -->
                    <a style="color:blue" href="${tbxaLearningPlanDO.url!}">下载</a>

                    <!-- 修改 -->
                    <a  style="color:blue" href="${ctx}/syncLearningPlan/prepareUpdate?id=${tbxaLearningPlanDO.id}" target="navTab">修改</a>
                    <!-- 作废 -->
                    <a style="color:blue" href="${ctx}/syncLearningPlan/delete?id=${tbxaLearningPlanDO.id}" target="ajaxTodo" title="温馨提示：是否确认删除" >删除</a>

                </td>
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
