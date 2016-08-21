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
**좋은 설계는 거의 잘 정의된 인터페이스와 등치된다. 그리고 언어가 지원한다면, 인터페이스를 의도한대로 표현하는 것이 훨씬 더 쉬워진다.**

예를 들어, UI나 API와 같은 류의 방식을 통한 외부 시스템에서 등록된 사용자가 구매할 수 있는 도서 판매 애플리케이션을 가정해 보자.

![Angular2, Typescript 로고](https://raw.githubusercontent.com/not-for-me/til/master/angular2/translations/images/module_diagram.png)

여러분이 보는 것과 같이, 두 클래스는 모두 구매자의 역할을 한다. 구매자의 개념이 이 애플리케이션에서 매우 중요한 부분임에도 불구하고 코드로 명확하게 표현되지 않는다. `purchase.js`라는 이름의 파일도 없다. 결과적으로 누군가가 코드를 수정할 때 이러한 역할이 있다는 사실을 놓칠 가능성을 내포하게 된다.

```JavaScript
function processPurchase(purchaser, details){ }

class User { }

class ExternalSystem { }
```

위의 코드만 들여다 봐서는 어떤 객체가 구매자의 역할을 하고, 이 역할을 포함하는 메서드가 무엇인지 논하기가 쉽지 않다. 우리는 확실하게 알 수도없고 도구의 지원을 받아도 많은 도움을 얻을 길이 없다. 우리는 이러한 정보를 매우 느리고 오류를 일으키시 쉽지만 수동으로 추론해야만 한다.

자, 이번에는 `Purchaser`라는 인터페이스를 명시적으로 정의한 버전과 이전 버전의 코드를 비교해 보자.

```Typescript
interface Purchaser {id: int; bankAccount: Account;}

class User implements Purchaser {}

class ExternalSystem implements Purchaser {}
```

타입정보가 포함된 버전은 명확하게 `Purchaser`인터페이스를 갖고 있고 `User`와 `ExternalSystem` 클래스가 이를 구현하고 있음을 알 수 있다. 따라서 Typescript의 인터페이스는 추상화, 프로토콜, 역할의 정의를 가능하게 한다.

**Typescript가 추상화 정보를 추가로 포함하도록 강제하는 것이 아니라는 사실을 인지하는 것이 중요하다.** `Purchaser`의 추상화된 정보는 명시적으로 정의되지 않았을 뿐 Javascript버전의 코드에서도 포함되어 있다.

**정적타입 언어에서는 하위시스템 간의 경계가 인터페이스를 통해 정의된다. Javascript는 인터페이스가 없으므로, 순수한 Javascript로는 경계가 잘 표현되지 않는다. 명확하게 경계를 확인하기 어려운 경우에 개발자들은 추상화된 인터페이스가 아닌 구상타입에 의존하기 시작하고 이는 강력한 커플링으로 귀결된다.**

나는 Typesciprt를 적용하기 전과 전환된 후의 Angular2를 모두 사용하면서 이러한 믿음이 더 강화되었다. 인터페이스를 정의하는 작업은 내게 API의 경계를 고민하도록 강제하고, 하위시스템의 퍼블릭 인터페이스를 정의하도록 도우며 부수적으로 일어나는 커플링을 드러나게 한다.


## TypeScript는 코드를 읽기 쉽게하며 이해를 돕는다.
그렇다. 나는 이 명제가 직관적이지 않다는 것을 알고 있다. 내 의견을 예제를 통해서 좀 더 상세하게 설명해 보겠다. `jQuery.ajax()` 함수를 살펴보자. 다음의 시그니쳐에서 우리는 어떤 종류의 정보를 얻을 수 있는가?

```JavaScript
jQuery.ajax(url, settings)
```

우리가 확실하게 말 할 수 있는 사실은 이 함수가 두가지 인수를 취한다는 것이다. 우리는 타입을 추론해야한다. 아마도, 첫번째 인수가 `string`이고 두번째는 설정을 위한 객체라고 가정일 것이다. 그러나 이는 단지 추론에 불과하며 틀릴 수도 있다. 우리는 어떤 옵션을 설정객체(설정정보의 이름이나 타입)에 넣어야 하는지, 이 함수가 무엇을 반환하는지 아무런 정보를 가지고 있지 않다.

소스코드를 확인하거나 문서를 보지 않는 이상 우리는 이 함수를 호출할 방법이 없다. 소스코드를 확인하는 것은 좋은 선택은 아니다. 왜냐하면 함수와 클래스를 사용한다는 것의 핵심은 어떻게 구현되었는지를 알지 않고도 이를 사용할 수 있어야 한다는 점이다. 다시 말하면, 우리는 구현체가 아닌 인터페이스를 활용해야 한다는 점이다. 우리는 문서를 확인할 수도 있지만 이 역시 개발자에게 유익한 경험은 아니다. 왜냐하면 이는 추가적인 시간을 소모하게 되고 때론 문서 자체가 옛 버전일 수 있기 때문이다.

`jQuery.ajax(url, settings)`이 읽는데 어려움이 없다고 하더라도, 정말로 이 함수를 어떻게 호출되는지 이해하려면 우리는 구현된 소스를 읽거나 문서를 봐야만 한다.

이제, 타입정보를 포함한 코드와 대조해 보자.

```Typescript
ajax(url: string, settings?: JQueryAjaxSettings): JQueryXHR;

interface JQueryAjaxSettings {
  async?: boolean;
  cache?: boolean;
  contentType?: any;
  headers?: { [key: string]: any; };
  //...
}

interface JQueryXHR {
  responseJSON?: any; //...
}
```

이 코드는 좀 더 많은 정보를 제공해 준다.

* 이 함수의 첫번째 인수는 `string`이다.
* 설정객체 인수는 선택사항이다. 우리는 이 함수에 전달된 옵션을 설정이름 뿐 아니라 타입까지 모두 확인할 수 있다.
* 함수는 `JQueryXHR`객체를 반환하고, 우리는 이 객체의 속성과 함수도 알 수 있다.

타입 정보가 더해진 코드는 분명히 타입이 없을 때 보다 길어졌지만, `:string`, `:JQueryAjaxSettings`, `JQueryXHR`는 결코 혼란을 주는 불필요한 정보가 아니다. 이는 코드의 이해도를 높여주는 중요한 문서이다. 우리는 코드의 구현체나 문서를 읽지 않아도 좀 더 깊은 수준으로 코드를 이해할 수 있다. 내 개인적인 경험으로 비추어 보면 타입정보가 기술된 코드가 좀 더 많은 맥락을 제공하기 때문에 코드를 읽는 속도가 더 빠르다. 누구라도 타입정보가 코드의 가독성에 어떤 영향을 주는지에 대한 연구를 찾았다는 댓글로 알려주기를 부탁한다.

Typescript가 Javascript로 컴파일되는 다른 여러 언어와 비교할 때의 한가지 차이점은 Typescript에서 타입정보는 선택사항이라는 점이다. 따라서 `jQuery.ajax(url, settings)`는 Typescript에서 여전히 유효한 코드이다. 그러므로 Typescript에서 타입정보는 온오프 스위치라기 보다는 다이얼에 더 가깝다.(역주: 필요에 따라 선택할 수 있는 버튼과 같다는 의미) 여러분이 봤을 때 타입정보가 읽고 이해하는데 진부하게 느껴지면 타입을 기술하지 않아도 되며, 타입이 필요한 때 타입 저보를 포함하면 된다.

## TypeScript가 언어의 표현성을 제한하는가?
Dynamically-typed languages have inferior tooling, but they are more malleable and expressive. I think using TypeScript makes your code more rigid, but to a much lesser degree than people think. Let me show you what I mean. Let’s say I use ImmutableJS to define the Person record.


```Typescript
const PersonRecord = Record({name:null, age:null});

function createPerson(name, age) {
  return new PersonRecord({name, age});
}

const p = createPerson("Jim", 44);

expect(p.name).toEqual("Jim");
```

How do we type the record? Let’s start with defining an interface called Person:


```Typescript
interface Person { name: string, age: number };
```

If we try to do the following:

```Typescript
function createPerson(name: string, age: number): Person {
  return new PersonRecord({name, age});
}
```

the TypeScript compiler will complain. It does not know that PersonRecord is actually compatible with Person because PersonRecord is created reflectively. Some of you with the FP background are probably saying: “If only TypeScript had dependent types!” But it does not. TypeScript’s type system is not the most advanced one. But its goal is different. It is not here to prove that the program is 100% correct. It is about giving you more information and enable greater tooling. So it is OK to take shortcuts when the type system is not flexible enough. So we can just cast the created record, as follows:

```Typescript
function createPerson(name: string, age: number): Person {
  return <any>new PersonRecord({name, age});
}
```

The typed example:

```Typescript
interface Person { name: string, age: number };

const PersonRecord = Record({name:null, age:null});

function createPerson(name: string, age: number): Person {
  return <any>new PersonRecord({name, age});
}

const p = createPerson("Jim", 44);

expect(p.name).toEqual("Jim");
```

The reason why it works is because the type system is structural. As long as the created object has the right fields — name and age — we are good.
You need to embrace the mindset that it is OK to take shortcuts when working with TypeScript. Only then you will find using the language enjoyable. For instance, don’t try to add types to some funky metaprogramming code — most likely you won’t be able to express it statically. Type everything around that code, and tell the typechecker to ignore the funky bit. In this case you will not lose a lot of expressiveness, and the bulk of your code will remain toolable and analyzable.
This is similar to trying to get 100% unit test code coverage. Whereas getting 95% is usually not that difficult, getting 100% can be challenging, and may negatively affect the architecture of your application.
The optional type system also preserves the JavaScript development workflow. Large parts of your application’s code base can be “broken”, but you can still run it. TypeScript will keep generating JavaScript, even when the type checker complains. This is extremely useful during development.

## Why TypeScript?
There are a lot of options available to frontend devs today: ES5, ES6 (Babel), TypeScript, Dart, PureScript, Elm, etc.. So why TypeScript?
Let’s start with ES5. ES5 has one significant advantage over TypeScript: it does not require a transpiler. This allows you to keep your build setup simple. You do not need to set up file watchers, transpile code, generate source maps. It just works.
ES6 requires a transpiler, so the build setup will not be much different from TypeScript. But it is a standard, which means that every single editor and build tool either supports ES6 or will support it. This is a weaker argument that it used to be as most editors at this point have excellent TypeScript support.
Elm and PureScript are elegant languages with powerful type systems that can prove a lot more about your program than TypeScript can. The code written in Elm and PureScript can be a lot terser than similar code written in ES5.
Each of these options has pros and cons, but I think TypeScript is in a sweet spot that makes it a great choice for most projects. TypeScript takes 95% of the usefulness of a good statically-typed language and brings it to the JavaScript ecosystem. You still feel like you write ES6: you keep using the same standard library, same third-party libraries, same idioms, and many of the same tools (e.g., Chrome dev tools). It gives you a lot without forcing you out of the JavaScript ecosystem.
