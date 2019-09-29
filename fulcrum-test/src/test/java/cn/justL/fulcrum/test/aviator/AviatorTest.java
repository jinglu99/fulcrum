package cn.justL.fulcrum.test.aviator;

import com.googlecode.aviator.AviatorEvaluator;
import com.googlecode.aviator.Expression;
import org.junit.jupiter.api.Test;

import java.util.*;

/**
 * @Date : 2019/9/19
 * @Author : jingl.wang [jingl.wang123@gmail.com]
 * @Desc :
 */
public class AviatorTest {


    @Test
    public void aviatorBasicTest() {
        Expression compiledExp = AviatorEvaluator.compile("a == 1 && b == 2 && c != 1 && d == nil");

        Map<String, Object> env = new HashMap<String, Object>();
        env.put("a", 1);
        env.put("b", 2);
        env.put("c", 3);
        env.put("d", null);
        Boolean result = (Boolean) compiledExp.execute(env);
        System.out.println(result);
    }

    @Test
    public void aviatorTimeCostTest() {
        int testTimes = 100000;
        List<Long> costTimes = new ArrayList<Long>();

        Expression compiledExp = AviatorEvaluator.compile("a == 1 && b == 2 && c != 1 && d != nil");
        for (int i = 0; i < testTimes; i++) {
            long startTime = System.currentTimeMillis();
            Map<String, Object> env = new HashMap<String, Object>();
            env.put("a", 1);
            env.put("b", 2);
            env.put("c", 3);
            env.put("d", null);
            Boolean result = (Boolean) compiledExp.execute(env);
            costTimes.add(System.currentTimeMillis() - startTime);

        }

        System.out.println(costTimes.stream().mapToLong(x -> x).average());
    }
}
