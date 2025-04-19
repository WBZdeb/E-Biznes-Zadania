package controllers

import javax.inject._
import play.api.mvc._
import models.{Koszyk, KoszykForm, Produkt}
import scala.collection.mutable
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._

@Singleton
class KoszykController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {
  
}
