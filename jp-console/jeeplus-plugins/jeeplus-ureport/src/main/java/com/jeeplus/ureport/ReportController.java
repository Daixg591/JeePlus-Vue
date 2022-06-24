package com.jeeplus.ureport;

import com.jeeplus.common.utils.ResponseUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@Slf4j
@RestController
@RequestMapping("/reports")
public class ReportController {

    private final ReportService reportService;

    ReportController(ReportService reportService) {
        this.reportService = reportService;
    }

    @GetMapping("/list")
    public ResponseEntity getReports() {
        log.debug("ReportController.getReports");
        return ResponseUtil.newInstance ().add("reports", reportService.getReportFiles()).add("prefix", "file:").ok ();
    }

    @DeleteMapping("/delete")
    public ResponseEntity delete(@RequestParam("id") String id) {
        log.debug("ReportController.deleteById {}", id);
        reportService.deleteReport(id);
        return ResponseEntity.ok ("删除报表成功!");
    }
}
