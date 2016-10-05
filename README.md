# Badge Generator

Software component dedicated to the badge generation used for the Toulouse
DevFest.

## Installation

### Clone

First clone this project since it's not provided using Maven central.

```shell
$ git clone https://github.com/GDGToulouse/badge-generator.git
```

### Build

Second build the project using Maven command line.

```shell
$ maven package
...
[INFO] ------------------------------------------------------------------------
[INFO] Reactor Summary:
[INFO] 
[INFO] org.contrail Maven Multi Project .................. SUCCESS [0.003s]
[INFO] badge-generator-core .............................. SUCCESS [0.000s]
[INFO] commons ........................................... SUCCESS [5.336s]
[INFO] template .......................................... SUCCESS [0.153s]
[INFO] attendee .......................................... SUCCESS [1.230s]
[INFO] command ........................................... SUCCESS [1.783s]
[INFO] badge-generator-extensions ........................ SUCCESS [0.001s]
[INFO] billetweb ......................................... SUCCESS [1.019s]
[INFO] svg-template ...................................... SUCCESS [9.182s]
[INFO] interactive ....................................... SUCCESS [3.002s]
[INFO] ------------------------------------------------------------------------
[INFO] BUILD SUCCESS
[INFO] ------------------------------------------------------------------------
[INFO] Total time: 21.949s
[INFO] Finished at: Thu Sep 29 09:21:01 CEST 2016
[INFO] Final Memory: 29M/257M
[INFO] ------------------------------------------------------------------------
```

### Install

You can now install the badge generator somewhere.

For unix:
```shell
$ unzip <badge-generator-dir>/interactive/target/interactive-1.0-SNAPSHOT-unix.zip 
```
For windows:
```shell
$ unzip <badge-generator-dir>/interactive/target/interactive-1.0-SNAPSHOT-windows.zip 
```

### Generate

Then you have to create or copy a badge template. A DevFest Toulouse is availale in the `document/2016` directory.

```shell
$ ./bin/attendee.sh
usage: attendee
 badge ...
$ ./bin/attendee.sh badge 
usage: attendee badge
 -bw <arg>        Billet Web CSV Attendee repository
 -o <arg>         Output directory
 -svg_pdf <arg>   SVG template modelisation and PDF generation
 ```

That's all and have fun.

# LICENSE

Project is published under the MIT license Feel free to clone and modify repo
as you want, but don't forget to add reference to authors.
