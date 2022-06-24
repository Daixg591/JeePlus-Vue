package com.jeeplus.ureport.cache;

import com.bstek.ureport.cache.ReportDefinitionCache;
import com.bstek.ureport.definition.ReportDefinition;
import org.springframework.stereotype.Component;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class ErasableMemoryCache implements ReportDefinitionCache {

    private Map<String, ReportDefinition> reportMap = new ConcurrentHashMap<String, ReportDefinition>();

    /**
     * 清除缓存
     */
    public void clearCache() {
        this.reportMap.clear();
    }

    @Override
    public ReportDefinition getReportDefinition(String file) {
        return reportMap.get(file);
    }

    @Override
    public void cacheReportDefinition(String file, ReportDefinition reportDefinition) {
        if (reportMap.containsKey(file)) {
            reportMap.remove(file);
        }
        reportMap.put(file, reportDefinition);
    }
}
