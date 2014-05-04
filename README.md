# Overview

appframework is a java web application framework based [spring-framework](http://www.springsource.org/spring-framework)

appframework is for the Java programming language.

appframework features:
 * all dao and service class has its parent class.

 * all http request param is wrapped in Map

 * provides asynchronous calls of service/dao for http request

 * provides page/data/download/upload interface to light your code

 * provides listeners wrapped for you to filter http request

 * supports html/xml/json result for ajax request

 * provides basic encode and encrypt methods, such as MD5,base64,zip

 * provides wrapped log for log4j and common-logging

 * provides common utils , such as DateTimeUtil, ConfigUtil, IpUtil, EscapeUtil, DomainUtil, SwfUtil ...

 * provides [fusioncharts](http://www.fusioncharts.com/) java bean wrapped classes


#Requirements

appframework requires:

 * Java 6 or higher

 * spring-framework 3.2.*

 * other jars in the central repository


##Develop Requirements

Eclispe 3.6+ with [m2eclipse plugins](http://www.eclipse.org/m2e/download/)

#Download and Installation

Releases are available in the central repository.

The current stable release of appframework: *1.1.6*

##Download links:
source: http://hg.jiucai.org/appframework/src

JAR package: http://repo2.maven.org/maven2/org/jiucai/appframework/1.1.6/appframework-1.1.6.jar

Repository: http://search.maven.org/#search|ga|1|org.jiucai


##Maven project configuration:

Edit your project pom.xml and add :

```
    <dependency>
        <groupId>org.jiucai</groupId>
        <artifactId>appframework</artifactId>
        <version>1.1.6</version>
    </dependency>
```

#Development and bug reports

You may check out the appframework source code from appframework [HG repository](https://bitbucket.org/jiucai/appframework).

You may also [browse](http://hg.jiucai.org/appframework/src) the lastest appframework source code.

If you find a bug in appframework, please [report a bug](http://hg.jiucai.org/appframework/issues/new).

You may review open bugs through [the list of open issues](http://hg.jiucai.org/appframework/issues?status=new&status=open).


Feel free to improve the [documentation](http://code.google.com/p/appframework/w/list) .


And you can [contact me on weibo.com](http://weibo.com/forcer521)

#History

1.1.6 Stable Release based spring-framework 3.2.8

1.1.5 Stable Release based spring-framework 3.2.6

1.1.4 Stable Release based spring-framework 3.2.3

1.0.1 Stable Release based spring-framework 3.0.7

#Author and copyright

The appframework library is developed by the team of developers and it is based on spring framework.

appframework is released under the Apache 2.0 license.
