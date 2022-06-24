/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.flowable.controller;

import com.baomidou.mybatisplus.extension.plugins.pagination.Page;
import com.jeeplus.extension.domain.FlowCopy;
import com.jeeplus.extension.service.FlowCopyService;
import com.jeeplus.flowable.service.FlowProcessService;
import com.jeeplus.flowable.vo.ProcessVo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * 流程定义相关Controller
 *
 * @author jeeplus
 * @version 2021-09-03
 */
@RestController
@RequestMapping("/flowable/process/history")
public class FlowableProcessHistoryController {

    @Autowired
    private FlowProcessService flowProcessService;

    @Autowired
    private FlowCopyService flowCopyService;

    /**
     * 已结束的实例
     */
    @GetMapping("historyList")
    public ResponseEntity historyListData(Page <ProcessVo> page, String procInsId, String procDefKey) throws Exception {
        page = flowProcessService.historyList(page, procInsId, procDefKey);
        return ResponseEntity.ok (page);
    }

    /**
     * 删除历史流程实例
     *
     * @param procInsId 流程实例ID
     */
    @DeleteMapping("deleteProcIns")
    public ResponseEntity deleteProcIns(String procInsId) {
        flowProcessService.delHistoryProcInsById(procInsId);
        flowCopyService.lambdaUpdate ().eq ( FlowCopy::getProcInsId, procInsId ).remove ();
        return ResponseEntity.ok ("删除成功，流程实例ID=" + procInsId);
    }

    /**
     * 删除历史流程实例
     *
     * @param procInsIds 流程实例ID
     */
    @DeleteMapping("deleteAllProcIns")
    public ResponseEntity deleteAllProcIns(String procInsIds) {
        String[] procInsIdArra = procInsIds.split(",");
        for (String procInsId : procInsIdArra) {
            flowProcessService.delHistoryProcInsById(procInsId);
            flowCopyService.lambdaUpdate ().eq ( FlowCopy::getProcInsId, procInsId ).remove ();
        }
        return ResponseEntity.ok ("删除成功，流程实例ID=" + procInsIds);
    }

}
