import entities.Pluviometria;
import reader.LeitorCSV;
import services.Estatistica;

import java.io.IOException;
import java.time.Month;
import java.util.List;
import java.util.Map;

public class Main {
    public static void main(String[] args) {
        int ano = 2025;
        int quantidadeDias = 10;

        try {
            LeitorCSV leitorCSV = new LeitorCSV();
            List<Pluviometria> registros = leitorCSV.lerArquivo();
            Estatistica estatistica = new Estatistica(registros);

            imprimirTotaisPorMes(estatistica, ano);
            imprimirDiaMaiorMenor(estatistica, ano);
            imprimirMesMaiorMenor(estatistica, ano);
            imprimirMedias(estatistica, ano);
            imprimirMaioresDias(estatistica, ano, quantidadeDias);
        } catch (IOException erro) {
            System.out.println("Nao foi possivel realizar a leitura. Erro: " + erro.getMessage());
        }
    }

    private static void imprimirTotaisPorMes(Estatistica estatistica, int ano) {
        System.out.println("Total de precipitacao por mes em " + ano + ":");
        for (Map.Entry<Month, Double> entrada : estatistica.totalPrecipitacaoPorMes(ano).entrySet()) {
            System.out.printf("%s: %.1f mm%n", Estatistica.nomeMes(entrada.getKey()), entrada.getValue());
        }
    }

    private static void imprimirDiaMaiorMenor(Estatistica estatistica, int ano) {
        System.out.println("\nDia de maior precipitacao:");
        estatistica.diaMaiorPrecipitacao(ano).ifPresent(System.out::println);

        System.out.println("\nDia de menor precipitacao:");
        estatistica.diaMenorPrecipitacao(ano).ifPresent(System.out::println);
    }

    private static void imprimirMesMaiorMenor(Estatistica estatistica, int ano) {
        System.out.println("\nMes de maior precipitacao:");
        estatistica.mesMaiorPrecipitacao(ano)
                .ifPresent(entrada -> System.out.printf("%s: %.1f mm%n",
                        Estatistica.nomeMes(entrada.getKey()), entrada.getValue()));

        System.out.println("\nMes de menor precipitacao:");
        estatistica.mesMenorPrecipitacao(ano)
                .ifPresent(entrada -> System.out.printf("%s: %.1f mm%n",
                        Estatistica.nomeMes(entrada.getKey()), entrada.getValue()));
    }

    private static void imprimirMedias(Estatistica estatistica, int ano) {
        System.out.printf("%nMedia de precipitacao no ano: %.2f mm%n", estatistica.mediaPrecipitacaoAno(ano));

        System.out.println("\nMedia de precipitacao por mes:");
        for (Map.Entry<Month, Double> entrada : estatistica.mediaPrecipitacaoPorMes(ano).entrySet()) {
            System.out.printf("%s: %.2f mm%n", Estatistica.nomeMes(entrada.getKey()), entrada.getValue());
        }
    }

    private static void imprimirMaioresDias(Estatistica estatistica, int ano, int quantidadeDias) {
        System.out.println("\n" + quantidadeDias + " dias de maior precipitacao:");
        estatistica.maioresDiasPrecipitacao(ano, quantidadeDias).forEach(System.out::println);
    }
}
