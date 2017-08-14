<div class="pageContent">
    <form method="post" enctype="multipart/form-data" action="${ctx}/courseWare/update" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);" >
        <div class="pageFormContent" layoutH="60">
            <fieldset>
                <legend>修改课件</legend>
                <input type="hidden" name="id" value="${courseWare.id!}"/>
                <dl class="nowrap">
                    <dt>课件名称：</dt>
                    <dd>
                        <input type="text" name="name" class="required" value="${courseWare.name!}"/>
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

