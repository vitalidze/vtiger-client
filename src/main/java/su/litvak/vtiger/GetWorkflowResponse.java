package su.litvak.vtiger;

import java.util.Map;

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
