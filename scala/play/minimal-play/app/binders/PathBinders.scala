package binders

import play.api.i18n.Lang
import play.api.mvc.PathBindable

object ParthBinders {
  implicit object LangPathBinders extends PathBindable[Lang] {
    override def bind(key: String, vaue: String):
        Either[String, Lang] =
      Lang.get(value).toRight(s"Language $value" is mot )
  }
}
