토비의 스프링6 - 이해와 원리

# 오브젝트와 의존관계

## ✔ 자바에서의 오브젝트
- 객체(Object)란 클래스의 인스턴스를 의미하며, 배열도 객체로 간주된다.
- 모든 객체는 힙 메모리에 생성되며 참조 변수를 통해 접근한다.

## ✔ 의존관계 (Dependency)

### 1. 클래스 간 의존관계: 정적, 타입 간 연결, 컴파일 시점에 결정
- **사용(Use)**: 한 클래스가 다른 클래스의 메서드나 필드를 사용하는 경우
- **호출(Call)**: 다른 클래스의 메서드를 호출하여 기능을 실행하는 경우
- **생성(Instantiation)**: `new` 키워드를 통해 다른 클래스의 인스턴스를 생성
- **전송(Passing)**: 메서드 매개변수 등을 통해 객체를 전달
- **상속(Inheritance)**: 부모 클래스의 기능을 상속받아 사용하는 관계

### 2. 객체 간 의존관계: 동적, 인스턴스 간 연결, 실행 시점에 결정
- **런타임 의존**: 실행 중 객체 간 의존이 발생하는 구조
  - **의존성 주입(DI)**: 생성자, 세터, 필드 등을 통해 외부에서 필요한 객체를 주입받는 방식
  - **전략 패턴**: 런타임에 전략 객체를 교체하거나 주입하여 동작을 다르게 구성
  - **인터페이스 기반 설계**: 인터페이스 타입으로 참조하고, 구현체는 실행 시점에 주입됨

## ✔ 관심사의 분리 & 확장성 (Separation of Concerns & Extensibility)
- **메서드 분리**  
  - 하나의 메서드는 하나의 책임만 가지도록 구성
- **클래스 분리**  
  - 서로 다른 역할과 책임을 가진 기능은 별도의 클래스로 분리
- **모듈화**  
  - 관련된 기능은 하나의 모듈로, 독립적인 기능은 다른 모듈로 나누어 구성
- **상속 (Inheritance)**  
  - 공통 기능을 상위 클래스에 정의하여 하위 클래스에서 재사용 및 확장
- **인터페이스 도입**  
  - 구현체를 분리하여 유연한 구조와 다형성 확보
- **합성 (Composition)**  
  - 필요한 기능을 객체로 조합하여 관심사를 분리하고 유연하게 확장 가능

> 관심사의 분리를 통해 각 구성 요소의 역할을 명확히 하고,  
> 상속과 합성 등의 구조적 기법으로 시스템을 유연하게 확장할 수 있도록 설계함.

## ✔ 관계설정 책임의 분리
- **의존관계 설정하는 코드의 분리**
  - 기존에 만들어진 코드는 전혀 건드리지 않고 사용할 주체가 사용할 코드를 생성하여 사용할 수 있게 해야한다.
  - 관심사 분리를 위해 책임을 계속해서 다른 클래스에 넘겨주는 방법을 사용한다.
  - 
## ✔ 관심사 분리의 원칙과 패턴
- **개방폐쇄원칙(Open-Closed Principle)**
  - 클래스나 모듈은 확장에는 열려 있어야 하고 변경에는 닫혀 있어야 한다.
- **높은 응집도와 낮은 결합도(High coherence and low coupling)**
  - 응집도가 높다는 것은 하나의 모듈이 하나의 책임 또는 관심사에 집중되어있다는 뜻.
  - 변화가 일어 날 때 해당 모듈에서 변하는 부분이 크다.
  - 책임과 관심사가 다른 모듈과는 낮은 결합도, 즉 느슨하게 연결된 형태를 유지하는 것이 바람직하다.
- **전략 패턴**
  - 자신의 기능 맥락(context)에서 필요에 따라서 변경이 필요한 알고리즘을 인터페이스를 통해 통째로 외부로 분리시키고,
  - 이를 구현한 구체적인 알고리즘 클래스를 필요에 따라 바꿔서 사용할 수 있게 하는 디자인 패턴.

제어의 역전(Inversion of Control)
- 제어권 이전을 통한 제어관계 역전 - 프레임워크의 기본 동작 원리

스프링이란? 빈팩토리를 이용해서 객체지향설계 원칙이 잘 적용된 애플리케이션을 만드는 것. IoC/DI 컨테이너이다.
