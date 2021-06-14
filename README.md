# Visualization-Spring-boot-starter
This is the back end.

# 代码目录结构
主要分为三部分，controller层用于写与前端交互的接口同时调用service服务，service层用于写与逻辑代码，即从dao层取出数据后对数据进行处理。Dao层则用于从数据库取出数据。Pojo为过程中可能使用的对象

# Hugegraph获取数据方法
可以直接看dao层，
主要有两种，一种是直接编写gremlin语句进行访问，另一种是调用hugegraph提供的restful api。
如果是调用restfulapi的，如下，需要配合使用restTemplate,拿到数据后在强转成json对象，然后在service层对数据进行整合成前端所需数据格式
