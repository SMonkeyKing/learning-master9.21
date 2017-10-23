<div class="pageContent">
    <form method="post" enctype="multipart/form-data" action="${ctx}/courseWare/save" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);" >
        <div class="pageFormContent" layoutH="55">
            <fieldset>
                <legend>添加课件</legend>
                <dl class="nowrap">
                    <dt>上传课件：</dt>
                    <dd>
                        <input type="hidden" value="${typeid}" name="typeid"/>
                        <#--<input id="addFile" type="button" value="添加文件">-->
                        <div id="fileDiv">
                            <input type="file" name="file" multiple="multiple"/>
                        </div>
                        <label style="color: red;width: auto">文档请上传.doc,.docx,.ppt,.pptx格式文件</label>
                    </dd>

                </dl>
                <dl class="nowrap">
                    <dt>上传者姓名：</dt>
                    <dd>

                        <div>
                            <input type="text" name="uploadName"/>
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

//    $(function () {
//        $("#addFile").click(function () {
//            $("#fileDiv").append("<input type='file' name='file'/>");
//        })
//
//    });
</script>
