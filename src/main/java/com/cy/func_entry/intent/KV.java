package com.cy.func_entry.intent;

public class KV {
    public KV(String k, String v) {
        this.k = k;
        this.v = v;
    }

    public String k;
    public String v;

    public String getK() {
        return k;
    }

    public KV setK(String k) {
        this.k = k;
        return this;
    }

    public String getV() {
        return v;
    }

    public KV setV(String v) {
        this.v = v;
        return this;
    }
}
