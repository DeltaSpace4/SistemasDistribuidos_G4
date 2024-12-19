import java.util.List;

public class PrimosThreads implements Runnable {

    // Intervalo de números a ser processado ppelas threads
    private final long valorInicial;
    private final long valorFinal;
    private final List<Long> primos;

    // Construtor: recebe o intervalo e a lista compartilhada
    PrimosThreads(long valorInicial, long valorFinal, List<Long> primos) {
        this.valorInicial = valorInicial;
        this.valorFinal = valorFinal;
        this.primos = primos;
    }

    // Validação dos números primos, eliminando pares,2, 3, e multiplos de 2 e 3.
    private boolean ePrimo(long n) {
        if (n <= 1) return false;
        if (n <= 3) return true;
        if (n % 2 == 0 || n % 3 == 0) return false;

        for (long i = 5; i * i <= n; i += 6) {
            if (n % i == 0 || n % (i + 2) == 0) return false;
        }
        return true;
    }

    // Aqui que o sistemas de threads funcionará como necessário.
    @Override
    public void run() {
        for (long n = valorInicial; n <= valorFinal; n++) {
            if (ePrimo(n)) {
                primos.add(n);
            }
        }
        System.out.println(Thread.currentThread().getName() + " terminou!");
    }
}

