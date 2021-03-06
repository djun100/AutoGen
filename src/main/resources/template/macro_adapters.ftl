<#macro adapters widgets>
<#include "macro_adapter_clicklistener.ftl">
<#list widgets as item>
<#if item.type?contains("ListView")>
        mAdapter${item.resId?substring(1)?cap_first}= new Adapter<${item.bean}>(
                mBean${item.resId?substring(1)?cap_first}, R.layout.${item.layoutTags[0]}) {
            @Override
            protected void convert(AdapterHelper helper, final ${item.bean} bean, int i) {
            <#list item.subWidgets as subItem>
               <#if subItem.clickable> final</#if> ${subItem.type} ${subItem.defineName?substring(1)?uncap_first} = helper.getView(R.id.${subItem.resId});
            </#list>
            <@clicklistener widgets=item.subWidgets>
            </@clicklistener>
            }
        };
        ${item.defineName}.setAdapter(mAdapter${item.resId?substring(1)?cap_first});
<#elseif item.type ?contains("PullToRefreshRecyclerView") >
        mAdapter${item.resId?substring(1)?cap_first} = new AdapterRecycler<${item.bean}>(
                mWrapRecyclerView, mBean${item.resId?substring(1)?cap_first}, R.layout.${item.layoutTags[0]}) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, final ${item.bean} bean, int i) {
            <#list item.subWidgets as subItem>
               <#if subItem.clickable> final</#if> ${subItem.type} ${subItem.defineName?substring(1)?uncap_first} = helper.getView(R.id.${subItem.resId});
            </#list>
            <@clicklistener widgets=item.subWidgets>
            </@clicklistener>
            }
        };
        mWrapRecyclerView.setAdapter(mAdapter${item.resId?substring(1)?cap_first});
<#elseif item.type ?contains("RecyclerView")>
        mAdapter${item.resId?substring(1)?cap_first} = new AdapterRecycler<${item.bean}>(
                ${item.defineName}, mBean${item.resId?substring(1)?cap_first}, R.layout.${item.layoutTags[0]}) {
            @Override
            protected void convert(RecyclerAdapterHelper helper, final ${item.bean} bean, int i) {
            <#list item.subWidgets as subItem>
               <#if subItem.clickable> final</#if> ${subItem.type} ${subItem.defineName?substring(1)?uncap_first} = helper.getView(R.id.${subItem.resId});
            </#list>
            <@clicklistener widgets=item.subWidgets>
            </@clicklistener>
            }
        };
        ${item.defineName}.setAdapter(mAdapter${item.resId?substring(1)?cap_first});
</#if>
</#list>
</#macro>