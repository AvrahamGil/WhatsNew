# WhatsNew

#### Web application project URL - https://whatsnew.me

## Installation

### Docker Image
#### *Dockerfile:*

```
FROM whatsnewwebsite/whatsnew
```
**Or**
#### *Docker pull from command line:*
```
docker pull whatsnewwebsite/whatsnew
```

Once the image is ready, execute `docker run -p 8080:8080 whatsnew`

---
### Source Code

***Disclaimer:***
- The instructions are focused on Windows OS.
- Other OS users will require to adjust few steps according to their system.

#### Pre-Installation Environment

- The project runs on JDK 18:
    - https://www.oracle.com/java/technologies/javase/jdk18-archive-downloads.html

- Tomcat webserver, version 9.0.39:
    - https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.39/src/

- Install MongoDB on your system:
    - https://www.mongodb.com/

- Clone the project
    - `git clone https://github.com/AvrahamGil/WhatsNew.git`


- Import as Maven project
    - *File* -> *Import...* -> *Existing Maven Projects* -> *WhatsNew* Folder


####  API Keys
The project requires API keys to pull data from 3rd party APIs.
- RapidAPI
1) Browse to https://rapidapi.com/hub
2) Sign up and log in
2) On the navigation bar, navigate to **API Hub** and choose any API
4) Your API key is the `X-RapidAPI-Key`.



####  Request Headers
- Edit **./src/main/java/com/gil/whatsnew/enums/Requests.java** file, and modify the following:

```
RapidHost("x-rapidapi-host"),
RapidKey("x-rapidapi-key"),
RapidApi("https://contextualwebsearch-websearch-v1.p.rapidapi.com/api/search/NewsSearchAPI?toPublishedDate=null&fromPublishedDate=null&withThumbnails=true&pageSize=15&autoCorrect=false&pageNumber=1&q="),
RapidKeyValue("{Your-Rapid-API-Key}"),

ContextRapidHostValue("contextualwebsearch-websearch-v1.p.rapidapi.com");
```

####  Database Configurations

-  **MongoDB**
    - Create collection name `articles` ****(required)****
    - Edit the following lines in **./src/main/resources/application.properties**:
     ```
    spring.data.mongodb.database="db"
    spring.data.mongodb.username="yourusername"
    spring.data.mongodb.password="yourpassword"

-  **MySQL**
    - The project contains legacy support for MySQL but the default works with MongoDB.

####  Angular8 Production Mode
1) Open the command line and execute the following commands:
```
cd {WhatsNew}/src/main/webapp
npm install
ng build
```
2) Copy the production files inside **./dist/whatsnew/** and paste them in **{WhatsNew}/src/main/webapp/** folder.

**Note**: The original files inside the **webapp/** folder can be deleted, except for the **WEB-INF/** folder.

#### Production
1) Open IDE, and export the project as *WAR* file.
2) Copy the project's **.war** file to **{tomcat}/webapp/** folder.
3) Open the command line and execute:
```
cd {tomcat}/bin/
startup.bat
```
4) The new window is Tomcat console running in the background.
5) Browse to http://localhost:8080/whatsnew