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
import org.junit.jupiter.api.Test
import java.math.BigInteger
import kotlin.reflect.KClass

class UnifyTest {

    @Test
    fun numbersAreUnifiedToLargerType() = testCases.forEach {
        val unified = unify(it.numbers.first, it.numbers.second)
        it.assertExpectedTypes(unified)
        it.assertSameDoubleRepresentationAs(unified)
    }

    private data class TestCase(
            val numbers: Pair<Number, Number>,
            private val expectedType: KClass<*>
    ) {
        fun assertExpectedTypes(toCheck: Pair<Number, Number>) =
                toCheck.toList().forEach {
                    assertEquals(expectedType, it::class) {
                        "unifying ${numbers.first::class} and ${numbers.second::class}, " +
                                "found ${it::class} instead of $expectedType"
                    }
                }

        fun assertSameDoubleRepresentationAs(other: Pair<Number, Number>) =
                numbers.toList().zip(other.toList()).forEach {
                    assertEquals(it.first.toDouble(), it.second.toDouble())
                }
    }

    private companion object {
        val testCases: Iterable<TestCase> get() {
            val result = mutableListOf<TestCase>()
            for ((i, a) in supportedTypes.withIndex())
                for ((j, b) in supportedTypes.withIndex())
                    result += TestCase(Pair(a, b), if (i < j) b::class else a::class)
            return result
        }

        val supportedTypes = arrayOf<Number>(
                83.toByte(),
                83.toShort(),
                83,
                83L,
                BigInteger("83"),
                83.0F,
                83.0)
    }
}