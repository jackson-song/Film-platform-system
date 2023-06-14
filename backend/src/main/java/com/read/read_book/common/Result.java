package com.read.read_book.common;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class Result {
    private String code;
    private String msg;
    private Object data;
    private Integer total;
    public Result success(){
        return new Result(Constants.CODE_200,"success",null,null);
    }//没有数据的成功
    public Result success(Object data){
        return new Result(Constants.CODE_200,"success",data,null);
    }//有数据的成功
    public Result error(String code,String msg){
        return new Result(code,msg,data,total)
                ;
    }//失败
    public Result success(Object data,Integer total){
        return new Result(Constants.CODE_200,"success",data,total);
    }
}
