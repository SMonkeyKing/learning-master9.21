<style>
    #content{
        height: auto;
        margin: 15px auto 5px 12px;
        /*border:1px solid #B3E4EB;*/
    }
</style>
<div  class="pageContent">
    <div class="accordionContent" layoutH="62">
        <div id ="questionShowDiv">
        <#--<div class="accordionContent" layoutH="62">-->
            <div>
                <span style="float: left;">绝密★启用前</span>
            </div>
                <br/>
                <br/>
            <div style="text-align: center">
                <span style="text-align: center;font-size: larger"><strong>2017-2018学年度xx学校xx月考卷</strong></span>
                <br/>
                <br/>
                <br/>
                <span style="text-align: center"> 学校：___________姓名：___________班级：___________考号：___________</span>
            </div>
        <#--<div class="pageFormContent" layoutH="138">-->
            <#if contents??&&contents?size gt 0>
                <#list contents as content>
                    <div class="panelContent" >
                        <div id="content">
                            <span style="float: left">${content_index+1}. </span>${content!}
                        </div>
                    </div>
                </#list>
            </#if>
        </div>
    </div>
</div>
 <div class="formBar">
   <ul>
   <li><a class="buttonActive" id ="download" href="javascript:;" onclick="printPage();"><span>导出</span></a></li>
    </ul>
    </div>
    <div class="panelBar">
        <div class="pages">
           共${size!}道题</span>
        </div>
    </div>

<div class="top"></div>
<script>
function printPage(){
var body = window.document.body.innerHTML;
var printHtml = $("#questionShowDiv").html();
window.document.body.innerHTML=printHtml;
window.print();
window.document.body.innerHTML=body;
}
</script>
