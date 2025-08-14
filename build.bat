doctl registry login
docker build . -t registry.digitalocean.com/quest-and-glory/app:latest
docker push registry.digitalocean.com/quest-and-glory/app:latest