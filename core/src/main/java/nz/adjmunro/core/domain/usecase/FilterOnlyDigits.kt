package nz.adjmunro.core.domain.usecase

class FilterOnlyDigits {

    operator fun invoke(input: String): String = input.filter { it.isDigit() }

}
