<div class="pageContent">
    <#--<form method="post" enctype="multipart/form-data" action="${ctx}/syncLearningPlan/save" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);" >-->
        <div class="pageFormContent" layoutH="60">
            <fieldset>
                <legend>答题结果</legend>
                <#--<dl class="nowrap">
                    <dt>选择题正确率：</dt>
                    <dd>
                        <div >
                            <input type="text" name="correctRate" readonly="true" value="${correctRate}"/>
                        </div>
                    </dd>
                </dl>-->
                <#if paperTestQuestionDOS??&&paperTestQuestionDOS?size gt 0>
                    <#list paperTestQuestionDOS as paperTestQuestionDO>
                        <dl class="nowrap">
                            <dt>第${paperTestQuestionDO_index+1}题:</dt>
                            <dd>
                                <div >
                                    <input type="text" name="" readonly="true" value="${paperTestQuestionDO.stuAns!}"/>
                                    &nbsp;&nbsp;&nbsp;
                                    正确答案：${paperTestQuestionDO.answer!}
                                </div>
                            </dd>
                        </dl>
                    </#list>
                </#if>
                <dl class="nowrap">
                    <dt>填空计算题答案：</dt>
                    <dd>
                        <div >
                            <textarea style="height: 300px;width: 800px;"readonly="true" value="${tkjsAnswer}">${tkjsAnswer}</textarea>
                            <#--<input type="text" style="height: 300px" name="" readonly="true" value="${tkjsAnswer}"/>-->
                        </div>
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>参考答案：</dt>
                    <dd>
                        <div >
                            <#--<input type="button" name="" value="点击查看" onclick="javascript:window.location.href='${answerUrl}' var open_blank_default = ture;"/>-->
                            <input type="button" name="" value="点击查看" onclick="window.open('${ctx}/office?path=${answerUrl}')"/>
                        </div>
                    </dd>
                </dl>
                <#--<dl class="nowrap">
                    <dt>详细答案：</dt>
                    <dd>
                        <div >
                            <label name="">${answerUrl}</label>
                            &lt;#&ndash;<input type="label" name="" value="${answerUrl}"/>&ndash;&gt;
                        </div>
                    </dd>
                </dl>-->
            </fieldset>
        </div>
</div>

