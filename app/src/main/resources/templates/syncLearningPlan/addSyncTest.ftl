<div class="pageContent">
    <form method="post" enctype="multipart/form-data" action="${ctx}/syncTest/save" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);" >
        <div class="pageFormContent" layoutH="55">
            <fieldset id="field">
                <legend>添加试卷</legend>
                <dl class="nowrap">
                    <dt>上传测试题目：</dt>
                    <dd style="width: 500px">
                        <input type="hidden" value="${typeid}" name="typeid"/>
                        <#--<input id="addFile" type="button" value="添加文件">-->
                        <div id="fileDiv">
                            <input type="file" name="file1" />
                        </div>
                        <label style="color: red">请上传.pdf格式文件</label>
                    </dd>


                </dl>
                <dl class="nowrap">
                    <dt>答案及解析：</dt>
                    <dd style="width: 500px">
                        <div id="fileDiv">
                            <input type="file" name="file2" />
                        </div>
                        <label style="color: red;width: auto">请上传.doc,.docx格式文件</label>
                    </dd>

                </dl>

                <dl class="nowrap">
                    <dt>选择题数量：</dt>
                    <dd >
                        <input type="text" name="xzt_num" id="xzt_num"/>
                        &nbsp;&nbsp;&nbsp;
                        <input type="button" value="生成答题卡" onclick="produceCard()">
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

    
    function  produceCard() {
        var num = $("#xzt_num").val();
        //多少道选择题就增加多少了输入框
        //alert(num);
        var html="";
        for(var i=1;i<=num;i++)
        {

            html+='<dl class="nowrap">\n';
            html+='<dt>第'+i+'题：</dt>\n';
            html+='<dd>\n';
            html+='<input type="text" name="xzt_num'+i+'"/>\n';
            html+='</dd>\n';
            html+='</dl>\n';
        }
        $("#field").append(html);
    }
</script>
