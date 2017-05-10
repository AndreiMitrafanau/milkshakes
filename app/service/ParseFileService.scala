package service

import java.io.File

import domain._

import scala.collection.mutable
import scala.collection.mutable.ListBuffer
import scala.io.Source

class ParseFileService {
  def parseFile(file: File): List[TestCase] = {
    val lines = Source.fromFile(file).getLines().toList
    val testCaseIds = (1 to lines.head.toInt).foldLeft(mutable.Queue[Int]())(_ += _)
    val testCaseRaw = splitTestCases(lines.tail)
    testCaseRaw.map(tc => TestCase(testCaseIds.dequeue(), tc.head.toInt, parseCustomers(tc.tail)))
  }

  private def splitTestCases(list: List[String]): List[List[String]] = {
    def tailSplit(list: List[String], acc: ListBuffer[List[String]]): List[List[String]] = {
      list match {
        case Nil => acc.toList
        case n :: m :: tail => {
          val splitIndex = 2 + m.toInt
          val (testCase, rest) = list.splitAt(splitIndex)
          tailSplit(rest, acc += testCase)
        }
        case _ => throw new RuntimeException(s"Malformed list: $list")
      }
    }

    tailSplit(list, new mutable.ListBuffer[List[String]]())
  }


  private def parseCustomers(customers: List[String]): List[Customer] = {
    val ids = (0 to customers.length).foldLeft(mutable.Queue[Int]())(_ += _)
    customers.tail.map { str =>
      val arr = str.split(" ")
      Customer(ids.dequeue(), arr.head.toInt, parseMilkShakes(arr.tail))
    }
  }

  private def parseMilkShakes(list: Array[String]): List[MilkShake] = {
    val sliding = list.grouped(2).toList
    sliding.map(t => MilkShake(t.head.toInt - 1, parseShakeType(t.tail.head.toInt)))
  }

  private def parseShakeType(value: Int): ShakeType =
    if (value == 1) Malted
    else if (value == 0) Unmalted
    else throw new RuntimeException(s"Malformed string: $value")

}
