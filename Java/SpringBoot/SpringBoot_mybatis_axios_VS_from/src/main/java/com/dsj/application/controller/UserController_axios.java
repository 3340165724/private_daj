package com.dsj.application.controller;

import com.dsj.application.mapper.UserMapper;
import com.dsj.application.mapper.UserMapper_axios;
import com.dsj.application.model.User;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpSession;

// @Controller // 接收客户端请求 返回界面
@RestController // 接收客户端请求 返回数据
//@RequestMapping("/")
public class UserController_axios {
    @Autowired
    private UserMapper_axios userMapper_axios;

    @Autowired
    protected HttpSession session;

    @RequestMapping("/_")
    public ModelAndView login_() {
        return new ModelAndView("login_axios");
    }
    /**
     * TODO
     *  AJAX JSON 登陆检验
     *  /
     *  JSON 格式是字符串/String，比如
     *  {username: "3234", password: "23"}
     *  仅用于数据数据传输，发送端/Page和接收端/Server
     *  都要解析成 Object 处理。
     *  /
     *  页面
     *  JSON.stringify()	JSON Object(0) -> JSON String(1)
     *  JSON.parse()		JSON String -> JSON Object
     *  /
     *  后端
     *  @RequestBody		JSON String(1) -> JSON Object(2)
     *  @ResponseBody		JSON Object -> JSON String
     *
     *
     * @RequestBody User user		绑定并自动解析页面 JSON 数据
     */
    @RequestMapping(value = "/doLogin_", method = RequestMethod.POST)
    public @ResponseBody  String doLogin_(@RequestBody User user) {
        System.out.println(user.getUsername());
        User user_ = userMapper_axios.queryUsernamr(user.getUsername());
        System.out.println(user_);
        boolean rs = user_ != null && user_.getPassword().equals(user.getPassword());
        System.out.println(rs);
        //  向前端返回 JSON "{\"result\" : false }"
        if(rs){
            return  "{\"result\" : true }";
        }else {
            return  "{\"result\" : false }";
        }

    }

    @RequestMapping("/index_axios")
    public ModelAndView index() {
        return new ModelAndView("index_axios");
    }


    @RequestMapping("/enroll_axios")
    public ModelAndView enroll() {
        return new ModelAndView("enroll_axios");
    }

}
