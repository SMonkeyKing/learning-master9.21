<script>
    function doAdd()
    {
        $("#question").val(editor1.txt.html());
        //alert(editor1.txt.html());
        $("#answer").val(editor2.txt.html());
    }
</script>
<div class="pageContent">
    <form method="post" enctype="multipart/form-data" action="${ctx}/jxhd/question/save" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);" >
        <div class="pageFormContent" layoutH="60">
            <fieldset>
                <legend>添加题目</legend>
                <dl class="nowrap">
                    <dt>题目类型：</dt>
                    <dd>
                        <select name="type">
                            <option value ="1">选择题</option>
                            <option value ="2">填空题</option>
                            <option value ="3">综合题</option>
                        </select>
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>题目描述：</dt>
                    <dd>
                        <input  type="hidden" id="question" name="question" />
                        <div id="editor1" name="editorContent">

                        </div>

                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>选择题答案：</dt>
                    <dd>
                        <input  type="text" id="xztAnswer" name="xztAnswer" />
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
    editor1.customConfig.uploadImgServer = '${ctx}/jxhd/uploadImg';
    editor1.customConfig.uploadFileName = 'myimgfile';
    editor1.customConfig.uploadImgHooks = {
        customInsert: function (insertImg, result, editor1) {
            var url = result.url;
            insertImg(url);
        }

    }
    // 隐藏“网络图片”tab
    //editor1.customConfig.showLinkImg = false;
    editor1.create();
    var editor2 = new E('#editor2');
    editor2.customConfig.uploadImgServer = '${ctx}/jxhd/uploadImg';
    editor2.customConfig.uploadFileName = 'myimgfile';
    editor2.customConfig.uploadImgHooks = {
        customInsert: function (insertImg, result, editor2) {
            var url = result.url;
            insertImg(url);
        }

    }
    // 隐藏“网络图片”tab
    //editor2.customConfig.showLinkImg = false;
    editor2.create();
</script>
