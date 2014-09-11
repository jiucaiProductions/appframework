# Overview

appframework is a java web application framework based [spring-framework](http://www.springsource.org/spring-framework)

appframework is for the Java programming language.

appframework features:

 * provides dao and service interface

 * provides supports for spring NamedParameterJdbcTemplate, Hibernate, Mybatis

 * provides wrapped http request param in Map<String,String>

 * provides wrapped http request param in ParameterMapper(include map and java bean) (since 1.2.0)

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

 * Java 6 or higher

 * spring-framework 3.2.*

 * servlet 3.0 + , tomcat7+

 * third party jar files defined in pom.xml which stored in the central repository


##Develop Requirements

Eclispe 3.6+ with [m2eclipse plugins](http://www.eclipse.org/m2e/download/)

I recommend Eclipse 3.7.2 for whos computer ram is less than 4GB.

#Download and Installation

Releases are available in the central repository.

The current stable release of appframework: *1.2.0*

##Download links:
source: http://hg.jiucai.org/appframework/src

download: http://hg.jiucai.org/appframework/download

JAR package: http://repo2.maven.org/maven2/org/jiucai/appframework/1.2.0/appframework-1.2.0.jar

Repository: http://search.maven.org/#search|ga|1|org.jiucai


##Maven project configuration:

Edit your project pom.xml and add :

```
    <dependency>
        <groupId>org.jiucai</groupId>
        <artifactId>appframework</artifactId>
        <version>1.2.0</version>
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

1.2.0 Stable Release based spring-framework 3.2.11 (*Recommand upgrade to this version*)

1.1.9 Stable Release based spring-framework 3.2.10

1.1.7 Stable Release based spring-framework 3.2.9

1.1.6 Stable Release based spring-framework 3.2.8

1.1.5 Stable Release based spring-framework 3.2.6

1.1.4 Stable Release based spring-framework 3.2.3

1.0.1 Stable Release based spring-framework 3.0.7

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


---------- updated 2014-09-07