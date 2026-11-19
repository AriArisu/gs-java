package dominio;

import com.example.gsjava.EstrategiaPrevisao;
import com.example.gsjava.PrevisaoComparacao;
import com.example.gsjava.PrevisaoMedialMovel;
import excecao.DadosInvalidosException;
import org.junit.jupiter.api.Test;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

class PrevisaoMediaMovelTest {

    @Test
    void calculaAMediaDasUltimasNLeituras() throws Exception, DadosInvalidosException {
        // Arrange: janela 3 => média de 30, 40 e 50
        EstrategiaPrevisao estrategia = new PrevisaoMedialMovel(3, 5);
        List<Double> historico = List.of(10.0, 20.0, 30.0, 40.0, 50.0);

        // Act
        double previsto = estrategia.prever(historico);

        // Assert
        assertEquals(40.0, previsto, 0.0001);
    }

    @Test
    void historicoCurtoLancaExcecaoEmVezDeChutar() throws Exception, DadosInvalidosException {
        // Arrange
        EstrategiaPrevisao estrategia = new PrevisaoMedialMovel(3, 5);


        assertThrows(DadosInvalidosException.class, () -> estrategia.prever(List.of(10.0, 20.0)));
    }

    @Test
    void comparacaoSomaATendenciaAUltimaLeitura() throws Exception, DadosInvalidosException {

        EstrategiaPrevisao estrategia = new PrevisaoComparacao(3, 5);
        List<Double> historico = List.of(10.0, 10.0, 10.0, 20.0, 20.0, 20.0);


        double previsto = estrategia.prever(historico);


        assertEquals(30.0, previsto, 0.0001);
    }

    @Test
    void estrategiasDiferentesDaoResultadosDiferentesParaOMesmoHistorico() throws Exception, DadosInvalidosException {

        List<Double> historico = List.of(10.0, 10.0, 10.0, 20.0, 20.0, 20.0);


        double media = new PrevisaoMedialMovel(3, 5).prever(historico);
        double comparacao = new PrevisaoComparacao(3, 5).prever(historico);


        assertEquals(20.0, media, 0.0001);
        assertEquals(30.0, comparacao, 0.0001);
    }
}
