# Angular2에서 Typecript를 사용하는 이유

![Angular2, Typescript 로고](https://raw.githubusercontent.com/not-for-me/til/master/angular2/translations/images/angular2_typescript_logo.png)

Angular 2는 Typescript로 작성된 코드이다. 이 글에서 나는 Angular2에서 Typescript를 사용하기로 했는지 설명하기로 한다. 더불어 Typescipt의 사용이 코드의 작성과 리팩토링에 어떤 영향을 주는지에 Typescript를 사용하면서 느꼈던 경험들을 함께 나눌것이다.

## 나는 Typescript를 좋아하지만 여러분들은 그럴 필요는 없다.
Angular2는 Typescript로 작성되었지만 여러분도 Angular2 애플리케이션을 Typescript를 사용할 의무는 없습니다. Angular2 프레임워크는 ES5, ES6와 Dart 언어로도 훌륭하게 작업할 수 있습니다.


## TypeScript는 훌륭한 도구가 있습니다.
Typescript의 가장 큰 장점은 도구의 지원입니다. **Typecript는 높은 수준의 코드 자동생성, 코드 탐색과 리팩토링을 제공할 수 있습니다. 이러한 도구들은 일정 규모 이상의 프로젝트에서는 거의 필수적인 요소 입니다.**

The biggest selling point of TypeScript is tooling. It provides advanced autocompletion, navigation, and refactoring. Having such tools is almost a requirement for large projects. Without them the fear changing the code puts the code base in a semi-read-only state, and makes large-scale refactorings very risky and costly.


TypeScript is not the only typed language that compiles to JavaScript. There are other languages with stronger type systems that in theory can provide absolutely phenomenal tooling. But in practice most of them do not have anything other than a compiler. This is because building rich dev tools has to be an explicit goal from day one, which it has been for the TypeScript team. That is why they built language services that can be used by editors to provide type checking and autocompletion. If you have wondered why there are so many editors with great TypeScript supports, the answer is the language services.
The fact that intellisense and basic refactorings (e.g., rename a symbol) are reliable makes a huge impact on the process of writing and especially refactoring code. Although it is hard to measure, I feel that the refactorings that would have taken a few days before now can be done in less than a day.
While TypeScript greatly improves the code editing experience, it makes the dev setup more complex, especially comparing to dropping an ES5 script on a page. In addition, you cannot use tools analyzing JavaScript source code (e.g., JSHint), but there are usually adequate replacements.
TypeScript is a Superset of JavaScript
Since TypeScript is a superset of JavaScript, you don’t need to go through a big rewrite to migrate to it. You can do it gradually, one module at a time.
Just pick a module, rename the .js files into .ts, then incrementally add type annotations. When you are done with this module, pick the next one. Once the whole code base is typed, you can start tweaking the compiler settings to make it more strict.
This process can take some time, but it was not a big problem for Angular 2, when we were migrating to TypeScript. Doing it gradually allowed us to keep developing new functionality and fixing bugs during the transition.
TypeScript Makes Abstractions Explicit
A good design is all about well-defined interfaces. And it is much easier to express the idea of an interface in a language that supports them.
For instance, imagine a book-selling application where a purchase can be made by either a registered user through the UI or by an external system through some sort of an API.
