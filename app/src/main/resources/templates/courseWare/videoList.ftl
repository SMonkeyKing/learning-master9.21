<form id="pagerForm" method="post" action="${ctx}/courseWare/video/config">
    <input type="hidden" name="pageNum" value="${page.currentPage!}"/>
    <input type="hidden" name="numPerPage" value="${page.numPerPage!}"/>
    <input type="hidden" name="orderBy" value="desc">
    <input type="hidden" name="orderField" value="lastUpdated">
    <input type="hidden" name="name" value="${courseWare.name!}">
    <input type="hidden" name="typeid" value="${typeid}">
    <input type="hidden" name="videoUrl" value="${courseWare.url!}">
</form>

<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="${ctx}/courseWare/video/config?typeid=${typeid}" class="required-validate" method="post">
        <div class="searchBar">
            <table class="searchContent">
                <input type="hidden" name="typeid" value="${typeid}">
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
    <#if (role<2)>
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" href="${ctx}/courseWare/prepareAdd?typeid=${typeid}" target="navTab"><span>增加</span></a></li>
        </ul>
    </div>
    </#if>
    <table class="table" width="100%" layoutH="110">
        <thead>
        <tr align="center">
            <th width="50"><b>序号</b></th>
            <th width="200"><b>名称</b></th>
            <th width="60"><b>上传者</b></th>
            <th width="70"><b>上传时间</b></th>
            <th width="150"><b>操作</b></th>
        </tr>
        </thead>
        <tbody>
        <#if courseWares??&&courseWares?size gt 0>
            <#list courseWares as courseWare>
            <tr align="center">
                <td>${courseWare_index?if_exists+page.pageNum*page.numPerPage+1}</td>
                <#--<td><a href="javascript:void(0);" onclick="openWord(${courseWare.url!})" target="_blank">${courseWare.name!}</a></td>-->
                <#--<td><embed src="${courseWare.url!}">${courseWare.name!}</embed></td>-->
                <#--<td><video src="${courseWare.url!}"><span>${courseWare.name!}</span></video></td>-->
                <#--<td><iframe src="${courseWare.url!}"></iframe></td>-->
                <td>
                    <#--<object align=middle class=OBJECT classid=CLSID:22d6f312-b0f6-11d0-94ab-0080c74c7e95 height=320 id=MediaPlayer width=356>
                        <param name="ShowStatusBar" value="-1">
                        <param name="Filename" value=""${courseWare.name!}"">
                        <embed type=application/x-oleobject codebase=http://activex.microsoft.com/activex/controls/mplayer/en/nsmp2inf.cab#Version=5,1,52,701 flename=mp src="${courseWare.url!}" width=356 height=320>
                        </embed>
                    </object>-->
                        <a href="${ctx}/courseWare/playVideo?url=${courseWare.url}" onclick="openWord(${courseWare.url!})" target="_blank">${courseWare.name!}</a>
                </td>
                <td>${courseWare.uploadName!}</td>
                <td>${courseWare.dateCreated!}</td>

                <td>

                    <!-- 下载 -->
                    <a style="color:blue" href="${courseWare.url!}">下载</a>
                    <#if (role<2)>
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
<script>
    function  openWord(url1) {
        /*window.open("http://localhost/courseWare/教学资源管理平台.doc");*/
        /*var cmd = new ActiveXObject('WScript.Shell');
        cmd.Run('winword http://localhost/courseWare/教学资源管理平台.doc');*/
        var url = "http://localhost/courseWare/教学资源管理平台.doc";
        //直接打开word
        var word = new ActiveXObject("Word.Application");
        word.Visible = true;
        word.Activate();//打开的word激活房子最前面窗口
        word.Documents.Open(url);
    }
</script>
