Objective
---------

The objective of the project is to do an application that complies the programming assignment given in the file TechnicalAssignment.pdf

Limitations
-----------

The solution has certain limitations. They are the following:
. The solution only uses a in memory storage. When the application stops all the data is lost.
. For the same reason the solution doesn't provide scalability. Two different Java VMs will have different data sets.

Storage
-------
In this simplistic implementation the storage is a Map where the key of the map entry is the name of the account and the value of the map entry is an instance of Account class.
The management of the concurrency and the integrity has its roots in the MVCC (Multiversion concurrency control). The implementation uses the snapshot isolation as level of isolation. Everyone could read the last consistent state of the accounts and let modify freely the content of the accounts, but when the thread want to write (commit) the changes the system evaluates if the modified accounts to be written are the latest or in between other thread did a commit first.
The system hasn't retries. In case of fail to write the changes, an error is returned and the business layer is responsible to retry or not the operation. At the moment is not retrying.
A lock is used to serialize the write access the the map.

Self-contained application
--------------------------
Although it is possible to package this service as a traditional WAR file for deployment to an external application server, this approach creates a standalone application. 
All is packed in a single, executable JAR file, driven by a Java main() method. 
Along the way, uses Spring’s support for embedding the Tomcat servlet container as the HTTP runtime, instead of deploying to an external instance.

Requirements
------------
The application needs Java 1.8 and Maven 3.x installed.

Compile / Run / Test
-------------------- 
. Compile
In the root directory run the command:
 	$ mvn clean compile
 
. Run
Is possible to execute the application without create a jar file. Do the following in the root directory:
	$ mvn sprint-boot:run

Is possible to create a jar file and the execute it. Do the following in the root directory:
	$ mvn clean package
	$ java -jar target/bank-0.0.1.jar 

. Test
To test the application do the following in the root directory:
	$ mvn test

Compatibility with Java 9
-------------------------
This application is not full compliant with Java 9. Modules JAXB and SOAP are not available by default.
When you run the java command or the mvn command add the Java VM argument to the command line 
	--add-modules java.xml.bind --add-modules java.xml.soap
	
TODO
----
. More unit tests
. Better error management
. AOP or something similar to catch system and runtime exceptions in the Bank service
. Retries at level of the persistence to re-read the latest snapshot of the accounts and do the operation again
. Use a RDBMS engine that supports use MVCC instead of the map/lock.




