/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.controller;

import cn.afterturn.easypoi.excel.ExcelExportUtil;
import cn.afterturn.easypoi.excel.entity.ExportParams;
import cn.afterturn.easypoi.excel.entity.enmus.ExcelType;
import cn.hutool.core.date.DateUtil;
import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.baomidou.mybatisplus.core.metadata.IPage;
import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.aop.demo.annotation.DemoMode;
import com.jeeplus.aop.logging.annotation.ApiLog;
import com.jeeplus.common.utils.FileUtils;
import com.jeeplus.common.utils.ResponseUtil;
import com.jeeplus.config.properties.FileProperties;
import com.jeeplus.core.query.QueryWrapperGenerator;
import com.jeeplus.security.util.SecurityUtils;
import com.jeeplus.sys.domain.User;
import com.jeeplus.sys.service.UserService;
import com.jeeplus.sys.service.dto.UserDTO;
import com.jeeplus.sys.service.mapstruct.UserWrapper;
import com.jeeplus.sys.utils.*;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.apache.poi.ss.usermodel.Workbook;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;
import java.io.File;
import java.io.IOException;
import java.net.URLEncoder;
import java.util.Date;
import java.util.List;
import java.util.Map;

/**
 * 用户Controller
 *
 * @author jeeplus
 * @version 2021-8-29
 */

@Api(tags ="用户管理")
@RestController
@RequestMapping("/sys/user")
public class UserController {
    @Autowired
    private UserService userService;
    @Autowired
    private FileProperties fileProperties;

    /**
     * 根据id查询用户
     * @param id
     * @return
     */
    @ApiLog("查询用户")
    @GetMapping("queryById")
    @ApiOperation(value = "查询用户")
    public ResponseEntity queryById(@RequestParam("id") String id) {
        UserDTO userDTO = userService.get (id);
        return ResponseEntity.ok (userDTO);
    }

    /**
     * 查询列表
     * @param userDTO
     * @param page
     * @return
     */
    @ApiLog("用户数据列表")
    @PreAuthorize("hasAuthority('sys:user:list')")
    @GetMapping("list")
    public ResponseEntity list(UserDTO userDTO, Page<UserDTO> page) throws Exception {
        QueryWrapper<UserDTO> queryWrapper = QueryWrapperGenerator.buildQueryCondition (userDTO, UserDTO.class);
        IPage<UserDTO> result = userService.findPage (page, queryWrapper);
        return ResponseEntity.ok (result);
    }

    /**
     * 保存用户
     * @param userDTO
     * @return
     */
    @DemoMode
    @ApiLog("保存用户")
    @PreAuthorize ("hasAnyAuthority('sys:user:add', 'sys:user:edit')")
    @ApiOperation(value = "保存用户")
    @PostMapping("save")
    public ResponseEntity save(@Valid @RequestBody UserDTO userDTO) {
        // 如果新密码为空，则不更换密码
        if (StrUtil.isNotBlank(userDTO.getNewPassword())) {
            userDTO.setPassword( SecurityUtils.encryptPassword (userDTO.getNewPassword()));
        }
        if (!isCheckLoginName(userDTO.getOldLoginName(), userDTO.getLoginName())) {
            return ResponseEntity.badRequest ().body ("保存用户'" + userDTO.getLoginName() + "'失败，登录名已存在!");
        }
        // 保存用户信息
        userService.saveOrUpdate (userDTO);
        return ResponseEntity.ok ("保存用户'" + userDTO.getLoginName() + "'成功!");
    }


    /**
     * 用户信息显示编辑保存
     *
     * @param userDTO
     * @return
     */
    @DemoMode
    @ApiLog("修改个人资料")
    @ApiOperation(value = "修改个人资料")
    @PostMapping("saveInfo")
    public ResponseEntity saveInfo(@RequestBody UserDTO userDTO) {
        userService.updateById (UserWrapper.INSTANCE.toEntity (userDTO));
        UserUtils.deleteCache ( UserUtils.getCurrentUserDTO () );
        return ResponseEntity.ok ("修改个人资料成功!");
    }

    /**
     * 批量删除用户
     */
    @DemoMode
    @ApiLog("删除用户")
    @ApiOperation(value = "删除用户")
    @PreAuthorize ("hasAuthority('sys:user:del')")
    @DeleteMapping("delete")
    public ResponseEntity delete(String ids) {
        String idArray[] = ids.split(",");
        StringBuffer msg = new StringBuffer();
        boolean success = true;
        for (String id : idArray) {
            UserDTO userDTO = userService.get(id);
            if ( UserUtils.getCurrentUserDTO ().getId().equals(userDTO.getId())) {
                success = false;
                msg.append("["+userDTO.getLoginName()+"]删除失败，不允许删除当前用户!<br/>");
            } else if (userDTO.getIsAdmin ()) {
                success = false;
                msg.append("["+userDTO.getLoginName()+"]删除失败，不允许删除超级管理员!<br/>");//删除用户失败, 不允许删除超级管理员用户
            } else {
                msg.append("["+userDTO.getLoginName()+"]删除成功!<br/>");
                userService.deleteUser(userDTO);//删除用户成功
            }
        }
        if(success){
            return ResponseEntity.ok (msg.toString());
        }else {
            return ResponseEntity.badRequest ().body (msg.toString());
        }
    }

    /**
     * 导出用户数据
     * @param userDTO
     * @param page
     * @param response
     * @throws Exception
     */
    @ApiLog("导出用户数据")
    @PreAuthorize ("hasAnyAuthority('sys:user:export')")
    @GetMapping("export")
    @ApiOperation(value = "导出用户excel")
    public void exportFile(UserDTO userDTO, Page<UserDTO> page, HttpServletResponse response) throws Exception {
        String fileName = "用户数据" + DateUtil.format (new Date (), "yyyyMMddHHmmss") +".xlsx";
        QueryWrapper<UserDTO> queryWrapper = QueryWrapperGenerator.buildQueryCondition (userDTO, UserDTO.class);
        IPage<UserDTO> result = userService.findPage (page, queryWrapper);

        try {
            ExportParams params = new ExportParams("用户数据", fileName, ExcelType.XSSF);
            Workbook workbook = ExcelExportUtil.exportExcel(params, UserDTO.class, result.getRecords ());
            response.reset();
            response.setContentType("application/octet-stream; charset=utf-8");
            response.setHeader("Content-Disposition", URLEncoder.encode(fileName, "UTF8"));
            workbook.write(response.getOutputStream());
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    /**
     * 导入用户数据
     *
     * @return
     */
//    @PreAuthorize ("hasAnyAuthority('sys:user:import")
//    @PostMapping("import")
//    @ApiOperation(value = "导入用户excel")
//    public AjaxJson importFile(MultipartFile file, RedirectAttributes redirectAttributes) {
//        if (jeePlusProperites.isDemoMode()) {
//            return AjaxJson.error("演示模式，不允许操作！");
//        }
//        try {
//            int successNum = 0;
//            int failureNum = 0;
//            StringBuilder failureMsg = new StringBuilder();
//            ImportExcel ei = new ImportExcel(file, 1, 0);
//            List<User> list = ei.getDataList(User.class);
//            for (User user : list) {
//                try {
//                    if (isCheckLoginName("", user.getLoginName())) {
//                        user.setPassword(userService.entryptPassword("123456"));
//                        BeanValidators.validateWithException(validator, user);
//                        userService.saveUser(user);
//                        successNum++;
//                    } else {
//                        failureMsg.append("<br/>登录名 " + user.getLoginName() + " 已存在; ");
//                        failureNum++;
//                    }
//                } catch (ConstraintViolationException ex) {
//                    failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：");
//                    List<String> messageList = BeanValidators.extractPropertyAndMessageAsList(ex, ": ");
//                    for (String message : messageList) {
//                        failureMsg.append(message + "; ");
//                        failureNum++;
//                    }
//                } catch (Exception ex) {
//                    failureNum++;
//                    failureMsg.append("<br/>登录名 " + user.getLoginName() + " 导入失败：" + ex.getMessage());
//                }
//            }
//            if (failureNum > 0) {
//                failureMsg.insert(0, "，失败 " + failureNum + " 条用户，导入信息如下：");
//            }
//            return AjaxJson.success("已成功导入 " + successNum + " 条用户" + failureMsg);
//        } catch (Exception e) {
//            return AjaxJson.error("导入用户失败！失败信息：" + e.getMessage());
//        }
//    }
//
//    /**
//     * 下载导入用户数据模板
//     *
//     * @param response
//     * @return
//     */
//    @PreAuthorize ("hasAnyAuthority('sys:user:import")
//    @GetMapping("import/template")
//    @ApiOperation(value = "下载模板")
//    public AjaxJson importFileTemplate(HttpServletResponse response) {
//        try {
//            String fileName = "用户数据导入模板.xlsx";
//            List<User> list = Lists.newArrayList();
//            list.add(UserUtils.getCurrentUserDTO ());
//            new ExportExcel("用户数据", User.class, 2).setDataList(list).write(response, fileName).dispose();
//            return null;
//        } catch (Exception e) {
//            return AjaxJson.error("导入模板下载失败！失败信息：" + e.getMessage());
//        }
//    }



    private boolean isCheckLoginName(String oldLoginName, String loginName) {
        if (loginName != null && loginName.equals(oldLoginName)) {
           return true;
        } else if (loginName != null && userService.getUserByLoginName(loginName) == null) {
            return true;
        }
      return false;
    }

    /**
     * 用户头像显示编辑保存
     *
     * @return
     * @throws IOException
     * @throws IllegalStateException
     */
    @ApiLog("上传头像")
    @PostMapping("imageUpload")
    @ApiOperation(value = "上传头像")
    public ResponseEntity imageUpload(MultipartFile file) throws IllegalStateException, IOException {
        String  userId = UserUtils.getCurrentUserDTO ().getId ();
        // 判断文件是否为空
        if (!file.isEmpty()) {
            if(fileProperties.isImage (file.getOriginalFilename ())){
                // 文件保存路径
                String realPath = FileKit.getAttachmentDir() + "sys/user/images/";
                // 转存文件
                FileUtils.createDirectory(realPath);
                file.transferTo(new File(realPath + file.getOriginalFilename()));
                User user = new User (userId);
                user.setPhoto (FileKit.getAttachmentUrl() + "sys/user/images/" + file.getOriginalFilename());
                userService.updateById (user);
                UserUtils.deleteCache ( UserUtils.getCurrentUserDTO () );
                return ResponseUtil.newInstance ().add ("path", FileKit.getAttachmentUrl() + "sys/user/images/" + file.getOriginalFilename()).ok ("上传成功!");
            }else{
                return ResponseEntity.badRequest().body ("请上传图片!");
            }

        }else{
            return ResponseEntity.badRequest().body ("文件不存在!");
        }


    }

    /**
     * 返回用户信息
     *
     * @return
     */
    @ApiLog("获取当前用户信息")
    @GetMapping("info")
    @ApiOperation(value = "获取当前用户信息")
    public ResponseEntity infoData() {
        return ResponseUtil.newInstance ().add("role", UserUtils.getRoleDTOList()).add("user", UserUtils.getCurrentUserDTO ()).ok ("获取个人信息成功!");
    }


    @DemoMode
    @ApiLog("修改密码")
    @PutMapping("savePwd")
    @ApiOperation(value = "修改密码")
    public ResponseEntity savePwd(String oldPassword, String newPassword) {
        UserDTO userDTO = UserUtils.getCurrentUserDTO ();
        if (StrUtil.isNotBlank(oldPassword) && StrUtil.isNotBlank(newPassword)) {
            if (SecurityUtils.validatePassword(oldPassword, userDTO.getPassword())) {
                User user = new User (userDTO.getId ());
                user.setPassword (SecurityUtils.encryptPassword ( newPassword ));
                userService.updateById (user);
                UserUtils.deleteCache (userDTO );
                return ResponseEntity.ok ("修改密码成功！");
            } else {
                return ResponseEntity.badRequest ().body ("修改密码失败，旧密码错误！");
            }
        }
        return ResponseEntity.badRequest ().body ("参数错误！");
    }


    @ApiLog("查询用户列表")
    @GetMapping("treeData")
    public List<Map<String, Object>> treeData(@RequestParam(required = false) String officeId, HttpServletResponse response) {
        List<Map<String, Object>> mapList = Lists.newArrayList();
        List<User> list = userService.lambdaQuery ().eq (User::getOfficeId, officeId).list ();
        for (int i = 0; i < list.size(); i++) {
            User e = list.get(i);
            Map<String, Object> map = Maps.newHashMap();
            map.put("id", "u_" + e.getId());
            map.put("pId", officeId);
            map.put("name", StrUtil.replace(e.getName(), " ", ""));
            mapList.add(map);
        }
        return mapList;
    }

    /**
     * 获取菜单
     * @return
     */
    @ApiLog("获取用户菜单")
    @GetMapping("getMenus")
    public ResponseEntity getMenus() {
        return ResponseUtil.newInstance ()
                .add ("dictList",  DictUtils.getDictMap()) //获取字典
                .add("permissions", UserUtils.getPermissions())
                .add("menuList", MenuUtils.getMenus())
                .add ("routerList", RouterUtils.getRoutersByMenu()).ok ();
    }





}
