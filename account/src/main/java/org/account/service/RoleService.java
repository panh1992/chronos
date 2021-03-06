package org.account.service;

import org.account.entity.Role;
import org.account.repository.RoleRepository;
import org.core.exception.EntityNotExistException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import javax.annotation.Resource;
import java.util.List;
import java.util.Optional;

/**
 * 角色服务
 *
 * @author panhong
 */
@Service
public class RoleService {

    @Resource
    private RoleRepository roleRepository;

    /**
     * 查询所有角色信息列表
     */
    @Transactional(readOnly = true)
    public List<Role> findAllRole() {
        return roleRepository.findAll();
    }

    /**
     * 根据角色id获取角色信息
     */
    @Transactional(readOnly = true)
    public Role findRoleById(long roleId) {
        Optional<Role> optional = roleRepository.findById(roleId);
        if (optional.isPresent()) {
            return optional.get();
        }
        throw EntityNotExistException.build("此角色不存在");
    }

}
