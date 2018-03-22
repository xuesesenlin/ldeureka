package org.ld.com.service;

import com.netflix.appinfo.InstanceInfo;
import org.ld.com.util.EmailUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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

    private final static Logger logger = LoggerFactory
            .getLogger(EurekaStateChangeListener.class);

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
        String s = "<span style=\"color:red;\">服务断开[{名称," + eurekaInstanceCanceledEvent.getAppName()+"]</span>";
        EmailUtil emailUtil = new EmailUtil();
        emailUtil.email(mailSender, forEmail, forEmail, s);
        logger.info(s);
    }

    /**
     * 服务注册事件
     *
     * @param event
     */
    @EventListener
    public void listen(EurekaInstanceRegisteredEvent event) {
        InstanceInfo instanceInfo = event.getInstanceInfo();
        String s = "新服务注册成功[{名称," + instanceInfo.getAppName() + "},{ip地址," + instanceInfo.getIPAddr() + "},{端口,}" + instanceInfo.getPort() + "]";
        EmailUtil emailUtil = new EmailUtil();
        emailUtil.email(mailSender, forEmail, forEmail, s);
        logger.info(s);
    }

    /**
     * 服务更新事件
     *
     * @param event
     */
    @EventListener
    public void listen(EurekaInstanceRenewedEvent event) {
        String s = "服务更新成功[{名称," + event.getInstanceInfo().getAppName() + "},{ip地址," + event.getInstanceInfo().getIPAddr() + "},{端口,}" + event.getInstanceInfo().getPort() + "]";
        EmailUtil emailUtil = new EmailUtil();
//        emailUtil.email(mailSender, forEmail, forEmail, s);
        logger.info(s);
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
        logger.info("服务注册中心启动成功");
    }
}
