<#list intent.params as item>
<#assign key = "KEY_${intent.bizName?upper_case}_${item.v}" >
    private ${item.k} m${item.v?cap_first};
</#list>
