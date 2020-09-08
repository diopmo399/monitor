package africa.atps.monitornotification.services.Impl;

import africa.atps.monitornotification.services.IMailService;
import dto.Contact;
import dto.Notification;
import freemarker.template.Configuration;
import freemarker.template.Template;
import freemarker.template.TemplateException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;
import org.springframework.ui.freemarker.FreeMarkerTemplateUtils;

import javax.mail.MessagingException;
import javax.mail.internet.MimeMessage;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.HashMap;
import java.util.Map;

@Service
public class MailServiceImpl implements IMailService {
    @Autowired
    private JavaMailSender sender;

    @Autowired
    private Configuration config;

    @Override
    public Boolean sendEmail(Notification notification) {
        Map<String, Object> model = new HashMap<>();

            //
        //
        //
        // model.put("serveur", notification.getEtatServer().getServerHote().getNom() );
            model.put("serveur", notification.getServerHote().getNom() );
            model.put("tauxEchec", notification.getEtatServer().getTauxEchecHttp());
            model.put("motif", notification.getMotifTache());


        //  sender.send(msg);
        //  envoi mail with fremarker

        MimeMessage message = sender.createMimeMessage();
        try {
            MimeMessageHelper helper = new MimeMessageHelper(message, MimeMessageHelper.MULTIPART_MODE_MIXED_RELATED,
                    StandardCharsets.UTF_8.name());
            Template t = config.getTemplate("email-template.ftl");
            String html = FreeMarkerTemplateUtils.processTemplateIntoString(t, model);
            notification.getServerHote().getContacts()
                    .stream()
                    .findFirst()
                    .ifPresent(contact -> {
                        try {
                            helper.setTo(contact.getEmail());
                        } catch (MessagingException e) {
                            e.printStackTrace();
                        }
                    })
            ;
            if (notification.getServerHote().getContacts().size() > 2) {
                helper.setCc(
                        (String[]) notification.getServerHote().getContacts()
                                .stream()
                                .skip(0)
                                .map(Contact::getEmail)
                                .distinct()
                                .toArray()
                )
                ;
            }
            System.out.println("____________test___________");
            System.out.println("set to : " + notification.getServerHote().getContacts().get(0).getEmail());
            helper.setText(html, true);
            helper.setSubject("REPORTING STATUS SERVEUR");
            helper.setFrom("${spring.mail.username}");
            sender.send(message);
            System.out.println("mail envoyer");
            return true;
        } catch (MessagingException | IOException | TemplateException e) {
            System.out.println(e.getMessage());
            System.out.println("erreur mail");
            return false;
        }
    }
}
