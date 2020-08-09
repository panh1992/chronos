package org.storage.controller;

import org.core.params.StoreSpaceParams;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.storage.entity.StoreSpace;
import org.storage.service.StoreSpaceService;

import javax.annotation.Resource;
import java.util.List;

/**
 * 存储空间api
 *
 * @author panhong
 */
@CrossOrigin
@RestController
@RequestMapping("/spaces")
public class StoreSpaceController {

    @Resource
    private StoreSpaceService storeSpaceService;

    /**
     * 获取存储空间
     *
     * @param creatorId 创建者主键
     */
    @GetMapping
    public List<StoreSpace> findStoreSpace(@RequestHeader Long creatorId) {
        return storeSpaceService.findStoreSpace(creatorId);
    }

    /**
     * 创建存储空间
     *
     * @param creatorId 创建者主键
     * @param params    创建存储空间参数
     */
    @PostMapping
    public void createStoreSpace(@RequestHeader Long creatorId, @RequestBody StoreSpaceParams params) {
        storeSpaceService.createStoreSpace(creatorId, params);
    }

    /**
     * 修改存储空间
     *
     * @param creatorId 创建者主键
     * @param params    创建存储空间参数
     */
    @PutMapping
    public void updateStoreSpace(@RequestHeader Long creatorId, @RequestBody StoreSpaceParams params) {
        storeSpaceService.updateStoreSpace(creatorId, params);
    }


}
