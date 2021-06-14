package com.cad.demo.pojo;

import org.bson.types.ObjectId;
import org.springframework.data.mongodb.core.mapping.Document;

import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.List;

@Document(collection = "MedicalCategory")
public class MedicalCategory {
    String name;
    private ObjectId id;
//    ArrayList<LinkSon> link;
    List<List<String>> link;
//    ArrayList<String> link_son;

    public  List<List<String>>  getLink() {
        return link;
    }

    public void setLink( List<List<String>>  link) {
        this.link = link;
    }

    public ObjectId get_id() {
        return id;
    }

    public void set_id(ObjectId id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
