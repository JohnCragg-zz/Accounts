package journal

import java.io.File

import journal.Journal.{JournalCommand, JournalRehydrated, JournalRehydrationStarted, replayJournal}
import org.scalatest.{FunSuite, Matchers}
import uk.camsw.cqrs.{Actor, ActorTestSupport, Event}

import scala.collection.immutable.Seq
import scala.reflect.classTag

class JournalDomainTest extends FunSuite with Matchers with ActorTestSupport[JournalCommand, JournalDomain] {

  import JournalDomainTest._

  override val commandTag = classTag[JournalCommand]

  var journalFile: File = _

  override def actorUnderTest(): Actor[JournalCommand, JournalDomain] = {
    journalFile = new File("C:/working/dev/Accounts/src/test/resources/journal.json")
    JournalDomain(journalFile)
  }


  test("Journal domain can rehydrate events") {
    for {e <- events} actorSystem << e
    actorSystem << replayJournal(journalFile)
    actorSystem.raisedEvents should contain(JournalRehydrationStarted(journalFile))
    events.map(actorSystem.raisedEvents should contain(_))
    actorSystem.raisedEvents should contain(JournalRehydrated(journalFile))
  }

  test("should rehydrate nothing where the file does not exist") {
    val fileThatDoesntExist = new File("somefile")
    actorSystem << replayJournal(fileThatDoesntExist)
    actorSystem.raisedEvents should contain only(JournalRehydrationStarted(fileThatDoesntExist), JournalRehydrated(fileThatDoesntExist))
  }
}

object JournalDomainTest {
  val events: Seq[TestEvent] = for {
    s <- (1 to 10).toList
  } yield TestEvent(Map(s -> s"Event: $s"))

  case class TestEvent(data: Map[Int, String]) extends Event[Map[Int, String]]

}
