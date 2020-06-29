
package com.gemframework.modules.extend.novel.controller;

import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;

import com.gemframework.common.annotation.Log;
import com.gemframework.common.utils.GemBeanUtils;
import com.gemframework.common.constant.GemModules;
import com.gemframework.modules.perkit.BaseController;
import com.gemframework.model.common.BaseResultData;
import com.gemframework.model.common.PageInfo;
import com.gemframework.model.common.validator.SaveValidator;
import com.gemframework.model.common.validator.UpdateValidator;
import com.gemframework.model.enums.ErrorCode;
import com.gemframework.model.enums.OperateType;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.apache.shiro.authz.annotation.RequiresPermissions;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import com.gemframework.modules.extend.novel.entity.NovelReward;
import com.gemframework.modules.extend.novel.entity.NovelRewardVo;
import com.gemframework.modules.extend.novel.service.NovelRewardService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title: NovelRewardController
 * @Date: 2020-06-29 14:57:23
 * @Version: v1.0
 * @Description: 控制器
 * @Author: yuanrise
 * @Email: 1649951967@qq.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
@RestController
@RequestMapping(GemModules.Extend.PATH_PRE+"/novel/novelReward")
public class NovelRewardController extends BaseController {

    private static final String moduleName = "";

    @Autowired
    private NovelRewardService novelRewardService;

    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/page")
    @RequiresPermissions("novelReward:page")
    public BaseResultData page(PageInfo pageInfo, NovelRewardVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = novelRewardService.page(setOrderPage(pageInfo),queryWrapper);
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }
    /**
     * 获取列表
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("novelReward:list")
    public BaseResultData list(NovelRewardVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = novelRewardService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加
     * @return
     */
    @Log(type = OperateType.ALTER,value = "保存"+moduleName)
    @PostMapping("/save")
    @RequiresPermissions("novelReward:save")
    public BaseResultData save(@RequestBody NovelRewardVo vo) {
        GemValidate(vo, SaveValidator.class);
        NovelReward entity = GemBeanUtils.copyProperties(vo, NovelReward.class);
        if(!novelRewardService.save(entity)){
            return BaseResultData.ERROR(ErrorCode.SAVE_OR_UPDATE_FAIL);
        }
        return BaseResultData.SUCCESS(entity);
    }


    /**
     * 删除 & 批量刪除
     * @return
     */
    @Log(type = OperateType.ALTER,value = "删除"+moduleName)
    @PostMapping("/delete")
    @RequiresPermissions("novelReward:delete")
    public BaseResultData delete(Long id,String ids) {
        if(id!=null) novelRewardService.removeById(id);
        if(StringUtils.isNotBlank(ids)){
            List<Long> listIds = Arrays.asList(ids.split(",")).stream().map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
            if(listIds!=null && !listIds.isEmpty()){
                    novelRewardService.removeByIds(listIds);
            }
        }
        return BaseResultData.SUCCESS();
    }


    /**
     * 编辑
     * @return
     */
    @Log(type = OperateType.ALTER,value = "编辑"+moduleName)
    @PostMapping("/update")
    @RequiresPermissions("novelReward:update")
    public BaseResultData update(@RequestBody NovelRewardVo vo) {
        GemValidate(vo, UpdateValidator.class);
        NovelReward entity = GemBeanUtils.copyProperties(vo, NovelReward.class);
        if(!novelRewardService.updateById(entity)){
            return BaseResultData.ERROR(ErrorCode.SAVE_OR_UPDATE_FAIL);
        }
        return BaseResultData.SUCCESS(entity);
    }


    /**
     * 获取用户信息ById
     * @return
     */
    @Log(type = OperateType.NORMAL,value = "查看"+moduleName)
    @GetMapping("/info")
    @RequiresPermissions("novelReward:info")
    public BaseResultData info(Long id) {
        NovelReward info = novelRewardService.getById(id);
        return BaseResultData.SUCCESS(info);
    }

}