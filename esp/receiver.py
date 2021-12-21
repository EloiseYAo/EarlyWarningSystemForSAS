
from machine import Pin 
from machine import ADC
from machine import PWM
from machine import Timer
import utime 
import json
import network
import urequests as requests

LED_buzzer  = Pin(13, Pin.OUT)
adc = ADC(0)
light = 0

disabled = False


alarm = False


def do_connect():
    wlan = network.WLAN(network.STA_IF)
    wlan.active(True)
    if not wlan.isconnected():
        print('connecting to network...')
        wlan.connect('Columbia University', '')
        while not wlan.isconnected():
            print("Not connected")
    print('network config:', wlan.ifconfig())

def is_dangerous():
    data={'command':"give me command"}
    url_json = 'http://3.145.205.174:5000/command'
    utime.sleep(1)
    response = requests.get(url_json)

    print(response.text)
    return response.text=='1'

do_connect()
senttw = False
while True:
    alarm = is_dangerous()
    if alarm:

        LED_buzzer.on()
        if not senttw:
            turl="https://api.thingspeak.com/apps/thingtweet/1/statuses/update?api_key=I2QOWLDUGF8JKN7F&status="
            text = "I need help !!!"
            text.replace(" ", "+")
            turl+=text
            r = requests.post(turl)
            senttw = True

        # B.freq(300)
        # B.duty(1023)
        # L.freq(500) 
        # print("d=",d)
        # L.duty(0)
        print("warning!")
        utime.sleep(10)
    # utime.sleep(1)
    # print("duty=",L.duty())
        
    else:
        LED_buzzer.off()
        print("normal")
    utime.sleep(2)
    
# print("duty2=",L.duty())



