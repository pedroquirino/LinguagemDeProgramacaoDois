package services;

import entities.Pluviometria;

import java.time.Month;
import java.time.format.TextStyle;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

public class Estatistica {
    private final List<Pluviometria> registros;

    public Estatistica(List<Pluviometria> registros) {
        this.registros = registros;
    }

    public double totalPrecipitacaoMes(int ano, int mes) {
        return filtrarPorAnoMes(ano, mes).stream()
                .mapToDouble(Pluviometria::getValor)
                .sum();
    }

    public Map<Month, Double> totalPrecipitacaoPorMes(int ano) {
        Map<Month, Double> totais = criarMapaMensalZerado();

        registros.stream()
                .filter(registro -> registro.getData().getYear() == ano)
                .forEach(registro -> totais.merge(registro.getData().getMonth(), registro.getValor(), Double::sum));

        return totais;
    }

    public Optional<Pluviometria> diaMaiorPrecipitacao(int ano) {
        return registros.stream()
                .filter(registro -> registro.getData().getYear() == ano)
                .max(Comparator.comparingDouble(Pluviometria::getValor));
    }

    public Optional<Pluviometria> diaMenorPrecipitacao(int ano) {
        return registros.stream()
                .filter(registro -> registro.getData().getYear() == ano)
                .min(Comparator.comparingDouble(Pluviometria::getValor));
    }

    public Optional<Map.Entry<Month, Double>> mesMaiorPrecipitacao(int ano) {
        return totalPrecipitacaoPorMes(ano).entrySet().stream()
                .max(Map.Entry.comparingByValue());
    }

    public Optional<Map.Entry<Month, Double>> mesMenorPrecipitacao(int ano) {
        return totalPrecipitacaoPorMes(ano).entrySet().stream()
                .min(Map.Entry.comparingByValue());
    }

    public double mediaPrecipitacaoAno(int ano) {
        return registros.stream()
                .filter(registro -> registro.getData().getYear() == ano)
                .mapToDouble(Pluviometria::getValor)
                .average()
                .orElse(0.0);
    }

    public double mediaPrecipitacaoMes(int ano, int mes) {
        return filtrarPorAnoMes(ano, mes).stream()
                .mapToDouble(Pluviometria::getValor)
                .average()
                .orElse(0.0);
    }

    public Map<Month, Double> mediaPrecipitacaoPorMes(int ano) {
        Map<Month, Double> medias = criarMapaMensalZerado();

        registros.stream()
                .filter(registro -> registro.getData().getYear() == ano)
                .collect(Collectors.groupingBy(
                        registro -> registro.getData().getMonth(),
                        Collectors.averagingDouble(Pluviometria::getValor)
                ))
                .forEach(medias::put);

        return medias;
    }

    public List<Pluviometria> maioresDiasPrecipitacao(int ano, int quantidade) {
        return registros.stream()
                .filter(registro -> registro.getData().getYear() == ano)
                .sorted(Comparator.comparingDouble(Pluviometria::getValor).reversed())
                .limit(quantidade)
                .toList();
    }

    public List<Pluviometria> filtrarPorAno(int ano) {
        return registros.stream()
                .filter(registro -> registro.getData().getYear() == ano)
                .toList();
    }

    public List<Pluviometria> filtrarPorAnoMes(int ano, int mes) {
        return registros.stream()
                .filter(registro -> registro.getData().getYear() == ano)
                .filter(registro -> registro.getData().getMonthValue() == mes)
                .toList();
    }

    public static String nomeMes(Month mes) {
        String nome = mes.getDisplayName(TextStyle.FULL, Locale.of("pt", "BR"));
        return nome.substring(0, 1).toUpperCase() + nome.substring(1);
    }

    private Map<Month, Double> criarMapaMensalZerado() {
        Map<Month, Double> mapa = new LinkedHashMap<>();
        for (Month mes : Month.values()) {
            mapa.put(mes, 0.0);
        }
        return mapa;
    }
}
