<div class="pageHeader">
    <span>学案名称：${lpName}</span>
</div>
<div class="pageContent">

    <#--<div id="myPdf" style="float: left;">

    </div>
    <div style="float:right;">
        <span>edwqeqw</span>
    </div>-->
        <div style="float: left;width: 65%;height: 100%;" layoutH="25">
        <object width="100%" height="100%" data="${syncTestDO.url!}" type="application/pdf">
            <p><b>Example fallback content</b>: This browser does not support PDFs. Please download the PDF to view it: <a href="/pdf/sample-3pp.pdf">Download PDF</a>.</p>
        </object>
        </div>
    <div style="float: right;width: 35%;height: 100%;"  layoutH="25">

        <#--<form method="post" enctype="multipart/form-data" action="${ctx}/syncTest/studentAnswer/save"  class="pageForm required-validate"  onsubmit="return iframeCallback(this, navTabAjaxDone);" >-->
            <form id="form1" method="post" enctype="multipart/form-data" action="${ctx}/syncTest/studentAnswer/save"  class="pageForm required-validate"  onsubmit="return iframeCallback(this)" >
            <input type="hidden" name="testId" value="${syncTestDO.id!}">
            <div class="pageFormContent" >
                <dl class="nowrap">
                    <dt>选择题答案：</dt>
                    <dd>
                        <div>
                            <input type="text" name="xztAnswer" />
                            <label style="color: red">填写样例：ABAA（不要有空格）</label>
                        </div>
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>填空计算题答案：</dt>
                    <dd>
                        <div>
                            <textarea style="height: 200px;width: 320px" name="tkjsAnswer"></textarea>
                        </div>
                    </dd>
                </dl>

                <div class="formBar">
                    <ul>
                        <li style="text-align: center"><div class="buttonActive"><div class="buttonContent"><button id="save" type="submit" onclick="changeButton()" <#--onclick="javascript:{this.innerHTML='已提交';this.disabled=true;}"-->>提交答案</button></div></div></li>
                        <li><a class="buttonActive"  id = "gen" href="${ctx}/syncTest/studentAnswer/showTestResult" target="navTab"><span>查看测试结果</span></a></li>
                    </ul>
                </div>
        </form>
    </div>
</div>

<#--
<script>
    PDFObject.embed("http://localhost/file/lujie1.pdf", "#myPdf");
</script>-->


<script>
    /*$("#save").click(function(){
        this.innerHTML = "已提交";
        this.disabled=true;
    });*/
    function  changeButton() {
        var x = document.getElementsById("save");
        x.innerHTML = "已提交";
        x.disabled = true;
    }
</script>
