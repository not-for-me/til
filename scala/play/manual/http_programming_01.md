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

## 간단한 결과
지금까지는 단지 상태코드를 지닌 HTTP결과, 일련의 HTTP 헤더와 웹 사용자에게 전송할 바디로 이루어진 같은 간단한 결과만을 다루었습니다.

이러한 결과는 `play.api.mvc.Result`로 정의할 수 있습니다.:

```scala
import play.api.http.HttpEntity

def index = Action {
  Result(
    header = ResponseHeader(200, Map.empty),
    body = HttpEntity.Strict(ByteString("Hello world!"), Some("text/plain"))
  )
}
```

물론 공통의 결과를 생성하는데 위에서 살펴본 `Ok` 결과와 같은 몇가지 헬퍼 메서드를 사용할 수 있습니다.

```scala
def index = Action {
  Ok("Hello world!")
}
```

위 예제는 정확하게 이전의 코드와 동일한 결과를 생성합니다.

여기 다양한 결과를 생성하는 몇가지 예제가 좀 더 있습니다.:

```scala
val ok = Ok("Hello world!")
val notFound = NotFound
val pageNotFound = NotFound(<h1>Page not found</h1>)
val badRequest = BadRequest(views.html.form(formWithErrors))
val oops = InternalServerError("Oops")
val anyStatus = Status(488)("Strange response type")
```

여기의 모든 헬퍼 메서드는 `play.api.mvc.Results` 트레이트와 동반 객체에서 확인할 수 있습니다.



## 리다이렉트 역시 간단히 결과 입니다.
새로운 URL로 브라우저를 리다이렉트하는 것은 또 다른 간단한 결과에 불과합니다. 그러나 이러한 결과 타입은 응답 본문을 취하지 않습니다.

리다이렉트 결과를 생성하는데 필요한 여러 헬퍼 메서드가 있습니다.:

```scala
def index = Action {
  Redirect("/user/home")
}
```

기본적으로 `303 SEE_OTHER` 응답 타입을 사용하지만 필요에 따라서 좀 더 구체적은 응답 코드를 설정할 수도 있습니다.:

```scala
def index = Action {
  Redirect("/user/home", MOVED_PERMANENTLY)
}
```

## `TODO` 더미 페이지

`TODO`로 빈 `Action`을 구현할 수 있습니다. 이 때 결과는 ‘Not implemented yet’ 페이지가 됩니다.:

```scala
def index(name:String) = TODO
```
