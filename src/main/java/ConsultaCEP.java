


import com.google.gson.Gson;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.HttpURLConnection;
import java.net.URL;
import java.util.Scanner;

// Classe para mapear o JSON de resposta
class Endereco {
    private String cep;
    private String logradouro;
    private String bairro;
    private String localidade;
    private String uf;
    private String erro;

    public String getCep() {
        return cep;
    }

    public String getLogradouro() {
        return logradouro;
    }

    public String getBairro() {
        return bairro;
    }

    public String getLocalidade() {
        return localidade;
    }

    public String getUf() {
        return uf;
    }

    public String getErro() {
        return erro;
    }
}

public class ConsultaCEP {

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        System.out.println("Digite o CEP (somente números): ");
        String cep = scanner.nextLine();

        try {	
            // URL da API ViaCEP
            String urlString = "https://viacep.com.br/ws/" + cep + "/json/";
            URL url = new URL(urlString);

            // Abrindo conexão HTTP
            HttpURLConnection connection = (HttpURLConnection) url.openConnection();
            connection.setRequestMethod("GET");

            // Verificando se a resposta foi bem-sucedida (código 200)
            int responseCode = connection.getResponseCode();
            if (responseCode == HttpURLConnection.HTTP_OK) {
                BufferedReader in = new BufferedReader(new InputStreamReader(connection.getInputStream()));
                String inputLine;
                StringBuilder response = new StringBuilder();

                while ((inputLine = in.readLine()) != null) {
                    response.append(inputLine);
                }
                in.close();

                // Usando Gson para converter o JSON para a classe Endereco
                Gson gson = new Gson();
                Endereco endereco = gson.fromJson(response.toString(), Endereco.class);

                if (endereco.getErro() == null) {
                    System.out.println("CEP: " + endereco.getCep());
                    System.out.println("Logradouro: " + endereco.getLogradouro());
                    System.out.println("Bairro: " + endereco.getBairro());
                    System.out.println("Cidade: " + endereco.getLocalidade());
                    System.out.println("Estado: " + endereco.getUf());
                } else {
                    System.out.println("CEP inválido.");
                }
            } else {
                System.out.println("Erro ao consultar o CEP: " + responseCode);
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}