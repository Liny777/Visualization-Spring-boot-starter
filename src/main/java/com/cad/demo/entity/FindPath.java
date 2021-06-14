/**

 * entity层

 * 用于访问Restful API 路径查询时使用

 * @author Lin Youguang

 * @Time 2021-03-24

 *

 */
package com.cad.demo.entity;

public class FindPath {
    private String SId;
    private String EId;
    private int maxdepth;

    public int getMaxdepth() {
        return maxdepth;
    }

    public void setMaxdepth(int maxdepth) {
        this.maxdepth = maxdepth;
    }

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
}
