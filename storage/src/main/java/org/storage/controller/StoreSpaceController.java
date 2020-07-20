package org.storage.controller;

import org.core.params.StoreSpaceParams;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.storage.service.StoreSpaceService;

import javax.annotation.Resource;

/**
 * 存储空间api
 */
@RestController
@RequestMapping("/spaces")
public class StoreSpaceController {

    @Resource
    private StoreSpaceService storeSpaceService;

    @PostMapping
    public void createStoreSpace(@RequestHeader Long creatorId, @RequestBody StoreSpaceParams params) {
        storeSpaceService.createStoreSpace(creatorId, params);
    }


}
