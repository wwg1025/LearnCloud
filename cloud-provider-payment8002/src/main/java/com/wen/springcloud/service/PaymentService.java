package com.wen.springcloud.service;

import com.wen.springcloud.entity.Payment;
import org.apache.ibatis.annotations.Param;

/**
 * @author wwg
 * @descrip 支付业务处理类接口
 */
public interface PaymentService {
    public int create(Payment payment);

    public Payment getPaymentById(@Param("id") Long id);
}
