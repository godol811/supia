# SUPIA UserGuide

### 제작자 : 고종찬, 김보람, 도하진, 박인우, 주혜정

## 1.Build gradle에 추가를 한다.

```java 
    //켈린더 관련
        implementation 'com.prolificinteractive:material-calendarview:1.4.3'
    //카드뷰 관련
        implementation 'com.google.android.material:material:1.2.1'
    //리사이클러뷰 관련
        implementation 'com.android.support:recyclerview-v7:30.0.0'
    //사진 라이브러리 관련
        implementation 'com.github.bumptech.glide:glide:4.11.0'
        annotationProcessor 'com.github.bumptech.glide:compiler:4.11.0'
        implementation 'com.android.volley:volley:1.1.1'
    //바텀시트
        implementation 'com.android.support:design:30.0.0'
        implementation 'com.google.android.material:material:1.2.1'
    //탭 메뉴
        implementation 'com.google.android.material:material:1.3.0-beta01'
        implementation 'androidx.lifecycle:lifecycle-livedata-ktx:2.2.0'
        implementation 'androidx.lifecycle:lifecycle-viewmodel-ktx:2.2.0'
    //카카오 로그인 관련
        implementation "com.kakao.sdk:v2-user:2.2.0" // 카카오 로그인
        implementation "com.kakao.sdk:v2-talk:2.2.0" // 친구, 메시지(카카오톡)
        implementation "com.kakao.sdk:v2-story:2.2.0" // 카카오스토리
        implementation "com.kakao.sdk:v2-link:2.2.0" // 메시지(카카오링크)
        implementation "com.kakao.sdk:v2-navi:2.2.0" // 카카오내비
    //구글 로그인 관련
        implementation 'com.google.android.gms:play-services-auth:19.0.0'//구글로그인 모듈

    //인증 이메일 관련
        implementation files('libs/activation.jar')
        implementation files('libs/additionnal.jar')
        implementation files('libs/mail.jar')
```
---

## 2.Andorid에 권한을 추가한다.

1.Manifest에 아래코드를 추가한다.
```xml
        <uses-permission android:name="android.permission.INTERNET" />
        <uses-permission android:name="android.permission.ACCESS_NETWORK_STATE" />
        <uses-permission android:name="android.permission.WRITE_EXTERNAL_STORAGE" />
        <uses-permission android:name="android.permission.READ_EXTERNAL_STORAGE" />

        <application.....
            android:networkSecurityConfig="@xml/network_security_config">

        <uses-library android:name="org.apache.http.legacy"
            android:required="false"/>
```
2.Tomcat 서버와 연동하기 위해 src폴더안에 /xml 폴더를 만든다.

이후 xml 폴더안에 network_security_config.xml 이라는 XML Resource File 을 생성한다.

Network_security_config.xml 안에 이 코드를 삽입한다.
```xml
    <?xml version="1.0" encoding="utf-8"?>
    <network-security-config>
    <base-config cleartextTrafficPermitted="true">
            <trust-anchors>
                <certificates src="system"/>
            </trust-anchors>
        </base-config>
    </network-security-config>
```
---

## 3.Tomcat에 연동할 jsp 파일을 폴더에 넣는다.

방법은 tomcat 라이브러리 폴더의 /webapps/ROOT/안에 넣으면 된다.

Link: [JSP파일 Link][jsp link]

[jsp link]: https://github.com/AndroidMnS/supiaDocument/tree/main/jsp

---

## 4.Gmail 인증 설정.


1. 프로젝트 내부 libs 폴더에 아래 3개 jar 파일 추가

+ activation.jar
+ additionnal.jar
+ mail.jar  
  -  ->Link: [Gmail Library][jsp link]

[jsp link]: https://github.com/AndroidMnS/supiaDocument/tree/main/libs





1. FindPWActivity에서 사용할 Gmail 및 비밀번호 기입


2. 사용할 Gmail 내 보안 > 보안 수준이 낮은 앱의 액세스 허용 설정



---

## 4.Tomcat에 제품 사진을 넣기 위해 pictures 폴더를 만든다.

방법은 tomcat 라이브러리 폴더의 /webapps/ROOT/안에 pictures폴더를 생성하면 된다.

---

## 5.Tomcat lib 에 MYSQL 라이브러리를 넣는다

방법은 connector를 받아 tomcat 라이브러리 폴더의 /lib 에 넣어주면 된다.

참고) windowOS는 /webapps/ROOT/WEB-INF에 cos.jar를 넣어주면 된다. MYSQL Connector는 이전과 동일하게 /lib에 넣어주면 된다.

Link: [MYSQL Connector][my sql connector]

[my sql connector]: https://github.com/AndroidMammamia/MammamiaDocument/tree/main/databaseConnector

---

## 6.test 폴더 안에 jsp MYSQL 데이터 베이스의 주소를 수정하도록한다.

이를테면

```java
    String stSearch =  request.getParameter("addrName");

    String url_mysql = "jdbc:mysql://이부분에 데이터베이스주소를 수정하세요/MYSQL스키마이름?serverTimezone=Asia/Seoul&characterEncoding=utf8&useSSL=false";

    String id_mysql = "아이디";

    String pw_mysql = "암호";
```

한글로 적혀있는 부분은 각자 환경에 맞게 채우면 된다.

---
## 7.데이터 베이스와 Tomcat 서버를 연동하기 위해 안드로이드 내부의 IP를 수정한다.

방법은 project 내부의 com.example.supia.ShareVar package를 보면 ShareVar.class가 있다

그 부분에서 

```java
 public static String urlIp = "172.20.10.3"; 

```
이부분에 Ip 주소를 본인의 Ip 주소로 변경한다.


---
## 8.SQL Table은 다음과 같다.

| user        | product    | calendar     | cart     | liked  | orderlist |userDeliveryAddrList              | qna     | review | subscribeOrder 
| ------------- | ----------- | ----------- | ------------ | ----------- | ---------------------- | ----------- | ------------- |----------- |----------- |
| 사용자 정보 관련 | 제품 정보 관련 | 사용자 생리주기,배송날짜 관련 | 장바구니 관련 | 찜 목록 관련 | 일반주문 관련| 구독주문 관련 | 제품 QnA 관련 | 제품 리뷰 관련   |구매자 배송 리스트 관련

---

## 9.실행 영상.

썸네일을 누르면 Youtube 페이지로 이동
[![](http://img.youtube.com/vi/unRgWQw08sE/0.jpg)](http://www.youtube.com/watch?v=unRgWQw08sE "")


## 개발 개요

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.001.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.002.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.003.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.004.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.005.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.006.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.007.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.008.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.009.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.0010.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.011.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.012.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.013.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.014.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.015.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.016.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.017.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.018.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.019.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.020.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.021.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.022.jpeg)

![](https://github.com/AndroidMnS/supiaDocument/blob/main/android_supia_projectReport/%E1%84%8B%E1%85%A1%E1%86%AB%E1%84%83%E1%85%B3%E1%84%85%E1%85%A9%E1%84%8B%E1%85%B5%E1%84%83%E1%85%B3_%E1%84%89%E1%85%AE%E1%84%91%E1%85%B5%E1%84%8B%E1%85%A1.023.jpeg)

