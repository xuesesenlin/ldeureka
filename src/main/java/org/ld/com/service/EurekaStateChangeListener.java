package org.ld.com.service;

import com.netflix.appinfo.InstanceInfo;
import org.ld.com.util.EmailUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.cloud.netflix.eureka.server.event.*;
import org.springframework.context.event.EventListener;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

/**
 * @author ld
 * @name
 * @table
 * @remarks
 */
@Component
public class EurekaStateChangeListener {

    @Value("${spring.mail.username}")
    private String forEmail;

    @Autowired
    JavaMailSender mailSender;

    /**
     * 服务断开事件
     *
     * @param eurekaInstanceCanceledEvent
     */
    @EventListener
    public void listen(EurekaInstanceCanceledEvent eurekaInstanceCanceledEvent) {
        //服务断线事件
        String appName = eurekaInstanceCanceledEvent.getAppName();
        String serverId = eurekaInstanceCanceledEvent.getServerId();
        System.out.println(appName);
        System.out.println(serverId);
    }

    /**
     * 服务注册事件
     *
     * @param event
     */
    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        InstanceInfo instanceInfo = event.getInstanceInfo();
        System.out.println(instanceInfo);
    }

    /**
     * 服务更新事件
     *
     * @param event
     */
    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        event.getAppName();
        event.getServerId();
    }

    /**
     * 服务注册表可用事件
     *
     * @param event
     */
    @EventListener
    public void listen(EurekaRegistryAvailableEvent event) {

    }

    /**
     * 服务器启动事件
     *
     * @param event
     */
    @EventListener
    public void listen(EurekaServerStartedEvent event) {
        //Server启动
        EmailUtil emailUtil = new EmailUtil();
        emailUtil.email(mailSender, forEmail, forEmail, "服务注册中心开始启动");
    }
}
