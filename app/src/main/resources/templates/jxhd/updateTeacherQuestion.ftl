<script>
    jQuery(document).ready(function()
    {
        $("#type").val(${jxhdQuestionDO.type!});
    });
</script>
<div class="pageContent">
    <form method="post" enctype="multipart/form-data" action="${ctx}/jxhd/question/update" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);" >
        <div class="pageFormContent" layoutH="60">
            <fieldset>
                <legend>添加题目</legend>
                <input type="hidden" name="qid" value="${jxhdQuestionDO.id!}"/>
                <dl class="nowrap">
                    <dt>题目类型：</dt>
                    <dd>
                        <select name="type" id="type">
                            <option value ="1">选择题</option>
                            <option value ="2">填空题</option>
                            <option value ="3">综合题</option>
                        </select>
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>题目描述：</dt>
                    <dd>
                        <textarea id="topic" name="topic"  style="height: 200px;width: 800px;">${jxhdQuestionDO.topic!}</textarea>
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>选项A：</dt>
                    <dd>
                        <input  type="text" id="answerA" name="answerA" style="height: 30px;width: 800px" value="${jxhdQuestionDO.answerA!}"/>
                    </dd>
                </dl>

                <dl class="nowrap">
                    <dt>选项B：</dt>
                    <dd>
                        <input  type="text" id="answerB" name="answerB" style="height: 30px;width: 800px" value="${jxhdQuestionDO.answerB!}"/>
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>选项C：</dt>
                    <dd>
                        <input  type="text" id="answerC" name="answerC" style="height: 30px;width: 800px" value="${jxhdQuestionDO.answerC!}"/>
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>选项D：</dt>
                    <dd>
                        <input  type="text" id="answerD" name="answerD" style="height: 30px;width: 800px" value="${jxhdQuestionDO.answerD!}"/>
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>答案：</dt>
                    <dd>
                        <input  type="text" id="correctAnswer" name="correctAnswer" style="height: 30px;width: 800px" value="${jxhdQuestionDO.correctAnswer!}"/>
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>题目解析：</dt>
                    <dd>
                        <textarea id="parsing" name="parsing" style="height: 200px;width: 800px;">${jxhdQuestionDO.parsing!}</textarea>
                        <#--<input type="text" id="answer" name="answer" style="height: 30px;width: 800px;">-->
                    </dd>
                </dl>
            </fieldset>

        </div>
        <div class="formBar">
            <ul>
                <li><div class="buttonActive"><div class="buttonContent"><button type="submit" >保存</button></div></div></li>
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
