package com.jeeplus.ureport.cache;

import com.bstek.ureport.cache.ReportDefinitionCache;
import com.bstek.ureport.definition.ReportDefinition;

//@Component
public class NullCache implements ReportDefinitionCache {
    @Override
    public ReportDefinition getReportDefinition(String file) {
        System.out.println("NullCache.getReportDefinition");
        System.out.println("file = " + file);
        return null;
    }

    @Override
    public void cacheReportDefinition(String file, ReportDefinition reportDefinition) {
        System.out.println("NullCache.cacheReportDefinition");
    }
}
