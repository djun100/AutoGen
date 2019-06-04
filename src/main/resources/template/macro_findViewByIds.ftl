<#macro findViewByIds widgets view_type>
<#import "const.ftl" as sys>
<#--gen findViewById block-->
<#list widgets as item>
<#if sys.view_type == sys.activity>
    <#if item.includeIdName?? && item.includeIdName?length gt 0>
        <#if item.type??>
        ${item.defineName} = <#--(${item.type})-->${item.includeIdName}.findViewById(R.id.${item.resId});
        <#else >
        ${item.defineName} = ${item.includeIdName}.findViewById(R.id.${item.resId});
        </#if>
    <#elseif item.type?? && item.type != "include">
        ${item.defineName} = <#--(${item.type})-->findViewById(R.id.${item.resId});
    <#else>
        ${item.defineName} = findViewById(R.id.${item.resId});
    </#if>
<#elseif sys.view_type == sys.fragment>
    <#if item.includeIdName?? && item.includeIdName?length gt 0>
        ${item.defineName} = <#--(${item.type})-->${item.includeIdName}.findViewById(R.id.${item.resId});
    <#elseif item.type?? && item.type != "include">
        ${item.defineName} = <#--(${item.type})-->view.findViewById(R.id.${item.resId});
    <#else>
        ${item.defineName} = view.findViewById(R.id.${item.resId});
    </#if>
</#if>
<#if item.type?contains("PullToRefreshRecyclerView")>
        ${item.defineName}.setMode(PullToRefreshBase.Mode.BOTH);
        mWrapRecyclerView = ${item.defineName}.getRefreshableView();
</#if>
</#list>
</#macro>