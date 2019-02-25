#!/usr/bin/python
import json
from bson import json_util
import bottle
from bottle import route, run, request, abort
import pymongo
from pymongo import MongoClient

//This is the class that runs the RESTful API
//The at route statements indicate what URI you should enter to call these functions

//Connect to the database and collection
connection = MongoClient('localhost', 27017)
db = connection['market']
collection = db['stocks']

# set up URI paths for REST service
@route('/stocks/api/v1.0/getStock/<tickerSymR>', method='GET')
def rest_read(tickerSymR):
  result = collection.find_one({"Ticker" : tickerSymR})
  string = str(result) + "\n"
  return string
@route('/stocks/api/v1.0/createStock/<tickerSymC>', method='POST')
def rest_create(tickerSymC):
  result = request.json
  result.update({ "Ticker" : tickerSymC })
  string = str(result) + "\n"
  collection.save(result)
  return string
@route('/stocks/api/v1.0/deleteStock/<tickerSymD>', method='DELETE')
def rest_delete(tickerSymD):
  result = collection.find_one({ "Ticker" : tickerSymD})
  string = str(result) + "\n"
  collection.find_one_and_delete({ "Ticker" : tickerSymD})
  return string
@route('/stocks/api/v1.0/updateStock/<tickerSymU>', method='PUT')
def rest_update(tickerSymU):
  changes = request.json
  result = collection.find_one({ "Ticker" : tickerSymU})
  result.update(changes)
  collection.find_one_and_replace({ "Ticker" : tickerSymU}, result)
  string = str(result) + "\n"
  return string
@route('/stocks/api/v1.0/industryReport/<Indust>', method='GET')
def rest_industry_report(Indust):
  result = collection.find({ "Industry" : Indust}, { "_id": 0, "Industry" : 1, "Company" : 1, "Ticker": 1, "Return on Investment" : 1}).sort("Return on Investment", pymongo.DESCENDING).limit(5)
  string = ""
  for entry in result:
    string = string + str(entry)
  return string
@route('/stocks/api/v1.0/stockReport', method='POST')
def rest_stock_summaries():
  listAsJson = request.json
  inputs = listAsJson["array"]
  string = ""
  for singleTicker in inputs:
    result = collection.find_one({"Ticker" : singleTicker}, { "_id": 0, "Industry" : 1, "Company" : 1, "Ticker": 1, "Sector" : 1, "Price" : 1, "Profit Margin" : 1})
    string = string + str(result) + "\n"
  return string
if __name__ == '__main__':
    run(host='localhost', port=8080)
