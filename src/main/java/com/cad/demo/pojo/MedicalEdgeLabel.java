package com.cad.demo.pojo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "MedicalEdgeLabel")
public class MedicalEdgeLabel {
    String name;

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
