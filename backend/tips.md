## 数据库

1. 数据库表名最好与实体类保持一致(注意:Windows下Mysql数据库名表名不区分大小写)
   如果表名添加固定前缀t_,需添加如下全局配置

   ``` xml
   mybatis-plus.global-config.db-config.table-prefix=t_
   ```

   也可使用 @TableName 表名注解指定当前实体类对应的表名，比如下面 UserInfo 实体类对应表名为 user：

   ```Java 
   @Data
   @TableName(value = "user")
   public class UserInfo {
       private Integer id;
       private String userName;
       private String passWord;
   }
   ```

   字段名类似,使用 @TableId 注解（标注在主键上）和 @TableField 注解（标注在其他成员属性上）来指定对应的字段名

   ```java
   @Data
   public class UserInfo {
       @TableId(value = "id")
       private Integer id;
       @TableField(value = "user_name")
       private String userName;
       @TableField(value = "pass_word")
       private String passWord;
   }
   
   ```

   

   ## 报错

   1. Mapper not found
      在Springboot入口程序上加注解@MapperScan("包名")
   2. 



