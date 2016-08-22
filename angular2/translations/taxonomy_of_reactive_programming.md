> Angular2 코어 멤버인 [victorsavkin](https://twitter.com/victorsavkin)의 글 "[THE TAXONOMY OF REACTIVE PROGRAMMING](https://vsavkin.com/the-taxonomy-of-reactive-programming-d40e2e23dee4)"을 [허락](https://twitter.com/JoeWoojin/status/746147467951931393)을 받고 번역 하였습니다. 번역에 대한 피드백이나 오류는 주저없이 [트윗](https://twitter.com/JoeWoojin)이나 [메일](mailto:jwj0831@gmail.com)로 알려주세요.

> This post is the translation version of the original post "[THE TAXONOMY OF REACTIVE PROGRAMMING](https://vsavkin.com/the-taxonomy-of-reactive-programming-d40e2e23dee4)" with allowed to translate by the author [victorsavkin](https://twitter.com/victorsavkin).

# 반응형 프로그램 분류
우리는 모두 반응형 프로그램의 일부 요소를 활용하여 사용자 인터페이스를 구축합니다. 하나의 Todo 항목이 늘었났다고 해봅시다. 화면에 새 항목을 그려줘야 합니다. 이번엔 누군가가 Todo 항목의 제목을 변경했다면 어떨까요? 우리는 DOM의 텍스트 요소를 갱신해줘야 합니다. 이와 같은 일들을 도와줄 수십 종류의 라이브러리들이 이미 존재합니다. 이러한 라이브러리들은 비슷한 점도 있으면서도 각기 다른 점도 가지고 있습니다.

이 글에서 저는 반응형 프로그래밍의 각기 독립적인 4가지 측면을 설명할 것입니다.
1. 이벤트와 상태 (events and state)
2. 도출과 실행 (deriving and executing)
3. 구체적 방식과 투명한 방식(reified and transparent)
4. 자기 관찰과 외부 관찰 (self observation and external observation)

위 주제들에 대한 개념과 함께 4가지 측면이 각각 어떻게 쓰이는지를 설명할 예정입니다. 이를 통해 여러 라이브러리들을 좀 더 객관적이고 명확하게 바라보고 건설적인 토론을 이끌어 낼 수 있을 것입니다.

이 글의 예제는 Angular2와 RxJS를 사용하여 작성하였습니다. 여기에 쓰인 예제를 포함하여 재사용 가능한 라이브러리들과 대부분의 기능을 제공하고 있는 여러 프레임워크를 비교하는 것이 좀더 명확해 질 것입니다. 동일한 개념을 사용하여 이 글에서 논의를 이어나갈 예정입니다. 따라서 이러한 비교논의가 의미 있을 것입니다.

그럼 이제 한번 들어가 봅시다.

## 이벤트와 상태 (Events and State)
우리는 반응성을 논할 때 이벤트와 이벤트 스트림을 이야기 하곤 합니다. 이벤트 스트림은 반응형 객체에서 중요한 부분을 차지합니다. 하지만 두번째로 중요한 상태에 대해서도 간과해서는 안됩니다. 각각의 속성을 한번 비교해 봅시다.

이벤트는 독립적인 것으로 결코 지나칠 수 없습니다. 모든 단일 이벤트는 발생된 순서를 바탕으로 신경써야 할 대상입니다. 그러므로 "가장 최근의 이벤트" 같은 것이 우리가 관심 가져야 하는 특별한 것이 아닙니다. 따라서 사용자에게 직접적으로 보여지는 이벤트는 거의 없다고 볼 수 있습니다.

반면에 상태는 연속적인 속성을 지니고 있기에 시간을 기준으로 특정한 때의 값을 정의할 수 있습니다. 우리는 보통 얼마나 많이 상태가 갱신되었는지에 관심을 갖지 않고 가장 최신의 값에만 신경을 씁니다. 상태는 종종 화면에 노출되거나 일련의 의미있는 형태로 있기도 합니다.

쇼핑카트를 예로 들어봅시다. 우리는 쇼핑카트의 내용을 변경하기 위해서 마우스 클릭 이벤트를 사용할 수 있습니다. 더하기 버튼을 클릭하면 제품의 수를 증가시켜줄 것이고, 마이너스 버튼을 클릭하면 제품의 수를 감소 시킬 것입니다. 결국, 클릭의 수가 중요하기 때문에 쇼핑카트의 내용을 유지하기 위해서 단 하나의 이벤트도 누락해서는 안됩니다. 그러나 우리는 마지막 이벤트를 반드시 점검해야 하거나 이를 사용자에게 노출시킬 일도 없습니다.

반면에 쇼핑카트의 전체 항목수는 상태에 해당합니다. 우리는 전체 항목수가 얼마나 갱신되었나를 보지 않고 쇼핑카트의 내용을 나타내는 가장 최신의 값만을 고려합니다.

### 정의
_이벤트 스트림_ 은 특정한 시간 주기 동안 생성된 값들의 순차적인 나열입니다. 그리고 _상태_ 는 시간에 따라 바뀔 수 있는 단일 값입니다.

한 번 예제를 살펴봅시다.

```typescript
type Pair = [number, number];
const moves: Observable = fromEvent(document.body, "mousemove").map(e => [e.screenX, e.screenY]);
const position = new BehaviorSubject([0,0]);
moves.subscribe(position);

position.value // returns the current position of the mouse
```

위 예제에서는 하나의 이벤트 흐름인 `moves`라는 `observable`을 가지고 있습니다. 사용자가 매번 마우스를 움직일 때마다, `observable`은 이벤트를 발산(emit)시킵니다. 우리는 상태값인 `position` `subject`를 생성하여 `moves` `observable`을 사용합니다. `position`값의 속성은 마우스의 현재 위치를 반환합니다. 이 예제는 이벤트와 상태가 무엇인지 묘사해줄 뿐 아니라 상태는 종종 이벤트의 순차적인 나열에서 생성된다는 것을 보여줍니다.

> 역자 주: RxJS가 익숙하지 않은 분들은 observable이 계속해서 발생하는 이벤트 흐름의 발생출처 라고 생각하시고 읽으시면 됩니다. 그래서 이름과 같이 _관찰가능한_ 대상이기에 observable이라고 합니다. RxJS에 대해 자세히 알고 싶으신 분들은 [rxmarble](http://rxmarbles.com/)의 그림을 참조해 보세요.

이벤트와 상태라는 이분법이 단지 RxJS만의 산물이 아니라는 것을 보여주기 위해서 이번에는 Angular의 예제를 봅시다.

```typescript
@Component({
  selector: 'company',
  template: `
    <div *ngFor="let e of employees">
      Employee: <employee [name]="e.name" (selected)="employeeSelected($event)"></employee>
    </div>
  `,
  directives: [EmployeeCmp]
})
class CompanyCmp {
  employees: Array<{name: string}>;
  selectedEmployees = [];

  employeeSelected(e) {
    this.selectedEmployees.push(e.selected);
  }
}

@Component({
  selector: 'employee',
  template: `
    Name {{name}}
    <button (click)="select()">Select</button>
  `
})
class EmployeeCmp {
  @Input() name: string;
  @Output() selected = new EventEmitter();

  select() { this.selected.next({selected: this.name}); }
}
```

이 예제에서 `employee` 컴포넌트의 `name` 입력은 `company` 컴포넌트의 employees 프로퍼티에서 끄집어 낸 상태입니다. 주의할 점은 우리는 오로지 가장 최신의 이름 값에만 신경을 쓴다는 것입니다. 예를 들어 중도에 갖고 있던 이름 값을 지나쳐도 어떤 것에도 영향을 미치지 않습니다. 모든 단일한 값을 다루면서 발생된 순서정보까지 내포한 `selected` 이벤트의 순차적 나열과 비교해 보세요.

### 정리
반응형 프로그램의 세계 안에 이벤트 스트림과 상태라는 두 개의 카테고리가 있습니다. 이벤트 스트림은 사용자에게 노출되지 않고 시간에 따라 흐르는 독립적인 값의 순차적인 나열입니다. 상태는 사용자게에 종종 노출되기도 하고 의미있는 형태를 갖는 시간에 따라 변할 수 있는 값입니다.

## 도출과 실행 (deriving and executing)

먼저 마우스 클릭에 대한 RxJS observable이 다음과 같이 있다고 해봅시다.

```typescript
const clicks: Observable = fromEvent(button, "click");
```

이 observable로 무얼 할 수 있을까요? 당장 드는 생각은 좌표를 표시하는 것이네요.
```typescript
clicks.forEach(e => {
console.log("click", "x", e.screenX, "y", e.screenY);
});
```

나름 유용하지만, 콜백을 사용하는 것과 큰 차이가 없습니다.

```javascript
button.addEventListener("click", (e) => {
console.log("click", "x", e.screenX, "y", e.screenY);
});
```

마우스 클릭 observable의 진짜 가치는 새로운 observable을 도출할 수 있다는 점입니다.

```typescript
type Pair = [number, number];
const coordinates: Observable = clicks.map(e => [e.screenX, e.screenY]);
```

`map` 연산이 제공한 것은 오로지 새로운 `observable`을 제공한다는 점입니다. 이로 인해 프로그램에 영향을 주는 것은 전혀 없기에, 이 프로그램은 부수효과를 갖지 않습니다.

> 역자 주: 이 번역에서 side effect는 부수효과로 통일합니다. 부수효과는 함수가 자신의 결과값 이에외 다른 상태에 영향을 주는 것을 말합니다. 예를 들면 console.log를 사용한 화면 출력도 마찬가지입니다.

이제, 조금 더 깊이 들어가서 몇가지 RxJS 연산을 사용하여 새로운 `observable`을 생성해 봅시다. 이번에 생성할 `observable`은 모든 요소가 `to`와 `from`의 좌표를 갖고 있습니다.

```typescript
type Pair = [number, number];
const coordinates: Observable = clicks.map(e => [e.screenX, e.screenY]);
const startFromSecond: Observable = coordinates.skip(1);
const pairs: Observable<[Pair, Pair]> = zip(coordinates, startFromSecond);
```

다시 한번, `observable` 사이의 관계만 집중해 봅시다. 도출이란 어떻게 서로 다른 존재(entity)가 어떻게 연관되어 있는지에 대한 모든 것입니다. 결론적으로는 여러분의 프로그램이 수행하게 될 연산을 표현해주는 하나의 `observable` 또는 여러 `observable`이 있게 됩니다. 이는 연산(computation) 그 자체가 아니라 연산의 표현입니다. 사용자에게 실제로 노출되는 것은 아무것도 없습니다.

사용자에게 무엇인가를 보여주기 위해서 (다른 말로 표현하자면, 부수효과를 실행하기 위해서는) 우리는 `forEach`함수의 호출이 필요하다.

```typescript
pairs.forEach(([from, to]) => {
console.log("You moved from: ", from[0], from[1], " to: ", to[0], to[1]);
});
```

RxJS만이 도출과 부수효과의 수행을 별개의 연산으로 분류하는 유일한 라이브러리가 아닙니다. Angular 역시 가능합니다.

```typesecipt
@Component({
  selector: 'company',
  template: `
    CEO: <employee [name]="ceo.name"></employee>
  `,
  directives: [EmployeeCmp]
})
class CompanyCmp {
  ceo: {name: string};
}

@Component({
  selector: 'employee',
  template: `
    Name {{name}}
  `
})
class EmployeeCmp {
  @Input() name: string;

  ngOnChanges(changes) {
  // effects
  console.log("changes detected", changes);
  }
}
```

`name`속성은 `ceo` 속성에 도출되어 `ngOnChanges`가 부수효과를 수행하는데 사용됩니다.

> 역자 주: Angular2에서는 컴포넌트가 뷰의 일부 요소가 되어 트리형태를 만듭니다. 따라서 부모 컴포넌트에서 자식 컴포넌트로 상태 값을 전달할 수 있고, 이 때 자식 컴포넌트에서는 값이 변결될 때 ngOnChanges 함수가 자동으로 실행됩니다. 위 예제는 간략하게 부모 컴포넌트의 ceo 상태가 변경되어 ceo 안에 name을 받는 자식 컴포넌트의 ngOnchange가 console.log로 부수효과를 일으키는 것을 보여줍니다.

### Hot과 Cold. 그리고 프라미스에 대해서

흥미롭게도 RxJS의 observable은 차갑기 때문에, 라이브러리가 두 종류의 연산으로 구분되어 있어야만 합니다. `forEach`와 구독 개념과 같은 일련의 연산들을 제공해야 합니다. 그렇지 않으면 어떠한 부수효과도 실행되지 않을 것입니다.

반면에 프라미스는 뜨겁습니다. 뜨겁다는 의미는 프라미스를 제공하는 쪽에서 값이 정해지면, 모든 도출된 프라미스에게 전달이 됩니다. 왜냐하면 프라미스는 도출과 부수효과를 실행하는데 모두 사용될 수 있는 `then` 연산을 통해서 이를 해낼 수 있기 때문입니다.

```typescript
const jsonPromise = fetchData().then(result => result.json); //deriving a new value
fetchData().then(result => console.log(result.json)); // executing side effects
```

hot/cold 주제는 미묘하지만 단순히 부수효과나 구독과 관련해서 영향을 주는 것이 아니라 취소와도 관련되어 있습니다. 더 자세한 내용은 Ben Lesh의 [훌륭한 글](https://medium.com/@benlesh/hot-vs-cold-observables-f8094ed53339#.nkybzxi7w)을 참조하세요.

> 역자 주: Hot과 Cold Observable에 대한 차이는 Rx 방식의 개념에서 나온 것으로 자세한 내용은 위의 글을 참조해 주시기 바랍니다. 간단하게 Hot은 변경된 데이터가 있으면 Push 형태로 바로 전달해 주는 것이고, Cold는 변경된 데이터를 구독하는 리스너가 있기 전까지 실행되지 않는 Pull 형태라고 보시면 됩니다.

### 정리
시간에 따라 변하는 변수를 다루는 방법은 두 가지가 있습니다. 기존 변수로 부터 새로운 변수를 유도하거나 값이 변화할 때마다 부수효과를 실행하는 것입니다. 이 두 방법을 분리하는 것은 코드를 좀 더 구성 가능하고 리팩토링하기 쉽게 만들어 줍니다.

## 구체적 방식(REIFIED)과 투명한 방식(TRANSPARENT)
먼저, 예제를 살펴봅시다.

```typescript
const ceo: Subject<{name: string}> = getCeo();
const name: Subject = ceo.map(c => c.name);
```

예제에서 우리는 두 개의 `subject`인 `ceo`와 `name`을 가지고 있습니다. 이 두 `subject`는 값의 변경이 일어나는 것을 관찰할 수 있도록 도와줍니다. 반응형 프로그램에서 이러한 타입을 우리는 *구체적 방식*(reified)이라고 호명합니다. 그 이유는 관찰행위를 표현하는 구체적인 객체에 접근할 수 있기 때문입니다. 또한 이러한 구체적인 객체의 소유는 이를 수정하고 다른 곳에 전달하고, 새로운 것을 구성할 수 있기에 강력한 특징을 갖고 있습니다.

`name.value` 와 `name`의 차이는 처음에 봤을때 미묘할 수도 있지만 핵심적인 부분입니다. `name.value`는 `name`상태의 현재 값을 의미하지만 `name`은 그 자체로 다른 것입니다. `name` 시간에 따라 변화하는 값을 나타냅니다.

유사한 Angular 방식의 예제와 대조해 봅시다.

```typescript
@Component({
  selector: 'company',
  template: `CEO: <employee [name]="ceo.name"></employee>`,
  directives: [EmployeeCmp]
})
class CompanyCmp {
  ceo: {name: string};
}

@Component({
  selector: 'employee',
  template: `Name {{name}}`
})
class EmployeeCmp {
  @Input() name: string;
}
```

Angular는 시간에 따라 변하는 `name`을 표현하는 어떠한 형태의 객체도 제공하지 않습니다. 우리는 오로지 현재 값만을 갖고 있습니다. 반응형 프로그래밍에서 이러한 타입을 *투명한 방식*(transparent)라고 호명합니다. 왜냐하면 개발자들은 오로지 가장 최신의 값만을 다루기 때문입니다. 관찰행위는 프레임워크 안에서 이뤄지기에 알 수 없습니다.

### 구성

투명한 방식과 구체적 방식을 활용한 반응형 프로그래밍은 상태를 구성할 때 매우 다르게 작동합니다.

구체적 방식의 반응형 프로그램을 사용하면 우리는 다음과 같이 기존의 것에서 새로운 변수를 생성하는 특수한 연산을 사용해야만 합니다.

``` typescript
const ceo: Subject<{firstName: string, lastName: string}> = getCeo();
const firstName: Subject<string> = ceo.map(c => c.firstName);
const lastName: Subject<string> = ceo.map(c => c.lastName);
const fullName: Subject<string> = zip(firstName, lastName).map(p => `${p[0]} ${p[1]}`);
```

투명한 방식의 반응형 프로그래밍을 활용 시 우리는 일반적인 자바스크립트 문법을 사용합니다.

```typescript
@Component({
  selector: 'company',
  template: `CEO: <employee [fullName]="ceo.firstName + ' ' + ceo.lastName"></employee>`
})
class CompanyCmp {
  ceo: {firstName: string, lastName: string};
}
```

상태를 다룰 때에, 우리는 위 두 타입의 반응형 프로그래밍을 효율적으로 사용할 수 있습니다. 투명한 반응형 프로그래밍이 좀 더 간단하고 성능에 우위가 있는 반면, 구체화한 반응형 프로그래밍은 좀 더 유연합니다.

이벤트를 다루는 경우에는 두 방식을 모두 사용할 수 없습니다. 왜 두 타입의 반응형 프로그래밍을 살펴볼 필요가 있는지 이해하려면 시간의 개념을 다뤄야 합니다.

### 암묵적 시간과 명시적 시간

투명한 방식의 반응형 프로그램을 사용 시, 우리는 시간을 명시적으로 다룰 수 없습니다. 상세히 설명하면 시간의 개념 자체를 실제로 정의하지 않습니다. 물론 `name` 속성이 시간의 흐름 속에 변할 수 있다는 것을 인지하고 있지만, 시간에 흐름에 기반하여 흥미로운 결정을 도출할 만한 어떠한 수단도 없습니다.

그래서 구체적 방식의 반응형 프로그램이 뜨기 시작한 겁니다. 구체적 방식의 반응형 프로그램은 시간의 흐름을 명시적으로 만들어 이를 활용하는 특수한 연산들을 제공하므로 새로운 것을 구성할 수 있게 도와줍니다. 예를 들면, 우리는 다음과 같이 `name` `subject`를 0.5초 지연시켜 갱신할 수 있습니다.

``` typescript
const ceo: Subject<{name: string}> = getCeo();
const name: Subject<string> = ceo.map(c => c.name).delay(500);
```

상태값을 활용할 때 시간을 직접 이용하는 것은 그다지 실용적이지 않겠지만 이벤트를 다룰 때는 흔한 방식입니다. (예를 들면 `debouncing` 같은 것이 있습니다.) 이러한 이유로, 지금까지 익숙했던 투명한 방식의 패러다임으로서 가장 최신의 값에 접근하는 것은 이벤트 처리에 있어서는 종종 충분하지 않을 때가 있습니다.

### 정리
투명한 방식의 반응형 프로그램은 시간에 따라 변하는 여러 변수들을 구성하는데 있어서 기존의 자바스크립트 문법을 사용하기 때문에 더 간단합니다. 그러나 이 방법은 오로지 변수의 가장 최신의 값만을 제공하며 명시적으로 시간이 흐름을 다룰 방법을 제공하지 않습니다. 결과적으로 상태 값을 도출하는 데는 유용하지만 이벤트를 조작하는데는 그렇지 않습니다.

## 자기관찰과 외부 관찰

### 정의
외부관찰(External observation)은 관찰 대상인 객체를 스스로 관찰해서 알아낼 수 없고 대신 어떤 외부 존재 (예를 들면, Angular 프레임워크)가 객체의 변화를 알아내는 방식입니다. 자기 관찰(Self observation)은 스스로 객체의 변화를 직접 관리하는 방식입니다.

다시 한번 예제를 보도록 합시다.

```typescript
@Component({
  selector: 'company',
  template: `CEO: <employee [fullName]="ceo.firstName + ' ' + ceo.lastName"></employee>`
})
class CompanyCmp {
  ceo: {firstName: string, lastName: string};
}
```

이 예제에서는 `employee` 컴포넌트의 `fullName` 속성을 `company` 컴포넌트의 `ceo` 속성에서 도출하고 있습니다. 다시 말하면, Angular에게 `ceo` 객체를 관찰하여 성이나 이름이바뀔 때마다 `employee` 컴포넌트를 갱신하라고 명령합니다. 여기서 흥미로운 점은 `ceo` 객체는 관찰에 필요한 어떠한 정보도 소유하고 있지 않고, 일련의 특수한 방식으로 표현되거나 생성된 것도 아닙니다.

이는 Angular가 대신 외부관찰을 하고 있기 때문입니다. Angular는 template[^1] 안에 표현식을 계산하고 결과를 기억하고 있습니다. 다음 번에도 동일한 계산을 수행하면서 이전의 값과 새로운 값을 비교하게 됩니다. 이 때 만약 값이 변경되었을 경우, Angular 프레임워크에서 `employee` 컴포넌트를 갱신해 줍니다.

외부 관찰은 많은 이점을 갖고 있습니다. 먼저, 상당한 유연함을 가져다 줍니다. 여러분은 예제와 같이 `{firstName: string, lastName: string}` 인터페이스를 구현한 어떤 형태의 객체를 사용해도 됩니다. 서버로부터 전송된 json 일수도 있고 [ImmutableJS](https://facebook.github.io/immutable-js/) 객체도 가능합니다. 이에 더하여, 외부 관찰은 강력하게 관찰의 순서를 보장하여 연속적인 갱신의 문제를 피할 수 있도록 실시합니다. 물론 불리한 측면은 실행할 코드가 프레임워크의 컨텍스트 안에서 관찰되어야 합니다. 그래서 Angular1이 `$scope.apply`를 API를 가지고 있으며, Angular2 에서 [zone.js](https://github.com/angular/zone.js/)가 필요한 이유이기도 하니다.

객체의 변화를 스스로 인지하고 이를 구독하고 있는 대상 에게 알려주는 자기관찰과 대조해 보시기 바랍니다. [MobX](https://mobxjs.github.io/mobx/) 또는 [Knockout](http://knockoutjs.com)이 이러한 접근방식을 취하고 있는 라이브러리 입니다.


## 총정리
본 글에서, 반응형 프로그래밍에 대한 논의에 필수적인 4가지 측면들을 소개하고자 정리해 보았습니다. 제 바람은 이 글을 통해서 좀 더 명확하고 객관적으로 여러 라이브러리나 접근 방식을 비교하는 것입니다.

이제 여러분이 새로운 라이브러리를 접할 때 스스로에게 다음과 같은 4가지 질문을 던져 보시길 바랍니다.

* 라이브러리가 이벤트를 다루는가? 상태를 다루는가?
* 라이브러리가 값을 도출하는가? 부수효과를 실행하는가?
* 라이브러리가 투명한 방식의 반응형 프로그램인가? 구체적 방식의 반응형 프로그램인가?
* 라이브러리가 외부관찰인가? 자기관찰인가?

예를 들면, Angular에 대해서 논의할 때 우리는 상태를 도출하기 위하여 외부관찰을 통해 투명한 방식의 반응형 프로그램을 사용하거나 혹은 이벤트를 다루기 위해서 자기관찰을 통해 구체적 방식의 반응형 프로그램을 사용한다고 말할 수 있습니다. 그리고 더 바라기는 여러분은 Angular 개발팀이 이러한 선택지를 만든 이유를 알았으면 합니다. 다른 예로 자기관찰을 통해 투명한 방식의 반응형 프로그램을 사용하며 상태를 다루는 [MobX](https://mobxjs.github.io/mobx/)가 있습니다. 또는 자기 관찰을 통해 구체적 방식의 반응형 프로그램을 지원하는 [Cycle.js](http://cycle.js.org/)를 고려해 볼수도 있습니다.


모든 형태의 반응형 프로그램을 이 글을 통해서 알았을까요? 물론 아닙니다! 이 하나의 글을 통해서 반응형 프로그램에 대한 전체 분야를 다뤘다는 것은 순진한 생각입니다. 예를 들면, 저는 푸쉬나 풀 방식, 일급 및 고계함수의 FRP(Functional Reactive Programming), continus FRP, Observable/Iterable duality, back pressure, sampling 등 여러 다른 흥미롭고 중요한 주제를 다루지 않았습니다. 어쩌면 다음 블로그 포스팅의 주제가 될지도 모르겠습니다. 그러니 계속해서 주목해 주세요.

저자의 Angular2 Medium: https://vsavkin.com/

[^1]: http://www.notforme.kr/archives/1627#template
