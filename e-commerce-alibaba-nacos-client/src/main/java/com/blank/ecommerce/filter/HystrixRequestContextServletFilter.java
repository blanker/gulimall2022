package com.blank.ecommerce.filter;

import com.netflix.hystrix.strategy.HystrixPlugins;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategy;
import com.netflix.hystrix.strategy.concurrency.HystrixConcurrencyStrategyDefault;
import com.netflix.hystrix.strategy.concurrency.HystrixRequestContext;
import com.netflix.hystrix.strategy.eventnotifier.HystrixEventNotifier;
import com.netflix.hystrix.strategy.executionhook.HystrixCommandExecutionHook;
import com.netflix.hystrix.strategy.metrics.HystrixMetricsPublisher;
import com.netflix.hystrix.strategy.properties.HystrixPropertiesStrategy;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.servlet.*;
import javax.servlet.annotation.WebFilter;
import java.io.IOException;

@Slf4j
@Component
@WebFilter(
        filterName = "HystrixRequestContextServletFilter",
        urlPatterns = "/*",
        asyncSupported = true
)
public class HystrixRequestContextServletFilter implements Filter {


    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        final HystrixRequestContext context = HystrixRequestContext.initializeContext();
        try {
            hystrixConcurrencyStrategyConfig();
            filterChain.doFilter(servletRequest, servletResponse);
        } catch (Exception ex){
            context.shutdown();
        }
    }

    public void hystrixConcurrencyStrategyConfig(){
        try {
            final HystrixConcurrencyStrategy target =
                    HystrixConcurrencyStrategyDefault.getInstance();
            final HystrixConcurrencyStrategy strategy = HystrixPlugins.getInstance().getConcurrencyStrategy();

            if (strategy instanceof HystrixConcurrencyStrategyDefault) {
                return;
            }

            final HystrixCommandExecutionHook executionHook = HystrixPlugins.getInstance().getCommandExecutionHook();
            final HystrixEventNotifier eventNotifier = HystrixPlugins.getInstance().getEventNotifier();
            final HystrixMetricsPublisher metricsPublisher = HystrixPlugins.getInstance().getMetricsPublisher();
            final HystrixPropertiesStrategy propertiesStrategy = HystrixPlugins.getInstance().getPropertiesStrategy();

            HystrixPlugins.reset();

            HystrixPlugins.getInstance().registerConcurrencyStrategy(target);
            HystrixPlugins.getInstance().registerCommandExecutionHook(executionHook);
            HystrixPlugins.getInstance().registerEventNotifier(eventNotifier);
            HystrixPlugins.getInstance().registerMetricsPublisher(metricsPublisher);
            HystrixPlugins.getInstance().registerPropertiesStrategy(propertiesStrategy);
            log.info("config hystrix concurrency strategy success");

        } catch (Exception exception) {
            log.error("error to config hystrix concurrency strategy: [{}]", exception.getMessage(), exception);
        }
    }
}
