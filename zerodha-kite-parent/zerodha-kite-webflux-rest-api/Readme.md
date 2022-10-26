## curl command for http/2

```
curl -sI http://127.0.0.1:8080/hello-world
```

#### :warning: http/2 Information
1. HTTP/2 only works over HTTPS

For Example: <br/>

```
curl -sI http://127.0.0.1:8080/hello-world
```


Output is:<br/>

```
HTTP/1.1 200 OK
Content-Type: text/plain;charset=UTF-8
Content-Length: 11
```

Here, Based on the first line of the response: ```HTTP/1.1 200``` 
we can easily spot the fact that ```HTTP/1.1``` is still being used.


## Generate Keystore
```
keytool -genkeypair -alias zerodha -keyalg RSA -keysize 2048 -storetype PKCS12 -keystore zerodha.p12 -validity 3650
```

## [Spring JPA (Non blocking)] (https://github.com/ivangfr/spring-data-jpa-r2dbc-mysql-stream-million-records/blob/main/streamer-data-r2dbc/pom.xml)

## Postgres
```
docker run -d --name stocks-dev -e POSTGRES_PASSWORD=stockpassword -e PGDATA=/var/lib/postgresql/data/pgdata -v E:\DEV\CONTAINERIZATION\DOCKER\VOLUME:/var/lib/postgresql/data postgres
```