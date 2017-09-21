<div class="pageContent">
    <form method="post" enctype="multipart/form-data" action="${ctx}/syncTest/save" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);" >
        <div class="pageFormContent" layoutH="60">
            <fieldset>
                <legend>添加同步测试</legend>
                <dl class="nowrap">
                    <dt>上传测试题目：</dt>
                    <dd>
                        <input type="hidden" value="${learningPlanId}" name="learningPlanId"/>
                        <#--<input id="addFile" type="button" value="添加文件">-->
                        <div id="fileDiv">
                            <input type="file" name="file1" />
                        </div>
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>答案及解析：</dt>
                    <div id="fileDiv">
                        <input type="file" name="file2" />
                    </div>
                </dl>
                <dl class="nowrap">
                    <dt>选择题答案：</dt>
                    <dd>
                        <textarea name="xzt_answer"></textarea>
                    </dd>
                </dl>
            </fieldset>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit">保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
    </form>
</div>

<script type="text/javascript">

    $(function () {
        $("#addFile").click(function () {
            $("#fileDiv").append("<input type='file' name='file'/>");
        })

    });
</script>
