package com.sample.marsrobot

import com.sample.marsrobot.model.Instruction
import com.sample.marsrobot.model.RobotPosition


class MainPresenter {

    private var gridX: Int = 0
    private var gridY: Int = 0

    private var robotPosition: RobotPosition? = null

    private val movedOffPositions = mutableListOf<RobotPosition>()

    fun initGrid(x: String, y: String): Boolean {
        if (x.isNotEmpty() && y.isNotEmpty()) {
            if (x.toInt() in 0..MAX_COORDINATE_VALUE && y.toInt() in 0..MAX_COORDINATE_VALUE) {
                gridX = x.toInt()
                gridY = y.toInt()
                return true
            }
        }
        return false
    }

    fun initRobotPosition(x: String, y: String, direction: String): Boolean {
        if (x.isNotEmpty() && y.isNotEmpty() && direction.isNotEmpty()) {
            if (x.toInt() in 0..MAX_COORDINATE_VALUE && y.toInt() in 0..MAX_COORDINATE_VALUE) {
                robotPosition =
                    RobotPosition(
                        x = x.toInt(),
                        y = y.toInt(),
                        direction = RobotPosition.Direction.valueOf(direction)
                    )
                return true
            }
        }
        return false
    }

    fun computeRobotPosition(instructions: String): String {
        if (instructions.isNotEmpty()) {
            robotPosition?.let { robotPosition ->
                if (instructions.length < MAX_INSTRUCTIONS_LENGTH) {
                    instructions.forEach { instructionStr ->
                        when (Instruction.valueOf(instructionStr.toString())) {
                            Instruction.F -> {
                                val oldPosX = robotPosition.x
                                val oldPosY = robotPosition.y
                                val oldDirection = robotPosition.direction
                                processForwardInstruction(robotPosition)
                                if (!checkRobotPositionInsideGrid()) {
                                    movedOffPositions.add(
                                        RobotPosition(
                                            oldPosX,
                                            oldPosY,
                                            oldDirection
                                        )
                                    )
                                    return "$oldPosX $oldPosY ${robotPosition.direction} LOST"
                                }
                            }
                            Instruction.L -> {
                                processLeftInstruction(robotPosition)
                            }
                            Instruction.R -> {
                                processRightInstruction(robotPosition)
                            }
                        }
                    }
                } else {
                    return "Invalid instructions length"
                }
                return "${robotPosition.x} ${robotPosition.y} ${robotPosition.direction}"
            } ?: kotlin.run {
                return "Robot position not initialised"
            }
        } else {
            return "Invalid robot instructions"
        }
    }

    private fun processRightInstruction(robotPosition: RobotPosition) {
        when (robotPosition.direction) {
            RobotPosition.Direction.W -> {
                robotPosition.direction = RobotPosition.Direction.N
            }
            RobotPosition.Direction.E -> {
                robotPosition.direction = RobotPosition.Direction.S
            }
            RobotPosition.Direction.N -> {
                robotPosition.direction = RobotPosition.Direction.E
            }
            RobotPosition.Direction.S -> {
                robotPosition.direction = RobotPosition.Direction.W
            }
        }
    }

    private fun processLeftInstruction(robotPosition: RobotPosition) {
        when (robotPosition.direction) {
            RobotPosition.Direction.W -> {
                robotPosition.direction = RobotPosition.Direction.S
            }
            RobotPosition.Direction.E -> {
                robotPosition.direction = RobotPosition.Direction.N
            }
            RobotPosition.Direction.N -> {
                robotPosition.direction = RobotPosition.Direction.W
            }
            RobotPosition.Direction.S -> {
                robotPosition.direction = RobotPosition.Direction.E
            }
        }
    }

    private fun processForwardInstruction(robotPosition: RobotPosition) {
        if (!isMovedOffPosition(robotPosition)) {
            when (robotPosition.direction) {
                RobotPosition.Direction.W -> {
                    robotPosition.x--
                }
                RobotPosition.Direction.E -> {
                    robotPosition.x++
                }
                RobotPosition.Direction.N -> {
                    robotPosition.y++
                }
                RobotPosition.Direction.S -> {
                    robotPosition.y--
                }
            }
        }
    }

    private fun checkRobotPositionInsideGrid(): Boolean {
        robotPosition?.let { robotPosition ->
            return (robotPosition.x in 0..gridX) && (robotPosition.y in 0..gridY)
        }
        return false
    }

    private fun isMovedOffPosition(robotPosition: RobotPosition): Boolean {
        movedOffPositions.forEach { movedOffPosition ->
            if (movedOffPosition.x == robotPosition.x
                && movedOffPosition.y == robotPosition.y
                && movedOffPosition.direction == robotPosition.direction
            ) {
                return true
            }
        }
        return false
    }

    companion object {
        const val MAX_COORDINATE_VALUE = 50
        const val MAX_INSTRUCTIONS_LENGTH = 100
    }
}
