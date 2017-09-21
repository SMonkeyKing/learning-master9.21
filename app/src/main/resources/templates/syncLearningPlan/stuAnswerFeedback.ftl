<div class="pageContent">
    <#--<form method="post" enctype="multipart/form-data" action="${ctx}/syncLearningPlan/save" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);" >-->
        <div class="pageFormContent" layoutH="60">
            <fieldset>
                <legend>答题结果</legend>
                <dl class="nowrap">
                    <dt>选择题正确率：</dt>
                    <dd>
                        <div >
                            <input type="text" name="correctRate" readonly="true" value="${correctRate}"/>
                        </div>
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>你的答案：</dt>
                    <dd>
                        <div >
                            <input type="text" name="" readonly="true" value="${xztAnswer}"/>
                        </div>
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>参考答案：</dt>
                    <dd>
                        <div >
                            <input type="text" name="" readonly="true" value="${correctXztAnswer}"/>
                        </div>
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>详细答案：</dt>
                    <dd>
                        <div >
                            <label name="">${answerUrl}</label>
                            <#--<input type="label" name="" value="${answerUrl}"/>-->
                        </div>
                    </dd>
                </dl>
            </fieldset>

        </div>
        <#--<div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>-->
    <#--</form>-->
</div>

