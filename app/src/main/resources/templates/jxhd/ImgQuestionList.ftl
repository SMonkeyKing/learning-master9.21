<style>
    .kuang {position: relative;}
    .kuang ul{padding:0px;margin: 0 auto; width:1100px; position: relative;float:center;list-style-type:none;}
    .kuang li{width:230px;height:220px;display:inline;margin:10px 0 0 10px;background:#f3f3f3;float:left;padding:10px 10px 0 10px;overflow:hidden;filter:alpha(opacity=100);opacity:1;}
    .kuang li span{display:block;height:190px;}
    .kuang li h2{display:block;height:30px;line-height:30px;text-align:center;text-indent:3px;margin:0px;padding:0px;font-style:normal;}
    .kuang .menu{width:918px;height:76px;display:inline;margin-top:28px;text-align:center; position:absolute;bottom:0px;left:0px;}
    .kuang .menu a{width:52px;height:52px;display:inline-block;margin-top:12px; color:#9c9c9c;font-size:20px;text-decoration:none;text-align:center;line-height:50px;}
    .kuang .menu a.hover{color:#fff;}
</style>
<div class="pageContent" >
    <div class="accordionContent" layoutH="87">
    <div class="kuang">
        <ul>
            <#if jxhdStudentAnswerDOs??&&jxhdStudentAnswerDOs?size gt 0>
                <#list jxhdStudentAnswerDOs as jxhdStudentAnswerDO>
                <li>
                    <span><a href="${jxhdStudentAnswerDO.answer!}" target="_blank"><img src="${jxhdStudentAnswerDO.answer!}" width="100%" height="100%" /></a></span>
                    <h2>${jxhdStudentAnswerDO.username!}</h2>
                </li>
            </#list>
            </#if>

        </ul>
        <div class="menu">
            <a href="javascript:;" class="hover">1</a>
            <a href="javascript:;">2</a>
        </div>
    </div>
    </div>
</div>