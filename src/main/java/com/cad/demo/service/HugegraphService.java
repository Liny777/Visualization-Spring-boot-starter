/**

 * Service层

 * 处理从HugeGraph取出数据的逻辑代码

 * @author Lin Youguang

 * @Time 2021-03-24

 *

 */
package com.cad.demo.service;

import com.baidu.hugegraph.structure.graph.Edge;
import com.baidu.hugegraph.structure.graph.Path;
import com.baidu.hugegraph.structure.graph.Vertex;
import com.baidu.hugegraph.structure.gremlin.Result;
import com.baidu.hugegraph.structure.gremlin.ResultSet;
import com.cad.demo.dao.HugegraphDAO;
import com.cad.demo.entity.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.awt.event.ItemEvent;
import java.util.*;
@Service
public class HugegraphService {


    @Autowired
    private HugegraphDAO hugegraphDao;

    public List<Object> getEntityCount(){
        ResultSet resultSet = hugegraphDao.getEntityNum();
        Iterator<Result> results = resultSet.iterator();
        List<Object> numList = new ArrayList<>();
        results.forEachRemaining(result -> {
//            System.out.println(result.getObject().getClass());
            Object object = result.getObject();
//            System.out.println(object);
            numList.add(object);
        });
        return numList;
    }

    public List<Object> getRelationCount(){
        ResultSet resultSet = hugegraphDao.getRelationNum();
        Iterator<Result> results = resultSet.iterator();
        List<Object> numList = new ArrayList<>();
        results.forEachRemaining(result -> {
//            System.out.println(result.getObject().getClass());
            Object object = result.getObject();
//            System.out.println(object);
            numList.add(object);
        });
        return numList;
    }

    public List<Object> getAllNode(){
        ResultSet resultSet = hugegraphDao.getAllClass();
//        System.out.println(resultSet.get(1).getVertex().label());
        Iterator<Result> results = resultSet.iterator();
        List<Object> someList = new ArrayList<>();
        results.forEachRemaining(result -> {
            Object object = result.getObject();
            HugeGraphVertex v = new HugeGraphVertex();
            v.setLabel(((Vertex)object).label());
            v.setId(((Vertex)object).id().toString());
            v.setPropertie(((Vertex)object).properties());
//            v.setName(((Vertex)object).property("name").toString());
            someList.add(v);
            });
        return someList;
    }

    public List<Object> getAllLabel(){
        ResultSet resultSet = hugegraphDao.getAllLabel();
        Iterator<Result> results = resultSet.iterator();
        List<Object> someList = new ArrayList<>();
        results.forEachRemaining(result -> {
//            System.out.println(result.getObject().getClass());
            someList.add(result.getObject());

        });
        HashSet set = new HashSet(someList);
        someList.clear();
        someList.addAll(set);
//        for (int i = 0; i < someList.size() - 1; i++) {
//            for (int j = someList.size() - 1; j > i; j--) {
//                if (someList.get(j).equals(someList.get(i))) {
//                    someList.remove(j);
//                }
//            }
//        }
        for(int i = 0; i < someList.size() - 1; i++){
            System.out.println(i+": "+someList.get(i));
        }
        System.out.println("XXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXXx");
        return someList;
    }
    //接收的参数是所有的类别，输出是一个类别一个列表，列表里是与他有关系的节点。
//    public List<Object> getEdgeNode(List<Object> someList){
    public List<Object> getEdgeNode(List<Object> someList){
        List<Object> sum = new ArrayList<>();
        for(int i = 0; i < someList.size(); i++){
            String s = someList.get(i).toString();
            ResultSet resultSet = hugegraphDao.getEdgeNode(s);
            List<Object> one = new ArrayList<>();
//            one.add(s);//先把自己加进去
            Iterator<Result> results = resultSet.iterator();
            results.forEachRemaining(result -> {
                Object object = result.getObject();
                one.add(((Edge)(((Path) object).objects().get(1))).sourceLabel()); //把他作为出节点的所有入节点加进去
            });
//            if(one.size()>1){
            HashSet set = new HashSet(one);
            one.clear();
            one.addAll(set);
            one.add(0,s);
            sum.add(one);
//            }

        }
        for(int i = 0; i < sum.size() - 1; i++){
            System.out.println(i+": "+sum.get(i));
        }
        return sum;
    }

    //输入是类别名称，输出通过类别查找其所有实体。
    public List<Object> FromLabeltoFindV(String category){
        ResultSet resultSet = hugegraphDao.FromLabeltoFindV(category);
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
//    public void getEdgeNode() {
//        ResultSet resultSet = hugegraphDao.getEdgeNode("疾病类型");
//        Iterator<Result> results = resultSet.iterator();
//            results.forEachRemaining(result -> {
//                Object object = result.getObject();
//                System.out.println(  ((Edge)(((Path) object).objects().get(1))).sourceLabel().getClass());
//            });
//    }
    public List<Object> IdfindEdge(String Id){
        ResultSet resultSet = hugegraphDao.IdfindEdge(Id);
        Iterator<Result> results = resultSet.iterator();
        List<Object> someList = new ArrayList<>();
        results.forEachRemaining(result -> {
            Object object = result.getObject();
            HugeGraphEdge e = new HugeGraphEdge();
            System.out.println("label: " + ((Edge)object).label());
            String inID = ((Edge)object).sourceId().toString();
            String inName = getName(inID).toString();
            String outID = ((Edge)object).targetId().toString();
            String outName = getName(outID).toString();
            e.setInVName(inName);
            e.setOutVName(outName);
            e.setInVLabel(getLabel(inID).toString());
            e.setOutVLabel(getLabel(outID).toString());
//            System.out.println("inID"+ inID);
//            System.out.println("outID"+outID);
//            System.out.println("inid"+ inid);
//            System.out.println("outid"+outid);
//            System.out.println(((Edge)object).properties());
            e.setLabel(((Edge)object).label());
            e.setInV(((Edge)object).sourceId().toString());
            e.setOutV(((Edge)object).targetId().toString());
            e.setPropertie(((Edge)object).properties());
            someList.add(e);
//            System.out.println(v);
        });
        return someList;
    }

    public List<Object> getEdgeDescriptionById(String id1,String id2){
        ResultSet nameResult = hugegraphDao.getEdgeDescriptionById(id1,id2); // 通过id获取目标name
        Iterator<Result> results = nameResult.iterator();
        List<Object> someList = new ArrayList<>();
        results.forEachRemaining(result -> {
            Object object = result.getObject();
            System.out.println("object: " + object);
            someList.add(object);
        });
        return someList;
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

    public Object getLabel(String id){
        ResultSet nameResult = hugegraphDao.getLabelById(id); // 通过id获取目标name
        Iterator<Result> results = nameResult.iterator();
        List<Object> someList = new ArrayList<>();
        results.forEachRemaining(result -> {
            Object object = result.getObject();
            someList.add(object);
        });
        return someList.get(0);
    }
    public Object getProperties(String id){
        ResultSet nameResult = hugegraphDao.getVerticalPropertiesById(id); // 通过id获取目标name
        Iterator<Result> results = nameResult.iterator();
        List<Object> someList = new ArrayList<>();
        results.forEachRemaining(result -> {
            Object object = result.getObject();
            System.out.println(object);
            someList.add(object);
        });
        return someList;
    }


    public List<Object> StartEndFindEdgeLabel(String id1,String id2){
        ResultSet nameResult = hugegraphDao.StartEndFindEdgeLabel(id1,id2); // 通过id获取目标name
//        System.out.println(nameResult['paths']);
        Iterator<Result> results = nameResult.iterator();
        List<Object> someList = new ArrayList<>();
        results.forEachRemaining(result -> {
            Object object = result.getObject();
            System.out.println(object);
            someList.add(object);
        });
        return someList;
    }

    public List<Object> getEdgeVertical(String name){
        ResultSet nameResult = hugegraphDao.getEdgeVertical(name);
        Iterator<Result> results = nameResult.iterator();
        List<Object> someList = new ArrayList<>();
        results.forEachRemaining(result -> {
            Object object = result.getObject();
            HugeGraphEdge e = new HugeGraphEdge();
            // 起点ID
            String inID = ((Edge)object).sourceId().toString();
            e.setInV(inID);
            // 起点名字
//            String inName = getName(inID).toString();
//            e.setInVName(inName);
            // 终点ID
            String outID = ((Edge)object).targetId().toString();
            e.setOutV(outID);
            // 终点名字
//            String outName = getName(outID).toString();
//            e.setOutVName(outName);
            // 起始点标签
            String inLabel =((Edge)object).sourceLabel().toString();
            String outLabel = ((Edge)object).targetLabel().toString();
//            String des = ((Edge)object).properties().toString();
//            System.out.println("inID: : "+ inID);
//            System.out.println("inName: " + inName);
//            System.out.println("outID: " + outID);
//            System.out.println("outName: " + outName);
//            System.out.println("inLabel: " + inLabel);
//            System.out.println("outLabel: " + outLabel);
//            System.out.println("outLabel: " + outLabel);
//            System.out.println("inID: : "+ inLabel + " outID: " + outLabel);
//            System.out.println("inID: : "+ inID + " inName: " + inName + " outID: " + outID + " outName: " + outName + " inLabel: " + inLabel + " outLabel: " + outLabel);
            e.setInVLabel(inLabel);
            e.setOutVLabel(outLabel);
            e.setLabel(((Edge)object).label());
            e.setPropertie(((Edge)object).properties());
//            System.out.println("inID: : "+ inID + " outID: " + outID + " inLabel: " + inLabel + " outLabel: " + outLabel);
            someList.add(e);
//            someList.add(object);
        });
        return someList;
    }

}
