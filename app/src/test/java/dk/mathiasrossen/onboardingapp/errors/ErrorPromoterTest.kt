package dk.mathiasrossen.onboardingapp.errors

import dk.mathiasrossen.onboardingapp.utils.errors.DismissError
import dk.mathiasrossen.onboardingapp.utils.errors.ErrorPromoter
import org.junit.Assert.assertEquals
import org.junit.Assert.assertTrue
import org.junit.Test

class ErrorPromoterTest {
    private val errorPromoter = ErrorPromoter()
    private val error = createError()

    @Test
    fun init_errorListIsEmpty() {
        assertTrue(errorPromoter.errors.value.isEmpty())
    }

    @Test
    fun submitError_errorIsAddedToList() {
        errorPromoter.submitError(error)

        assertTrue(errorPromoter.errors.value.isNotEmpty())
        assertEquals(error, errorPromoter.errors.value.last())
    }

    @Test
    fun submitGenericError_newDismissErrorIsAddedToList() {
        errorPromoter.submitGenericError()

        assertTrue(errorPromoter.errors.value.isNotEmpty())
        assertTrue(errorPromoter.errors.value.last() is DismissError)
    }

    @Test
    fun dismissError_errorIsRemovedFromList() {
        errorPromoter.submitError(error)
        errorPromoter.dismissError(error)

        assertTrue(errorPromoter.errors.value.isEmpty())
    }

    @Test
    fun dismissAllErrors_errorListIsEmpty() {
        errorPromoter.submitError(createError(0))
        errorPromoter.submitError(createError(1))
        errorPromoter.submitError(createError(2))
        errorPromoter.dismissAllErrors()

        assertTrue(errorPromoter.errors.value.isEmpty())
    }

    private fun createError(mockResource: Int = 0) = DismissError(mockResource)
}
