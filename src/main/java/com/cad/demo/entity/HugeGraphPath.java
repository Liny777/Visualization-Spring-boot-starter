/**

 * entity层

 * 用于存储查询数据路径的响应

 * @author Lin Youguang

 * @Time 2021-03-24

 *

 */
package com.cad.demo.entity;

import java.util.ArrayList;
import java.util.List;

public class HugeGraphPath {

    private String SId;
    private String SName;
    private String EName;
    private String SLabel;
    private String ELabel;

    public String getSLabel() {
        return SLabel;
    }

    public void setSLabel(String SLabel) {
        this.SLabel = SLabel;
    }

    public String getELabel() {
        return ELabel;
    }

    public void setELabel(String ELabel) {
        this.ELabel = ELabel;
    }

    public String getSName() {
        return SName;
    }

    public void setSName(String SName) {
        this.SName = SName;
    }

    public String getEName() {
        return EName;
    }

    public void setEName(String EName) {
        this.EName = EName;
    }

    private String EId;
    private List<Object> PathNode;

    public String getSId() {
        return SId;
    }

    public void setSId(String SId) {
        this.SId = SId;
    }

    public String getEId() {
        return EId;
    }

    public void setEId(String EId) {
        this.EId = EId;
    }

    public List<Object> getPathNode() {
        return PathNode;
    }

    public void setPathNode(List<Object> pathNode) {
        PathNode = pathNode;
    }
}
