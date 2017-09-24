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
<form id="pagerForm" method="post" action="${ctx}/jxhd/question/config">
    <input type="hidden" name="pageNum" value="${page.currentPage!}"/>
    <input type="hidden" name="numPerPage" value="${page.numPerPage!}"/>
    <input type="hidden" name="orderBy" value="desc">
    <input type="hidden" name="orderField" value="lastUpdated">
    <#--<input type="hidden" name="content" value="${jxhdQuestionDO.content!}">-->
</form>

<div class="pageHeader">
    <form id="questionForm" onsubmit="return navTabSearch(this);" action="${ctx}/jxhd/question/config" class="required-validate" method="post">
        <div class="searchBar">

            <table class="searchContent">
                <tr>
                    <td>题目类型：</td>
                    <input type="hidden" name="type"  id="type">
                    <td><button id="btn0" type="submit" value="0">全部</button></td>
                    <td><button id="btn1" type="submit" value="1">选择题</button></td>
                    <td><button id="btn2" type="submit" value="2">填空题</button></td>
                    <td><button id="btn3" type="submit" value="3">计算题</button></td>
                </tr>
            </table>
        </div>
    </form>
        <div class="subBar">
            <ul>
                <#--<li>
                    <div>
                        <div class="buttonContent" style="float: right;">
                            <button  onclick="addAllToPaper()">一键推送</button>
                            <span>&nbsp;&nbsp;</span>
                            &lt;#&ndash;<button><a href="${ctx}/paper/paperBasket" target="dialog" ></a></button>&ndash;&gt;
                        </div>
                    </div>
                </li>-->

                <#--<li >
                    <div>
                        <div class="buttonContent"  style="float: right;">
                            <a href="${ctx}/paper/paperBasket" target="dialog" ><button>查看试题篮</button></a>
                        </div>
                    </div>
                </li>-->
            </ul>
        </div>
</div>
<div class="pageContent">
    <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" href="${ctx}/jxhd/question/prepareAdd" target="navTab"><span>增加</span></a></li>
        </ul>
    </div>
        <div class="accordionContent" layoutH="87">

        <#--<div class="pageFormContent" layoutH="138">-->
            <#if jxhdQuestionDOs??&&jxhdQuestionDOs?size gt 0>
                <#list jxhdQuestionDOs as jxhdQuestionDO>
                    <div class="panelContent" >
                        <div id="content">
                            <#--<span style="float: left"><input type="checkbox"></span>-->

                            ${jxhdQuestionDO.getQuestion()!}

                        </div>
                    </div>
                    <div class="panelBar">
                        <ul class="toolBar">
                            <li style="float: left"><a class="edit" href="${ctx}/jxhd/question/prepareUpdate?id=${jxhdQuestionDO.getId()}" target="navTab" ><span>修改</span></a></li>
                            <#if jxhdQuestionDO.version == 1>
                                <li style="float: right"><a class="add" style="color: red" name="push${jxhdQuestionDO.getId()}" href="javascript:void(0)"  onclick="pushQuestion(${jxhdQuestionDO.getId()});" ><span>推送</span></a></li>
                            <#else >
                                <li style="float: right"><a class="add" style="color: grey" name="push${jxhdQuestionDO.getId()}" href="javascript:void(0)" ><span>推送</span></a></li>
                            <#--<li style="float: right"><a class="add" href="${ctx}/paper/addCookies?questionId=${questionDO.getId()!}" ><span>加入试卷</span></a></li>-->
                            </#if>
                        </ul>
                    </div>
                    <div style="display:none;" class="panelContent" >
                        <div id="answer">
                            ${jxhdQuestionDO.getAnswer()!}
                        </div >
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

    function pushQuestion(id) {
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
            $(this).disabled = true;
        }});
    }
    /*function  addToPaper(id) {

        $.getJSON("${ctx}/paper/addCookies?questionId="+id,function () {
            //alert(id+"加入成功");
        });
    }
    function addAllToPaper() {
        $.getJSON("${ctx}/paper/addAll",function () {
            //alert("加入成功");
        });
    }*/
</script>
