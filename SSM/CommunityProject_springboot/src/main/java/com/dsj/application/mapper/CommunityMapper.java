package com.dsj.application.mapper;

import com.dsj.application.model.Community;

import org.apache.ibatis.annotations.Insert;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;

import java.util.List;

@Mapper
public interface CommunityMapper {
    @Select("select issue, interpretation, knowledge, comments from tb_community")
    public List<Community> queryList();


    // 多字段模糊匹配
    @Select("select id ,issue,interpretation,knowledge,comments \n" +
            "from tb_community \n" +
            "where concat(issue,interpretation,knowledge) like concat('%',#{menu},'%')")
    public List<Community> likeList(@Param("menu") String menu);


    // 分页查询
    @Select("select issue,interpretation,knowledge,comments \n" +
            "from tb_community \n" +
            "order by id desc\n" +
            "limit 5")
    public List<Community> queryListPage();

//    //分页查询
//    @Select("select * from tb_community limit #{pageBegin} ,  #{pageSize}")
//    List<Community> findByPage(@Param("pageBegin") Integer pageBegin, @Param("pageSize") Integer pageSize);


    // 发表添加
    @Insert("insert into tb_community(issue) values(#{issue})")
    public Integer addCommunity(@Param("issue") String issue);
}
