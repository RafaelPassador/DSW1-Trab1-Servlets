#!/usr/bin/sh
docker run -it --rm --name web1 -v "$(pwd):/app" -v "$(pwd)/.m2:/root/.m2" -w /app maven mvn package
