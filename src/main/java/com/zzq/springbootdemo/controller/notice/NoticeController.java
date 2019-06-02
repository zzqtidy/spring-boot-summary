package com.zzq.springbootdemo.controller.notice;

import com.zzq.springbootdemo.dao.notice.NoticeMapper;
import com.zzq.springbootdemo.dto.JsonResponse;
import com.zzq.springbootdemo.model.notice.Notice;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.web.bind.annotation.*;

import java.util.*;

/**
 * @author TYLER
 * @title: SimpleNoticeController
 * @description: 简单通知操作，url严格遵循RestFul格式
 * @date 2019/6/1
 */

@RestController
@RequestMapping(value = "/")
@Slf4j
public class NoticeController {
    private String []STATUS_ARR = {"UNREAD","READ","RECYCLE"};
    @Autowired
    NoticeMapper noticeMapper;

    @Autowired
    JdbcTemplate jdbcTemplate;

//    @AspectLogOnMethod("123")
    @GetMapping("notices/id/{p}")
    public Notice getNoticeById(@PathVariable(value = "p") Integer id){
        log.info("获取特定ID的通知:"+id);
        Notice notice = noticeMapper.selectByPrimaryKey(id);
        return notice;
    }

    @GetMapping("notices/type/{p}")
    public List<Notice> getNoticeByType(@PathVariable(value = "p") String type){
        log.info("获取特定类型的通知："+type);
        return null;
    }

    @GetMapping("notices")
    public HashMap<String, Object> getNoticeAll(){
        List<Map<String, Object>> unreadListMap = jdbcTemplate.queryForList("select * from notice where status='UNREAD'");
        List<Map<String, Object>> readListMap = jdbcTemplate.queryForList("select * from notice where status='READ'");
        List<Map<String, Object>> recycleListMap = jdbcTemplate.queryForList("select * from notice where status='RECYCLE'");

        HashMap<String, Object> stringObjectHashMap = new HashMap<>();
        stringObjectHashMap.put("unread",unreadListMap);
        stringObjectHashMap.put("read",readListMap);
        stringObjectHashMap.put("recycle",recycleListMap);

        return stringObjectHashMap;
    }

    @DeleteMapping("notices/id/{p}")
    public JsonResponse deleteNoticeById(@PathVariable(value = "p") int id){
        int delete = noticeMapper.deleteByPrimaryKey(id);
        if (delete > 0) {
            return new JsonResponse().success(delete);
        } else {
            return new JsonResponse().failure();
        }

    }
    @DeleteMapping("notices/status/{p}")
    public JsonResponse deleteNoticeByStatus(@PathVariable(value = "p") String status){
        int deletes = jdbcTemplate.update("delete from notice where status=?", status);
        if (deletes > 0) {
            return new JsonResponse().success(deletes);
        } else {
            return new JsonResponse().failure();
        }
    }
    @PutMapping("/notice")
    public JsonResponse insertNotice(@RequestBody Notice notice){
        if(notice!=null)
        {
            notice.setCreateid("admin");
        }
        int insert = noticeMapper.insertSelective(notice);
        if (insert > 0) {
            return new JsonResponse().success(insert);
        } else {
            return new JsonResponse().failure();
        }
    }
    @PostMapping("/notice")
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
    @PostMapping("/notice/status/{p}")
    public JsonResponse updateNoticeStatus(@RequestBody Notice notice,@PathVariable("p") String status){

        if(notice!=null)
        {
            if(!Arrays.asList(STATUS_ARR).contains(status)){
                return new JsonResponse().failure("无效的通知状态更改");
            }
            notice.setUpdatedt(new Date());
            notice.setUpdateid("admin");
            notice.setStatus(status);
        }
        int update = noticeMapper.updateByPrimaryKeySelective(notice);
        if (update > 0) {
            return new JsonResponse().success(update);
        } else {
            return new JsonResponse().failure();
        }
    }
    @PostMapping("/notices/status/{p}")
    public JsonResponse updateNoticesStatus(@RequestBody List<Notice> notices,@PathVariable("p") String status){
        int updates = 0;
        if(notices.size()>0){
            for (Notice notice:notices) {
                if(!Arrays.asList(STATUS_ARR).contains(status)){
                    continue;
                }
                notice.setUpdatedt(new Date());
                notice.setUpdateid("admin");
                notice.setStatus(status);
                int update = noticeMapper.updateByPrimaryKeySelective(notice);
                updates+=update;
            }

        }
        if (updates > 0) {
            return new JsonResponse().success();
        } else {
            return new JsonResponse().failure();
        }
    }
}
