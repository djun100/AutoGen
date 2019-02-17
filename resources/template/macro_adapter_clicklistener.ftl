<#macro clicklistener widgets>
<#assign hasClickableWidget = false >
<#--
第一次循环，OnClickListener实例化
-->
<#list widgets as item>
    <#if item.clickable>
        <#assign hasClickableWidget = true >

        View.OnClickListener mOnClickListener=new View.OnClickListener() {
            @Override
            public void onClick(View v) {
        <#break >
    </#if>
</#list>
<#--
第二次循环，判断view来区分各自的点击事件
-->
<#assign dealt_first_clickable_widget = false >
<#if hasClickableWidget>
    <#list widgets as item>
        <#if item.clickable>
            <#if dealt_first_clickable_widget == false >
                <#assign dealt_first_clickable_widget = true >
                if (v == ${item.defineName?substring(1)?uncap_first} ) {

            <#else >
                } else if (v == ${item.defineName?substring(1)?uncap_first} ) {
            
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
        ${item.defineName?substring(1)?uncap_first}.setOnClickListener(mOnClickListener);
    </#if>
</#list>

<#assign end></#assign>
</#macro>