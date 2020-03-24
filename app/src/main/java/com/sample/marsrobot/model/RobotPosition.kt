package com.sample.marsrobot.model

class RobotPosition(

    var x: Int = 0,

    var y: Int = 0,

    var direction: Direction = Direction.N
) {
    enum class Direction {
        N, S, E, W
    }
}