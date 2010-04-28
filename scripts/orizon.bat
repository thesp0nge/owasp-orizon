@echo off
rem This is Orizon 1.31 (C) 2006-2010, Paolo Perego <thesp0nge@owasp.org>
rem Script by Federico Casani <f.casani@owasp.org>

rem Set here your JAVA_HOME!!!
rem example: JAVAHOME=%JAVA_HOME%
set JAVAHOME=C:\Programmi\Java\jdk1.6.0_06
echo JAVAHOME=%JAVAHOME%
if not "%JAVAHOME%" == "" goto USE_JAVA

rem Unable to find it
echo JAVAHOME not found! Please set enviroment variable on orizon.bat file.
goto END

:USE_JAVA
set JAVA=%JAVAHOME%\bin\java.exe
echo JAVA=%JAVA%
rem JAVA_PARAMS=-XX:+PrintGCDetails -Xms32m -Xmx512m
set JAVA_PARAMS=-Xms32m -Xmx512m

set WORKING_DIR=%cd%
echo WORKING_DIR=%WORKING_DIR%

if not "%WORKING_DIR%\lib\log4j.jar" == "" goto EXECUTE
echo log4j.jar not found!
goto END
if not "%WORKING_DIR%\lib\jline.jar" == "" goto EXECUTE
echo jline.jar not found!
goto END

:EXECUTE
set TRIMMED_DIR=%WORKING_DIR%\bin
cd %TRIMMED_DIR%
echo TRIMMED_DIR=%TRIMMED_DIR%
set VERSION=1.19

%JAVA% %JAVA_PARAMS% -jar "%WORKING_DIR%\lib\orizon-core-%VERSION%.jar"

:END
cd %WORKING_DIR%
%COMSPEC% /C exit %ERRORLEVEL%