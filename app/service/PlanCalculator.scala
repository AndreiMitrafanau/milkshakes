package service


import domain.{Malted, MilkShake, TestCase}

import scala.util.Try

class PlanCalculator {
  def getProductionPlan(testCases: List[TestCase]): List[String] = {
    testCases.map { testCase =>
      val numCustomers = testCase.customers.length
      val shakesCanUse = Array.ofDim[Boolean](numCustomers, testCase.numShakes)
      val maltedByCustomer: Array[MilkShake] = new Array(numCustomers)
      val numCanUse: Array[Int] = new Array(numCustomers)

      testCase.customers.foreach { customer =>
        val id = customer.id
        customer.shakes.foreach { milkShake =>
          if (milkShake.shakeType == Malted) {
            maltedByCustomer(id) = milkShake
          } else {
            shakesCanUse(id)(milkShake.id) = true
            numCanUse(id) += 1
          }
        }
      }
      s"Case #${testCase.id}: ${calculate(maltedByCustomer, shakesCanUse, numCanUse)}"
    }
  }

  def calculate(maltedByCustomer: Array[MilkShake], canUse: Array[Array[Boolean]], numCanUse: Array[Int]): String = {
    val numCustomers = maltedByCustomer.length
    val numShakes = canUse(0).length
    val choice: Array[Boolean] = new Array(numShakes)
    val visited: Array[Boolean] = new Array(numCustomers)
    var possible = true
    var found = true

    val isPossible = Try {
      while (found && possible) {
        found = false
        (0 until numCustomers).map { j =>
          if (numCanUse(j) == 0 && !visited(j)) {
            visited(j) = true
            found = true
            val maltedForCustomer = maltedByCustomer(j)
            if (Option(maltedForCustomer).isEmpty) {
              possible = false
              throw new RuntimeException("Stop!")
            } else {
              choice(maltedForCustomer.id) = true
              (0 until numCustomers).foreach { k =>
                if (canUse(k)(maltedForCustomer.id)) {
                  numCanUse(k) -= 1
                  canUse(k)(maltedForCustomer.id) = false
                }
              }
            }
          }
          j
        }
      }
    } isSuccess

    if (isPossible) choice.map(if (_) "1" else "0").mkString(" ") else "IMPOSSIBLE"
  }
}