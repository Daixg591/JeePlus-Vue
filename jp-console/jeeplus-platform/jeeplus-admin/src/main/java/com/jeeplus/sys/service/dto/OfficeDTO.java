/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service.dto;

import com.jeeplus.core.service.dto.TreeDTO;
import io.swagger.annotations.ApiModelProperty;
import lombok.Data;
import lombok.EqualsAndHashCode;
import springfox.documentation.annotations.ApiIgnore;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import java.util.List;

/**
 * 机构Entity
 * @author jeeplus
 * @version 2021-05-15
 */
@Data
@EqualsAndHashCode(callSuper = false)
public class OfficeDTO extends TreeDTO <OfficeDTO> {

	private static final long serialVersionUID = 1L;

	/**
	 * 归属区域
	 */
	@NotNull
	private AreaDTO areaDTO;

	/**
	 * 机构编码
	 */
	@Size(min = 0, max = 64)
	private String code;

	/**
	 * 机构类型（1：公司；2：部门）
	 */
	@Size(min = 1, max = 1 )
	private String type;

	/**
	 * 机构等级（1：一级；2：二级；3：三级；4：四级）
	 */
	@Size(min =1, max = 1)
	private String grade;

	/**
	 * 联系地址
	 */
	@Size(min = 0, max = 255)
	private String address;

	/**
	 * 邮政编码
	 */
	@Size(min = 0, max = 64)
	private String zipCode;

	/**
	 * 负责人
	 */
	private String master;

	/**
	 * 电话
	 */
	@Size(max = 64)
	private String phone;

	/**
	 * 传真
	 */
	@Size(max = 64)
	private String fax;

	/**
	 * 邮箱
	 */
	@Size(max = 64)
	private String email;

	/**
	 * 是否可用
	 */
	private String useable;

	/**
	 * 主负责人
	 */
	@ApiModelProperty(hidden = true)
	private UserDTO primaryPerson;

	/**
	 * 副负责人
	 */
	@ApiModelProperty(hidden = true)
	private UserDTO deputyPerson;

	/**
	 * 备注
	 */
	private String remarks;

	/**
	 * 子部门
	 */
	private List<String> childDeptList;

	/**
	 * 是否可用
	 */
	private boolean disabled = false;

}
