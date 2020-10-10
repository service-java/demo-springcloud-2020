# README

- 芋道 Spring Boot Elasticsearch 入门
    - <http://www.iocoder.cn/Spring-Boot/Elasticsearch>
    - https://blog.csdn.net/chengyuqiang/article/details/78837712

```
两种连接方式

rest --> HTTP API @nice
transport --> tcp @ignore --> 准备废弃

推荐spring-data-jest??

先安装 IK 插件

===
// 复杂查询
QueryBuilder 和 SearchQuery


JestElasticsearchTemplate
```

```java
public interface ProductRepository03 extends ElasticsearchRepository<ESProductDO, Integer> {

    default Page<ESProductDO> search(Integer cid, String keyword, Pageable pageable) {
        // <1> 创建 NativeSearchQueryBuilder 对象
        NativeSearchQueryBuilder nativeSearchQueryBuilder = new NativeSearchQueryBuilder();
        // <2.1> 筛选条件 cid
        if (cid != null) {
            nativeSearchQueryBuilder.withFilter(QueryBuilders.termQuery("cid", cid));
        }
        // <2.2> 筛选
        if (StringUtils.hasText(keyword)) {
            FunctionScoreQueryBuilder.FilterFunctionBuilder[] functions = { // TODO 芋艿，分值随便打的
                    new FunctionScoreQueryBuilder.FilterFunctionBuilder(matchQuery("name", keyword),
                            ScoreFunctionBuilders.weightFactorFunction(10)),
                    new FunctionScoreQueryBuilder.FilterFunctionBuilder(matchQuery("sellPoint", keyword),
                            ScoreFunctionBuilders.weightFactorFunction(2)),
                    new FunctionScoreQueryBuilder.FilterFunctionBuilder(matchQuery("categoryName", keyword),
                            ScoreFunctionBuilders.weightFactorFunction(3)),
//                    new FunctionScoreQueryBuilder.FilterFunctionBuilder(matchQuery("description", keyword),
//                            ScoreFunctionBuilders.weightFactorFunction(2)), // TODO 芋艿，目前这么做，如果商品描述很长，在按照价格降序，会命中超级多的关键字。
            };
            FunctionScoreQueryBuilder functionScoreQueryBuilder = QueryBuilders.functionScoreQuery(functions)
                    .scoreMode(FunctionScoreQuery.ScoreMode.SUM) // 求和
                    .setMinScore(2F); // TODO 芋艿，需要考虑下 score
            nativeSearchQueryBuilder.withQuery(functionScoreQueryBuilder);
        }
        // 排序
        if (StringUtils.hasText(keyword)) { // <3.1> 关键字，使用打分
            nativeSearchQueryBuilder.withSort(SortBuilders.scoreSort().order(SortOrder.DESC));
        } else if (pageable.getSort().isSorted()) { // <3.2> 有排序，则进行拼接
            pageable.getSort().get().forEach(sortField -> nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort(sortField.getProperty())
                    .order(sortField.getDirection().isAscending() ? SortOrder.ASC : SortOrder.DESC)));
        } else { // <3.3> 无排序，则按照 ID 倒序
            nativeSearchQueryBuilder.withSort(SortBuilders.fieldSort("id").order(SortOrder.DESC));
        }
        // <4> 分页
        nativeSearchQueryBuilder.withPageable(PageRequest.of(pageable.getPageNumber(), pageable.getPageSize())); // 避免
        // <5> 执行查询
        return search(nativeSearchQueryBuilder.build());
    }
}
```


# 常见问题 @faq

- 记得要配置对cluster-name!!!  
- jest --> url is valid

```
不要忘记配置前加 http://
```

- Must be in the format host:port!

```
必须是内网地址??
```

- elasticsearch需要配置远程访问 @ignore
