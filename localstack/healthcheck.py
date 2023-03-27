#!/usr/local/bin/python
import requests
import sys

status=1
try:
    ready=requests.get("http://localhost:4566/_localstack/init/ready").json()["completed"]
    if ready==True:
        print("localstack is ready")
        status=0
    else:
        print("localstack starting")
except:
    print("localstack is starting")
sys.exit(status)


