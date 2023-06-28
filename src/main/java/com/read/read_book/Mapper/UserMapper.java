package com.read.read_book.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.read.read_book.pojo.Book;
import com.read.read_book.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {



    @Select("select *from user")
    List<User> getall();//查找所有用户的信息
    // @select的测试
    @Select("select comment from comment where uid = #{id}")
    List<String> findallcomment(@Param("id") int uid);

    @Select("select email from user where id = #{id}")
    String getemail(@Param("id") int userid);//根据userid查询email

    @Select("select * from user where email = #{email}")
    User getbyemail(@Param("email") String email);//用户查看自己信息

    @Update("update user set pwd=#{pwd} where email =#{email}")
    int updatepwd(@Param("pwd") String pwd,@Param("email") String email);//修改密码

    @Select("select *from user where email = #{email} and pwd= #{pwd}")
    User check(@Param("email") String emial,@Param("pwd") String pwd);
    int updateuser(User user);//修改用户信息，动态修改
//    @Update("update user set username= #{username},gender= #{gender},age=#{age},email=#{email},introduction= #{introduction} where email =#{email}")
//    int updateuser(@Param("username") String username,@Param("gender") String gender,@Param("age") int age,@Param("email") String email,@Param("introduction") String introduction);
    @Select("select Upower from user where email = #{email} ")
    User checkadmin(@Param("email") String email);//核实管理员

    List<User> selectuser (User user);//查找用户信息，根据username和email

    @Update("update user set state = #{state} where userid=#{userid}")
    int freezeuser(@Param("state") int state,@Param("userid") int userid);//冻结

    @Select("select *,locate(#{username},username) loc,locate(#{email},email) loc1 from user where 1=1 and username like concat('%',#{username},'%') or email like concat('%',#{email},'%') ORDER BY loc,loc1  limit #{page},#{size};")
    List<User> adminseluser(@Param("username") Object text,@Param("email") Object text1,int page, int size);
    @Select("select count(*) from (select *,locate(#{username},username) loc,locate(#{email},email) loc1 from user where 1=1 and username like concat('%',#{username},'%') or email like concat('%',#{email},'%') ORDER BY loc,loc1  )a")
    Integer total(@Param("username") Object text,@Param("email") Object text1);

}

