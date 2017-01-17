package controllers

import scala.concurrent.ExecutionContext.Implicits.global
import domain.Product
import play.api.Logger
import play.api.libs.json.JsValue
import play.api.libs.json.Json
import play.api.mvc.{Action, Controller, Request, Result}
import repositories.ProductRepositoryImpl
import play.api.mvc.Result

import scala.concurrent.TimeoutException

/**
  * Created by locnguyen on 1/17/17.
  */
class ProductController extends Controller {

  implicit val productWrites = Json.writes[Product]
  implicit val productReads = Json.reads[Product]

  def findProduct(id: Int) = Action.async { implicit request =>
    ProductRepositoryImpl.findById(id).map(product => Ok(Json.toJson(product))).recover {
      case ex: TimeoutException =>
        Logger.error("Problem found in product")
        BadRequest(ex.getMessage)
    }
  }

  def createProduct = Action(parse.json) { implicit request =>
    unmarshalProduct(request, (resource: Product) => {
      ProductRepositoryImpl.insertProduct(resource)
      Created
    })
  }

  def updateProduct(id: Int) = Action(parse.json) { implicit request =>
    unmarshalProduct(request, (resource: Product) => {
      ProductRepositoryImpl.updateProduct(id, resource)
      NoContent
    })
  }

  def deleteProduct(id: Int) = Action(parse.json) { implicit request =>
    ProductRepositoryImpl.deleteProduct(id)
    NoContent
  }

  def listProducts = Action.async { implicit request =>
    ProductRepositoryImpl.listProducts.map(product => Ok(Json.toJson(product))).recover {
      case ex: TimeoutException =>
        Logger.error("Problem found in product")
        BadRequest(ex.getMessage)
    }
  }

  private def unmarshalProduct(request: Request[JsValue], block: (Product) => Result): Result = {
    request.body.validate[Product].fold(
      valid = block,
      invalid = (e => {
        val error = e.mkString
        Logger.error(error)
        BadRequest(error)
      }))
  }
}
