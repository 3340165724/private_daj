package com.dsj.application.controller;

import com.dsj.application.mapper.CommunityMapper;
import com.dsj.application.model.Community;
import com.dsj.application.model.User;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import java.util.List;



@RestController
@RequestMapping("/o")
public class CommunityController {
    @Autowired
    private CommunityMapper communityMapper;

    @RequestMapping("/index")
    public ModelAndView index() {
        return new ModelAndView("index");
    }

    // json格式的数据
    @RequestMapping("/doListIndex")
    public @ResponseBody
    String jsonList() throws JsonProcessingException {
        List<Community> list = communityMapper.queryList();
        // 创建一个实例ObjectMapper并使用它的writeValueAsString()方法将列表序列化为 JSON 字符串
        return new ObjectMapper().writeValueAsString(list);
    }

    @RequestMapping("/likeList")
    public @ResponseBody
    String list(@RequestBody String menu) throws JsonProcessingException {
        // 反解析 JSON to Java Object
        ObjectMapper mapper = new ObjectMapper();
        JsonNode jsonNode = mapper.readTree(menu);
        String menu_ = jsonNode.get("menu").asText();
        List<Community> likeList = communityMapper.likeList(menu_);
        return mapper.writeValueAsString(likeList);
    }

//    // axios方式获取后端带分页数据并记录分页信息
//    @GetMapping("/jsonListPaging/{page}/{size}")
//    public  @ResponseBody String jsonPaging(@PathVariable Integer page,@PathVariable Integer size) throws JsonProcessingException {
//        Pageable paging = PageRequest.of(page, size);
//        Page<Community> pageList = communityMapper.queryListPage(paging);
//        return new ObjectMapper().writeValueAsString(pageList);
//    }
//
    // axios方式获取后端带分页数据并记录分页信息
    @RequestMapping("/jsonListPaging")
    public  @ResponseBody String jsonPaging() throws JsonProcessingException {
        List<Community> pageList = communityMapper.queryListPage();
        return new ObjectMapper().writeValueAsString(pageList);
    }

//    // 分页
//    @GetMapping("/allByPage")
//    public List<Community> findByPage(Integer page,Integer pageSize) {
//        Integer pageBegin = (page-1) * pageSize;
//        return communityMapper.findByPage(pageBegin,pageSize);
//    }
//



        // 发布
    @RequestMapping("/publication")
    public  ModelAndView publication(){
        return new ModelAndView("publication");
    }

    @RequestMapping("/doPublication")
    public @ResponseBody String doEnroll(@RequestBody Community community) {
        String issue = community.getIssue();
        Integer result = communityMapper.addCommunity(issue);
        if(result > 0){
            return  "{\"result\" : true }";
        }else {
            return  "{\"result\" : false }";
        }
    }
}
