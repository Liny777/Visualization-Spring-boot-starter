/**

 * Controller层

 * 与前端交互的接口（使用了MongoDB数据库的接口）

 * @author Lin Youguang

 * @Time 2021-03-24

 *

 */
package com.cad.demo.controller;

import com.cad.demo.pojo.MedicalCategory;
import com.cad.demo.pojo.MedicalEdge;
import com.cad.demo.pojo.MedicalEdgeLabel;
import com.cad.demo.service.MongoDBService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping(value = "/MongoDB")
public class MongoDBController {
    @Autowired
    private MongoDBService mongodbService;
    // 获取最新文献列表 或 搜索文献列表
    @GetMapping(value = "/getEntityType")
    public List<MedicalCategory> getEntityType(){
        List<MedicalCategory> res= mongodbService.getMedicalCategory();
//        System.out.println(res);
        return res;
    }

    @GetMapping(value = "/getEdgeLabel")
    public List<MedicalEdgeLabel> getEdgeLabel(){
        List<MedicalEdgeLabel> res= mongodbService.getMedicalRelation();
//        System.out.println(res);
        return res;
    }
    @GetMapping(value = "/getAllEdgeLabel")
    public List<MedicalEdge> getAllEdgeLabel(){
        List<MedicalEdge> res= mongodbService.getMedicalEdge();
//        System.out.println(res);
        return res;
    }

}
