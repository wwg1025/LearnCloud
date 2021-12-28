package com.wen.springcloud.service.impl;

import com.wen.springcloud.dao.AccountMapper;
import com.wen.springcloud.service.AccountService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
public class AccountServiceImpl implements AccountService {

    @Autowired
    private AccountMapper accountMapper;

    /**
     * 扣减账户余额
     *
     * @param userId 用户id
     * @param money  金额
     */
    @Override
    public void decrease(Long userId, BigDecimal money) {
        log.info("------->account-service中扣减账户余额开始");
        try {
            TimeUnit.SECONDS.sleep(20);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        accountMapper.decrease(userId,money);
        log.info("------->account-service中扣减账户余额结束");
    }
}
