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
        System.out.println(event.getConnectionDescription().getClass().getName());
        System.out.println(JSONUtil.toJsonStr(event));
        String s = "";
        if(event instanceof CommandStartedEvent){
            System.out.println(((CommandStartedEvent)event).getDatabaseName());
            s = ((CommandStartedEvent)event).getDatabaseName();
        }
        if(event instanceof CommandSucceededEvent){
            System.out.println(((CommandSucceededEvent)event).getResponse().toJson());
        }
        System.out.println(JSONUtil.toJsonStr(event.getConnectionDescription()));
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