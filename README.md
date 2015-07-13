# ePages Demo App

App Store example app using OAuth2 for accessing the [ePages REST API](https://developer.epages.com/apps/api-reference/rest-resources.html).

This app was developed to show-case how to intregrate a third party app into the ePages App Store using OAuth2. It is based on [Spring Boot](http://projects.spring.io/spring-boot/) and can be easily deployed to [Heroku](https://www.heroku.com). A sample deployment can be used at [http://kitten-app.herokuapp.com]().

## Build the app

This app uses [Gradle](http://gradle.org/) as it's build tool. Build it by using this command:

    ./gradlew clean bootRepackage

After successful build you will find a fat jar containing the whole app and all of it's assets in `build/libs/`.

## Run the app

Once built you can run the app by using this command:

    java -jar build/libs/*.jar

Another way to run the application is to use Gradle as well:

    ./gradlew run

You can pass parameters like this:

    ./gradlew run -Dserver.port=5000 -DCLIENT_ID=abc -DCLIENT_SECRET=xyz

If you have the [Heroku Toolbelt](https://toolbelt.heroku.com/) installed you can also use [Foreman](https://github.com/ddollar/foreman) for starting the app:

    foreman start web

Last but not least you can also use [Docker](https://www.docker.com/) and [Docker Compose](https://docs.docker.com/compose/) for starting the app:

    docker-compose build
    docker-compose up -d

It uses a public base image from [Docker Hub](https://registry.hub.docker.com/u/jensfischerhh/docker-java/) (Source: [jensfischerhh/docker-java](https://github.com/jensfischerhh/docker-java)).

### Command Line Options

You can provide a number of different command line options in order to modify the apps's behaviour. Either you set these properties as environment variables or as Java system properties (using the `-D` command line switch). For a list of standard properties have a look at [Spring Boot Appendix A](http://docs.spring.io/spring-boot/docs/current/reference/html/common-application-properties.html).

Common properties include:

| Purpose              | Environment Variable                 | System Property                        |
|----------------------|--------------------------------------|----------------------------------------|
| HTTP Port            | `PORT=8080`                          | `-Dport=8080`                          |
| Profile              | `PROFILE=development`                | `-Dprofile=development`                |
| OAuth2 client id     | `CLIENT_ID=54f46f318732110bd85f41c7` | `-Dclient_id=54f46f318732110bd85f41c7` |
| OAuth2 client secret | `CLIENT_SECRET=ClientSecret1`        | `-Dclient_secret=ClientSecret1`        |
| ePages AppStore Link | `INSTALL_LINK=https://www.epagesdemo.de/epages/TrialEpagesD20150529T084132R81.admin/?ViewAction=MBO-ViewAppDetails&appID=54f46f318732110bd85f41c7` | `-Dinstall_link=https://www.epagesdemo.de/epages/TrialEpagesD20150529T084132R81.admin/?ViewAction=MBO-ViewAppDetails&appID=54f46f318732110bd85f41c7` |

Debug mode can be activated by appending the command line option `--debug`.

## Access the app

The app can be accessed by HTTP ([http://localhost:5000]()) and HTTPS ([https://localhost:5001]()). For SSL it uses a self-signed certificate, so you will need to accept this in your browser.

## Using the app

The app needs to be installed into an ePages merchant's shop as documented at [https://developer.epages.com/apps/install-app.html]()

App installations are only held in-memory and currently don't get persisted to any database: after restarting the app all previous installations are gone! Feel free to submit a pull request for enhancing this behaviour to any real persistence ;-)

### Demo App API

The app uses Spring Boot Actuator to provide some valuable [REST endpoints](http://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-endpoints.html) for managing it's installation. These endpoints can be accessed below the `/system/` URL path, e.g. [https://localhost:5001/system/env](). It also uses [Jolokia](http://docs.spring.io/spring-boot/docs/current/reference/html/production-ready-jmx.html) to expose JMX configuration over HTTP below `/system/jmx/`, e.g. [https://localhost:5001/system/jmx/version]() or [https://localhost:5001/system/jmx/read/java.lang:type=Memory/HeapMemoryUsage]().

It also provides CRUD endpoints for managing it's app installations:

| Endpoint                    | Verb     | Purpose                      | Example |
|-----------------------------|----------|------------------------------|---------|
| `/api/installations`        | `GET`    | read all installations       | `curl -i -XGET http://localhost:5000/api/installations`|
| `/api/installations`        | `DELETE` | delete all installations     | `curl -i -XDELETE http://localhost:5000/api/installations` |
| `/api/installations`        | `POST`   | create new installation      | `curl -i -XPOST -H 'Content-Type: application/json' -d '{"apiUrl":"http://localhost:8088/rs/shops/DemoShop","token":"ABCDEF0123456789"}' http://localhost:5000/api/installations` |
| `/api/installations/{shop}` | `GET`    | read specific installation   | `curl -i -XGET http://localhost:5000/api/installations/http:%2F%2Flocalhost:8088%2Frs%2Fshops%2FDemoShop` |
| `/api/installations/{shop}` | `DELETE` | delete specific installation | `curl -i -XDELETE http://localhost:5000/api/installations/http:%2F%2Flocalhost:8088%2Frs%2Fshops%2FDemoShop` |
