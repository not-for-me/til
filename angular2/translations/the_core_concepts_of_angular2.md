> Angular2 코어 멤버인 [victorsavkin](https://twitter.com/victorsavkin)의 글 "[THE CORE CONCEPTS OF ANGULAR 2](http://victorsavkin.com/post/118372404541/the-core-concepts-of-angular-2)"을 [허락](https://twitter.com/JoeWoojin/status/746147467951931393)을 받고 번역 하였습니다. 번역에 대한 피드백이나 오류는 주저없이 [트윗](https://twitter.com/JoeWoojin)이나 [메일](mailto:jwj0831@gmail.com)로 알려주세요.

> This post is the translation version of the original post "[THE CORE CONCEPTS OF ANGULAR 2](http://victorsavkin.com/post/118372404541/the-core-concepts-of-angular-2)" with allowed to translate by the author [victorsavkin](https://twitter.com/victorsavkin).

# Angular2의 핵심 개념

본 글에서는 Angular2의 핵심 개념인 컴포넌트, 의존성 주입과 바인딩에 대해서 이야기 해보려고 합니다.

## 앱을 만들면서 시작하기

아래 그림과 같이 기술 세미나의 발표자 목록 어플리케이션을 개발하여 여기에 각 연사 정보를 확인하고, 필터링하고, 별점을 주는 기능을 구현해 보기로 합니다.

![어플리케이션 예](https://raw.githubusercontent.com/not-for-me/til/master/angular2/translations/images/app_sample.png)


## 컴포넌트(COMPONENTS)

Angular2 어플리케이션 개발 시에 모든 UI 요소와 화면 및 화면의 동적 효과를 위해서 반드시 일련의 컴포넌트를 정의해야 합니다. Angular2 기반 어플리케이션에서는 언제나 다른 모든 하위 컴포넌트를 포함하는 하나의 최상위 부모 컴포넌트가 존재합니다. 다시 말하면, 모든 Angular2 기반 어플리케이션은 컴포넌트 트리 형태로 표현할 수 있습니다. 본 글의 어플리케이션은 아마도 다음과 같은 형태의 트리를 갖게 될 것입니다.

![컴포넌트의 트리구조](https://raw.githubusercontent.com/not-for-me/til/master/angular2/translations/images/component_tree.png)

여기서 `Application`은 최상위 컴포넌트입니다. `Filters` 컴포넌트는 발표자의 이름을 입력받을 요소와 필터 버튼으로 구성할 수 있습니다. `TalkList`는 위 어플리케이션의 하단에 위치한 발표자 목록에 해당하며 `TalkCmp`는 목록의 요소 컴포넌트가 됩니다.

Angular2에서 컴포넌트를 어떻게 구성하는지 좀 더 깊이 알아보기 위해서 `TalkCmp`를 살펴 봅시다.

TalkCmp.ts:
```typescript
@Component({
  selector: 'talk-cmp',
  directives: [FormattedRating, WatchButton, RateButton],
  templateUrl: 'talk_cmp.html'
})
class TalkCmp {
  @Input() talk: Talk;
  @Output() rate: EventEmitter;
  //...
}
```

talk_cmp.html:
```html
{{talk.title}}
{{talk.speaker}}
<formatted-rating [rating]="talk.rating"></formatted-rating>
<watch-button [talk]="talk"></watch-button>
<rate-button [talk]="talk"></rate-button>
```

### 입/출력 속성

컴포넌트는 각각 입력과 출력 속성을 갖고 있으며 컴포넌트 [데코레이터(`decorator`)](https://github.com/wycats/javascript-decorators) 안에 정의 하거나 속성 데코레이터를 사용하여 정의합니다.

```typescript
...
class TalkCmp {
  @Input() talk: Talk;
  @Output() rate: EventEmitter;
  //...
}
...
```

입력 속성을 통해 컴포넌트 안으로 데이터가 들어오며 반대로 출력 속성을 통해서 데이터가 컴포넌트의 외부로 흐릅니다.

![컴포넌트에서 데이터의 흐름
](https://raw.githubusercontent.com/not-for-me/til/master/angular2/translations/images/data_flow.png)

컴포넌트의 입/출력 속성은 `public` API 입니다. 따라서 어플리케이션 안에서 컴포넌트를 생성하여 정의된 입/출력 속성을 사용하여 데이터를 교환할 수 있습니다.

```typescript
<talk-cmp [talk]="someExp" (rate)="eventHandler($event.rating)"></talk-cmp>
```

입력 속성은 대괄호(`[]`)를 사용하여 선언하며, 출력속성은 소괄호(`()`)를 사용하여 컴포넌트에서 발생한 이벤트를 구독할 수 있게 해줍니다.

컴포넌트를 웹 페이지 안에서 어떻게 렌더링할 지에 대한 정보는 `template` 안에서 작성합니다.

```typescript
@Component({
  selector: 'talk-cmp',
  directives: [FormattedRating, WatchButton, RateButton],
  templateUrl: 'talk_cmp.html'
})
```

위에서 살펴 본 `talk_cmp.html`이 이러한 template에 해당합니다.

```html
{{talk.title}}
{{talk.speaker}}
<formatted-rating [rating]="talk.rating"></formatted-rating>
<watch-button [talk]="talk"></watch-button>
<rate-button [talk]="talk"></rate-button>
```

Angular에서 `template`을 렌더링하기 위해서는 반드시 두 가지 정보가 필요합니다. 첫 번째는 `template` 안에서 사용할 수 있는 지시자(`directive`)의 목록이고, 두번째는 `template`의 내용입니다. `template`은 위의 `talk_cmp.html`의 예와 같이 `templateUrl`을 통해서 외부에 정의할 수도 있으며, 그렇지 않으면 다음과 같이 컴포넌트 안에 직접 정의합니다.

```typescript
...
@Component({
  selector: 'talk-cmp',
  directives: [FormattedRating, WatchButton, RateButton],
  template: `
    {{talk.title}}
    {{talk.speaker}}
    <formatted-rating [rating]="talk.rating"></formatted-rating>
    <watch-button [talk]="talk"></watch-button>
    <rate-button [talk]="talk"></rate-button>
  `
})
...
```

### 생명주기

Angular의 컴포넌트에는 잘 갖추어진 생명주기가 있습니다. (이는 컴포넌트를 둘러 싸고 일어나는 일련의 이벤트를 선언해 둔 인터페이스입니다.) 따라서 특정 생명주기에 접근할 필요가 있을 때 정의된 인터페이스에서 해당 메서드를 구현하여 사용할 수 있게 해줍니다. `TalkCmp`에서는 어떠한 이벤트도 구독하지 않았지만 원한다면 필요한 이벤트에 대하여 컴포넌트 안에서 손쉽게 구독할 수 있습니다. 예를 들어, 아래 컴포넌트는 입력 속성의 변경이 일어날 때마다 `ngOnChanges`를 통해서 변화를 감지할 수 있습니다.

```typescript
@Component({selector: 'cares-about-changes'})
class CareAboutChanges {
  @Input() field1;
  @Input() field2;
  ngOnChanges(changes) {
    //..
  }
}
```

## 공급자(Provider)

컴포넌트는 자신이 사용하거나 혹은 자식 컴포넌트에서 사용할 수도 있는 공급자의 목록을 포함하기도 합니다.

```typescript
@Component({
  selector: 'conf-app',
  providers: [ConfAppBackend, Logger]
})
class TalksApp {
  //...
}

class TalksCmp {
  constructor(backend:ConfAppBackend) {
    //...
  }
}
```

이 예제에서는 최상위 컴포넌트 `TalksApp`에서 `backend`와 `logger` 서비스를 선언하였기에 어플리케이션 내 어느 컴포넌트에서든 이를 사용할 수 있습니다. 예제의 `TalksCmp`는 `backend`를 주입받았습니다. 의존성 주입에 대해서는 두번째 파트에서 다시 설명하기로 하고 지금은 간단하게 컴포넌트가 의존성 주입에 대한 정보를 구성한다고 기억합시다.


### 주인 요소(HOST ELEMENT)

Angular의 컴포넌트를 DOM에서 렌더링될 대상으로 전환하기 위해서 반드시 DOM의 요소와 Angular의 컴포넌트간의 관계를 따져보아야 합니다. Angular의 컴포넌트에 대응하는 DOM의 요소를 우리는 주인 요소라고 부릅니다.

하나의 컴포넌트는 다음와 같은 방식으로 주인 DOM 요소와 상호작용합니다.:
* 컴포넌트는 DOM의 이벤트를 구독할 수 있습니다.
* 컴포넌트는 DOM의 속성을 갱신할 수 있습니다.
* 컴포넌트는 DOM 안에서 자신의 메서드를 호출할 수 있습니다.

예를 들면, 컴포넌트는 `HostListener`를 사용하여 입력 이벤트를 구독하여, 값을 trim하고 이를 필드 안에 저장할 수도 있습니다. 이후 Angular는 저장된 값을 DOM에 갱신해 줍니다.


```typescript
@Component({selector: 'trimmed-input'})
class TrimmedInput {
  @HostBinding() value: string;

  @HostListener("input", "$event.target.value")
  onChange(updatedValue: string) {
    this.value = updatedValue.trim();
  }
}
```

Note, I don’t actually interact with the DOM directly. Angular 2 aims to provide a higher-level API, so the native platform, the DOM, will just reflect the state of the Angular application.

This is useful for a couple of reasons:

* It makes components easier to refactor.
* It allows unit testing most of the behavior of an application without touching the DOM. Such tests are easier to write and understand. In addition, they are significantly faster.
* It allows running Angular applications in a web worker.
* It allows running Angular applications outside of the browser completely, on other platforms, for instance using NativeScript.

Sometimes you just need to interact with the DOM directly. Angular 2 provides such APIs, but our hope is that you will rarely need to use them.

### 컴포넌트는 자기서술적 존재이다.

What I have listed constitutes a component.

A component knows how to interact with its host element.
A component knows how to render itself.
A component configures dependency injection.
A component has a well-defined public API of input and output properties.
All of these make components in Angular 2 self-describing, so they contain all the information needed to instantiate them. This is extremely important.

This means that any component can be bootstrapped as an application. It does not have to be special in any way. Moreover, any component can be loaded into a router outlet. As a result, you can write a component that can be bootstrapped as an application, loaded as a route, or used in some other component directly. This results in less API to learn. And it also makes components more reusable.

### 지시자는 어떻게 되는가?

If you are familiar with Angular 1, you must be wondering “What happened to directives?”.

Actually directives are still here in Angular 2. The component is just the most important type of a directive, but not the only one. A component is a directive with a template. But you can still write decorator-style directives, which do not have templates.

![지시자](https://raw.githubusercontent.com/not-for-me/til/master/angular2/translations/images/directive.png)



### 요약

Components are fundamental building blocks of Angular 2 applications.

They have well-defined inputs and outputs.
They have well-defined lifecycle.
They are self-describing.
