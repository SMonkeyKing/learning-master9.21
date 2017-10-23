<div id="container" style="float:right;">
    <div id="navTab" class="tabsPage">
        <div class="tabsPageHeader">
            <div class="tabsPageHeaderContent"><!-- 显示左右控制时添加 class="tabsPageHeaderMargin" -->
                <ul class="navTab-tab">
                    <li tabid="main" class="main"><a href="javascript:"><span><span class="home_icon">我的主页</span></span></a></li>
                </ul>
            </div>
            <#--<div class="tabsLeft" style="width: 25%">left</div><!-- 禁用只需要添加一个样式 class="tabsLeft tabsLeftDisabled" &ndash;&gt;-->
            <#--<div class="tabsRight" style="width: 75%">right</div><!-- 禁用只需要添加一个样式 class="tabsRight tabsRightDisabled" &ndash;&gt;-->
            <div class="tabsMore">more</div>
        </div>
        <ul class="tabsMoreList">
            <li><a href="javascript:">我的主页</a></li>
        </ul>
        <div class="navTab-panel tabsPageContent layoutBox">
            <#--<div class="page unitBox">-->
                <div class=" page unitBox" style="height: 100%">
                <#--<div class="accountInfo">-->
                    <h1 style="font-size: large"><strong>本机IP地址：${ip!}</strong></h1>
                    <br>

                    <object width="100%" height="100%" data="http://localhost/courseWare/教育说明书.pdf" type="application/pdf">
                        <p><b>Example fallback content</b>: This browser does not support PDFs. Please download the PDF to view it: <a href="/pdf/sample-3pp.pdf">Download PDF</a>.</p>
                    </object>
                <#--</div>-->
            </div>

            <#--<div class="pageContent">-->
                <#--<h1 style="font-size: large"><strong>本机IP地址：${ip!}</strong></h1>
                <br>
                <object width="100%" height="100%" data="http://localhost/courseWare/教育说明书.pdf" type="application/pdf">
                    <p><b>Example fallback content</b>: This browser does not support PDFs. Please download the PDF to view it: <a href="/pdf/sample-3pp.pdf">Download PDF</a>.</p>
                </object>-->
            <#--</div>-->
        </div>

    </div>
</div>