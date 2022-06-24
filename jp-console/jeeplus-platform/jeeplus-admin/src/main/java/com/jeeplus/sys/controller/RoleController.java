/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.controller;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.aop.demo.annotation.DemoMode;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.sys.constant.CommonConstants;
import com.jeeplus.sys.domain.Role;
import com.jeeplus.sys.service.RoleService;
import com.jeeplus.sys.service.UserService;
import com.jeeplus.sys.service.dto.RoleDTO;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.utils.UserUtils;
import io.swagger.annotations.Api;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

/**
 * 角色Controller
 * @author jeeplus
 * @version 2021-09-05
 */

@Api("角色管理")
@RestController
@RequestMapping("/sys/role")
public class RoleController {

	@Autowired
	private RoleService roleService;
	@Autowired
	private UserService userService;

	/**
	 * 角色列表数据
	 * @param role
	 * @param page
	 * @return
	 */
	@ApiLog("查询角色列表")
	@PreAuthorize("hasAuthority('sys:role:list')")
	@GetMapping("list")
	public ResponseEntity<IPage<Role>> data(Role role, Page<Role> page) throws Exception {
		QueryWrapper<Role> queryWrapper = QueryWrapperGenerator.buildQueryCondition (role, Role.class);
		IPage<Role> result = roleService.page (page, queryWrapper);
		return ResponseEntity.ok (result);
	}


	/**
	 * 按id查询
	 * @param id
	 * @return
	 */
	@ApiLog("查询角色")
	@PreAuthorize ("hasAnyAuthority('sys:role:view','sys:role:add','sys:role:edit')")
	@GetMapping("queryById")
	public ResponseEntity queryById(@RequestParam String id) {
		RoleDTO roleDTO = roleService.get (id);
		String newDataRuleIds = "";
		if (roleDTO != null) {
			if (StrUtil.isNotBlank(roleDTO.getDataRuleIds())) {
				for (String rId : roleDTO.getDataRuleIds().split(",")) {
					newDataRuleIds = newDataRuleIds + "dataRule-" + rId + ',';
				}
			}
			if (newDataRuleIds.length() > 1) {
				roleDTO.setDataRuleIds(newDataRuleIds.substring(0, newDataRuleIds.length() - 1));
			}
			roleDTO.setMenuIdList(roleService.queryAllNotChildrenMenuId(roleDTO.getId()));
		}

		return ResponseEntity.ok (roleDTO);
	}

	/**
	 * 保存角色
	 * @param roleDTO
	 * @return
	 */
	@ApiLog("保存角色")
	@PreAuthorize ("hasAnyAuthority('sys:role:assign','sys:role:auth','sys:role:add','sys:role:edit')")
	@PostMapping("save")
	public ResponseEntity save(@Valid @RequestBody RoleDTO roleDTO) {
        if ( !UserUtils.getCurrentUserDTO ( ).getIsAdmin ()&& CommonConstants.YES.equals ( roleDTO.getSysData ( ) ) ) {
			return ResponseEntity.badRequest ().body ("无权操作，只有超级管理员才能修改此数据。");
		}
		if(StrUtil.isNotBlank(roleDTO.getDataRuleIds())){
			String dataRuleIds = roleDTO.getDataRuleIds();
			String newDataRuleIds= "";
			String[]ruleIds = dataRuleIds.split(",");
			for(String ruleId:ruleIds){
				if(ruleId.startsWith("dataRule-")){
					newDataRuleIds = newDataRuleIds + ruleId.substring(9) + ',';
				}
			}
			if(newDataRuleIds.length() > 1){
				roleDTO.setDataRuleIds(newDataRuleIds.substring(0, newDataRuleIds.length()-1));
			}

		}
		roleService.saveOrUpdate (roleDTO);
		return ResponseEntity.ok ("保存角色成功!");
	}


	/**
	 * 删除角色
	 * @param ids
	 * @return
	 */
	@DemoMode
	@ApiLog("删除角色")
	@PreAuthorize("hasAuthority('sys:role:del')")
	@DeleteMapping("delete")
	public ResponseEntity<String> delete( String ids) {
		StringBuffer msg = new StringBuffer();
		for(String id : ids.split(",")){
			RoleDTO roleDTO = roleService.get(id);
			if(!UserUtils.getCurrentUserDTO ().getIsAdmin () && roleDTO.getSysData().equals(CommonConstants.YES)){
				msg.append( "无权操作，只有超级管理员才能修改["+roleDTO.getName()+"]数据。");
			}else{
				roleService.deleteRole(id);
				msg.append( "删除角色["+roleDTO.getName()+"]成功。");
			}
		}
		return ResponseEntity.ok (msg.toString());
	}

	/**
	 * 获取所属角色用户
	 * @return
	 */
	@ApiLog("获取所属角色用户")
	@PreAuthorize("hasAuthority('sys:role:assign')")
	@GetMapping("assign")
	public ResponseEntity assign(UserDTO userDTO, Page<UserDTO> page) throws Exception {
		QueryWrapper<UserDTO> queryWrapper = QueryWrapperGenerator.buildQueryCondition (userDTO, UserDTO.class);
		IPage<UserDTO> result = userService.findPageByRole (page, queryWrapper);
		return ResponseEntity.ok (result);
	}



	/**
	 * 角色分配 -- 从角色中移除用户
	 * @param userId
	 * @param roleId
	 * @return
	 */
	@DemoMode
	@ApiLog("从角色中移除用户")
	@PreAuthorize("hasAuthority('sys:role:assign')")
	@DeleteMapping("removeUserFromRole")
	public ResponseEntity removeUserFromRole(String userId, String roleId) {
		RoleDTO role = roleService.get(roleId);
		UserDTO user = userService.get(userId);
		if (UserUtils.getCurrentUserDTO ().getId().equals(userId) && !UserUtils.getCurrentUserDTO ().getIsAdmin ()) {
			return ResponseEntity.badRequest ().body ("无法从角色【" + role.getName() + "】中移除用户【" + user.getName() + "】自己！");
		}else {
			if (user.getRoleDTOList ().size() <= 1){
				return ResponseEntity.badRequest ().body ("用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！这已经是该用户的唯一角色，不能移除。");
			}else{
				Boolean flag = roleService.removeUserFromRole (role, user);
				if (!flag) {
					return ResponseEntity.badRequest ().body ("用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除失败！");
				}else {
					return ResponseEntity.ok ("用户【" + user.getName() + "】从角色【" + role.getName() + "】中移除成功！");
				}
			}
		}
	}

	/**
	 * 添加用户到角色
	 * @param roleId
	 * @param userIds
	 * @return
	 */
	@DemoMode
	@ApiLog("添加用户到角色")
	@PreAuthorize("hasAuthority('sys:role:assign')")
	@PutMapping("addUserToRole")
	public ResponseEntity addUserToRole(String roleId, String[] userIds) {
		StringBuilder msg = new StringBuilder();
		int newNum = 0;
		RoleDTO roleDTO = roleService.get ( roleId );
		for (int i = 0; i < userIds.length; i++) {
			UserDTO user = roleService.addUserToRole (roleDTO, userService.get(userIds[i]));
			if (user != null) {
				msg.append("<br/>新增用户【" + user.getName() + "】到角色【" + roleDTO.getName() + "】！");
				newNum++;
			}
		}
		return ResponseEntity.ok ("已成功分配 "+newNum+" 个用户"+msg);
	}

    /**
     * 验证角色名是否存在
     * @param role
     * @return
     */
    @ApiLog("验证角色名是否存在")
    @GetMapping("validateNotExist")
    public ResponseEntity validateExist(Role role) {

        role = roleService.lambdaQuery ( ).eq ( StrUtil.isNotBlank ( role.getName ( ) ), Role::getName, role.getName ( ) )
                .eq ( StrUtil.isNotBlank ( role.getEnName ( ) ), Role::getEnName, role.getEnName ( ) ).one ( );

        if ( role == null ) {
            return ResponseEntity.ok ( true );
        } else {
            return ResponseEntity.ok ( false );
        }
    }

}
