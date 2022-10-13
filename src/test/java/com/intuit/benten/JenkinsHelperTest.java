package com.intuit.benten;

import com.offbytwo.jenkins.model.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class JenkinsHelperTest {

    private JenkinsHelper jk;
    private String jobName2 = "demo2";

/*    @Test
    public void testJenkins1(){ // valida job
        jk = new JenkinsHelper();
        jk.init();
        JobWithDetails job1 = jk.getJobByJobName(jobName1);
        JobWithDetails job2 = jk.getJobByJobName(jobName2);
        assertTrue(job1 != null);
        assertTrue(job2 != null);
    }

    @Test
    public void testJenkins2() { // job sem parametros
        jk = new JenkinsHelper();
        jk.init();
        String retorno_build_start = jk.build(jobName1);

        if ((retorno_build_start != null) && retorno_build_start.contains("Build Number:")) {
            String build_str = retorno_build_start.substring(retorno_build_start.indexOf("Build Number:" + 14)).trim();
            int build_number = Integer.parseInt(build_str);
            jk.waitFinish(jobName1, build_number);
            String retorno = jk.showConsoleLogForJobWithBuildNumber(jobName1, build_number);
            System.out.println(retorno);
        }
        assertTrue(retorno_build_start != null);
    }*/


    @Test
    public void testJenkins3() { // job com parametros
        jk = new JenkinsHelper();
        jk.init();
        int lastId=-1;
        int nextId=-1;
        Map<String,String> parametros = new HashMap<>();
        parametros.put("MSISDN","12345678");
        JobWithDetails job2 = jk.getJobByJobName(jobName2);

        lastId = job2.getLastBuild().getNumber();
        nextId = job2.getNextBuildNumber();
        System.out.println("salidalast:" + lastId );
        System.out.println("salidanext:" + nextId );

        try {
            QueueReference queue = job2.build(parametros, true);
            QueueItem queueItem = null;
            int waitFor = 0;
            while (job2.details().isInQueue()) {
                waitFor++;
                Thread.sleep(5000);
                if (waitFor > 12) {
                    break;
                }
            }
            System.out.println("FIMQUEUE1:" + waitFor);
            waitFor = 0;
            do {
                waitFor++;
                Thread.sleep(5000);
                if (waitFor > 12) {
                    break;
                }
                queueItem = jk.getJenkins().getQueueItem(queue);
            } while (queueItem.getExecutable() == null);
            System.out.println("FIMQUEUE2:" + waitFor);
            Build build = jk.getJenkins().getBuild(queueItem);
            waitFor = 0;
            while(build.details().isBuilding()){
                waitFor++;
                Thread.sleep(5000);
                if (waitFor > 12) {
                    break;
                }
            }
            System.out.println("FIMQUEUE3:" + waitFor);

            String x1 = build.details().getConsoleOutputText();
            int p1 = x1.indexOf("---CHIP");
            if (p1 > 0) {
                System.out.println("Respuesta:" + x1.substring(p1+10,p1+30));
            }
        } catch (Exception e){
            e.printStackTrace();
        }

        assertTrue(job2 != null);
    }
}
