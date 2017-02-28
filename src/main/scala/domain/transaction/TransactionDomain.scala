package domain.transaction

import uk.camsw.cqrs.EventBus.EventList
import uk.camsw.cqrs.{Actor, Command, Event, EventBus}

case class TransactionDomain() extends Actor[RegisterTransaction, TransactionDomain] {

  override def ch(cmd: RegisterTransaction)(implicit bus: EventBus): EventList = cmd match {
    case RegisterTransaction(data) => List(TransactionRegistered(data))
    case _ => Nil
  }

  override val eh: (Event[_]) => Actor[RegisterTransaction, TransactionDomain] = _ => this
}

case class RegisterTransaction(data: Transaction) extends Command[Transaction]

//@TODO: decouple types here
case class TransactionRegistered(data: Transaction) extends Event[Transaction]
