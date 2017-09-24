<div class="pageContent">
    <form method="post" enctype="multipart/form-data" action="${ctx}/syncLearningPlan/save" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);" >
        <div class="pageFormContent" layoutH="60">
            <fieldset>
                <legend>添加学案</legend>
                <dl class="nowrap">
                    <dt>上传学案：</dt>
                    <dd>
                        <input type="hidden" value="${typeid}" name="typeid"/>
                        <#--<input id="addFile" type="button" value="添加文件">-->
                        <div id="fileDiv">
                            <input type="file" name="file" multiple="multiple"/>
                        </div>
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
