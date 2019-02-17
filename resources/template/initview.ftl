<#import "const.ftl" as sys>
<#include "macro_findViewByIds.ftl">
<#include "macro_clicklistener.ftl">
<#include "macro_adapters.ftl">

<#if sys.view_type == sys.activity>
    private void ${sys.initViewMethodName}() {
<#else >
    @Override
    public void onViewCreated(View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
</#if>
        <@findViewByIds widgets=widgets view_type=view_type/>
        <@clicklistener widgets=widgets/>
        <@adapters widgets=widgets/>
    }