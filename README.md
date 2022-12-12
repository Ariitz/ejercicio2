![master](https://github.com/JDA-Product-Development/exec-ecom-bff-web/workflows/master/badge.svg)
[![Codacy Badge](https://app.codacy.com/project/badge/Grade/5fad601b1dea45a0a28401bc2a5b5489)](https://www.codacy.com?utm_source=github.com&amp;utm_medium=referral&amp;utm_content=JDA-Product-Development/exec-ecom-bff-web&amp;utm_campaign=Badge_Grade)
[![Codacy Badge](https://app.codacy.com/project/badge/Coverage/5fad601b1dea45a0a28401bc2a5b5489)](https://www.codacy.com?utm_source=github.com&utm_medium=referral&utm_content=JDA-Product-Development/exec-ecom-bff-web&utm_campaign=Badge_Coverage)

# exec-ecom-bff-web

## Table of Contents
- [Artifactory Requirements](#artifactory)
- [Building](#building)
- [Run Check](#runcheck)
- [Running Service](#running)
- [Calling an API](#apis)

## Artifactory Requirements

JFrog artifactory server requires authentication to consume artifacts.  Therefore, developers must configure local credentials to artifactory for gradle to use. See the
 [Onboarding Doc](https://jda365.sharepoint.com/sites/a_ExecutionECommerce919/SitePages/E-Commerce-Internal-Onboarding.aspx#artifactorysetup) for details.

<a name="ide"></a>
## IDE Requirements

This project uses [Lombok](https://projectlombok.org/) to automatically generate boilerplate such as POJO getters + setters. Lombok uses Annotation Processing, which
requires some [IntelliJ configuration](https://www.baeldung.com/lombok-ide).

<a name="building"></a>
## Building

This project uses gradle wrapper to prevent users from having to install gradle at a global level.  Make sure you use `gradlew` for execution calls _not_ `gradle`.  This also ensures that the same versions of the tooling are being used across deployments.

To build the project execute the following from the project root:

```
./gradlew build
```

<a name="runcheck"></a>
## Run check

Simply run `````./gradlew clean check.`````

<a name="running"></a>

## Running the service locally

The BFF web service is a spring boot app and can be started using gradlew.

```
./gradlew bootrun
```

The default app location is `localhost:8080`.

If communication with Data Platform is required, you'll need to include an Authorization bearer token on your requests.  The easiest way to get one is from the API Developer portal:
# Navigate to https://apideveloper-tst.jdadelivers.com.  Open any API and click Try It.
# Change the LIAM authentication from `No auth` to `implicit`.
# Copy the bearer token from the sample HTTP request and add to your Postman request.

<a name="graphiql"></a>
## GraphiQL

This project includes GraphiQL, a graphical user interface for browsing and executing GraphQL requests.  When the BFF is running, navigate to `http://localhost:8080/graphiql`.

<a name="dataplatform"></a>
## Data Platform communication

Once deployed to staging, the BFF relies upon the internal API Gateway endpoint to communicate with data platform.  End user requests submitted to the BFF (normally through an MFE) will
enter Azure through the public API Gateway, which requires authentication through LIAM.  Once authenticated, API Gateway forwards the request to the BFF service while including an
<a href="https://github.com/JDA-Product-Development/plat-apim-api-registry/blob/master/docs/IDENTITY-CONTEXT.md">identity context header</a> that identifies the user that made the request.
This header is propagated on all outgoing requests initiated by the BFF to effectively impersonate the original user making the request.  

This method of communication does not work when running locally as the internal API Gateway endpoint is only accessible from resources deployed in the same Azure VNET as APIM.  Instead,
the dev profile uses the public API Gateway endpoint to communicate with data platform.  This means that you must supply a LIAM token to authenticate with Gateway.  If using Postman for
dev testing, see the instructions in the <a href="https://teams.microsoft.com/l/entity/com.microsoft.teamspace.tab.wiki/tab::b096ae1d-76f5-4ebe-a694-985277685d1d?context=%7B%22subEntityId%22%3A%22%7B%5C%22pageId%5C%22%3A55%2C%5C%22sectionId%5C%22%3A57%2C%5C%22origin%5C%22%3A2%7D%22%2C%22channelId%22%3A%2219%3Ab138afbf7ce24e899efe413cd6f15747%40thread.tacv2%22%7D&tenantId=2ac36cee-0617-4ac0-bebf-e1ce5dfab86c">#development Teams wiki</a>.
If using GraphiQL, you can install a browser extension to include the Authentication header on your requests.  If using this method, you'll need to retrieve the LIAM token yourself to supply
it to the extension.

NOTE: If you supply a LIAM token in the Authorization header, the BFF will *not* create an identity context from this token.  If a different service (such as inventory service) requires
an identity context on incoming requests, you will still need to supply this header, too.