package com.zavar.weblab3;

import com.zavar.weblab3.bean.PointBean;
import com.zavar.weblab3.bean.PointHitsPercent;

import javax.management.*;
import javax.servlet.ServletContextEvent;
import javax.servlet.ServletContextListener;
import javax.servlet.annotation.WebListener;
import java.lang.management.ManagementFactory;

@WebListener
public class MXBeanRegistrationListener implements ServletContextListener {
    @Override
    public void contextInitialized(ServletContextEvent sce) {
        try {
            ManagementFactory.getPlatformMBeanServer().registerMBean(new PointBean(), new ObjectName("com.zavar.weblab3.bean:type=PointBean,name=stats"));
            ManagementFactory.getPlatformMBeanServer().registerMBean(new PointHitsPercent(), new ObjectName("com.zavar.weblab3.bean:type=PointHitsPercent,name=per"));
        } catch (MalformedObjectNameException | NotCompliantMBeanException | InstanceAlreadyExistsException | MBeanRegistrationException e) {
            e.printStackTrace();
        }
    }

    @Override
    public void contextDestroyed(ServletContextEvent sce) {
        try {
            ManagementFactory.getPlatformMBeanServer().unregisterMBean(new ObjectName("com.zavar.weblab3.bean:type=PointBean,name=stats"));
            ManagementFactory.getPlatformMBeanServer().unregisterMBean(new ObjectName("com.zavar.weblab3.bean:type=PointHitsPercent,name=per"));
        } catch (MalformedObjectNameException | MBeanRegistrationException | InstanceNotFoundException e) {
            e.printStackTrace();
        }
    }
}
