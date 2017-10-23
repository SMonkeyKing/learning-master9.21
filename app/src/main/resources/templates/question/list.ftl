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

<form id="pagerForm" method="post" action="${ctx}/question/config?typeid=${typeid}">
    <input type="hidden" name="pageNum" value="${page.currentPage!}"/>
    <input type="hidden" name="numPerPage" value="${page.numPerPage!}"/>
    <input type="hidden" name="orderBy" value="desc">
    <input type="hidden" name="orderField" value="lastUpdated">
    <input type="hidden" name="name" value="${questionDO.subject!}">
</form>

<div class="pageHeader">
    <form id="questionForm" onsubmit="return navTabSearch(this);" action="${ctx}/question/config" class="required-validate" method="post">
        <div class="searchBar">

            <table class="searchContent">
                <tr>
                    <td>题目类型：</td>
                    <input type="hidden" name="typeid"  id="typeid" value="${typeid}">
                    <input type="hidden" name="type"  id="type" >
                    <td><button id="btn0" type="submit" value="0">全部</button></td>
                    <td><button id="btn1" type="submit" value="1">选择题</button></td>
                    <td><button id="btn2" type="submit" value="2">填空题</button></td>
                    <td><button id="btn3" type="submit" value="3">综合题</button></td>
                </tr>
                <#--<tr>
                    <td><button style="float: right">全部加入试卷</button></td>
                    &lt;#&ndash;<td><button style="float: right">查看试题篮</button></td>&ndash;&gt;
                </tr>-->
            </table>
        </div>
    </form>
        <div class="subBar">
            <ul>
                <#--<li>
                    <div>
                        <div class="buttonContent" style="float: right;">
                            <button onclick="addAllToPaper()">全部加入试卷</button>
                            <span>&nbsp;&nbsp;</span>
                            &lt;#&ndash;<button><a href="${ctx}/paper/paperBasket" target="dialog" ></a></button>&ndash;&gt;
                        </div>
                    </div>
                </li>-->

                <li >
                    <div>
                        <div class="buttonContent"  style="float: right;">
                            <a href="${ctx}/paper/paperBasket" target="dialog" ><button>查看试题篮</button></a>
                        </div>
                    </div>
                </li>
            </ul>
        </div>
        <#--<div class="subBar" style="float: right">
            <table>
                <tr>
                    <td style="float: right"><a class="button" href="#" id="addAll" onclick="addAllToPaper()">全部加入试卷</a></td>
                    <td style="float: right"><a class="button" href="${ctx}/paper/paperBasket" target="dialog" ><span>查看试题篮</span></a></td>
                </tr>
            </table>
        </div>-->
        <#--<div class="panelBar">
            <a class="button" href="${ctx}/paper/paperBasket" target="dialog" ><span>查看试题篮</span></a>
            <span>&nbsp;&nbsp;</span>
            <a class="button" href="#"  onclick="addAllToPaper();" ><span>全部加入试卷</span></span></a>
        </div>-->
</div>
<div class="pageContent">
   <div class="panelBar">
        <ul class="toolBar">
            <li><a class="add" href="${ctx}/question/prepareAdd?typeid=${typeid}" target="navTab"><span>增加</span></a></li>
        </ul>
    </div>
        <div class="accordionContent" layoutH="115">

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
                            <#--<li style="float: left"><a class="edit" href="${ctx}/question/prepareUpdate?id=${questionDO.getId()}" target="navTab" ><span>编辑</span></a></li>-->
                            <li style="float: right"><a class="add" href="javascript:void(0)" name="add${questionDO.getId()}"onclick="addToPaper(${questionDO.getId()});" ><span id="add">加入试卷</span></a></li>
                            <#if questionDO.type == 1 >
                                <#--只有选择题显示正确率-->
                                <li style="float: right"><span id="add" style="color: red">正确率：<#if questionDO.correctRate??>${questionDO.getCorrectRate()!}<#else >0</#if></span></li>
                            </#if>
                        <#--<li style="float: right"><a class="add" href="${ctx}/paper/addCookies?questionId=${questionDO.getId()!}" ><span>加入试卷</span></a></li>-->
                        </ul>
                    </div>
                    <div style="display:none;" class="panelContent" >
                        <div id="answer">
                            ${questionDO.getAnswer()!}
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
    
    function  addToPaper(id) {
        /*var add = document.getElementsById("add");
        add.innerHTML = "已加入试卷"
        add.disabled = true;
        */
        $.getJSON("${ctx}/paper/addCookies?questionId="+id,function () {
            //alert(id+"加入成功");
        });

        $.ajax({
            url: "${ctx}/paper/addCookies?questionId="+id,
            context: document.getElementsByName("add"+id),
            success: function(){

                $(this).css("color","#CCC");
                $(this).innerHTML = "已加入试卷";
                $(this).unbind("click");
                $(this).disabled = true;
            }});
    }


    function addAllToPaper() {
        $.getJSON("${ctx}/paper/addAll?typeid=${type}",function () {
            //alert("加入成功");
        });
    }
</script>
