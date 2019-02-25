import json
import sys
from bson import json_util
from pymongo import MongoClient

//Connect to the database and collection
connection = MongoClient('localhost', 27017)
db = connection['market']
collection = db['stocks']

//function for section III A ii (see rubric)
def find_tickers():
  try:
    script_name, input = sys.argv     //separates arguements so all after class name is stored in the "input" variable
    query = { "Industry" : input}
    result=collection.find(query, { "_id": 0, "Ticker": 1})
  except Exception as ve:             //generic exception handling, add specific exception possibilities when possible
    return ve
  return result
def main():
  output = find_tickers()
  for entry in output:                //print results
    print entry
main()
