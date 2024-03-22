package com.zhang;

import com.alibaba.fastjson.JSONObject;

import java.sql.Array;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import java.util.concurrent.CountDownLatch;

/**
 * @author zh
 * @date 2022/9/24 14:57
 */
public class Test {
    public static void main(String[] args) {

    }

    /*
    # "nurseList->nurseName":"nurse"
    # "nurseList->nursePrefs->day":"day"
    # "nurseList->nursePerfs->intervals":"interval"
     */
    public static void function(String[] strs) {

        List<String> ans = new ArrayList<>();
        JSONObject json = new JSONObject();
        for (String s: strs) {
            String[] tmp = s.split("->");
            int deep = tmp.length;
            // 例子中 deep = 3; 进入JSON的 deep - 1 层
            for (int i = 0; i < deep - 1; i ++) {
                String key = tmp[i];
                if (json.containsKey(key)) {
                    json = json.getJSONObject(key);
                }
            }
            // 最后一层为 List, 遍历
        }
    }
}


