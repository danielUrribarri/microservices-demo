#!/bin/bash

ACTORS_URL="http://localhost:8085/api/v1/actors"
FILMS_URL="http://localhost:8086/api/v1/films"

INTERVAL=1

ACTOR_IDS=("1" "5" "10" "9999")
FILM_IDS=("1" "5" "20" "9999")

while true; do
    SERVICE=$(( RANDOM % 2 ))

    if [ "$SERVICE" -eq 0 ]; then
        ID=${ACTOR_IDS[$RANDOM % ${#ACTOR_IDS[@]}]}
        URL="$ACTORS_URL/$ID"
        echo "[actors] GET $URL"
        curl -s -o /dev/null -w "status=%{http_code}\n" "$URL"
    else
        ID=${FILM_IDS[$RANDOM % ${#FILM_IDS[@]}]}
        URL="$FILMS_URL/$ID"
        echo "[films] GET $URL"
        curl -s -o /dev/null -w "status=%{http_code}\n" "$URL"
    fi

    sleep $INTERVAL
done
