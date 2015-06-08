# SAP Hana Cloud + Vaadin example

This is an example project that shows how you can use Vaadin UI framework to create powerful user interfaces to your SAP Hana backend with plain Java. The JPA persistency layer is built using local EJB to keep things simple but efficient. To make writing complex JPA queries easier, there is also a configuration for a handy library called QueryDSL that is used for some queries.

## Running/trying out the project:

### 1. Check out from the GitHub

If you have GIT installed you can just issue following command: 

    git clone http://github.com/mstahv/sap-hana-with-vaadin.git

Or you can just [download the example project as ZIP file](https://github.com/mstahv/sap-hana-with-vaadin/archive/master.zip).

### 2. Preparations

To run the project, you'll naturally need an account to SAP Hana Cloud services and a SAP Hana Cloud developer environment set up. There are couple of properties that needs to be set up. A handy and easy way to do this is to define a Maven profile, that you can enable and disable if you are working with SAP Hana Cloud stuff. Here is an example that you could add to your ~/.m2/settings.xml

Some IDEs don't enable profiles from default settings.xml so you may need to enable them from IDE as well.

    <settings xmlns="http://maven.apache.org/SETTINGS/1.0.0"
              xmlns:xsi="http://www.w3.org/2001/XMLSchema-instance"
              xsi:schemaLocation="http://maven.apache.org/SETTINGS/1.0.0
                          http://maven.apache.org/xsd/settings-1.0.0.xsd">
        <profiles>
            <profile>
                <id>sap-hana-cloud</id>
                <activation>
                    <activeByDefault>true</activeByDefault>
                </activation>
                <properties>
                    <!-- Properties that hold information on the location, version and plugin of the SAP HANA Cloud Platform SDK -->
                    <sap.cloud.sdk.path>/CHANGETHISPATH/neo-javaee6-wp-sdk-2.57.9</sap.cloud.sdk.path>
                    <sap.cloud.sdk.version>2.57.9</sap.cloud.sdk.version>
                    <sap.cloud.sdk.plugin>neo-javaee6-wp-maven-plugin</sap.cloud.sdk.plugin>
    
                    <!-- Property that holds the supported JDK version for the given SAP HANA Cloud Platform SDK -->
                    <sap.cloud.jdk.version>1.7</sap.cloud.jdk.version>
    
                    <!-- Properties for account, user name, and password that are required to access the SAP HANA Cloud Platform -->
                    <!-- data center for the cloud integration tests must be provided as Maven properties, e.g.: -->
                    <!-- mvn clean verify -P cloud-integration-tests -Dsap.cloud.account=... -Dsap.cloud.username=... -Dsap.cloud.password=... -->
                    <!-- To avoid retyping the above whenever you call Maven fill values here: -->
                     <sap.cloud.account>p123456789CHANGETHIS</sap.cloud.account> 
                     <sap.cloud.username>p123456789trialCHANGETHIS</sap.cloud.username> 
                    <!-- Hint: You may like to use environment variables and set the properties based on their values, e.g.: -->
                    <sap.cloud.password>xyzCHANGETHIS</sap.cloud.password>
    
                    <!-- Property that holds the data center host is pre-defined below, but may be overwritten if required, e.g.: -->
                    <!-- mvn clean verify -P cloud-integration-tests ... -Dsap.cloud.host=hanatrial.ondemand.com -->
                    <sap.cloud.host>hanatrial.ondemand.com</sap.cloud.host>
    
                    <!-- Property that holds the name of the application under which it will later appear in the cloud -->
                    <sap.cloud.application>sample</sap.cloud.application>
    
                    <!-- Properties that hold the local server configuration required for local integration-testing -->
                    <local.server.root>${project.build.directory}/server</local.server.root>
                    <local.server.host>localhost</local.server.host>
                    <local.server.shutdown.port>9003</local.server.shutdown.port>
                    <local.server.jmx.port>9004</local.server.jmx.port>
                    <local.server.http.port>9080</local.server.http.port>
                    <local.server.https.port>9443</local.server.https.port>
                    <local.server.ajp.port>9009</local.server.ajp.port>
    
                    <!-- You should most often have this as a global property anyways... -->
                    <project.build.sourceEncoding>UTF-8</project.build.sourceEncoding>
                    
                </properties>
            </profile>
        </profiles>
    </settings>

### 3. Import to IDE

Import the project to your IDE. This is definitely the easiest way familiarize yourself with the example code. 

The projects is a multi-module Maven project with a parent module that just defines some common dependencies and cascades the build to backend and vaadin-ui modules. The backend module contains JPA entities and a stateless EJB via it is easy to access the data in client applications.

There are some generated classes that needs to be built, so issue *mvn install* from the top level once you have imported the project. Eclipse might also need you to reconfigure/refresh the projects so that it discovers the auto-generated resources.

If you are new to Maven, check out [Maven essentials for the impatient developer](https://vaadin.com/blog/-/blogs/the-maven-essentials-for-the-impatient-developer) tutorial.

### 4. Deploying the project

The easiest method to deploy the application is to use the SAP Hana Cloud web UI and just upload the war file from vaadin-ui/target directory to your server. You can also use the SAP Hana Cloud Eclipse plugin and deploy the application to your cloud server. Deploy the app from the vaadin-ui project that picks up the backend module (the EJB based facade) as a jar file to your war package and contains the [Vaadin](https://vaadin.com/) based rich web UI layer.

Be sure to choose JRE 7 as execution JVM. Java 8 is not yet supported.
