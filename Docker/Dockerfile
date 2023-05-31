FROM mongo

WORKDIR /tmp

RUN apt-get update && apt-get install -y \
    openjdk-11-jre \
    curl

RUN groupadd tomcat && \
    useradd -s /bin/false -g tomcat -d /opt/tomcat tomcat && \
    curl -O https://archive.apache.org/dist/tomcat/tomcat-9/v9.0.70/bin/apache-tomcat-9.0.70.tar.gz && \
    mkdir /opt/tomcat && \
    tar -xzvf apache-tomcat-9.0.70.tar.gz -C /opt/tomcat --strip-components=1

COPY ./whatsnew.war /opt/tomcat/webapps/
COPY ./mongo-users.js /tmp/mongo-users.js
COPY ./startup.sh /tmp/startup.sh
COPY ./json/* /tmp/json/

WORKDIR /opt/tomcat

RUN chgrp -R tomcat /opt/tomcat && \
    chmod -R g+r conf && \
    chmod g+x conf && \
    chown -R tomcat webapps/ work/ temp/ logs

ENTRYPOINT ["/tmp/startup.sh"]

