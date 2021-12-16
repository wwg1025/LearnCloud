package com.wen.springcloud.service.impl;

import com.wen.springcloud.dao.PaymentDao;
import com.wen.springcloud.entity.Payment;
import com.wen.springcloud.service.PaymentService;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @author wwg
 * @descrip 支付业务处理类接口实现类
 */
@Service
public class PaymentServiceImpl implements PaymentService {

    @Resource
    private PaymentDao paymentDao;

    @Override
    public int create(Payment payment) {
        return paymentDao.create(payment);
    }

    @Override
    public Payment getPaymentById(Long id) {
        return paymentDao.getPaymentById(id);
    }
}
