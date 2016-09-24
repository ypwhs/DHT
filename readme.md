# DHT

远程温湿度数据采集系统，单片机课程设计。

## 硬件设计

* Arduino Nano
* DHT22
* USR-WIFI232-A

## 软件设计

### Arduino

主要代码：[dht.ino](DHT/dht.ino)

在线IED：[dht.ino](https://create.arduino.cc/editor/ypwhs/8e64cb82-5bce-4ba7-8343-1ce6c6b2745d/preview)

### Android

工程：[DHTClient](DHTClient)

主要代码：[MainActivity.java](DHTClient/app/src/main/java/com/yangpeiwen/dhtclient/MainActivity.java)

在线安装：[dht.apk](https://github.com/ypwhs/resources/raw/master/dht.apk)

## 实物展示

![实物](https://raw.githubusercontent.com/ypwhs/resources/master/IMG_0857.jpg)

## APP截图

### 正常

<img src="https://raw.githubusercontent.com/ypwhs/resources/master/Snip20160924_5.png" width="50%" >

### 吹气后

<img src="https://raw.githubusercontent.com/ypwhs/resources/master/Snip20160924_6.png" width="50%" >

## 参考资料

### [DHT11-chinese.pdf](DHT11-chinese.pdf)

### [Digital+humidity+and+temperature+sensor+AM2302.pdf](Digital+humidity+and+temperature+sensor+AM2302.pdf)

### [Arduino DHTLib](http://playground.arduino.cc/Main/DHTLib)

### [Android开发简介](https://developer.android.com/guide/index.html)

### [Socket](https://developer.android.com/reference/java/net/Socket.html)
