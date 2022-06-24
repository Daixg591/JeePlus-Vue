package com.jeeplus.ureport.cache;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;

@Controller
public class CacheController {

    private final ErasableMemoryCache erasableMemoryCache;

    public CacheController(ErasableMemoryCache erasableMemoryCache) {
        this.erasableMemoryCache = erasableMemoryCache;
    }

    /**
     * 清除缓存的入口
     * @return 返回首页
     */
    @PostMapping("/clearCache")
    public String clearCache() {
        erasableMemoryCache.clearCache();
        return "redirect:/reports/index";
    }
}
