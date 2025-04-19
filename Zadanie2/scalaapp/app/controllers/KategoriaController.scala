package controllers

import javax.inject._
import play.api.mvc._
import models.{Kategoria, KategoriaForm}
import scala.collection.mutable
import play.api.data._
import play.api.data.Forms._
import play.api.data.format.Formats._

@Singleton
class KategoriaController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val kategorie: mutable.ListBuffer[Kategoria] = mutable.ListBuffer(
    Kategoria(1, "Zabawki"),
    Kategoria(2, "Elektronika"),
    Kategoria(3, "Ubrania")
  )

  val kategoriaCreateForm: Form[KategoriaForm] = Form(
    mapping(
      "nazwa" -> nonEmptyText
    )(KategoriaForm.apply)(KategoriaForm.unapply)
  )

  val kategoriaUpdateForm: Form[Kategoria] = Form(
    mapping(
      "id" -> number,
      "nazwa" -> nonEmptyText
    )(Kategoria.apply)(Kategoria.unapply)
  )

  // Endpoint Read All
  def listAll(): Action[AnyContent] = Action {
    Ok(views.html.kategoriaList(kategorie.toList))
  }

  // Endpoint Read One
  def getKategoria(id: Int): Action[AnyContent] = Action {
    kategorie.find(_.id == id) match {
      case Some(kategoria) => Ok(kategoria.toString)
      case None => NotFound(s"Kategoria o id $id nie znaleziony")
    }
  }

  // Endpoint Create
  def createKategoria(): Action[AnyContent] = Action { implicit request =>
    kategoriaCreateForm.bindFromRequest.fold(
      errorForm => {
        BadRequest("Blad w formularzu")
      },
      data => {
        val nextId = if (kategorie.isEmpty) 1 else kategorie.map(_.id).max + 1
        val kategoria = Kategoria(nextId, data.nazwa)
        kategorie += kategoria
        Ok(s"Kategoria zostal dodany.")
      }
    )
  }

  // Endpoint Update
  def updateKategoria(): Action[AnyContent] = Action { implicit request =>
    kategoriaUpdateForm.bindFromRequest.fold(
      errorForm => {
        BadRequest("Blad w formularzu")
      },
      data => {
        val kategoria = Kategoria(data.id, data.nazwa)
        kategorie.update(data.id, kategoria)
        Ok(s"Kategoria o id ${data.id} został zaktualizowany.")
      }
    )
  }

  // Endpoint Delete
  def deleteKategoria(id: Int): Action[AnyContent] = Action {
    val kategoriaToDelete = kategorie.find(_.id == id)
    kategoriaToDelete match {
      case Some(kategoria) =>
        kategorie -= kategoria
        Ok(s"Kategoria o id $id został usunięty.")
      case None => NotFound(s"Kategoria o id $id nie znaleziony")
    }
  }

}