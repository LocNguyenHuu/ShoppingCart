package domain

import play.api.libs.json.Json

/**
  * Created by locnguyen on 1/17/17.
  */
case class Product(id: Option[Long], name: String, catId: Int, price: Double, stock: Int)

object JsonFormats {
  implicit val productFormat = Json.format[Product]
}
