/**

 * entity层

 * 用于存储数据边的信息

 * @author Lin Youguang

 * @Time 2021-03-24

 *

 */
package com.cad.demo.entity;

import java.util.Map;

public class HugeGraphEdge {
//    private String id;
    private String inVName;
    private String outVName;

    public String getInVName() {
        return inVName;
    }

    public void setInVName(String inVName) {
        this.inVName = inVName;
    }

    public String getOutVName() {
        return outVName;
    }

    public void setOutVName(String outVName) {
        this.outVName = outVName;
    }

    private String label;
    private String inVLabel;
    private String outVLabel;
    private String inV;
    private String outV;
    private Map<String,Object> propertie;
    private String description;
    private Boolean ishave;
    private String inName;
    private String outName;

    public String getInName() {
        return inName;
    }

    public void setInName(String inName) {
        this.inName = inName;
    }

    public String getOutName() {
        return outName;
    }

    public void setOutName(String outName) {
        this.outName = outName;
    }

    public Boolean getIshave() {
        return ishave;
    }

    public void setIshave(Boolean ishave) {
        this.ishave = ishave;
    }
    private String proper;
    public void setProper(String o) {
        this.proper = o;
    }
    public String getProper() {
        return this.proper;
    }
    public Map<String, Object> getPropertie() {
        return propertie;
    }

    public void setPropertie(Map<String, Object> propertie) {
        this.propertie = propertie;
        this.MaptoStirng();
    }

    public String getLabel() {
        return label;
    }

    public void setLabel(String label) {
        this.label = label;
    }

    public String getInVLabel() {
        return inVLabel;
    }

    public void setInVLabel(String inVLabel) {
        this.inVLabel = inVLabel;
    }

    public String getOutVLabel() {
        return outVLabel;
    }

    public void setOutVLabel(String outVLabel) {
        this.outVLabel = outVLabel;
    }

    public String getInV() {
        return inV;
    }

    public void setInV(String inV) {
        this.inV = inV;
    }

    public String getOutV() {
        return outV;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public void setOutV(String outV) {
        this.outV = outV;
    }
    public void MaptoStirng(){
        String des ="Label: "+ this.label+"<br/>";
        for (Map.Entry<String,Object> entry : this.propertie.entrySet()) {
            des = des+ entry.getKey()+": " +entry.getValue()+"<br/>";
        }
        this.setDescription(des);
    }
}
