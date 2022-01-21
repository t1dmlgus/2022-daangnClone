# 2022-daangnClone 
넘블 백엔드 챌린지 - 당근 클론 프로젝트


<div align="center" style="display:flex;">
    <img src="https://user-images.githubusercontent.com/59961350/150383175-95ae60e7-b1b0-4a9d-b8fc-43aa4701a3e4.png" width="max" alt="cover"/>
</div>

---
<br>

# 1. 서비스 소개

#### 당큰 마켓을 클론하여 서비스를 구현한다. 
#### 주요 기능 - 회원가입, 로그인, 상품등록, 상품조회, 좋아요, 댓글 기능, 프로필수정, 게시글 수정, 게시글 삭제
---
<br>

# 2. 기술 스택
* Java11
* Spring Boot 2.6.2
* Spring Data JPA
* Spring Security
* Querydsl
* Junit5
* AWS EC2, RDS, S3, CloudFront
* Jenkins, docker
* Thymeleaf

---
<br>

# 3. 프로젝트 아키텍처


![프로젝트 아키텍터01](https://user-images.githubusercontent.com/59961350/144152453-5f732099-8dd4-4bd9-a285-6ec3385b0ef5.PNG)

---
<br>

# 4. 업데이트 내역
* v0.1.1
    * 도메인 모델 설계 및 ERD 
    * 개발 환경 구축
    * 회원 API 구현(회원가입, 회원조회)
    * 회원가입 시 유효성 검사(AOP 이용)
    * 글로벌 예외 핸들러 구현
    * 로깅 설정(log4j:2.17.0)
    * Spring REST Docs 설정(컨트롤러 단위 테스트 문서화)
    * 로그인 구현(UsernamePasswordAuthenticationToken 사용)
    
* v0.1.2
    * 상품 API 구현(상품등록, 상품조회)
    * 상품 등록 시 상품 이미지 S3 버킷 업로드
    * S3, CloudFront 연동
    * 웹 퍼블리싱(인덱스페이지, 회원가입페이지, 로그인페이지, 랜딩페이지, 상품페이지, 전체상품 페이지)
    * 페이지 렌더링
    * github webhook 이용한 Jenkins 연동
    * 1차 배포(EC2, RDS) - 테스트, 빌드, 배포 자동화

---
<br>

# 5. Info
- [서비스 바로가기](http://ec2-3-36-197-4.ap-northeast-2.compute.amazonaws.com:8080)
- [프로젝트 문서 바로가기](https://github.com/t1dmlgus/2022-daangnClone/wiki)
- [InstagramProject version01 바로가기](https://github.com/t1dmlgus/instagram03)
- dmlgusgngl@gmail.com

---
<br>
