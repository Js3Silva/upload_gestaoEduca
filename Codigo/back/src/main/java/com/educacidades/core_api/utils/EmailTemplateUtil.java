package com.educacidades.core_api.utils;

public class EmailTemplateUtil {

    /**
     * Retorna o corpo HTML do e-mail de acesso à plataforma do Instituto Educa Cidades.
     *
     * @param login O login de acesso do cliente.
     * @param senha A senha temporária ou gerada.
     * @return String contendo o HTML do e-mail pronto para envio.
     */
    public static String gerarEmailAcesso(String login, String senha) {
        String template = """
            <!DOCTYPE html>
            <html lang="pt-BR">
            <head>
            <meta charset="UTF-8">
            <title>Bem-vindo à Plataforma Educa Cidades</title>
            <style>
              @import url('https://fonts.googleapis.com/css2?family=Inter:wght@400;600&display=swap');
              body { font-family: 'Inter', sans-serif; color: #000000; margin: 0; padding: 0; }
              .container { padding: 20px; }
              .title { color: rgb(25, 25, 112); font-size: 24px; font-weight: 600; margin-bottom: 20px; }
              .highlight { color: rgb(255, 140, 0); font-weight: 600; }
              .login-info { background-color: #f8fafc; padding: 15px; border-radius: 6px; margin: 15px 0; }
              .button { display: inline-block; padding: 10px 20px; background-color: rgb(25, 25, 112); color: #ffffff; text-decoration: none; border-radius: 5px; font-weight: 600; }
              .footer { margin-top: 30px; font-size: 14px; color: rgb(25, 25, 112); }
            </style>
            </head>
            <body>
            <div class="container">
              <div class="title">Bem-vindo à Plataforma do Instituto Educa Cidades!</div>
              <p>Olá,</p>
              <p>Você está recebendo este e-mail porque agora sua empresa está cadastrada na plataforma do <span class="highlight">Instituto Educa Cidades</span>.</p>
              <p>Segue abaixo as informações de acesso que você poderá utilizar:</p>
              <div class="login-info">
                Login de acesso: <span class="highlight">%s</span><br>
                Senha: <span class="highlight">%s</span>
              </div>
              <p>Para acessar o sistema, basta clicar no botão abaixo:</p>
              <a href="http://localhost:3000" class="button">Acessar o Sistema</a>
              <p>Com esse acesso, você poderá entrar no sistema, acessar o projeto em que sua empresa está associada e publicar comentários.</p>
              <p>Não se preocupe! Sempre que os colaboradores do Instituto enviarem alguma solicitação relacionada ao seu projeto, você será notificado automaticamente.</p>
              <p>Estamos muito felizes em tê-lo conosco e esperamos que aproveite a experiência na plataforma.</p>
              <div class="footer">
                Um abraço,<br>
                Equipe Instituto Educa Cidades
              </div>
            </div>
            </body>
            </html>
            """;

        return String.format(template, login, senha);
    }
}

