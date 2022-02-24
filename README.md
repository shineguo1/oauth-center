### 工作规划

|步骤|工作|完成度|
|---|---|---|
|1.|搭建项目目录||
|   |引入mybatis-plus|100%|
|   |引入security|100%|
|   |引入swagger|100%|
|   |拓展mybatis-plus基类|100%|
|2. |完成spring-security配置工作||
|   |创建数据表（用户表）|100%|
|   |WebSecurityConfigurerAdapter(security基本配置)|100%|
|   |UserDetailsService JDBC配置|100%|
|3. |完成oauth2配置工作||
|   |引入redis|100%|
|   |创建数据表（客户端表、access_token表、refresh_token表）|100%|
|   |ResourceServerConfigurerAdapter(资源服务配置)|90%|
|   |AuthorizationServerConfigurerAdapter(授权服务配置)|100%|
|   |ClientDetailsService JDBC配置|100%|
|   |grant_token存储功能(AuthorizationCodeService, redis实现)|100%|
|   |access_token存储功能(TokenStore, 集成security-oauth2原生JDBC实现)|100%|
|   |access_token存储功能(TokenStore, 自定义JDBC实现)|0%|
|   |功能点：定时清理过期token(关系型数据库无法过期自动删除)|0%|
|4. |完成客户端对第三方请求的验权功能||
|   |创建数据表(客户端模块scope关系表、客户端接口scope关系表)|100%|
|   |功能点：客户端能通过access_token查到用户授权的scope（客户端拿到scope自行验权）|100%|
|   |功能点：客户端能通过access_token、被访问*模块*查到用户是否授权第三方访问资源|100%|
|   |功能点：客户端能通过access_token、被访问*接口*查到用户是否授权第三方访问资源|100%|
|5. |完成客户端注册管理功能||
|   |功能点：用户能注册客户端(自动拥有所有人权限[^1])|0%|
|   |功能点：客户端的管理员能赋予其他用户管理权限|0%|
|   |功能点：管理员能注册客户端的模块与scope关系|0%|
|   |功能点：管理员能注册客户端的接口与scope关系|0%|
|6*.|有时间会优化的锦上添花的部分||
|   |功能点：用户查看授权列表|0%|
|   |功能点：用户取消授权|0%|
|   |oauth2-provider多数据源实现并用（代理+责任链模式）|100%|
|   |实现jwt登录|0%|
|   |抽象出可插拔的模块/接口，最终目标是抽象出一个jar包依赖|0%|
|扩展性|超出demo演示范围的功能，未来改进方向：||
|i. |各类查询添加缓存||
|ii.|sharding分库分表||
|iii.|数据库主键提供雪花算法自动生成模式，自增模式，通过配置文件开启||
|iv.|ouath2的各类provider提供其他实现方式||


[^1]: 所有人权限：所有人与管理员对客户端的权限范围一模一样，唯一的区别是管理员权限可以被删除，所有人权限只能移交，不能删除。每个客户端有且仅有唯一的所有人。



测试过程：
1. 执行SQL文件夹下的sql文件，创建数据表，导入默认测试数据。
2. 配置yml里的数据库和redis地址
3. GET 访问，在百度的回调地址中找到grant_code：http://localhost:8081/oauth/authorize?client_id=myClient&response_type=code&redirect_uri=http://www.baidu.com
4. 在回调页面上选择授权user、account，这个两个权限. 使用接口模拟调用如下：
```
http://localhost:8081/oauth/authorize?user_oauth_approval=true&scope.all=true&scope.account=true&scope.pay=false&scope.user=true&scope.dept=false&authorize=Authorize
```

5. POST 模拟客户端请求access_token, 返回结果包含access_token, refresh_token 和有效期: http://localhost:8081/oauth/token?code={GRANT_CODE}}&grant_type=authorization_code&redirect_uri=http://www.baidu.com
6. POST 模拟过期后客户端刷新 access_token : http://localhost:8081/oauth/token?grant_type=refresh_token&refresh_token={REFRESH_TOKEN}&client_id=myClient&client_secret=123456 
7. GET 模拟客户端使用 access_token 获取用户授权 scope : http://localhost:8081/oauth/scope<br/>
  需要设置bearear token: {ACCESS_TOKEN}
8. POST 模拟客户端验权:   : http://localhost:8081/oauth/scope/check<br/>
  需要设置bearear token: {ACCESS_TOKEN}, 请求参数如下：
  ```
  

//前提：仅授权了user、account权限
{
    "clientId": "myClient",
    //期望返回：pay:未授权，user:授权，dept:未授权，"USER":未注册，holiday:未注册
    "scopes": [
        "pay",
        "user",
        "dept",
        "USER",
        "holiday"
    ],
    //模块code区分大小写，期望返回：USER:授权，DEPT:未授权，user:未注册，HOLIDAY:未注册
    "modules": [
        "USER",
        "DEPT",
        "user",
        "HOLIDAY"
    ],
    "interfaces": [
        // POST方法未注册，期望返回：未注册
        "POST http://www.baidu.com/user",
        // 注册授权方法，期望返回：授权
        "GET http://www.baidu.com/user",
        //前后空格会自动消除，期望返回：授权
        "   GET http://www.baidu.com/dept  ",
        //GET后只能有2个空格，String.split可以接受，期望返回：授权
        " GET  http://www.baidu.com/dept",
        //http后有空格，存在两个空格，期望返回：抛出异常
        " GET  http ://www.baidu.com/dept",
        // 无请求方式方法未注册，期望返回：未注册
        "http://www.baidu.com/user",
        // 已注册未授权(dept、all权限)方法，期望返回：未授权
        "http://www.baidu.com/dept"
    ]
}
```