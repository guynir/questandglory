# Local help file.

### Build Docker image and push

    docker buildx build -f Dockerfile . -t registry.digitalocean.com/quest-and-glory/app:latest

    docker push registry.digitalocean.com/quest-and-glory/app:latest

### Login to DigitalOcean Container Registry

    doctl registry login

### Setup authentication with DigitalOcean

    doctl auth init

You will be asked to provide an API access token.

### Removing existing authentication secret

    # For listing all existing authentication contexs.
    doctl auth list

    # Remove the relevant authentication context (e.g.: 'default').
    doctl auth remove <name-of-authentication-context>

### Run nginx locally

    docker run -p 80:9090 proxy