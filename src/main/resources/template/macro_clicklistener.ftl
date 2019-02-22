<#macro clicklistener widgets>
<#assign hasClickableWidget = false >
<#--
第一次循环，OnClickListener实例化
-->
<#list widgets as item>
    <#if item.clickable>
        <#assign hasClickableWidget = true >

        mOnClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        <#break >
    </#if>
</#list>
<#--
第二次循环，判断view来区分各自的点击事件
-->
<#assign used_if = false >
<#if hasClickableWidget>
    <#list widgets as item>
        <#if item.clickable>
            <#if used_if == false >
                <#assign used_if = true >
                if (v == ${item.defineName} ) {

            <#else >
                } else if (v == ${item.defineName} ) {

            </#if>
        </#if>
    </#list>
                }
            }
        };
</#if>
<#--
第三次循环，写xxx.setOnClickListener(mOnClickListener);
-->
<#list widgets as item>
    <#if item.clickable>
        ${item.defineName}.setOnClickListener(mOnClickListener);
    </#if>
</#list>

<#assign end></#assign>
</#macro>