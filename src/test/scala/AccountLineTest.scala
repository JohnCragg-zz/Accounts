import org.scalatest.Matchers
import org.scalatest.FunSuite

class AccountLineTest extends FunSuite with Matchers{

  test("an AccountLine should not have null values"){
    val accountLine = AccountLine(0,0)
  }

}
