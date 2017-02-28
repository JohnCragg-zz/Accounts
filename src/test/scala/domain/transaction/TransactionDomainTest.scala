package domain.transaction

import org.joda.time.DateTime
import org.scalatest.{FunSuite, Matchers}
import uk.camsw.cqrs.{Actor, ActorTestSupport}

import scala.reflect.classTag

class TransactionDomainTest extends FunSuite with Matchers with ActorTestSupport[RegisterTransaction, TransactionDomain] {
  override val commandTag = classTag[RegisterTransaction]

  override def actorUnderTest(): Actor[RegisterTransaction, TransactionDomain] = TransactionDomain()

  test("should register new transactions and put them onto the event bus") {
    actorSystem << RegisterTransaction(transaction)
    actorSystem.raisedEvents should contain only TransactionRegistered(transaction)
  }
//need to implement sort of identification
  ignore("should drop any duplicate transactions") {
    actorSystem << TransactionRegistered(transaction)
    actorSystem << RegisterTransaction(transaction)
    actorSystem.raisedEvents shouldBe empty
  }

  val transaction = Transaction(new DateTime(2017, 1, 1, 0, 0),
    Debit, "", "", "", BigDecimal(0), BigDecimal(0), BigDecimal(0))
}
