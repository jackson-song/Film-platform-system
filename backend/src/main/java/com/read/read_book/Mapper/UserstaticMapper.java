package com.read.read_book.Mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.read.read_book.dto.loginstastic;
import com.read.read_book.dto.loginstastic3;
import com.read.read_book.dto.loginstatistic2;
import com.read.read_book.pojo.Userstatistic;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface UserstaticMapper extends BaseMapper<Userstatistic> {
@Select("select count(*) times,logintime from userstatistic where logintime BETWEEN DATE_SUB(NOW(), INTERVAL 1 WEEK) AND NOW() GROUP BY logintime")
    List<loginstastic> statisticloginweek();
@Select("select username ,count(*)as times from userstatistic GROUP BY userid,username ORDER BY times DESC limit 5;")
    List<loginstatistic2> statisticmost();

@Select("SELECT CONCAT(YEAR(logintime), '-', LPAD(MONTH(logintime), 2, '0')) AS `time`, COUNT(*) AS times\n" +
        "FROM userstatistic\n" +
        "WHERE logintime >= DATE_SUB(CURRENT_DATE(), INTERVAL 1 YEAR)\n" +
        "GROUP BY `time`\n" +
        "ORDER BY `time`;")
    List<loginstastic3> monthstatistic();

}
