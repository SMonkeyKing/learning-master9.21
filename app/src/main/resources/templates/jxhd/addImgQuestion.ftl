<style>
    .myEditor p{
        height: auto;
        width: 730px;
        margin: 0px 0px;
        padding: 2px 0px;
    }
</style>
<script>
    function doAdd()
    {
        $("#question").val(editor1.txt.html());
        //alert(editor1.txt.html());
        $("#answer").val(editor2.txt.html());
    }
</script>
<div class="pageContent">
    <form method="post" enctype="multipart/form-data" action="${ctx}/jxhd/img/save" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);" >
        <div class="pageFormContent" layoutH="55">
            <fieldset>
                <legend>添加题目</legend>
                <dl class="nowrap">
                    <dt>题目截图：</dt>
                    <dd>
                        <input  type="hidden" id="question" name="question" />
                            <div class="myEditor" id="editor1" name="editorContent" style="width: 800px;">
                        </div>
                    </dd>
                </dl>
            </fieldset>
        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit" onclick="doAdd()" >保存</button></div></div></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
    </form>
</div>

<script type="text/javascript">

    //var val = obj.options[index].value;
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
</script>
