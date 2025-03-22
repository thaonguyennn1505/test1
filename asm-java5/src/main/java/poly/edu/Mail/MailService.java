package poly.edu.Mail;

public interface MailService {

    @lombok.Data
    @lombok.Builder
    public static class Mail {
        @lombok.Builder.Default
        private String from = "WebShop <web-shop@gmail.com>";
        private String to;
        private String cc;
        private String bcc;
        private String subject;
        private String body;
        private String filenames;
    }

    void send(Mail mail);

    default void send(String to, String subject, String body) {
        Mail mail = Mail.builder()
                        .to(to)
                        .subject(subject)
                        .body(body)
                        .build();
        this.send(mail);
    }

    // Thêm phương thức push vào hàng đợi
    void push(Mail mail);

    default void push(String to, String subject, String body) {
        this.push(Mail.builder()
                      .to(to)
                      .subject(subject)
                      .body(body)
                      .build());
    }
}


