package study.user

import scala.concurrent.Future

trait UserService {

  def findAll(): Future[Seq[User]]

  def findById(id: String): Future[User]

  def create(id: String, password: String): Future[User]
}
