package com.cad.demo.controller;

import com.cad.demo.entity.Query;
import com.cad.demo.service.NewHugegraphService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin
@RestController
@RequestMapping("/hugegraph1")
public class NewHugegraphController {

    @Autowired
    private NewHugegraphService hugegraphService;

    @PostMapping(value = "/LabeltoFindV")
    public List<Object> LabeltoFindV(@RequestBody Query query){
        String category= query.getCategory();
        List<Object> someList1 = hugegraphService.FromLabeltoFindV(category);
        return someList1;
    }
    @PostMapping(value = "/IdfindEdge")
    public List<Object> IdfindEdge(@RequestBody Query query){
        String id = query.getContent();
//        System.out.println("content: "+query.getContent());
//        System.out.println("category: "+query.getCategory());
//        System.out.println("id: "+id);
        List<Object> someList1 = hugegraphService.IdfindEdge(id);
        return someList1;
    }


}
