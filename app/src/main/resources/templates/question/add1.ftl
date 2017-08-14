<script src="${c_static}/dwz/js/kindeditor-4.1.10/kindeditor-all-min.js" type="text/javascript"></script>
<script src="${c_static}/dwz/js/kindeditor-4.1.10/lang/zh_CN.js" type="text/javascript"></script>
<script>
    /*function doAdd()
    {
        $("#content").val(editor1.txt.html());
        alert(editor1.txt.html());
        $("#answer").val(editor2.txt.html());
    }*/
    itemAddEditor = TAOTAO.createEditor("#content");
    //初始化类目选择和图片上传器
    TAOTAO.init({fun:function(node){
        //TAOTAO.changeItemParam(node, "itemAddForm");
    }});

    var TT = TAOTAO = {
        // 编辑器参数
        kingEditorParams : {
            //指定上传文件参数名称
            filePostName : "uploadFile",
            //指定上传文件请求的url。
            uploadJson : '/pic/upload',
            //上传类型，分别为image、flash、media、file
            dir : "image"
        },

        init : function(data){
            // 初始化图片上传组件
            this.initPicUpload(data);
            // 初始化选择类目组件
            //this.initItemCat(data);
        },
        // 初始化图片上传组件
        initPicUpload : function(data){

                //给“上传图片按钮”绑定click事件
                $(e).click(function(){
                    var form = $(this).parentsUntil("form").parent("form");
                    //打开图片上传窗口
                    KindEditor.editor(TT.kingEditorParams).loadPlugin('multiimage',function(){
                        var editor = this;
                        editor.plugin.multiImageDialog({
                            clickFn : function(urlList) {
                                var imgArray = [];
                                KindEditor.each(urlList, function(i, data) {
                                    imgArray.push(data.url);
                                    form.find(".pics ul").append("<li><a href='"+data.url+"' target='_blank'><img src='"+data.url+"' width='80' height='50' /></a></li>");
                                });
                                form.find("[name=image]").val(imgArray.join(","));
                                editor.hideDialog();
                            }
                        });
                    });
                });
            });
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
                        <textarea id="content" ></textarea>
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>题目答案：</dt>
                    <dd>

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
    editor1.customConfig.uploadImgServer = '/upload';
    //editor.customConfig.uploadImgServer = '/upload';
    // 隐藏“网络图片”tab
    //editor.customConfig.showLinkImg = false;
    editor1.create();
    editor2.create();
    //var content = editor.txt.html();

    //alert(editor1.txt.html());
    //$("#content").val($('#editor').val());
</script>
