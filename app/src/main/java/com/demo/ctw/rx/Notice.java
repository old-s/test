package com.demo.ctw.rx;

/**
 * Created by qiu on 2018/9/6.
 */

public class Notice {
  public   int type;
    Object content;

    public Notice(int type, Object content) {
        this.type = type;
        this.content = content;
    }

    public Notice(int type) {
        this.type = type;
    }
}
