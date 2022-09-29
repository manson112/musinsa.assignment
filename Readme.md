# 무신사 글로벌개발팀 Backend 과제

## 실행방법

- Amazon Linux release 2 (Karoo) 및 MacOS Monterey version 12.4 에서 확인되었습니다.
- java 11 설치를 위해 yum 이 필요하며, 설치되어있는 경우 설치하지 않고 실행합니다.
- java 설치 시 sudo 권한이 필요할 수 있습니다.

### 1. java 11 버전이 설치되지않은 경우
```shell
git clone https://github.com/manson112/musinsa.assignment.git
cd musinsa.assignment

chmod +x start.sh

./start.sh
```

### 2. java 11 버전이 설치되어 있는 경우
```shell
git clone https://github.com/manson112/musinsa.assignment.git
cd musinsa.assignment

./gradlew build bootrun

혹은

chmod +x start.sh
./start.sh
```

## 기술 스택
<div align="left">
<img src="https://img.shields.io/badge/java 11-007396?style=for-the-badge&logo=java&logoColor=white"/>
<img src="https://img.shields.io/badge/springboot 2.7.4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
<img src="https://img.shields.io/badge/gradle 7.5-02303A?style=for-the-badge&logo=gradle&logoColor=white"/>
<img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white"/>
<img src="https://img.shields.io/badge/mybatis-red?style=for-the-badge&logo=&logoColor=white"/>
<img src="https://img.shields.io/badge/h2-yellow?style=for-the-badge&logo=&logoColor=white"/>
<img src="https://img.shields.io/badge/mockito-brightgreen?style=for-the-badge&logo=&logoColor=white"/>
</div>