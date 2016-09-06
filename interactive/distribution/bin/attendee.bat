@echo off
setlocal

REM *--------------------------------------------------------------*

set GENERATOR_HOME=%~dp0..

IF "%JAVA_HOME%" == "" (
    set JAVA=java
) ELSE (
    set JAVA="%JAVA_HOME%\bin\java"
)

REM *--------------------------------------------------------------*

set CPATH="%GENERATOR_HOME%\lib\*;"

REM *--------------------------------------------------------------*

set COPTS=-Dgenerator.home=%GENERATOR_HOME%
set GENERATOR_CLASS=gdg.toulouse.command.Attendee

%JAVA% %COPTS% -cp %CPATH% %GENERATOR_CLASS% %*

