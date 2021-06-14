/**

 * Controller层

 * 与前端交互的接口（使用了HUgeGraph数据库的接口）

 * @author Lin Youguang

 * @Time 2021-03-24

 *

 */
package com.cad.demo.controller;

import com.cad.demo.entity.Query;
import com.cad.demo.service.HugegraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.net.ServerSocket;
import java.util.HashSet;
import java.util.List;
import java.util.Map;


@CrossOrigin
@RestController
@RequestMapping("/hugegraph")
public class HugegraphController {

    @Autowired
    private HugegraphService hugegraphService;

    @GetMapping(value = "/get/entity/num")
    public List<Object> getEntityNum(){
        List<Object> numList = hugegraphService.getEntityCount();
        return numList;
    }

    @GetMapping(value = "/get/relation/num")
    public List<Object> getRelationNum(){
        List<Object> numList = hugegraphService.getRelationCount();
        return numList;
    }

    @PostMapping(value = "/getEdgeVertical")
    public List<Object> getEdgeVertical(@RequestBody Query query){
        String category= query.getCategory();
        List<Object> someList = hugegraphService.getEdgeVertical(category);
        return someList;
    }

    @GetMapping(value = "/allData")
    public List<Object> allData(){
        List<Object> someList = hugegraphService.getAllNode();
        return someList;
    }

    @GetMapping(value = "/allLabel")
    public  List<Object> allLabel(){
        List<Object> someList = hugegraphService.getAllLabel();
        return someList;
    }

    @GetMapping(value = "/FindOutV")
    public List<Object> FindOutV(){
        List<Object> someList1 = hugegraphService.getAllLabel();
//        System.out.println("test: "+someList1);
        List<Object> someList2 = hugegraphService.getEdgeNode(someList1);
        return someList2;
    }

    @PostMapping(value = "/LabeltoFindV")
    public List<Object> LabeltoFindV(@RequestBody Query query){
        String category= query.getCategory();
        List<Object> someList1 = hugegraphService.FromLabeltoFindV(category);
        return someList1;
    }

    @PostMapping(value = "/IdfindEdge")
    public List<Object> IdfindEdge(@RequestBody Query query){
//        System.out.println("id: "+id);
//        String category= query.getCategory();
        String id = query.getContent();
        System.out.println("content: "+query.getContent());
        System.out.println("category: "+query.getCategory());
        System.out.println("id: "+id);
        List<Object> someList1 = hugegraphService.IdfindEdge(id);
        return someList1;
    }

    @PostMapping(value = "/IdfindName")
    public Object IdfindName(@RequestBody Query query){
        String id = query.getContent();
        Object someList = hugegraphService.getName(id);
        return someList;
    }

    @PostMapping(value = "/IdfindLabel")
    public Object IdfindLabel(@RequestBody Query query){
        String id = query.getContent();
        Object someList = hugegraphService.getLabel(id);
        return someList;
    }

    @PostMapping(value = "/StartEndFindEdgeLabel")
    public Object StartEndFindEdgeLabel(@RequestBody Query query){
        String id1 = query.getContent();
        String id2 = query.getCategory();
        Object someList = hugegraphService.StartEndFindEdgeLabel(id1,id2);
        return someList;
    }

    @PostMapping(value = "/getProperties")
    public Object getProperties(@RequestBody Query query){
        String id = query.getContent();
        Object someList = hugegraphService.getProperties(id);
        return someList;
    }

    @PostMapping(value = "/StartEndFindEdgeProperty")
    public Object StartEndFindEdgeProperty(@RequestBody Query query){
        String id1 = query.getContent();
        String id2 = query.getCategory();
        Object someList = hugegraphService.getEdgeDescriptionById(id1,id2);
        return someList;
    }


}

