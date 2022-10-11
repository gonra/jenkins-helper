package com.intuit.benten;

import com.offbytwo.jenkins.JenkinsServer;
import com.offbytwo.jenkins.client.JenkinsHttpClient;
import com.offbytwo.jenkins.model.Job;
import com.offbytwo.jenkins.model.JobWithDetails;
import com.offbytwo.jenkins.model.QueueItem;
import com.offbytwo.jenkins.model.QueueReference;

import java.net.URI;
import java.util.Map;

public class JenkinsHelper {

    private String JENKIN_URL = "http://localhost:8090";
    private String JENKIN_USER= "API";
    private String JENKIN_TOKEN= "API123";

    private JenkinsServer jenkins;
    private JenkinsHttpClient jenkinsHttpClient;

    public JenkinsServer getJenkins() {
        return jenkins;
    }

    public JenkinsHttpClient getJenkinsHttpClient() {
        return jenkinsHttpClient;
    }

    public void init(){
        try{
            jenkins = new JenkinsServer(new URI(JENKIN_URL), JENKIN_USER, JENKIN_TOKEN);
            jenkinsHttpClient = new JenkinsHttpClient(new URI(JENKIN_URL), JENKIN_USER, JENKIN_TOKEN);

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public JobWithDetails getJobByJobName(String jobName){
        try{
            return jenkins.getJob(jobName);
        }catch (Exception e){
            e.printStackTrace();
        }
        return null;
    }

    public String build(String jobName){

        try {
            Job job = jenkins.getJob(jobName);

            int lastBuildNumber = job.details().getLastBuild().getNumber();
            int nextBuildNumber = job.details().getNextBuildNumber();

            QueueReference queueReference = job.build(true);

            int waitFor = 0;
            while(job.details().isInQueue()){
                waitFor++;

                Thread.sleep(5000);
                if(waitFor>4) return "Job is built successfully, but is in Queue";

            }
            JobWithDetails jobWithDetails =job.details();
            if(job.details().getBuildByNumber(nextBuildNumber).details().isBuilding()) {

                return "Jenkins job "+ jobName +" is building with Build Number: " + nextBuildNumber;
            }
            else {

                return "Jenkins job is stuck for :" + jobName;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String build(String jobName, Map<String,String> parameters){

        try {
            Job job = jenkins.getJob(jobName);

            int lastBuildNumber = job.details().getLastBuild().getNumber();
            int nextBuildNumber = job.details().getNextBuildNumber();

            QueueReference queueReference = job.build(parameters,true);

            int waitFor = 0;
            while(job.details().isInQueue()){
                waitFor++;

                Thread.sleep(5000);
                if(waitFor>4) return "Job is built successfully, but is in Queue";

            }
            JobWithDetails jobWithDetails =job.details();
            if(job.details().getBuildByNumber(nextBuildNumber).details().isBuilding()) {

                return "Jenkins job "+ jobName +" is building with Build Number: " + nextBuildNumber;
            }
            else {

                return "Jenkins job is stuck for :" + jobName;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public boolean getStatusFinishedJobWithBuildNumber(String jobName, int buildNumber){
        try {
            int last = jenkins.getJob(jobName).getLastBuild().getNumber();
            return (buildNumber == last);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public String showConsoleLogForJobWithBuildNumber(String jobName, int buildNumber){

        try {
            String fullLog = jenkins.getJob(jobName).getBuildByNumber(buildNumber).details().getConsoleOutputText();
            return fullLog.substring(fullLog.length()-1000, fullLog.length()-1);
        }catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public int getLatestBuildNumber(String jobName){
        try{
            return jenkins.getJob(jobName).getLastBuild().getNumber();
        }catch (Exception e){
            e.printStackTrace();
        }
        return -1;
    }

    public boolean waitFinish(String jobName, int buildNumber){
        boolean p = getStatusFinishedJobWithBuildNumber(jobName,buildNumber);
        while (!p){
            try {
                Thread.sleep(1000);
            } catch (Exception e){
                e.printStackTrace();
                return false;
            }
            p = getStatusFinishedJobWithBuildNumber(jobName,buildNumber);
        }
        return p;

    }


}
