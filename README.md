# IOIO Rover
Android Rover with IOIO board and two l293d motor driver ICs (2x for 4WD)


* * *
+ [See this IOIO-Rover in acton Youtube](#youtube)
+ [Items Needed](#itemsNeeded)
+ [Hardware Instructions](#instructionshard)
+ [Android Robot+ 2.1 App Instructions](#instructionsapp)
+ [Credit / source](#credit)

* * *

##<a name="youtube"></a>See this Android 4WD Robot in acton on Youtube

<p><img src="https://oxinon.com/wp-content/uploads/2018/11/ioio-Rover.png" alt="Android-ioio-RC-4WD-Robot" width="600"></a></p>

In this video I show you the drive with chains and 4 wheel. I was so outdoors, indoors and a panoramic view this project https://www.youtube.com/watch?v=rT1MDyTA88E.


##<a name="itemsNeeded"></a>Items needed

+ IOIO + BT dongle
+ 2x Android Smartphone or Tablet
+ 2x L293D Driver board (or one for 2WD)
+ 4x 6Volt Motors
+ Battery 6V (I use 7,2V LiPo battery, most 6V motors can handle this)
+ Robot chassis  (my parts from http://www.makeblock.cc/)

##<a name="instructionshard"></a>Hardware Instructions

Connect pins of your IOIO Board to the Part swith the following assignment, here is the schematic of the parts Design with Fritzing (http://fritzing.org)


<p><a href="https://oxinon.com/wp-content/uploads/2018/11/IOIO-4WD-Robot-shematic-v1.2.png"><img src="https://oxinon.com/wp-content/uploads/2018/11/IOIO-4WD-Robot-shematic-v1.2.png" alt="ioio l293D schematic" width="800"></a></p>

Pinout from IOIO to L293d

+ ioio 1 to IN2 
+ ioio 2 to IN1
+ ioio 3 to EN1/2
+ ioio 8 to Laser Pointer
+ ioio 10 to Digital Servo in
+ ioio 13 to EN3/4
+ ioio 16 to IN4
+ ioio 17 to IN3


This is my IOIO and 2x driver L293D boards built in a case and mounting on the chassis 
<p><a href="https://oxinon.com/wp-content/uploads/2018/11/IMG_1278-1024x768.jpg"><img src="https://oxinon.com/wp-content/uploads/2018/11/IMG_1278-1024x768.jpg" alt="ioio l293D schematic" width="800"></a></p>


##<a name="instructionsapp"></a>Android Robot+ 2.1 App Instructions

##<a name="credit"></a>Credit / Source
Good inspiration from Inex, this is the app which was rebuilt for my Robot.
App and Source Code on PlayStore link: https://play.google.com/store/apps/details?id=app.akexorcist.ioiocamerarobot&hl=en

github:  https://github.com/akexorcist/IOIO-CameraRobot/

Licensed under the Apache License:
http://www.apache.org/licenses/LICENSE-2.0


