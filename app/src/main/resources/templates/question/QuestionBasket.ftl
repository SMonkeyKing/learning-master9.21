<div class="pageContent">
    <form method="post" id ="form1" name="form1"  action="${ctx}/showQuestion/PreShowPaper" class="pageForm required-validate">
        <div class="pageFormContent" layoutH="56">
            <p>
                <label>选择题：</label>
                <label>${xztNum!}&nbsp;道</label>
                <#--<a style="color:blue" href="#">删除</a>-->
            </p>
            <p>
                <label>填空题：</label>
                <label>${tktNum!}&nbsp;道</label>
                <#--<a style="color:blue" href="#">删除</a>-->
            </p>
            <p>
                <label>计算题：</label>
                <label>${jstNum!}&nbsp;道</label>
                <#--<a style="color:blue" href="#">删除</a>-->
            </p>
        </div>
        <div class="formBar">
            <ul>
                <li><a class="buttonActive" id ="save" href="javascript:;" ><span>保存</span></a></li>
                <li><a class="buttonActive"  id = "gen" href="${ctx}/showQuestion/PreShowPaper" target="navTab"><span>生成试卷</span></a></li>
                <li>
                    <div class="button"><div class="buttonContent"><button type="button" id="close" class="close">取消</button></div></div>
                </li>
            </ul>
        </div>
    </form>

</div>

 <script>
 $("#gen").click(function(){

 $("#close").click();
 });

 $("#save").click(function(){
  $.ajax({
 url:"${ctx}/paper/save",
 type:"post"

 })
  .done(function() {
 console.log("success");               
            })
  .fail(function() {
 console.log("error");       
 });
 });

 </script>