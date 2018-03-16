package dev.yamin.cryptcurrencyacademy.alerts.job;

import android.os.Bundle;

import com.firebase.jobdispatcher.Constraint;
import com.firebase.jobdispatcher.FirebaseJobDispatcher;
import com.firebase.jobdispatcher.GooglePlayDriver;
import com.firebase.jobdispatcher.Job;
import com.firebase.jobdispatcher.Lifetime;
import com.firebase.jobdispatcher.RetryStrategy;
import com.firebase.jobdispatcher.Trigger;

import dev.yamin.cryptcurrencyacademy.base.App;

/**
 * Created by Yamin on 3/16/2018.
 */

public class JobDispatcherManager {

    private static final JobDispatcherManager instance = new JobDispatcherManager();

    private JobDispatcherManager() { }

    public static JobDispatcherManager getInstance() {
        return instance;
    }

    public void dispatchJob() {
        FirebaseJobDispatcher dispatcher = new FirebaseJobDispatcher(new GooglePlayDriver(App.getContext()));

        Bundle myExtrasBundle = new Bundle();
        myExtrasBundle.putString("some_key", "some_value");

        Job myJob = dispatcher.newJobBuilder()
                // the JobService that will be called
                .setService(AlertJobDispatcher.class)
                // uniquely identifies the job
                .setTag("my-unique-tag")
                // one-off job
                .setRecurring(false)
                // persist past device reboot
                .setLifetime(Lifetime.FOREVER)
                // start between 0 and 60 seconds from now
                .setTrigger(Trigger.executionWindow(0, 60))
                // don't overwrite an existing job with the same tag
                .setReplaceCurrent(false)
                // retry with exponential backoff
                .setRetryStrategy(RetryStrategy.DEFAULT_EXPONENTIAL)
                // constraints that need to be satisfied for the job to run
                .setConstraints(
                        Constraint.ON_ANY_NETWORK
                )
                .setExtras(myExtrasBundle)
                .build();

        dispatcher.mustSchedule(myJob);

    }

}
