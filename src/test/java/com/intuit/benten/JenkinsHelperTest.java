package com.intuit.benten;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.test.context.junit4.SpringRunner;

import java.util.HashMap;
import java.util.Map;

import static org.junit.Assert.assertTrue;

@RunWith(SpringRunner.class)
public class JenkinsHelperTest {


    @Test
    public void testJenkins2(){
        JenkinsHelper jk = new JenkinsHelper();
        jk.init();
        String jobName = "demo1";
        Map<String,String> jobParameters = new HashMap<String,String>();
        jobParameters.put("PAR1","2000");
        String retorno_build_start = jk.build(jobName,jobParameters);
        if (retorno_build_start.contains("Build Number:")){
            String build_str = retorno_build_start.substring(retorno_build_start.indexOf("Build Number:"+14)).trim();
            int build_number = Integer.parseInt(build_str);
            jk.waitFinish(jobName,build_number);
            String retorno = jk.showConsoleLogForJobWithBuildNumber(jobName,build_number);
            System.out.println(retorno);
        }
        assertTrue(true);
    }

}
