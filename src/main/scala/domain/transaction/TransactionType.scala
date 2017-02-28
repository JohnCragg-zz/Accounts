package domain.transaction

import sun.security.pkcs.ParsingException

sealed trait TransactionType

object TransactionType {
  def apply(s: String): TransactionType = s match {
    case "DEB" => Debit
    case "CPT" => CashPoint
    case "BGC" => BankGiroCredit
    case "DEP" => Deposit
    case "FPO" => OutgoingPayment
    case "FPI" => IncomingPayment
    case _ => throw new ParsingException("unknown transaction type was not expected")
  }
}

object Debit extends TransactionType

object CashPoint extends TransactionType

object OutgoingPayment extends TransactionType

object IncomingPayment extends TransactionType

object BankGiroCredit extends TransactionType

object Deposit extends TransactionType
