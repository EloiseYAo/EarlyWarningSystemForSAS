import max30102
import hrcalc
import gc
from machine import Pin, I2C
import utime
import json
import network
import urequests as requests


#max30102模块初始化
m = max30102.MAX30102()
 


i2c = I2C(scl=Pin(5), sda=Pin(4), freq=100000)


def do_connect():
    wlan = network.WLAN(network.STA_IF)
    wlan.active(True)
    if not wlan.isconnected():
        print('connecting to network...')
        wlan.connect('Columbia University', '')
        while not wlan.isconnected():
            print("Not connected")
    print('network config:', wlan.ifconfig())


do_connect()


data = dict()
# {'label':[(x,y,index)*20]*sample_size}
label = 'data'
data[label] = {"ir" : 0, "red" : 0}


while True:

    #采样250条数据，大约10秒钟
    # try:
    red, ir = m.read_sequential(700)

# print(len(red),len(ir))
# print(red,ir)
#进行分析
    # ir_avg = []
    # red_avg = []
    # for i in range(25):
    #     d = hrcalc.calc_hr_and_spo2(ir[25*i:25*i+100], red[25*i:25*i+100])
    #     print(d)
    #     if d[1]:
    #         ir_avg.append(d[0])
    #     if d[3]:
    #         red_avg.append(d[2])
    # ir_D = (sum(ir_avg) - max(ir_avg) - min(ir_avg)) // len(ir_avg)
    # red_D = (sum(red_avg) - max(red_avg) - min(red_avg)) // len(red_avg)
    # print('ir:',ir_D)
    # print('red:',red_D)
    # data[label]["ir"] = ir_D
    # data[label]["red"] = red_D
    # print(gc.mem_alloc())
    # gc.collect()
    # print(gc.mem_free())
# except Exception as e:
#     print(e)

    ir_send={}
    ir_send['ir']=ir[0:300]
    ir_send['red']=red[0:300]
    url_json = 'http://3.145.205.174:5000/predict'
    utime.sleep(1)
    res_json = requests.post(url_json, data=json.dumps(ir_send))

    del red
    del ir
    gc.collect()
        # print("gc coll")
    # url_json = 'http://3.15.190.36:5000/rr'
    # utime.sleep(1)
    # res_json = requests.post(url_json, data=json.dumps(data))

    