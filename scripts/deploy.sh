#!/usr/bin/env bash

ssh deploy@gendercomics.net 'docker pull gendercomics/api:latest | cd /var/gendercomics/docker | docker-compose up -d'
