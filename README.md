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
|   |WebSecurityConfigurerAdapter(security基本配置)|90%|
|   |UserDetailsService JDBC配置|100%|
|3. |完成oauth2配置工作||
|   |引入redis|0%|
|   |创建数据表（客户端表、access_token表、refresh_token表）|20%|
|   |ResourceServerConfigurerAdapter(资源服务配置)|90%|
|   |AuthorizationServerConfigurerAdapter(授权服务配置)|90%|
|   |ClientDetailsService JDBC配置|100%|
|   |grant_token存储功能(AuthorizationCodeService, redis实现)|0%|
|   |access_token存储功能(TokenStore, JDBC实现)|0%|
|   |功能点：定时清理过期token(关系型数据库无法过期自动删除)|0%|
|4. |完成客户端对第三方请求的验权功能||
|   |创建数据表(客户端模块scope关系表、客户端接口scope关系表)|0%|
|   |功能点：客户端能通过access_token查到用户授权的scope（客户端拿到scope自行验权）|0%|
|   |功能点：客户端能通过access_token、被访问*模块*查到用户是否授权第三方访问资源|0%|
|   |功能点：客户端能通过access_token、被访问*接口*查到用户是否授权第三方访问资源|0%|
|5. |完成客户端注册管理功能||
|   |功能点：用户能注册客户端(自动拥有所有人权限[^1])|0%|
|   |功能点：客户端的管理员能赋予其他用户管理权限|0%|
|   |功能点：管理员能注册客户端的模块与scope关系|0%|
|   |功能点：管理员能注册客户端的接口与scope关系|0%|
|扩展性|超出demo演示范围的功能，未来改进方向：||
|i. |各类查询添加缓存||
|ii.|sharding分库分表||
|iii.|数据库主键提供雪花算法自动生成模式，自增模式，通过配置文件开启||
|iv.|ouath2的各类provider提供其他实现方式||
|v.|抽象出可插拔的模块/接口，最终目标是抽象出一个jar包依赖||


[^1]: 所有人权限：所有人与管理员对客户端的权限范围一模一样，唯一的区别是管理员权限可以被删除，所有人权限只能移交，不能删除。每个客户端有且仅有唯一的所有人。