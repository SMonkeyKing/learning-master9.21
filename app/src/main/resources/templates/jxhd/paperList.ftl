<form id="pagerForm" method="post" action="${ctx}/jxhd/paper/config">
    <input type="hidden" name="pageNum" value="${page.currentPage!}"/>
    <input type="hidden" name="numPerPage" value="${page.numPerPage!}"/>
    <input type="hidden" name="orderBy" value="desc">
    <input type="hidden" name="orderField" value="lastUpdated">
    <input type="hidden" name="paperName" value="${jxhdQuestionDO.paperName!}">
    <#--<input type="hidden" name="typeid" value="${typeid}">-->
</form>

<div class="pageHeader">
    <form onsubmit="return navTabSearch(this);" action="${ctx}/jxhd/paper/config" class="required-validate" method="post">
        <div class="searchBar">
            <table class="searchContent">
                <tr>
                    <td>
                        名称：<input type="text"  name="paperName" maxlength="20"
                                     value="${jxhdQuestionDO.paperName!}"/>
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
    <#--<form id="pushForm" onsubmit="return navTabSearch(this);" action="" class="required-validate" method="post">-->
    <div class="panelBar">
        <ul class="toolBar">
            <li style="float: left"><a class="add" href="${ctx}/jxhd/paper/prepareAdd" target="navTab"><span>增加</span></a></li>
            <#--<li style="float: right"><button onClick='SelectCheckbox("paper",this.form)'>推送</button></li>-->
        </ul>
    </div>

    <table class="table" width="100%" layoutH="110">
        <thead>
        <tr align="center">
            <th width="50"><b>序号</b></th>
            <th width="200"><b>试卷名称</b></th>
            <th width="60"><b>上传者</b></th>
            <th width="70"><b>上传时间</b></th>
            <th width="150"><b>操作</b></th>
            <th width="50"><b>推送</b></th>
        </tr>
        </thead>
        <tbody>
        <#if jxhdQuestionDOs??&&jxhdQuestionDOs?size gt 0>
            <#list jxhdQuestionDOs as jxhdQuestionDO>
            <tr align="center">

                <td>${jxhdQuestionDO_index?if_exists+page.pageNum*page.numPerPage+1}</td>
                <td><a href="${jxhdQuestionDO.paperUrl!}" target="_blank">${jxhdQuestionDO.paperName!}</a></td>
                <td>${jxhdQuestionDO.uploadName!}</td>
                <td>${jxhdQuestionDO.dateCreated!}</td>
                <td>
                    <!-- 下载 -->
                    <a style="color:blue" href="${jxhdQuestionDO.paperUrl!}">下载</a>

                    <!-- 修改 -->
                    <a  style="color:blue" href="${ctx}/jxhd/paper/prepareUpdate?id=${jxhdQuestionDO.id}" target="navTab">修改</a>
                    <!-- 作废 -->
                    <a style="color:blue" href="${ctx}/jxhd/paper/delete?id=${jxhdQuestionDO.id}" target="ajaxTodo" title="温馨提示：是否确认删除" >删除</a>

                </td>
                <td>
                    <#if jxhdQuestionDO.version==1>
                        <a name="push${jxhdQuestionDO.id}" style="color:red" href="javascript:void(0)" onclick="pushPaper(${jxhdQuestionDO.id})">推送</a>
                    <#else >
                    <#--<a style="color:red" href="${ctx}/jxhd/push?id=${jxhdQuestionDO.id}" target="ajaxTodo" title="温馨提示：是否确认推送" >推送</a>-->
                    <a name="push${jxhdQuestionDO.id}" style="color:grey" href="javascript:void(0)" >推送</a>
                    </#if>
                </td>
                <#--<td><input type="checkbox" id="${jxhdQuestionDO.id}" name="paper"></td>-->

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
<script>
    $(function(){
        $("#push").click(function(){
            alert("1");
            $(this).css("color","#CCC");
            $(this).unbind("click");
            $(this).innerHTML = "已推送";
        })
    });
    function pushPaper(id) {

        /*$.getJSON("${ctx}/jxhd/push?id="+id,function () {
            //alert("加入成功");
            $("#push").css("color","#CCC");
        });*/
        $.ajax({
            url: "${ctx}/jxhd/push?id="+id,
            context: document.getElementsByName("push"+id),
            success: function(){
                $(this).css("color","#CCC");
                $(this).innerHTML = "已推送";
                $(this).unbind("click");
            }});
    }
    /*function  openWord(url1) {
        /!*window.open("http://localhost/courseWare/教学资源管理平台.doc");*!/
        /!*var cmd = new ActiveXObject('WScript.Shell');
        cmd.Run('winword http://localhost/courseWare/教学资源管理平台.doc');*!/
        var url = "http://localhost/courseWare/教学资源管理平台.doc";
        //直接打开word
        var word = new ActiveXObject("Word.Application");
        word.Visible = true;
        word.Activate();//打开的word激活房子最前面窗口
        word.Documents.Open(url);
    }*/
    function SelectCheckbox(name,forml){
        var s=false;
        var boarId,n=0;
        var strid,strurl;
        name=document.getElementsByName(name)
        for(i=0;i<name.length;i++)
        {
            if(name[i].checked){
                n=n+1;
                s=true;
                boarId=name[i].id+"";

                if(n==1){
                    strid=boarId;
                }
                else {
                    strid=strid+","+boarId;
                }
            }
        }
        $.getJSON("${ctx}/jxhd/paper/push?paperid="+strid,function () {
            //alert("加入成功");
        });
        /*strurl="${ctx}/jxhd/paper/push?paperid="+strid;
        if(!s){
            alert("请选择要推送的试卷！");
            return false;
        }
        if(confirm("你确定要推送这些试卷吗？")){
            forml.action=strurl;
            forml.submit();
        }*/
    }
</script>
