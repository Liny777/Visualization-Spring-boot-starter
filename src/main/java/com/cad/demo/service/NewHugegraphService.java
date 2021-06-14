package com.cad.demo.service;


import com.alibaba.fastjson.JSONObject;
import com.baidu.hugegraph.structure.graph.Edge;
import com.baidu.hugegraph.structure.graph.Vertex;
import com.baidu.hugegraph.structure.gremlin.Result;
import com.baidu.hugegraph.structure.gremlin.ResultSet;
import com.cad.demo.dao.NewHugegraphDAO;
import com.cad.demo.dao.RestfulDAO;
import com.cad.demo.entity.HugeGraphEdge;
import com.cad.demo.entity.HugeGraphVertex;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

@Service
public class NewHugegraphService {

    @Autowired
    private NewHugegraphDAO hugegraphDao;
    @Autowired
    private RestfulDAO restfulDAO;

    //输入是类别名称，输出通过类别查找其所有实体。
    public List<Object> FromLabeltoFindV(String category){
        ResultSet resultSet = hugegraphDao.getEntity(category);
        Iterator<Result> results = resultSet.iterator();
        List<Object> someList = new ArrayList<>();
        results.forEachRemaining(result -> {
            Object object = result.getObject();
            HugeGraphVertex v = new HugeGraphVertex();
            v.setLabel(((Vertex)object).label());
            v.setId(((Vertex)object).id().toString());
            v.setPropertie(((Vertex)object).properties());
            someList.add(v);
//            System.out.println(v);
        });
        return someList;
    }

    public List<Object> getEntityNeighbor(String Id){
        ResultSet resultSet = hugegraphDao.getEntityNeighbor(Id);
        List<Object> someList = new ArrayList<>();
        Iterator<Result> results = resultSet.iterator();
        System.out.println("是否为0: "+resultSet.size());
        results.forEachRemaining(result -> {
            Object object = result.getObject();
            System.out.println("hh: " + object);
            HugeGraphEdge e = new HugeGraphEdge();
//            String inID = ((Edge)object).sourceId().toString();
            String inName = ((Edge)object).properties().get("object").toString();
//            String outID = ((Edge)object).targetId().toString();
            String outName = ((Edge)object).properties().get("subject").toString();
            e.setInVName(inName);
            e.setOutVName(outName);
            e.setIshave(true);
            e.setInVLabel(((Edge)object).sourceLabel().toString());
            e.setOutVLabel(((Edge)object).targetLabel().toString());
//            String inlabel = getLabel(inID).toString();
//            e.setInVLabel(inlabel);
//            e.setOutVLabel(getLabel(outID).toString());
//            e.setLabel(((Edge)object).label());
            e.setInV(((Edge)object).sourceId().toString());
            e.setOutV(((Edge)object).targetId().toString());
            e.setLabel(((Edge)object).label().toString());
//            e.setPropertie(((Edge)object).properties());
            someList.add(e);
        });
        return someList;
    }

    public List<Object> IdfindEdge(String Id){
        ResultSet resultSet = hugegraphDao.getEntityNeighbor(Id);
        List<Object> someList = new ArrayList<>();
        Iterator<Result> results = resultSet.iterator();
        System.out.println("是否为0: "+resultSet.size());
        if(resultSet.size()==0){
            HugeGraphEdge e = new HugeGraphEdge();
            e.setIshave(false);
            e.setInVLabel(getLabel(Id).toString());
            e.setInV(Id);
            e.setInVName(getName(Id).toString());
            e.setProper(getProper(Id));
            someList.add(e);
        } else {
            results.forEachRemaining(result -> {
                Object object = result.getObject();
                System.out.println("hh: " + object);
                HugeGraphEdge e = new HugeGraphEdge();
//            String inID = ((Edge)object).sourceId().toString();
                String inName = ((Edge)object).properties().get("object").toString();
//            String outID = ((Edge)object).targetId().toString();
                String outName = ((Edge)object).properties().get("subject").toString();
                e.setInVName(inName);
                e.setOutVName(outName);
                e.setIshave(true);
                e.setInVLabel(((Edge)object).sourceLabel().toString());
                e.setOutVLabel(((Edge)object).targetLabel().toString());
//            String inlabel = getLabel(inID).toString();
//            e.setInVLabel(inlabel);
//            e.setOutVLabel(getLabel(outID).toString());
//            e.setLabel(((Edge)object).label());
                e.setInV(((Edge)object).sourceId().toString());
                e.setOutV(((Edge)object).targetId().toString());
                e.setProper(getProper(Id));
                e.setLabel(((Edge)object).label().toString());
//            e.setPropertie(((Edge)object).properties());
                someList.add(e);
            });
        }

        return someList;
    }

    public Object getLabel(String id){
        System.out.println("qwee: "+id);
        ResultSet nameResult = hugegraphDao.getLabelById(id); // 通过id获取目标name
        Iterator<Result> results = nameResult.iterator();
        List<Object> someList = new ArrayList<>();
        results.forEachRemaining(result -> {
            Object object = result.getObject();
            System.out.println("ooo: " + object);
            someList.add(object);
        });
        return someList.get(0);
    }

    public Object getName(String id){
        ResultSet nameResult = hugegraphDao.getNameById(id); // 通过id获取目标name
        Iterator<Result> results = nameResult.iterator();
        List<Object> someList = new ArrayList<>();
        results.forEachRemaining(result -> {
            Object object = result.getObject();
            someList.add(object);
        });
        return someList.get(0);
    }

    public String getProper(String id) {
        String s = restfulDAO.getProper(id); // 通过id获取目标name
        System.out.println("s: " +s);
        JSONObject jsonResult = null;
        jsonResult = JSONObject.parseObject(s);
        String p = jsonResult.getString("properties");
        return p;
    }

}
