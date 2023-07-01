package com.example.materialcalculator.domain

import com.google.common.truth.Truth.assertThat
import org.junit.Before
import org.junit.Test


class ExpressionWriterTest {

    private lateinit var writer: ExpressionWriter

    @Before
    fun setup() {
        writer = ExpressionWriter()
    }

    @Test
    fun `Initial parentheses parsed`() {

        writer.processAction(CalculatorAction.Parentheses)
        writer.processAction(CalculatorAction.Number(5))
        writer.processAction(CalculatorAction.Op(Operation.ADD))
        writer.processAction(CalculatorAction.Number(4))
        writer.processAction(CalculatorAction.Parentheses)

        assertThat(writer.expression).isEqualTo("(5+4)")
    }

    @Test
    fun `Closing parentheses at the start not parsed`() {
        writer.processAction(CalculatorAction.Parentheses)
        writer.processAction(CalculatorAction.Parentheses)

        assertThat(writer.expression).isEqualTo("((")
    }

    @Test
    fun `Parentheses around a number are not parsed`() {
        writer.processAction(CalculatorAction.Parentheses)
        writer.processAction(CalculatorAction.Number(5))
        writer.processAction(CalculatorAction.Parentheses)

        assertThat(writer.expression).isEqualTo("(5)")
    }

    @Test
    fun `Multiple parentheses are properly parsed`() {
        writer.processAction(CalculatorAction.Parentheses)
        writer.processAction(CalculatorAction.Parentheses)
        writer.processAction(CalculatorAction.Number(5))
        writer.processAction(CalculatorAction.Op(Operation.ADD))
        writer.processAction(CalculatorAction.Number(5))
        writer.processAction(CalculatorAction.Parentheses)
        writer.processAction(CalculatorAction.Op(Operation.MULTIPLY))
        writer.processAction(CalculatorAction.Parentheses)
        writer.processAction(CalculatorAction.Number(5))
        writer.processAction(CalculatorAction.Op(Operation.SUBTRACT))
        writer.processAction(CalculatorAction.Number(5))
        writer.processAction(CalculatorAction.Parentheses)
        writer.processAction(CalculatorAction.Parentheses)
        writer.processAction(CalculatorAction.Op(Operation.DIVIDE))
        writer.processAction(CalculatorAction.Number(2))


        assertThat(writer.expression).isEqualTo("((5+5)x(5-5))/2")
    }
}