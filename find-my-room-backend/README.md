# Backend Overview

This folder is import-ready for Eclipse.

## Structure

```
├── README.md # You Are Here!
├── mvnw
├── mvnw.cmd
├── pom.xml # dependencies
├── src
    ├── main
    │   ├── appengine # settings for app engine deployment
    │   ├── java # all main java source code
    │   └── resources # application properties, SQL initializers, statically hosted files
    └── test
        └── java # all testing source code
```

### A closer look in `src/main/java`

```
├── FindMyRoomBackendApplication.java # the entry point
├── ServletInitializer.java
├── controller # url handlings
├── model # models used to pass information
└── service # all the busy work, including database connections
```

