package com.extopy.presentation.viewmodels

/**
 * How many rows a screen asks the server for at a time.
 *
 * Every paginated screen reads the same amount, so a list that reaches its end can be recognised by
 * a page coming back empty rather than by tracking a total the server never sends.
 */
internal const val PAGE_SIZE = 25L
