> Angular2 코어 멤버인 [victorsavkin](https://twitter.com/victorsavkin)의 글 "[THE CORE CONCEPTS OF ANGULAR 2](http://victorsavkin.com/post/118372404541/the-core-concepts-of-angular-2)"을 [허락](https://twitter.com/JoeWoojin/status/746147467951931393)을 받고 번역 하였습니다. 번역에 대한 피드백이나 오류는 주저없이 [트윗](https://twitter.com/JoeWoojin)이나 [메일](mailto:jwj0831@gmail.com)로 알려주세요.

> This post is the translation version of the original post "[THE CORE CONCEPTS OF ANGULAR 2](http://victorsavkin.com/post/118372404541/the-core-concepts-of-angular-2)" with allowed to translate by the author [victorsavkin](https://twitter.com/victorsavkin).

# Angular2의 핵심 컨셉
본 글에서는 Angular2의 핵심 컨셉인 컴포넌트, 의존성 주입과 바인딩에 대해서 이야기 해보려고 합니다.

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

![컴포넌트에서 데이터의 흐름](https://raw.githubusercontent.com/not-for-me/til/master/angular2/translations/images/data_flow.png)
