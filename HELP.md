# Local help file.

### Build Docker image and push

    docker buildx build -f Dockerfile . -t registry.digitalocean.com/quest-and-glory/app:latest

    docker push registry.digitalocean.com/quest-and-glory/app:latest
