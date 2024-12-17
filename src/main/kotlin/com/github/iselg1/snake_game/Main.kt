package com.github.iselg1.snake_game

import com.github.iselg1.snake_game.common.*
import com.github.iselg1.snake_game.snake.SnakePartType
import com.github.iselg1.snake_game.snake.eat
import com.github.iselg1.snake_game.snake.plus
import com.github.iselg1.snake_game.snake.withDirection
import pt.isel.canvas.*

// The global tick speed. The higher this is, the faster everything goes.
const val GLOBAL_TICK_SPEED = 1

// The different ticking speed for both the snake and brick actions (every x ms)
const val SNAKE_TICK_SPEED = 150
const val BRICK_TICK_SPEED = 5 * 1000

// The width of the board measured in grid squares
const val BOARD_WIDTH = 20
const val BOARD_HEIGHT = 16
const val TOTAL_SQUARES = BOARD_HEIGHT * BOARD_WIDTH

// The size in pixels of one of the square's sides and the label height
const val SQUARE_DIMENSIONS = 32
const val LABEL_HEIGHT = SQUARE_DIMENSIONS * 2

// The size the snake should gain by eating an apple
const val NUTRITION = 5

// The main arena and game where everything will be drawn on
val arena = Canvas(BOARD_WIDTH * SQUARE_DIMENSIONS, (BOARD_HEIGHT * SQUARE_DIMENSIONS) + LABEL_HEIGHT, BLACK)
var game = emptyGame()

/**
 * Main entrypoint for the program, kick off the main logic flow and spawn the snake
 */
fun main() {


    onStart {

        // Spawns the snake and adds the initial bricks and apple, alongside any other resources
        spawnSnake()
        placeInitialBricks()
        game.newApple(arena)
        game.drawLabel()

        // Starts the ticking process for both the snake and the brick
        arena.onTimeProgress(SNAKE_TICK_SPEED * (1 / GLOBAL_TICK_SPEED)) { onSnakeTick() }
        arena.onTimeProgress(BRICK_TICK_SPEED * (1 / GLOBAL_TICK_SPEED)) { onBrickTick() }
        arena.onKeyPressed { key -> onKeyPressed(key) }
    }
    onFinish { }
}

/**
 * Spawns in the snake, creating a head-typed snake part d a tail.
 */
fun spawnSnake() {

    // Create the snake head and add it to the snake parts
    val centerHeight = BOARD_HEIGHT / 2
    val centerWidth = BOARD_WIDTH / 2

    var newSnake = game.snake.plus(SnakePartType.HEAD, Position(centerWidth, centerHeight))
    newSnake = newSnake.plus(SnakePartType.TAIL, Position(centerWidth-1, centerHeight), game.snake.direction, true)

    game = game.withSnake(newSnake.eat(3))
}

/**
 * Places the initial bricks within the screen
 */
fun placeInitialBricks() {

    val bricks = listOf(

        // The top left corner
        Position(0,0),
        Position(0,1),
        Position(0,2),
        Position(0,3),
        Position(1,0),
        Position(2,0),

        // The top right corner
        Position(17,0),
        Position(18,0),
        Position(19,0),
        Position(19,1),
        Position(19,2),
        Position(19,3),

        // The bottom left corner
        Position(0,12),
        Position(0,13),
        Position(0,14),
        Position(0,15),
        Position(1,15),
        Position(2,15),

        // The bottom right corner
        Position(17,15),
        Position(18,15),
        Position(19,12),
        Position(19,13),
        Position(19,14),
        Position(19,15),
    )

    game = game.withBricks(bricks)
}

/**
 * Fired whenever a key is pressed;
 * Checks if the keys are WASD/UP, DOWN, LEFT, RIGHT, and changes the direction accordingly.
 * @param key Use the pressed key to change de direction
 */
fun onKeyPressed(key: KeyEvent) {

    // Only process key events if the queued direction hasn't been processed
    if (game.queuedDirection != Direction.NONE) return;

    // Get the direction associated with the key pressed.
    // If the key isn't mapped to a direction, return.
    val direction = getDirectionFor(key.code) ?: return

    // Prevent the snake from moving in opposite directions
    if (game.snake.direction.isOpposite(direction)) return;

    game = game.withDirectionQueue(direction)
}

/**
 * Fired whenever a brick drawing event occurs;
 * Clears the arena and draws all the bricks in their positions, generating one more brick
 */
fun onBrickTick() {

    // Checks if all the squares have been filled, and if so, stops generating them
    if (game.bricks.size == TOTAL_SQUARES - game.snake.body.size) return

    // Generates a new brick and draws it on screen, excluding the snake body positions
    val exclusionList = game.snake.body.map { part -> part.position }

    game = game.withBricks(game.bricks.plus(game.generateNewPosition(exclusionList)))
    game.drawBricks()
}

/**
 * Fired whenever a snake drawing event occurs
 * Clears the arena and draws the snake parts in their positions
 */
fun onSnakeTick() {

    if (game.isSnakeBlocked()) return;

    // Get the queued input, or maintain the current direction if there's none
    val directionInput = if (game.queuedDirection != Direction.NONE) game.queuedDirection else game.snake.direction
    val headPosition = game.snake.body.first().position

    // If the snake is about to "snap its neck" with a bad move, ignore the last input.
    val correctedInput = if (game.isSnakeObstructed(directionInput)) game.snake.direction else directionInput
    game = game.withSnake(game.snake.withDirection(correctedInput)).withDirectionQueue(Direction.NONE)

    // If the next position contains a brick, stop the snake.
    if (game.isSnakeObstructed(correctedInput)) return

    // If the snake has eaten an apple, increment the score, generate a new apple and mark it having eaten
    val nextHeadPosition = headPosition.applyDirection(correctedInput)

    if (nextHeadPosition.isEqual(game.apple)) {
        game = game.incrementScore()
        game = game.withSnake(game.snake.eat())
        game.newApple(arena)
    }

    game = game.withSnake(game.calculateSnakeMovement(correctedInput, nextHeadPosition))
    game.drawSnake(game.snake.body)
}