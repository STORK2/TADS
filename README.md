
Trusted Attribute Display Service (TADS)
========================================

To deploy TADS, please follow the instructions provided below.

I highly recommend using a unix/linux system, as I provide a bash script to deploy
signserver. I have tested deployment on Windows, but I might not be able to give
proper support if needed.


=============================================================
Setup
=============================================================


* Node.js

TADS 2.0.0 was tested and deployed on Node version 0.12.1. If you already have 
node installed, you can upgrade it using the n package from npm: 
https://www.npmjs.com/package/n .


* SAMLEngine

TADS uses the STORK SAML Engine written in JAVA with a wrapping API.
The Java API is called STORKTADSUtils and it's located within the java/ folder.
The STORKTADSUtils can be imported as a maven project that is dependant on the STORK
common code and libraries. If you have the STORKSAMLEngine and Commons from the
common code in your maven local repository, you should be able to compile it without
any issue by running "mvn clean package", which will generate a jar file (located at
target/STORKTADSUtils-1.4.0.jar). The jar should then be copied to the TADS java/ folder 
so that the API can be loaded upon deployment. You can also run the command 
"npm run tadsutils_compile" from the project root folder. This will automatically
compile and copy the jar to the right location.

To configure your own certificate and keys you need to edit the .xml files in 
java/STORKTADSUtils/src/main/resources before compiling. The key and cert should
be within a keystore in jks format so that the SAMLEngine can load it. For testing
purposes a keystore is provided, so you can skip the step of creating the keystore,
but mind that this is a fundamental step to put TADS into production.


* TADS

Most of the configurations of TADS are set in the file resources/tads.properties.
There, you can set the identifier of your TADS server, set your PEPS url and configure 
the attributes that can be requested in the friendly name format and respective 
"translation" to be displayed to the user. The attributes need to match the ones
supported by the STORKSAMLEngine (this is configurable in the file 
"java/STORKTADSUtils/src/main/resources/StorkSamlEngine_SP.xml) otherwise the 
wrapping API will thrown an exception upon execution.

The browser cookies that store the session id are signed using a secret provided in
the [tads] section of the tads.properties file. Please change this secret key with
a string of your choosing. You can use the command "openssl rand -base64 32" to
generate a random string

The url configuration in the tads.properties file sets the protocol and port where
the TADS server will be listening. If you are behind a reverse proxy that serves 
HTTPS, set the option reverseProxy to true and set the samlReturnUrl in the tads.properties 
accordingly to the protocol and port the proxy is listening on.

To deploy TADS with HTTPS using your own keys and cert please put them in the keys/
folder and configure the paths in the tads.properties file. The cert and key must be 
in .pem format.

NOTE: The provided certificate and key are self signed and for testing only.
Once you set up the keys, change the protocol in the url in tads.properties 
([tads] section). You can also change the port there, if none is specified, port
80 will be used for http and port 443 for https.

TADS will prompt a verifier for his email and send him a unique url that he can
use to access the document verification service. To configure the SMTP server and 
the email account you edit the [eMail] section of tads.properties.

NOTE: The SMTP account currently being used is a gmail account I used for testing only,
      you should configure a proper SMTP account under your organization.


* SignServer (www.signserver.org)

SignServer is the open source server used to sign the PDF documents created by 
TADS. I recommend using JBOSS 7.1.1 as the Application Server (TOMCAT should also
work, but I won't provide documentation on how to deploy it there).

Software Requirements:
  -JBOSS 7.1.1 ("http://download.jboss.org/jbossas/7.1/jboss-as-7.1.1.Final/jboss-as-7.1.1.Final.zip");
  -Apache ANT ("http://ant.apache.org/);

Please configure the necessary environment variables for JBOSS and make sure it
runs properly. If you are using Linux you can install Ant from the repositories of 
the distribution you are using.


=============================================================
Deployment
=============================================================

  * TADS
  ------

 Software Requirements:

  Common:
    Nodejs 0.12.1 (http://www.nodejs.org);
    mongodb (http://www.mongodb.org);
    Python 2.x (2.7 recommended);
    Oracle Java JDK (or open-JDK) 1.6 or higher.

  On Unix/Mac:
    - A gcc compiler (like GCC);
    - make.

  NOTE: All of the above requirements are usually available on linux repositories
        (with the exception of Oracle Java).
        Install them with your distribution package manager.

  On Windows(7/8):
    - Microsoft Visual Studio C++ 2012 (you can get the express version on: 
        go.microsoft.com/?linkid=9816758).

  On Windows (XP/Vista/7):
    - Microsoft Visual Studio C++ 2010 (you can get the express version on: 
        go.microsoft.com/?linkid=9709949)
    - For 64-bit builds of node and native modules you will also need 
        Windows 7 64-bit SDK (http://www.microsoft.com/en-us/download/details.aspx?id=8279);
  

After you have installed the required software for your platform, follow these steps:

  On Unix/Mac:
    1) Create directories /opt/storklogs /opt/keystores;
    2) Create empty log file /opt/storklogs/stork-commons.log;
    
    Note: The above two steps can have a different path if you modified the 
          SAMLEngine Configuration files 

    3) Move the provided resources/keys/sskeystore.jks file to /opt/keystores;
    4) Go to the TADS folder and run "npm install";
    5) Run the mongodb daemon (mongod);
    6) Run "node tads.js"

    NOTE: If you get errors on step 4 or 6, please see the notice at the end of this file.

  On Windows:
    1) Create folders C:\opt\storklogs and C:\opt\keystores;
    2) Move the provided storkDemoKeys.jks File (in the TADS resources folder) 
          to C:\opt\keystores;
    3) Add necessary environment variables:
      3.1) Right click “My Computer”, select properties -> 
              Advanced System Properties -> Environment Variables;
      3.2) Add a new variable under System Variables with Name: JAVA_HOME 
              and Value: “C:\path\to\java_home” (it is usually located under 
              C:\Program Files\Java\jdk1.x.x);
      3.3) Append to the Path variable value in System Variables: 
              “;C:\Program Files\Java\jdk1.x.x\bin;C:\Program Files\Java\jdk1.x.x\jre\bin\server”. 
              This path depends on where your java SDK is installed (Mind that 
              the \server folder is instead called \client on earlier versions 
              of java than 1.8.0).
    4) Open a command prompt, go to the TADS folder, run "npm install";
    5) Run the mongodb daemon;
    6) Run "node tads.js".


The TADS server should now be running.

-------------------------------------------------------------

* SignServer
------------

Before you install and deploy SignServer, you need to install JBOSS and make sure
it is up and running. Then, from the TADS home folder, run the command: 
"npm run-script signserver".

This command will run a script (located at: scripts/signserver.sh) that automatically 
exports the necessary SignServer environment variables, downloads, configures, builds 
and deploys signserver on JBOSS.

NOTE: If something goes wrong with running the script, it might be because of a 
      platform variation of a command. Please try to follow the script steps manually.

If all goes well, you should be able to access the SignServer GUI from:
"http://localhost:8080/signserver".

To use your own keys for signing, you need to edit the corresponding lines
in the file $SIGNSERVER_HOME/doc/sample-configs/qs_pdfsigner_configuration.properties,
and then run: "npm run-script signserver_config" (from the TADS home folder) 
to update the current deployment with the new settings (mind that JBOSS must be
running and signserver must be deployed).

If you need further assistance please check the SignServer Documentation at: 
http://signserver.org/manual/installguide.html#Signer%20setup

-------------------------------------------------------------
NOTICE FOR SOME LINUX DISTRIBUTIONS (Debian/Ubuntu/Others)
-------------------------------------------------------------

If the npm command doesn't seem to be avaliable, install it from your distribution
package manager ($sudo apt-get install npm for Ubuntu/Debian). NPM is a package 
manager for node, and it's sometimes included in the node package, but sometimes 
it is not.

It should be noticed that for some linux distributions the nodejs executable 
is not called 'node', but 'nodejs' (due to a conflict with an already existing 
package called 'node'), and when running the 'npm' command, it will fail because
it is trying to invoke the executable as 'node' (I guess the other modules use 
some environment variable to find the executable). A simple patch is to temporary 
link the executable to the expected name.

As root, run:
ln -s /usr/bin/nodejs /usr/bin/node

-------------------------------------------------------------

TROUBLESHOOTING
---------------

If the module node-java fails to install during the npm install command, please
try to run the command again with the following flag:

npm install --msvs_version=2013

