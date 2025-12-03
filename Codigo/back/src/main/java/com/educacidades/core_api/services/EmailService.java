package com.educacidades.core_api.services;

import com.educacidades.core_api.dto.configuracao_email.ConfiguracaoEmailDTO;
import com.educacidades.core_api.dto.configuracao_email.ConfiguracaoEmailRequestDTO;
import com.educacidades.core_api.mapper.ConfiguracaoEmailMapper;
import com.educacidades.core_api.models.ConfiguracaoEmail;
import com.educacidades.core_api.repositories.ConfiguracaoEmailRepository;
import com.educacidades.core_api.utils.CriptografiaUtil;
import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import jakarta.transaction.Transactional;
import org.springframework.mail.MailAuthenticationException;
import org.springframework.mail.MailException;
import org.springframework.mail.MailParseException;
import org.springframework.mail.MailSendException;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.JavaMailSenderImpl;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import java.util.Optional;
import java.util.Properties;

@Service
public class EmailService {

    private final ConfiguracaoEmailRepository repository;
    private final ConfiguracaoEmailMapper mapper;

    public EmailService(ConfiguracaoEmailRepository repository, ConfiguracaoEmailMapper mapper) {
        this.repository = repository;
        this.mapper = mapper;
    }

    public ConfiguracaoEmailDTO buscar() {
        Optional<ConfiguracaoEmail> configuracaoEmail = repository.findAll().stream().findFirst();
        return configuracaoEmail.map(mapper::configuracaoEmailToConfiguracaoEmailDTO).orElse(null);
    }

    @Transactional
    public void salvar(ConfiguracaoEmailRequestDTO dto) {
        String senhaCriptografada = CriptografiaUtil.criptografar(dto.senha());
        ConfiguracaoEmail configuracaoEmail = new ConfiguracaoEmail(dto.host(), dto.porta(), dto.usuario(), senhaCriptografada, dto.nomeRemetente(), dto.tipoSeguranca());

        repository.deleteAll();
        repository.save(configuracaoEmail);
    }

    public void enviarEmail(String destinatario, String assunto, String corpo) throws Exception {
        Optional<ConfiguracaoEmail> configuracaoEmail = repository.findAll().stream().findFirst();

        if (configuracaoEmail.isEmpty()) {
            throw new RuntimeException("Nenhuma configuração de e-mail foi encontrada.");
        }

        try {
            JavaMailSender mailSender = getMailSender(configuracaoEmail.get());

            MimeMessage mensagem = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(mensagem, "UTF-8");
            helper.setTo(destinatario);
            helper.setSubject(assunto);
            helper.setText(corpo, true);
            helper.setFrom(new InternetAddress(configuracaoEmail.get().getUsuario(), configuracaoEmail.get().getNomeRemetente()));

            mailSender.send(mensagem);

        } catch (MailAuthenticationException e) {
            throw new RuntimeException("Falha na autenticação do servidor SMTP. Verifique o usuário e a senha.");

        } catch (MailSendException e) {
            throw new RuntimeException("Não foi possível enviar o e-mail. Verifique o servidor SMTP, porta ou tipo de segurança.");

        } catch (MailParseException e) {
            throw new RuntimeException("Endereço de e-mail inválido.");

        } catch (MessagingException e) {
            throw new RuntimeException("Erro ao montar o e-mail HTML: " + e.getMessage());

        } catch (MailException e) {
            throw new RuntimeException("Erro desconhecido ao enviar o e-mail: " + e.getMessage());
        }
    }

    private JavaMailSender getMailSender(ConfiguracaoEmail configuracaoEmail) {
        JavaMailSenderImpl mailSender = new JavaMailSenderImpl();
        mailSender.setHost(configuracaoEmail.getHost());
        mailSender.setPort(configuracaoEmail.getPorta());
        mailSender.setUsername(configuracaoEmail.getUsuario());

        String senha = CriptografiaUtil.descriptografar(configuracaoEmail.getSenha());
        mailSender.setPassword(senha);

        mailSender.setJavaMailProperties(buildMailProperties(configuracaoEmail));

        return mailSender;
    }

    private Properties buildMailProperties(ConfiguracaoEmail configuracaoEmail) {
        Properties properties = new Properties();
        properties.put("mail.smtp.auth", true);

        switch (configuracaoEmail.getTipoSeguranca()) {
            case STARTTLS -> {
                properties.put("mail.smtp.starttls.enable", "true");
                properties.put("mail.smtp.ssl.enable", "false");
            }
            case SSL_TLS -> {
                properties.put("mail.smtp.ssl.enable", "true");
                properties.put("mail.smtp.starttls.enable", "false");
            }
            case NENHUMA -> {
                properties.put("mail.smtp.starttls.enable", "false");
                properties.put("mail.smtp.ssl.enable", "false");
            }
        }

        return properties;
    }
}
