#!/bin/bash

if type -p java; then
    echo found java executable in PATH
    _java=java
elif [[ -n "$JAVA_HOME" ]] && [[ -x "$JAVA_HOME/bin/java" ]];  then
    echo found java executable in JAVA_HOME
    _java="$JAVA_HOME/bin/java"
else
    echo "no java"
    sudo yum install -y java-11-amazon-corretto.x86_64
    ./gradlew build bootrun
fi

if [[ "$_java" ]]; then
  java -version 2>&1 \

    version=$("$_java" -version 2>&1  | head -1 | cut -d'"' -f2 | sed 's/^1\.//' | cut -d'.' -f1)
    echo version "$version"
    if [[ "$version" -eq "11" ]]; then
        ./gradlew build bootrun
    else
        sudo yum install -y java-11-amazon-corretto.x86_64
        ./gradlw build bootrun
    fi
fi