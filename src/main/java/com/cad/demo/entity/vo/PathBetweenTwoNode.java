package com.cad.demo.entity.vo;

import java.util.ArrayList;
import java.util.List;

public class PathBetweenTwoNode {
    String HeadId;
    String HeadName;
    String TailId;
    String TailName;
    String Relation;
    String HeadLabel;
    String TailLabel;
    String Description;
    String HeadDes;
    String TailDes;

    public String getHeadDes() {
        return HeadDes;
    }

    public void setHeadDes(String headDes) {
        HeadDes = headDes;
    }

    public String getTailDes() {
        return TailDes;
    }

    public void setTailDes(String tailDes) {
        TailDes = tailDes;
    }

    public String getDescription() {
        return Description;
    }

    public void setDescription(String description) {
        Description = description;
    }

    public String getHeadLabel() {
        return HeadLabel;
    }

    public void setHeadLabel(String headLabel) {
        HeadLabel = headLabel;
    }

    public String getTailLabel() {
        return TailLabel;
    }

    public void setTailLabel(String tailLabel) {
        TailLabel = tailLabel;
    }

    public String getHeadId() {
        return HeadId;
    }

    public void setHeadId(String headId) {
        HeadId = headId;
    }

    public String getHeadName() {
        return HeadName;
    }

    public void setHeadName(String headName) {
        HeadName = headName;
    }

    public String getTailId() {
        return TailId;
    }

    public void setTailId(String tailId) {
        TailId = tailId;
    }

    public String getTailName() {
        return TailName;
    }

    public void setTailName(String tailName) {
        TailName = tailName;
    }

    public String getRelation() {
        return Relation;
    }

    public void setRelation(String relation) {
        Relation = relation;
    }
}
