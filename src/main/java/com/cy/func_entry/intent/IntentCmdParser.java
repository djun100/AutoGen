package com.cy.func_entry.intent;

import com.cy.func_entry.IConcFuncParser;

import java.util.*;

public class IntentCmdParser implements IConcFuncParser {
    private String bizName;
    private List<KV> kvs;

    public IntentCmdParser(String[] params) {
        IntentCmdParser parser = (IntentCmdParser) parse(params);
        GenIntent.gen(parser);
    }

    @Override
    public IConcFuncParser parse(String[] params) {
        bizName = params[0];
        String[] keys = new String[params.length - 1];
        System.arraycopy(params, 1, keys, 0, keys.length);
        List<String> baseTypes = Arrays.asList(new String[]{"byte", "char", "int", "long", "float", "double"});
        String tempType = null;
        for (String key : keys) {
            //如果是基本类型 或 首字母为大写，则看做是数据类型，而非变量
            if (baseTypes.contains(key) || key.substring(0, 1).equals(key.substring(0, 1).toUpperCase())) {
                tempType = key;
            } else {
                if (kvs == null) kvs = new ArrayList<>();
                kvs.add(new KV(tempType, key));
            }

        }
        return this;
    }

    public String getBizName() {
        return bizName;
    }

    public List<KV> getKvs() {
        return kvs;
    }

}
