#!/bin/bash

while read p;
do
    curl -X POST --header "Content-Type: application/json" --request POST --data "$p" https://mois-banking.herokuapp.com/v1/transaction?accountId=6669 -v
done <apis.txt
