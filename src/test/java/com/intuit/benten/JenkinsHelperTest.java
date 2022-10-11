package com.intuit.benten;

import com.offbytwo.jenkins.model.Job;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class JenkinsHelperTest {

    private JenkinsHelper jk;
    private String jobName1 = "demo1";
    private String jobName2 = "demo2";

    @Test
    public void testJenkins1(){ // valida job
        jk = new JenkinsHelper();
        jk.init();
        Job job1 = jk.getJobByJobName (jobName1);
        Job job2 = jk.getJobByJobName(jobName2);
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
    }


    @Test
    public void testJenkins3() { // job com parametros
        jk = new JenkinsHelper();
        jk.init();
        Map<String,String> parametros = new HashMap<>();
        parametros.put("MSISDN","12345678");
        String retorno_build_start = jk.build(jobName2,parametros);

        if ((retorno_build_start != null) && retorno_build_start.contains("Build Number:")) {
            String build_str = retorno_build_start.substring(retorno_build_start.indexOf("Build Number:" + 14)).trim();
            int build_number = Integer.parseInt(build_str);
            jk.waitFinish(jobName1, build_number);
            String retorno = jk.showConsoleLogForJobWithBuildNumber(jobName1, build_number);
            System.out.println(retorno);
        }
        assertTrue(retorno_build_start != null);
    }
}
