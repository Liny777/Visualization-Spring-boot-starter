/**

 * Dao层

 * 使用HugeGraph提供的Restful API 访问HugeGraph数据库

 * @author Lin Youguang

 * @Time 2021-03-24

 *

 */
package com.cad.demo.dao;

import com.alibaba.fastjson.JSONObject;
import com.cad.demo.entity.KneighbourParams;
//import org.apache.http.HttpEntity;
import com.cad.demo.pojo.MedicalEntity;
import org.springframework.http.HttpEntity;
import org.apache.http.client.ClientProtocolException;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPut;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.MediaType;
import org.springframework.http.client.OkHttp3ClientHttpRequestFactory;
import org.springframework.http.converter.HttpMessageConverter;
import org.springframework.http.converter.StringHttpMessageConverter;
import org.springframework.stereotype.Repository;
import org.springframework.util.LinkedMultiValueMap;
import org.springframework.util.MultiValueMap;
import com.cad.demo.RestTemplateConfig;
import org.springframework.util.ObjectUtils;
import org.springframework.web.client.RestTemplate;
//import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.io.UnsupportedEncodingException;
import java.net.URI;
import java.net.URLEncoder;
import java.nio.charset.Charset;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;

import java.util.List;
import java.util.Map;


@Repository
public class RestfulDAO {

    @Autowired
    private RestTemplate restTemplate;
//    @Before
//    public void init() {
//        restTemplate = new RestTemplate();
//    }
public String getEdgeLabel(){
    String url = "http://114.67.200.39:44640/graphs/hugegraph1/schema/edgelabels/";
//    url = url + name;
    return restTemplate.getForObject(url,String.class);

}
    public String getAllpath(String id1,String id2,int maxdepth){
        Long id_1 = Long.parseLong(id1);
        Long id_2 = Long.parseLong(id2);
        String url = "http://114.67.200.39:44640/graphs/hugegraph/traversers/paths?source={id1}&target={id2}&max_depth={maxdepth}";
        Map<String, Object> params = new HashMap<>();
        params.put("id1", id_1);
        params.put("id2", id_2 );
        params.put("maxdepth",maxdepth);
        return restTemplate.getForObject(url, String.class, params);
    }
    public String getAllEdgeLabel(){
        String url = "http://114.67.200.39:44640/graphs/hugegraph/schema/edgelabels";
          return restTemplate.getForObject(url,String.class);
    }
    public String getKNeighbor(KneighbourParams request){
        String url = "http://114.67.200.39:44640/graphs/hugegraph/traversers/kneighbor?source={id}&max_depth={maxdepth}";
        Long id = Long.parseLong(request.getSource());
        Map<String, Object> params = new HashMap<>();
        params.put("id", id);
        params.put("maxdepth",request.getMax_depth());
//        params.put("maxdepth",3);
        return restTemplate.getForObject(url, String.class, params);
//        http://localhost:8080/graphs/{graph}/traversers/kneighbor?source=“1:marko”&max_depth=2
    }
    public  String getShortestPath(int maxdepth, String id1,String id2){
        Long id_1 = Long.parseLong(id1);
        Long id_2 = Long.parseLong(id2);
        String url = "http://114.67.200.39:44640/graphs/hugegraph/traversers/shortestpath?source={id1}&target={id2}&max_depth={maxdepth}";
        Map<String, Object> params = new HashMap<>();
        params.put("id1", id_1);
        params.put("id2", id_2 );
        params.put("maxdepth",maxdepth);
        return restTemplate.getForObject(url, String.class, params);
    }
    public String getAllVertexLabel(){
        String url = "http://114.67.200.39:44640/graphs/hugegraph1/schema/vertexlabels";
        return restTemplate.getForObject(url,String.class);
    }

    public String getEntityNeighbor(String id1) {
        String s = "\"" + id1 + "\"";
        String url = "http://114.67.200.39:44640/graphs/hugegraph1/traversers/kout?source=" + s + "&max_depth=1";
        return restTemplate.getForObject(url, String.class);
    }

    public String getVerticesNoPage(String cate) {
        String url = "http://114.67.200.39:44640/graphs/hugegraph1/graph/vertices?label={cate}&page&limit=3";
        Map<String, Object> params = new HashMap<>();
        params.put("cate", cate);
        StringHttpMessageConverter m = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        RestTemplate restTemplate = new RestTemplateBuilder().additionalMessageConverters(m).build();
        return restTemplate.getForObject(url,String.class,params);
    }

    public String getVerticesPage(String cate, String pageNum) {
        String url = "http://114.67.200.39:44640/graphs/hugegraph1/graph/vertices?label={cate}&page={pageNum}&limit=5";
        Map<String, Object> params = new HashMap<>();
        params.put("cate", cate);
        params.put("pageNum", pageNum);
        return restTemplate.getForObject(url,String.class,params);
    }

    public String getEdgeNoPage(String cate) {
        String s = "\"" + cate + "\"";
        String url = "http://114.67.200.39:44640/graphs/hugegraph1/graph/edges?direction=BOTH&label="+cate+"&limit=10";
        StringHttpMessageConverter m = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        RestTemplate restTemplate = new RestTemplateBuilder().additionalMessageConverters(m).build();
        return restTemplate.getForObject(url,String.class);
    }

    public String getProper(String id) {
        String s = "\"" + id + "\"";
        String url = "http://114.67.200.39:44640/graphs/hugegraph1/graph/vertices/" + s;
        StringHttpMessageConverter m = new StringHttpMessageConverter(Charset.forName("UTF-8"));
        RestTemplate restTemplate = new RestTemplateBuilder().additionalMessageConverters(m).build();
        return restTemplate.getForObject(url,String.class);
    }

}
