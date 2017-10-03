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
<form id="pagerForm" method="post" action="${ctx}/unitTest/list">
    <#--<input type="hidden" name="pageNum" value="${page.currentPage!}"/>
    <input type="hidden" name="numPerPage" value="${page.numPerPage!}"/>-->
    <input type="hidden" name="orderBy" value="desc">
    <input type="hidden" name="orderField" value="lastUpdated">
    <#--<input type="hidden" name="name" value="${syncTestFromTKDO.subject!}">-->
</form>

<#--<div class="pageHeader">
</div>-->
<div class="pageContent">
    <form id="xztAnswerForm" method="post" action="${ctx}/unitTest/submitAnswer" class="pageForm required-validate" onsubmit="return navTabSearch(this);">
        <div class="formBar">
            <ul>
                <li style="text-align: center"><div class="buttonActive"><div class="buttonContent"><button  type="submit" ">提交答案</button></div></div></li>
                <li><a class="buttonActive"  id = "gen" href="${ctx}/unitTest/backResult?typeid=${typeid}" target="navTab"><span>查看测试结果</span></a></li>
            </ul>
        </div>
        <div class="accordionContent" layoutH="47">
            <input type="hidden" name="typeid" value="${typeid!}">
            <#if syncTestFromTKDOs??&&syncTestFromTKDOs?size gt 0>
                <#list syncTestFromTKDOs as syncTestFromTKDO>
                    <div class="panelContent" >
                        <div id="content">
                            ${syncTestFromTKDO.getQuestionContent()!}
                        </div>
                    </div>
                    <#if syncTestFromTKDO.questionType==1>
                        <div class="panelBar">
                            <ul class="toolBar">
                                <li style="float: left">
                                    <label>选择答案：</label>
                                    <input name="answer${syncTestFromTKDO_index}" type="radio" value="A${syncTestFromTKDO.questionAnswer!}${syncTestFromTKDO.questionId!}">A</input>
                                    <input name="answer${syncTestFromTKDO_index}" type="radio" value="B${syncTestFromTKDO.questionAnswer!}${syncTestFromTKDO.questionId!}">B</input>
                                    <input name="answer${syncTestFromTKDO_index}" type="radio" value="C${syncTestFromTKDO.questionAnswer!}${syncTestFromTKDO.questionId!}">C</input>
                                    <input name="answer${syncTestFromTKDO_index}" type="radio" value="D${syncTestFromTKDO.questionAnswer!}${syncTestFromTKDO.questionId!}">D</input>
                                </li>
                            </ul>
                        </div>
                    <#else>
                        <div class="panelContent">
                            <ul >
                                <li>
                                    <input type="hidden" id="id${syncTestFromTKDO_index}" name="id${syncTestFromTKDO_index}" value="${syncTestFromTKDO.questionId!}"/>
                                    <#--<input type="text" style="height: 40px;width: 800px">-->
                                    <textarea name="answer1${syncTestFromTKDO_index}" id="answer${syncTestFromTKDO_index}" style="width: 800px;height: 80px;"></textarea>
                                </li>
                            </ul>
                        </div>

                    </#if>
                    <#if role == 1>
                    <div class="panelBar">
                        <ul class="toolBar">
                            <li style="float: right">
                                <a class="delete"  href="${ctx}/unitTest/delete?id=${syncTestFromTKDO.id}" target="ajaxTodo" title="温馨提示：是否确认删除"  ><span id="delete">删除</span></a>
                            </li>
                        </ul>
                    </div>
                    </#if>
                    <div style="display:none;" class="panelContent" >
                        <div id="answer">
                            ${syncTestFromTKDO.questionAnswer!}
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
    /*$(function () {

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

    });*/
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
