@echo off

:: Startup script for Tinustris.
:: This script assumes that a valid javaw.exe, version 1.7 or later, is on the PATH.

set PWD="%CD%"

set LIB_DIR=%PWD%\..\lib

set CONFIG_DIR=%PWD%\..\etc

set JAVA_OPTS=

set JAVA=javaw

start %JAVA% %JAVA_OPTS% -cp %CONFIG_DIR%;%LIB_DIR%\* nl.mvdr.tinustris.gui.Tinustris