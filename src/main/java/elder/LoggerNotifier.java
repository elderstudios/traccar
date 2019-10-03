package elder;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.traccar.Context;
import org.traccar.database.ConnectionManager;
import org.traccar.model.Device;
import org.traccar.model.Event;
import org.traccar.model.Position;
import org.traccar.notification.MessageException;
import org.traccar.notificators.Notificator;

public class LoggerNotifier extends Notificator {

    private Logger LOGGER = LoggerFactory.getLogger(LoggerNotifier.class);

    public LoggerNotifier() {
        LOGGER.info("Registering Update listener");
        Context.getConnectionManager().addListener(
                1,
                new NotificatorListener(
                        Context.getObjectMapper()
                )
        );
    }

    @Override
    public void sendSync(long userId, Event event, Position position) throws MessageException, InterruptedException {
        // nothing to be done here.
    }

    private class NotificatorListener implements ConnectionManager.UpdateListener {

        private ObjectMapper objectMapper;

        private NotificatorListener(ObjectMapper objectMapper) {
            this.objectMapper = objectMapper;
        }

        @Override
        public void onUpdateDevice(Device device) {
            LOGGER.info("Device update {}", asString(device));
        }

        @Override
        public void onUpdatePosition(Position position) {
            LOGGER.info("Position update {}", asString(position));
        }

        @Override
        public void onUpdateEvent(Event event) {
            LOGGER.info("Event update {}", asString(event));
        }

        private String asString(Object object) {
            try {
                return objectMapper.writeValueAsString(object);
            } catch (JsonProcessingException e) {
                throw new IllegalStateException("Cannot create json", e);
            }
        }
    }
}
