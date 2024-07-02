#server.address=0.0.0.0
logging.level.org.springframework.cloud.commons.util.InetUtils= DEBUG
eureka.client.serviceUrl.defaultZone = http://localhost:8761/eureka
eureka.client.register-with-eureka=true
eureka.client.fetch-registry=true
eureka.client.healthcheck.enabled=false
eureka.client.instance-info-replication-interval-seconds=30
logging.level.com.netflix.eureka=OFF
logging.level.com.netflix.discovery=OFF
spring.cloud.inetutils.timeout-seconds: 10
#/eureka
server.port=8084
spring.application.name = to-do-list-app
#spring.datasource.driver-class-name =com.mysql.jdbc.Driver
#spring.datasource.url=jdbc:mysql://192.168.29.2:3306/inventory
# spring.datasource.url=jdbc:mariadb://192.168.1.8:3306/inventory
# spring.datasource.username=ravi
# spring.datasource.password=ravi
spring.datasource.url=jdbc:mariadb://192.168.29.198:3306/todo
# spring.datasource.url=jdbc:mysql://inventoryapp.mysql.database.azure.com:3306/inventory?useSSL=true&sslMode=REQUIRED&serverTimezone=UTC
spring.datasource.username=ravi
spring.datasource.password=ravi
spring.datasource.hikari.minimum-idle=10
spring.datasource.hikari.maximum-pool-size=30000
#logging.level.org.springframework.data.jpa=DEBUG
spring.jpa.show-sql = true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.hibernate.ddl-auto = update
#spring.jpa.properties.hibernate.dialect = org.hibernate.dialect.MySQLDialect
logging.level.org.hibernate.SQL=DEBUG
#logging.level.org.hibernate.type.descriptor.sql=TRACE
#hibernate.id.new_generator_mappings=true
spring.jackson.serialization.write-dates-as-timestamps=false
#spring.main.allow-circular-references=true
#logging.level.org.hibernate.type.descriptor.sql.BasicBinder=TRACE

#logging.level.org.hibernate.orm.jdbc.bind=TRACE

spring.jackson.date-format=yyyy-MM-dd'T'HH:mm:ssZ
app.jwt.secret=secretKey

#spring.jpa.database-platform=org.hibernate.dialect.H2Dialect
spring.main.allow-bean-definition-overriding=true
#management.endpoints.web.exposure.include=*
management.endpoint.shutdown.enabled=true
#enableLoggingRequestDetails=true
#spring.boot.admin.client.instance.name=InventoryManagementSystem
#spring.boot.admin.client.url=http://localhost:9090
#spring.boot.admin.client.instance.name=Inventory Management System
#spring.boot.admin.client.username=admin
#spring.boot.admin.client.password=admin
management.endpoints.web.exposure.include=*
management.endpoint.health.show-details=always

management.info.env.enabled=true
spring.mail.host=smtp.gmail.com
#smtp.gmail.com
spring.mail.port=587
spring.mail.username=jenkinsweather@gmail.com
spring.mail.password=linm paas akrb msny
spring.mail.properties.mail.smtp.auth=false
spring.mail.properties.mail.smtp.starttls.enable=true



spring.kafka.bootstrap-servers=localhost:29092,localhost:39092

spring.kafka.producer.key-serializer=org.apache.kafka.common.serialization.StringSerializer
spring.kafka.producer.value-serializer=org.apache.kafka.common.serialization.StringSerializer