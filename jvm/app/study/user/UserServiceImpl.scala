package study.user

import scala.concurrent.Future

import javax.inject.{ Inject, Singleton }

@Singleton
class UserServiceImpl @Inject() (dao: UserDao) extends UserService  {

  import dao.dbConfig.db
  import dao.dbConfig.profile.api._
  import dao.users

  def findAll(): Future[Seq[User]] = {
    db run {
      users.sortBy(_.id).result
    }
  }

  def create(title: String, password: String): Future[User] = {
    db run {
      val rows = users returning users.map(_.id) into ((item, id) => item.copy(id = id))

      rows += User(0, title, password)
    }
  }
}
