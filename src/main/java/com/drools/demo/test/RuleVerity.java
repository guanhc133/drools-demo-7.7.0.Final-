package com.drools.demo.test;

import org.drools.core.impl.InternalKnowledgeBase;
import org.drools.core.impl.KnowledgeBaseFactory;
import org.kie.api.io.ResourceType;
import org.kie.api.runtime.KieSession;
import org.kie.internal.builder.KnowledgeBuilder;
import org.kie.internal.builder.KnowledgeBuilderFactory;
import org.kie.internal.io.ResourceFactory;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
import java.util.List;

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
public class RuleVerity {

    public String rule(){
        KieSession ksession = null;
        String selectedPartner = "";
        try {
            InternalKnowledgeBase kbase = KnowledgeBaseFactory.newKnowledgeBase();
            KnowledgeBuilder kbuilder = KnowledgeBuilderFactory.newKnowledgeBuilder();

            //drl文件源
            /**
             *   import com.drools.example.Message
             *
             *   rule "Hello World"
             *   when
             *      m : Message( status == Message.HELLO, printMsg : msg )
             *   then
             *      System.out.println( printMsg );
             *      m.setMessage( "Goodbye~" );
             *      m.setStatus( Message.GOODBYE );
             *   end
             */
            //将drl文件源存入数据库并保留格式（空格，换行，引号）
            /**
             * insert into table (a)
             * value(
             * 'import com.drools.example.Message'
             * ||chr(10)||chr(13)||chr(10)
             * ||'rule'||chr(32)||'"'||'Hello World"'||'"'||chr(10)||chr(13)
             * ||'when'||chr(10)||chr(13)||chr(32)
             * ||'m : Message( status == Message.HELLO, printMsg : msg )'||chr(10)||chr(13)
             * ||'then'||chr(10)||chr(13)
             * ||chr(32)||'ystem.out.println( printMsg );'||chr(10)||chr(13)
             * ||chr(32)||'m.setMessage( "Goodbye~" );'||chr(10)||chr(13)
             * ||chr(32)||'m.setStatus( Message.GOODBYE );'||chr(10)||chr(13)
             * ||'end'
             */
            //解释 chr(10)||chr(13)：换行
            // chr(32)：回车
            // '"'||'Hello World"'||'"'：打印双引号
            //TODO 从数据库中读取规则
            String rule = getRule();
            //装入规则
            kbuilder.add(ResourceFactory.newByteArrayResource(rule.getBytes("utf-8")), ResourceType.DRL);

            if (kbuilder.hasErrors()) {
                System.out.println("路由规则编译错误");
            }

            kbase.addPackages(kbuilder.getKnowledgePackages());
            ksession = kbase.newKieSession();

            //声明选中权重合作方全局变量
            ksession.setGlobal("selectedPartner", "");

            RuleMessage ruleMessage1 = new RuleMessage();
            ruleMessage1.setStatus("1");
            ruleMessage1.setMsg("我的状态是1");
            RuleMessage ruleMessage2 = new RuleMessage();
            ruleMessage2.setStatus("2");
            ruleMessage2.setMsg("我的状态是2");
            List<RuleMessage> routeRuleMessages = new ArrayList<>();
            for ( int i = 0; i < routeRuleMessages.size(); i++ ) {
                RuleMessage routeRuleMessage = routeRuleMessages.get(i);
                System.out.println("向规则引擎插入路由规则传输对象信息");
                ksession.insert(routeRuleMessage);
            }
            ksession.fireAllRules();
            //选中权重合作方
            selectedPartner = String.valueOf(ksession.getGlobal("selectedPartner"));
        } catch (UnsupportedEncodingException e) {
            System.out.println("路由规则引擎服务中存在不支持编码异常:"+e);
        } catch (Exception ex) {
            System.out.println("路由规则引擎服务存在异常:"+ex);

        } finally {
            if (ksession != null) {
                ksession.dispose(); // Stateful rule session must always be disposed when finished
            }
        }
        return selectedPartner;
    }

    public String getRule() {
        String rule = "";
        return rule;
    }
}
