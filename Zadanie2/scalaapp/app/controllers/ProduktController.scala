package controllers

import javax.inject._
import play.api.mvc._
import models.{Produkt, ProduktForm}
import scala.collection.mutable
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._

@Singleton
class ProduktController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val produkty: mutable.ListBuffer[Produkt] = mutable.ListBuffer(
    Produkt(1, "Produkt A", 100.0),
    Produkt(2, "Produkt B", 150.0),
    Produkt(3, "Produkt C", 200.0)
  )

  val produktForm: Form[ProduktForm] = Form(
    mapping(
      "nazwa" -> nonEmptyText,
      "cena" -> of[Double]
    )(ProduktForm.apply)(ProduktForm.unapply)
  )

  val produktUpdateForm: Form[Produkt] = Form(
    mapping(
      "id" -> number,
      "nazwa" -> nonEmptyText,
      "cena" -> of[Double]
    )(Produkt.apply)(Produkt.unapply)
  )

  // Endpoint Read All
  def listAll(): Action[AnyContent] = Action {
    Ok(views.html.produktList(produkty.toList))
  }

  // Endpoint Read One
  def getProdukt(id: Int): Action[AnyContent] = Action {
    produkty.find(_.id == id) match {
      case Some(produkt) => Ok(produkt.toString)
      case None => NotFound(s"Produkt o id $id nie znaleziony")
    }
  }

  // Endpoint Create
  def createProdukt(): Action[AnyContent] = Action { implicit request =>
    produktForm.bindFromRequest.fold(
      errorForm => {
        BadRequest("Blad w formularzu")
      },
      data => {
        val nextId = if (produkty.isEmpty) 1 else produkty.map(_.id).max + 1
        val produkt = Produkt(nextId, data.nazwa, data.cena)
        produkty += produkt
        Ok(s"Produkt zostal dodany.")
      }
    )
  }

  // Endpoint Update
  def updateProdukt(): Action[AnyContent] = Action { implicit request =>
    produktUpdateForm.bindFromRequest.fold(
      errorForm => {
        BadRequest("Blad w formularzu")
      },
      data => {
        val produkt = Produkt(data.id, data.nazwa, data.cena)
        produkty.update(data.id, produkt)
        Ok(s"Produkt o id ${data.id} został zaktualizowany.")
      }
    )
  }

  // Endpoint Delete
  def deleteProdukt(id: Int): Action[AnyContent] = Action {
    val produktToDelete = produkty.find(_.id == id)
    produktToDelete match {
      case Some(produkt) =>
        produkty -= produkt
        Ok(s"Produkt o id $id został usunięty.")
      case None => NotFound(s"Produkt o id $id nie znaleziony")
    }
  }
}
