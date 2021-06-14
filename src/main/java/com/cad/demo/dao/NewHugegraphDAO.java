package com.cad.demo.dao;


import com.baidu.hugegraph.driver.GremlinManager;
import com.baidu.hugegraph.driver.HugeClient;
import com.baidu.hugegraph.driver.SchemaManager;
import org.springframework.stereotype.Repository;
import com.baidu.hugegraph.structure.gremlin.ResultSet;


@Repository
public class NewHugegraphDAO {
    HugeClient hugeClient = new HugeClient("http://114.67.200.39:44640","hugegraph1");
    SchemaManager schema = hugeClient.schema();
    GremlinManager gremlin = hugeClient.gremlin();

    public ResultSet getEntity(String category) {
        return gremlin.gremlin("g.V().hasLabel('"+category+"').limit(500)").execute();
    }

    public ResultSet getEntityNeighbor(String id) {
        return gremlin.gremlin("g.V('"+id+"').outE()").execute();
    }

    public ResultSet getLabelById(String id){
        return gremlin.gremlin("g.V().hasId('"+ id +"').label()").execute(); // 注意加双引号
    }

    public ResultSet getEntityLittle(String category) {
        return gremlin.gremlin("g.V().hasLabel('"+category+"').limit(20)").execute();
    }

    public ResultSet getNameById(String id){
        return gremlin.gremlin("g.V().hasId('"+ id +"').values('name')").execute();
    }


}
