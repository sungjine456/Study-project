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

  def create(id: String, password: String): Future[User] = {
    db run {
      val rows = users returning users.map(_.idx) into((user, idx) => user.copy(idx = idx))

      rows += User(0, id, password)
    }
  }
}
