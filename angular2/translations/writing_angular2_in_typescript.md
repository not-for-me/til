# Angular2에서 Typecript를 사용하는 이유

![Angular2, Typescript 로고](https://raw.githubusercontent.com/not-for-me/til/master/angular2/translations/images/angular2_typescript_logo.png)

Angular 2는 Typescript로 작성된 코드이다. 이 글에서 나는 Angular2에서 Typescript를 사용하기로 했는지 설명하기로 한다. 더불어 Typescipt의 사용이 코드의 작성과 리팩토링에 어떤 영향을 주는지에 Typescript를 사용하면서 느꼈던 경험들을 함께 나눌것이다.

## 나는 Typescript를 좋아하지만 여러분들은 그럴 필요는 없다.
Angular2는 Typescript로 작성되었지만 여러분도 Angular2 애플리케이션을 Typescript를 사용할 의무는 없다. Angular2 프레임워크는 ES5, ES6와 Dart 언어로도 훌륭하게 작업할 수 있다.


## TypeScript는 훌륭한 도구가 있습니다.
Typescript의 가장 큰 장점은 도구의 지원이다. **Typecript는 높은 수준의 코드 자동생성, 코드 탐색과 리팩토링을 제공한다. 이러한 도구들은 일정 규모 이상의 프로젝트에서는 거의 필수적인 요소이다.** 도구의 지원이 없다는 것은 거의 수정하기 어려운 상태의 코드 베이스에 코드를 수정해야만 하는 두려움을 심는 일이고, 대규모의 리팩토링 시 큰 위험성과 비용을 감수해야한다는 것을 의미한다.

Typescript만이 Javascript로 컴파일할 수 있는 유일한 타입언어는 아니다. 이론적으로 완벽하게 들어맞는 도구를 제공하는 강력한 타입 시스템을 갖춘 다른 언어들도 있다. 그러나 실용적인 관점에서 대부분 컴파일러를 제외하면 아무것도 가지고 있지 않다. **이러한 차이는 Typescript팀은 시작부터 풍성한 개발도구를 만드는 것을 명확한 목표로 갖고 있었기 때문이다.** 그래서 Typescript팀이 에디터에서 타입체크 기능과 코드완성을 제공할 수 있도록 언어를 만든 이유이다. Typecript 개발을 돕는 훌륭한 도구들이 여러 에디터에 즐비한 이유가 궁금했다면, 그 답은 바로 Typescript 언어의 지원때문이다.


Intellisense와 Rename과 같은 기본적인 리팩토링이 안정적으로 지원한다는 사실은 코드를 작성하는 과정과 특별히 리팩토링할 때 매우 큰 영향을 미친다. 비록 측정하기 어렵지만, 이전에 며칠씩 걸렸을 법한 리팩토링 작업이 이제는 하루도 안되는 시간안에 끝나는 것 같다.

TypeScript는 코드 수정의 경험을 극대화해주는 반면, 개발환경 구축은 특히 간단하게 페이지에 ES5스크립트를 떨어뜨리는 기존 방식과 비교할 때 좀 더 복잡해졌다는 측면이 있다. 게다가 Javascript 소스코드를 분석하는 도구류(예를 들면 JSHint)를 사용할 수 없다. 그러나 적절한 대안이 있다.


## TypeScript는 JavaScript를 포함한 확장된 언어이다.
Typescript는 JavaScript를 포함한 확장된 언어이기 때문에, 여러분은 Typescript로 코드를 전확하기 위해 크게 코드를 재작성할 필요가 없다. 여러분은 시간을 고려하여 하나의 모듈씩 점진적으로 전환해도 된다.

하나의 모듈을 가볍게 골라 js 파일을 ts로 바꿔보자. 그리고 점차 하나씩 타입 정보를 추가하자. 작업이 끝나면 순차적으로 다른 모듈을 골라 똑같은 작업을 하면 된다. 일단 전체 코드베이스가 타입정보를 갖추게 되면, 그때 여러분은 Typescript의 컴파일러 설정을 좀 더 엄격하게 조정할 수도 있다.

이러한 과정은 시간이 좀 필요할 수도 있지만 Angular2를 Typescript로 전환했던 과정을 보면 그리 큰 문제가 아니었다. 점진적으로 이러환 과정을 하면서도 우리는 새로운 기능을 개발하면서 버그를 수정하는 일을 지속할 수 있었다.


## TypeScript는 추상화를 명시적으로 지원한다.
A good design is all about well-defined interfaces. And it is much easier to express the idea of an interface in a language that supports them.

For instance, imagine a book-selling application where a purchase can be made by either a registered user through the UI or by an external system through some sort of an API.
