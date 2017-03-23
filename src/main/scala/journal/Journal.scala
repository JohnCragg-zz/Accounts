package journal

import uk.camsw.cqrs.{Command, Event}
import java.io.File

object Journal {

  def replayJournal(journalPath: File) = JournalCommand(JournalCommandData(rehydrate = Option(journalPath)))

  case class JournalCommand(data: JournalCommandData) extends Command[JournalCommandData]

  case class JournalCommandData(rehydrate: Option[File] = None)

  case class JournalRehydrated(data: File) extends Event[File]

  case class JournalRehydrationStarted(data: File) extends Event[File]

}
