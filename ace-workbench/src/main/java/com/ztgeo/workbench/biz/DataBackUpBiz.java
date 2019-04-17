package com.ztgeo.workbench.biz;

import com.alibaba.fastjson.JSONObject;
import com.github.ag.core.context.BaseContextHandler;
import com.github.wxiaoqi.security.common.msg.TableResultResponse;
import com.mongodb.BasicDBObject;
import com.mongodb.MongoClient;
import com.mongodb.QueryOperators;
import com.mongodb.client.FindIterable;
import com.mongodb.client.MongoCollection;
import com.mongodb.client.MongoCursor;
import com.mongodb.client.MongoDatabase;
import com.ztgeo.workbench.entity.ApiBaseInfo;
import com.ztgeo.workbench.entity.HttpEntity;
import com.ztgeo.workbench.entity.NoticeEntity;
import com.ztgeo.workbench.entity.NoticeRecord;
import com.ztgeo.workbench.mapper.DataBackUpMapper;
import org.bson.codecs.configuration.CodecRegistries;
import org.bson.codecs.configuration.CodecRegistry;
import org.bson.codecs.pojo.PojoCodecProvider;
import org.mapstruct.Mapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import java.time.LocalDateTime;
import java.time.ZoneOffset;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import com.ztgeo.workbench.mapper.DataBackUpMapper;
@Service
public class DataBackUpBiz {

    @Autowired
    private MongoClient mongoClient;
    @Value("${customAttributes.dbName}")
    private String dbName; // 存储用户发送数据的数据库名
    @Value("${customAttributes.dbnoticeName}")
    private String dbnoticeName; // 存储用户发送数据的数据库名

    /**
     * 查询规定日期内的接口访问详情
     */
    public TableResultResponse<JSONObject> getApiAccessDataDetail(Map<String, Object> params) {
        // 获得开始和结束时间
        Object startTime = params.get("startTime");
        Object endTime = params.get("endTime");
        // 获得分页条件
        int pageNo = Integer.valueOf(params.get("page").toString());
        int pageSize = Integer.valueOf(params.get("limit").toString());
        // 连接mongoDB
        CodecRegistry pojoCodecRegistry = CodecRegistries.fromRegistries(MongoClient.getDefaultCodecRegistry(),
                CodecRegistries.fromProviders(PojoCodecProvider.builder().automatic(true).build()));
        MongoDatabase mongoDB = mongoClient.getDatabase(dbName).withCodecRegistry(pojoCodecRegistry);
        MongoCollection<HttpEntity> storeCollection = mongoDB.getCollection(BaseContextHandler.getUsername(), HttpEntity.class);
        BasicDBObject condition = new BasicDBObject();
        condition.append("sendUserID",BaseContextHandler.getUserID());
        if (!Objects.equals(startTime,null) && !Objects.equals(endTime,null)) { // 添加查询条件： 开始时间、结束时间
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
            long startSeconds = LocalDateTime.parse(String.valueOf(startTime),dateTimeFormatter).toInstant(ZoneOffset.of("+8")).getEpochSecond();
            long endSeconds = LocalDateTime.parse(String.valueOf(endTime),dateTimeFormatter).toInstant(ZoneOffset.of("+8")).getEpochSecond();
            condition.append(QueryOperators.AND,new BasicDBObject[]{ new BasicDBObject().append("currentTime", new BasicDBObject(QueryOperators.GTE,startSeconds)),new BasicDBObject().append("currentTime", new BasicDBObject(QueryOperators.LTE,endSeconds))});
        }
        FindIterable<HttpEntity> findIterable = storeCollection.find(condition).skip((pageNo-1)*pageSize).limit(pageSize);
        MongoCursor mongoCursor = findIterable.iterator();
        // 预存数据列表
        List<JSONObject> list = new ArrayList<>();
        while(mongoCursor.hasNext()){
            HttpEntity httpEntity = (HttpEntity) mongoCursor.next();
            JSONObject obj = new JSONObject();
            obj.put("apiName",httpEntity.getApiName()); // 访问的服务名称
            obj.put("apiPath",httpEntity.getApiPath()); // 访问服务路径
            obj.put("contentType",httpEntity.getContentType());
            obj.put("method",httpEntity.getMethod());
            obj.put("params",httpEntity.getParams());
            DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyy/MM/dd HH:mm:ss");
            LocalDateTime localDateTime = LocalDateTime.ofEpochSecond(httpEntity.getCurrentTime(),0, ZoneOffset.of("+8"));
            obj.put("currentTime",dtf.format(localDateTime));
            obj.put("sendData",httpEntity.getSendBody()); // 发送的数据
            obj.put("receiveData",httpEntity.getReceiveBody()); // 接收的数据
            list.add(obj);
        }
        return new TableResultResponse<>(list.size(),list);
    }

}
