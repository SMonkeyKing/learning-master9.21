<div class="pageContent">
    <form method="post" enctype="multipart/form-data" action="${ctx}/sczy/academicJournal/update" class="pageForm required-validate" onsubmit="return iframeCallback(this, navTabAjaxDone);" >
        <div class="pageFormContent" layoutH="60">
            <fieldset>
                <legend>修改期刊</legend>
                <input type="hidden" name="id" value="${academicJournalDO.id!}"/>
                <dl class="nowrap">
                    <dt>期刊名称：</dt>
                    <dd>
                        <input type="text" name="name" value="${academicJournalDO.name!}" />
                    </dd>
                </dl>
                <dl class="nowrap">
                    <dt>期刊链接：</dt>
                    <dd>
                        <input type="text" name="url" value="${academicJournalDO.url!}"/>
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

