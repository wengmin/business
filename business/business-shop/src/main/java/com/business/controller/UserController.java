package com.business.controller;

import com.business.entity.UserEntity;
import com.business.service.UserService;
import com.business.utils.PageUtils;
import com.business.utils.Query;
import com.business.utils.R;
import com.business.utils.excel.ExcelExport;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import com.business.utils.Base64;

import javax.servlet.http.HttpServletResponse;
import java.util.*;

/**
 * Controller
 *
 * @author lipengjun
 * @email 939961241@qq.com
 * @date 2017-08-16 15:02:28
 */
@RestController
@RequestMapping("user")
public class UserController {
    @Autowired
    private UserService userService;
    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("user:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);
        List<UserEntity> userList = userService.queryList(query);
        int total = userService.queryTotal(query);
        for(UserEntity user : userList) {
        	user.setNickname(Base64.decode(user.getNickname()));
        	user.setMobile(Base64.decode(user.getMobile()));
        }
        PageUtils pageUtil = new PageUtils(userList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }
    /**
     * 查看信息
     */
    @RequestMapping("/info/{id}")
    @RequiresPermissions("user:info")
    public R info(@PathVariable("id") Integer id) {
        UserEntity user = userService.queryObject(id);
        user.setNickname(Base64.decode(user.getNickname()));
        user.setMobile(Base64.decode(user.getMobile()));
        return R.ok().put("user", user);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("user:save")
    public R save(@RequestBody UserEntity user) {
        userService.save(user);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("user:update")
    public R update(@RequestBody UserEntity user) {
        user.setNickname(Base64.decode(user.getNickname()));
        user.setMobile(Base64.decode(user.getMobile()));
        userService.update(user);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("user:delete")
    public R delete(@RequestBody Integer[] ids) {
        userService.deleteBatch(ids);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<UserEntity> userList = userService.queryList(params);
        for(UserEntity user : userList) {
            user.setNickname(Base64.decode(user.getNickname()));
            user.setMobile(Base64.decode(user.getMobile()));
        }
        return R.ok().put("list", userList);
    }

    /**
     * 总计
     */
    @RequestMapping("/queryTotal")
    public R queryTotal(@RequestParam Map<String, Object> params) {
        int sum = userService.queryTotal(params);

        return R.ok().put("userSum", sum);
    }
    /**
     * 导出会员
     */
    @RequestMapping("/export")
//    @RequiresPermissions("user:export")
    public R export(@RequestParam Map<String, Object> params, HttpServletResponse response) {

        List<UserEntity> userList = userService.queryList(params);

        ExcelExport ee = new ExcelExport("会员列表");

        String[] header = new String[]{"会员名称", "昵称", "性别", "是否实名", "手机号码", "真实姓名", "身份证号"};

        List<Map<String, Object>> list = new ArrayList<>();

        if (userList != null && userList.size() != 0) {
            for (UserEntity userEntity : userList) {
                LinkedHashMap<String, Object> map = new LinkedHashMap<>();
                //map.put("USERNAME", Base64.decode(userEntity.getUsername()));
                //map.put("Nickname", Base64.decode(userEntity.getNickname()));
                //map.put("GENDER", userEntity.getGender() == 1 ? "男" : (userEntity.getGender() == 2 ? "女" : "未知"));
                //map.put("isReal", (userEntity.getIsReal().equals("1")? "未实名" :"实名"));
                //map.put("MOBILE", userEntity.getMobile());
                //map.put("realName", userEntity.getRealName());
                //map.put("idCard", userEntity.getIdCard());
                list.add(map);
            }
        }

        ee.addSheetByMap("会员", list, header);
        ee.export(response);
        return R.ok();
    }
}
