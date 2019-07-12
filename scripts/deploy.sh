#!/usr/bin/env bash

ssh root@gendercomics.net 'docker pull gendercomics/api:latest && docker-compose up -f /root/gendercomics/docker-gendercomics/docker-compose.yml -d'
