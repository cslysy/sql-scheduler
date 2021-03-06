package io.github.cslysy.sql.scheduler.schedule;

import io.github.cslysy.sql.scheduler.ApplicationConfig;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.scheduling.annotation.SchedulingConfigurer;
import org.springframework.scheduling.config.ScheduledTaskRegistrar;
import org.springframework.scheduling.support.CronTrigger;
import org.springframework.stereotype.Component;

/**
 * Scheduling configuration.
 * 
 * @author cslysy <jakub.sprega@gmail.com>
 */
@Component
public class SqlSchedulerConfig implements SchedulingConfigurer {
    
    private final ApplicationConfig config;
    private final ApplicationContext context;

    @Autowired
    public SqlSchedulerConfig(final ApplicationConfig config,
        final  ApplicationContext context) {
        this.config = config;
        this.context = context;
    }
    
    /**
     * Registers available SQL triggers.
     * 
     * @param taskRegistrar 
     */
    @Override
    public void configureTasks(ScheduledTaskRegistrar taskRegistrar) {
        config.getTriggers().forEach(trigger -> 
            taskRegistrar.addTriggerTask(
                context.getBean(SqlTask.class),
                new CronTrigger(trigger)
            )
        );
    }
}
