/**
 * Copyright &copy; 2021-2026 <a href="http://www.jeeplus.org/">JeePlus</a> All rights reserved.
 */
package com.jeeplus.config;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.CommandLineRunner;
import org.springframework.core.env.Environment;
import org.springframework.stereotype.Component;

import java.net.InetAddress;

/**
 * Created by jeeplus on 2021/11/30.
 */
@Component
public class AppInit implements CommandLineRunner {
    @Value("${server.port}")
    private String port;

    @Value("${server.servlet.context-path}")
    private String path;

    @Override
    public void run(String... args) throws Exception {
        System.out.println(">>>>>>>>>>>>>>>jeeplus 启动成功<<<<<<<<<<<<<");
        String ip = InetAddress.getLocalHost().getHostAddress();
        System.out.println("Jeeplus Application running at:\n\t" +
                "- Local: http://localhost:" + port + path + "/\n\t" +
                "- Network: http://" + ip + ":" + port + path + "/\n\t" +
                "- swagger: http://" + ip + ":" + port + path + "/doc.html\n" +
                "----------------------------------------------------------");
    }


}

