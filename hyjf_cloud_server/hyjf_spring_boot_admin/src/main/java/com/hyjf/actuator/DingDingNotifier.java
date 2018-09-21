package com.hyjf.actuator;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	private Expression text;
	private final SpelExpressionParser parser = new SpelExpressionParser();

	public DingDingNotifier(InstanceRepository repository) {
		super(repository);
		String text = "#{registration.name} \nstatus changed to #{statusInfo.status} \n#{registration.healthUrl}";
		this.text = this.parser.parseExpression(text, ParserContext.TEMPLATE_EXPRESSION);
	}

	@Override
	protected Mono<Void> doNotify(InstanceEvent event, Instance instance) {
		return Mono.fromRunnable(() -> {

			if (event instanceof InstanceStatusChangedEvent) {
				InstanceStatusChangedEvent statusChange = (InstanceStatusChangedEvent) event;
				String from = getLastStatus(event.getInstance());
				String to = statusChange.getStatusInfo().getStatus();
				logger.info("instance status change from {} to {}", from, to);
			} else {
				logger.error("event is not want target class, now it is " + event.getClass().getName());
				return;
			}

			String text = this.text.getValue(instance, String.class);
			DingDingMessageUtil.sendTextMessage(text);
		});
	}
}
