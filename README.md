# Lambda

A distributed Function-as-a-Service (FaaS) service.

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

Request body:

```json
{
  "name": "sum",
  "description": "Sum numbers.",
  "runtime": "java",
  "source": "<sample>"
}
```

Source code sample:

```java
import java.util.List;

public class SumLambda {
  public static Number compute(List<Number> numbers) {
    System.out.println("Running sum lambda...");

    return numbers
      .stream()
      .reduce(0, (a, b) -> a.doubleValue() + b.doubleValue());
  }
}
```

Execute lambda:

```
POST http://localhost:8000/api/v1/execution
```

Request body:

```json
{
  "lambda_name": "sum",
  "arguments": "[[0, 1, 2, 3, 4, 5]]"
}
```

Query executions:

```
GET http://localhost:8000/api/v1/execution?lambdaName=sum
```

## Documentation

Check the API reference for models and routes:

```
http://localhost:8000/api/v1/docs
```

The hostname and port of the server are defined by the `.env` file in the project root directory.

## Roadmap

New features:

* Partial updates
* Synchronous executions
* Package dependencies
* One-shot container runtimes
* Python runtime

## Copyright and license

Copyright (c) 2021, Danilo Peixoto. All rights reserved.

Project developed under a [BSD-3-Clause license](LICENSE.md).
