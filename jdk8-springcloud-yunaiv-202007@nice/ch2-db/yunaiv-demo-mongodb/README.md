# README

- 芋道 Spring Boot MongoDB 入门
    - <http://www.iocoder.cn/Spring-Boot/MongoDB>
    - https://github.com/MorphiaOrg/morphia

```
介于关系数据库和非关系数据库之间

Database
Collection
Document
Field
嵌入文档或者链接

morphia --> Spring Data MongoDB

MongoDBConfig

===
// 创建排序条件
Sort sort = new Sort(Sort.Direction.DESC, "id"); // ID 倒序
// 创建分页条件。
Pageable pageable = PageRequest.of(0, 10, sort);
// 执行分页操作
Page<UserDO> page = userRepository.findByUsernameLike("yu", pageable);

===
UserDO probe = new UserDO();
probe.setUsername(username);

ExampleMatcher matcher = ExampleMatcher.matching()
    .withMatcher("username", ExampleMatcher.GenericPropertyMatchers.contains()); // 模糊匹配 username 查询
Example<UserDO> example = Example.of(probe, matcher);

```
