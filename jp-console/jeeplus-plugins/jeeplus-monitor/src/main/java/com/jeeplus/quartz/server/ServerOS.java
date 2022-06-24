package com.jeeplus.quartz.server;

import com.google.common.collect.Lists;
import com.jeeplus.quartz.utils.MathUtils;
import com.jeeplus.quartz.utils.IpUtils;
import oshi.SystemInfo;
import oshi.hardware.CentralProcessor;
import oshi.hardware.GlobalMemory;
import oshi.hardware.HardwareAbstractionLayer;
import oshi.software.os.FileSystem;
import oshi.software.os.OSFileStore;
import oshi.software.os.OperatingSystem;
import oshi.util.Util;

import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Properties;

/**
 * @author : liugf
 * @date : 2021/9/27
 **/
public class ServerOS {
    public static Map getInfo(){
        Map map = new HashMap();
        SystemInfo si = new SystemInfo();
        HardwareAbstractionLayer hal = si.getHardware();
        CentralProcessor processor = hal.getProcessor();  //获取cpu信息
        map.put("cpu", setCpuInfo(processor));
        GlobalMemory memory = hal.getMemory();  //获取内存信息
        map.put("mem", setMemInfo(memory));
        map.put("sys", setSysInfo()); //服务器信息
        map.put("jvm", setJvmInfo()); //jvm信息
        OperatingSystem op = si.getOperatingSystem();
        map.put("file",setSysFiles(op)); //磁盘信息
        map.put("ip", IpUtils.getHostIp());
        map.put("hostname", IpUtils.getHostName());
        return map;
    }

    /**
     * cpu信息
     * @param processor
     */
    private static Map setCpuInfo(CentralProcessor processor) {   // CPU信息
        long[] prevTicks = processor.getSystemCpuLoadTicks();
        Util.sleep(1000);
        long[] ticks = processor.getSystemCpuLoadTicks();
        double nice = ticks[CentralProcessor.TickType.NICE.getIndex()] - prevTicks[CentralProcessor.TickType.NICE.getIndex()];
        double irq = ticks[CentralProcessor.TickType.IRQ.getIndex()] - prevTicks[CentralProcessor.TickType.IRQ.getIndex()];
        double softirq = ticks[CentralProcessor.TickType.SOFTIRQ.getIndex()] - prevTicks[CentralProcessor.TickType.SOFTIRQ.getIndex()];
        double steal = ticks[CentralProcessor.TickType.STEAL.getIndex()] - prevTicks[CentralProcessor.TickType.STEAL.getIndex()];
        /**
         * CPU系统使用率
         */
        double sys = ticks[CentralProcessor.TickType.SYSTEM.getIndex()] - prevTicks[CentralProcessor.TickType.SYSTEM.getIndex()];
        /**
         * CPU用户使用率
         */
        double used = ticks[CentralProcessor.TickType.USER.getIndex()] - prevTicks[CentralProcessor.TickType.USER.getIndex()];
        /**
         * CPU当前等待率
         */
        double iowait = ticks[CentralProcessor.TickType.IOWAIT.getIndex()] - prevTicks[CentralProcessor.TickType.IOWAIT.getIndex()];
        /**
         * CPU当前空闲率
         */
        double free = ticks[CentralProcessor.TickType.IDLE.getIndex()] - prevTicks[CentralProcessor.TickType.IDLE.getIndex()];
        /**
         * CPU总的使用率
         */
        double total = used + nice + sys + free + iowait + irq + softirq + steal;

        /**
         * 核心数
         */
        int cpuNum = processor.getLogicalProcessorCount();

        /**
         * CPU当前等待率
         */

        Map cpu = new HashMap<String,String>();
        cpu.put("cpucard", processor);//cpu信息
        cpu.put("num", processor.getLogicalProcessorCount());//cpu核心数
        cpu.put("used", MathUtils.round(MathUtils.mul((sys+used) / total, 100), 2));//cpu 用户使用率;
        cpu.put("free", MathUtils.round(MathUtils.mul(free / total, 100), 2));//cpu 空闲率
        return cpu;
    }

    /**
     * 内存信息
     */
    private static Map setMemInfo(GlobalMemory memory) {
        /**
         * 内存总量
         */
        double total = +memory.getTotal();

        /**
         * 已用内存
         */
        double used = memory.getTotal() - memory.getAvailable();

        /**
         * 剩余内存
         */
        double free = memory.getAvailable();
        Map mem = new HashMap();
        mem.put("total", div(total, (1024 * 1024 * 1024), 2));// 内存总大小
        mem.put("usedMem", div(used, (1024 * 1024 * 1024), 2));// 已使用内存大小
        mem.put("free", MathUtils.round(MathUtils.mul(free / total, 100), 2));// 剩余内存大小
        mem.put("used", MathUtils.round(MathUtils.mul(used / total, 100), 2));
        return mem;
    }

    /**
     * 服务器信息
     */
    private static Properties setSysInfo() {
        Properties props = System.getProperties();
        return props;
    }


    /**
     * Java虚拟机
     */
    private static  Map setJvmInfo()  {
        Properties props = System.getProperties();
        Map jvm = new HashMap();
        jvm.put("total", div(Runtime.getRuntime().totalMemory(),1024*1024,2));
        jvm.put("maxTotal",div(Runtime.getRuntime().maxMemory(),1024*1024,2));
        jvm.put("usedMem", div((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory()),1024*1024,2));
        jvm.put("used", MathUtils.round(MathUtils.mul((Runtime.getRuntime().totalMemory() - Runtime.getRuntime().freeMemory())*1.0/ Runtime.getRuntime().totalMemory(), 100), 2));
        return jvm;
    }

    /**
     * 设置磁盘信息
     */
    private static List setSysFiles(OperatingSystem os) {
        FileSystem fileSystem = os.getFileSystem();
        List<OSFileStore> fsArray = fileSystem.getFileStores();
        List list = Lists.newArrayList();
        for (OSFileStore fs : fsArray) {
            long free = fs.getUsableSpace();
            long total = fs.getTotalSpace();
            long used = total - free;
            Map map = new HashMap();
            map.put("path", fs.getMount());
            map.put("type", fs.getType());
            map.put("name", fs.getName());
            map.put("total", convertFileSize(total));
            map.put("free", convertFileSize(free));
            map.put("used", convertFileSize(used));
            map.put("rate", div(used, total, 4)*100);
            list.add(map);
        }
        return list;
    }

    /**
     * 字节转换
     *
     * @param size 字节大小
     * @return 转换后值
     */
    public  static String convertFileSize(long size) {
        long kb = 1024;
        long mb = kb * 1024;
        long gb = mb * 1024;
        if (size >= gb) {
            return String.format("%.1f GB", (float) size / gb);
        } else if (size >= mb) {
            float f = (float) size / mb;
            return String.format(f > 100 ? "%.0f MB" : "%.1f MB", f);
        } else if (size >= kb) {
            float f = (float) size / kb;
            return String.format(f > 100 ? "%.0f KB" : "%.1f KB", f);
        } else {
            return String.format("%d B", size);
        }
    }

    /**
     * 提供（相对）精确的除法运算。当发生除不尽的情况时，由scale参数指
     * 定精度，以后的数字四舍五入。
     * @param v1 被除数
     * @param v2 除数
     * @param scale 表示表示需要精确到小数点以后几位。
     * @return 两个参数的商
     */
    public static double div(double v1, double v2, int scale)
    {
       return ((double)((int)( v1/v2 * Math.pow(10 , scale))))/Math.pow(10 , scale) ;

    }

    public static void main(String[] args){
        System.out.println(div(19, 7, 4));
    }
}
