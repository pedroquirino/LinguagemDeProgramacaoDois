package reader;

import entities.Pluviometria;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public class LeitorCSV {
    private static final String NOME_ARQUIVO =
            "PluviometriaFuncemeNormalizada_2026-05-19T21_02_25.csv";

    public List<Pluviometria> lerArquivo() throws IOException {
        InputStream arquivo = getClass().getClassLoader().getResourceAsStream(NOME_ARQUIVO);
        if (arquivo == null) {
            return lerArquivo("ExPrecipitacao/src/main/resources/" + NOME_ARQUIVO);
        }

        try (BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(arquivo))) {
            return lerRegistros(bufferedReader);
        }
    }

    public List<Pluviometria> lerArquivo(String caminhoArquivo) throws IOException {
        try (BufferedReader bufferedReader = Files.newBufferedReader(Path.of(caminhoArquivo))) {
            return lerRegistros(bufferedReader);
        }
    }

    private List<Pluviometria> lerRegistros(BufferedReader bufferedReader) throws IOException {
        List<Pluviometria> lista = new ArrayList<>();
        bufferedReader.readLine();
        String linha = bufferedReader.readLine();

        while (linha != null) {
            String[] linhaSplited = linha.split(";");
            int id = Integer.parseInt(linhaSplited[0]);
            double valor = Double.parseDouble(linhaSplited[1].replace(",", "."));
            LocalDate data = LocalDate.parse(linhaSplited[2]);
            int posto = Integer.parseInt(linhaSplited[3]);

            lista.add(new Pluviometria(id, valor, data, posto));

            linha = bufferedReader.readLine();
        }

        return lista;
    }
}
