from flask import Flask,request,Response
import json
import time
import pymongo
import pickle
import numpy as np
from sklearn import ensemble
import hrcalc

myclient = pymongo.MongoClient('mongodb://localhost:27017/')
iot_db = myclient['IoT']
rr_col=iot_db['rr']
pred_col = iot_db['pred_con']

total_rr = [] 

ir_d_list = []
red_d_list = []

command = 0

app = Flask(__name__)
@app.route('/')
def index():
    return "dddd"


@app.route('/rr', methods=['POST'])
def getAcc():
    data = json.loads(request.get_data(as_text=True))  
    print(data)
    rr_col.insert_one(data)

    response = Response("ye!")
    response.status_code = 200
    
    return response

@app.route('/predict', methods=['POST'])
def pred():
    global total_rr, command, ir_d_list, red_d_list
    data = json.loads(request.get_data(as_text=True))  
    ir = data['ir'] # get feature by json dic
    red = data['red']

    ir_d_list.extend(ir)
    red_d_list.extend(red)

    if len(ir_d_list)% 900 == 0 and len(red_d_list)%900 == 0:
        ir_avg = []
        red_avg = []
        for i in range(33):
            d = hrcalc.calc_hr_and_spo2(ir_d_list[25*i:25*i+100], red_d_list[25*i:25*i+100])
            print(d)
            if d[1]:
                ir_avg.append(d[0])
            if d[3]:
                red_avg.append(d[2])
        ir_D = (sum(ir_avg) - max(ir_avg) - min(ir_avg)) // len(ir_avg)
        red_D = (sum(red_avg) - max(red_avg) - min(red_avg)) // len(red_avg)
        print('ir:',ir_D)
        print('red:',red_D)
        data = {'data':{'ir':ir_D,'red':red_D}}
        rr_col.insert_one(data)
        ir_d_list = []
        red_d_list = []

    if len(total_rr)< 19*300:
        total_rr.extend(ir)
        print(len(total_rr)/300)
    elif len(total_rr) == 19*300:
        total_rr.extend(ir)
        # print(total_rr)
        print("predict!")
        filename = 'finalized_model.sav'
        loaded_model = pickle.load(open(filename, 'rb'))
        command = int(loaded_model.predict([total_rr])[0])  # 0 or 1

        print("result:", command)

        pred_col.insert_one({"total_rr":total_rr})
        # total_rr = []
        del total_rr [:300]
    else:
        print("bbbb")
        total_rr = []

    response = Response("ye!")
    response.status_code = 200
    
    return response

@app.route('/command', methods=['GET'])
def getID():
    global command
    # data = json.loads(request.get_data(as_text=True))
    # print(data)
    response = Response(str(command))
    response.status_code = 200
    
    return response


@app.route('/android', methods=['POST'])
def android():
    global command
    data = json.loads(request.get_data(as_text=True)) 
    print(data)
    
    list_data = []
    for x in rr_col.find():
        ts = time.strftime("%H:%M", time.localtime())
        list_data.append({"value":x['data']['ir'],"ts":ts,"com":command})

    response = Response(json.dumps(list_data,ensure_ascii=False))
    response.status_code = 200
    
    return response
        


if __name__ == '__main__':
    app.run(host='0.0.0.0')
