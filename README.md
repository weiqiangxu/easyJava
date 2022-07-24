# easyJava

### spring项目构建

新建模块 - Spring Init - spring web


### Spring知识点

1. spring valid
2. spring elastic
3. spring params 读取
4. feign调用
5. spring MySQL \ MyBatic \ MyBatic Plus
6. spring mongo
7. spring 日志
8. spring cloud kubernetes
9. spring 文件上传下载
10. kafka
11. spring配置时区、json时区、编码格式
12. 编码安全-参数规范、防注入
13. 异常处理
14. spring security
15. test应用
16. K8s
17. oauth2
18. 过滤器javax.servlet.Filter
19. 拦截器handlerInterceptor
20. spring异常处理机制
21. spring i18n
22. Message Queue
23. springBoot配置注入
24. spring activity
25. hu tool
26. AOP切面
27. self4j日志-log4j深度学习 https://www.bilibili.com/video/BV1iJ411H74S?p=11
28. 自定义注解切面日志
29. 自定义注解赋值对象
30. 自定义注解生成代码
31. Logstash入门 https://www.bilibili.com/video/BV1q64y1u7qt?p=11
32. Filebeat配置
33. com.alibaba.ttl.TransmittableThreadLocal
34. MDC
35. com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper如何实现获取class的key的
36. @Async\@EnableAsync 异步会开多线程?
37. spring.factories定义扫描类
38. TaskDecorator\threadPoolTaskExecutor线程池
39. prometheus

https://developpaper.com/springboot-project-traceid-generation-log-printing/

### Java基础知识

1. alibaba fastjson、jackson
2. 数据结构枚举、集合、字典、哈希
3. IDEA各个功能按钮和快捷键
4. Maven
5. java debug调试工具使用
6. lombok builder

### 备注
JDK 8 中有关反射相关的功能自从 JDK 9 开始就已经被限制了，为了兼容原先的版本，需要在运行项目时添加 --add-opens java.base/java.lang=ALL-UNNAMED 选项来开启这种默认不被允许的行为

### mongo指标

```
# HELP mongodb_driver_commands_seconds_max Timer of mongodb commands
# TYPE mongodb_driver_commands_seconds_max gauge
mongodb_driver_commands_seconds_max{application="one",cluster_id="62dbd0067c80cb0e7c84d5ab",command="buildInfo",server_address="127.0.0.1:27017",status="SUCCESS",} 0.008892208
# HELP mongodb_driver_commands_seconds Timer of mongodb commands
# TYPE mongodb_driver_commands_seconds summary
mongodb_driver_commands_seconds_count{application="one",cluster_id="62dbd0067c80cb0e7c84d5ab",command="buildInfo",server_address="127.0.0.1:27017",status="SUCCESS",} 1.0
mongodb_driver_commands_seconds_sum{application="one",cluster_id="62dbd0067c80cb0e7c84d5ab",command="buildInfo",server_address="127.0.0.1:27017",status="SUCCESS",} 0.008892208
```

### 手册地址

```
https://docs.spring.io/spring-boot/docs/
https://docs.spring.io/spring-boot/docs/2.3.7.RELEASE/actuator-api/html/
https://docs.spring.io/spring-boot/docs/current/api/overview-summary.html
```

### 掠影

> https://www.sstz.info/dashboard/index
