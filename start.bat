@echo off
title Galaxy Blitz
echo Iniciando o jogo...

:: Verifica se existe uma JRE embutida na pasta jre/
if exist "jre\bin\java.exe" (
    echo Usando JRE local...
    jre\bin\java -jar GalaxyBlitz.jar
) else (
    echo Usando JRE do sistema...
    java -jar GalaxyBlitz.jar
)

pause
