package org.ld.com.util;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;

import javax.mail.internet.MimeMessage;

public class EmailUtil {

    private final static Logger logger = LoggerFactory
            .getLogger(EmailUtil.class);

    /**
     * @param formUser
     * @param toUser
     * @param str
     * @return boolean
     */
    public boolean email(JavaMailSender mailSender, String formUser, String toUser, String str) {
        logger.info("开始发送邮件[{参数},{formUser," + formUser + "},{toUser," + toUser + "},{str," + str + "}]");
        try {
            final MimeMessage mimeMessage = mailSender.createMimeMessage();
            final MimeMessageHelper message = new MimeMessageHelper(
                    mimeMessage);
            message.setFrom(formUser);
            message.setTo(toUser);
            // 邮件主题
            message.setSubject("服务监控");
            // 邮件内容
            message.setText(new String(str.getBytes("UTF-8"), "UTF-8"), true);
            mailSender.send(mimeMessage);
            logger.info("邮件发送成功[{参数},{formUser," + formUser + "},{toUser," + toUser + "},{str," + str + "}]");
            return true;
        } catch (Exception ex) {
            logger.info("邮件发送失败[{参数},{formUser," + formUser + "},{toUser," + toUser + "},{str," + str + "}]", ex);
            return false;
        }
    }
}
