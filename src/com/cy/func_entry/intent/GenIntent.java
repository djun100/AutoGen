package com.cy.func_entry.intent;

import com.cy.bean.BeanIntent;
import com.cy.manager.FreeMarkerManager;

public class GenIntent {

    /**intent account long userId User user
     * @param parser
     */
    public static void gen(IntentCmdParser parser){
        BeanIntent intent=new BeanIntent();
        intent.setAct("SplashAct")
                .setParams(parser.getKvs())
                .setBizName(parser.getBizName());

        //目标Act —— getIntent取值
        String getIntentValues= FreeMarkerManager.filter("/intent/fetch_variable.ftl",
                new FreeMarkerManager.Builder().setBeanIntent(intent));

        //目标Act | IntentUtils —— 定义KEY_常量
        String defineKes= FreeMarkerManager.filter("/intent/define_keys.ftl",
                new FreeMarkerManager.Builder().setBeanIntent(intent));

        //IntentUtils | 目标Act —— startAct函数
        String startAct = FreeMarkerManager.filter("/intent/startAct.ftl",
                new FreeMarkerManager.Builder().setBeanIntent(intent));

        //目标Act —— 存储取值
        String defineVariable = FreeMarkerManager.filter("/intent/define_variable.ftl",
                new FreeMarkerManager.Builder().setBeanIntent(intent));

        System.out.println(getIntentValues+"\n"+defineKes+"\n"+defineVariable+"\n"+startAct);
    }
}
