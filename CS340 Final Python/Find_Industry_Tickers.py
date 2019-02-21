import json
import sys
from bson import json_util
from pymongo import MongoClient
connection = MongoClient('localhost', 27017)
db = connection['market']
collection = db['stocks']
def find_tickers():
  try:
    script_name, input = sys.argv
    query = { "Industry" : input}
    result=collection.find(query, { "_id": 0, "Ticker": 1})
  except Exception as ve:
    return ve
  return result
def main():
  output = find_tickers()
  for entry in output:
    print entry
main()