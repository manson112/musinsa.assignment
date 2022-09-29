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
    version=$("$_java" -version 2>&1 | awk -F '"' '/version/ {print $2}')
    echo version "$version"
    if [[ "$version" -gt "11" ]]; then
        ./gradlew build bootrun
    else
        sudm yum install -y java-11-amazon-corretto.x86_64
        ./gradlw build bootrun
    fi
fi