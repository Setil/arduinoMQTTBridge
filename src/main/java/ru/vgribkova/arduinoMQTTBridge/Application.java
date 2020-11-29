package ru.vgribkova.arduinoMQTTBridge;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.json.JSONArray;
import org.json.JSONException;
import org.json.JSONObject;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;


public class Application {
    private static final Logger logger = LogManager.getLogger();
    public static void main(String[] args) {
        final MQTTClient client = new MQTTClient();
        Arduino arduino = new Arduino(
                Properties.get().getArduinoPortName(),
                Properties.get().getArduinoBaudRate());

        boolean connected = arduino.openConnection();
        System.out.println("Соединение установлено: " + connected);
        arduino.serialRead("DONE",message ->{
            logger.debug(message);
            try{
                JSONObject messageObject = new JSONObject(message);
                JSONArray thermometers = messageObject.getJSONArray("thermometers");
                List<String> ids = new ArrayList<>();
                for (Object o : thermometers) {
                    JSONObject thermometer = (JSONObject) o;
                    String thermometerId = thermometer.getString("address");
                    ids.add(thermometerId);
                    float temperature = thermometer.getFloat("temperatureC");
                    client.sendMessageTo("/heat/temperature/" + thermometerId, Float.toString(temperature));
                }
                client.sendMessageTo("/heat/temperature/ids", String.join(" ", ids));
            }catch (JSONException e){
                logger.error("Message not parsed: " + message);
            }
            client.sendMessageTo("/heat/temperature/", message);
        });

    }
}
