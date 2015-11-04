# M-Pin Demo Application - Java implementation

## Prerequisites
* M-Pin Core successfully installed.
* Java 7 or above
* Tomcat 7 or above

## Setup

In order for this demo to work correctly, the M-Pin Core services should be installed on the same machine on which Tomcat and this demo are running.
Typical M-Pin Core insallation deploys M-Pin Server (sometimes refered as RPS) and a demo Relying Party Application (RPA), implemented in Python.
The M-Pin Server should and listens only to the loopback interface - 127.0.0.1:8011. The RPA is the service that is open to external connections and it proxies requests to the RPS, or makes "internal" requests to the private RPS API.
In order to run M-Pin Core without the included Pyhton Demo, the following command should be executed:
```
> cd <mpin-core-install-folder>
> sudo ./mpin stop all
> sudo ./mpin start
```
**NOTE** that the default for _<mpin-core-install-folder>_ is `/opt/mpin`

## Configuration

The default config file for the M-Pin Server should be replaced with the one included in this repo.
For M-Pin Core v3.4 and v3.5, this config file is `<mpin-core-installation-folder>/config_rps.py`
If your Tomcat listens on port 8080, the provided config file should work as it is. If not, the `config_rps.py` should be adjusted accordingly.
**NOTE** that after changing any M-Pin Core configuration, it should be restarted with the following commands:
```
> cd <mpin-core-install-folder>
> sudo ./mpin stop
> sudo ./mpin start
```

The Java RPA has a config file under its WEB-INF folder - `config.properties`.
By default it looks like that:
```
#M-PIN RPS URL
RPS_SERVER=http://localhost:8011
VERIFY_LINK_BASE_URL=http://192.168.10.73:8080
FORCE_ACTIVATE=true
#SMTP_HOST=
#SMTP_PORT=
#SMTP_USERNAME=
#SMTP_PASSWORD=
```
Configured like this, this Demo App will not verify the user identities by sending a verification e-mail, but will "force-activate" them.
In order to enable identity verification via sending e-mail, the following parameters should be set:
`VERIFY_LINK_BASE_URL` - the base URL for the verification link sent in the e-mail. Should be set with the accessible address of the server on which the Demo App is running.
`FORCE_ACTIVATE` - enable/disable user "force-activation". Set to `false`.
`SMTP_HOST`, `SMTP_PORT`, `SMTP_USERNAME` and `SMTP_PASSWORD` - address and credentials of the SMTP server that should be used to send verification e-mails. Unremark and set them as needed.

## Getting the M-Pin Mobile App working

When you have installed the M-Pin Mpbile App, you should go to the Configurations List and define a new configuration.
Assuming that your Tomcat listens on _<tomcat-server-address>:8080_, the settings should be:
M-Pin Server: <tomcat-server-address>:8080/mpin
RPS Prefix: (leave empty, as it is)
Service Type: Login to Online Session