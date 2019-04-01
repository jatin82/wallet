## wallet

### Requirement

Require Mysql Ver 5.7.25
Require JAVA 8 
Require Gradle 4.9

### Deploy

1. run command gradle clean build
2. cd /build/libs
3. rum cmd  java -jar wallet-0.0.1-SNAPSHOT.jar --{options}
    options can be seen in application.properties
4. Server will up and running!!

### API Documentation

#### -User SignUp 
  curl -X POST \
  http://localhost:8085/wallet/signup \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 34293f55-f589-4c90-9648-098bcf72609f' \
  -H 'cache-control: no-cache' \
  -d '{
	"email":"jatin.sokhal4@gmail.com",
	"password":"password23",
	"name":"Jatin Sokhal"
}'

#### -User SignIn

curl -X POST \
  http://localhost:8085/wallet/signin \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: ad25df4b-1f17-4f70-b231-59af2dcb1ead' \
  -H 'cache-control: no-cache' \
  -d '{
	"email":"jatin.sokhal4@gmail.com",
	"password":"password23"
}'

#### -Get User Details

curl -X GET \
  http://localhost:8085/wallet/user \
  -H 'Postman-Token: 6b667bdd-34ca-4197-ad6e-9fe537dd2881' \
  -H 'authorization: eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjQsIm5hbWUiOiJKYXRpbiBTb2toYWxzIiwiZW1haWwiOiJqYXRpbi5zb2toYWw0QGdtYWlsLmNvbSIsImV4cCI6MTgwMCwiaWF0IjoxNTU0MTE1NTkzfQ.wLC7grao-c_A8EUWwUwNglNljw2b8HUWx7a2dM4d5BR_ZbLidumREMDhEc0DAU_QryRgcKoWu08smyaLLbXuNw' \
  -H 'cache-control: no-cache'
  
#### -Update User details ( only name and password can be updated ) 
curl -X PUT \
  http://localhost:8085/wallet/user \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: 39a0f8b5-54f6-4c08-9264-f1d9284877f4' \
  -H 'authorization: eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjQsIm5hbWUiOiJKYXRpbiBTb2toYWwiLCJlbWFpbCI6ImphdGluLnNva2hhbDRAZ21haWwuY29tIiwiZXhwIjoxODAwLCJpYXQiOjE1NTQwNDgxODB9.CZ5q8xV-AVM4wunOpYWk-7JhF6R0FRAuBX8uturwN0O_vQ5tdcVZs5-4gRm91vJIAAfDvZWJS7VpCAQlpZRClw' \
  -H 'cache-control: no-cache' \
  -d '{
        "name": "Jatin Sokhals",
        "password":"password23"
    }'
    
#### -User Add money to wallet

curl -X POST \
  http://localhost:8085/wallet/user/transaction \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: c822ef0a-09c8-4a6e-b076-80095b327ce7' \
  -H 'authorization: eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjQsIm5hbWUiOiJKYXRpbiBTb2toYWwiLCJlbWFpbCI6ImphdGluLnNva2hhbDRAZ21haWwuY29tIiwiZXhwIjoxODAwLCJpYXQiOjE1NTQwNDgxODB9.CZ5q8xV-AVM4wunOpYWk-7JhF6R0FRAuBX8uturwN0O_vQ5tdcVZs5-4gRm91vJIAAfDvZWJS7VpCAQlpZRClw' \
  -H 'cache-control: no-cache' \
  -d '{
	"user":"{user-own-email-id}",
	"money":90,
	"type":"ADDED"
}'

#### -User Add transfer money from wallet

curl -X POST \
  http://localhost:8085/wallet/user/transaction \
  -H 'Content-Type: application/json' \
  -H 'Postman-Token: c872c389-1276-4238-89e4-43cc434d1e7e' \
  -H 'authorization: eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjQsIm5hbWUiOiJKYXRpbiBTb2toYWwiLCJlbWFpbCI6ImphdGluLnNva2hhbDRAZ21haWwuY29tIiwiZXhwIjoxODAwLCJpYXQiOjE1NTQwNDgxODB9.CZ5q8xV-AVM4wunOpYWk-7JhF6R0FRAuBX8uturwN0O_vQ5tdcVZs5-4gRm91vJIAAfDvZWJS7VpCAQlpZRClw' \
  -H 'cache-control: no-cache' \
  -d '{
	"user":"{recieving-user-email-id}",
	"money":90,
	"type":"TRANSFERED"
}'

#### -View User Passbook
curl -X GET \
  http://localhost:8085/wallet/user/passbook \
  -H 'Postman-Token: 57f6225e-72e6-4dd1-b5d9-c07d5e304114' \
  -H 'authorization: eyJhbGciOiJIUzUxMiJ9.eyJ1c2VySWQiOjQsIm5hbWUiOiJKYXRpbiBTb2toYWwiLCJlbWFpbCI6ImphdGluLnNva2hhbDRAZ21haWwuY29tIiwiZXhwIjoxODAwLCJpYXQiOjE1NTQwNDgxODB9.CZ5q8xV-AVM4wunOpYWk-7JhF6R0FRAuBX8uturwN0O_vQ5tdcVZs5-4gRm91vJIAAfDvZWJS7VpCAQlpZRClw' \
  -H 'cache-control: no-cache'

