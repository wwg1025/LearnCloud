package com.wen.springcloud.controller;

import com.alibaba.csp.sentinel.annotation.SentinelResource;
import com.alibaba.csp.sentinel.slots.block.BlockException;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

@RestController
@Slf4j
@RequestMapping("/flowLimit")
public class FlowLimitController {
    @GetMapping("/testA")
    public String testA() throws InterruptedException {
        return "------testA";
    }

    /**
     * 服务流控 流控模式QPS大于一定阈值触发限流-直接-快速失败
     * 流控模式QPS大于一定阈值触发限流-直接-warm up 预热模式 服务长期处于低水位，猛地拉高访问量容易将服务打死，缓慢的放入访问量，达到预热时长允许服务拉到最高
     * 流控模式QPS大于一定阈值触发限流-直接-排队等待 QPS10个访问请求过来，只能处理一个，服务放进来排队等待，超过超时时间服务请求不做处理
     * 流控模式并发线程数大于一定阈值触发限流-直接-快速失败 sentinel友好提示
     * .......
     * @return
     */
    @GetMapping("/testB")
    public String testB()
    {
        log.info(Thread.currentThread().getName()+"\t"+"...testB");
        return "------testB";
    }

    /**
     * 服务QPS>=5 0.2秒内处理完请求，高并发请求下服务熔断 RT（平均处理响应时间）慢调用
     * @return
     */
    @GetMapping("/testD")
    public String testD()
    {
        try {
            TimeUnit.SECONDS.sleep(1);
        } catch (Exception e) {
            e.printStackTrace();
        }
        log.info(Thread.currentThread().getName()+"\t"+"...testD");
        return "------testD";
    }

    /**
     * 服务异常比例服务熔断 QPS>=5 异常比例从0到1 表示0%至100%，例：0.2异常比例就表示满足QPS>=5 QPS10次并发请求出现异常2次触发熔断规则
     * 时间窗口期过后（服务正常？过了窗口期就关闭服务熔断？）则关闭服务熔断，服务正常放开访问
     * @return
     */
    @GetMapping("/testE")
    public String testE()
    {
        int age = 10 / 0;
        log.info(Thread.currentThread().getName()+"\t"+"...testE");
        return "------testE";
    }

    /**
     * 异常数是按照分钟统计的*
     * 服务异常数服务熔断 异常数>=5 例：分钟内异常数>=5请求出现异常触发熔断规则
     * 时间窗口期过后（服务正常？过了窗口期就关闭服务熔断？）则关闭服务熔断，服务正常放开访问
     * @return
     */
    @GetMapping("/testF")
    public String testF()
    {
        int age = 10 / 0;
        log.info(Thread.currentThread().getName()+"\t"+"...testF");
        return "------testF";
    }

    /**
     * 配置热点规则 QPS 参数索引超过设定阈值 例：/flowLimit/hotKey?p1=aaa QPS>=1 走下面的兜底服务友好提示
     *
     * 参数例外项*{
     *     指定参数类型对应的参数值限流阈值达到200就不违背规则，不会出现兜底 例：p1这个参数p1=5 限流阈值200
     * }
     * @param p1
     * @param p2
     * @return
     */
    @GetMapping("/hotKey")
    @SentinelResource(value = "hotKey", blockHandler = "del_hotKey")
    public String hotKey(@RequestParam(value = "p1", required = false) String p1, @RequestParam(value = "p2", required = false) String p2) {
        log.info("最新热点信息hotKey！！！！！！！！！！");
        return "----------hotKey!!!!!!!";
    }

    /**
     * 热点信息出现异常兜底服务
     * @param p1
     * @param p2
     * @param exception
     * @return
     */
    public String del_hotKey(String p1, String p2, BlockException exception) {
        return "最新热点信息hotKey访问失败啦~请稍后重试哦~~~~~~~";
    }


}
