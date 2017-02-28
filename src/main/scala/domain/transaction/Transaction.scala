package domain.transaction

import org.joda.time.DateTime
//TODO: investigate local date
case class Transaction(date: DateTime,
                       transactionType: TransactionType,
                       sortCode: String,
                       accountNumber: String,
                       description: String,
                       debitAmount: BigDecimal = BigDecimal(0),
                       creditAmount: BigDecimal = BigDecimal(0),
                       balanceAfter: BigDecimal
                      )
