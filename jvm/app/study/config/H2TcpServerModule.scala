package study.config

import com.google.inject._

class H2TcpServerModule extends AbstractModule {

  override def configure() {
    bind(classOf[H2TcpServer]).asEagerSingleton()
  }
}

@Singleton
class H2TcpServer @Inject()() {
  import org.h2.tools.Server

  val server: Server = Server.createTcpServer().start()
}
