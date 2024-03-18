## rcfs-caihx 动态刷新本地配置
> 简单的实现springboot运行时 配置的动态刷新
### 1.当前更新版本 最新 1.0.0
```text
1.0.1-beta
1.0.2-beta
1.0.3-beta
1.0.4-beta
1.0.0
```
### 2.Maven
```xml
        <dependency>
            <groupId>io.github.caihxai</groupId>
            <artifactId>rcfs-core</artifactId>
            <version>1.0.0</version>
        </dependency>
```
### 3.设置配置信息[application.properties/application.yml]
```yaml
spring:
  rcfs:
    config:
      resources: D:\Project Code\code\rcfs-caihx\rcfs-core\src\main\resources
      enable: true
      mode: watch
      watch:
        time: 3000
        file:
          - aaa.yml
          - bbb.yml
      client:
        path: /update
        port: 7001
```
