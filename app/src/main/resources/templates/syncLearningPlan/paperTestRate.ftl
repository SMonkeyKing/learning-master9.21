<div class="pageContent">

    <#--<div id="myPdf" style="float: left;">

    </div>
    <div style="float:right;">
        <span>edwqeqw</span>
    </div>-->
        <div style="float: left;width: 65%;height: 100%;" layoutH="25">
        <object width="100%" height="100%" data="${syncTestDO.url!}" type="application/pdf">
            <p><b>Example fallback content</b>: This browser does not support PDFs. Please download the PDF to view it: <a href="/pdf/sample-3pp.pdf">Download PDF</a>.</p>
        </object>
        </div>
    <div style="float: right;width: 35%;height: 100%;"  layoutH="25">
            <div class="pageFormContent" >
                <fieldset>
                <legend>选择题正确率：</legend>
                <dl class="nowrap">
                    <#if paperTestQuestionDOS??&&paperTestQuestionDOS?size gt 0>
                        <#list paperTestQuestionDOS as paperTestQuestionDO>
                        <dt>第${paperTestQuestionDO_index+1}题： ${paperTestQuestionDO.correctRate!}</dt>
                        <#--<dd>-->
                            <#--<div>-->
                                <#--${paperTestQuestionDO.correctRate!}-->
                                <#--&lt;#&ndash;<label style="color: red">填写样例：ABAA（不要有空格）</label>&ndash;&gt;-->
                            <#--</div>-->
                        <#--</dd>-->
                        </#list>
                    </#if>

                </dl>
                </fieldset>
                <#--<dl class="nowrap">
                    <dt>填空计算题答案：</dt>
                    <dd>
                        <div>
                            <textarea style="height: 200px;width: 320px" name="tkjsAnswer"></textarea>
                        </div>
                    </dd>
                </dl>-->
    </div>
</div>

