package com.tiagomissiato.challenge.repository.base

import io.mockk.MockKAnnotations
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.junit.Before
import org.junit.Rule

@OptIn(ExperimentalCoroutinesApi::class)
open class BaseTest {
    @get:Rule
    val mainDispatcherRule = MainDispatcherRule()

    @Before
    fun setup() {
        MockKAnnotations.init(this)
    }
}