package ua.ivan909020.scheduler.core.model.domain.instance;

public enum InstanceMode {

    PARTITIONING, LEADERSHIP;

    public class Fields {

        public static final String PARTITIONING = "PARTITIONING";
        public static final String LEADERSHIP = "LEADERSHIP";

        private Fields() {
        }

    }

}
