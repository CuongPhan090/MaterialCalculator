package com.example.materialcalculator.domain


class ExpressionParser(
    private val calculation: String
) {

    fun parse(): List<ExpressionPart> {
        val result = mutableListOf<ExpressionPart>()

        var i = 0
        while (i < calculation.length) {
            val currentChar = calculation[i]
            when {
                currentChar in operationSymbols -> {
                    result.add(
                        ExpressionPart.Op(
                            operationFromSymbol(currentChar)
                        )
                    )
                }
                currentChar in "()" -> {
                    parseParenthesis(currentChar, result)
                }
                currentChar.isDigit() -> {
                    i = parseNumber(i, result)
                    continue
                }
            }
            i++
        }

        return result
    }

    private fun parseNumber(startingIndex: Int, result: MutableList<ExpressionPart>): Int {
        var currentIndex = startingIndex
        val numberAsString = buildString {
            while (currentIndex < calculation.length && calculation[currentIndex] in "0123456789.") {
                append(calculation[currentIndex])
                currentIndex++
            }
        }

        result.add(ExpressionPart.Number(numberAsString.toDouble()))
        return currentIndex
    }

    private fun parseParenthesis(
        char: Char,
        result: MutableList<ExpressionPart>
    ) {
        result.add(
            ExpressionPart.Parentheses(
                when (char) {
                    '(' -> ParenthesesType.Opening
                    ')' -> ParenthesesType.Closing
                    else -> throw IllegalArgumentException("Invalid parentheses type")
                }
            )
        )
    }
}
