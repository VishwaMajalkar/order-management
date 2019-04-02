package com.vish.example.order.config;

import org.apache.catalina.connector.Connector;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.web.embedded.tomcat.TomcatConnectorCustomizer;
import org.springframework.context.ApplicationListener;
import org.springframework.context.event.ContextClosedEvent;

import java.util.concurrent.Executor;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * This Class to configure shutdown hooks when
 * application is shut down gracefully
 * Created by Vishwanath on 18/03/2019.
 */
public class ForceShutdown implements TomcatConnectorCustomizer,
        ApplicationListener<ContextClosedEvent> {

    private static final Logger log = LoggerFactory.getLogger(ForceShutdown.class);

    private volatile Connector connector;

    @Override
    public void customize(Connector connector) {
        this.connector = connector;
    }

    @Override
    public void onApplicationEvent(ContextClosedEvent contextClosedEvent){
        log.info("Application Was Forcefully Shutdown!");
        this.connector.pause();
        Executor executor = this.connector.getProtocolHandler().getExecutor();
        if (executor instanceof ThreadPoolExecutor) {
            ThreadPoolExecutor threadPoolExecutor = (ThreadPoolExecutor) executor;
            threadPoolExecutor.shutdown();
        }
    }
}
