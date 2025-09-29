# Kotlin-spring-migration
기존의 모놀리스 서버를 MSA 형태로 변경

## 학습 목표
모놀리스 서버를 각 도메인 별로 분리하고 같은 동작을 하도록 구현

### 구현 목표
 아래 사항들을 이용하여 분리
- Spring Cloud Gateway
- Eureka Server (with Load Balancing)
- Config Server

> Kafka 대신 API를 이용하여 각 도메인끼리 통신 예정 (불필요하다고 판단하여 Interface를 이용하여 구현 후 실습으로 추가 예정)

