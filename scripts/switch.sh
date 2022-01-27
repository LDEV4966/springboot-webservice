#!/usr/bin/env bash

ABSPATH=$(readlink -f $0) # stop.sh 위치
ABSDIR=$(dirname $ABSPATH) # stop.sh 위치의 dirname 가져온다. -> import profile.sh 목적을 위해
source ${ABSDIR}/profile.sh #import profile.sh

function switch_proxy() {
    IDLE_PORT=$(find_idle_port)

    echo "> 전환할 Port: $IDLE_PORT"
    echo "> Port 전환"
    echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" | sudo tee /etc/nginx/conf.d/service-url.inc
    # echo "set \$service_url http://127.0.0.1:${IDLE_PORT};" : 하나의 문장을 만들어 파이프라인으로 넘겨주기 위해 echo를 사용
    # | sudo tee /etc/nginx/conf.d/service-url.inc : 앞에서 넘겨준 문장을 service-url.inc 에 덮어 씐다.
    echo "> 엔진엑스 Reload"
    sudo service nginx reload
}
