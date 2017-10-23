<link href="${c_static}/dwz/themes/default/style.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${c_static}/dwz/themes/css/core.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${c_static}/dwz/themes/css/print.css" rel="stylesheet" type="text/css" media="print"/>
<link href="${c_static}/dwz/uploadify/css/uploadify.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${c_static}/dwz/css/default.css" rel="stylesheet" type="text/css" media="screen"/>
<link href="${c_static}/dwz/favicon.ico" rel="icon"  type="image/x-icon" />
<link href="${c_static}/dwz/favicon.ico" rel="shortcut icon" type="image/x-icon" />
<!--[if IE]>
<link href="themes/css/ieHack.css" rel="stylesheet" type="text/css" media="screen"/>

<![endif]-->
<!--[if lt IE 9]><script src="${c_static}/dwz/js/speedup.js" type="text/javascript"></script><script src="${c_static}/dwz/js/jquery-1.11.3.min.js" type="text/javascript"></script><![endif]-->
<!--[if gte IE 9]><!--><script src="${c_static}/dwz/js/jquery-2.1.4.min.js" type="text/javascript"></script><!--<![endif]-->
<!--- my97datepicker-->
<script src="${c_static}/dwz/js/My97DatePicker/WdatePicker.js"></script>

<!-- 可以用dwz.min.js替换前面全部dwz.*.js (注意：替换时下面dwz.regional.zh.js还需要引入)
-->
<script src="${c_static}/dwz/js/jquery.cookie.js" type="text/javascript"></script>
<script src="${c_static}/dwz/js/jquery.validate.js" type="text/javascript"></script>
<script src="${c_static}/dwz/bin/dwz.min.js" type="text/javascript"></script>
<script src="${c_static}/dwz/js/dwz.regional.zh.js" type="text/javascript"></script>
<script src="${c_static}/dwz/js/wangEditor.min.js"></script>
<script src="${c_static}/dwz/js/tinymce.min.js"></script>
<script src="${c_static}/dwz/js/editor/ueditor.all.min.js" type="text/javascript"></script>
<script src="${c_static}/dwz/js/editor/ueditor.config.js" type="text/javascript"></script>
<script src="${c_static}/dwz/js/pdfobject.js" type="text/javascript"></script>
<!--<script src="${c_static}/dwz/xheditor/xheditor-1.2.2.min.js" type="text/javascript"></script>-->

<script type="text/javascript">
    $(function(){
        DWZ.init("${c_static}/dwz/dwz.frag.xml", {
            loginUrl:"${c_static}/dwz/login_dialog.html", loginTitle:"登录",	// 弹出登录对话框
            //loginUrl:"login.html",	// 跳到登录页面
            statusCode:{ok:200, error:300, timeout:301}, //【可选】
            pageInfo:{pageNum:"pageNum", numPerPage:"numPerPage", orderField:"orderField", orderDirection:"orderDirection"}, //【可选】
            debug:false,	// 调试模式 【true|false】
            callback:function(){
                initEnv();
                $("#themeList").theme({themeBase:"themes"}); // themeBase 相对于index页面的主题base路径
            }
        });
    });
</script>
<script src="${c_static}/dwz/xheditor/xheditor-1.2.2.min.js" type="text/javascript"></script>
