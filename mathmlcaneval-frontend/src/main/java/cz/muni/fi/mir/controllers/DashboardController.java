package cz.muni.fi.mir.controllers;


import java.lang.management.ManagementFactory;
import java.lang.management.MemoryUsage;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;
import java.lang.management.ThreadMXBean;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.annotation.Secured;
import org.springframework.stereotype.Controller;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.ModelAndView;

import cz.muni.fi.mir.services.TaskService;

@Controller
@RequestMapping(value = "/dashboard")
public class DashboardController
{
    @Autowired
    TaskService taskService;

    @RequestMapping(value = "/")
    @Secured("ROLE_USER")
    public ModelAndView list()
    {
        ModelMap mm = new ModelMap();

        MemoryUsage memoryUsage = ManagementFactory.getMemoryMXBean().getHeapMemoryUsage();
        OperatingSystemMXBean operatingSystem = ManagementFactory.getOperatingSystemMXBean();
        RuntimeMXBean runtime = ManagementFactory.getRuntimeMXBean();
        ThreadMXBean threadManager = ManagementFactory.getThreadMXBean();

        mm.addAttribute("freeHeap", memoryUsage.getUsed() / 1024.0 / 1024.0);
        mm.addAttribute("maxHeap", memoryUsage.getMax() / 1024.0 / 1024.0);
        mm.addAttribute("cpuAverage", operatingSystem.getSystemLoadAverage());

        long seconds = runtime.getUptime() / 1000;
        long hours = seconds / 3600;
        long minutes = (seconds % 3600) / 60;
        seconds = seconds % 60;
        mm.addAttribute("uptime_h", hours);
        mm.addAttribute("uptime_m", minutes);
        mm.addAttribute("uptime_s", seconds);

        mm.addAttribute("threadCount", threadManager.getThreadCount() - threadManager.getDaemonThreadCount());

        mm.addAttribute("taskList", taskService.getTasks());

        return new ModelAndView("dashboard",mm);
    }
}
