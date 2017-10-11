<script type="text/javascript">
    var E = window.wangEditor;
    var editor1 = new E('#editor1');
    editor1.customConfig.uploadImgServer = '${ctx}/question/uploadImg';
    editor1.customConfig.uploadFileName = 'myimgfile';
    //editor1.customConfig.pasteFilterStyle = true;
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
    editor2.customConfig.uploadImgServer = '${ctx}/question/uploadImg';
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


<script>
    /*var editor1;
    KindEditor.ready(function(K) {
        editor1 = K.create('textarea[name="realcontent"]', {
            resizeType : 1,
            allowPreviewEmoticons : true,
            allowImageUpload : true,
            //allowFileManager : true,
            items : [
                'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                'insertunorderedlist', '|', 'emoticons', 'image', 'link'],
            uploadJson : '${ctx}/question/uploadImg',
            /!*afterBlur: function () {
                //alert(editor1.text());
                $("#myContent").val(editor1.text()); //this.sync();   获取文本值
            },//数据同步*!/
        });
    });*/
    /*var editor2;
    KindEditor.ready(function(K) {
        editor2 = K.create('textarea[name="realAnswer"]', {
            resizeType : 1,
            allowPreviewEmoticons : true,
            allowImageUpload : true,
            allowFileManager : true,
            items : [
                'fontname', 'fontsize', '|', 'forecolor', 'hilitecolor', 'bold', 'italic', 'underline',
                'removeformat', '|', 'justifyleft', 'justifycenter', 'justifyright', 'insertorderedlist',
                'insertunorderedlist', '|', 'emoticons', 'image', 'link', 'preview','undo','redo','fullscreen','media'],
            uploadJson : '/question/uploadImg',
            afterBlur: function () {
                //alert(editor2.text());
                $("#myAnswer").val(editor2.text()); //this.sync();   获取文本值
            },//数据同步
        });
    });*/
    function doAdd()
    {
        $("#question").val(editor1.txt.html());
        $("#answer").val(editor2.txt.html());
    }
</script>
<div class="pageContent">
    <form method="post" enctype="multipart/form-data" action="${ctx}/question/save" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);" >
        <div class="pageFormContent" layoutH="60">
            <fieldset>
                <legend>添加题目</legend>
                <dl class="nowrap">
                    <input type="hidden" name="typeid" value="${typeid}" />
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
                        <div id="editor1" name="editorContent" style="width: 800px;">

                        </div>
                        <#--<div>
                            <textarea  name="realcontent" style="width:650px;height:500px;visibility: hidden;"></textarea>
                            <input type="hidden" id="myContent" name="myContent"/>
                        </div>-->
                        <#--<div>
                            <textarea id="content" class="editor" style="width:650px;height:500px;"></textarea>
                        </div>-->
                            <#--<div class="unit">
                                <textarea class="editor" name="description" rows="8" cols="100"></textarea>
                            </div>-->
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
                        <#--<div>
                            <textarea id="answer" class="editor" style="width:650px;height:500px;"></textarea>
                        </div>-->
                        <input  type="hidden" id="answer" name="answer" />
                        <div id="editor2" name="editorAnswer" style="width: 800px;">

                        </div>
                            <#--<div >
                                <textarea  name="realAnswer" style="width:650px;height:500px;"></textarea>
                                <input type="hidden" id="myAnswer" name="myAnswer"/>
                            </div>-->
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


