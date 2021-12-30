# find-my-room :bed:

A web application for students to find subleases.

## Setup :construction:

### Importing Files :file_folder:

1. Run the command below (SSH key required):
```bash
git clone git@github.com:enoue/find-my-room.git
```
2. Run the initialization SQL files to set up a local MySQL database.

Open the backend project (`find-my-room-backend` folder) in Eclipse through **Open Projects from File System...**

### Installations :hammer_and_wrench:

* Install and add the JDK 11 runtime environment in **Window > Preferences > Java > Installed JREs** (On Mac, **Eclipse > Preferences > Java > Installed JREs**)
  * Default installation location
    * Windows: `C:\Program Files\Java\jdk-11.x.x`
    * MacOS: `/System/Library/Java/JavaVirtualMachines/11.x.x.jdk/Contents/Home`
  * In **find-my-room >  Properties > Java Build Path > Libraries**, ensure that the JRE System Library is bound properly to JDK 11. If it isn't fix, it by editing it to use to correct alternate JRE.
  * Alternatively, you can change the JDK runtime to your own preferred version - just note that GCP only supports Java 8 or 11
* OPTIONAL: Install Spring Tools 4 (you can install via **Help > Eclipse Marketplace...**)

## Running Locally :round_pushpin:

### Frontend :arrow_forward:

```bash
cd find-my-room-frontend
npm install
npm start
```

You might need to modify `find-my-room-frontend/src/setupProxy.js` so that the target matches your local instance of the backend. 

### Backend with Local MySQL :cd:

* In `find-my-room-backend/src/main/resources/application.properties`, ensure the first line is not commented to disabled Cloud SQL, and specify the JDBC URL and credentials for your database.
* **Run As > Maven Build...** using goal `spring-boot:run`
  * Alternatively, if you installed Spring Tools, you can **Run As > Spring Boot App**
* Check out http://localhost:8080

### Backend with Cloud SQL server :cloud:
You must specify the RSA key which allows you to communicate with GCP.
1. Obtain a JSON key for the service (*keep it safe and off of GitHub!*)
2. Add the following environment variable to **Run Configurations > *name of server run configuration* > Environment**:

| Variable name                  | Value                        |
| ------------------------------ | ---------------------------- |
| GOOGLE_APPLICATION_CREDENTIALS | *full path to JSON key file* |

You can alternatively define the environment variable through your OS:

* **Windows PowerShell**: `$env:GOOGLE_APPLICATION_CREDENTIALS="[PATH]"`

* **Windows CMD**: `set GOOGLE_APPLICATION_CREDENTIALS=[PATH]`

* **Linux/MacOS**: `export GOOGLE_APPLICATION_CREDENTIALS="[PATH]"`

3. Switch the MySQL server specified in `find-my-room-backend/src/main/resources/application.properties` 
4. **Run As > Maven Build...** or **Run As > Spring Boot App**
5. Check out http://localhost:8080

## Deploying on App Engine :boat:

You will need to deploy the frontend and backend separately as microservices.

### Migration :arrow_right:

1. Clone this repository to GCP
2. Add SQL dump files to a bucket in Google Storage
3. Create a MySQL instance in Cloud SQL, start it up, and import the SQL dump files from the bucket
   1. Import `schema.sql` first, then import `data.sql`
4. Ensure the first line in `application.properties` is commented out to enable Cloud SQL, and change the SQL URL and credentials if necessary.
5. Follow the instructions below in the Google Cloud Shell to deploy

### Deploying Frontend :rocket:

```bash
cd find-my-room/find-my-room-frontend
npm install
npm run build # to test locally
gcloud app deploy # to deploy
```

### Deploying Backend :ship:

```bash
cd find-my-room/find-my-room-backend
chmod +x mvnw
vim src/main/resources/application.properties # to open editor to change SQL connection
./mvnw spring-boot:run # to test locally
./mvnw package appengine:deploy # to deploy
```

If you want to deploy without running tests, add the `-DskipTests` parameter

### Connecting Microservices :arrow_up_down:

```bash
# in the root folder
gcloud app deploy dispatch.yaml
```

Make sure to test after deployment!