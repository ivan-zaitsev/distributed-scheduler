package ua.ivan909020.scheduler.core.model.domain.scheduler;

public enum SchedulerMode {

    PARTITIONING, LEADERSHIP;

    public class Fields {

        public static final String PARTITIONING = "PARTITIONING";
        public static final String LEADERSHIP = "LEADERSHIP";

        private Fields() {
        }

    }

}
