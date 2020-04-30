package com.business.controller;

import com.business.entity.CardEntity;
import com.business.service.CardService;
import com.business.utils.Base64;
import com.business.utils.PageUtils;
import com.business.utils.Query;
import com.business.utils.R;
import com.qiniu.util.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * Controller
 *
 * @author wengmin
 * @email wengmin@vip.qq.com
 * @date 2020-04-28 15:10:37
 */
@RestController
@RequestMapping("card")
public class CardController {
    @Autowired
    private CardService cardService;

    /**
     * 查看列表
     */
    @RequestMapping("/list")
    @RequiresPermissions("card:list")
    public R list(@RequestParam Map<String, Object> params) {
        //查询列表数据
        Query query = new Query(params);

        List<CardEntity> cardUserList = cardService.queryList(query);
        int total = cardService.queryTotal(query);
        for (CardEntity c : cardUserList) {
            c.setNickname(!StringUtils.isNullOrEmpty(c.getNickname()) ? Base64.decode(c.getNickname()) : "");
            c.setMobile(!StringUtils.isNullOrEmpty(c.getMobile()) ? Base64.decode(c.getMobile()) : "");
            c.setRealname(!StringUtils.isNullOrEmpty(c.getRealname()) ? Base64.decode(c.getRealname()) : "");
            c.setWechat(!StringUtils.isNullOrEmpty(c.getWechat()) ? Base64.decode(c.getWechat()) : "");
            c.setEmail(!StringUtils.isNullOrEmpty(c.getEmail()) ? Base64.decode(c.getEmail()) : "");
        }

        PageUtils pageUtil = new PageUtils(cardUserList, total, query.getLimit(), query.getPage());

        return R.ok().put("page", pageUtil);
    }

    /**
     * 查看信息
     */
    @RequestMapping("/info/{cardId}")
    @RequiresPermissions("card:info")
    public R info(@PathVariable("cardId") Integer cardId) {
        CardEntity cardUser = cardService.queryObject(cardId);

        return R.ok().put("cardUser", cardUser);
    }

    /**
     * 保存
     */
    @RequestMapping("/save")
    @RequiresPermissions("card:save")
    public R save(@RequestBody CardEntity cardUser) {
        cardService.save(cardUser);

        return R.ok();
    }

    /**
     * 修改
     */
    @RequestMapping("/update")
    @RequiresPermissions("card:update")
    public R update(@RequestBody CardEntity cardUser) {
        cardService.update(cardUser);

        return R.ok();
    }

    /**
     * 删除
     */
    @RequestMapping("/delete")
    @RequiresPermissions("card:delete")
    public R delete(@RequestBody Integer[] cardIds) {
        cardService.deleteBatch(cardIds);

        return R.ok();
    }

    /**
     * 查看所有列表
     */
    @RequestMapping("/queryAll")
    public R queryAll(@RequestParam Map<String, Object> params) {

        List<CardEntity> list = cardService.queryList(params);

        return R.ok().put("list", list);
    }
}
