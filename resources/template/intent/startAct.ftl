    public static void start${intent.act}(Activity act<#list intent.params as item>,${item.k} ${item.v}</#list>) {
        Intent intent = new Intent(act, ${intent.act}.class);
        <#list intent.params as item>
        intent.putExtra(${item.v?upper_case},${item.v});
        </#list>
        act.startActivity(intent);
    }