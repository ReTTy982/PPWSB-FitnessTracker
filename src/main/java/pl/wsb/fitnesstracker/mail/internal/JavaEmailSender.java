package pl.wsb.fitnesstracker.mail.internal;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;
import pl.wsb.fitnesstracker.mail.api.EmailDto;
import pl.wsb.fitnesstracker.mail.api.EmailSender;


@Component
@RequiredArgsConstructor
@Slf4j
public class JavaEmailSender implements EmailSender {
    private final JavaMailSender javaMailSender;

    @Override


    public void send(EmailDto email) {


        log.debug("Sending the email {}", email);


        SimpleMailMessage message = new SimpleMailMessage();


        message.setFrom(email.from());


        message.setTo(email.toAddress());


        message.setSubject(email.subject());


        message.setText(email.content());


        javaMailSender.send(message);


    }



}
