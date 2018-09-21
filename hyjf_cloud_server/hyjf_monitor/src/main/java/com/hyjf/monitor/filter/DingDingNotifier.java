package com.hyjf.monitor.filter;

import com.hyjf.monitor.util.DingDingMessageUtil;
import org.jolokia.util.DateUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.expression.Expression;
import org.springframework.expression.ParserContext;
import org.springframework.expression.spel.standard.SpelExpressionParser;

import de.codecentric.boot.admin.server.domain.entities.Instance;
import de.codecentric.boot.admin.server.domain.entities.InstanceRepository;
import de.codecentric.boot.admin.server.domain.events.InstanceEvent;
import de.codecentric.boot.admin.server.domain.events.InstanceStatusChangedEvent;
import de.codecentric.boot.admin.server.notify.AbstractStatusChangeNotifier;
import reactor.core.publisher.Mono;

/**
 * @author xiasq
 * @version DingDingNotifier , v0.1 2018/8/23 17:38 自定义钉钉发消息
 */
public class DingDingNotifier extends AbstractStatusChangeNotifier {

    private Logger logger = LoggerFactory.getLogger(DingDingNotifier.class);
    @Value("${spring.boot.admin.notify.dingding.access_token}")
    private String accessToken;
    @Value("${spring.boot.admin.notify.dingding.switch_on}")
    private boolean switchOn;

    public DingDingNotifier(InstanceRepository repository) {
        super(repository);
    }

    @Override
    protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
        return Mono.fromRunnable(() -> {

            if (event instanceof InstanceStatusChangedEvent) {
                InstanceStatusChangedEvent statusChange = (InstanceStatusChangedEvent) event;
                String from = getLastStatus(event.getInstance());
                String to = statusChange.getStatusInfo().getStatus();
                logger.info("instance status change from {} to {}", from, to);

                String text = String.format("应用:[%s]，服务ID:[%s]，状态从:[%s]改变为:[%s]，时间：[%s]"
                        , instance.getRegistration().getName()
                        , instance.getId()
                        , from
                        , to
                        , event.getTimestamp());

                if (switchOn) {
                    DingDingMessageUtil.sendTextMessage(text, accessToken);
                }
            } else {
                logger.error("event is not want target class, now it is {}, type is {}", event.getClass().getName(), event.getType());
            }

        });
    }
}
