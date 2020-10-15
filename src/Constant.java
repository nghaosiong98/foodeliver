public class Constant {
    public enum OrderStatus {
        OPEN("open"),
        SELF_COLLECT("self-collect"),
        PENDING("pending"),
        PREPARING("preparing"),
        READY("ready"),
        COLLECTED("collected"),
        DELIVERED("delivered");

        private final String status;

        OrderStatus(String status) {
            this.status = status;
        }

        public String getStatus() {
            return status;
        }
    }
}
