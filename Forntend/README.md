# user_app

## Project setup
```shell
npm install
```

### Compiles and hot-reloads for developments
```shell
npm run serve
```

### Compiles and minifies for production
```shell
npm run build
```

### Lints and fixes files
```shell
npm run lint
```

### Customize configuration
See [Configuration Reference](https://cli.vuejs.org/config/).

启动后，在浏览器中输入如下地址，可以看到login界面

````shell
http://localhost:8080/#/login
````



![image-20230530154127820](https://gitee.com/JacksonSong/img/raw/master/img/image-20230530154127820.png)

输入如下地址可以看到register界面

```shell
http://localhost:8080/#/register
```

![image-20230530154321926](https://gitee.com/JacksonSong/img/raw/master/img/image-20230530154321926.png)

如果没有node_modules目录时，就需要进行项目修复，来进行构建node项目在Frontend目录下打开cmd，输入如下命令:

```shell
npm audit fix --force
```

![image-20230605205320388](https://gitee.com/JacksonSong/img/raw/master/img/image-20230605205320388.png)

结构目录如上图所示，即可成功运行。
