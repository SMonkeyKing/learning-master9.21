<style>
    #content{
        height: auto;
        margin: 15px auto 5px 12px;
        /*border:1px solid #B3E4EB;*/
    }
    #content img{
        width: 10%;
        height:10%;
    }
    .panelContent{
        border:1px solid #B3E4EB;
        height: auto;
    }
    #answer{
        height: auto;
        margin: 15px auto 5px 12px;
        /*border:1px solid #B3E4EB;*/
    }
    .accordionContent{
        height: auto;
    }
</style>

<form id="pagerForm" method="post" action="${ctx}/syncTestFromTK/config?typeid=${typeid}">
    <input type="hidden" name="pageNum" value="${page.currentPage!}"/>
    <input type="hidden" name="numPerPage" value="${page.numPerPage!}"/>
    <input type="hidden" name="orderBy" value="desc">
    <input type="hidden" name="orderField" value="lastUpdated">
    <#--<input type="hidden" name="name" value="${questionDO.subject!}">-->
</form>

<div class="pageHeader">
    <form id="questionForm" onsubmit="return navTabSearch(this);" action="${ctx}/syncTestFromTK/config" class="required-validate" method="post">
        <div class="searchBar">

            <table class="searchContent">
                <tr>
                    <td>题目类型：</td>
                    <input type="hidden" name="typeid"  id="typeid" value="${typeid}">
                    <input type="hidden" name="type"  id="type" >
                    <td><button id="btn0" type="submit" value="0">全部</button></td>
                    <td><button id="btn1" type="submit" value="1">选择题</button></td>
                    <td><button id="btn2" type="submit" value="2">填空题</button></td>
                    <td><button id="btn3" type="submit" value="3">计算题</button></td>
                </tr>
            </table>
        </div>
    </form>
</div>
<div class="pageContent">

    <div class="accordionContent" layoutH="60">

    <#--<div class="pageFormContent" layoutH="138">-->
    <#if questionDOs??&&questionDOs?size gt 0>
        <#list questionDOs as questionDO>
            <div class="panelContent" >
                <div id="content">
                ${questionDO.getContent()!}
                </div>
            </div>
            <div class="panelBar">
                <ul class="toolBar">
                    <#if questionDO.getVersion() == 1>
                        <li style="float: right"><a class="add" href="javascript:void(0)" id="add${questionDO.getId()}" name="add${questionDO.getId()}" onclick="addToSyncTest(${questionDO.getId()});" ><span id="add">加入同步测试</span></a></li>
                    <#else >
                        <li style="float: right;color: grey"><a href="javascript:return false;" onclick="return false;" ><span id="add" style="color: #CCC;">已加入同步测试</span></a></li>
                    </#if>
                <#--<li style="float: right"><a class="add" href="${ctx}/paper/addCookies?questionId=${questionDO.getId()!}" ><span>加入试卷</span></a></li>-->
                </ul>
            </div>
            <div style="display:none;" class="panelContent" >
                <div id="answer">
                ${questionDO.getAnswer()!}
                </div>
            </div>
        </#list>


    </#if>
    </div>

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
    $(function () {
        $("#btn0").click(
                function () {
                    $("#type").val($("#btn0").val());
                }
        );
        $("#btn1").click(
                function () {
                    $("#type").val($("#btn1").val());
                }
        );
        $("#btn2").click(
                function () {
                    $("#type").val($("#btn2").val());
                }
        );
        $("#btn3").click(
                function () {
                    $("#type").val($("#btn3").val());
                }
        );

    });

    function  addToSyncTest(id) {
        /*var add = document.getElementsById("add");
        add.innerHTML = "已加入试卷"
        add.disabled = true;
        */
        /*$.getJSON("/syncTestFromTK/addQuestion?questionId="+id,function (data) {
            //alert(id+"加入成功");
            alert(data);
        });*/

       $.ajax({
            type: "POST",
            url: "${ctx}/syncTestFromTK/addQuestion",
            data: {'questionId': id},
            context: document.getElementsByName("add"+id),
            success: function (data) {
                //alert(data);
                if (data == 1) {
                    $(this).css("color", "#CCC");
                    $(this).innerHTML = "已加入同步测试";
                    $(this).text("已加入同步测试");
                    $(this).unbind("click");
                    $(this).disabled = true;
                    $(this).click(function(){
                        return false;
                    });
                    document.getElementById("add"+id).setAttribute("href","javascript:return false;");
                    document.getElementById("add"+id).setAttribute("onclick","return false;");
                }
            }
        });
    }


    function addAllToPaper() {
        $.getJSON("${ctx}/paper/addAll?typeid=${type}",function () {
            //alert("加入成功");
        });
    }
</script>
