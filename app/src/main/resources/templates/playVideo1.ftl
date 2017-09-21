<!DOCTYPE html>
<html>
    <head>
        <title></title>
        <meta charset="utf-8">
        <meta name="viewport" content="target-densitydpi=device-dpi, width=device-width, initial-scale=1, user-scalable=yes, minimum-scale=1.0, maximum-scale=3.0">
        <link rel="stylesheet" href="css/ui-btn.css">
        <link rel="stylesheet" href="css/ui-base.css">
        <link rel="stylesheet" href="css/ui-box.css">
        <link rel="stylesheet" href="css/ui-color.css">
        <link rel="stylesheet" href="css/ui-res.css">
        <script src="js/zy_control1.js"></script>
        <script src="js/zy_click.js"></script>
    </head>
    <body class="um-vp c-wh" ontouchstart>
    <div id="page_0" class="up ub ub-ver" tabindex="0">
        <!--header开始-->
        <div id="header" class="uh c-org c-m1 t-wh ub">
            <div id="video-wrapper" style="width:100%; height=100%">
                <video poster="http://media.w3.org/2010/05/sintel/poster.png"  controls="controls" controls="controls"   preload="metadata" loop="loop"autoplay="autoplay" width="  100%" height=" "name="media">
                    <source src="${videoUrl}" type="video/mp4;codecs="theora,vorbis"" media="screen" />
                    <source src="${videoUrl}" type="video/mp4" />
                </video>
            </div>
        </div>
        <!--header结束--><!--content开始-->
        <div id="content" class="ub-f1 tx-l t-bla ub-img6 res10">

        </div>
        <!--content结束--><!--footer开始-->
        <div id="footer" class="uf c-m2 c-bla t-wh">
            <div class="umw4" ontouchstart="zy_touch()" onclick="">
                <!--按钮开始-->
                <div class="btn btn-l btn-a ub ub-ac " onclick="goback();">
                    <div class="ulim">返回</div>
                </div>
                <!--按钮结束-->
            </div>
        </div>
        <!--footer结束-->
    </div>
    </body>
    <script>
        zy_init();
        window.uexOnload = function(type){
            if (!type) {
                zy_con("content", "page5_content.html", 0, $("header").offsetHeight);
            }
            window.onorientationchange=window.onresize=function()
            {
                zy_resize("content",0,$("header").offsetHeight);
            }
        }
        function goback(){
            uexWindow.close('-1');
        }
        // <script>
        // document.getElementById('video-wrapper').webkitRequestFullscreen();

    </script>
</html>
