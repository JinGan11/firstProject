package com.ucar.combination.utils;

import com.ucar.combination.common.Result;
import com.ucar.combination.model.Account;
import com.ucar.combination.service.MailService;
import com.ucar.combination.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @version 1.0
 * @description: 定时发送邮件
 * @author: peng.zhang11@ucarinc.com
 * @create: 2019-08-12 11:09
 */
@Component
public class MailSchedulerTask {

    @Autowired
    private MailService mailService;

    @Autowired
    private UserService userService;

    /**
     * 表示每天12点发送一次邮件
     */
    @Scheduled(cron = "0 0 12 * * ?")
    private void proces(){
        Result result = userService.getAllAccountListByModifyTime();
        List<Account> accountList = (List<Account>) result.get("accountList");
        if (accountList != null) {
            for (int i = 0; i < accountList.size(); i++) {
                Account account = accountList.get(i);
                String content = account.getAccountName() + "您好！您的密码还有7天即将到期，请及时修改密码！";
                mailService.sendMail(account.getAccountSecretEmail(),"密码到期提醒",content);
            }
        }
    }
}
