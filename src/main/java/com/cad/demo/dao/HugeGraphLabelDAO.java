package com.cad.demo.dao;
import com.baidu.hugegraph.driver.GremlinManager;
import com.baidu.hugegraph.driver.HugeClient;
import com.baidu.hugegraph.driver.SchemaManager;
import com.baidu.hugegraph.structure.gremlin.ResultSet;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Repository
@Mapper
public class HugeGraphLabelDAO {
    HugeClient hugeClient = new HugeClient("http://114.67.200.39:44640", "hugegraph1");
    SchemaManager schemaManager = hugeClient.schema();
    GremlinManager gremlinManager = hugeClient.gremlin();

    public ResultSet getNodes(String category) {
        System.out.println(category);
        return gremlinManager.gremlin("g.V().hasLabel('" + category + "').limit(50)").execute();
    }

    public ResultSet getEdges(String category) {
        System.out.println(category);
        return gremlinManager.gremlin("g.E().hasLabel('" + category + "').limit(30)").execute();
    }

    public ResultSet getVerticalById(String id){
        System.out.println(id);
        if (judgeContainsStr(id)) {
            id = "\"" + id + "\"";
        }
        return gremlinManager.gremlin("g.V().hasId("+ id +")").execute();
    }

    public static boolean judgeContainsStr(String str) {
        String regex=".*[a-zA-Z]+.*";
        Matcher m= Pattern.compile(regex).matcher(str);
        return m.matches();
    }


}
