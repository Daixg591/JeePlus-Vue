/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.core.service;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.core.domain.TreeEntity;
import com.jeeplus.core.domain.TreeMapper;
import com.jeeplus.core.service.TreeService;
import com.jeeplus.core.service.dto.TreeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

import java.lang.reflect.ParameterizedType;
import java.util.List;
import java.util.Map;

/**
 * Service基类
 *
 * @author jeeplus
 * @version 2021-05-16
 */
@Slf4j
@Transactional
public abstract class TreeDTOService<D extends TreeMapper <T>, T extends TreeEntity <T>, DTO extends TreeDTO <DTO>> extends TreeService <D, T> {



    private Class<DTO> clzzDTO;

    public TreeDTOService() {
        clzzDTO = (Class<DTO>) ((ParameterizedType) getClass().getGenericSuperclass()).getActualTypeArguments()[2];
    }





    /**
     * 获取JSON树形数据。
     * @return
     */
    public List <DTO> treeDataDTO() {
        return treeDataDTO ( null );
    }


    public abstract List<DTO> listDTO();
    /**
     * 获取JSON树形数据。
     *
     * @param extId 排除的ID
     * @return
     */
    public List <DTO> treeDataDTO(String extId) {
        List <DTO> allList = this.listDTO ( );
        try {
            DTO root = clzzDTO.getConstructor ( ).newInstance ( );
            root.setId ( TreeDTO.getRootId () );
            List <DTO> rootTree = this.formatListToTree ( root, allList, extId );
            return rootTree;
        } catch (Exception e) {
            log.error ( "{}", e );
            return null;
        }

    }



    /**
     * 以root为根节点, 将allList从线性列表转为树形列表
     *
     * @param root    根节点, 为空抛出空指针异常
     * @param allList 所有需要参与构造为树的列表
     * @param extId   需要排除在树之外的节点(子节点一并被排除)
     * @return java.util.List<T>
     * @Author 滕鑫源
     * @Date 2020/10/23 17:04
     **/
    public List <DTO> formatListToTree(DTO root, List <DTO> allList, String extId) {
        String rootId = root.getId ( );

        // 最终的树形态
        List <DTO> trees = Lists.newArrayList ( );

        // 把需要构造树的所有列表, 根据以父id作为key, 整理为列表
        Map <String, List <DTO>> treeMap = Maps.newHashMap ( );
        for (DTO entity : allList) {
            List <DTO> entities = treeMap.get ( entity.getParentId ( ) );
            if ( entities == null ) {
                entities = Lists.newLinkedList ( );
            }

            // 剔除排除项, 构造treeMap, 转递归为线性操作
            if ( StrUtil.isBlank ( extId ) || (!extId.equals ( entity.getId ( ) ) && entity.getParentIds ( ).indexOf ( "," + extId + "," ) == -1) ) {
                entities.add ( entity );
                treeMap.put ( entity.getParentId ( ), entities );
            }
        }

        // 没有给定的子树, 返回空树
        if ( treeMap.get ( rootId ) == null || treeMap.get ( rootId ).isEmpty ( ) ) {
            return trees;
        }

        // 开始递归格式化
        List <DTO> children = treeMap.get ( rootId );
        for (DTO parent : children) {
            formatFillChildren ( parent, treeMap );
            trees.add ( parent );
        }
        if ( StrUtil.equals ( rootId, TreeDTO.getRootId () ) ) {
            return children;
        } else {
            root.setChildren ( trees );
            return Lists.newArrayList ( root );
        }
    }

    /**
     * 从treeMap中取出子节点填入parent, 并递归此操作
     *
     * @param parent
     * @param treeMap
     * @return void
     * @Author 滕鑫源
     * @Date 2020/9/30 16:33
     **/
    private void formatFillChildren(DTO parent, Map <String, List <DTO>> treeMap) {
        List <DTO> children = treeMap.get ( parent.getId ( ) );
        parent.setChildren ( children );
        if ( children != null && !children.isEmpty ( ) ) {
            for (DTO child : children) {
                formatFillChildren ( child, treeMap );
            }
        }
    }
}
