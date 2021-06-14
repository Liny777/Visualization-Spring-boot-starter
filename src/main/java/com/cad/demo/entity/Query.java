package com.cad.demo.entity;

/*
请求搜索列表和请求搜索详情都采用本实体
 */
public class Query {
    private String category;
    private String content;
//    private String inV;
//    private String outV;

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

//    public String getInV() {
//        return inV;
//    }
//
//    public void setInV(String inV) {
//        this.inV = inV;
//    }
//
//    public String getOutV() {
//        return outV;
//    }
//
//    public void setOutV(String outV) {
//        this.outV = outV;
//    }
}