oscp5
=====

An Open Sound Control (OSC) implementation for java and processing.org

## Updates
By https://github.com/trackme518 :

* fixed bug on Linux with OSC broadcast (in UdpServer.java)
* exposed sender ip address with new function .getIP() - returns IP as a String (ie "168.0.4.2")


```
public String getIP( ) {
	return hostAddress;//Bytes.getAsString( hostAddress );
}
```
In OscMessage.java

```
channel.socket().setBroadcast(true);
```
In UdpServer.java

## <a name="issues"></a>Digital Object Identifiers

In case you make use of OscP5 in your research publication, please use the DOI below as a reference.

Digital Object Identifiers (DOI) are the backbone of the academic reference and metrics system which allows researchers writing software to make their work they share on GitHub citable by archiving one of their GitHub repositories and assigning a DOI with the data archiving tool Zenodo [(link)](https://guides.github.com/activities/citable-code/).


[![DOI](https://zenodo.org/badge/11256/sojamo/oscp5.svg)](http://dx.doi.org/10.5281/zenodo.16308)

Copyright 2004-2015 Andreas Schlegel
