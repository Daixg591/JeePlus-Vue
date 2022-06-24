/**
 * Copyright &copy; 2021-2025 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.controller.app;

import com.jeeplus.sys.controller.UserController;
import io.swagger.annotations.Api;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 用户Controller
 *
 * @author jeeplus
 * @version 2021-8-29
 */
@RestController
@RequestMapping("/app/sys/user")
@Api(tags ="用户管理")
public class AppUserController extends UserController {




}
