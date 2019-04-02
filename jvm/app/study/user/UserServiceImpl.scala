package study.user

import scala.concurrent.{ ExecutionContext, Future }

import javax.inject.{ Inject, Singleton }
import study.common.SequenceService

@Singleton
class UserServiceImpl @Inject()(dao: UserDao, databaseSupport: SequenceService)(implicit ec: ExecutionContext) extends UserService {

  import dao.dbConfig.db
  import dao.dbConfig.profile.api._
  import dao.users

  def findAll(): Future[Seq[User]] = {
    db run {
      users.sortBy(_.id).result
    }
  }

  def create(id: String, password: String): Future[User] = {
    for {
      idx <- databaseSupport.nextValue("User")
      user <- db run {
        val rows = users returning users.map(_.idx) into ((user, idx) => user.copy(idx = idx))

        rows += User(idx, id, password)
      }
    } yield {
      user
    }
  }
}
