/**

 * entity层

 * 用于请求Restful API Kneighbour功能的请求参数

 * @author Lin Youguang

 * @Time 2021-03-24

 *

 */
package com.cad.demo.entity;

public class KneighbourParams {
    private String source;
    private int max_depth;

    public String getSource() {
        return source;
    }

    public void setSource(String source) {
        this.source = source;
    }

    public int getMax_depth() {
        return max_depth;
    }

    public void setMax_depth(int max_depth) {
        this.max_depth = max_depth;
    }
}
