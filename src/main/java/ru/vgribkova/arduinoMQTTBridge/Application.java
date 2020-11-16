package ru.vgribkova.arduinoMQTTBridge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

public class Application {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        final MQTTClient client = new MQTTClient();
        Arduino arduino = new Arduino("COM3", 9600);
        boolean connected = arduino.openConnection();
        System.out.println("Соединение установлено: " + connected);
        arduino.serialRead("addresses.",message ->{
            logger.debug(message);
            client.sendMessageTo("/heat/temperature/", message);
        });

    }
}
