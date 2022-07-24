package com.example.one.metrics;

import cn.hutool.json.JSONUtil;
import com.mongodb.event.*;
import io.micrometer.core.annotation.Incubating;
import io.micrometer.core.instrument.MeterRegistry;
import io.micrometer.core.instrument.Timer;
import io.micrometer.core.lang.NonNullApi;
import io.micrometer.core.lang.NonNullFields;

import java.util.concurrent.TimeUnit;
/**
 * {@link CommandListener} for collecting command metrics from {@link MongoClient}.
 *
 * @author Christophe Bornet
 * @since 1.2.0
 */
@NonNullApi
@NonNullFields
@Incubating(since = "1.2.0")
public class MyMongoMetricsCommandListener implements CommandListener {

    private final Timer.Builder timerBuilder = Timer.builder("mongodb.driver.commands")
            .description("Timer of mongodb commands");

    private final MeterRegistry registry;

    public MyMongoMetricsCommandListener(MeterRegistry registry) {
        this.registry = registry;
    }

    @Override
    public void commandStarted(CommandStartedEvent commandStartedEvent) {
        timeCommand(commandStartedEvent, "START", 0);
    }

    @Override
    public void commandSucceeded(CommandSucceededEvent event) {
        timeCommand(event, "SUCCESS", event.getElapsedTime(TimeUnit.NANOSECONDS));
    }

    @Override
    public void commandFailed(CommandFailedEvent event) {
        timeCommand(event, "FAILED", event.getElapsedTime(TimeUnit.NANOSECONDS));
    }

    private void timeCommand(CommandEvent event, String status, long elapsedTimeInNanoseconds) {
        System.out.println("my mongodb metrics");
        // com.mongodb.connection.ConnectionDescription
        System.out.println(event.getConnectionDescription().getClass().getName());
        // {"response":{"cursor":{"firstBatch":[{"_id":{"value":{"timestamp":1658570012}},"title":{"value":"MongoDB 教程"},"description":{"value":"MongoDB 是一个 Nosql 数据库"},"by":{"value":"菜鸟教程"},"url":{"value":"http://www.runoob.com"},"tags":[{"value":"mongodb"},{"value":"database"},{"value":"NoSQL"}],"likes":{"value":100}}],"id":{"value":0},"ns":{"value":"run.user"}},"ok":{"value":1}},"requestId":10,"connectionDescription":{"connectionId":{"serverId":{"clusterId":{"value":"62dd05a9fb60f143f419efc3"},"address":{"host":"127.0.0.1","port":27017}},"localValue":2,"serverValue":73},"maxWireVersion":13,"serverType":"STANDALONE","maxBatchCount":100000,"maxDocumentSize":16777216,"maxMessageSize":48000000,"compressors":[]},"commandName":"find"}
        System.out.println(JSONUtil.toJsonStr(event));
        String s = "";
        if(event instanceof CommandStartedEvent){
            System.out.println(((CommandStartedEvent)event).getDatabaseName());
            s = ((CommandStartedEvent)event).getDatabaseName();
        }
        if(event instanceof CommandSucceededEvent){
            // {"cursor": {"firstBatch": [{"_id": {"$oid": "62dbc51cefe3d31a5e688a66"}, "title": "MongoDB 教程",..}], "id": 0, "ns": "run.user"}, "ok": 1.0}
            System.out.println(((CommandSucceededEvent)event).getResponse().toJson());
        }
        // {"connectionId":{"serverId":{"clusterId":{"value":"62dd05a9fb60f143f419efc3"},"address":{"host":"127.0.0.1","port":27017}},"localValue":2,"serverValue":73},"maxWireVersion":13,"serverType":"STANDALONE","maxBatchCount":100000,"maxDocumentSize":16777216,"maxMessageSize":48000000,"compressors":[]}
        System.out.println(JSONUtil.toJsonStr(event.getConnectionDescription()));
        // /Users/xuweiqiang/apache-maven-3.8.4/maven-repo/org/mongodb/bson/4.0.5/bson-4.0.5.jar!/org/bson/types/ObjectId.class
        timerBuilder
                .tag("database",s)
                .tag("command", event.getCommandName())
                .tag("cluster.id", event.getConnectionDescription().getConnectionId().getServerId().getClusterId().getValue())
                .tag("server.address", event.getConnectionDescription().getServerAddress().toString())
                .tag("status", status)
                .register(registry)
                .record(elapsedTimeInNanoseconds, TimeUnit.NANOSECONDS);
    }

}