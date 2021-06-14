/**

 * Service层

 * 处理从MongoDB取出数据的逻辑代码

 * @author Lin Youguang

 * @Time 2021-03-24

 *

 */
package com.cad.demo.service;

import com.cad.demo.pojo.MedicalCategory;
import com.cad.demo.pojo.MedicalEdgeLabel;
import com.cad.demo.pojo.MedicalEdge;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import org.springframework.stereotype.Service;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;
import org.springframework.data.mongodb.core.query.Query;
import java.util.List;

@Service
public class MongoDBService {
    private MongoTemplate mongoTemplate;

    public MongoDBService(MongoTemplate mongoTemplate) {
        this.mongoTemplate = mongoTemplate;
    }

    public List<MedicalCategory> getMedicalCategory(){
        List<MedicalCategory> res;
        Query query = new Query();
        res = mongoTemplate.find(query, MedicalCategory.class);
        return res;
    }

    public List<MedicalEdgeLabel> getMedicalRelation(){
        List<MedicalEdgeLabel> res;
        Query query = new Query();
        res = mongoTemplate.find(query,MedicalEdgeLabel.class);
        return res;
    }

    public List<MedicalEdge> getMedicalEdge(){
        List<MedicalEdge> res;
        Query query = new Query();
        res = mongoTemplate.find(query,MedicalEdge.class);
        return res;
    }
}
