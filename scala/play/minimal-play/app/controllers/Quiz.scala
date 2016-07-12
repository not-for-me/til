package controllers

import play.api.mvc._
import play.api.i18n.Lang
import javax.inject.Inject

import services.VocabularyService
import models.Vocabulary

class Quiz @Inject() (vocabulary: VocabularyService) extends Controller {
  def quiz(sourceLanguage: Lang, targetLanguage: Lang) = {
    val maybeVocabulary = vocabulary.findRandomVocabulary(sourceLanguage, targetLanguage)
    if (maybeVocabulary.isEmpty) {
      NOT_FOUND
    } else {
      maybeVocabulary.get.word
    }
  }

  def check(sourceLanguage: Lang, word: String, targetLanguage: Lang, translation: String) = TODO
}
