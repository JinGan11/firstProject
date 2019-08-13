package com.ucar.combination.service.impl;

import com.ucar.combination.common.Result;
import com.ucar.combination.service.MailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Service;

/**
 * @version 1.0
 * @description: 邮件服务类
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-08-12 10:33
 */
@Service
public class MailServiceImpl implements MailService {

    @Autowired
    private JavaMailSender mailSender;

    @Value("${mail.fromMail.addr}")
    private String form;

    /**
     * description: 发送邮件
     * @author peng.zhang11@ucarinc.com
     * @date   2019/8/12 12:41
     * @params to 邮件接收者
     * @param: subject 邮件主题
     * @param: content 邮件内容
     * @return
     */
    @Override
    public Result sendMail(String to, String subject, String content) {
        Result result = new Result();
        SimpleMailMessage mailMessage=new SimpleMailMessage();
        mailMessage.setFrom(form);
        mailMessage.setTo(to);

        mailMessage.setSubject(subject);
        mailMessage.setText(content);
        try {
            mailSender.send(mailMessage);
            result.put("code",200)
            .put("msg","发送邮件成功");
        }catch (Exception e){
            result.put("code",201).put("msg","系统错误");
        }
        return result;
    }
}
