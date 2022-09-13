@echo off
if [%1]==[] goto usage

if exist "%1"\ (
    cd "%1"
    .\mvnw verify
    java -jar .\target\%1-2022.0.0.jar
) else (
    echo error: Folder does not exist
)

goto :eof
:usage
@echo usage: %~n0%~x0 ^<folder\name\^>
