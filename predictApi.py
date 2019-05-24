import numpy as np
import random
from flask import Flask, request, jsonify
import json
import datetime
from dateutil import parser
from datetime import * 
from dateutil.relativedelta import *


app = Flask(__name__)



@app.route("/")
def hello():
    return "Welcome to machine learning model APIs!"

@app.route("/predict",methods=['POST'])
def predict():
    #Read input from httprequest 
    data = request.get_json(force=True)
    
    #Replace single quotations to double to parse
    python_obj = json.loads(str(data[0]).replace("\'", "\""))
    
    #extract datetopredict
    expPredictDate = python_obj["DateToPredict"]
    
    #parsing date from string
    dt = parser.parse(str(expPredictDate))
    
    #Create Array to store 5 days of this format date, price
    jstringbuild = "[ "
    for i in range(5):
        jstringbuild += "{ \"Date\" : \"" + str((dt+relativedelta(days=+i)).strftime("%x")) + "\" , \"price\" : " + str(random.randrange(2050, 5000)) +" }, "

    jstringbuild += " ]"

    #from joblib import dump, load
    #model =load('MangoPriceModel.pkl')
    
    return str(jstringbuild)

if __name__ == '__main__':
    app.run(port=5012, debug=True)