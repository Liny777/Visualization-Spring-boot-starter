package com.cad.demo.controller;

import com.cad.demo.entity.*;
import com.cad.demo.entity.vo.PathBetweenTwoNode;
import com.cad.demo.service.HugegraphService;
import com.cad.demo.service.RestfulService;
import org.junit.Before;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.ArrayList;
/**

 * Controller层

 * 与前端交互的接口（使用了Restful API 访问HugeGraph数据库的接口）

 * @author Lin Youguang

 * @Time 2021-03-24

 *

 */
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@CrossOrigin
@RestController
@RequestMapping(value = "/restfulController")
public class RestfulController {

    @Autowired
    private RestfulService restfulService;


    @GetMapping(value = "/getAlledgelabel")
    public String FindAllPath(){
        return restfulService.getAllEdgeLabel();
    }

    @PostMapping(value = "/transver/path")
    public HugeGraphPath FindAllPath(@RequestBody FindPath findPath){
        String id1 = findPath.getSId();
        String id2 = findPath.getEId();
        int maxdepth = findPath.getMaxdepth();
        System.out.println("id1: "+id1);

        HugeGraphPath p = restfulService.getAllpath(id1,id2,maxdepth);
        return p;
    }

    @PostMapping(value = "/transver/neighbour")
    public HugegraphNeighbor Neighbour(@RequestBody KneighbourParams request) {
        HugegraphNeighbor p = restfulService.getNeighbour(request);
        return p;
    }

    @GetMapping(value = "/getAllvertexlabel")
    public ArrayList getAllVertexLabel(){
        return restfulService.getVertexLabel();
    }

    @GetMapping(value = "/getAllEdgelabel")
    public ArrayList getAllEdgeLabel(){
        return restfulService.getEdgeLabel();
    }

    @PostMapping(value = "/getVerticesNoPage")
    public ArrayList getVerticesNoPage(@RequestBody Query query) {
        String cate = query.getCategory();
        ArrayList p = restfulService.getVerticesNoPage(cate);
        return p;
    }

    @PostMapping(value = "/getEdgeNoPage")
    public ArrayList getEdgeNoPage(@RequestBody Query query)  {
        String cate = query.getCategory();
        ArrayList p = restfulService.getEdgeNoPage(cate);
        return p;
    }
}
