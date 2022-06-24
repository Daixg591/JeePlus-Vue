/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.core.service;

import cn.hutool.core.util.StrUtil;
import com.baomidou.mybatisplus.core.conditions.query.LambdaQueryWrapper;
import com.baomidou.mybatisplus.extension.service.impl.ServiceImpl;
import com.google.common.collect.Lists;
import com.google.common.collect.Maps;
import com.jeeplus.core.domain.TreeEntity;
import com.jeeplus.core.domain.TreeMapper;
import com.jeeplus.core.service.dto.TreeDTO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.transaction.annotation.Transactional;

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
public abstract class TreeService<D extends TreeMapper <T>, T extends TreeEntity <T>> extends ServiceImpl <D, T> {


    public boolean saveOrUpdate(T entity) {
        T parent;
        // 如果没有设置父节点，则代表为跟节点，有则获取父节点实体
        if ( TreeDTO.getRootId ().equals ( entity.getParentId ( ) ) || StrUtil.isBlank ( entity.getParentId ( ) ) ) {
            parent = null;
        } else {
            parent = super.getById ( entity.getParentId ( ) );
        }
        if ( parent == null ) {
            try {
                parent = entityClass.getConstructor ( ).newInstance ( );
                parent.setId ( TreeDTO.getRootId () );

            } catch (Exception e) {
                log.error ( "{}", e );
            }
            entity.setParentId ( parent.getId ( ) );
            parent.setParentIds ( StrUtil.EMPTY );
        }
        // 获取修改前的parentIds，用于更新子节点的parentIds
        String oldParentIds = StrUtil.isNotBlank ( entity.getId ( ) ) ? super.getById ( entity.getId ( ) ).getParentIds ( ) : null;
        // 设置新的父节点串
        entity.setParentIds ( parent.getParentIds ( ) + parent.getId ( ) + "," );
        // 保存或更新实体
        super.saveOrUpdate ( entity );
        LambdaQueryWrapper <T> queryWrapper = new LambdaQueryWrapper <> ( (Class <T>) entity.getClass ( ) ).eq ( T::getParentIds, entity.getId ( ) );
        List <T> list = super.list ( queryWrapper );
        for (T e : list) {
            if ( e.getParentIds ( ) != null && oldParentIds != null ) {
                e.setParentIds ( e.getParentIds ( ).replace ( oldParentIds, entity.getParentIds ( ) ) );
                LambdaQueryWrapper <T> updateWrapper = new LambdaQueryWrapper <> ( (Class <T>) entity.getClass ( ) ).eq ( T::getId, e.getId ( ) );
                super.update ( updateWrapper );
            }
        }
        return true;

    }

    public List <T> getChildren(T parent) {
        LambdaQueryWrapper <T> queryWrapper = new LambdaQueryWrapper <> ( (Class <T>) parent.getClass ( ) ).eq ( T::getParentId, parent.getId ( ) );
        return super.list ( queryWrapper);
    }

    /**
     * 删除数据
     *
     * @param id
     */
    public boolean removeWithChildrenById(String id) {
        LambdaQueryWrapper <T> queryWrapper = new LambdaQueryWrapper <> ( (Class <T>) entityClass ).like ( StrUtil.isNotBlank ( id ), T::getParentIds, id ).or ( ).eq ( StrUtil.isNotBlank ( id ), T::getId, id );
        return super.remove (queryWrapper );
    }

    /**
     * 批量删除数据
     */
    public void removeWithChildrenByIds(List <String> idList) {
        idList.stream ( ).forEach ( this::removeWithChildrenById );
    }

    /**
     * 获取JSON树形数据。
     * @return
     */
    public List <T> treeData() {
        return treeData ( null );
    }


    /**
     * 获取JSON树形数据。
     *
     * @param extId 排除的ID
     * @return
     */
    public List <T> treeData(String extId) {
        List <T> allList = this.list ( );
        try {
            T root = entityClass.getConstructor ( ).newInstance ( );
            root.setId ( TreeDTO.getRootId () );
            List <T> rootTree = this.formatListToTree ( root, allList, extId );
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
    public List <T> formatListToTree(T root, List <T> allList, String extId) {
        String rootId = root.getId ( );

        // 最终的树形态
        List <T> trees = Lists.newArrayList ( );

        // 把需要构造树的所有列表, 根据以父id作为key, 整理为列表
        Map <String, List <T>> treeMap = Maps.newHashMap ( );
        for (T entity : allList) {
            List <T> entities = treeMap.get ( entity.getParentId ( ) );
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
        List <T> children = treeMap.get ( rootId );
        for (T parent : children) {
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
    private void formatFillChildren(T parent, Map <String, List <T>> treeMap) {
        List <T> children = treeMap.get ( parent.getId ( ) );
        parent.setChildren ( children );
        if ( children != null && !children.isEmpty ( ) ) {
            for (T child : children) {
                formatFillChildren ( child, treeMap );
            }
        }
    }
}
