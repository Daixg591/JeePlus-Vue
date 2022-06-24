/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.sys.service;

import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.core.service.TreeService;
import com.jeeplus.sys.constant.CommonConstants;
import com.jeeplus.sys.constant.enums.OfficeTypeEnum;
import com.jeeplus.sys.domain.Office;
import com.jeeplus.sys.mapper.OfficeMapper;
import com.jeeplus.sys.service.dto.OfficeDTO;
import com.jeeplus.sys.service.mapstruct.OfficeWrapper;
import org.apache.commons.lang.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

/**
 * 机构Service
 * @author jeeplus
 * @version 2021-05-16
 */
@Service
@Transactional
public class OfficeService extends TreeService<OfficeMapper, Office> {

    @Autowired
    private OfficeWrapper officeWrapper;

    public List <OfficeDTO> getRootTree(List<OfficeDTO> list, String extId, String type, String showAll) {
        List<OfficeDTO> offices = Lists.newArrayList ();
        List<OfficeDTO> rootTrees = officeWrapper.toDTO (super.getChildren (new Office (OfficeDTO.getRootId ())));
        for (OfficeDTO root : rootTrees) {
            if (this.isUseAble ( extId, type,root, showAll )){
                // 不是被排除节点的子节点
                List<OfficeDTO> officeList = formatListToTree (root, list, extId, type, showAll);
                offices.addAll (officeList);
            }
        }
        return offices;
    }


    public List<OfficeDTO> formatListToTree(OfficeDTO root, List<OfficeDTO> allList, String extId, String type, String showAll) {
        String rootId = root.getId ();

        // type为2时，是选择部门，因此禁用type为1的公司节点
        if(OfficeTypeEnum.OFFICE.getValue ().equals(type) && root.getType().equals(OfficeTypeEnum.COMPANY.getValue ())){
            root.setDisabled(true);
        }else {
            root.setDisabled(false);
        }
        // 最终的树形态
        List<OfficeDTO> trees = Lists.newArrayList ();

        // 把需要构造树的所有列表, 根据以父id作为key, 整理为列表
        Map <String, List<OfficeDTO>> treeMap = Maps.newHashMap ();
        for (OfficeDTO entity : allList) {
            List<OfficeDTO> offices = treeMap.get (entity.getParent ().getId ());
            if (offices == null) {
                offices = Lists.newLinkedList ();
            }

            if (this.isUseAble ( extId, type,root, showAll )){
                // type为2时，是选择部门，因此禁用type为1的公司节点
                if(OfficeTypeEnum.OFFICE.getValue ().equals(type) && entity.getType().equals(OfficeTypeEnum.COMPANY.getValue ())){
                    entity.setDisabled(true);
                }else {
                    entity.setDisabled(false);
                }
                offices.add (entity);
                treeMap.put (entity.getParent ().getId (), offices);
            }
        }

        // 开始递归格式化
        List<OfficeDTO> children = treeMap.get (rootId);
        if (children != null) {
            for (OfficeDTO parent : children) {
                formatFillChildren (parent, treeMap);
                trees.add (parent);
            }
        }

        root.setChildren (trees);
        return Lists.newArrayList (root);
    }

    /**
     * 从treeMap中取出子节点填入parent, 并递归此操作
     **/
    private void formatFillChildren(OfficeDTO parent, Map<String, List<OfficeDTO>> treeMap) {

        List<OfficeDTO> children = treeMap.get (parent.getId ());
        parent.setChildren (children);
        if (children != null && !children.isEmpty ()) {
            for (OfficeDTO child : children) {
                formatFillChildren (child, treeMap);
            }
        }
    }

    private  boolean isUseAble(String extId, String type, OfficeDTO dto, String showAll){
        return  (StringUtils.isBlank (extId) || (extId != null && !extId.equals (dto.getId ()) && dto.getParentIds ().indexOf ("," + extId + ",") == -1))
                && (type == null || (type != null && (type.equals ( OfficeTypeEnum.COMPANY.getValue ()) ? type.equals (dto.getType ()) : true)))
                &&(CommonConstants.YES.equals ( showAll ) || CommonConstants.YES.equals (dto.getUseable ()));
    }



}
