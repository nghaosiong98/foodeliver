public class Constant {
    public enum OrderStatus {
        OPEN("open"),
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
