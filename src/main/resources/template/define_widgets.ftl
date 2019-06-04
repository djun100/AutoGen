<#assign hasClickableWidget = false >
<#list widgets as item>
<#if item.type =="include">
    private View ${item.defineName};
<#else >
    private ${item.type} ${item.defineName};
</#if>
<#if item.bean??>
    private ArrayList<${item.bean}> mBean${item.resId?substring(1)?cap_first} = new ArrayList<>();
<#--
三方控件可定制初始化额外变量
-->
    <#if item.type?contains("PullToRefreshRecyclerView")>
    private WrapRecyclerView mWrapRecyclerView;
    private AdapterRecycler mAdapter${item.resId?substring(1)?cap_first};
    <#elseif item.type ?contains("RecyclerView")>
    private AdapterRecycler mAdapter${item.resId?substring(1)?cap_first};
    </#if>
</#if>
<#if item.clickable><#assign hasClickableWidget = true ></#if>

</#list>
<#--
有控件可点击的话要声明clickListener
-->
<#if hasClickableWidget>
    private View.OnClickListener mOnClickListener;
</#if>
