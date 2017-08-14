<script>
    function doAdd()
    {
        $("#content").val(editor1.txt.html());
        //alert(editor1.txt.html());
        $("#answer").val(editor2.txt.html());
        /*$.ajax({
            url:'${ctx}/question/upload'
            success:function (data) {
                $("#content").val(data);
            }
        });*/
    }
</script>
<div class="pageContent">
    <form method="post" enctype="multipart/form-data" action="${ctx}/question/save" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);" >
        <div class="pageFormContent" layoutH="60">
            <fieldset>
                <legend>添加题目</legend>
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
                        <input  type="hidden" id="content" name="content" />
                        <div id="editor1" name="editorContent">

                        </div>
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>题目答案：</dt>
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
    var editor2 = new E('#editor2');
    // 或者 var editor = new E( document.getElementById('#editor') )
    //editor1.customConfig.uploadImgShowBase64 = true;
    editor2.customConfig.uploadImgShowBase64 = true;
    editor1.customConfig.uploadImgServer = '${ctx}/question/uploadImg';
    editor1.customConfig.uploadFileName = 'myimgfile';
    editor1.customConfig.uploadImgHooks = {
        customInsert: function (insertImg, result, editor1) {
            // 图片上传并返回结果，自定义插入图片的事件（而不是编辑器自动插入图片！！！）
            // insertImg 是插入图片的函数，editor 是编辑器对象，result 是服务器端返回的结果

            // 举例：假如上传图片成功后，服务器端返回的是 {url:'....'} 这种格式，即可这样插入图片：
            var url = result.url;
            insertImg(url);

            // result 必须是一个 JSON 格式字符串！！！否则报错
        }

    }

    //editor.customConfig.uploadImgServer = '/upload';
    // 隐藏“网络图片”tab
    //editor.customConfig.showLinkImg = false;
    editor1.create();
    editor2.create();
    //var content = editor.txt.html();

    //alert(editor1.txt.html());
    //$("#content").val($('#editor').val());
</script>
