package es.jolusan.recipesapp.utils

import org.junit.Assert.*

import org.junit.Test

class ExtensionsTest {

    @Test
    fun `when apply upperCaseFirst method return string with first letter capitalized`() {
        // Given
        var string1 = "test1"
        var string2 = "Test2 test2"
        var string3 = " test3"
        var string4 = "3test"
        var string5 = "t"
        var emptyString = ""

        // When
        string1 = string1.upperCaseFirst()
        string2 = string2.upperCaseFirst()
        string3 = string3.upperCaseFirst()
        string4 = string4.upperCaseFirst()
        string5 = string5.upperCaseFirst()
        emptyString = emptyString.upperCaseFirst()

        // Then
        assertEquals("Test1", string1)
        assertEquals("Test2 test2", string2)
        assertEquals(" test3", string3)
        assertEquals("3test", string4)
        assertEquals("T", string5)
        assertEquals("", emptyString)
    }
}