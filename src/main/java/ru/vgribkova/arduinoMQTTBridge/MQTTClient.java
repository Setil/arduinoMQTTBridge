package ru.vgribkova.arduinoMQTTBridge;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.eclipse.paho.client.mqttv3.*;

import java.util.UUID;

public class MQTTClient {
    private IMqttClient client;
    private String publisherId;
    private Logger logger = LogManager.getLogger();
    private MqttConnectOptions options;

    public MQTTClient(){
        publisherId = UUID.randomUUID().toString();
        initialize();
    }

    private void initialize(){
        try {
            client = new MqttClient(
                    String.format("%s:%d", Properties.get().getMqttHost(), Properties.get().getMqttPort()),
                    publisherId);
            options = new MqttConnectOptions();
            options.setAutomaticReconnect(true);
            options.setCleanSession(true);
            options.setConnectionTimeout(10);
        } catch (MqttException e) {
            logger.error(e);
        }
    }

    private void connect(){
        if (!client.isConnected()){
            try {
                client.connect(options);
            } catch (MqttException e) {
                logger.error(e);
            }
        }
    }

    public void sendMessageTo(String topic, String message){
        connect();
        MqttMessage msg = new MqttMessage(message.getBytes());
        msg.setQos(0);
        msg.setRetained(true);
        try {
            client.publish(topic,msg);
        } catch (MqttException e) {
            logger.fatal(e);
        }
    }
}
