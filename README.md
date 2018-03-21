[![license](https://img.shields.io/badge/gradle-4.6-brightgreen.svg)](https://gradle.org)
[![license](https://img.shields.io/github/license/mashape/apistatus.svg)](https://opensource.org/licenses/mit-license.php)

#  [Usrcloud](http://cloud.usr.cn/development_instruction.html#二次开发介绍)  integration  with springboot

Usrcloud-Spring-Boot-Starter 帮助你集成通用 [Usrcloud](http://cloud.usr.cn/development_instruction.html#二次开发介绍) 到 Spring Boot。


## How to use

### maven

在pom.xml加入nexus资源库（解决中国访问慢的问题,已经加入中央仓库）

Add the following nexus repository(fix china access slow problem,already append to central nexus.)  to your pom.xml:

    <repositories>
        <repository>
            <id>nexus</id>
            <name>nexus</name>
            <url>http://maven.cuisongliu.com/content/groups/public</url>
            <releases>
                <enabled>true</enabled>
            </releases>
            <snapshots>
                <enabled>false</enabled>
            </snapshots>
        </repository>
    </repositories>

在pom.xml加入依赖

Add the following dependency to your pom.xml:
    
    <dependency>
       <groupId>com.cuisongliu</groupId>
       <artifactId>usrcloud-spring-boot-starter</artifactId>
       <version>1.3</version>
     </dependency>


### gradle

在build.gradle加入nexus资源库（解决中国访问慢的问题,已经加入中央仓库）

Add the following nexus repository(fix china access slow problem,already append to central nexus.)  to your build.gradle:

    allprojects {
        repositories {
            mavenLocal()
            maven { url "http://maven.cuisongliu.com/content/groups/public" }
            mavenCentral()
            jcenter()
        }
    }
    
在build.gradle加入依赖

Add the following dependency to your build.gradle:
    
    compile "com.cuisongliu:usrcloud-spring-boot-starter:1+"

### spring Autowired Bean (by type)

UsrcloudApi  UsrcloudMqttClient 接口已经实现 你可以直接使用在被spring管理的Bean中

UsrcloudApi  UsrcloudMqttClient already implement , you can used in the spring bean.

       @Autowired
       private UsrcloudApi usrcloudApi; 
       @Autowired
       private UsrcloudMqttClient usrcloudMqttClient;

但是UsrcloudMqttCallback回调函数需要自己实现接口,因为这个是个回调函数需要经过RTU返回指令后进行业务操作.
However, the UsrcloudMqttCallback callback function needs to implement its own interface, because this is a callback function to perform business operations after the RTU return instruction.
      
      /**
       * 有人云回调函数 只能一个
       *
       * @author cuisongliu [cuisongliu@qq.com]
       * @since 2018-03-10 21:17
       */
      @Service
      public class UsrCloudCallback implements UsrCloudMqttCallback {
            @Override
            public void onConnectAck(int returnCode, String description) {
            }
        
            @Override
            public void onSubscribeAck(int messageId, String clientId, String topics, int returnCode) {
            }
        
            @Override
            public void onDisSubscribeAck(int messageId, String clientId, String topics, int returnCode) {
            }
        
            @Override
            public void onReceiveEvent(int messageId, String topic, byte[] data) {
            }
        
            @Override
            public void onReceiveParsedEvent(int messageId, String topic, String jsonData) {
            }
        
            @Override
            public void onPublishDataAck(int messageId, String topic, boolean isSuccess) {
            }
        
            @Override
            public void onPublishDataResult(int messageId, String topic) {
            }
      }
      
### springboot properties set

在application.properties 或者application.yml设置用户信息

at  application.properties or application.yml append your account message.

| properties | IsNull? | Defaults |
| :------|:------|:------|
|spring.usrcloud.account|no|null|
|spring.usrcloud.password|no|null|

## Example


    spring:
       usrcloud:
           account: cuisongliu
           password: xxxxxx
           

## Acknowledgments

 [usrcloud](https://github.com/UsrIot).