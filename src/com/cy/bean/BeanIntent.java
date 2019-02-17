package com.cy.bean;

import com.cy.func_entry.intent.KV;

import java.util.List;

public class BeanIntent {
    String act;
    String bizName;
    List<KV> params;

    public String getAct() {
        return act;
    }

    public BeanIntent setAct(String act) {
        this.act = act;
        return this;
    }

    public List<KV> getParams() {
        return params;
    }

    public BeanIntent setParams(List<KV> params) {
        this.params = params;
        return this;
    }

    public String getBizName() {
        return bizName;
    }

    public BeanIntent setBizName(String bizName) {
        this.bizName = bizName;
        return this;
    }
}
