package checkpoint1.br.com.fiap.twoespwx.libunclepresser;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.*;
import java.util.Map;

public class AppTest {

    public static void main(String[] args) {
        AppTest appTest = new AppTest();

        boolean todosTestesPassaram = true;

        todosTestesPassaram &= appTest.testCodificacaoComUnicoCaractere();
        todosTestesPassaram &= appTest.testCodificacaoComMultiplosCaracteres();
        todosTestesPassaram &= appTest.testArquivoDeEntradaVazio();
        todosTestesPassaram &= appTest.testLeituraArquivoComEspacos();
        todosTestesPassaram &= appTest.testGravacaoArquivoDeSaidaVazio();
        todosTestesPassaram &= appTest.testCalcularFrequenciasComCasoDeBorda();
        todosTestesPassaram &= appTest.testCompressaoCompletaComDadosGrandes();
        todosTestesPassaram &= appTest.testGravacaoArquivoComCaracteresEspeciais();
        todosTestesPassaram &= appTest.testCalcularFrequenciasComEntradaVazia();

        if (todosTestesPassaram) {
            System.out.println("Todos os testes foram executados com sucesso!");
        }
    }

    public boolean testCodificacaoComUnicoCaractere() {
        String entrada = "BBBBB";
        String resultadoEsperado = "5B";
        String resultadoAtual = App.runLengthEncode(entrada);
        if (resultadoEsperado.equals(resultadoAtual)) {
            System.out.println("Teste de codificação com único caractere repetido: OK");
            return true;
        } else {
            System.out.println("Erro ao codificar sequência com um único caractere repetido.");
            return false;
        }
    }

    public boolean testCodificacaoComMultiplosCaracteres() {
        String entrada = "AABBBCCAA";
        String resultadoEsperado = "2A3B2C2A";
        String resultadoAtual = App.runLengthEncode(entrada);
        if (resultadoEsperado.equals(resultadoAtual)) {
            System.out.println("Teste de codificação com múltiplos caracteres: OK");
            return true;
        } else {
            System.out.println("Erro na codificação RLE com múltiplos caracteres.");
            return false;
        }
    }

    public boolean testArquivoDeEntradaVazio() {
        String caminhoArquivoTemp = "arquivo_entrada_vazio.txt";
        try {
            Files.write(Paths.get(caminhoArquivoTemp), "".getBytes(StandardCharsets.UTF_8));
            String conteudoEsperado = "";
            String conteudoAtual = App.readInputFile(caminhoArquivoTemp);
            if (conteudoEsperado.equals(conteudoAtual)) {
                System.out.println("Teste com arquivo de entrada vazio: OK");
                return true;
            } else {
                System.out.println("Erro ao processar arquivo vazio.");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Erro ao ler arquivo vazio: " + e.getMessage());
            return false;
        } finally {
            try { Files.deleteIfExists(Paths.get(caminhoArquivoTemp)); } catch (IOException ignored) {}
        }
    }

    public boolean testLeituraArquivoComEspacos() {
        String caminhoArquivoTemp = "arquivo_com_espacos.txt";
        String conteudoArquivo = " A G T C  ";
        try {
            Files.write(Paths.get(caminhoArquivoTemp), conteudoArquivo.getBytes(StandardCharsets.UTF_8));
            String conteudoEsperado = "AGTC";
            String conteudoAtual = App.readInputFile(caminhoArquivoTemp);
            if (conteudoEsperado.equals(conteudoAtual)) {
                System.out.println("Teste de leitura com espaços extras: OK");
                return true;
            } else {
                System.out.println("Erro ao remover espaços extra no arquivo.");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Falha ao ler arquivo com espaços: " + e.getMessage());
            return false;
        } finally {
            try { Files.deleteIfExists(Paths.get(caminhoArquivoTemp)); } catch (IOException ignored) {}
        }
    }

    public boolean testGravacaoArquivoDeSaidaVazio() {
        String caminhoArquivoTemp = "arquivo_saida_vazio.txt";
        String dadosParaEscrever = "";

        try {
            App.writeOutputFile(caminhoArquivoTemp, dadosParaEscrever);
            String conteudoAtual = new String(Files.readAllBytes(Paths.get(caminhoArquivoTemp)), StandardCharsets.UTF_8);
            if (dadosParaEscrever.equals(conteudoAtual)) {
                System.out.println("Teste de gravação de arquivo de saída vazio: OK");
                return true;
            } else {
                System.out.println("Erro ao gravar conteúdo vazio no arquivo.");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Falha ao testar gravação de conteúdo vazio no arquivo: " + e.getMessage());
            return false;
        } finally {
            try { Files.deleteIfExists(Paths.get(caminhoArquivoTemp)); } catch (IOException ignored) {}
        }
    }

    public boolean testCalcularFrequenciasComCasoDeBorda() {
        String entrada = "ZZZZZZZZ";
        Map<Character, Integer> frequencias = App.calculateFrequencies(entrada);

        if (frequencias.get('Z') != null && frequencias.get('Z') == 8) {
            System.out.println("Teste de cálculo de frequências com caso de borda: OK");
            return true;
        } else {
            System.out.println("Frequência do caractere Z não está correta.");
            return false;
        }
    }

    public boolean testCompressaoCompletaComDadosGrandes() {
        String entrada = repeatString("A", 1000) + repeatString("B", 1000);
        String resultadoEsperado = "1000A1000B";
        String resultadoComprimido = App.runLengthEncode(entrada);
        if (resultadoEsperado.equals(resultadoComprimido)) {
            System.out.println("Teste de compressão com dados grandes: OK");
            return true;
        } else {
            System.out.println("Falha na compressão de grandes sequências.");
            return false;
        }
    }

    public boolean testGravacaoArquivoComCaracteresEspeciais() {
        String caminhoArquivoTemp = "arquivo_saida_com_caracteres_especiais.txt";
        String dadosParaEscrever = "3#2@1*";

        try {
            App.writeOutputFile(caminhoArquivoTemp, dadosParaEscrever);
            String conteudoAtual = new String(Files.readAllBytes(Paths.get(caminhoArquivoTemp)), StandardCharsets.UTF_8);
            if (dadosParaEscrever.equals(conteudoAtual)) {
                System.out.println("Teste de gravação com caracteres especiais: OK");
                return true;
            } else {
                System.out.println("Erro ao gravar caracteres especiais no arquivo.");
                return false;
            }
        } catch (IOException e) {
            System.out.println("Falha ao testar gravação de caracteres especiais: " + e.getMessage());
            return false;
        } finally {
            try { Files.deleteIfExists(Paths.get(caminhoArquivoTemp)); } catch (IOException ignored) {}
        }
    }

    public boolean testCalcularFrequenciasComEntradaVazia() {
        String entrada = "";
        Map<Character, Integer> frequencias = App.calculateFrequencies(entrada);

        if (frequencias.isEmpty()) {
            System.out.println("Teste de cálculo de frequências com entrada vazia: OK");
            return true;
        } else {
            System.out.println("A frequência não deve conter elementos quando a entrada for vazia.");
            return false;
        }
    }

    private String repeatString(String str, int times) {
        StringBuilder builder = new StringBuilder();
        for (int i = 0; i < times; i++) {
            builder.append(str);
        }
        return builder.toString();
    }
}
