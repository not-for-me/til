package controllers

import play.api.mvc._
import play.api.i18n.Lang

class Import extends Controller {
  def importWord(
    sourceLanguage: Lang,
    word: String,
    targetLanguage: Lang,
    translation: String) = TODO
}
