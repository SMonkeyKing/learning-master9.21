<script>
    /*function doAdd()
    {
        $("#content").val(editor1.txt.html());
        $("#answer").val(editor2.txt.html());
    }*/
</script>
<div class="pageContent">
    <form method="post" enctype="multipart/form-data" action="${ctx}/question/update" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);" >
        <div class="pageFormContent" layoutH="60">
            <fieldset>
                <legend>修改试题</legend>
                <input type="hidden" name="id" value="${questionDO.id!}"/>
                <dl class="nowrap">
                    <dt>题目类型：</dt>
                    <dd>
                        <select name="type">
                            <option value ="1">选择题</option>
                            <option value ="2">填空题</option>
                            <option value ="3">计算题</option>
                        </select>
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>题目描述：</dt>
                    <dd>
                        <input  type="hidden" id="content" name="content"/>
                        <div id="editor1" name="editorContent">

                        </div>
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>选择题答案：</dt>
                    <dd>
                        <input  type="text" id="judgeAnswer" name="judgeAnswer" value="${questionDO.judgeAnswer!}"/>
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>题目答案及解析：</dt>
                    <dd>
                        <input  type="hidden" id="answer" name="answer" />
                        <div id="editor2" name="editorAnswer">

                        </div>
                    </dd>
                </dl>
                <#--<dl class="nowrap">
                    <dt>课件名称：</dt>
                    <dd>
                        <input type="text" name="name" class="required" value="${courseWare.name!}"/>
                    </dd>
                </dl>-->
            </fieldset>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit" onclick="doAdd()">保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
    </form>
</div>
<script type="text/javascript">
    var E = window.wangEditor;
    var editor1 = new E('#editor1');
    editor1.create();
    editor1.txt.html('${questionDO.content!}');
    editor1.customConfig.uploadImgServer = '${ctx}/question/uploadImg';
    editor1.customConfig.uploadFileName = 'myimgfile';
    editor1.customConfig.uploadImgHooks = {
        customInsert: function (insertImg, result, editor1) {
            var url = result.url;
            insertImg(url);
        }
    }

    //alert(${questionDO.content!});
    var editor2 = new E('#editor2');
    editor2.create();
    editor2.txt.html('${questionDO.answer!}');
    // 隐藏“网络图片”tab
    //editor2.customConfig.showLinkImg = false;
    editor2.customConfig.uploadImgServer = '${ctx}/question/uploadImg';
    editor2.customConfig.uploadFileName = 'myimgfile';
    editor2.customConfig.uploadImgHooks = {
        customInsert: function (insertImg, result, editor2) {
            var url = result.url;
            insertImg(url);
        }
    }

</script>
