
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

import com.gemframework.modules.extend.novel.entity.NovelRecharge;
import com.gemframework.modules.extend.novel.entity.NovelRechargeVo;
import com.gemframework.modules.extend.novel.service.NovelRechargeService;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

/**
 * @Title: NovelRechargeController
 * @Date: 2020-06-29 14:57:25
 * @Version: v1.0
 * @Description: 控制器
 * @Author: yuanrise
 * @Email: 1649951967@qq.com
 * @Copyright: Copyright (c) 2020 wanyong
 * @Company: www.gemframework.com
 */
@Slf4j
@RestController
@RequestMapping(GemModules.Extend.PATH_PRE+"/novel/novelRecharge")
public class NovelRechargeController extends BaseController {

    private static final String moduleName = "";

    @Autowired
    private NovelRechargeService novelRechargeService;

    /**
     * 获取列表分页
     * @return
     */
    @GetMapping("/page")
    @RequiresPermissions("novelRecharge:page")
    public BaseResultData page(PageInfo pageInfo, NovelRechargeVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        Page page = novelRechargeService.page(setOrderPage(pageInfo),queryWrapper);
        return BaseResultData.SUCCESS(page.getRecords(),page.getTotal());
    }
    /**
     * 获取列表
     * @return
     */
    @GetMapping("/list")
    @RequiresPermissions("novelRecharge:list")
    public BaseResultData list(NovelRechargeVo vo) {
        QueryWrapper queryWrapper = makeQueryMaps(vo);
        List list = novelRechargeService.list(queryWrapper);
        return BaseResultData.SUCCESS(list);
    }

    /**
     * 添加
     * @return
     */
    @Log(type = OperateType.ALTER,value = "保存"+moduleName)
    @PostMapping("/save")
    @RequiresPermissions("novelRecharge:save")
    public BaseResultData save(@RequestBody NovelRechargeVo vo) {
        GemValidate(vo, SaveValidator.class);
        NovelRecharge entity = GemBeanUtils.copyProperties(vo, NovelRecharge.class);
        if(!novelRechargeService.save(entity)){
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
    @RequiresPermissions("novelRecharge:delete")
    public BaseResultData delete(Long id,String ids) {
        if(id!=null) novelRechargeService.removeById(id);
        if(StringUtils.isNotBlank(ids)){
            List<Long> listIds = Arrays.asList(ids.split(",")).stream().map(s ->Long.parseLong(s.trim())).collect(Collectors.toList());
            if(listIds!=null && !listIds.isEmpty()){
                    novelRechargeService.removeByIds(listIds);
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
    @RequiresPermissions("novelRecharge:update")
    public BaseResultData update(@RequestBody NovelRechargeVo vo) {
        GemValidate(vo, UpdateValidator.class);
        NovelRecharge entity = GemBeanUtils.copyProperties(vo, NovelRecharge.class);
        if(!novelRechargeService.updateById(entity)){
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
    @RequiresPermissions("novelRecharge:info")
    public BaseResultData info(Long id) {
        NovelRecharge info = novelRechargeService.getById(id);
        return BaseResultData.SUCCESS(info);
    }

}