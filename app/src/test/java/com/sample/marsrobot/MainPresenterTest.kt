package com.sample.marsrobot

import org.junit.Assert.assertEquals
import org.junit.Test

class MainPresenterTest {

    private val presenter = MainPresenter()

    @Test
    fun computeRobotPositionWhenIsInGrid() {
        presenter.initGrid("5", "3")

        presenter.initRobotPosition("1", "1", "E")
        var result = presenter.computeRobotPosition("RFRFRFRF")
        assertEquals("1 1 E", result)

        presenter.initRobotPosition("3", "2", "N")
        result = presenter.computeRobotPosition("FRRFLLFFRRFLL")
        assertEquals("3 3 N LOST", result)

        presenter.initRobotPosition("0", "3", "W")
        result = presenter.computeRobotPosition("LLFFFLFLFL")
        assertEquals("2 3 S", result)
    }

    @Test
    fun computeRobotPositionWhenInvalidGridValues() {
        assertEquals(false, presenter.initGrid("51", "3"))
    }

}
