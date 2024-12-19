import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.CopyOnWriteArrayList;

public class Main {
    public static void main(String[] args) {

        // Variável que marca o inicio da execução para cálculo de tempo depois
        long time = System.currentTimeMillis();

        Scanner scanner = new Scanner(System.in);

        // Aquji verificamos o números de threads disponíveis para serem usadas, quanto mais melhor para a otimização
        long numeroThreads = Runtime.getRuntime().availableProcessors();

        System.out.println("Insira o início do intervalo: /n");
        long valorInicial = scanner.nextInt();

        System.out.println("Insira o final do intervalor /n ");
        long valorFinal = scanner.nextInt();

        // Lista que vai armazenar os primos encontrados
        List<Long> primos = new CopyOnWriteArrayList<>();

        // Lista que vai armazenar as threads.
        List<Thread> threads = new ArrayList<>();

        long intervalo = (valorFinal - valorInicial + 1) / numeroThreads;

        // Aqui as threads são criadas de acordo com a necessidade.
        for(long i = 0; i < numeroThreads; i++){
            long inicio = valorInicial + i * intervalo;
            long fim = (i == numeroThreads - 1) ? valorFinal : inicio + intervalo - 1;

            Thread thread = new Thread(new PrimosThreads(inicio, fim, primos));
            thread.setName("Thread " + (i+1));
            threads.add(thread);
        }

        // Inicia todas threads juntas
        for (Thread thread : threads){
            thread.start();
        }

        // Aguarda que todas as threads terminem sua execução para nenhum erro de cálculo
        for(Thread thread : threads){
            try {
                thread.join();
            }catch (Exception e ){
                System.out.println(e);
            }
        }

        System.out.println("Quantidade de números primos encontrados:  " + primos.size());
        System.out.println("Tempo: "+(System.currentTimeMillis()-time)+"ms");
    }
}