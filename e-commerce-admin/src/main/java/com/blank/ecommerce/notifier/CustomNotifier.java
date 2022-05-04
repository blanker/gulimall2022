package com.blank.ecommerce.notifier;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractEventNotifier;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;
import reactor.core.publisher.Mono;

@Slf4j
@Component
public class CustomNotifier extends AbstractEventNotifier {
    public CustomNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> {
            if (event instanceof InstanceStatusChangedEvent) {
                log.info("instance status changed: [{}, {}, {}]",
                        instance.getRegistration().getName(),
                        event.getInstance(),
                        ((InstanceStatusChangedEvent) event).getStatusInfo().getStatus()
                );
            } else {
                log.info("instance event: [{}, {}, {}]",
                        instance.getRegistration().getName(),
                        event.getInstance(),
                        event.getType());
            }
        });
    }
}
