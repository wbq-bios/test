## idea基于maven搭建ssm框架并实现一个简单显示用户表
###  环境介绍
搭建环境是mac上的idea,框架是springmvc,mybatis,spring.
#### Spring
简单来说，Spring是一个轻量级的控制反转（IoC）和面向切面（AOP）的容器框架。通过这个框架我们可以实现代码之间的解耦,其中我感觉最为方便的是我们可以通过注解的方式取代编写大量的bean,解放生产力.
#### Spring-mvc
我认为 spring-mvc其实是Spring框架的一部分,他在整个工程的位置是在view层,其中最为简单的模式大概是
	
* 用户发起request请求至控制器(Controller)控制接收用户请求的数据，委托给模型进行处理.
* 控制器通过模型(Model)处理数据并得到处理结果
模型通常是指业务逻辑
* 控制器将模型数据在视图(View)中展示web中模型无法将数据直接在视图上显示，需要通过控制器完成。如果在C/S应用中模型是可以将数据在视图中展示的。
* 控制器将视图response响应给用户通过视图展示给用户要的数据或处理结果。

#### 流程大概是这样的
* 用户发送请求至前端控制器**DispatcherServlet**.
* **DispatcherServlet**收到请求调用**HandlerMapping**处理器映射器.
* 处理器映射器找到具体的处理器，生成处理器对象及处理器拦截器(如果有则生成)一并返回DispatcherServlet.
* DispatcherServlet调用HandlerAdapter处理器适配器.
* HandlerAdapter经过适配调用具体的处理器(Controller，也叫后端控制器).
* Controller执行完成返回ModelAndView.
* HandlerAdapter将controller执行结果ModelAndView返回给DispatcherServlet.
* DispatcherServlet将ModelAndView传给ViewReslover视图解析器.
* ViewReslover解析后返回具体View.
* DispatcherServlet根据View进行渲染视图（即将模型数据填充
* DispatcherServlet响应用户

![流程图](http://upload-images.jianshu.io/upload_images/763193-fad0ac1f4cfbb5ac.png?imageMogr2/auto-orient/strip%7CimageView2/2)
####mybatis
mybatis主要是用于处理数据库映射到对象的这个过程,是一款一流的支持自定义SQL、存储过程和高级映射的持久化框架。MyBatis几乎消除了所有的JDBC代码,MyBatis能够使用简单的XML格式或者注解进行来配置，能够映射基本数据元素、Map接口和POJOs（普通java对象）到数据库中的记录。

------------------------
### 环境配置
#### 创建maven工程
首先我们需要下载一个idea,然后创建一个maven工程

* File -> New project，进入创建项目窗口，选择Maven项目。 
* (1)勾选Create from archetype  
* (2)选择 maven-archetype-webapp
* (3)点击Next
* 选择Maven home directory为maven安装路径，点击Next

![maven图](http://upload-images.jianshu.io/upload_images/5229437-fd5e0f3f7df410c9.png?imageMogr2/auto-orient/strip%7CimageView2/2)
![maven图2](http://upload-images.jianshu.io/upload_images/5229437-2396f2ded886651a.png?imageMogr2/auto-orient/strip%7CimageView2/2/w/1240)
#### 二.将工程文件补全
其中java需要设置为代码的根目录,我们需要右键java文件夹,然后点击**Mark Directory As选项为Sources Root**
![工程图](http://img.blog.csdn.net/20160717142542722?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)
### 三.文件配置
因为我们使用的是maven来引入项目所需要的jar包，所以也就不需要手动来管理jar包了。我们只需要将代码复制进对应的文件的就好了.  
##### 'pom.xml'
```html
<project xmlns="http://maven.apache.org/POM/4.0.0" xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  
  xsi:schemaLocation="http://maven.apache.org/POM/4.0.0 http://maven.apache.org/maven-v4_0_0.xsd">  
  <modelVersion>4.0.0</modelVersion>  
  <groupId>com.heitian.web</groupId>  
  <artifactId>web-ssm</artifactId>  
  <packaging>war</packaging>  
  <version>1.0-SNAPSHOT</version>  
  <name>web-ssm Maven Webapp</name>  
  <url>http://maven.apache.org</url>  
  <properties>  
    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>  
    <project.reporting.outputEncoding>UTF-8</project.reporting.outputEncoding>  
  
    <!-- spring版本号 -->  
    <spring.version>4.2.5.RELEASE</spring.version>  
  
    <!-- mybatis版本号 -->  
    <mybatis.version>3.2.8</mybatis.version>  
  
    <!-- mysql驱动版本号 -->  
    <mysql-driver.version>5.1.29</mysql-driver.version>  
  
    <!-- log4j日志包版本号 -->  
    <slf4j.version>1.7.18</slf4j.version>  
    <log4j.version>1.2.17</log4j.version>  
  
  </properties>  
  
  
  <dependencies>  
    <!-- 添加jstl依赖 -->  
    <dependency>  
      <groupId>jstl</groupId>  
      <artifactId>jstl</artifactId>  
      <version>1.2</version>  
    </dependency>  
  
    <dependency>  
      <groupId>javax</groupId>  
      <artifactId>javaee-api</artifactId>  
      <version>7.0</version>  
    </dependency>  
  
    <!-- 添加junit4依赖 -->  
    <dependency>  
      <groupId>junit</groupId>  
      <artifactId>junit</artifactId>  
      <version>4.11</version>  
      <!-- 指定范围，在测试时才会加载 -->  
      <scope>test</scope>  
    </dependency>  
  
    <!-- 添加spring核心依赖 -->  
    <dependency>  
      <groupId>org.springframework</groupId>  
      <artifactId>spring-core</artifactId>  
      <version>${spring.version}</version>  
    </dependency>  
    <dependency>  
      <groupId>org.springframework</groupId>  
      <artifactId>spring-web</artifactId>  
      <version>${spring.version}</version>  
    </dependency>  
    <dependency>  
      <groupId>org.springframework</groupId>  
      <artifactId>spring-oxm</artifactId>  
      <version>${spring.version}</version>  
    </dependency>  
    <dependency>  
      <groupId>org.springframework</groupId>  
      <artifactId>spring-tx</artifactId>  
      <version>${spring.version}</version>  
    </dependency>  
    <dependency>  
      <groupId>org.springframework</groupId>  
      <artifactId>spring-jdbc</artifactId>  
      <version>${spring.version}</version>  
    </dependency>  
    <dependency>  
      <groupId>org.springframework</groupId>  
      <artifactId>spring-webmvc</artifactId>  
      <version>${spring.version}</version>  
    </dependency>  
    <dependency>  
      <groupId>org.springframework</groupId>  
      <artifactId>spring-context</artifactId>  
      <version>${spring.version}</version>  
    </dependency>  
    <dependency>  
      <groupId>org.springframework</groupId>  
      <artifactId>spring-context-support</artifactId>  
      <version>${spring.version}</version>  
    </dependency>  
    <dependency>  
      <groupId>org.springframework</groupId>  
      <artifactId>spring-aop</artifactId>  
      <version>${spring.version}</version>  
    </dependency>  
  
    <dependency>  
      <groupId>org.springframework</groupId>  
      <artifactId>spring-test</artifactId>  
      <version>${spring.version}</version>  
    </dependency>  
  
    <!-- 添加mybatis依赖 -->  
    <dependency>  
      <groupId>org.mybatis</groupId>  
      <artifactId>mybatis</artifactId>  
      <version>${mybatis.version}</version>  
    </dependency>  
  
    <!-- 添加mybatis/spring整合包依赖 -->  
    <dependency>  
      <groupId>org.mybatis</groupId>  
      <artifactId>mybatis-spring</artifactId>  
      <version>1.2.2</version>  
    </dependency>  
  
    <!-- 添加mysql驱动依赖 -->  
    <dependency>  
      <groupId>mysql</groupId>  
      <artifactId>mysql-connector-java</artifactId>  
      <version>${mysql-driver.version}</version>  
    </dependency>  
    <!-- 添加数据库连接池依赖 -->  
    <dependency>  
      <groupId>commons-dbcp</groupId>  
      <artifactId>commons-dbcp</artifactId>  
      <version>1.2.2</version>  
    </dependency>  
  
    <!-- 添加fastjson -->  
    <dependency>  
      <groupId>com.alibaba</groupId>  
      <artifactId>fastjson</artifactId>  
      <version>1.1.41</version>  
    </dependency>  
  
    <!-- 添加日志相关jar包 -->  
    <dependency>  
      <groupId>log4j</groupId>  
      <artifactId>log4j</artifactId>  
      <version>${log4j.version}</version>  
    </dependency>  
    <dependency>  
      <groupId>org.slf4j</groupId>  
      <artifactId>slf4j-api</artifactId>  
      <version>${slf4j.version}</version>  
    </dependency>  
    <dependency>  
      <groupId>org.slf4j</groupId>  
      <artifactId>slf4j-log4j12</artifactId>  
      <version>${slf4j.version}</version>  
    </dependency>  
  
    <!-- log end -->  
    <!-- 映入JSON -->  
    <dependency>  
      <groupId>org.codehaus.jackson</groupId>  
      <artifactId>jackson-mapper-asl</artifactId>  
      <version>1.9.13</version>  
    </dependency>  
    <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-core -->  
    <dependency>  
      <groupId>com.fasterxml.jackson.core</groupId>  
      <artifactId>jackson-core</artifactId>  
      <version>2.8.0</version>  
    </dependency>  
    <!-- https://mvnrepository.com/artifact/com.fasterxml.jackson.core/jackson-databind -->  
    <dependency>  
      <groupId>com.fasterxml.jackson.core</groupId>  
      <artifactId>jackson-databind</artifactId>  
      <version>2.8.0</version>  
    </dependency>  
  
    <dependency>  
      <groupId>commons-fileupload</groupId>  
      <artifactId>commons-fileupload</artifactId>  
      <version>1.3.1</version>  
    </dependency>  
  
    <dependency>  
      <groupId>commons-io</groupId>  
      <artifactId>commons-io</artifactId>  
      <version>2.4</version>  
    </dependency>  
  
    <dependency>  
      <groupId>commons-codec</groupId>  
      <artifactId>commons-codec</artifactId>  
      <version>1.9</version>  
    </dependency>  
  </dependencies>  
  
  <build>  
    <finalName>web-ssm</finalName>  
  </build>  
</project>  
```
需要注意的是
```
  <modelVersion>4.0.0</modelVersion>   
  <groupId>com.heitian.web</groupId>  
  <artifactId>web-ssm</artifactId>  
  <packaging>war</packaging>  
  <version>1.0-SNAPSHOT</version>  
  <name>web-ssm Maven Webapp</name>  
```
这些都需要依照项目而定.**finalName**标签的内容应与工程名相同.
####jdbc.properties
```html
driverClasss=com.mysql.jdbc.Driver  
jdbcUrl=jdbc:mysql://localhost:3306/db_ssm?useUnicode=true&characterEncoding=utf-8&zeroDateTimeBehavior=convertToNull  
username=root  
password=root  
  
#定义初始连接数  
initialSize=0  
#定义最大连接数  
maxActive=20  
#定义最大空闲  
maxIdle=20  
#定义最小空闲  
minIdle=1  
#定义最长等待时间  
maxWait=60000  
```
需要注意的是其中的**username**应该是你的**连接sql用户名**,**password**是你**连接数据库的密码**.**jdbcURL中的db_ssm**应该取代为你想要连接到的数据库名.最为重要的就是其中jdbcURL的最后不能接**空格**等其他符号!!!
#### log4j.properties
```html
log4j.rootLogger=INFO,Console,File  
  
#控制台日志  
log4j.appender.Console=org.apache.log4j.ConsoleAppender  
log4j.appender.Console.Target=System.out  
log4j.appender.Console.layout=org.apache.log4j.PatternLayout  
log4j.appender.Console.layout.ConversionPattern=[%p][%t][%d{yyyy-MM-dd HH\:mm\:ss}][%C] - %m%n  
  
#普通文件日志  
log4j.appender.File=org.apache.log4j.RollingFileAppender  
log4j.appender.File.File=logs/ssm.log  
log4j.appender.File.MaxFileSize=10MB  
#输出日志，如果换成DEBUG表示输出DEBUG以上级别日志  
log4j.appender.File.Threshold=ALL  
log4j.appender.File.layout=org.apache.log4j.PatternLayout  
log4j.appender.File.layout.ConversionPattern=[%p][%t][%d{yyyy-MM-dd HH\:mm\:ss}][%C] - %m%n  
```
####spring-mvc.xml
``` html
<?xml version="1.0" encoding="UTF-8"?>  
<beans xmlns="http://www.springframework.org/schema/beans"  
       xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance" xmlns:p="http://www.springframework.org/schema/p"  
       xmlns:context="http://www.springframework.org/schema/context"  
       xmlns:mvc="http://www.springframework.org/schema/mvc"  
       xsi:schemaLocation="http://www.springframework.org/schema/beans  
                        http://www.springframework.org/schema/beans/spring-beans-4.0.xsd  
                        http://www.springframework.org/schema/context  
                        http://www.springframework.org/schema/context/spring-context-4.0.xsd  
                        http://www.springframework.org/schema/mvc  
                        http://www.springframework.org/schema/mvc/spring-mvc-4.0.xsd">  
  
    <!-- 自动扫描  @Controller-->  
    <context:component-scan base-package="com.heitian.ssm.controller"/>  
  
    <!--避免IE执行AJAX时，返回JSON出现下载文件 -->  
    <bean id="mappingJacksonHttpMessageConverter"  
          class="org.springframework.http.converter.json.MappingJackson2HttpMessageConverter">  
        <property name="supportedMediaTypes">  
            <list>  
                <value>text/html;charset=UTF-8</value>  
            </list>  
        </property>  
    </bean>  
    <!-- 启动SpringMVC的注解功能，完成请求和注解POJO的映射 -->  
    <bean class="org.springframework.web.servlet.mvc.annotation.AnnotationMethodHandlerAdapter">  
        <property name="messageConverters">  
            <list>  
                <ref bean="mappingJacksonHttpMessageConverter"/> <!-- JSON转换器 -->  
            </list>  
        </property>  
    </bean>  
  
  
    <!-- 定义跳转的文件的前后缀 ，视图模式配置 -->  
    <bean class="org.springframework.web.servlet.view.InternalResourceViewResolver">  
        <property name="prefix" value="/WEB-INF/jsp/" />  
        <property name="suffix" value=".jsp"/>  
    </bean>  
  
    <!-- 文件上传配置 -->  
    <bean id="multipartResolver" class="org.springframework.web.multipart.commons.CommonsMultipartResolver">  
        <!-- 默认编码 -->  
        <property name="defaultEncoding" value="UTF-8"/>  
        <!-- 上传文件大小限制为31M，31*1024*1024 -->  
        <property name="maxUploadSize" value="32505856"/>  
        <!-- 内存中的最大值 -->  
        <property name="maxInMemorySize" value="4096"/>  
    </bean>  
</beans>  
```
需要注意的是
```
    <!-- 自动扫描  @Controller-->  
    <context:component-scan base-package="com.heitian.ssm.controller"/> 
```
这里面的**com.heitian.ssm.controller**应该是自己项目中对应的controller层
#### web.xml
```html
<?xml version="1.0" encoding="UTF-8"?>  
<web-app xmlns="http://java.sun.com/xml/ns/javaee"  
         xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"  
         xsi:schemaLocation="http://java.sun.com/xml/ns/javaee  
          http://java.sun.com/xml/ns/javaee/web-app_3_0.xsd"  
         version="3.0">  
  
    <display-name>web-ssm</display-name>  
      
    <context-param>  
        <param-name>contextConfigLocation</param-name>  
        <param-value>classpath:spring-mybatis.xml</param-value>  
    </context-param>  
  
    <context-param>  
        <param-name>log4jConfigLocation</param-name>  
        <param-value>classpath:log4j.properties</param-value>  
    </context-param>  
  
    <!-- 编码过滤器 -->  
    <filter>  
        <filter-name>encodingFilter</filter-name>  
        <filter-class>org.springframework.web.filter.CharacterEncodingFilter</filter-class>  
        <init-param>  
            <param-name>encoding</param-name>  
            <param-value>UTF-8</param-value>  
        </init-param>  
    </filter>  
    <filter-mapping>  
        <filter-name>encodingFilter</filter-name>  
        <url-pattern>/*</url-pattern>  
    </filter-mapping>  
  
    <!-- spring监听器 -->  
    <listener>  
        <listener-class>org.springframework.web.context.ContextLoaderListener</listener-class>  
    </listener>  
  
    <!-- 防止spring内存溢出监听器，比如quartz -->  
    <listener>  
        <listener-class>org.springframework.web.util.IntrospectorCleanupListener</listener-class>  
    </listener>  
  
  
    <!-- spring mvc servlet-->  
    <servlet>  
        <servlet-name>SpringMVC</servlet-name>  
        <servlet-class>org.springframework.web.servlet.DispatcherServlet</servlet-class>  
        <init-param>  
            <param-name>contextConfigLocation</param-name>  
            <param-value>classpath:spring-mvc.xml</param-value>  
        </init-param>  
        <load-on-startup>1</load-on-startup>  
        <async-supported>true</async-supported>  
    </servlet>  
    <servlet-mapping>  
        <servlet-name>SpringMVC</servlet-name>  
        <!-- 此处也可以配置成 *.do 形式 -->  
        <url-pattern>/</url-pattern>  
    </servlet-mapping>  
  
    <welcome-file-list>  
        <welcome-file>/index.jsp</welcome-file>  
    </welcome-file-list>  
  
    <!-- session配置 -->  
    <session-config>  
        <session-timeout>15</session-timeout>  
    </session-config>  
  
</web-app>  
```
然后就是编写一些业务代码并映射到数据库了.具体的可以下载我已经配置好的项目进行参考
最后就是将这个部署到Tomcat上
##### 部署和发布
 File -> Project Structure，进入创建项目配置窗口。创建一个Tomcat容器实例，并把项目部署进去
 ![部署图](http://img.blog.csdn.net/20160718220320553?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)
 项目所需配置好项目访问的根路径，然后启动Tomcat。
 ![部署图2](http://img.blog.csdn.net/20160718220343257?watermark/2/text/aHR0cDovL2Jsb2cuY3Nkbi5uZXQv/font/5a6L5L2T/fontsize/400/fill/I0JBQkFCMA==/dissolve/70/gravity/Center)
 在浏览器地址栏中输入：http://localhost:8080/web-ssm/user/showUser 就会看到相应的数据库的数据.
 
