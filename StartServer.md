# 1. MacOS
## 1. DataBase Server 采用MacOS的brew管理，(linux采用apt-get与.bash_profile)
mongo
```
    brew install mongodb-community@5.0 // 安装指定版本号
    // mongosh 进入mongo shell
    brew services start mongodb-community@5.0
```
neo4j
```
    brew install neo4j
    neo4j start | brew services start neo4j
```
redis
```
    brew search redis
    brew install redis@4.0 // 指定版本号
    brew services start redis
```
mysql
```
    brew install mysql@8.0 // 同上
    brew services start mysql
    // 手动下载， 添加环境变量 vi ~/.bash_profile
```

## 2. 服务启动
后端: spring-boot项目（maven），进入 backend2 路径( 到/src/main/resources/application.yml配置DataBase信息 )
```
    mvn package // 打包
    java -jar target/eye.war // 启动服务
```
前端 Node+vue项目 （yarn/npm），进入 frontend3 路径
```
    yarn/npm install 
    yarn/npm serve
```
前端: 数据大屏，进入 dashboard 路径
```
    yarn/npm install 
    yarn/npm serve
```
