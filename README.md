# Overview

appframework is a java web application framework based [spring-framework](http://www.springsource.org/spring-framework)

appframework is for the Java programming language.

appframework features:

 * provides dao and service interface

 * provides supports for spring NamedParameterJdbcTemplate, Hibernate, Mybatis

 * provides wrapped http request param in ParameterMapper(include map and java bean) (since 2.0.0)

 * provides asynchronous calls of service/dao for http request

 * provides page/data/download/upload interface to light your code

 * provides listeners wrapped for you to filter http request

 * supports html/xml/json result for ajax request

 * provides basic encode and encrypt methods, such as md5,base64,dsa,rsa,aes,des,xxtea

 * provides wrapped log for log4j and common-logging

 * provides common utils , such as DateTimeUtil, ConfigUtil, IpUtil, EscapeUtil, DomainUtil, SwfUtil,InvokeSpeedLimitUtil ...

 * provides [fusioncharts](http://www.fusioncharts.com/) java bean wrapped classes


#Requirements

appframework min requirement:

 * JDK 1.6 or higher

 * spring-framework 4.1.*

 * servlet 3.0+ , tomcat 7+

 * third party jar files defined in pom.xml which stored in the central repository


##Develop Requirements

Eclispe 4.4+ with [m2eclipse plugins](http://www.eclipse.org/m2e/download/)

#Download and Installation

Releases are available in the central repository.

The current stable release of appframework: *2.1.1*

The dev version now is: 2.1.2-SNAPSHOT

##Download links:
source: http://hg.jiucai.org/appframework/src

download: http://hg.jiucai.org/appframework/download

JAR package: http://repo2.maven.org/maven2/org/jiucai/appframework/2.1.1/appframework-2.1.1.jar

Repository: http://search.maven.org/#search|ga|1|org.jiucai


##Maven project configuration:

Edit your project pom.xml and add :

```
    <dependency>
        <groupId>org.jiucai</groupId>
        <artifactId>appframework</artifactId>
        <version>2.1.1</version>
    </dependency>
```

Maven Site URL:  http://appframework.jiucai.org/

#Development and bug reports

You may check out the appframework source code from appframework [HG repository](https://bitbucket.org/jiucai/appframework).

You may also [browse](http://hg.jiucai.org/appframework/src) the lastest appframework source code.

If you find a bug in appframework, please [report a bug](http://hg.jiucai.org/appframework/issues/new).

You may review open bugs through [the list of open issues](http://hg.jiucai.org/appframework/issues?status=new&status=open).


Feel free to improve the [documentation](http://hg.jiucai.org/appframework/wiki) .


And you can [contact me on weibo.com](http://weibo.com/forcer521)

Anyone interested is welcome to join in QQ group: 56883769 (JAVA&SSH&SSI)

#History

2.1.1 Stable version based spring-framework 4.1.4 (*Recommand upgrade to this version*)

2.1.0 Stable version based spring-framework 4.1.1 

2.0.0 Stable version based spring-framework 4.1.0 

1.2.0 Stable Release based spring-framework 3.2.11

#Author and copyright

The appframework library is developed by the team of developers and it is based on spring framework.

appframework is released under the Apache 2.0 license.

#Build Using Gradle
to convert maven project to gradle project:

gradle 1:

gradle setupBuild --type pom

gradle 2:

gradle init --type pom

when build.gradle is ready, then run

gradle build


---------- updated 2015-01-02

