package com.wen.springcloud.service;

import com.wen.springcloud.common.CommonResult;
import com.wen.springcloud.entity.Payment;
import org.springframework.stereotype.Component;

/**
 * @descrip 服务出现异常熔断降级兜底服务
 */
@Component
public class FallBackService implements ConsumerService {
    @Override
    public CommonResult<Payment> paymentSql(Long id) {

        return new CommonResult<>(500, "支付服务开小差啦,请稍后再试");
    }
}
