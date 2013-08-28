package su.litvak.vtiger;

import java.util.Map;

/**
 * Created with IntelliJ IDEA.
 * User: Vitaly
 * Date: 29.08.13
 * Time: 0:36
 * To change this template use File | Settings | File Templates.
 */
public class GetWorkflowResponse {
    public boolean success;
    public VtigerError error;
    public Map<Long, Workflow> result;

    public static class Strategy {

    }

    public static class Workflow {
        public Strategy conditionStrategy;
        public long id;
        public String moduleName;
        public String description;
        public String test;
        public long executionCondition;
        public int defaultworkflow;
    }
}
