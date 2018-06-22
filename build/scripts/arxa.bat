@if "%DEBUG%" == "" @echo off
@rem ##########################################################################
@rem
@rem  arxa startup script for Windows
@rem
@rem ##########################################################################

@rem Set local scope for the variables with windows NT shell
if "%OS%"=="Windows_NT" setlocal

set DIRNAME=%~dp0
if "%DIRNAME%" == "" set DIRNAME=.
set APP_BASE_NAME=%~n0
set APP_HOME=%DIRNAME%..

@rem Add default JVM options here. You can also use JAVA_OPTS and ARXA_OPTS to pass JVM options to this script.
set DEFAULT_JVM_OPTS=

@rem Find java.exe
if defined JAVA_HOME goto findJavaFromJavaHome

set JAVA_EXE=java.exe
%JAVA_EXE% -version >NUL 2>&1
if "%ERRORLEVEL%" == "0" goto init

echo.
echo ERROR: JAVA_HOME is not set and no 'java' command could be found in your PATH.
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:findJavaFromJavaHome
set JAVA_HOME=%JAVA_HOME:"=%
set JAVA_EXE=%JAVA_HOME%/bin/java.exe

if exist "%JAVA_EXE%" goto init

echo.
echo ERROR: JAVA_HOME is set to an invalid directory: %JAVA_HOME%
echo.
echo Please set the JAVA_HOME variable in your environment to match the
echo location of your Java installation.

goto fail

:init
@rem Get command-line arguments, handling Windows variants

if not "%OS%" == "Windows_NT" goto win9xME_args

:win9xME_args
@rem Slurp the command line arguments.
set CMD_LINE_ARGS=
set _SKIP=2

:win9xME_args_slurp
if "x%~1" == "x" goto execute

set CMD_LINE_ARGS=%*

:execute
@rem Setup the command line

set CLASSPATH=%APP_HOME%\lib\arxa.jar;%APP_HOME%\lib\opencv-320.jar;%APP_HOME%\lib\spark-core-2.7.2.jar;%APP_HOME%\lib\groovy-all-2.4.15.jar;%APP_HOME%\lib\jetty-webapp-9.4.8.v20171121.jar;%APP_HOME%\lib\websocket-server-9.4.8.v20171121.jar;%APP_HOME%\lib\jetty-servlet-9.4.8.v20171121.jar;%APP_HOME%\lib\jetty-security-9.4.8.v20171121.jar;%APP_HOME%\lib\jetty-server-9.4.8.v20171121.jar;%APP_HOME%\lib\websocket-servlet-9.4.8.v20171121.jar;%APP_HOME%\lib\javax.servlet-api-4.0.1.jar;%APP_HOME%\lib\slf4j-simple-1.7.25.jar;%APP_HOME%\lib\slf4j-api-1.7.25.jar;%APP_HOME%\lib\websocket-client-9.4.8.v20171121.jar;%APP_HOME%\lib\jetty-client-9.4.8.v20171121.jar;%APP_HOME%\lib\jetty-http-9.4.8.v20171121.jar;%APP_HOME%\lib\websocket-common-9.4.8.v20171121.jar;%APP_HOME%\lib\jetty-io-9.4.8.v20171121.jar;%APP_HOME%\lib\jetty-xml-9.4.8.v20171121.jar;%APP_HOME%\lib\websocket-api-9.4.8.v20171121.jar;%APP_HOME%\lib\jetty-util-9.4.8.v20171121.jar

@rem Execute arxa
"%JAVA_EXE%" %DEFAULT_JVM_OPTS% %JAVA_OPTS% %ARXA_OPTS%  -classpath "%CLASSPATH%" Server %CMD_LINE_ARGS%

:end
@rem End local scope for the variables with windows NT shell
if "%ERRORLEVEL%"=="0" goto mainEnd

:fail
rem Set variable ARXA_EXIT_CONSOLE if you need the _script_ return code instead of
rem the _cmd.exe /c_ return code!
if  not "" == "%ARXA_EXIT_CONSOLE%" exit 1
exit /b 1

:mainEnd
if "%OS%"=="Windows_NT" endlocal

:omega
