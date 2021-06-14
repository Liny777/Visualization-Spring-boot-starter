/**

 * Service层

 * 处理调用Restful API 从HUgeGraph数据库取数据的逻辑代码

 * @author Lin Youguang

 * @Time 2021-03-24

 *

 */
package com.cad.demo.service;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.alibaba.fastjson.JSONObject;
import com.baidu.hugegraph.structure.graph.Edge;
import com.baidu.hugegraph.structure.graph.Vertex;
import com.baidu.hugegraph.structure.gremlin.Result;
import com.baidu.hugegraph.structure.gremlin.ResultSet;
import com.cad.demo.dao.HugegraphDAO;
import com.cad.demo.dao.RestfulDAO;
import com.cad.demo.entity.*;
import com.cad.demo.entity.vo.PathBetweenTwoNode;
import com.cad.demo.entity.vo.edgedata;
import com.cad.demo.entity.vo.nodedata;
import com.cad.demo.pojo.MedicalEntity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpEntity;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import java.io.UnsupportedEncodingException;
import java.util.*;

@Service
public class RestfulService {



    @Autowired
    private RestfulDAO restfulDAO;
    @Autowired
    private NewHugegraphService newHugegraphService;
    @Autowired
    private HugegraphService hugegraphService;



    public ArrayList getVertexLabel() {
        String s = restfulDAO.getAllVertexLabel();
        ArrayList<String> t = new ArrayList<String>();
        ArrayList<ArrayList> arr = new ArrayList();
        ArrayList arr_drug = new ArrayList();
        arr_drug.add("药品");
        ArrayList arr_disease = new ArrayList();
        arr_disease.add("疾病");
        ArrayList arr_other = new ArrayList();
        arr_other.add("其他");
        ArrayList arr_patient = new ArrayList();
        arr_patient.add("患者");
        ArrayList arr_method = new ArrayList();
        arr_method.add("治疗方案");
        String[] NodeArray = s.split("name\"");
        for (int i=0; i<NodeArray.length;i++) {
             char first = NodeArray[i].charAt(0);
            if(first == ':'){
                String[] NodeArray1 = NodeArray[i].split("\"");
                if(NodeArray1[1].substring(0,2).equals("药品")){
                    arr_drug.add(NodeArray1[1]);
                }else if(NodeArray1[1].equals("联合用药")){
                    arr_drug.add(NodeArray1[1]);
                }else if(NodeArray1[1].substring(0,2).equals("药物")){
                    arr_method.add(NodeArray1[1]);
                }else if(NodeArray1[1].substring(0,2).equals("治疗")){
                    arr_method.add(NodeArray1[1]);
                }else if(NodeArray1[1].equals("给药方式")){
                    arr_method.add(NodeArray1[1]);
                }else if(NodeArray1[1].equals("一级疾病")){
                    arr_disease.add(NodeArray1[1]);
                }else if(NodeArray1[1].equals("二级疾病")){
                    arr_disease.add(NodeArray1[1]);
                }else if(NodeArray1[1].equals("三级疾病")){
                    arr_disease.add(NodeArray1[1]);
                }else if(NodeArray1[1].equals("其他疾病")){
                    arr_disease.add(NodeArray1[1]);
                }else if(NodeArray1[1].equals("患者")){
                    arr_disease.add(NodeArray1[1]);
                }else if(NodeArray1[1].equals("人群")){
                    arr_disease.add(NodeArray1[1]);
                }else {
                    arr_other.add(NodeArray1[1]);
                }

//                System.out.println(NodeArray1[1]);
//                arr.add(NodeArray1[1]);
            }
//            System.out.println(NodeArray[i]);

        }
        arr.add(arr_disease);
        arr.add(arr_method);
        arr.add(arr_patient);
        arr.add(arr_drug);
        arr.add(arr_other);
//        System.out.println(s);
        return arr;
    }

    public String[] getNode(String s) {
        String[] NodeArray = s.split(",");
        for (int i=0; i<NodeArray.length;i++) {
            if(i==0) {
                int len = NodeArray[0].length();
                NodeArray[0] = NodeArray[0].substring(14, len);
            }
            if(i==NodeArray.length-1) {
                int len1 = NodeArray[i].length() - 2;
                NodeArray[i] = NodeArray[i].substring(0, len1);
            }
//            System.out.println(NodeArray[i]);
        }
        return NodeArray;
    }
    public String[] getEdge(String s) { // {"path": [
        String[] EdgeArray = s.split(",");
        for (int i=0; i<EdgeArray.length;i++) {
            if(i==0) {
                int len = EdgeArray[0].length();
                EdgeArray[0] = EdgeArray[0].substring(10, len);
            }
            if(i==EdgeArray.length-1) {
                int len1 = EdgeArray[i].length() - 2;
                EdgeArray[i] = EdgeArray[i].substring(0, len1);
            }
//                System.out.println(EdgeArray[i]);
        }
        return EdgeArray;
    }

    public HugegraphNeighbor getNeighbour(KneighbourParams request) {
        HugegraphNeighbor ne = new HugegraphNeighbor();
        List<Object> path = new ArrayList<>();
        List<Object> datas = new ArrayList<>();
        String s = restfulDAO.getKNeighbor(request);
        String sid = request.getSource();
        String slabel = hugegraphService.getLabel(sid).toString();
        ne.setSid(sid); // 设置源节点
//            System.out.println("我想要的ID: " + sid);
        String[] node = getNode(s);
        // 将得到的邻居节点字符串进行整理后存入sourceArray
        nodedata s_1 = new nodedata();
        s_1.setId(sid);
        s_1.setLabel(slabel);
        String sname = hugegraphService.getName(sid).toString();
        s_1.setName(sname);
        datas.add(s_1);
        // 得到起始点到邻居节点的最短路径
        for (int j=0;j<node.length;j++){
            if(node[j].equals(sid) == false){
                // 添加数据节点
                nodedata data = new nodedata();
                data.setId(node[j]);
                data.setLabel(hugegraphService.getLabel(node[j]).toString());
                data.setName(hugegraphService.getName(node[j]).toString());
                datas.add(data);
                // 添加边
                String s1 = restfulDAO.getShortestPath(request.getMax_depth(),sid,node[j]);
                String[] edge = getEdge(s1);
                // k=1开始是因为k=0是源节点
                for (int k=1; k<edge.length; k++) {
                    edgedata link = new edgedata();
                    link.setDescription(hugegraphService.getEdgeDescriptionById(sid,node[j]).toString());
                    link.setHeadId(sid);
                    String relation = hugegraphService.StartEndFindEdgeLabel(sid,node[j]).toString();
                    int length_relation = relation.length()-1;
                    link.setRelation(relation.substring(1,length_relation));
                    link.setTailId(node[j]);
                    link.setHeadName(sname);
                    link.setTailName(hugegraphService.getName(node[j]).toString());
                    link.setHeadLabel(slabel);
                    link.setTailLabel(hugegraphService.getLabel(node[j]).toString());
                    path.add(link);
                }
            }

        }
        ne.setNode(datas);
        ne.setPath(path);
        return ne;
    }

    public HugeGraphPath getAllpath(String id1,String id2,int maxdepth){
        HugeGraphPath p = new HugeGraphPath();
        if(id1 != null && id1.length() != 0 && id2 != null && id2.length() != 0){
            String s = restfulDAO.getAllpath(id1,id2,maxdepth);
//                System.out.println("I get string s! " + s);
            p.setSId(id1);
            p.setEId(id2);
            String headName = hugegraphService.getName(id1).toString();
            String headLabel = hugegraphService.getLabel(id1).toString();
            p.setSLabel(headLabel);
            String tailLabel = hugegraphService.getLabel(id2).toString();
            p.setELabel(tailLabel);
            p.setSName(headName);
            String tailName = hugegraphService.getName(id2).toString();
            p.setEName(tailName);
//                System.out.println(s);

            String[] sourceArray = s.split("object");
            //sourceArray 里存了所有路径，里面是多条路
            if(sourceArray.length==0){

            }else{
                List<Object> path = new ArrayList<>();
                // i=0 是 "0{"paths":[" 所以i 从1开始
                for (int i = 1; i < sourceArray.length; i++) {
                    String[] sourceArray1 = sourceArray[i].split(",");
                    //sourceArray1 里是一条路径，里面是多段路
                    List<Object> someList = new ArrayList<>();
                    //一条路径一个someList
                    for (int j = 1; j < sourceArray1.length-2; j++){
                        PathBetweenTwoNode small_path = new PathBetweenTwoNode();
//                            System.out.println("i: "+i +" j: "+j+" sourceArray1[j]: "+sourceArray1[j]+" length: "+sourceArray1.length);
//                    //一段路（两个顶点之间）一个small_path
                        if(j==1){
                            small_path.setHeadId(id1);
                            // ..........................................
//                                small_path.setHeadDes(hugegraphService.getProperties(id1).toString());
                            small_path.setTailId(sourceArray1[j]);
                            // ..........................................
//                                small_path.setTailDes(hugegraphService.getProperties(sourceArray1[j]).toString());
                            small_path.setHeadName(headName);
                            small_path.setHeadLabel(headLabel);
                            small_path.setTailName(hugegraphService.getName(sourceArray1[j]).toString());
                            //-----------------------------------------------
                            small_path.setTailLabel(hugegraphService.getLabel(sourceArray1[j]).toString());
                            //-----------------------------------------------
                            String local_relation = hugegraphService.StartEndFindEdgeLabel(id1,sourceArray1[j]).toString();
                            if(local_relation == "[]"){
                                local_relation = hugegraphService.StartEndFindEdgeLabel(sourceArray1[j],id1).toString();
                                small_path.setDescription(hugegraphService.getEdgeDescriptionById(sourceArray1[j],id1).toString());
                            }else{
                                small_path.setDescription(hugegraphService.getEdgeDescriptionById(id1,sourceArray1[j]).toString());
                            }
//                                System.out.println("local: " + local_relation);
                            int length_relation = local_relation.length()-1;
                            small_path.setRelation(local_relation.substring(1,length_relation));
                            // ----------------------------------------------
//                                small_path.setDescription(hugegraphService.getEdgeDescriptionById(id1,sourceArray1[j]).toString());
                            //-----------------------------------------------
//                                System.out.println("small_path.getHeadName(): " + small_path.getHeadName());
//                                System.out.println("small_path.getHeadId(): " + small_path.getHeadId());
//                                System.out.println("small_path.getRelation(): " + small_path.getRelation());
//                                System.out.println("small_path.getTailName(): " + small_path.getTailName());
                        }else{
                            small_path.setHeadId(sourceArray1[j-1]);
                            // ..........................................
//                                small_path.setHeadDes(hugegraphService.getProperties(sourceArray1[j-1]).toString());
                            small_path.setTailId(sourceArray1[j]);
                            // ..........................................
//                                small_path.setTailDes(hugegraphService.getProperties(sourceArray1[j]).toString());
                            small_path.setHeadName(hugegraphService.getName(sourceArray1[j-1]).toString());
                            small_path.setTailName(hugegraphService.getName(sourceArray1[j]).toString());
                            //-----------------------------------------------
                            small_path.setHeadLabel(hugegraphService.getLabel(sourceArray1[j-1]).toString());
                            small_path.setTailLabel(hugegraphService.getLabel(sourceArray1[j]).toString());
                            //-----------------------------------------------
                            String local_relation = hugegraphService.StartEndFindEdgeLabel(sourceArray1[j-1],sourceArray1[j]).toString();
                            if(local_relation == "[]"){
                                local_relation = hugegraphService.StartEndFindEdgeLabel(sourceArray1[j],sourceArray1[j-1]).toString();
                                small_path.setDescription(hugegraphService.getEdgeDescriptionById(sourceArray1[j],sourceArray1[j-1]).toString());
                            }else{
                                small_path.setDescription(hugegraphService.getEdgeDescriptionById(sourceArray1[j-1],sourceArray1[j]).toString());
                            }
//                                System.out.println("local: " + local_relation);
                            int length_relation = local_relation.length()-1;
                            small_path.setRelation(local_relation.substring(1,length_relation));
                            // -------------------------------------------------
//                                small_path.setDescription(hugegraphService.getEdgeDescriptionById(sourceArray1[j-1],sourceArray1[j]).toString());
                            //-----------------------------------------------
                        }

                        someList.add(small_path); // 把每一段路都存进来，一个someList一条完整的路

                        //最后一段路
                        if ( sourceArray1[sourceArray1.length-1].equals("{\"") && j == sourceArray1.length-3){ //-3是把最后一个括号，首先length比起数组index多了1，所以减去1，后面减2直接定位到倒数第二个，就是除去末尾节点那个
                            PathBetweenTwoNode small_path1 = new PathBetweenTwoNode();
                            small_path1.setHeadId(sourceArray1[j]);
                            // ..........................................
//                                small_path1.setHeadDes(hugegraphService.getProperties(sourceArray1[j]).toString());
                            small_path1.setTailId(id2);
                            // ..........................................
//                                small_path1.setTailDes(hugegraphService.getProperties(id2).toString());
                            small_path1.setHeadName(hugegraphService.getName(sourceArray1[j]).toString());
                            //-----------------------------------------------
                            small_path1.setHeadLabel(hugegraphService.getLabel(sourceArray1[j]).toString());
                            //-------------------------------------------
                            small_path1.setTailName(tailName);
                            small_path1.setTailLabel(tailLabel);
                            //-----------------------------------------------
                            String local_relation = hugegraphService.StartEndFindEdgeLabel(sourceArray1[j],id2).toString();
                            if(local_relation == "[]"){
                                local_relation = hugegraphService.StartEndFindEdgeLabel(id2,sourceArray1[j]).toString();
                                small_path1.setDescription(hugegraphService.getEdgeDescriptionById(id2,sourceArray1[j]).toString());
                            }else{
                                small_path1.setDescription(hugegraphService.getEdgeDescriptionById(sourceArray1[j],id2).toString());
                            }
                            int length_relation = local_relation.length()-1;
                            small_path1.setRelation(local_relation.substring(1,length_relation));
                            // -------------------------------------------------
//                                small_path1.setDescription(hugegraphService.getEdgeDescriptionById(sourceArray1[j],id2).toString());
                            //-----------------------------------------------
                            someList.add(small_path1);
                        }
                        if(sourceArray1[sourceArray1.length-1].equals("{\"") == false){
                            PathBetweenTwoNode small_path1 = new PathBetweenTwoNode();
                            small_path1.setHeadId(sourceArray1[j]);
                            // ..........................................
//                                small_path1.setHeadDes(hugegraphService.getProperties(sourceArray1[j]).toString());
                            small_path1.setTailId(sourceArray1[j+1]);
                            // ..........................................
//                                small_path1.setTailDes(hugegraphService.getProperties(sourceArray1[j+1]).toString());
                            small_path1.setHeadName(hugegraphService.getName(sourceArray1[j]).toString());
                            small_path1.setTailName(hugegraphService.getName(sourceArray1[j+1]).toString());
                            //----------------------------------------------
                            small_path1.setHeadLabel(hugegraphService.getLabel(sourceArray1[j]).toString());
                            small_path1.setTailLabel(hugegraphService.getLabel(sourceArray1[j+1]).toString());
                            //-----------------------------------------------
                            String local_relation = hugegraphService.StartEndFindEdgeLabel(sourceArray1[j],sourceArray1[j+1]).toString();
                            if(local_relation == "[]"){
                                local_relation = hugegraphService.StartEndFindEdgeLabel(sourceArray1[j+1],sourceArray1[j]).toString();
                                small_path1.setDescription(hugegraphService.getEdgeDescriptionById(sourceArray1[j+1],sourceArray1[j]).toString());
                            }else {
                                small_path1.setDescription(hugegraphService.getEdgeDescriptionById(sourceArray1[j],sourceArray1[j+1]).toString());
                            }
                            int length_relation = local_relation.length()-1;
                            small_path1.setRelation(local_relation.substring(1,length_relation));
//                                small_path1.setDescription(hugegraphService.getEdgeDescriptionById(sourceArray1[j],sourceArray1[j+1]).toString());
                            //-----------------------------------------------
                            someList.add(small_path1);

                            PathBetweenTwoNode small_path2 = new PathBetweenTwoNode();
                            small_path2.setHeadId(sourceArray1[j+1]);
                            // ..........................................
//                                small_path2.setHeadDes(hugegraphService.getProperties(sourceArray1[j+1]).toString());
                            small_path2.setTailId(id2);
                            // ..........................................
//                                small_path2.setTailDes(hugegraphService.getProperties(id2).toString());
                            small_path2.setHeadName(hugegraphService.getName(sourceArray1[j+1]).toString());
                            //----------------------------------------------
                            small_path2.setHeadLabel(hugegraphService.getLabel(sourceArray1[j+1]).toString());
                            small_path2.setTailName(tailName);
                            small_path2.setTailLabel(tailLabel);
                            //-----------------------------------------------
                            String local_relation1 = hugegraphService.StartEndFindEdgeLabel(sourceArray1[j+1],id2).toString();
                            if(local_relation1 == "[]"){
                                local_relation1 = hugegraphService.StartEndFindEdgeLabel(id2,sourceArray1[j+1]).toString();
                                small_path2.setDescription(hugegraphService.getEdgeDescriptionById(id2,sourceArray1[j+1]).toString());
                            }else{
                                small_path2.setDescription(hugegraphService.getEdgeDescriptionById(sourceArray1[j+1],id2).toString());
                            }
//                                System.out.println("local: " + local_relation);
                            int length_relation1 = local_relation1.length()-1;
                            small_path2.setRelation(local_relation1.substring(1,length_relation1));
//                                small_path2.setDescription(hugegraphService.getEdgeDescriptionById(sourceArray1[j+1],id2).toString());
                            //-----------------------------------------------
                            someList.add(small_path2);
                        }


                        // example
                        //// i: 1 j: 0 sourceArray1[j]: s":[383437495502635008 length: 6
                        ////i: 1 j: 1 sourceArray1[j]: 383437494080765952 length: 6
                        ////i: 1 j: 2 sourceArray1[j]: 383434603748130816 length: 6
                        ////i: 1 j: 3 sourceArray1[j]: 383438751331778560 length: 6
                        ////i: 1 j: 4 sourceArray1[j]: 383438441418850304]} length: 6
                        ////i: 1 j: 5 sourceArray1[j]: {" length: 6
                    }
                    if(sourceArray1.length==3){
                        // 直达路径，两个顶点中间只有一条线
                        PathBetweenTwoNode small_path = new PathBetweenTwoNode();
                        small_path.setHeadId(id1);
                        // ..........................................
//                            small_path.setHeadDes(hugegraphService.getProperties(id1).toString());
                        small_path.setTailId(id2);
                        // ..........................................
//                            small_path.setTailDes(hugegraphService.getProperties(id2).toString());
                        small_path.setHeadName(headName);
                        small_path.setTailName(tailName);
                        small_path.setHeadLabel(headLabel);
                        small_path.setTailLabel(tailLabel);
                        //-----------------------------------------------
                        String local_relation = hugegraphService.StartEndFindEdgeLabel(id1,id2).toString();
                        if(local_relation == "[]"){
                            local_relation = hugegraphService.StartEndFindEdgeLabel(id2,id1).toString();
                            small_path.setDescription(hugegraphService.getEdgeDescriptionById(id2,id1).toString());
                        }else{
                            small_path.setDescription(hugegraphService.getEdgeDescriptionById(id1,id2).toString());
                        }
//                            System.out.println("local: " + local_relation);
                        int length_relation = local_relation.length()-1;
                        small_path.setRelation(local_relation.substring(1,length_relation));
//                            small_path.setDescription(hugegraphService.getEdgeDescriptionById(id1,id2).toString());

                        //-----------------------------------------------
                        someList.add(small_path);
                    }
                    path.add(someList);

                }
                p.setPathNode(path);
            }
            return p;
        }else{
            return p;
        }
    }

    public String getAllEdgeLabel(){
        String s = restfulDAO.getAllEdgeLabel();
        String[] sourceArray = s.split("name");
        for (int i = 0; i < sourceArray.length; i++) {
//                System.out.println(sourceArray[i]);
        }
//            System.out.println(s);
        return s;
    }

    public List<Object> getEntityNeighbor(String Id) {
//        HttpEntity<String> s = restfulDAO.getEntityNeighbor(Id);
//        MedicalEntity e = new MedicalEntity();
//        e.setId(Id);
        String s = restfulDAO.getEntityNeighbor(Id);
        System.out.println("s: " + s);
        List<Object> someList = new ArrayList<>();
        JSONObject jsonResult = null;
        jsonResult = JSONObject.parseObject(s);

        return someList;
    }

    public ArrayList getVerticesNoPage(String cate) {
        String s = restfulDAO.getVerticesNoPage(cate);
        System.out.println("zjw: "+ s);
        JSONObject jsonResult = null;
        jsonResult = JSONObject.parseObject(s);
//        JSONArray ja = jsonResult.get("vertices");
        ArrayList arr = new ArrayList();
        JSONArray ja = jsonResult.getJSONArray("vertices");
//        if ()
        Object page_object =jsonResult.get("page");
        String page;
        if (page_object == null){
            page = "null";
        } else {
            page = page_object.toString();
        }
        System.out.println("page: " + page);
        for (int i=0;i<ja.size();i++){
            JSONObject jo = ja.getJSONObject(i);
//            JSONArray property = jo.getJSONArray("properties");
            JSONObject p = jo.getJSONObject("properties");
//            Object property = jo.get("properties");
            String name = p.getString("name");
            System.out.println("properties: "+ name);
            String id = jo.getString("id");
            String label = jo.getString("label");
//            String name = property.getJSONObject(0).getString("name");
            System.out.println("id: "+id);
//            System.out.println("name: "+name);
            // 存储该节点下的所有邻居
//            List<Object> someList = getEntityNeighbor(id);
            List<Object> someList = newHugegraphService.getEntityNeighbor(id);
            if(someList.size()==0){
                HugeGraphEdge e = new HugeGraphEdge();
                e.setInV(id);
                e.setInVName(name);
                e.setInVLabel(label);
                e.setIshave(false);
                someList.add(e);
            }
            arr.add(someList);
        }
        return arr;
    }

    public ArrayList getEdgeNoPage(String cate){
        String s  = restfulDAO.getEdgeNoPage(cate);
        System.out.println("zjw: "+ s);
        JSONObject jsonResult = null;
        jsonResult = JSONObject.parseObject(s);
        ArrayList arr = new ArrayList();
        JSONArray ja = jsonResult.getJSONArray("edges");
        for (int i=0;i<ja.size();i++){
            JSONObject jo = ja.getJSONObject(i);
            JSONObject p = jo.getJSONObject("properties");
            String objectname = p.getString("object");
            String subjectname = p.getString("subject");
            String label = jo.getString("label");
            String id = jo.getString("id");
            String inV = jo.getString("inV");
            String outV = jo.getString("outV");
            String inVLabel = jo.getString("inVLabel");
            String outVLabel = jo.getString("outVLabel");
            String proper = jo.getString("properties");
            HugeGraphEdge e = new HugeGraphEdge();
            e.setInVName(subjectname);
            e.setInV(inV);
            e.setInVLabel(inVLabel);
            e.setLabel(label);
            e.setOutV(outV);
            e.setOutVName(objectname);
            e.setOutVLabel(outVLabel);
            e.setProper(jo.getString("properties"));
            arr.add(e);

        }
        return arr;
    }

    public ArrayList getEdgeLabel() {
        String s = restfulDAO.getEdgeLabel();
        JSONObject jsonResult = null;
        jsonResult = JSONObject.parseObject(s);
        System.out.println(s);
        ArrayList arr = new ArrayList();
        JSONArray ja = jsonResult.getJSONArray("edgelabels");
        for (int i=0;i<ja.size();i++){
            JSONObject jo = ja.getJSONObject(i);
//            JSONObject p = jo.getJSONObject("properties");
//            String objectname = p.getString("object");
//            String subjectname = p.getString("subject");
            String label = jo.getString("name");
            int index_in = label.indexOf('（');
            if(index_in != -1) {
                label = label.substring(0,index_in);
            }
            System.out.println();
            String id = jo.getString("id");
//            String inV = jo.getString("inV");
//            String outV = jo.getString("outV");
            String inVLabel = jo.getString("source_label");
            String outVLabel = jo.getString("target_label");
//            String proper = jo.getString("properties");
            HugeGraphEdge e = new HugeGraphEdge();
//            e.setInVName(subjectname);
//            e.setInV(inV);
            e.setInVLabel(inVLabel);
            e.setLabel(label);
//            e.setOutV(outV);
//            e.setOutVName(objectname);
            e.setOutVLabel(outVLabel);
//            e.setProper(jo.getString("properties"));
            arr.add(e);

        }
        return arr;
//        return s;
    }
}
