package org.ctrlaltdefeat.controllers.game;

/**
 * — This class is a manual timer. It is NOT based on seconds, minutes, or other traditional
 * units of time. Instead, it counts the number of renders that a GameLoop executes. More
 * precisely, it counts down from the countdownStart property until the rendersLeft property has
 * reached zero.
 * — It is also important that this is NOT an automatic timer. It does not start a new thread
 * when it executes, and it has no other forms of automation either, except for one: the timer
 * automatically resets itself when it has reached zero.
 * — Otherwise, the correct way to use this class is by instantiating it in the Class where you
 * want to use it, and then writing methods that the update() method of the GameModel can call
 * appropriately. This is very important because this way, everything will be synchronized.
 * — The timer also provides an option to manually start counting down from a value that is not
 * the predefined defaultStart value. In order to do so, use the set() method followed by the
 * start() method.
 * — The recommended use case of this class is when some action does not need to be executed
 * upon every single render, but with some delay. The value of the delay can be provided
 * as a parameter in the constructor (i.e. the 'defaultStart' parameter) of the class.
 *
 * Example:
 * Let's suppose that the GameLoop renders at 60 frames per second. If you want to execute an
 * action 3 seconds later, you would instantiate the class with a 180 start value ( 3 * 60 ).
 *
 * For other, more concrete examples see the Player and Bomb classes.
 */
public class RenderTimer {
    private int rendersLeft;
    private int countdownStart;
    private boolean countdownActive;

    public RenderTimer(int defaultStart) {
        this.countdownStart = defaultStart;
        this.rendersLeft = countdownStart;
        this.countdownActive = false;
    }

    public void start() {
        countdownActive = true;
        rendersLeft--;
    }

    /**
     * Decrease the countdown by one.
     */
    public void decrease() {
        if (rendersLeft <= 0) {
            reset();
        } else {
            rendersLeft--;
        }
    }

    /**
     * Reset the countdown to the start value. The timer automatically resets itself
     * to the countDownStart once it has reached zero. The timer can also be reset manually.
     */
    public void reset() {
        countdownActive = false;
        rendersLeft = countdownStart;
    }

    /**
     * Returns whether the countdown has finished.
     * @return true if the countdown has finished and false otherwise.
     */
    public boolean finished() {
        return !countdownActive;
    }

    /**
     * Sets the timer's start value from which is should start counting down. If you use this method,
     * make sure to call start() after it as well!
     * @param start An integer value that the timer should start counting down from.
     */
    public void set(int start) {
        countdownStart = start;
    }

    public int getCountdownStart() {
        return countdownStart;
    }
}
