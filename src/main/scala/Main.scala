import java.io.File
import java.util.concurrent.Executors

import domain.transaction.{Debit, RegisterTransaction, Transaction, TransactionDomain}
import org.joda.time.DateTime
import uk.camsw.cqrs.{EventBus, ServerAssembly}

import scala.concurrent.ExecutionContext

object Main extends App {

  val statement = new File("c:/")

  val domainBus = EventBus(ExecutionContext.fromExecutor(Executors.newSingleThreadExecutor()))

  new ServerAssembly() {
    val bus = domainBus
    withActor(TransactionDomain())
  }

  val transaction = Transaction(new DateTime(2017, 1, 1, 0, 0),
    Debit, "", "", "", BigDecimal(0), BigDecimal(0), BigDecimal(0))

  domainBus << RegisterTransaction(transaction)
  //@TODO: Start?
}
