package study.common

import org.scalatest.FlatSpec

class CalculateTest extends FlatSpec {

  "plus(num1, num2)" should "one plus two is three" in {
    assert(Calculate.plus(1, 2) == 3)
  }
}
