package com.migotech.wot.user.controller;

import com.github.pagehelper.PageHelper;
import com.github.pagehelper.PageInfo;
import com.migotech.wot.common.model.Result;
import com.migotech.wot.user.dao.UserDao;
import com.migotech.wot.user.model.User;
import org.apache.shiro.SecurityUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.Arrays;
import java.util.List;

@RestController
public class UserController {
    @Autowired
    private UserDao userDao;

    @GetMapping("/users")
    public Object getUsers(@RequestParam(defaultValue = "1") int page, @RequestParam(defaultValue = "10") int pageSize) {
        long companyId = 1L;

        PageHelper.startPage(page, pageSize);
        List<User> rslist = userDao.findByCompanyId(companyId);
        PageInfo pageInfo = new PageInfo<>(rslist);

        return Result.success()
                .put("list", rslist)
                .put("total",pageInfo.getTotal())
                .put("page", page)
                .put("pageSize", pageSize);
    }

    @GetMapping("/users/{userId}")
    public Object getOne(@PathVariable("userId") long id) {
        User loginOne = (User) SecurityUtils.getSubject().getPrincipal();
        User user = userDao.findOne(id);
        return Result.success().put("list", Arrays.asList(user));
    }

    @PutMapping("/users/{userId}")
    public Object editUsers(User user, @PathVariable Long userId) {
        user.setId(userId);
        userDao.editOne(user);
        return Result.success();
    }
}
