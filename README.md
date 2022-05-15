### mysql启动

### nacos启动
```bash
bin/startup.sh -m standalone
```

### zipkin启动

### seata启动

### seata集成
* 依赖
```xml
<dependency>
    <groupId>com.alibaba.cloud</groupId>
    <artifactId>spring-cloud-starter-alibaba-seata</artifactId>
</dependency>
<dependency>
    <groupId>com.zaxxer</groupId>
    <artifactId>HikariCP</artifactId>
    <optional>true</optional>
</dependency>
```
* 业务数据库创建undo_log表
```sql
-- 
-- Set character set the client will use to send SQL statements to the server
--
SET NAMES 'utf8';

--
-- Create table `undo_log`
--
CREATE TABLE undo_log (
                          branch_id bigint(20) NOT NULL COMMENT 'branch transaction id',
                          xid varchar(100) NOT NULL COMMENT 'global transaction id',
                          context varchar(128) NOT NULL COMMENT 'undo_log context,such as serialization',
                          rollback_info longblob NOT NULL COMMENT 'rollback info',
                          log_status int(11) NOT NULL COMMENT '0:normal status,1:defense status',
                          log_created datetime(6) NOT NULL COMMENT 'create datetime',
                          log_modified datetime(6) NOT NULL COMMENT 'modify datetime'
)
    ENGINE = INNODB,
    AVG_ROW_LENGTH = 16384,
    CHARACTER SET utf8,
    COLLATE utf8_general_ci,
    COMMENT = 'AT transaction mode undo table';

--
-- Create index `ux_undo_log` on table `undo_log`
--
ALTER TABLE undo_log
    ADD UNIQUE INDEX ux_undo_log (xid, branch_id);
```

* 配置事务分组
```
若应用程序是SpringBoot则通过seata.tx-service-group 配置
若应用程序是SpringBoot则通过seata.service.vgroup-mapping.事务分组名=集群名称 配置
seata.registry.type=file ----> 不推荐在正式环境使用
seata.service.grouplist.集群名称=127.0.0.1:8091 ----> vgroup-mapping（服务端cluster）各个seata-server节点信息
```
* 配置数据源代理（seata）
```java

@Configuration
public class DataSourceProxyAutoConfiguration {
    @Autowired
    private DataSourceProperties dataSourceProperties;

    @Primary
    @Bean("dataSourceProxy")
    public DataSource dataSource(){
        HikariDataSource dataSource = new HikariDataSource();
        dataSource.setJdbcUrl(dataSourceProperties.getUrl());
        dataSource.setUsername(dataSourceProperties.getUsername());
        dataSource.setPassword(dataSourceProperties.getPassword());
        dataSource.setDriverClassName(dataSourceProperties.getDriverClassName());
//        return dataSource;
        return new DataSourceProxy(dataSource);
    }
} 
```
* 添加拦截器[optional]
```java
registry.addInterceptor(new SeataHandlerInterceptor())
        .addPathPatterns("/**");
```
* 使用GlobalTransactional注解

### kafka启动

### rocketmq启动

### sentinel dashboard启动
```shell
java -Xms128M -Xmx128M -Dserver.port=7777 -Dcsp.sentinel.dashboard.server=localhost:7777 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.8.4.jar
```

### sentinel 客户端集成(网关限流)
* 依赖
```xml
       <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-starter-alibaba-sentinel</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba.cloud</groupId>
            <artifactId>spring-cloud-alibaba-sentinel-gateway</artifactId>
        </dependency>
        <dependency>
            <groupId>com.alibaba.csp</groupId>
            <artifactId>sentinel-datasource-nacos</artifactId>
        </dependency>
```

* 注册到dashboard
```yaml
spring:
  cloud:
    sentinel:
      eager: true
      transport:
        port: 8720
        dashboard: localhost:7777
```
* dashboard添加网关支持 
**-Dcsp.sentinel.app.type=1**  
`java -Xms128M -Xmx128M -Dcsp.sentinel.app.type=1 -Dserver.port=7777 -Dcsp.sentinel.dashboard.server=localhost:7777 -Dproject.name=sentinel-dashboard -jar sentinel-dashboard-1.8.4.jar
`

* 配置（按照routeid限流）
```java
@Configuration
public class SentinelGatewayConfiguration {
    private final List<ViewResolver> viewResolvers;
    private final ServerCodecConfigurer serverCodecConfigurer;

    public SentinelGatewayConfiguration(
            ObjectProvider<List<ViewResolver>> viewResolversProvider,
            ServerCodecConfigurer serverCodecConfigurer) {
        this.viewResolvers = viewResolversProvider.getIfAvailable(Collections::emptyList);
        this.serverCodecConfigurer = serverCodecConfigurer;
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public SentinelGatewayBlockExceptionHandler sentinelGatewayBlockExceptionHandler() {
        // 默认 code:429
        return new SentinelGatewayBlockExceptionHandler(viewResolvers, serverCodecConfigurer);
    }

    @Bean
    @Order(Ordered.HIGHEST_PRECEDENCE)
    public GlobalFilter sentinel() {
        return new SentinelGatewayFilter();
    }

    @PostConstruct
    public void init() {
        log.info("----------------begin init Sentinel Gateway Rules-------------------------------");
        log.info("load sentinel gateway rules (hard code)");
        initGatewayRules();
        log.info("----------------end   init Sentinel Gateway Rules-------------------------------");
    }

    private void initGatewayRules() {
        Set<GatewayFlowRule> gatewayFlowRules = new HashSet<>();

        GatewayFlowRule rule = new GatewayFlowRule();
        rule.setResourceMode(SentinelGatewayConstants.RESOURCE_MODE_ROUTE_ID);
        rule.setResource("ecommerce-nacos-client");
        rule.setGrade(RuleConstant.FLOW_GRADE_QPS);
        rule.setIntervalSec(60);
        rule.setCount(3);

        gatewayFlowRules.add(rule);

        GatewayRuleManager.loadRules(gatewayFlowRules);
    }
}
```

* 自定义BlockExceptionHandler
```java
    private void initBlockHandler(){
        BlockRequestHandler handler = new BlockRequestHandler() {
            @Override
            public Mono<ServerResponse> handleRequest(ServerWebExchange serverWebExchange, Throwable throwable) {
                log.error("------------trigger gateway sentinel rule-------------");
                Map<String, String> result = new HashMap<>();
                result.put("code", String.valueOf(HttpStatus.TOO_MANY_REQUESTS));
                result.put("message", HttpStatus.TOO_MANY_REQUESTS.getReasonPhrase());
                result.put("route", "ecommerce-nacos-client");
                return ServerResponse.status(HttpStatus.TOO_MANY_REQUESTS)
                        .contentType(MediaType.APPLICATION_JSON)
                        .body(BodyInserters.fromValue(result));
            }
        };

        GatewayCallbackManager.setBlockHandler(handler);
    }
```
