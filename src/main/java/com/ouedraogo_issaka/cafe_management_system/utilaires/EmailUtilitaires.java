package com.ouedraogo_issaka.cafe_management_system.utilaires;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

@Service
public class EmailUtilitaires {
    
    @Autowired                

    private JavaMailSender eMailSender;

    public void envoyeSimpleMessage(String a, String sujet, String texte, List <String> list){
        SimpleMailMessage mailMessage = new SimpleMailMessage();
        mailMessage.setFrom("issakastuart@gmail.com");
        mailMessage.setTo(a);
        mailMessage.setSubject(sujet);
        mailMessage.setText(texte);

        if (list != null && list.size()>0) {
            mailMessage.setCc(getCcArray(list));   
        }
        eMailSender.send(mailMessage );
    }

    private String[] getCcArray(List<String> ccList){
        String[] cc = new String[ccList.size()];
        for(int i=0; i<ccList.size();i++){
            cc[i]=ccList.get(i);
        }
        return cc; 
    }

    public void passwordOublie(String a, String sujet, String password) throws MessagingException{
        MimeMessage message = eMailSender.createMimeMessage();
        MimeMessageHelper helper = new MimeMessageHelper(message, true);
        helper.setFrom("issakastuart@gmail.com");
        helper.setTo(a);
        helper.setSubject(sujet);

        String htmlMsg = "<p><b>Vos informations de connexion au système de gestion de café</b><br><b>E-mail: </b> " + a + " <br><b>Mot de passe: </b> " + password + "<br><a href=\"http://localhost:4200/\">Cliquez ici pour vous connecter</a></p>";
        message.setContent(htmlMsg, "text/html");

        eMailSender.send(message);

    }
}
