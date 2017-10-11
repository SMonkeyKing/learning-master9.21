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
                        <select name="type" id="selectType">
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
                        <#--<textarea id="question" name="question"  style="height: 200px;width: 800px;"></textarea>-->
                            <#--<input type="text" id="question" name="question" style="height: 30px;width: 800px;">-->
                    </dd>
                </dl>
                <dl class="nowrap" id="optionA">
                    <dt>选项A：</dt>
                    <dd>
                        <input  type="text" id="answerA" name="answerA" style="height: 30px;width: 800px" />
                    </dd>
                </dl>

                <dl class="nowrap" id="optionB">
                    <dt>选项B：</dt>
                    <dd>
                        <input  type="text" id="answerB" name="answerB" style="height: 30px;width: 800px" />
                    </dd>
                </dl>
                <dl class="nowrap" id="optionC">
                    <dt>选项C：</dt>
                    <dd>
                        <input  type="text" id="answerC" name="answerC" style="height: 30px;width: 800px" />
                    </dd>
                </dl>
                <dl class="nowrap" id="optionD">
                    <dt>选项D：</dt>
                    <dd>
                        <input  type="text" id="answerD" name="answerD" style="height: 30px;width: 800px" />
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>答案：</dt>
                    <dd>
                        <input  type="text" id="correctAnswer" name="correctAnswer" style="height: 30px;width: 800px" />
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>题目解析：</dt>
                    <dd>
                        <input  type="hidden" id="answer" name="answer" />
                        <div id="editor2" name="editorAnswer" style="width: 800px;">

                        </div>
                        <#--<textarea id="answer" name="answer" style="height: 200px;width: 800px;"></textarea>-->
                        <#--<input type="text" id="answer" name="answer" style="height: 30px;width: 800px;">-->
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
    $(function () {
        $("#selectType").click(function () {
            var obj=document.getElementById('selectType');
            var index=obj.selectedIndex; //序号，取当前选中选项的序号
            var val = obj.options[index].value;
            //alert(1111);
            //alert(val);
            console.log(val);
            if (val!="1") {
                //alert(2);
                document.getElementById('optionA').style.display="none";
                document.getElementById('optionB').style.display="none";
                document.getElementById('optionC').style.display="none";
                document.getElementById('optionD').style.display="none";
            }else{
                document.getElementById('optionA').style.display="inline";
                document.getElementById('optionB').style.display="inline";
                document.getElementById('optionC').style.display="inline";
                document.getElementById('optionD').style.display="inline";
            }
            /*var index = this.selectedIndex;
            if(index>1)
            {
                document.getElementById('optionA').style.visibility="hidden";
            }*/
        });
    })

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
