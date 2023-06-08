package com.read.read_book.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.read.read_book.pojo.Comment;
import com.read.read_book.pojo.User;
import org.apache.ibatis.annotations.*;

import java.util.List;

@Mapper
public interface UserMapper extends BaseMapper<User> {

    // @select的测试
    @Select("select comment from comment where uid = #{id}")
    List<String> findallcomment(@Param("id") int uid);

    @Select("select email from user where id = #{id}")
    String getemail(@Param("id") int userid);

    @Select("select username, gender ,age ,email ,introduction from user where email = #{email}")
    User getbyemail(@Param("email") String email);

    @Update("update user set pwd=#{pwd} where email =#{email}")
    int updatepwd(@Param("pwd") String pwd,@Param("email") String email);

    @Select("select *from user where email = #{email} and pwd= #{pwd}")
    int check(@Param("email") String emial,@Param("pwd") String pwd);
    int updateuser(User user);
//    @Update("update user set username= #{username},gender= #{gender},age=#{age},email=#{email},introduction= #{introduction} where email =#{email}")
//    int updateuser(@Param("username") String username,@Param("gender") String gender,@Param("age") int age,@Param("email") String email,@Param("introduction") String introduction);
}

