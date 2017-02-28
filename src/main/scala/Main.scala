import java.io.File
import java.util.concurrent.Executors

import domain.transaction.TransactionDomain
import uk.camsw.cqrs.{EventBus, ServerAssembly}

import scala.concurrent.ExecutionContext

object Main extends App {

  val statement = new File("c:/working/dev/Accounts/src/test/resources/latestStatement.csv")

  val domainBus = EventBus(ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor()))

  new ServerAssembly() {
    val bus = domainBus
    withActor(TransactionDomain())
  }

  val statementReader = StatementReader
  val registeredTransactions = statementReader.parseFile(statement)

  for {
    transaction <- registeredTransactions
  } domainBus << transaction



  //@TODO: Start def see leon's main/domain assembly?
}
