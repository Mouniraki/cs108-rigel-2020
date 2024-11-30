package ch.epfl.rigel.gui;

import javafx.animation.AnimationTimer;
import javafx.beans.property.ObjectProperty;
import javafx.beans.property.ReadOnlyBooleanProperty;
import javafx.beans.property.SimpleBooleanProperty;
import javafx.beans.property.SimpleObjectProperty;
import java.time.ZonedDateTime;

/**
 * The time animator that uses the elapsed time in the real world
 * to change the time in the simulation.
 *
 * @author Nicolas Szwajcok (315213)
 */
public final class TimeAnimator extends AnimationTimer {
    private final DateTimeBean dateTimeBean;
    private final ObjectProperty<TimeAccelerator> accelerator;
    private final SimpleBooleanProperty running;
    private int counter;
    private long time;

    /**
     * Creates an instance of a time animator.
     *
     * @param dateTimeBean The date time bean at the moment of creation of the time animator
     */
    public TimeAnimator(DateTimeBean dateTimeBean){
        this.dateTimeBean = dateTimeBean;
        accelerator = new SimpleObjectProperty<>();
        running = new SimpleBooleanProperty();
        counter = 0;
    }

    /**
     * Updates the DateTime bean by the real world time. The real world time can be accelerated by the class' animator.
     *
     * @param l The number of nanoseconds that have elapsed since an unspecified starting instance
     */
    @Override
    public void handle(long l) {
        long deltaTime = counter == 0 ? 0 : l - time;
        ZonedDateTime newZonedDateTime = getAccelerator().adjust(dateTimeBean.getZonedDateTime(), deltaTime);

        dateTimeBean.setZonedDateTime(newZonedDateTime);
        time = l;
        counter += 1;
    }

    /**
     * Starts the time animator.
     */
    @Override
    public void start(){
        super.start();
        setRunning(true);
    }

    /**
     * Stops the time animator.
     */
    @Override
    public void stop(){
        super.stop();
        counter = 0;
        setRunning(false);
    }

    /**
     * Returns the object property of the time accelerator.
     *
     * @return The object property of the time accelerator
     */
    public ObjectProperty<TimeAccelerator> acceleratorProperty(){
        return accelerator;
    }

    /**
     * Returns the time accelerator.
     *
     * @return The time accelerator
     */
    public TimeAccelerator getAccelerator(){
        return accelerator.get();
    }

    /**
     * Sets the value of the time accelerator.
     */
    public void setAccelerator(TimeAccelerator accelerator){
        this.accelerator.setValue(accelerator);
    }

    /**
     * Returns the read-only boolean property informing if the time animator is currently running.
     *
     * @return The read-only boolean object informing if the time animator is currently running
     */
    public ReadOnlyBooleanProperty runningProperty(){
        return running;
    }

    /**
     * Returns the read-only boolean property informing if the time animator is currently running.
     *
     * @return The read-only boolean object informing if the time animator is currently running
     */
    public boolean getRunning(){
        return running.get();
    }

    /**
     * Sets the read-only boolean property informing if the time animator is currently running.
     */
    private void setRunning(Boolean running){
        this.running.setValue(running);
    }
}
