package com.drools.demo.test;

import java.io.Serializable;

/************************************************************************************
 * Copyright (c) 2017 © Bestpay Co., Ltd.  All Rights Reserved.
 * This software is published under the terms of the Bestpay.
 * Software License version 1.0, a copy of which has been included with this
 * distribution in the LICENSE.txt file.
 * <p>
 * File name:      
 * Create on:      2018/5/22
 * Author :        官红诚
 * <p>
 * ChangeList
 * -----------------------------------------------------------------------------
 * Date                Editor        ChangeReasons
 * 2018/5/22            官红诚         Create
 ************************************************************************************/
public class RuleMessage implements Serializable{
    private String status;
    private String msg;

    public RuleMessage(String status, String msg) {
        this.status = status;
        this.msg = msg;
    }

    public RuleMessage() {
    }

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
