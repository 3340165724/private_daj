package com.liu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.liu.pojo.ChinaMap;
import com.liu.pojo.News;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/back")
public class BackController extends GeneralController {

    /**
     * 1
     * 学院信息 index.vue
     * <p>
     * [
     * {
     * "name": "软件学院",
     * "value": 1264
     * },
     * {
     * "name": "信息与计算学院",
     * "value": 1402
     * },
     * {
     * "name": "材料科学与工程学院",
     * "value": 1263
     * },
     * {
     * "name": "建筑学院",
     * "value": 1502
     * },
     * {
     * "name": "土木工程学院",
     * "value": 1640
     * }
     * ]
     */
    @RequestMapping("/1-index")
    public @ResponseBody String index() throws JsonProcessingException {

        return jsonMapper.writeValueAsString(
                collegeDao.queryCollege().stream().map(o -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", o.getCollege());
            map.put("value", o.getStudents());

            return map;
        }).toArray());
    }


    /**
     * 2
     */
    @RequestMapping("/2-return_college")
    public @ResponseBody String returnCollege() throws JsonProcessingException {

        return jsonMapper.writeValueAsString(collegeDao.queryReturnCollege().stream().map(o -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", o.getCollege());
            map.put("value", o.getReturnschool());

            return map;
        }).toArray());
    }

    /**
     * 3
     * center
     * <p>
     * [
     * {
     * title: '学生总数',
     * number: {
     * number: [5000],
     * toFixed: 0,
     * textAlign: 'left',
     * content: '{nt}',
     * style: {
     * fontSize: 26
     * }
     * }
     * },
     * {
     * title: '已返校人数',
     * number: {
     * number: [4980],
     * toFixed: 0,
     * textAlign: 'left',
     * content: '{nt}',
     * style: {
     * fontSize: 26
     * }
     * }
     * },
     * {
     * title: '今日返校人数',
     * number: {
     * number: [1274],
     * toFixed: 0,
     * textAlign: 'left',
     * content: '{nt}',
     * style: {
     * fontSize: 26
     * }
     * }
     * },
     * ]
     */
    @RequestMapping("/3-center-1")
    public @ResponseBody
    String center_1() {

        return "[" + collegeDao.querySumStudent() + "]";
    }

    @RequestMapping("/3-center-2")
    public @ResponseBody
    String center_2() {

        return "[" + collegeDao.queryReturnSumStudent() + "]";
    }

    /**
     * 4
     * 疫情新闻 news index.vue
     * <p>
     * [
     * '国务院联防联控机制印发应对近期新冠病毒感染疫情疫苗接种工作方案',
     * '应对近期新冠病毒感染疫情疫苗接种工作方案',
     * '关于印发应对近期新冠病毒感染疫情疫苗接种工作方案的通知',
     * ]
     */
    @RequestMapping("/4-news")
    public @ResponseBody
    String news() throws JsonProcessingException {
        List<News> list = newsDao.queryNews();
        Object[] objects = new Object[list.size()];

        for (int i = 0; i < list.size(); i++) {
            Object[] o_AcademyRelease = new Object[2];
            o_AcademyRelease[0] = i + 1;
            o_AcademyRelease[1] = list.get(i).getContent();
            objects[i] = o_AcademyRelease;
        }
        return jsonMapper.writeValueAsString(objects);
    }

    @RequestMapping("/6-center-map")
    public @ResponseBody String china_map() throws JsonProcessingException {

        return jsonMapper.writeValueAsString(mapDao.queryList().stream().map(o -> {
            Map<String, Object> map = new HashMap<>();
            map.put("name", o.getName());
            map.put("value", o.getValue());

            return map;
        }).toArray());
    }
}
