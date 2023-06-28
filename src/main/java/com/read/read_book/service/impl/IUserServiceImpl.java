package com.read.read_book.service.impl;
import cn.hutool.core.util.RandomUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.read.read_book.Mapper.UserMapper;
import com.read.read_book.Mapper.UserstaticMapper;
import com.read.read_book.Mapper.ValidationMapper;
import com.read.read_book.common.Result;
import com.read.read_book.common.ValidationEnum;
import com.read.read_book.dto.Checkcodedto;
import com.read.read_book.dto.Recoverpwddto;
import com.read.read_book.pojo.User;
import com.read.read_book.pojo.Userstatistic;
import com.read.read_book.pojo.Validation;
import com.read.read_book.service.IUserService;
import com.read.read_book.service.ValidationService;
import io.micrometer.common.util.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class IUserServiceImpl extends ServiceImpl<UserMapper, User> implements IUserService {

    @Autowired
    UserMapper userMapper;
    @Autowired
    UserstaticMapper userstaticMapper;

    @Autowired
    JavaMailSender javaMailSender;

    @Autowired
    ValidationService validationService;

    @Autowired
    ValidationMapper validationMapper;

    @Value("${spring.mail.username}")
    private String from;

    @Override//查看个人信息
    public User getbyemail(String email) {
        return userMapper.getbyemail(email);
    }

    @Override
    public Result updatepwd(String password, String newpassword, String confirmedPassword, String email, String pwd) {
//        HttpSession session=request.getSession();
//        String sessionValue=(String)session.getAttribute("pwd");//登录后使用session存储数据
        Map<String, String> map = new HashMap<>();
        Result result = new Result();
//        String pwd = "123";
//        String email = "2971387095@qq.com";
        if (StringUtils.isBlank(password) || StringUtils.isBlank(newpassword) || StringUtils.isBlank(confirmedPassword)) {
            return result.error("400", "密码或新密码或再次确认密码输入为空");
        } else {
            if (pwd.equals(password)) {
                if (newpassword.equals(password)) {
                    return result.error("400", "新密码与原密码相同");
                }
                if (newpassword.equals(confirmedPassword)) {
                    if (userMapper.updatepwd(confirmedPassword, email) != 0) {
                        return result.success();
                    } else {
                        return result.error("500", "系统错误");
                    }
                } else {
                    return result.error("400", "两次输入的新密码不一致");
                }
            } else if (!pwd.equals(password)) {
                return result.error("400", "原密码输入有误");
            }
        }
        return result.error("500", "参数错误");
    }

    @Override
    public Result login(String email, String pwd) {
        Map<String, Object> map = new HashMap<>();
        Result result = new Result();
        if (StringUtils.isBlank(email)) {
            map.put("error_message", "邮箱不能为空");
            System.out.println(map);
            return result.error("400", "邮箱为空");
        } else if (StringUtils.isBlank(pwd)) {
            map.put("error_message", "密码不能为空");
            return result.error("400", "密码为空");
        } else {
            if (userMapper.check(email, pwd) != null) {
                int state = userMapper.check(email, pwd).getState();
                if (state != 0) {
//             map.put("User",userMapper.check(email,pwd));
//             map.put("message", "success");
//             return result.success();
                    User user = userMapper.check(email, pwd);
                    Date logintime = new Date();
                    int userid = user.getUserid();
                    String username = user.getUsername();
                    Userstatistic userstatistic = new Userstatistic();
                    userstatistic.setUserid(userid);
                    userstatistic.setUsername(username);
                    userstatistic.setLogintime(logintime);
                    userstaticMapper.insert(userstatistic);
                    return result.success(user);
                }
//             return map;}
                else {
                    map.put("error_message", "你的账号已经被冻结");
                    return result.error("400", "你的账号已经被冻结");
                }
            } else {
                map.put("error_message", "输入信息有误");
                System.out.println(map);
                return result.error("400", "输入信息有误");
//             return map;
            }
        }
    }

    @Override//注册
    public Result register(String email, String password, String confirmedPassword) {
        Map<String, String> map = new HashMap<>();
        Result result = new Result();
        if (email == null) {
            return result.error("400", "邮箱不能为空");
        }
        if (password == null || confirmedPassword == null) {
            return result.error("400", "密码不能为空");
//            return map;
        }

        //trim 删除头尾空白符
        email = email.trim();
        if (email.length() == 0) {
            System.out.println("去掉头尾空白后 email:" + email);
            map.put("error_message", "邮箱不能为空");
            return result.error("400", "邮箱不能为空");
        }

        //后端-邮箱合法性判断(未完成)


        if (password.length() == 0 || confirmedPassword.length() == 0) {
            System.out.println("密码length = 0");
            map.put("error_message", "密码不能为空");
            return result.error("400", "密码不能为空");
        }

        if (email.length() > 100) {
            map.put("error_message", "邮箱长度不能大于100");
            return result.error("400", "邮箱长度不能大于100");
        }

        if (password.length() > 100 || confirmedPassword.length() > 100) {
            map.put("error_message", "密码长度不能大于100");
            return result.error("400", "密码长度不能大于100");
        }

        if (!password.equals(confirmedPassword)) {
            map.put("error_message", "两次输入的密码不一致");
            return result.error("400", "两次输入的密码不一致");
        }

        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        List<User> users = userMapper.selectList(queryWrapper);
        if (!users.isEmpty()) {
            map.put("error_message", "邮箱已被使用");
            return result.error("400", "邮箱已被使用");
        }

//        System.out.println("一切正常,准备插入数据");
        //生成随机数用于用户名
        Random r = new Random();
        int rnum = r.nextInt(99999) + 100000;
        String username = "游客" + rnum;

        // 用户注册,username随机生成(可能重复),upower非管理员为0, state:0正常,1冻结;
        User user = new User(null, username, password, null, null, email, null, 0, 1);
        userMapper.insert(user);

        map.put("error_message", "success");
        return result.success();

    }

    @Override//修改个人信息
    public Result updateuser(User user) {
        Map<String, String> map = new HashMap<>();
        int i = userMapper.updateuser(user);
        Result result = new Result();
        if (i != 0) {
            map.put("error_message", "success");
            System.out.println(map);
            return result.success();
        } else {
            map.put("error_message", "failed");
            System.out.println(map);
            return result.error("400", "failed");
        }
    }

    @Override//确认是否是管理员
    public User checkadmin(String email) {
        Result result = new Result();
        User user = userMapper.checkadmin(email);
        return user;
    }

    @Override//根据userid或email查询user
    public Result seleuser(int page, int size, Object text) {
//        Page<User> page1 = new Page<>(page, size);
        Result result = new Result();
//        QueryWrapper<User> wrapper = new QueryWrapper<>();
//        wrapper.like("username", text).or().like("email", text);
//        return result.success(userMapper.selectPage(page1, wrapper));
        int pagenum=(page-1)*size;
        Object text1=text;
        int total=userMapper.total(text,text1);
        return result.success(userMapper.adminseluser(text,text1,pagenum,size),total);
    }

    @Override//冻结
    public int freezeuser(int userid) {
        return userMapper.freezeuser(0, userid);
    }

    @Override//解冻
    public int thaw(int userid) {
        return userMapper.freezeuser(1, userid);
    }

    @Override
    public Result sendmail(String email) //发送验证码
    {
        Date now=new Date();
        Result result=new Result();
//        String email=checkcodedto.getEmail();
//        DateTime offsetMillisecond = DateUtil.offsetMillisecond(now, 5);
        QueryWrapper<Validation> validationQueryWrapper = new QueryWrapper<>();
        validationQueryWrapper.eq("email",email);
        validationQueryWrapper.eq("type",  ValidationEnum.Forget.getCode());
        validationQueryWrapper.ge("time", now);  // 查询数据库没过期的code
        Validation validation = validationService.getOne(validationQueryWrapper);
        System.out.println(validation);
        if (validation != null) {
            return result.error("500","当前您的验证码仍然有效，请不要重复发送");
//            throw new ServiceException("-1", "当前您的验证码仍然有效，请不要重复发送");
        }else{
            SimpleMailMessage message = new SimpleMailMessage();
            message.setFrom(from);
            message.setTo(email);
            message.setSentDate(now);
            String r = new Random().toString();
//        String i = RandomUtil.randomInt(4);
            String code = RandomUtil.randomNumbers(4);
            message.setSubject("忘记密码验证");
            message.setText("b>尊敬的用户：</b><br>&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;&nbsp;您好，您本次忘记密码的验证码是：\"\n" +
                    "                \"<b color=\\\"'red'\\\">\" " + code + "\"</b><br>\"\n" +
                    "                \"，有效期5分钟。请妥善保管，切勿泄露\";\n" +
                    "————————————————\n");
            javaMailSender.send(message);
//        Date date = new Date(); // 时间参数
            Calendar calendar = Calendar.getInstance();
            calendar.setTime(now);
            calendar.add(Calendar.MINUTE, 5);
            Date newDate = calendar.getTime();
//        DateTime localDateTime = LocalDateTime.ofInstant(newDate.toInstant(), ZoneOffset.UTC);
            validationService.saveCode(email,code, ValidationEnum.Forget.getCode(), newDate );
            if(validationService.saveCode(email,code, ValidationEnum.Forget.getCode(), newDate )!=0){
                return result.success();
            }else {
                return result.error("500","发送验证码失败");}
    }
    }

    @Override
    public Result checkcode(Checkcodedto checkcodedto) //核实验证码
    {
        Result result=new Result();
        if(StringUtils.isBlank(checkcodedto.getCode())){
            return result.error("400","请输入验证码，如未获取验证码请先获取验证码");
        }
        else{
        String email=checkcodedto.getEmail();
        QueryWrapper<Validation> wrapper = new QueryWrapper<>();
        wrapper.eq("email",email).and(w -> w.eq("code",checkcodedto.getCode()));
        Validation validations = validationService.getOne(wrapper);
        if(validations!=null){
            Date vaildtime=validations.getTime();
            System.out.println(vaildtime);
            Date date=new Date();
            int re = date.compareTo(vaildtime);
            if(re<0){
                return result.success(validations);
            }else {
                return result.error("400","验证码已经过期，请重新获取验证码。");
            }
        }else {
            return  result.error("400","faild");
                }
            }}

    @Override
    public Result recoverpwd(Recoverpwddto recoverpwddto) {
        Result result=new Result();
        if (StringUtils.isBlank(recoverpwddto.getEmail())){
            return result.error("500","系统错误");
        }
        if (StringUtils.isBlank(recoverpwddto.getPwd())){
            return result.error("400","密码不能为空");
        }
        if (StringUtils.isBlank(recoverpwddto.getConfirmpwd())){
            return result.error("500","确认密码不能为空");
        }
        String pwd=recoverpwddto.getPwd();
        String email=recoverpwddto.getEmail();
        String confirmpwd=recoverpwddto.getConfirmpwd();
        if(pwd.equals(confirmpwd)){
//            userMapper.updatepwd(email,pwd);
            if(userMapper.updatepwd(pwd, email)!=0){
            return result.success();}
            else {
                return result.error("400","密码修改失败");
            }
        }else{
            return result.error("400","密码和确认密码输入不一致");
        }
    }
    @Override//注册
    public Result registertest(String email) {
        Map<String, String> map = new HashMap<>();
        Result result = new Result();
        if (email == null) {
            return result.error("400", "邮箱不能为空");
        }
        //trim 删除头尾空白符
        email = email.trim();
        if (email.length() == 0) {
            System.out.println("去掉头尾空白后 email:" + email);
            map.put("error_message", "邮箱不能为空");
            return result.error("400", "邮箱不能为空");
        }
        //后端-邮箱合法性判断(未完成)
        if (email.length() > 100) {
            map.put("error_message", "邮箱长度不能大于100");
            return result.error("400", "邮箱长度不能大于100");
        }
        QueryWrapper<User> queryWrapper = new QueryWrapper<>();
        queryWrapper.eq("email", email);
        List<User> users = userMapper.selectList(queryWrapper);
        if (!users.isEmpty()) {
            return result.error("400", "邮箱已被使用");
        }else {
            System.out.println(email);
            Date now = new Date();
            QueryWrapper<Validation> validationQueryWrapper = new QueryWrapper<>();
            validationQueryWrapper.eq("email", email);
            validationQueryWrapper.eq("type", ValidationEnum.register.getCode());
            validationQueryWrapper.ge("time", now);  // 查询数据库没过期的code
            Validation validation = validationService.getOne(validationQueryWrapper);
            System.out.println(validation);
            if (validation != null) {
                return result.error("500", "当前您的验证码仍然有效，请不要重复发送");
            } else {
                SimpleMailMessage message = new SimpleMailMessage();
                message.setFrom(from);
                message.setTo(email);
                message.setSentDate(now);
                String code = RandomUtil.randomNumbers(4);
                message.setSubject("用户注册验证");
                message.setText("尊敬的用户：您好!您本次注册的验证码是："+
                         code
                        +"，有效期5分钟。请妥善保管，切勿泄露");
                javaMailSender.send(message);
                Calendar calendar = Calendar.getInstance();
                calendar.setTime(now);
                calendar.add(Calendar.MINUTE, 5);
                Date newDate = calendar.getTime();
                validationService.saveCode(email, code, ValidationEnum.register.getCode(), newDate);
                if (validationService.saveCode(email, code, ValidationEnum.register.getCode(), newDate) != 0) {
                    return result.success();
                } else {
                    return result.error("500", "发送验证码失败");
                }
            }

        }

    }

    @Override
    public Result register3(String email, String password, String confirmedPassword) {
        Result result=new Result();
        if (email == null) {
            return result.error("400", "邮箱不能为空");
        }
        if (StringUtils.isBlank(password)||StringUtils.isBlank(confirmedPassword)) {
            return result.error("400", "密码不能为空");
//            return map;
        }
        //trim 删除头尾空白符
        email = email.trim();
        if (email.length() == 0) {
            System.out.println("去掉头尾空白后 email:" + email);
            return result.error("400", "邮箱不能为空");
        }
        //后端-邮箱合法性判断(未完成)
        if (password.length() == 0 || confirmedPassword.length() == 0) {
            return result.error("400", "密码不能为空");
        }

        if (email.length() > 100) {
            return result.error("400", "邮箱长度不能大于100");
        }

        if (password.length() > 100 || confirmedPassword.length() > 100) {
            return result.error("400", "密码长度不能大于100");
        }

        if (!password.equals(confirmedPassword)) {
            return result.error("400", "两次输入的密码不一致");
        }
        //生成随机数用于用户名
        Random r = new Random();
        int rnum = r.nextInt(99999) + 100000;
        String username = "游客" + rnum;

        // 用户注册,username随机生成(可能重复),upower非管理员为0, state:0正常,1冻结;
        User user = new User(null, username, password, null, null, email, null, 0, 1);
        userMapper.insert(user);
        return result.success();
    }
}

