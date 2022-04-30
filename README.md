# springboot-webservice

해당 프로젝트는 이동욱 개발자님의 "스프링 부트와 AWS로 혼자 구현하는 웹 서비스" 책을 읽으며 공부한 클론 프로젝트입니다.

* #### 개발 환경
  * Java8
  * gradle version 4.x
  * Spring Boot 2.4.x
  * IntelliJ Ultimate
  
---

### 학습 구성

#### "스프링 부트와 AWS로 혼자 구현하는 웹 서비스" 책에서는 크게 3가지를 다루고 있습니다.

1. 스프링부트를 사용한 웹어플리케이션 개발의 전반적인 설계 

2. 스프링부트 기반 어플리케이션이 실행될 AWS 인프라 관련 구축

3. CI/CD 

2가지 핵심요소를 중점적으로 배우고 이해한 부분을 작성하겠습니다.

---

### 스프링부트를 사용한 웹어플리케이션 개발의 전반적인 설계 과정

웹 어플리케이션의 기본적인 UI는 부트스트랩을 통해 구성했으며, Mustache 뷰 엔진을 사용했습니다.

#### OAuth2 소셜 로그인

1. Google, Naver 등의 console에 프로젝트를 등록 후 OAuth client-id, secret을 발급
2. application-oauth.properties file 생성 이후 client-id, client-secret 등을 spring-security 등록
3. 각 console에 승인된 URL, Redirection URL을 입력
4. spring-security dependency 추가 - compile('org.springframework.boot:spring-boot-starter-oauth2-client')

#### JUnit을 통한 단위테스트

* 테스트 코드 작성의 장점


> 단위 테스트는 개발단계 초기에 문제를 발견하게 도와준다.
> 
> 단위 테스트는 개발자가 나중에 코드를 리팩토링하거나 라이브러리 업그레이드 등에서 기존 기능이 올바르게 작동하는지 확인할 수 있다.
> 
> 단위 테스트는 기능에 대한 불확실성을 감소시킬 수 있다.
> 
> 단위 테스트는 시스템에 대한 실제 문서를 제공한다. 즉, 단위 테스트 자체가 문서로 사용할 수 있다.

#### Client에 응답,요청할때 DTO를 사용하는 이유

> DB와 직접적으로 연결 된 Entity Class는 절대로 Request/Response 목적으로 사용되면 안된다. 
> Entity Class를 기준으로 테이블이 생성되고, 스키마가 변경된다. 
> 만약 이러한 Entity Class가 뷰에 통해 빈번하게 변경된다면, 로직에 의도하지 않게 많은 변화가 초래될 수 있다.
> 따라서 view Layer와 DB Layer 간 철저하게 분리를 유지하는 것이 좋다!

* 추가적으로 데이터의 join이나 변경등의 목적으로도 DTO가 유용하게 사용된다.


---

### 스프링부트 기반 어플리케이션이 실행될 AWS 인프라 관련 구축


#### EC2 SSH 접속

1. EC2 보안 인바운드 SSH를 내IP로 변경

2. HostName에 ec2-user@EC2 EIP를 입력 후 접속
* ssh -i (.pem 위치) EC2 EIP
  * ~에 .ssh dir을 생성하여 .pem파일을 등록 후 chomod 600 
  * .ssh dir에 config를 생성 후 chomod 700


```
Host ServiceName
    HostName EC2 EIP
    User ec2-user
    IdentityFile ~/.ssh/.pem file
```

3. 이후 ssh (config에 등록한 ServiceName) 으로 접속 가능

`스프링부트 프로젝트 실행을 위한 EC2 Server 내 필수 설정`

* JDK설치 (여기서는 Java 8)

> sudo yum install -y java-1.8.0-openjdk-devel.x86_64
  
* timeZone 변경

> sudo rm /etc/localtime
> 
> sudo ln -s /usr/share/zoneinfo/Asia/Seoul /etc/localtime
> 
> 이후 date로 KST확인
> 
* hostName 변경(여러 서버 관리의 경우 IP만으로 서비스 파악이 힘듬)

> sudo vim /etc/sysconfig/network를 열어 HOSTNAME=원하는 이름을 추가
> 
> sudo vim /etc/hosts에 127.0.0.1 등록한 원하는 Hostname을 등록

## EC2 with RDS


1. RDS 인스턴스 생성 후 timeZone, character Set 변경
  * character Set의 경우 utf8mb4를 보편적으로 많이 사용
2. RDS 보안 그룹에 IP를 추가
  * 보안 그룹 ID와 IP를 RDS 보안 그룹의 인바운드로 추가
3. Database Navigator Plugin 설치 (InteliJ)
4. Host에는 RDS의 엔드 포인트를 등록 (RDS 설정에 보면 엔드포인트 확인가능)
5. User는 RDS생성시 입력한 마스터 사용자 이름(기본 admin)
6. EC2에 MySql 설치 sudo yum install mysql 이후 계정, 비밀번호, host주소를 사용해 RDS접속 
7. 접근 가능 확인


---

### CI/CD

* Travis CI
 * 프로젝트에서 .travis.yml생성
 * Travis CI와 AWS S3, CodeDeploy 연동 (인바운드 규칙 확인 필요)
 * .travis.yml에서 정의한 branch에 push하면 자동으로 배포

### 전체 개발 Flow

![스크린샷 2022-04-30 오후 8 02 25](https://user-images.githubusercontent.com/40168455/166102803-5a249571-9728-4056-866a-9665c60ef1e5.png)

### 무중단 배포 과정 

1. 서버 개발자는 InteliJ 를 사용해 개발을 진행하며, GitHub에서 코드가 관리 . 

2. GitHub master branch에 푸쉬가 이뤄지면 TravisCI를 통한 테스팅 과정 및 빌드가 진행되고 jar 파일을 AWS S3에 저장.(CodeDeploy에 저장 기능이 없기때문) 

3. 이 jar 파일을 통해 AWS CodeDeploy에서는 EC2로 배포를 진행.

4. EC2 에서 설계된 scprit가 실행되며 nginx가 바라보고 있지 않은 포트를 찾고 새로운 버젼의 배포가 실행. 

5. 배포가 정상적으로 실행되었으면 nginx가 바라보는 포트를 새로운 버젼의 포트를 바라보게 스위치 시킴.

