# 무신사 글로벌개발팀 Backend 과제

## 요구사항

---
- JDK 11

## 실행방법

---
- Amazon Linux release 2 (Karoo) 에서 확인되었습니다.
- java 11 설치를 위해 yum 이 필요하며, java 11이 설치되어있는 경우 설치하지 않고 실행합니다.
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

chmod +x start.sh
./start.sh

혹은

./gradlew clean bootrun
```

### 3. 테스트
```shell
./gradlew test
```

## 기술 스택

---
<div align="left">
<img src="https://img.shields.io/badge/java 11-007396?style=for-the-badge&logo=java&logoColor=white"/>
<img src="https://img.shields.io/badge/springboot 2.7.4-6DB33F?style=for-the-badge&logo=springboot&logoColor=white"/>
<img src="https://img.shields.io/badge/gradle 7.5-02303A?style=for-the-badge&logo=gradle&logoColor=white"/>
<img src="https://img.shields.io/badge/junit5-25A162?style=for-the-badge&logo=junit5&logoColor=white"/>
<img src="https://img.shields.io/badge/mybatis-red?style=for-the-badge&logo=&logoColor=white"/>
<img src="https://img.shields.io/badge/h2-yellow?style=for-the-badge&logo=&logoColor=white"/>
<img src="https://img.shields.io/badge/mockito-brightgreen?style=for-the-badge&logo=&logoColor=white"/>
</div>

## 설명

---
- 메뉴 조회 (단건) (MenuRestController.findById)
- 하위 메뉴 포함 조회 (MenuRestController.findHierarchy)
- 배너 조회 (최상위 메뉴) (MenuRestController.findById, MenuRestController.findHierarchy)
- 메뉴 등록 (MenuRestController.createMenu)
- 메뉴 수정 (MenuRestController.updateMenu)
- 메뉴 삭제 (MenuRestController.deleteMenu)
- 배너 등록 (BannerRestController.createBanner)
- 배너 삭제 (BannerRestController.deleteBanner)


## Swagger
서버 실행 후 
[Swagger Link](http://localhost:8080/swagger-ui/index.html)
로 접속