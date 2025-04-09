package com.wims.iot.common.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.scheduling.concurrent.ThreadPoolTaskExecutor;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

@Configuration
@EnableScheduling
public class ScheduleConfig {

    @Bean(value = "taskExecutor")
    public Executor taskExecutor() {
        ThreadPoolTaskExecutor executor = new ThreadPoolTaskExecutor();
        //核心线程数量
        executor.setCorePoolSize(5);
        //任务队列
        executor.setQueueCapacity(10);
        //最大线程数量
        executor.setMaxPoolSize(20);
        //线程空闲时间
        executor.setKeepAliveSeconds(60);
        //线程拒绝策略
        //CallerRunsPolicy：只要线程池没有关闭，任务不会被拒绝而是会通过提交任务的调用者线程执行
        executor.setRejectedExecutionHandler(new ThreadPoolExecutor.CallerRunsPolicy());
        //执行初始化
        executor.initialize();
        return executor;
    }

}
