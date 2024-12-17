package primosThreads.com.mycompany.primosthreads;


import java.util.ArrayList;
import java.util.List;

public class PrimosThreads {

    public static final long LIMITE = 1_000_000_000L; // Intervalo máximo
    public static final int THREADS = 4; // Número de threads
    public static final long TAMANHO_BLOCO = 10_000_000L; // Tamanho do bloco

    public static void main(String[] args) {
        // Calcular primos base até sqrt(LIMITE)
        int limitePrimosBase = (int) Math.sqrt(LIMITE);
        List<Integer> primosBase = crivoEratostenes(limitePrimosBase);

        // Dividir o intervalo em blocos e processar com threads
        List<Thread> threads = new ArrayList<>();

        for (long blocoInicio = 0; blocoInicio < LIMITE; blocoInicio += TAMANHO_BLOCO) {
            long blocoFim = Math.min(blocoInicio + TAMANHO_BLOCO - 1, LIMITE - 1);

            ExThreadCrivo thread = new ExThreadCrivo(blocoInicio, blocoFim, primosBase);
            threads.add(thread);
            thread.start();
        }

        // Aguardar todas as threads terminarem
        for (Thread thread : threads) {
            try {
                thread.join();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }
        }

        System.out.println("Processamento concluído!");
    }

    // Função auxiliar: Crivo de Eratóstenes para calcular primos base
    public static List<Integer> crivoEratostenes(int limite) {
        boolean[] isComposto = new boolean[limite + 1];
        List<Integer> primos = new ArrayList<>();

        for (int i = 2; i <= limite; i++) {
            if (!isComposto[i]) {
                primos.add(i);
                for (int j = i * i; j <= limite; j += i) {
                    isComposto[j] = true;
                }
            }
        }

        return primos;
    }
}

class ExThreadCrivo extends Thread {
    private final long inicio;
    private final long fim;
    private final List<Integer> primosBase;

    public ExThreadCrivo(long inicio, long fim, List<Integer> primosBase) {
        this.inicio = inicio;
        this.fim = fim;
        this.primosBase = primosBase;
    }

    @Override
    public void run() {
        // Crivo Segmentado para o bloco [inicio, fim]
        boolean[] isComposto = new boolean[(int) (fim - inicio + 1)];

        // Marcar múltiplos de primos base no bloco
        for (int primo : primosBase) {
            long menorMultiplo = Math.max(primo * primo, (inicio + primo - 1) / primo * primo);

            for (long j = menorMultiplo; j <= fim; j += primo) {
                isComposto[(int) (j - inicio)] = true;
            }
        }

        // Contar primos no bloco
        int countPrimos = 0;
        for (int i = 0; i < isComposto.length; i++) {
            if (!isComposto[i] && (i + inicio) > 1) {
                countPrimos++;
            }
        }

        System.out.println("Thread " + this.getName() + ": " +
            "Primos no intervalo [" + inicio + ", " + fim + "] = " + countPrimos);
    }
}

