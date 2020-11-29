package ru.vgribkova.arduinoMQTTBridge;

import ru.qatools.properties.Property;
import ru.qatools.properties.PropertyLoader;
import ru.qatools.properties.Resource;

@Resource.Classpath("application.properties")
public class Properties {
    @Property("mqttHost")
    private String mqttHost;
    @Property("mqttPort")
    private int mqttPort;
    @Property("arduinoPortName")
    private String arduinoPortName;
    @Property("arduinoBaudRate")
    private int arduinoBaudRate;

    private static Properties instance;


    public static Properties get() {
        if (instance == null) {
            instance = new Properties();
            PropertyLoader.newInstance().populate(instance);
        }
        return instance;
    }

    public String getMqttHost() {
        return mqttHost;
    }

    public Properties setMqttHost(String mqttHost) {
        this.mqttHost = mqttHost;
        return this;
    }

    public int getMqttPort() {
        return mqttPort;
    }

    public Properties setMqttPort(int mqttPort) {
        this.mqttPort = mqttPort;
        return this;
    }

    public String getArduinoPortName() {
        return arduinoPortName;
    }

    public void setArduinoPortName(String arduinoPortName) {
        this.arduinoPortName = arduinoPortName;
    }

    public int getArduinoBaudRate() {
        return arduinoBaudRate;
    }

    public void setArduinoBaudRate(int arduinoBaudRate) {
        this.arduinoBaudRate = arduinoBaudRate;
    }
}
