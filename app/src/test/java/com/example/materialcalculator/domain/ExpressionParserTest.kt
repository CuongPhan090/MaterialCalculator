package com.example.materialcalculator.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Test

class ExpressionParserTest {

    private lateinit var parser: ExpressionParser

    @Test
    fun `Simple expression is properly parsed`() {
        // GIVEN
        parser = ExpressionParser("3+5-3x4/3")

        // WHEN
        val actual = parser.parse()

        // THEN
        val expected = listOf(
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(5.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(4.0),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Number(3.0),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Expression with parentheses is properly parsed`() {
        // GIVEN
        parser = ExpressionParser("3+5-(3x4)/3")

        // WHEN
        val actual = parser.parse()

        // THEN
        val expected = listOf(
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(5.0),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(4.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Number(3.0),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Expression with multiple parentheses is properly parsed`() {
        // GIVEN
        parser = ExpressionParser("(3+5)-((3x4)/3)")

        // WHEN
        val actual = parser.parse()

        // THEN
        val expected = listOf(
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(5.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Number(4.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Number(3.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing)
            )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Expression with hundreds number is properly parsed`() {
        // GIVEN
        parser = ExpressionParser("300+200/100")

        // WHEN
        val actual = parser.parse()

        // THEN
        val expected = listOf(
            ExpressionPart.Number(300.0),
            ExpressionPart.Op(Operation.ADD),
            ExpressionPart.Number(200.0),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Number(100.0),
        )

        assertThat(actual).isEqualTo(expected)
    }

    @Test
    fun `Expression with multiple parentheses is not properly parsed`() {
        // GIVEN
        parser = ExpressionParser("(3+5)-((3x4)/3)")

        // WHEN
        val actual = parser.parse()

        // THEN
        val expected = listOf(
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(3.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing),
            ExpressionPart.Op(Operation.SUBTRACT),
            ExpressionPart.Parentheses(ParenthesesType.Opening),
            ExpressionPart.Number(3.0),
            ExpressionPart.Op(Operation.MULTIPLY),
            ExpressionPart.Op(Operation.DIVIDE),
            ExpressionPart.Number(3.0),
            ExpressionPart.Parentheses(ParenthesesType.Closing)
        )

        assertThat(actual).isNotEqualTo(expected)
    }
}