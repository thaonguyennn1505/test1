package poly.edu.Mail;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;

@Controller
@RequestMapping("/mail")
public class MailController {

    @Autowired
    private MailService mailService;

    // Hiển thị form gửi email
    @RequestMapping("/form")
    public String form() {
        return "email/mailForm";
    }

    // Xử lý gửi email trực tiếp
    @PostMapping("/send-direct")
    public String sendDirect(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("cc") String cc,
            @RequestParam("bcc") String bcc,
            @RequestParam("subject") String subject,
            @RequestParam("body") String body,
            Model model) {

        MailService.Mail mail = MailService.Mail.builder()
                .from(from)
                .to(to)
                .cc(cc)
                .bcc(bcc)
                .subject(subject)
                .body(body)
                .build();

        // Gửi trực tiếp
        mailService.send(mail);

        model.addAttribute("message", "Mail đã được gửi trực tiếp!");
        return "email/mailForm";
    }

    // Xử lý xếp email vào hàng đợi
    @PostMapping("/send-queue")
    public String sendQueue(
            @RequestParam("from") String from,
            @RequestParam("to") String to,
            @RequestParam("cc") String cc,
            @RequestParam("bcc") String bcc,
            @RequestParam("subject") String subject,
            @RequestParam("body") String body,
            Model model) {

        MailService.Mail mail = MailService.Mail.builder()
                .from(from)
                .to(to)
                .cc(cc)
                .bcc(bcc)
                .subject(subject)
                .body(body)
                .build();

        // Xếp vào hàng đợi
        mailService.push(mail);

        model.addAttribute("message", "Mail đã được xếp vào hàng đợi!");
        return "email/mailForm";
    }
}
