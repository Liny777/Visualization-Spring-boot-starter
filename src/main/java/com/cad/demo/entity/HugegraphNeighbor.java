/**

 * entity层

 * 用于存储查询数据邻居的响应

 * @author Lin Youguang

 * @Time 2021-03-24

 *

 */
package com.cad.demo.entity;

import com.cad.demo.entity.vo.edgedata;

import com.cad.demo.entity.vo.nodedata;

import java.util.List;

public class HugegraphNeighbor {

    private String sid;
    private List<Object> path;
    private List<Object> node;

    public String getSid() {
        return sid;
    }

    public void setSid(String sid) {
        this.sid = sid;
    }

    public void addNode(nodedata n) {
        this.node.add(n);
    }
    public void addEdge(edgedata e) {
        this.path.add(e);
    }

    public List<Object> getPath() {
        return path;
    }

    public void setPath(List<Object> path) {
        this.path = path;
    }

    public List<Object> getNode() {
        return node;
    }

    public void setNode(List<Object> node) {
        this.node = node;
    }
    //    public List<edgedata> getPath() {
//        return path;
//    }
//
//    public void setPath(List<edgedata> path) {
//        this.path = path;
//    }
//
//    public List<nodedata> getNode() {
//        return node;
//    }
//
//    public void setNode(List<nodedata> node) {
//        this.node = node;
//    }
}
