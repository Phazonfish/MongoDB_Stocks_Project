import json
import sys
from bson import json_util
from pymongo import MongoClient

//Connect to the database and collection
connection = MongoClient('localhost', 27017)
db = connection['market']
collection = db['stocks']

//function for section II A (see rubric)
def read_document():
  try:
    with open("Insert_Input.json", "r") as read_file:   //Make sure Insert_Input.json is located in the proper directory
      result = json.load(read_file)
    collection.save(result)
  except Exception as ve:                               //generic exception handling, add specific exception possibilities when possible
    return ve
  return result
def main():
  output = read_document()
  print output                                          //print results
main()
