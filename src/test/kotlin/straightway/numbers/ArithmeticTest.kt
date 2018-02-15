/*
 * Copyright 2016 github.com/straightway
 *
 *  Licensed under the Apache License, Version 2.0 (the &quot;License&quot;);
 *  you may not use this file except in compliance with the License.
 *  You may obtain a copy of the License at
 *
 *  http://www.apache.org/licenses/LICENSE-2.0
 *
 *  Unless required by applicable law or agreed to in writing, software
 *  distributed under the License is distributed on an &quot;AS IS&quot; BASIS,
 *  WITHOUT WARRANTIES OR CONDITIONS OF ANY KIND, either express or implied.
 *  See the License for the specific language governing permissions and
 *  limitations under the License.
 */
package straightway.numbers

import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Assertions.assertTrue
import org.junit.jupiter.api.Test
import java.lang.Math.nextDown
import java.lang.Math.nextUp
import java.lang.Math.rint
import java.math.BigDecimal
import java.math.BigInteger

class ArithmeticTest {

    @Test
    fun plus() =
            testArithmeticOperator { a, b ->
                expected = a.toInt() + b.toInt()
                actual = a + b
            }

    @Test
    fun minus() =
            testArithmeticOperator { a, b ->
                expected = a.toInt() - b.toInt()
                actual = a - b
            }

    @Test
    fun mul() =
            testArithmeticOperator { a, b ->
                expected = a.toInt() * b.toInt()
                actual = a * b
            }

    @Test
    fun div() =
            testArithmeticOperator { a, b ->
                expected = a.toInt() / b.toInt()
                actual = a / b
            }

    @Test
    fun rem() =
            testArithmeticOperator { a, b ->
                expected = a.toInt() % b.toInt()
                actual = a % b
            }

    @Test
    fun unaryPlus() =
            testArithmeticOperator { a ->
                expected = a.toInt()
                actual = +a
            }

    @Test
    fun unaryMinus() =
            testArithmeticOperator { a ->
                expected = -a.toInt()
                actual = -a
            }

    @Test
    fun round_general() =
            testArithmeticOperator { a ->
                expected = rint(a.toDouble()).toInt()
                actual = round(a)
            }

    @Test
    fun round_Float_upPositive() =
            TestOpResult {
                expected = 5
                actual = round(nextUp(4.5F))
            }.check()

    @Test
    fun round_Float_downPositive() =
            TestOpResult {
                expected = 4
                actual = round(nextDown(4.5F))
            }.check()

    @Test
    fun round_Float_upNegative() =
            TestOpResult {
                expected = -4
                actual = round(nextUp(-4.5F))
            }.check()

    @Test
    fun round_Float_downNegative() =
            TestOpResult {
                expected = -5
                actual = round(nextDown(-4.5F))
            }.check()

    @Test
    fun round_Double_upPositive() =
            TestOpResult {
                expected = 5
                actual = round(nextUp(4.5))
            }.check()

    @Test
    fun round_Double_downPositive() =
            TestOpResult {
                expected = 4
                actual = round(nextDown(4.5))
            }.check()

    @Test
    fun round_Double_upNegative() =
            TestOpResult {
                expected = -4
                actual = round(nextUp(-4.5))
            }.check()

    @Test
    fun round_Double_downNegative() =
            TestOpResult {
                expected = -5
                actual = round(nextDown(-4.5))
            }.check()

    @Test
    fun round_BigDecimal_upPositive() =
            TestOpResult {
                expected = 5
                actual = round(BigDecimal("4.5"))
            }.check()

    @Test
    fun round_BigDecimal_downPositive() =
            TestOpResult {
                expected = 4
                actual = round(BigDecimal("4.499999999999999"))
            }.check()

    @Test
    fun round_BigDecimal_upNegative() =
            TestOpResult {
                expected = -4
                actual = round(BigDecimal("-4.49999999999999"))
            }.check()

    @Test
    fun round_BigDecimal_downNegative() =
            TestOpResult {
                expected = -5
                actual = round(BigDecimal("-4.5"))
            }.check()

    @Test
    fun bigDecimalDivisionPrecision() {
        val a = 1.0
        val b = 7e10
        val expectedResult = BigDecimal(a / b)
        val actualResult = BigDecimal(a) / BigDecimal(b)
        val difference = (expectedResult - actualResult) as BigDecimal
        assertTrue(difference.abs() < BigDecimal(1e-20))
    }

    @Test
    fun bigDecimal_toBigInteger() {
        val bi = BigInteger(
                "1234567890123456789012345678901234578901234567890" +
                        "1234567890123456789012345678901234578901234567890")
        val bd = BigDecimal("1")
        val r = bi * bd
        assertEquals(bi, (r as BigDecimal).toBigInteger())
    }

    @Test
    fun typeOverflow_plus() =
            IntegerValueBorderTest("Overflow", { Pair(max, max) }, { this + it }).test()

    @Test
    fun typeUnderflow_plus() =
            IntegerValueBorderTest("Underflow", { Pair(min, min) }, { this + it }).test()

    @Test
    fun typeOverflow_minus() =
            IntegerValueBorderTest("Overflow", { Pair(max, min) }, { this - it }).test()

    @Test
    fun typeUnderflow_minus() =
            IntegerValueBorderTest("Underflow", { Pair(min, max) }, { this - it }).test()

    @Test
    fun typeOverflow_times() =
            IntegerValueBorderTest("Overflow", { Pair(max, max) }, { this * it }).test()

    @Test
    fun typeUnderflow_times() =
            IntegerValueBorderTest("Underflow", { Pair(min, min) }, { this * it }).test()

    @Test
    fun typeOverflow_div() =
            IntegerValueBorderTest("Overflow", { Pair(min, (-1).toByte()) }, { this / it }).test()

    @Test
    fun typeAdherence_plus() =
            testTypeAdherence { this + it }

    @Test
    fun typeAdherence_minus() =
            testTypeAdherence { this - it }

    @Test
    fun typeAdherence_times() =
            testTypeAdherence { this * it }

    @Test
    fun typeAdherence_div() =
            testTypeAdherence { this / it }

    @Test
    fun typeAdherence_rem() =
            testTypeAdherence { this % it }

    @Test
    fun typeAdherence_round() =
            testTypeAdherence { round(this) }

    @Test
    fun comparison_less() =
            testComparison { it[0] < it[1] }

    @Test
    fun comparison_lessEqual() =
            testComparison { it[0] <= it[1] && it[0] <= it[0] }

    @Test
    fun comparison_greater() =
            testComparison { it[1] > it[0] }

    @Test
    fun comparison_greaterEqual() =
            testComparison { it[1] >= it[0] && it[0] >= it[0] }

    //region Private

    private class TestOpResult(val tester: TestOpResult.() -> Unit) {
        var expected: Int = 0
        var actual: Number = 0
        fun check() {
            tester()
            assertEquals(expected, actual.toInt())
        }
    }

    private companion object {
        fun testArithmeticOperator(test: TestOpResult.(Number, Number) -> Unit) =
                testValues.forEach { a -> testArithmeticOperator { b -> test(a, b) } }

        fun testArithmeticOperator(test: TestOpResult.(Number) -> Unit) =
                testValues.forEach { TestOpResult { test(it) }.check() }

        private class IntegerValueBorderTest(
                val aspect: String,
                val testValueGetter: NumberInfo.() -> Pair<Number?, Number?>,
                val testedOperation: Number.(Number) -> Number) {

            fun test() =
                    testValues.forEach {
                        val (extremeA, extremeB) = NumberInfo[it].testValueGetter()
                        if (extremeA != null && extremeB != null) {
                            val expected = BigInteger(extremeA.toString())
                                    .testedOperation(BigInteger(extremeB.toString()))
                            val actual = BigInteger(extremeA.testedOperation(extremeB).toString())
                            assertEquals(expected, actual) {
                                "$aspect test for type ${it::class} failed"
                            }
                        }
                    }
        }

        private fun testTypeAdherence(op: Number.(Number) -> Number) =
                testValues.forEach {
                    assertEquals(it::class, it.op(it)::class)
                }

        private fun testComparison(tester: (Array<Number>) -> Boolean) =
                testValues.forEach {
                    val toTargetType = NumberInfo[it].unify
                    val args = arrayOf(1.toTargetType(), 2.toTargetType())
                    assertTrue(tester(args)) { "Testing ${it::class}" }
                }

        val testValues = arrayOf<Number>(
                1.toByte(),
                2.toShort(),
                3,
                4L,
                5.0F,
                6.0,
                BigInteger("7"),
                BigDecimal("8.0"))
    }

    //endregion
}