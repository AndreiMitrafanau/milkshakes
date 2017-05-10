package domain

case class TestCase (id: Int, numShakes : Int, customers: List[Customer])

case class Customer(id: Int, numShakesNeeded: Int, shakes : List[MilkShake])

case class MilkShake(id: Int, shakeType: ShakeType)

sealed trait ShakeType { def id: Int }
case object Unmalted extends ShakeType { val id = 0 }
case object Malted extends ShakeType { val id = 1 }