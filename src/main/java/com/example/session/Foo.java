package com.example.session;

import java.io.Serializable;

public class Foo implements Serializable {

    String bar;
    String baz;

    public String getBar() {
        return bar;
    }

    public void setBar(String bar) {
        this.bar = bar;
    }

    public String getBaz() {
        return baz;
    }

    public void setBaz(String baz) {
        this.baz = baz;
    }
}
