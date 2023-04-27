package com.liu.controller;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.liu.pojo.AcademyRelease;
import com.liu.pojo.Distribute;
import com.liu.pojo.InfoStudentState;
import com.liu.pojo.LateReturn;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/out")
public class OutController extends GeneralController {
    /**
     * 1
     * 班级学生外出情况 leftTop.vue
     * 接口样例：
     * [
     * ['计应2001', '罗梦杰', '40', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
     * ['计应2001', '罗梦杰', '49', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
     * ['计应2001', '罗梦杰', '45', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
     * ['计应2001', '罗梦杰', '65', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
     * ['计应2001', '罗梦杰', '38', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
     * ['计应2001', '罗梦杰', '94', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
     * ['计应2001', '罗梦杰', '36', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
     * ['计应2001', '罗梦杰', '84', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
     * ['计应2001', '罗梦杰', '834', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
     * ['计应2001', '罗梦杰', '94', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
     * ['计应2001', '罗梦杰', '24', "<span  class='colorBlue'>40</span>", "<span  class='colorRed'>439</span>"],
     * ]
     */
    @RequestMapping("/1-left-top/{date}")
    public @ResponseBody
    String left_top(@PathVariable String date) throws JsonProcessingException {
        List<InfoStudentState> list = infoStudentStateDao.queryList(date);
        Object[] objects = list.stream().map(o -> {
            Object[] object = new Object[6];
            object[0] = o.getId();
            object[1] = o.getClassName();
            object[2] = o.getClassTeacher();
            object[3] = o.getCount();
            object[4] = o.getCountQj();
            object[5] = o.getCountXj();
            return object;
        }).toArray();
//        Object[] objects = new Object[list.size()];
//        for (int i = 0; i < list.size(); i++) {
//            Object[] o_AcademyRelease = new Object[7];
//            o_AcademyRelease[0] = i + 1;
//            o_AcademyRelease[1] = list.get(i).getClassName();
//            o_AcademyRelease[2] = list.get(i).getClassTeacher();
//            o_AcademyRelease[3] = list.get(i).getCount();
//            o_AcademyRelease[4] = list.get(i).getCountQj();
//            o_AcademyRelease[5] = list.get(i).getCountXj();
//            o_AcademyRelease[6] = list.get(i).getDate();
//            objects[i] = o_AcademyRelease;
//        }
        return jsonMapper.writeValueAsString(objects);
    }


    /**
     * 2
     * 出校晚归信息 rightBottom.vue
     * 接口样例：
     * <p>
     * [
     * ['2022-06-10', '平*正', "<span  class='colorBlue'>计应2001</span>"],
     * ['2022-06-10', '平*正'  "<span  class='colorBlue'>计应2001</span>"],
     * ['2022-06-10', '平*正', "<span  class='colorBlue'>计应2001</span>"],
     * ['2022-06-10', '平*正', "<span  class='colorBlue'>计应2001</span>"],
     * ['2022-06-10', '平*正', "<span  class='colorBlue'>计应2001</span>"],
     * ['2022-06-10', '平*正', "<span  class='colorBlue'>计应2001</span>"],
     * ['2022-06-10', '平*正', "<span  class='colorBlue'>计应2001</span>"],
     * ['2022-06-10', '平*正', "<span  class='colorBlue'>计应2001</span>"],
     * ['2022-06-10', '平*正', "<span  class='colorBlue'>计应2001</span>"],
     * ['2022-06-10', '平*正', "<span  class='colorBlue'>计应2001</span>"],
     * ]
     */
    @RequestMapping("/2-right-bottom")
    public @ResponseBody
    String right_bottom() throws JsonProcessingException {
        List<LateReturn> list = lateReturnDao.queryLateReturn();

        Object[] objects = new Object[list.size()];
        for (int i = 0; i < list.size(); i++) {
            Object[] o_AcademyRelease = new Object[4];
            o_AcademyRelease[0] = i + 1;
            o_AcademyRelease[1] = list.get(i).getDate();
            o_AcademyRelease[2] = list.get(i).getStudent_name();
            o_AcademyRelease[3] = list.get(i).getClass_name();
            objects[i] = o_AcademyRelease;
        }
        return jsonMapper.writeValueAsString(objects);
    }


    /**
     * 3
     * 学院发布 rightTop.vue
     * 接口样例：
     * <p>
     * [
     * ['4月29日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
     * ['4月28日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
     * ['4月27日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
     * ['4月26日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
     * ['4月25日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
     * ['4月24日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
     * ['4月23日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
     * ['4月22日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
     * ['4月21日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
     * ['4月20日', '今日校园出现大规模感染', "<span  class='colorRed'>本科生院</span>"],
     * ]
     */
    @RequestMapping("/3-right-top")
    public @ResponseBody
    String right_top() throws JsonProcessingException {
        List<AcademyRelease> list = academyReleaseDao.queryAcademyRelease();

//        AtomicInteger i = new AtomicInteger(1);
//        Object[] objects = list.stream().map(o -> {
//            Object[] object = new Object[4];
//            object[0] = i;
//            i.getAndIncrement();
//            object[1] = o.getDate();
//            object[2] = o.getTopic();
//            object[3] = o.getUnit();
//            return object;
//        }).toArray();

        Object[] objects = new Object[list.size()];
        for (int i = 0; i < list.size(); i++ /* AcademyRelease o: list */) {
            Object[] o_AcademyRelease = new Object[4];
            o_AcademyRelease[0] = i + 1;
            o_AcademyRelease[1] = list.get(i).getDate();
            o_AcademyRelease[2] = list.get(i).getTopic();
            o_AcademyRelease[3] = list.get(i).getUnit();
            objects[i] = o_AcademyRelease;
        }
        return jsonMapper.writeValueAsString(objects);
    }


    /**
     * 4
     * 统计 center.vue
     * [
     * {
     * title: '学生人数',
     * number: {
     * number: [1476],
     * toFixed: 0,
     * textAlign: 'left',
     * content: '{nt}',
     * style: {
     * fontSize: 26
     * }
     * }
     * },
     * {
     * title: '累计请假',
     * number: {
     * number: [759],
     * toFixed: 0,
     * textAlign: 'left',
     * content: '{nt}',
     * style: {
     * fontSize: 26
     * }
     * }
     * },
     * {
     * title: '累计销假',
     * number: {
     * number: [758],
     * toFixed: 0,
     * textAlign: 'left',
     * content: '{nt}',
     * style: {
     * fontSize: 26
     * }
     * }
     * },
     * {
     * title: '当日请假',
     * number: {
     * number: [759],
     * toFixed: 0,
     * textAlign: 'left',
     * content: '{nt}',
     * style: {
     * fontSize: 26
     * }
     * }
     * },
     * {
     * title: '当日销假',
     * number: {
     * number: [758],
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

    // 学生人数
    @RequestMapping("/4-center-1")
    public @ResponseBody
    String center_1() {

        return "[" + studentDao.countStudent() + "]";
    }

    // 累计请假
    @RequestMapping("/4-center-2")
    public @ResponseBody
    String center_2() {

        return "[" + infoStudentStateDao.countQJStudent() + "]";
    }

    // 累计销假
    @RequestMapping("/4-center-3")
    public @ResponseBody
    String center_3() {

        return "[" + infoStudentStateDao.countXJStudent() + "]";
    }

    // 当日请假
    @RequestMapping("/4-center-4/{date}")
    public @ResponseBody
    String center_4(@PathVariable String date) {

        return "[" + infoStudentStateDao.countQJDay(date) + "]";
    }

    // 当日销假
    @RequestMapping("/4-center-5/{date}")
    public @ResponseBody
    String center_5(@PathVariable String date) {

        return "[" + infoStudentStateDao.countXJDay(date) + "]";
    }

    /**
     * 5
     * 离校返校对比 BottomLeftChart.vue
     * <p>
     * [
     * {
     * name: '出校人数',
     * type: 'line',
     * stack: 'Total',
     * data: [120, 132, 101, 134, 90, 230, 210],
     * lineStyle: {
     * color: "red"
     * },
     * },
     * {
     * name: '返校人数',
     * type: 'line',
     * stack: 'Total',
     * data: [220, 182, 191, 234, 290, 330, 310],
     * lineStyle: {
     * color: "blue"
     * },
     * },
     * ]
     */
    @RequestMapping("/5-bottom-left-chart/{date}")
    public @ResponseBody
    String bottom_left_chart_1(@PathVariable String date) {
        return "[" + outschoolDao.queryOutDate(date) + "]";
    }

    @RequestMapping("/5-bottom-left-chart-returndate/{date}")
    public @ResponseBody
    String bottom_left_chart_returndate_1(@PathVariable String date) {

        return "[" + outschoolDao.queryReturnDate(date) + "]";
    }

    /**
     * 学生出校分布情况 centerLeft2Chart index.vue
     * [
     * {
     * name: '吕梁市',
     * value: 298,
     * },
     * {
     * name: '临汾市',
     * value: 937,
     * },
     * {
     * name: '忻州市',
     * value: 374,
     * },
     * {
     * name: '运城市',
     * value: 572,
     * },
     * {
     * name: '晋中市',
     * value: 831,
     * },
     * {
     * name: '朔州市',
     * value: 53,
     * },
     * {
     * name: '晋城市',
     * value: 4,
     * },
     * {
     * name: '长治市',
     * value: 193,
     * },
     * {
     * name: '阳泉市',
     * value: 1200,
     * },
     * {
     * name: '太原市',
     * value: 942,
     * },
     * {
     * name: '大同市',
     * value: 221,
     * },
     * ]
     */
    @RequestMapping("/center_map")
    public @ResponseBody String center_map() throws JsonProcessingException {

        return jsonMapper.writeValueAsString(distributeDao.queryDistribute());
    }

}




