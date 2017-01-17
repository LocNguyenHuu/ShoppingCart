package persistence

import domain.Product
import slick.driver.MySQLDriver.api._
/**
  * Created by locnguyen on 1/17/17.
  */
class Products(tag: Tag)
  extends Table[Product](tag, "PRODUCTS") {

  def id = column[Long]("ProID", O.PrimaryKey, O.AutoInc)
  def name = column[String]("ProName")
  def catId = column[Int]("CatID")
  def price = column[Double]("Price")
  def stock = column[Int]("Stock")

  def * = (id.?, name, catId, price, stock) <> (Product.tupled, Product.unapply)
}
