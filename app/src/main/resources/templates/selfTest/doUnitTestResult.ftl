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


</form>

<#--<div class="pageHeader">
    <form id="questionForm" onsubmit="return navTabSearch(this);" action="${ctx}/syncTest/question/config" class="required-validate" method="post">
        <div class="searchBar">

            <table class="searchContent">
                <tr>
                    <td>题目类型：</td>
                    <input type="hidden" name="listSize" value="${listSize!}">
                    <input type="hidden" name="typeid" value="${typeid!}">
                    <input type="hidden" name="type"  id="type">
                    <td><button id="btn1" type="submit" value="1">选择题</button></td>
                    <td><button id="btn2" type="submit" value="2">填空题</button></td>
                    <td><button id="btn3" type="submit" value="3">计算题</button></td>
                </tr>
            </table>
        </div>
    </form>
</div>-->
<div class="pageContent">
    <form id="xztAnswerForm">
        <div class="accordionContent" layoutH="5">
        <#if syncTestFromTKDOS??&&syncTestFromTKDOS?size gt 0>
            <#list syncTestFromTKDOS as syncTestFromTKDO>
                <div class="panelContent" >
                    <div id="content">
                    ${syncTestFromTKDO.questionContent!}
                    </div>
                </div>
                <#--<#if syncTestFromTKDO.questionType == 1>
                    <div class="panelBar">
                        <ul class="toolBar">
                            <li style="float: right">
                                <label style="font-weight:blod;">你的答案：</label>
                                <label>${syncTestFromTKDO.stuAns!}</label>
                                <#if tbxaQuestionStudentAnsDO.correct gt 0>
                                    <label>正确</label>
                                <#else>
                                    <label style="color: red">错误</label>
                                    <label style="font-weight:blod;color: red">正确答案：${tbxaQuestionStudentAnsDO.correctAnswer!}</label>
                                </#if>
                            </li>

                        </ul>
                    </div>
                <#else >-->
                    <div class="panelContent">
                        <div>
                            <br/>
                            <label style="font-weight:blod;">你的答案：</label>
                                ${syncTestFromTKDO.stuAns!}
                            <br/>
                            <br/>
                        </div>
                        <div>
                            <label style="font-weight:blod;">参考答案：</label>
                            ${syncTestFromTKDO.questionAnswer!}
                            <br/>
                            <br/>
                        </div>
                    </div>
                <#--</#if>-->

            </#list>


        </#if>
        <#--<div class="pageFormContent" layoutH="138">-->
            <#--<#if tbxaQuestionStudentAnsDOs??&&tbxaQuestionStudentAnsDOs?size gt 0>
                <#list tbxaQuestionStudentAnsDOs as tbxaQuestionStudentAnsDO>
                    <div class="panelContent" >
                        <div id="content">
                            ${tbxaQuestionStudentAnsDO.questionContent!}
                        </div>
                    </div>
                    <#if tbxaQuestionStudentAnsDO.questionType == 1>
                        <div class="panelBar">
                            <ul class="toolBar">
                                <li style="float: right">
                                    <label style="font-weight:blod;">你的答案：</label>
                                    <label>${tbxaQuestionStudentAnsDO.answer!}</label>
                                    <#if tbxaQuestionStudentAnsDO.correct gt 0>
                                        <label>正确</label>
                                    <#else>
                                        <label style="color: red">错误</label>
                                        <label style="font-weight:blod;color: red">正确答案：${tbxaQuestionStudentAnsDO.correctAnswer!}</label>
                                    </#if>
                                </li>

                            </ul>
                        </div>
                    <#else >
                        <div class="panelContent">
                            <div>
                                <br/>
                                <label style="font-weight:blod;">你的答案：</label>
                                ${tbxaQuestionStudentAnsDO.answer!}
                                <br/>
                                <br/>
                            </div>
                            <div>
                                <label style="font-weight:blod;">参考答案：</label>
                                ${tbxaQuestionStudentAnsDO.correctAnswer!}
                            </div>
                        </div>
                    </#if>

                </#list>


            </#if>-->
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
    $(function(){

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
        document.forms["xztAnswerForm"].submit();
    }
</script>
