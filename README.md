<!-- ai를 참고해서 작성했습니다. -->
# 프로젝트 개요

이 프로젝트는 공공데이터포털(에어코리아)의 실시간 대기질 데이터를 가져와 백엔드에서 캐시하고, 프론트엔드에서 시각화하는 샘플 애플리케이션입니다.

- 백엔드: Java 17, Spring Boot, MyBatis, H2 (인메모리 DB)
- 프론트엔드: React, Axios, Chart.js
- 외부 데이터: 공공데이터포털(한국환경공단_에어코리아_대기오염정보)

---

## 기술 스택

Backend
- Language: Java 17
- Framework: Spring Boot 3.x
- Persistence: MyBatis
- Database: H2 (In-Memory)
- HTTP Client: RestTemplate
- Build Tool: Maven

Frontend
- Library: React
- HTTP Client: Axios
- Visualization: Chart.js (react-chartjs-2)
- Styling: CSS

---

## 전체 워크플로우

1. 사용자가 프론트엔드에서 측정소를 선택합니다.
2. 프론트엔드는 `GET /api/air/{station}` 요청으로 백엔드에 대기질 데이터를 요청합니다.
3. 백엔드는 공공데이터포털 API에 조회 요청을 보냅니다.
4. 수신한 JSON을 파싱하여 `AIR_DATA` 테이블(H2)에 저장(기존 데이터 초기화 후 저장)합니다.
5. 저장된 데이터를 클라이언트에 반환합니다.
6. 프론트엔드는 데이터로 차트를 생성하고 화면에 표시합니다.

---

## 프로젝트 구조 (요약)

- back/: Spring Boot 백엔드 소스
  - `src/main/java` - Controller, Service, Mapper, DTO
  - `src/main/resources` - `application.properties`, `schema.sql`
- front/: React 프론트엔드 소스
  - `src/components` - 차트 컴포넌트
  - `src/hooks` - 데이터 로직 훅
  - `src/api` - API 호출 로직
  - `public` - 정적 파일

---

## 실행 방법

**사전 요구사항**
- Java 17 이상
- Maven (프로젝트에는 Maven Wrapper 포함)
- Node.js 및 npm

1) API 키 설정 (권장: 환경변수 또는 .env 사용)

- 권장: 공공데이터포털에서 발급받은 Service Key를 환경변수로 설정하거나 프로젝트 루트의 `.env` 파일에 저장하세요.

예시 `.env` (백엔드 루트에 생성):

```
# 백엔드 환경 변수
API_KEY=여기에발급받은ServiceKey를넣으세요
API_URL=http://apis.data.go.kr/B552584/ArpltnInforInqireSvc/getMsrstnAcctoRltmMesureDnsty
```

2) 백엔드 실행

Windows:
```
cd back
mvnw.cmd spring-boot:run
```
Mac/Linux:
```
cd back
./mvnw spring-boot:run
```
서버 기본 포트: `http://localhost:8080`

3) 프론트엔드 실행

```
cd front
npm install
npm start
```
프론트엔드 기본 포트: `http://localhost:3000`

---

## API 예시

- 엔드포인트: `GET /api/air/{station}`
- 설명: 관측소 이름(`{station}`)을 넣어 호출하면 최근 24시간 데이터를 DB에 저장 후 JSON 배열을 반환합니다.

예시 호출:
```
curl http://localhost:8080/api/air/종로구
```

응답 예시 (요약):
```
[
  {
    "id": null,
    "station": "종로구",
    "time": "2025-12-30 12:00",
    "pm10": 15,
    "pm25": 7
  },
  ...
]
```

---

## H2 DB 및 콘솔

- H2 콘솔: `http://localhost:8080/h2-console`
  - JDBC URL: `jdbc:h2:mem:testdb`
  - 사용자: `sa` (비밀번호 없음)
- DB 초기 스키마: `back/src/main/resources/schema.sql` (`AIR_DATA` 테이블)

---

## 테스트

- 백엔드 테스트:
```
cd back
mvnw.cmd test
```
- 프론트엔드 테스트:
```
cd front
npm test
```

---

## 이미지
- flaticon 사용

