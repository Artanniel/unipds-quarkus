# unipds

This project uses Quarkus, the Supersonic Subatomic Java Framework.

If you want to learn more about Quarkus, please visit its website: <https://quarkus.io/>.

## Running the application in dev mode

You can run your application in dev mode that enables live coding using:

```shell script
./mvnw quarkus:dev
```

Classroom with Unipds:

```
mvn clean quarkus:dev

```

For generate package:

```
mvn clean package

```

For generate native executable:

```
mvn clean package -Dnative

```

Aftre run command, check the target folder for see build files:

```
cd target
ls
```

For run the application:

```
java -jar target/quarkus-app/quarkus-run.jar

or

./unipds-1.0.0-SNAPSHOT-runner
```

For add extension, you can access the sit: https://code.quarkus.io/, search the extension and click on generate button, then copy the command and run it in your terminal:

Sample:
```shell script
./mvnw quarkus:add-extension -Dextensions="io.quarkus:quarkus-rest-client"
```


> **_NOTE:_**  Quarkus now ships with a Dev UI, which is available in dev mode only at <http://localhost:8080/q/dev/>.

## Packaging and running the application

The application can be packaged using:

```shell script
./mvnw package
```

It produces the `quarkus-run.jar` file in the `target/quarkus-app/` directory.
Be aware that it’s not an _über-jar_ as the dependencies are copied into the `target/quarkus-app/lib/` directory.

The application is now runnable using `java -jar target/quarkus-app/quarkus-run.jar`.

If you want to build an _über-jar_, execute the following command:

```shell script
./mvnw package -Dquarkus.package.jar.type=uber-jar
```

The application, packaged as an _über-jar_, is now runnable using `java -jar target/*-runner.jar`.

## Creating a native executable

You can create a native executable using:

```shell script
./mvnw package -Dnative
```

Or, if you don't have GraalVM installed, you can run the native executable build in a container using:

```shell script
./mvnw package -Dnative -Dquarkus.native.container-build=true
```

You can then execute your native executable with: `./target/unipds-1.0.0-SNAPSHOT-runner`

If you want to learn more about building native executables, please consult <https://quarkus.io/guides/maven-tooling>.

## Related Guides

- REST ([guide](https://quarkus.io/guides/rest)): A Jakarta REST implementation utilizing build time processing and Vert.x. This extension is not compatible with the quarkus-resteasy extension, or any of the extensions that depend on it.
- SmallRye OpenAPI ([guide](https://quarkus.io/guides/openapi-swaggerui)): Document your REST APIs with OpenAPI - comes with Swagger UI

## Provided Code

### REST

Easily start your REST Web Services

[Related guide section...](https://quarkus.io/guides/getting-started-reactive#reactive-jax-rs-resources)

For testing the fallback application, in terminal:

while true; do curl -X 'GET'   'http://localhost:8081/api/starwars/starships'   -H 'accept: application/json'; sleep .3; done


Health Check e Readiness Check:

http://localhost:8081/q/dev-ui/quarkus-smallrye-health/health

http://localhost:8081/q/health/

![alt text](<src/main/resources/images/Screenshot from 2026-03-25 22-11-48.png>)
