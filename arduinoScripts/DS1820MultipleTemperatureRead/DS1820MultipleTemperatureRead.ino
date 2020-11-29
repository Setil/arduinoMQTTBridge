#include <Arduino_JSON.h>
#include <OneWire.h>
#include <DallasTemperature.h>

#define ONE_WIRE_BUS A5

OneWire oneWire(ONE_WIRE_BUS);
DallasTemperature sensors(&oneWire);
void setup(void)
{  
  Serial.begin(9600); 
  sensors.begin();      
}

void loop(void)
{
  JSONVar result = checkThermometers();  
  Serial.println(JSON.stringify(result));  
  Serial.println("DONE");
  delay(1000);
}

JSONVar checkThermometers(){
  int totalThermometers = sensors.getDeviceCount();
  oneWire.reset_search();
  DeviceAddress currentThermometer;  
  JSONVar result;
  result["totalThermometers"] = totalThermometers;
  JSONVar thermometersJson;
  for (int i = 0;  i < totalThermometers;  i++) {
    JSONVar thermometer;
    sensors.getAddress(currentThermometer,i);
    thermometer["address"] = convertAddressToString(currentThermometer);
    thermometer["temperatureC"] = sensors.getTempC(currentThermometer);
    thermometersJson[i]=thermometer;
  }
  result["thermometers"] = thermometersJson;
  return result;
}

/* got from https://handyman.dulare.com/esp8266-ds18b20-web-server/ */
String convertAddressToString(DeviceAddress deviceAddress) {
  String addrToReturn = "";
  for (uint8_t i = 0; i < 8; i++){
    if (deviceAddress[i] < 16) addrToReturn += "0";
    addrToReturn += String(deviceAddress[i], HEX);
  }
  return addrToReturn;
}
