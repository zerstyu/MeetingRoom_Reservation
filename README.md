# MeetingRoom_Reservation

회의실 예약을 위한 애플리케이션

== 기능 ==
- 30분 단위 예약
- 1회성 예약, 주 단위 반복 예약 설정
- 중복 예약 불가
- 다수의 사용자가 동일 트랜잭션 접근 시 먼저 처리되는 1건만 예약

== 개발환경 ==
- Spring boot
- Java8
- JPA(Hibernate)
- HTML5 (Template : Thymeleaf), Bootstrap
- Javascript, Jquery
- H2 Database (In-Memory)
- Gradle
- Junit

== 프로젝트 빌드, 실행 방법 ==

Springboot 환경에서 동작하는 프로젝트이며, Application.java에서 Application을 실행하면 동작한다.
- Front Page
http://localhost:8080/home

- DB Admin Page
http://localhost:8080/console

== 사용 Flow ==
1. http://localhost:8080/home에 접근
2. 예약을 원하는 날짜와 회의실 선택 후 조회
3. 예약이 가능한 시간대가 노출되면 해당 시간을 선택해서 예약명과 함께 저장한다.
필요한 경우에는 주 단위 반복 예약을 할 수 있다. 주 단위 예약은 2회 선택 시 현재 날짜 +7, +14일의 같은 회의실, 같은 시간대를 예약하게 된다.

== 동작방식 ==
1. Springboot Application 실행 시에 회의실과 1년치의 예약 테이블을 생성한다.
2. 웹 브라우저와 WAS와의 통신 방식은 HTTP 통신을 지원한다. (REST API)
조회, 등록 요청 시에 User -> Contoller -> Service -> Repository -> Database 형태로 요청된다. (Spring MVC)
3. 동시성 처리는 @Version을 통해 제공한다. Optimistic Lock을 통해 한 트랜잭션이 먼저 커밋이 되었을 시에, 두번째 커밋은 충돌을 감지하고 롤백을 한다.
4. DB로부터 읽어온 데이터는 Thymeleaf를 통해 정적인 HTML 문서에 동적인 데이터를 넣어준다. (WAS)

== 고려사항 ==
1. 회의실은 확장성을 고려하여 테이블을 분리하였고 예약 테이블과 연관을 맺어주었다.
2. Index는 현재 추가해두지 않았지만  (RoomId, Date), (RoomId, Date, StartTime)를 추가하면 성능에 도움이 될 것이다.
3. 만약 지금보다 훨씬 많은 회의실이 생성된다면 이 방식이 효과적일까?
A) 현재의 방식이라면 1달 분의 경우 24 x 2 x Room 개수 x 30개의 데이터가 생성이 된다. 
- 어차피 회의실은 정적으로 한 번 생성해두면 되기에 개수는 큰 부담이 되지 않는다고 생각하지만, 조회 시 데이터가 굉장히 많아질 수 있기에 필요에 따라 1주일, 1달치의 데이터는 NoSQL 저장소나 DB를 분리하여 성능 향상을 기대할 수 있다.
- NoSQL을 사용한다면 다양한 조회를 할 수 있도록 Elastic Search로 구현하여 지원할 것이다.
- RDBMS를 사용한다면 Master-Slave 구조로 Replication해서 Master DB에 부담을 줄이고 Slave에서 조회 성능을 향상할 것이다.
- 만약 샤딩이나 파티셔닝을 해야할 일이 있다면, Date 단위로 분리하는 것이 가장 좋아보이며, 더 분리한다면 Room과 StartTime 기준으로도 나눌 수 있을 것이다.

4. 만약 어떤 사용자가 24시간 전부를 선택해서 최대 반복 횟수로 예약 선택을 한다면 DB 부하는 괜찮을까?
- 업데이트 시에 DB에 Lock이 걸려 Deadlock 현상이 발생할 것이다. select for update을 사용하지 않고, 모든 데이터를 한 번에 업데이트를 하는 것이 아닌 트랜잭션 자체를 쪼개는 방식을 고려할 것이다. (500건씩 나눠서 업데이트 하는 방식)

5. 만약 회의실이나 예약 테이블 컬럼이 변한다면?
- 데이터가 굉장히 많을 경우에 컬럼 자체를 수정하는 것은 큰 이슈가 있다. 그럴 경우에는 컬럼 추가를 하거나 table을 다시 만드는 것을 고려할 것이다.
