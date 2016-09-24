#include "dht.h"
dht DHT;

void setup() {
  Serial.begin(115200);
}

void send_msg(){
  Serial.print("温度:");
  Serial.print(DHT.temperature, 1);
  Serial.print("℃,湿度:");
  Serial.print(DHT.humidity, 1);
  Serial.println("%");
}

void serialEvent(){
  if(Serial.read() == 'a')send_msg();
}

long last = 0;
void loop() {
  DHT.read(12);
  if(millis() - last >= 1000){
    last = millis();
    send_msg();
  }
}

