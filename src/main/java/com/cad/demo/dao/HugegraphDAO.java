/**

 * Dao层

 * 使用Gremlin语言访问HugeGraph数据库

 * @author Lin Youguang

 * @Time 2021-03-24

 *

 */
package com.cad.demo.dao;

import com.baidu.hugegraph.driver.GremlinManager;
import com.baidu.hugegraph.driver.HugeClient;
import com.baidu.hugegraph.driver.SchemaManager;
import com.baidu.hugegraph.structure.gremlin.ResultSet;
import org.springframework.stereotype.Repository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
public class HugegraphDAO {
    HugeClient hugeClient = new HugeClient("http://114.67.200.39:44640","hugegraph1");
    SchemaManager schema = hugeClient.schema();
    GremlinManager gremlin = hugeClient.gremlin();

    //获取实体类型及数量
    public ResultSet getEntityNum() {return gremlin.gremlin("g.V().label().groupCount()").execute();}
    //获取关系类型及数量
    public ResultSet getRelationNum() {return gremlin.gremlin("g.E().label().groupCount()").execute();}
    //返回所有实体
    public ResultSet getAllClass(){
        return gremlin.gremlin("g.V().limit(500)").execute();
    }
    //返回所有标签
    public ResultSet getAllLabel() {return gremlin.gremlin("g.V().label()").execute();}
    //通过指定边的入顶点找到出顶点
//    public ResultSet getEdgeNode(String inV){return gremlin.gremlin("g.E().outV().hasLabel('"+inV+"')").execute();}
    public ResultSet getEdgeNode(String inV){return gremlin.gremlin("g.V().hasLabel('"+inV+"').inE().outV().path()").execute();}
    //获取所有边
//    public ResultSet getAllEdge() {return gremlin.gremlin("g.E()").execute();}

    public ResultSet getNameById(String id){
        return gremlin.gremlin("g.V().hasId("+ id +").values('name')").execute();
    }
    public ResultSet getLabelById(String id){
        return gremlin.gremlin("g.V().hasId("+ id +").label()").execute();
    }

    public ResultSet FromLabeltoFindV(String category){
//        System.out.println("666666666666666666666");
//        System.out.println("g.V().hasLabel('"+category+"')");
        return gremlin.gremlin("g.V().hasLabel('"+category+"').limit(500)").execute();
    }

    public ResultSet IdfindEdge(String id){
        System.out.println("g.V("+id+").outE()");
        return gremlin.gremlin("g.V("+id+").outE()").execute();
    }

    public ResultSet StartEndFindEdgeLabel(String id1,String id2){
//        g.V(383437495502635008).outE().filter(inV().hasId(383438441418850304)).label()
//        return gremlin.gremlin("g.V("+id1+").repeat(both().simplePath()).until(hasId("+id2+").and().loops().is(lte(4))).hasId("+id2+")).path()").execute();
        //.or().loops()).hasId("+id2+")
//        return gremlin.gremlin("g.V("+id1+").hasId("+id2+").path().limit(1)").execute();
//        System.out.println("id1: "+id1);
//        System.out.println("id2: "+id2);
//        String s = Long.toString(383437495502635008L);
//        return gremlin.gremlin("g.V("+s+").outE()").execute();
//        return gremlin.gremlin("g.V("+id1+").both().hasId("+id2+").values()").execute();
//        return gremlin.gremlin("g.V("+id1+").outV("+id2+")").execute();
        return gremlin.gremlin("g.V("+id1+").bothE().filter(inV().hasId("+id2+")).label()").execute();
        ///g.V(383437495502635008).outE().inV().hasId(383438441418850304)
        //g.V(383437495502635008).outE().filter(inV().hasId(383438441418850304)).label()
//        g.V(383437495502635008).bothE().filter(inV().hasId(383438441418850304)).label()
    }
    public ResultSet getEdgeDescriptionById(String id1,String id2){
        return gremlin.gremlin("g.V("+id1+").bothE().filter(inV().hasId("+id2+")).properties()").execute();
    }
    public ResultSet getVerticalPropertiesById(String id){
        return gremlin.gremlin("g.V().hasId("+ id +").properties()").execute();
    }
    public ResultSet getEdgeVertical(String name){
        return gremlin.gremlin("g.E().hasLabel('"+name+"')").execute();
    }


}
