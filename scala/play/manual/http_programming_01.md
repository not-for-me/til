# Actions, Controllers and Results

## Action이란?

Play 어플리케이션에서 대부분의 요청은 `Action`이 처리합니다.

`play.api.mvc.Action`은 기본적으로 요청을 처리하여 사용자에게 반환할 결과값을 생성하는 `(play.api.mvc.Request => play.api.mvc.Result)` 함수입니다.

```scala
def echo = Action { request =>
  Ok("Got request [" + request + "]")
}
```

하나의 action에서는 웹 사용자에게 전송할 HTTP 응답을 표현하는 `play.api.mvc.Result` 값을 반환합니다. 이 예제에서는 `Ok`는 *text/plain* 응답 본문을 포함하는 *200 OK* 응답을 구성합니다.

## Action 생성
`play.api.mvc.Action`는 Action 값을 구성하기 위한 몇가지 헬퍼 메서드를 제공하는 동반객체 입니다.

최초의 가장 간단한 시작은 인수로 실행 블록으로 받아 `Result`를 반환하는 것입니다.:

```scala
Action {
  Ok("Hello world")
}
```

이 예제가 Action을 생성하는 가장 간단한 방법입니다. 하지만 여기서는 요청에 대한 참조를 취하고 있지 않습니다. Action을 호출하여 HTTP 요청에 접근하는 것이 종종 유용한 방법입니다.

따라서 `Request => Result`함수를 인수로 받는 또 다른 Action 빌더가 있습니다:

```scala
Action { request =>
  Ok("Got request [" + request + "]")
}
```

`request`인수를 `implicit`으로 마크하여 다른 API에서 필요할 때 암묵적으로 사용할 수 있게 해주는 것은 종종 유용합니다.:

It is often useful to mark the  parameter as implicit so it can be implicitly used by other APIs that need it:

```scala
Action { implicit request =>
  Ok("Got request [" + request + "]")
}
```

Action의 값을 생성하는 마지막 방법은 추가로 `BodyParser`인수를 지정해 주는 것입니다.

```scala
Action(parse.json) { implicit request =>
  Ok("Got request [" + request + "]")
}
```
Body parser는 매뉴얼의 후반부에서 다룰 예정입니다. 지금은 Action의 값을 생성하는 다른 메서들들은 기본으로 *Any content body parser* 를 사용한다는 점입니다.

## Controller는 action 생성기

하나의 `Controller`는 `Action`값을 생성하는 객체에 지나지 않습니다.

Controller는 [의존성 주입](https://www.playframework.com/documentation/2.5.x/ScalaDependencyInjection)의 이점을 취해 클래스로 선언하거나 객체로 선언할 수 있습니다.

> Note: Play의 향후 버전에서 controller를 객체로 선언하는 것은 지원하지 않을 수 있다는 점을 주의해 주세요. 클래스의 사용을 권장하는 방식입니다.

action 생성기를 정의하는 가장 간단한 사용법은 인수 없이 action 값을 반환하는 메서드입니다.:

```scala
package controllers

import play.api.mvc._

class Application extends Controller {

  def index = Action {
    Ok("It works!")
  }

}
```

물론, action 생성 메서드는 인수를 가질 수 있으며, Action 클로저에서 이러한 인수를 포획하여 사용할 수 있습니다.:

```scala
def hello(name: String) = Action {
  Ok("Hello " + name)
}
```
