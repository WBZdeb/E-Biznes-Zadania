package controllers

import javax.inject._
import play.api.mvc._
import models.Produkt
import scala.collection.mutable

@Singleton
class ProduktController @Inject()(val controllerComponents: ControllerComponents) extends BaseController {

  private val produkty: mutable.ListBuffer[Produkt] = mutable.ListBuffer(
    Produkt(1, "Produkt A", 100.0),
    Produkt(2, "Produkt B", 150.0),
    Produkt(3, "Produkt C", 200.0)
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

}
