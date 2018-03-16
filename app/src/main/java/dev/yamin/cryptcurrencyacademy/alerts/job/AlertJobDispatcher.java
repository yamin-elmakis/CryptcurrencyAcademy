package dev.yamin.cryptcurrencyacademy.alerts.job;

import com.firebase.jobdispatcher.JobParameters;
import com.firebase.jobdispatcher.JobService;

import lib.yamin.easylog.EasyLog;

/**
 * Created by Yamin on 3/15/2018.
 */

public class AlertJobDispatcher extends JobService {

    @Override
    public boolean onStartJob(JobParameters job) {
        EasyLog.e();
        return false; // Answers the question: "Is there still work going on?"
    }

    @Override
    public boolean onStopJob(JobParameters job) {
        EasyLog.e();
        return false; // Answers the question: "Should this job be retried?"
    }
}
