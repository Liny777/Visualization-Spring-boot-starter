/**

 * entity层

 * 用于存储Restful API Kneighbour功能的响应

 * @author Lin Youguang

 * @Time 2021-03-24

 *

 */
package com.cad.demo.entity;

public class KneighbourRequest {
    private Long source;
    private int max_depth;
    private boolean with_path;
    private boolean with_vertex;

    public Long getSource() {
        return source;
    }

    public void setSource(Long source) {
        this.source = source;
    }

    public int getMax_depth() {
        return max_depth;
    }

    public void setMax_depth(int max_depth) {
        this.max_depth = max_depth;
    }

    public boolean getWith_path() {
        return with_path;
    }

    public void setWith_path(boolean with_path) {
        this.with_path = with_path;
    }

    public boolean getWith_vertex() {
        return with_vertex;
    }

    public void setWith_vertex(boolean with_vertex) {
        this.with_vertex = with_vertex;
    }
}
