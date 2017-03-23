package journal

import java.io.{File, FileWriter}

import grizzled.slf4j.Logging
import journal.Journal.{JournalCommand, JournalCommandData, JournalRehydrated, JournalRehydrationStarted}
import journal.JournalDomain.{deserialize, serialize, writeTo}
import uk.camsw.cqrs.EventBus.EventList
import uk.camsw.cqrs.{Actor, Event, EventBus}

import scala.util.{Failure, Success, Try}

case class JournalDomain(journalPath: File, rehydrate: Boolean = false) extends Actor[JournalCommand, JournalDomain]
  with Logging {

  override def ch(cmd: JournalCommand)(implicit bus: EventBus): EventList = cmd.data match {
    case JournalCommandData(Some(file)) => {
      info(s"Rehydrating journal from [$file]")
      if (file.exists()) {
        val events = for {line <- io.Source.fromFile(file).getLines()} yield deserialize(line)
        val (success, failure) = events partition {
          case Success(e) => true
          case Failure(e) => false
        }
        //TODO: implement hospicing of bad events
        (JournalRehydrationStarted(file) :: success.map { case Success(ev) => ev }.toList) :+ JournalRehydrated(file)
      }
      else {
        warn(s"File: [$file] does not exist")
        JournalRehydrationStarted(file) :: JournalRehydrated(file) :: Nil
      }
    }
    case _ => Nil
  }

  override val eh: (Event[_]) => Actor[JournalCommand, JournalDomain] = {
    case JournalRehydrationStarted(f) =>
      info("Rehydration started")
      this.copy(journalPath = f)
    case JournalRehydrated(f) =>
      info("Rehydration finished")
      this.copy(journalPath = f)
    case ev if ev.isInstanceOf[Event[_]] =>
      (serialize andThen writeTo(journalPath)) (ev.asInstanceOf[Event[_]])
      this
    case ev => this
  }
}

object JournalDomain {

  import scala.pickling._
  import json._

  val deserialize: String => Try[Event[_]] = line => Try {
    line.unpickle[Event[_]]
  }
  val serialize: Event[_] => String = _.pickle.value.replaceAll("\n", "")

  val writeTo: File => String => Unit =
    file => entry => {
      val fw = new FileWriter(file.getPath, true)
      try {
        fw.write(entry)
        fw.write("\n")
      }

      finally fw.close()
    }

}
