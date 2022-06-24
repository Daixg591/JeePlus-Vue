package com.jeeplus.ureport;

import com.bstek.ureport.provider.report.ReportFile;
import com.bstek.ureport.provider.report.file.FileReportProvider;
import com.jeeplus.ureport.cache.ErasableMemoryCache;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
public class ReportService {

    private final ErasableMemoryCache erasableMemoryCache;

    private final FileReportProvider fileReportProvider;

    ReportService(ErasableMemoryCache erasableMemoryCache, FileReportProvider fileReportProvider) {
        this.erasableMemoryCache = erasableMemoryCache;
        this.fileReportProvider = fileReportProvider;
    }


    List<ReportFile> getReportFiles() {
        log.debug("ReportService.getReportFiles");
        return fileReportProvider.getReportFiles();
    }

    void deleteReport(String file) {
        log.debug("ReportService.deleteReport, file: {}", file);
        if (file.startsWith(fileReportProvider.getPrefix())) {
            fileReportProvider.deleteReport(file);
        }else {
            log.error("ReportService.deleteReport, error when deleteById: {}", file);
        }
        // fix: deleteById report and cache.
        if (erasableMemoryCache != null) {
            erasableMemoryCache.clearCache();
        }
    }
}
