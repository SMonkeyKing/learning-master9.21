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
<form id="pagerForm" method="post" action="${ctx}/question/config">
    <#--<input type="hidden" name="pageNum" value="${page.currentPage!}"/>
    <input type="hidden" name="numPerPage" value="${page.numPerPage!}"/>-->
    <input type="hidden" name="orderBy" value="desc">
    <input type="hidden" name="orderField" value="lastUpdated">
    <input type="hidden" name="name" value="${questionDO.subject!}">

</form>

<div class="pageHeader">

    <form id="questionForm" onsubmit="return navTabSearch(this);" action="${ctx}/syncTest/question/config" class="required-validate" method="post">
        <div class="searchBar">

            <table class="searchContent">
                <tr>
                    <td>题目类型：</td>
                    <input type="hidden" name="listSize" value="${listSize!}">
                    <input type="hidden" name="typeid" value="${typeid!}">
                    <input type="hidden" name="type"  id="type" value="${type}">
                    <td><button id="btn1" type="submit" value="1">选择题</button></td>
                    <td><button id="btn2" type="submit" value="2">填空题</button></td>
                    <td><button id="btn3" type="submit" value="3">计算题</button></td>
                </tr>
            </table>
        </div>
    </form>
    <div class="formBar">
        <ul>
            <li style="text-align: center"><div class="buttonActive"><div class="buttonContent"><button  onclick="submitAnswer()">提交答案</button></div></div></li>
            <li><a class="buttonActive"  id = "gen" href="${ctx}/syncTest/backResult" target="navTab"><span>查看测试结果</span></a></li>
        </ul>
    </div>
        <#--<div class="subBar">
            <ul>
                <li>
                    <div>
                        <div class="buttonContent" style="float: right;">
                            <button  onclick="submitAnswer()">提交答案</button>
                            <span>&nbsp;&nbsp;</span>
                        </div>
                    </div>

                </li>
                <li>
                    <div>
                        <div class="buttonContent" style="float: right;">
                            <button  onclick="seeResult()">查看结果</button>
                            <span>&nbsp;&nbsp;</span>
                        </div>
                    </div>

                </li>
            </ul>
        </div>-->
        <#--<div class="panelBar">
            <ul class="toolBar">
                <li><a class="add" href="javascript:void(0)" target="navTab" onclick="submitAnswer()"><span>提交答案</span></a></li>
            </ul>
        </div>-->

</div>
<div class="pageContent">
    <form id="xztAnswerForm" method="post">
        <#--<div class="panelBar">
            <ul class="toolBar">
                <li><a class="add" href="javascript:void(0);" target="navTab" onclick="submitAnswer()"><span>提交答案</span></a></li>
            </ul>
        </div>-->
        <div class="accordionContent" layoutH="67">

        <#--<div class="pageFormContent" layoutH="138">-->
            <#if questionDOs??&&questionDOs?size gt 0>
                <#list questionDOs as questionDO>
                    <div class="panelContent" >
                        <div id="content">
                            ${questionDO.getContent()!}
                        </div>
                    </div>
                    <#if questionDO.type==1>
                        <div class="panelBar">
                            <ul class="toolBar">
                                <li style="float: left">
                                    <label>选择答案：</label>
                                    <input name="result${questionDO_index}" type="radio" value="A${questionDO.judgeAnswer!}${questionDO.id!}">A</input>
                                    <input name="result${questionDO_index}" type="radio" value="B${questionDO.judgeAnswer!}${questionDO.id!}">B</input>
                                    <input name="result${questionDO_index}" type="radio" value="C${questionDO.judgeAnswer!}${questionDO.id!}">C</input>
                                    <input name="result${questionDO_index}" type="radio" value="D${questionDO.judgeAnswer!}${questionDO.id!}">D</input>
                                </li>
                            </ul>
                        </div>
                    <#else>
                        <div class="panelContent">
                            <ul >
                                <li>
                                    <input type="hidden" id="id${questionDO_index}" name="id${questionDO_index}" value="${questionDO.id!}"/>
                                    <#--<input type="text" style="height: 40px;width: 800px">-->
                                    <textarea name="answer${questionDO_index}" id="answer${questionDO_index}" style="width: 800px;height: 80px;"></textarea>
                                </li>
                            </ul>
                        </div>

                    </#if>

                    <div style="display:none;" class="panelContent" >
                        <div id="answer">
                            ${questionDO.getAnswer()!}
                        </div >
                    </div>
                </#list>


            </#if>
        </div>
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
<script>
    $(function () {

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

</script>
<script>
    function submitAnswer() {
        document.forms["xztAnswerForm"].action = "${ctx}/syncTest/submitAnswer";
        document.forms["xztAnswerForm"].submit();
    }

    function seeResult() {
        $.getJSON("${ctx}/syncTest/backResult",function () {
            //alert("加入成功");
        });
    }
</script>
