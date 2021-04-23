# Lambda

A distributed Function-as-a-Service (FaaS) platform.

## Prerequisites

* [Docker Engine (>=19.03.10)](https://docs.docker.com/engine)
* [Docker Compose (>=1.27.0)](https://docs.docker.com/compose)

## Installation

Build and run service:

```
docker-compose up -d
```

## Usage

Create a lambda:

```
POST http://localhost:8000/api/v1/lambda
```

Body:

```json
{
  "name": "add",
  "description": "Add two floats and return sum.",
  "runtime": "Java",

}
```

## Documentation

Check the API reference for models and routes:

```
http://localhost:8000/api/v1/docs
```

The hostname and port of the server are defined by the `.env` file in the project root directory.

## Copyright and license

Copyright (c) 2021, Danilo Peixoto. All rights reserved.

Project developed under a [BSD-3-Clause license](LICENSE.md).
