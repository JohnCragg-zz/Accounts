import java.io.File

import StatementReader.read
import domain.transaction._
import org.joda.time.DateTime
import org.scalatest.{FunSuite, Matchers}


class StatementReaderTest extends FunSuite with Matchers {

  import StatementReaderTest._

  test("can extract and format a line from the statement into a case class") {
    val statementLine = "20/02/2017,DEB,77-16-01,75792260,LIDL UK CD 2113 ,5.62,,300.06"
    read(statementLine) shouldEqual t1
  }

  test("can parse a full sample statement into a List of Events") {
    val statement = new File("C:working/dev/Accounts/src/test/resources/latestStatement.csv")
//    parseFile(statement) should dispatch transactions
  }
}

object StatementReaderTest {
  val t1 = Transaction(
    new DateTime(2017, 2, 20, 0, 0),
    Debit, "77-16-01",
    "75792260",
    "LIDL UK CD 2113 ",
    BigDecimal("5.62"),
    BigDecimal("0.0"),
    BigDecimal("300.06"))
  val t2 = Transaction(
    new DateTime(16, 2, 2017),
    IncomingPayment,
    "77-16-01",
    "75792260",
    "CLARKE JAMES FOOTY FP17047O05526272",
    BigDecimal("0"),
    BigDecimal("6.10"),
    BigDecimal("338.92")
  )
  val t3 = Transaction(new DateTime(15, 2, 2017),
    CashPoint,
    "77-16-01",
    "75792260",
    "LNK TESCO LIM C RD CD 2113 15FEB17",
    BigDecimal(10.00),
    BigDecimal(0),
    BigDecimal(352.20))
  val t4 = Transaction(new DateTime(14, 2, 2017),
    OutgoingPayment,
    "77-16-01",
    "75792260",
    "DANIEL BROOK 100000000253292838 14FEB17 09:29",
    BigDecimal(5.50),
    BigDecimal(0),
    BigDecimal(367.13))
  val transactions = Seq(t1, t2, t3, t4)
}
