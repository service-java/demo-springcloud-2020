package com.spring.cloud.chapter14.datasource;

import java.util.SortedMap;
import java.util.TreeMap;

/**** imports ****/
public class ConsistentHashing {

    // key表示服务器的hash值，value表示数据源编号
    private static SortedMap<Integer, String> sortedMap = new TreeMap<>(); // ①

//    // 增加数据源
//    public static void addDsKey(String dsKey) {
//        // 计算散列值
//        int hashCode = getHash(dsKey);
//        System.out.println(dsKey + "--->" + hashCode);
//        // 存入排序Map中
//        sortedMap.put(hashCode, dsKey);
//    }


    // 增加数据源，带虚拟节点
    public static void addDsKey(String dsKey) {
        // 循环5次，1个节点虚拟出5个节点
        for (int i=1; i<=100; i++) {
            // 虚拟键
            String key = dsKey + "#" + i;
            // 计算虚拟键散列值
            int hashCode = getHash(key);
            // 存入排序Map中
            sortedMap.put(hashCode, dsKey);
        }
    }
    /**
     * 使用FNV1的32位散列算法计算字符串的散列值
     * @param str -- 需要求散列值的字符串
     */
    private static int getHash(String str) {
        final int p = 16777619;
        int hash = (int)2166136261L;
        for (int i = 0; i < str.length(); i++) {
            hash = (hash ^ str.charAt(i)) * p;
        }
        hash += hash << 13;
        hash ^= hash >> 7;
        hash += hash << 3;
        hash ^= hash >> 17;
        hash += hash << 5;
        // 如为负数，则取绝对值
        if (hash < 0) {
            hash = Math.abs(hash);
        }
        return hash;
    }

    /**
     * 根据字符串散列值找到对应的数据源
     */
    private static String getDataSource(String node) {
        // 计算字符串的散列值
        int hash = getHash(node);
        // 得到大于该散列值的所有Map
        SortedMap<Integer, String> subMap = sortedMap.tailMap(hash); // ②
        Integer firstKey = null;
        // 如果没有大于当前节点的散列值的数据，那么就选择散列值最小的数据源 // ③
        if (subMap == null || subMap.isEmpty()) {
            firstKey = sortedMap.firstKey();
        } else {
            // 第一个Key就是顺时针过去离字符串散列值最近的那个结点
            firstKey = subMap.firstKey();
        }
        // 返回对应的服务器名称
        return sortedMap.get(firstKey);
    }

    public static void main(String[] args) {
        // SnowFlake算法创建ID值，请参见第13章
        SnowFlakeWorker worker = new SnowFlakeWorker(003);
        addDsKey("001");
        addDsKey("002");
        addDsKey("003");
        for (int j=1; j<=10; j++) {
            // 统计各个数据源得到的记录数
            int[] dsCount = {0, 0, 0};
            for (int i = 1; i < 1000; i++) {
                String id = worker.nextId() + ""; // 生成ID值
                int hashCode = getHash(id); // 计算散列值
                String dsKey = getDataSource(id); // 获取目标数据源
                // 统计各个数据源得到的数据数
                if ("001".equals(dsKey)) {
                    dsCount[0]++;
                } else if ("002".equals(dsKey)) {
                    dsCount[1]++;
                } else if ("003".equals(dsKey)) {
                    dsCount[2]++;
                }
            }
            // 打印分配数据源记录数
            System.out.println(dsCount[0] + ",\t" + dsCount[1] + ",\t" + dsCount[2]);
        }
    }
}