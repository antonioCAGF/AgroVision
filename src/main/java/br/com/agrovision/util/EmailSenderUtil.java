package br.com.agrovision.util;

import org.springframework.stereotype.Component;

@Component
public class EmailSenderUtil {
    public void sendEmail(String email, String subject, String message) {
        System.out.println("Enviando e-mail para: " + email);
        System.out.println("Assunto: " + subject);
        System.out.println("Mensagem: " + message);
    }
}
