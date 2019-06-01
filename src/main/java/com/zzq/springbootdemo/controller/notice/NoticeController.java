package com.zzq.springbootdemo.controller.notice;

import com.zzq.springbootdemo.dao.notice.NoticeMapper;
import com.zzq.springbootdemo.dto.JsonResponse;
import com.zzq.springbootdemo.model.notice.Notice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

/**
 * @author TYLER
 * @title: SimpleNoticeController
 * @description: 简单通知操作，url严格遵循RestFul格式
 * @date 2019/6/1
 */

@RestController
@RequestMapping(value = "/notices") //宾词统一采用复数
@Slf4j
public class NoticeController {
    @Autowired
    NoticeMapper noticeMapper;

//    @AspectLogOnMethod("123")
    @GetMapping("/id/{p}")
    public Notice getNoticeById(@PathVariable(value = "p") Integer id){
        log.info("获取特定ID的通知:"+id);
        Notice notice = noticeMapper.selectByPrimaryKey(id);
        return notice;
    }

    @GetMapping("/type/{p}")
    public List<Notice> getNoticeByType(@PathVariable(value = "p") String type){
        log.info("获取特定类型的通知："+type);
        return null;
    }

    @GetMapping
    public List<Notice> getNoticeAll(){
        List<Notice> noticeList = noticeMapper.findAll();
        return noticeList;
    }

    @DeleteMapping("/id/{p}")
    public JsonResponse deleteNoticeById(@PathVariable(value = "p") int id){
        int delete = noticeMapper.deleteByPrimaryKey(id);
        if (delete > 0) {
            return new JsonResponse().success(delete);
        } else {
            return new JsonResponse().failure();
        }

    }
    @PutMapping
    public JsonResponse insertNotice(@RequestBody Notice notice){
        if(notice!=null)
        {
            notice.setCreatedt(new Date());
            notice.setCreateid("admin");
            notice.setVersion(0);
        }
        int insert = noticeMapper.insert(notice);
        if (insert > 0) {
            return new JsonResponse().success(insert);
        } else {
            return new JsonResponse().failure();
        }
    }
    @PostMapping
    public JsonResponse updateNotice(@RequestBody Notice notice){
        if(notice!=null)
        {
            notice.setUpdatedt(new Date());
            notice.setUpdateid("admin");
        }
        int update = noticeMapper.updateByPrimaryKeySelective(notice);
        if (update > 0) {
            return new JsonResponse().success(update);
        } else {
            return new JsonResponse().failure();
        }
    }
}
