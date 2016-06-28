# 반응형 프로그래밍 개념 정리
우리는 모두 어느 정도 반응형 프로그래밍의 기법을 사용하여 사용자 인터페이스를 구축합니다. 하나의 Todo 항목이 늘었났다고 해봅시다. 우리는 화면에 새 항목을 그려줘야 합니다. 이번엔 누군가가 Todo 항목의 제목을 변경했다면 어떨까요? 우리는 DOM의 텍스트 요소를 갱신해줘야 합니다. 이와 같은 일들을 도와줄 수십 종류의  라이브러리들이 이미 존재합니다. 이러한 라이브러리들은 비슷한 점도 있으면서도 각기 다른 점도 가지고 있습니다.

이 글에서 저는 반응형 프로그래밍의 각기 독립적인 4가지 주요 개념을 설명할 것입니다.
1. 이벤트와 상태 (events and state)
2. 유도와 실행 (deriving and executing)
3. 구체화와 투명도 (reified and transparent)
4. 자기 관찰과 외부 관찰 (self observation and external observation)

위 주제들에 대한 개념과 함께 4가지 차원이 각각 어떻게 쓰이는지를 설명할 예정입니다. 이를 통해 여러 라이브러리들을 좀 더 객관적이고 명확하게 바라보고 건설적인 토론을 이끌어 낼 수 있을 것입니다.

이 글의 예제는 Angular2와 RxJS를 사용하여 작성하였습니다. 여기에 쓰인 예제들을 포함해서 대부분의 기능을 제공하는 프레임워크와 재사용 가능한 라이브러리까지도 여러분이 직접 비교해 볼 수 있을 것입니다. 그러면서도 동일한 개념을 사용하여 이 글에서 논의를 이어나갈 예정이기에 의미가 있을 것입니다.

그럼 이제 한번 들어가 봅시다.

## 이벤트와 상태 (Events and State)
우리는 반응성을 논할 때 이벤트와 이벤트 스트림을 이야기 하곤 합니다. 이벤트 스트림은 반응형 객체에서 중요한 부분을 차지합니다. 하지만 두번째로 중요한 상태에 대해서도 간과해서는 안됩니다. 각각의 속성을 한번 비교해 봅시다.

이벤트는 독립적인 것으로 결코 지나칠 수 없습니다. 모든 단일 이벤트는 발생된 순서를 바탕으로 신경써야 할 대상입니다. 그러므로 "가장 최근의 이벤트" 같은 것이 우리가 관심 가져야 하는 특별한 것이 아닙니다. 따라서 사용자에게 직접적으로 보여지는 이벤트는 거의 없다고 볼 수 있습니다.

반면에 상태는 연속적인 속성을 지니고 있기에 시간을 기준으로 특정한 때의 값을 정의할 수 있습니다. 우리는 보통 얼마나 많이 상태가 갱신되었는지에 관심을 갖지 않고 가장 최신의 값에만 신경을 씁니다. 상태는 종종 화면에 노출되거나 일련의 의미있는 형태로 있기도 합니다.

쇼핑카트를 예로 들어봅시다. 우리는 쇼핑카트의 내용을 변경하기 위해서 마우스 클릭 이벤트를 사용할 수 있습니다. 더하기 버튼을 클릭하면 제품의 수를 증가시켜줄 것이고, 마이너스 버튼을 클릭하면 제품의 수를 감소 시킬 것입니다. 결국, 클릭의 수가 중요하기 때문에 쇼핑카트의 내용을 유지하기 위해서 단 하나의 이벤트도 누락해서는 안됩니다. 그러나 우리는 마지막 이벤트를 반드시 점검해야 하거나 이를 사용자에게 노출시킬 일도 없습니다.

반면에 쇼핑카트의 전체 항목수는 상태에 해당합니다. 우리는 전체 항목수가 얼마나 갱신되었나를 보지 않고 쇼핑카트의 내용을 나타내는 가장 최신의 값만을 고려합니다.

### 정의
_이벤트 스트림_은 특정한 시간 주기 동안 생성된 값들읜 순차적인 나열입니다. 그리고 _상태_는 시간에 따라 바뀔 수 있는 단일 값입니다.

한 번 예제를 살펴봅시다.
 d
```typescript
type Pair = [number, number];
const moves: Observable<Pair> = fromEvent(document.body, "mousemove").map(e => [e.screenX, e.screenY]);
const position = new BehaviorSubject<Pair>([0,0]);
moves.subscribe(position);

position.value // returns the current position of the mouse
```

이 예제는 하나의 이벤트 흐름인 `moves` observable을 가지고 있습니다. 사용자가 매번 마우스를 움직일 때마다, observable은 이벤트를 발산(emit)시킵니다. 우리는 상태값인 `position` subject를 생성하여 `moves` observable을 사용합니다. `position`값의 속성은 마우스의 현재 위치를 반환합니다. 이 예제에서 이벤트와 상태가 무엇인지 묘사해줄 뿐 아니라 상태는 종종 이벤트의 순차적인 나열에서 생성된다는 것을 보여줍니다.

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

이 예제에서 employee 컴포넌트의 name 입력은 company 컴포넌트의 employees 프로퍼티에서 끄집어 낸 상태입니다. 주의할 점은 우리는 오로지 가장 최신의 이름 값에만 신경을 쓴다는 것입니다. 예를 들어 중도에 갖고있던 이름 값을 지나쳐도 어떤 것에도 영향을 미치지 않습니다. 모든 단일한 값이 중요하여 발생된 순서를 포함하여 그 값을 중요시 여길 때 선택된 이벤트의 순차적 나열과 대조해 보십시오.

### 정리
반응형 프로그램의 세계 안에 이벤트 스트림과 상태라는 두 개의 카테고리가 있습니다. 이벤트 스트림은 사용자에게 노출되지 않고 시간에 따라 흐르는 독립적인 값의 순차적인 나열입니다. 상태는 사용자게에 종종 노출되기도 하고 의미있는 형태를 갖는 시간에 따라 변할 수 있는 값입니다.
