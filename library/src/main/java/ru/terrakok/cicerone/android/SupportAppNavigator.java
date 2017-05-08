package ru.terrakok.cicerone.android;

import android.app.Activity;
import android.content.Intent;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.FragmentManager;
import android.widget.Toast;

import ru.terrakok.cicerone.commands.BackTo;
import ru.terrakok.cicerone.commands.Command;
import ru.terrakok.cicerone.commands.Forward;
import ru.terrakok.cicerone.commands.Replace;

/**
 * Extends {@link SupportFragmentNavigator} to allow
 * open new or replace current activity.
 * <p>
 * This navigator DOESN'T provide full featured Activity navigation,
 * but can ease Activity start or replace from current navigator.
 * </p>
 *
 * @author Vasili Chyrvon (vasili.chyrvon@gmail.com)
 */
public abstract class SupportAppNavigator extends SupportFragmentNavigator {

    private Activity activity;

    public SupportAppNavigator(FragmentActivity activity, int containerId) {
        super(activity.getSupportFragmentManager(), containerId);
        this.activity = activity;
    }

    public SupportAppNavigator(FragmentActivity activity, FragmentManager fragmentManager, int containerId) {
        super(fragmentManager, containerId);
        this.activity = activity;
    }

    /**
     * Opens new screen based on the passed command.
     *
     * @param forward forward command to apply
     */
    @Override
    protected void applyForward(Forward forward) {
        Intent activityIntent = createActivityIntent(forward.getScreenKey(), forward.getTransitionData());

        // Start activity
        if (activityIntent != null) {
            activity.startActivity(activityIntent);
            return;
        }
    }

    /**
     * Replaces the current screen based on the passed command.
     *
     * @param replace replace command to apply
     */
    @Override
    protected void applyReplace(Replace replace) {
        Intent activityIntent = createActivityIntent(replace.getScreenKey(), replace.getTransitionData());

        // Replace activity
        if (activityIntent != null) {
            activity.startActivity(activityIntent);
            activity.finish();
            return;
        }
    }

    /**
     * Creates Intent to start Activity for {@code screenKey}.
     * <p>
     * <b>Warning:</b> This method does not work with {@link BackTo} command.
     * </p>
     *
     * @param screenKey screen key
     * @param data      initialization data, can be null
     * @return intent to start Activity for the passed screen key
     */
    protected abstract Intent createActivityIntent(String screenKey, Object data);

    @Override
    protected void showSystemMessage(String message) {
        // Toast by default
        Toast.makeText(activity, message, Toast.LENGTH_SHORT).show();
    }

    @Override
    protected void exit() {
        // Finish by default
        activity.finish();
    }
}