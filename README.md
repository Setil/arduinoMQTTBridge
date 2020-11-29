# arduinoMQTTBridge
Small application for binding arduino without Ethernet to MQTT

Arduino script publishes JSON object with information from each thermometer:
- thermometer address
- temperature (C)

JSON is printed to COM port. At the end of the message special separator is necessary (word/character)

Java client is listening to the COM port. 
Message is processed when special separator met.
After parsing JSON java client send info to MQTT topics:
- temperature value for each thermometer is placed to topic with it's address
- all temperature ids a placed to topic with name "ids"      