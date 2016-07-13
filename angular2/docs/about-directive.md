# 지시자 (Directive)

## 지시자에 대하여

오늘 다룰 개념은 Angularjs를 사용해 보신 분들은 적어도 한 번쯤은 들어보았을 지시자(Directive)입니다. 지시자에 대한 설명을 먼저 단어의 의미 에서부터 출발해 보기로 합니다. 지시자 본래의 단어 Directive를 네이버에서 검색할 때 제일 처음 나오는 [사전적 정의](http://endic.naver.com/enkrEntry.nhn?sLn=kr&entryId=699828d2bdf24c34bacde1c12b0691e9&query=directive)는 다음과 같습니다.

> (공식적인) 지시[명령]
> A directive is an official instruction that is given by someone in authority.

마찬가지로 네이버 한국어 사전에서 ["지시"의 의미](http://krdic.naver.com/detail.nhn?docid=35720700)를 찾아보면 1. 가리켜 보임, 2. 일러서 시킴. 또는 그 내용 등으로 나오는데 우리가 관심 갖는 의미는 2번째에 해당된다고 볼 수 있겠네요.

정리하자면, 어휘의 의미에서 접근했을 때 `지시자`라는 개념은 "어떠한 대상에게 일련의 명령을 수행하게 함" 정도로 다듬어서 정의할 수 있습니다. 그리고 이 의미는 Angular 프레임워크라는 맥락에서도 역시 동일합니다. Angular 프레임워크에서 의미하는 지시자는 "DOM을 다루기 위한 모든 것"이라 정의할 수 있습니다.

그럼 바로 다음의 예를 통해서 조금 더 익숙한 곳에서 Angular에서 지시자의 역할을 살펴보기로 합니다.

```typescript
import {Component} from '@angular/core';

@Component({
    selector: 'product-item',
    template: `<div>
      <span>Product Item: </span> {{item.name}}
    </div>`
})
export class ProductItemComponent {
  @Input() item: any;
}
```

지시자의 예제를 기대했는데 이제는 낯설지 않은 Component를 구현한 예를 보여드렸습니다. 지시자의 예로서 Component의 소스를 보여드리는 이유는 Component 역시 지시자이기 때문입니다. 위에서 정의한 지시자의 정의와 Component가 하는 역할을 생각하면 Component가 왜 지시자인지 이해할 수 있습니다. Component 또한 화면의 렌더링과 사용자의 이벤트 처리 등 DOM을 다루기 때문에 지시자입니다.

Component는 Angular에서 중요한 컨셉 중 하나이기 때문에 지시자의 일부로 다루기 보다는 독립적으로 구분해서 보는 이해하는 것이 좋습니다. 따라서 앞으로 지시자를 이야기 할 때 넓은 의미로는 Component를 포함하지만 협소한 의미로 이야기할 때는 Component를 제외하고 이야기 하도록 합니다.

그렇다면 좁은 의미로 볼 때, 지시자에는 어떤 것들이 있을까요? 구조 지시자(Structural directive)와 속성 지시자(Attribute directive)가 있습니다. 이 지시자들은 HTML의 태그 안에 속성에 선언하여 사용합니다. 세부적으로 각각 어떤 역할을 하는지 확인해 봅시다.

## 구조 지시자(Structural Directive)

구조 지시자는 DOM 요소를 추가하거나 삭제하는 등 화면의 구조를 변경할 때 사용하는 지시자입니다. 대표적인 구조 지시자에는 `ngIf`, `ngFor`가 있습니다. `ngIf` 지시자는 불리언 값을 입력 받아 true일 경우 `ngIf`가 선언된 DOM을 보여주고 false이면 제거해주는 용도의 지시자입니다. 반면 `ngFor`는 배열 형태의 모델을 반복해서 DOM에 표현할 때 사용하는 지시자입니다. 아래 공식매뉴얼의 간단한 [예제](https://angular.io/docs/ts/latest/guide/architecture.html#!#directives)를 살펴봅시다.

```html
<li *ngFor="let hero of heroes"></li>
<hero-detail *ngIf="selectedHero"></hero-detail>
```

설명한 정의와 같이 구조 지시자는 DOM을 직접 조작하는데 사용하는 지시작입니다. angular에서 제공하는 기본 구조 지시자로도 어플리케이션 개발이 충분하지만, 필요에 따라 자신만의 구조지시자를 만들수도 있습니다. 이에 대해서는 나중에 다시 다루어 보도록 합니다.

>
angularjs에서는 지시자는 태그명, 속성, 클래스명, 주석까지 총 4가지로 표현되어 사용할 수 있었습니다. angularjs의 태그명을 사용한 지시자가 angular에서는 Component에 해당하고, angularjs의 속성 지시자가 angular에서는 협의의 의미의 지시자에 대응한다고 볼 수 있습니다.

## 속성 지시자(Attribute Directive)

속성지시자는 지시자가 선언된 DOM의 모습이나 작동을 조작하는데 사용하는 지시자입니다. 조작할 DOM에 미리 정의한 속성 지시자를 마킹하듯이 태그 안에 속성으로 선언하면 해당 DOM이 지시작에서 정의한 대로 동작하게 됩니다. 다음의 예를 한 번 봅시다.

```typescript
import {Directive, ElementRef, Renderer} from "@angular/core";

@Directive({
  seletor: '[my-directive]'
})
export class MyDirective {
  constructor(el: ElementRef, renderer: Renderer) {
    renderer.setElementClass(el.nativeElement, 'my-new-class', true);
  }
}
```

이 예제는 DOM요소의 class에 'my-new-class'를 추가하는 'my-selector'라는 속성지시자를 선언한 것입니다. 따라서 필요에 따라 'my-new-class'란 클래스를 추가할 DOM에 다음과 같이 'my-directive'를 속성 중 하나로 선언해 주면 됩니다.

```html
<label my-directive>테스트 속성 지시자</label>
```

속성 지시자는 명칭에서 알 수 있듯이 DOM의 속성과도 직접 연관된 지시자입니다. 장황한 설명보다 다음의 예제가 그 역할을 충분히 설명할 수 있습니다.

```html
<span [style.color]="red">속성 지시자</span>
<input type="checkbox" [checked]="isChecked" />
<input type="text" [disabled]="isDisabled" />
```

예제를 보면 `[]`를 사용하여 단방향 바인딩으로 우변의 모델 값을 직접 반영하고 있다는 것을 알 수 있습니다. 예제와 같이 기존 DOM의 속성에 접근하기 위한 용도에도 속성지시자가 사용되며 지난 시간에 설명한 바인딩도 속성 지시자의 일부가 됩니다.

## Component와 지시자의 관계

오늘 소개했던 지시자와 Component는 Angular 프레임워크의 기둥과도 같은 중요한 개념입니다. 위에서 설명한 바와 같이 사실 Component도 지시자의 이야기 했습니다. 실제로 `@Component` 데코레이터는 `@Directive`에 `template`가 포함된 확장된 형태로 구현되어 있습니다. 넓은 의미의 지시자는 DOM을 다루는 데 필요한 모든 것이기 때문에 우리가 만들 어플리케이션은 지시자들을 조합하여 구성하게 됩니다. HTML template을 포함한 Component와 구조/속성 지시자들을 활용하여 새로운 Component를 만들고, 또 이렇게 만들어진 Component 들을 조합하여 하나의 완성된 어플리케이션을 구축하는 것입니다. 따라서 지난 시간에 설명한 Component의 트리의 개념을 지시자를 포함해서 생각하면 다음과 같이 표현할 수 있습니다.

![컴포넌트,지시자의 트리](http://blog.mgechev.com/images/component-tree-angular2.png)


지시자는 component를 포함하여
