<div id="leftside" >
    <div id="sidebar_s">
        <div class="collapse">
            <div class="toggleCollapse">
                <div></div>
            </div>
        </div>
    </div>
    <div id="sidebar">
        <div class="toggleCollapse">
            <h2>主菜单</h2>
            <div>收缩</div>
        </div>

        <div class="accordion" fillSpace="sidebar" >
            <div class="accordionHeader">
                <h2><span>Folder</span>学科资源信息平台</h2>
            </div>
            <div class="accordionContent" >
                <#if leftmenu??>
                <#list leftmenu as level1>
                <ul class="tree treeFolder collapse">
                    <li><a <#if level1.getUrl()??>href="${ctx}${level1.getUrl()!}" target="navTab" rel="${level1.getTitle()!}" </#if>>${level1.getTitle()}</a>
                    <#if !level1.hasSubmenus()>

                        <#assign level2menus = level1.getSubmenus()/>
                        <#list level2menus as level2>
                        <ul>
                            <li>
                                <a <#if level2.getUrl()??>href="${ctx}${level2.getUrl()!}" target="navTab" rel="${level2.getTitle()!}" </#if> >${level2.getTitle()}</a>
                                <#if !level2.hasSubmenus()>
                                    <ul>
                                    <#assign level3menus = level2.getSubmenus()/>
                                    <#list level3menus as level3>

                                       <li>
                                           <a <#if level3.getUrl()??>href="${ctx}${level3.getUrl()!}<#if level3.paramsid??>?typeid=${level3.getParamsid()!}</#if>" target="navTab" rel="${level3.getTitle()!}"</#if>>${level3.getTitle()}</a>
                                           <#--<a href="<#if level3.getUrl()??>${ctx}${level3.getUrl()}<#else >javascript:void(0)</#if>" target="navTab" rel="${level3.getTitle()}">${level3.getTitle()}</a>-->
                                        <ul>
                                        <#assign level4menus = level3.getSubmenus()/>
                                        <#list level4menus as level4>
                                            <li><a href="<#if level4.getUrl()??>${ctx}${level4.getUrl()!}</#if>?<#if level4.paramsid??>typeid=${level4.getParamsid()!}<#else >typeid=${level4.getId()!}</#if>" target="navTab" rel="${level4.getTitle()!}">${level4.getTitle()}</a>
                                                <ul>
                                                    <#assign level5menus = level4.getSubmenus()/>
                                                    <#list level5menus as level5>
                                                        <li><a href="<#if level5.getUrl()??>${ctx}${level5.getUrl()!}</#if>?<#if level5.paramsid??>typeid=${level5.getParamsid()!}<#else >typeid=${level5.getId()!}</#if>" target="navTab" rel="${level5.getTitle()!}">${level5.getTitle()}</a>

                                                        </li>
                                                    </#list>
                                                </ul>
                                            </li>
                                        </#list>
                                        </ul>
                                       </li>
                                    </#list>
                                    </ul>
                                </#if>
                            </li>
                        </ul>
                        </#list>

                    </#if>
                </ul>
                </#list>

                </#if>
                <#--<ul class="tree treeFolder">
                    <li><a href="javascript:void(0)" >教学课件</a>
                        <ul>
                            <li><a href="${ctx}/courseWare/config" target="navTab" rel="精选课件">精选课件</a>
                                <ul>
                                    <li><a href="${ctx}/courseWare/config" target="navTab" rel="精选课件">必修一</a></li>
                                    <li><a href="${ctx}/courseWare/config" target="navTab" rel="精选课件">必修二</a></li>
                                    <li><a href="${ctx}/courseWare/config" target="navTab" rel="精选课件">化学反应原理</a></li>
                                    <li><a href="${ctx}/courseWare/config" target="navTab" rel="精选课件">有机化合物</a></li>
                                    <li><a href="${ctx}/courseWare/config" target="navTab" rel="精选课件">实验化学</a></li>
                                </ul>
                            </li>
                        </ul>
                        <ul>
                            <li><a href="${ctx}/courseWare/config" target="navTab" rel="我的课件">我的课件</a></li>
                        </ul>
                    </li>
                </ul>
                <ul class="tree treeFolder">
                    <li><a href="javascript:void(0)" >教学互动</a>
                        <ul>
                            <li><a href="${ctx}/courseWare/config" target="navTab" rel="课件列表">课件列表</a></li>
                        </ul>
                    </li>
                </ul>
                <ul class="tree treeFolder">
                    <li><a href="javascript:void(0)" >素材资源</a>
                        <ul>
                            <li><a href="${ctx}/courseWare/config" target="navTab" rel="教学素材">教学素材</a></li>
                        </ul>
                        <ul>
                            <li><a href="${ctx}/courseWare/config" target="navTab" rel="我的资源">我的资源</a></li>
                        </ul>
                        <ul>
                            <li><a href="${ctx}/courseWare/config" target="navTab" rel="学科期刊">学科期刊</a></li>
                        </ul>

                    </li>
                </ul>
                <ul class="tree treeFolder">
                    <li><a href="javascript:void(0)" >试题集锦</a>
                        <ul>
                            <li><a href="${ctx}/courseWare/config" target="navTab" rel="学考高考">学考高考</a></li>
                        </ul>
                        <ul>
                            <li><a href="${ctx}/courseWare/config" target="navTab" rel="本校试题">本校试题</a></li>
                        </ul>
                        <ul>
                            <li><a href="${ctx}/question/config" target="navTab" rel="精选试题库">精选试题库</a></li>
                        </ul>
                        <ul>
                            <li><a href="${ctx}/courseWare/config" target="navTab" rel="拓展试题">拓展试题</a></li>
                        </ul>
                        <ul>
                            <li><a href="${ctx}/question/prepareAdd" target="navTab" rel="题目录入">题目录入</a></li>
                        </ul>
                    </li>
                </ul>
                <ul class="tree treeFolder">
                    <li><a href="javascript:void(0)" >优秀课例</a>
                        <ul>
                            <li><a href="${ctx}/courseWare/config" target="navTab" rel="课件列表">课件列表</a></li>
                        </ul>
                    </li>
                </ul>-->
            </div>
        </div>
    </div>
</div>
