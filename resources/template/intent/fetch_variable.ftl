<#list intent.params as item>
<#assign key = "KEY_${intent.bizName?upper_case}_${item.v?upper_case}" >
    <#if item.k=="String">
    m${item.v?cap_first} = getIntent().getStringExtra(${key});
    <#elseif item.k=="long">
    m${item.v?cap_first} = getIntent().getLongExtra(${key},-1);
    <#else >
    m${item.v?cap_first} = getIntent().getStringExtra(${key});
    </#if>
</#list>
