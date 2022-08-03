package com.fb.easy;

import java.util.HashSet;
import java.util.Set;

public abstract class Job {

    private static final Set<Job> jobs = new HashSet<>();
    private OnStart onStartListener;
    private OnStop onStopListener;

    protected void callStopListener() {
        if (onStopListener != null) onStopListener.onStop();
    }

    protected void callStartListener() {
        if (onStartListener != null) onStartListener.onStart();
    }

    public static void stopAll() {
        for (Job job : jobs) {
            job.stop();
        }
    }

    protected static void addJob(Job job) {
        jobs.add(job);
    }

    protected static void removeJob(Job job) {
        jobs.remove(job);
    }

    public void setOnStartListener(OnStart onStart) {

        this.onStartListener = onStart;
    }

    public void setOnStopListener(OnStop onStop) {

        this.onStopListener = onStop;
    }

    public abstract void stop();

    public abstract void start();

    public abstract boolean isRunning();

    public interface OnStart {
        void onStart();
    }

    public interface OnStop {
        void onStop();
    }
}
