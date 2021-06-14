package com.cad.demo.pojo.xieweihaoPojo;


import org.bson.BsonValue;
import org.springframework.data.mongodb.core.mapping.Document;

@Document(collection = "Guide1")
public class Pdf {
    private String id;
    private String name;

    public void setName(String name) {
        this.name = name;
    }

    public String getName() {
        return name;
    }

    public String getId() {
        return id;
    }

    public void setId(String  id) {
        this.id = id;
    }
}
