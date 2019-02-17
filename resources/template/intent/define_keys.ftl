<#list intent.params as item>
<#assign key = "KEY_${intent.bizName?upper_case}_${item.v?upper_case}" >
public static final ${item.k} ${key}="${item.v}";
</#list>
