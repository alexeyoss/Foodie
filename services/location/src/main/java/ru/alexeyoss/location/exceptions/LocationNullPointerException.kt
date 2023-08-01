package ru.alexeyoss.location.exceptions

internal class LocationNullPointerException : NullPointerException() {

    override val message: String = "Location is null"
}