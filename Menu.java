package conta;

import java.io.IOException;
import java.util.InputMismatchException;
import java.util.Scanner;

import conta.controller.ContaController;
import conta.model.ContaCorrente;
import conta.model.ContaPoupanca;
import conta.util.Cores;

public class Menu {
    public static void main(String[] args) throws Exception {
        		
    	Scanner leia = new Scanner(System.in);
	
		int opcao, numero, agencia, tipo, aniversario, numeroDestino;
		String titular;
		float saldo, limite, valor; 

		ContaController contas = new ContaController();
		
		System.out.println("\nCriar Contas\n");
		
		ContaCorrente cc1 = new ContaCorrente(contas.gerarNumero(), 123, 1, "João da Silva", 1000f, 100.0f);
		contas.cadastrar(cc1);
			
		ContaCorrente cc2 = new ContaCorrente(contas.gerarNumero(), 124, 1, "Maria da Silva", 2000f, 100.0f);
		contas.cadastrar(cc2);
		
		ContaPoupanca cp1 = new ContaPoupanca(contas.gerarNumero(), 125, 2, "Mariana dos Santos", 4000f, 12);
		contas.cadastrar(cp1);
		
		ContaPoupanca cp2 = new ContaPoupanca(contas.gerarNumero(), 125, 2, "Juliana Ramos", 8000f, 15);
		contas.cadastrar(cp2);
		
		contas.listarTodas();

		while (true) {

		
		try {
			opcao = leia.nextInt();
		}catch(InputMismatchException e){
			System.out.print("\nDigite valores inteiros!");
			leia.nextLine();
			opcao=0;
			}
				
		if (opcao == 9) {
			System.out.println(Cores.TEXT_WHITE_BOLD + "\nBanco do Brazil com Z - O seu Futuro começa aqui!");
			sobre();
			leia.close();
			System.exit(0);
			}
				
		switch (opcao) {
			case 1:
				System.out.println(Cores.TEXT_WHITE + "Criar Conta\n\n");
				
				System.out.print("Digite o Numero da Agência: ");
				agencia = leia.nextInt();
				System.out.print("Digite o Nome do Titular: ");
				leia.skip("\\R?");
				titular = leia.nextLine();
					
			do {
				System.out.print("Digite o Tipo da Conta (1-CC ou 2-CP): ");
				tipo = leia.nextInt();
			}while(tipo < 1 && tipo > 2);
						
				System.out.print("Digite o Saldo da Conta (R$): ");
				saldo = leia.nextFloat();
					
		switch(tipo) {
			case 1 -> {
				System.out.print("Digite o Limite de Crédito (R$): ");
				limite = leia.nextFloat();
				contas.cadastrar(new ContaCorrente(contas.gerarNumero(), agencia, tipo, titular, saldo, limite));
		}
			case 2 -> {
				System.out.print("Digite o dia do Aniversario da Conta: ");
				aniversario = leia.nextInt();
				contas.cadastrar(new ContaPoupanca(contas.gerarNumero(), agencia, tipo, titular, saldo, aniversario));
			}
		}
                 keyPress();
				break;
				
			case 2:
				System.out.println(Cores.TEXT_WHITE + "Listar todas as Contas\n\n");
					
				contas.listarTodas();

                 keyPress();
				break;
				
			case 3:
				System.out.println(Cores.TEXT_WHITE + "Buscar Conta por número\n\n");
	
				System.out.print("Digite o número da conta: ");
				numero = leia.nextInt();
					
				contas.procurarPorNumero(numero);

                keyPress();
				break;
				
			case 4:
				System.out.println(Cores.TEXT_WHITE + "Atualizar dados da Conta\n\n");
					
				System.out.print("Digite o número da conta: ");
				numero = leia.nextInt();
					
				var buscaConta = contas.buscarNaCollection(numero);

				if (buscaConta != null) {
						
					System.out.print("Digite o Numero da Agência: ");
					agencia = leia.nextInt();
					System.out.print("Digite o Nome do Titular: ");
					leia.skip("\\R?");
					titular = leia.nextLine();
							
					System.out.print("Digite o Saldo da Conta (R$): ");
					saldo = leia.nextFloat();
						
					tipo = buscaConta.getTipo();
						
		switch(tipo) {
			case 1 -> {
					System.out.print("Digite o Limite de Crédito (R$): ");
					limite = leia.nextFloat();
					contas.atualizar(new ContaCorrente(numero, agencia, tipo, titular, saldo, limite));
					}
			case 2 -> {
					System.out.print("Digite o dia do Aniversario da Conta: ");
					aniversario = leia.nextInt();
					contas.atualizar(new ContaPoupanca(numero, agencia, tipo, titular, saldo, aniversario));
					}
			default ->{
					System.out.println("Tipo de conta inválido!");
					}
		}
						
				}else
					System.out.println("\nConta não encontrada!");

                keyPress();
				break;
					
			case 5:
				System.out.println(Cores.TEXT_WHITE + "Apagar a Conta\n\n");
		
				System.out.print("Digite o número da conta: ");
				numero = leia.nextInt();
						
				contas.deletar(numero);

                keyPress();
				break;
					
			case 6:
				System.out.println(Cores.TEXT_WHITE + "Sacar\n\n");
				System.out.print("Digite o número da conta: ");
				numero = leia.nextInt();
					
				do {
					System.out.print("Digite o valor do saque: (R$)");
					valor = leia.nextFloat();
				}while(valor <=0);
					
				contas.sacar(numero, valor);

                keyPress();
				break;
					
			case 7:
				System.out.println(Cores.TEXT_WHITE + "Depositar\n\n");
				System.out.print("Digite o número da conta: ");
				numero = leia.nextInt();
					
				do {
					System.out.print("Digite o valor do depósito(R$): ");
					valor = leia.nextFloat();
				}while (valor <= 0);
					
				contas.depositar(numero, valor);
					
                keyPress();
				break;
					
			case 8:
				System.out.print("\nTransferência entre Contas\n\n");
				System.out.print("Digite o número da conta de origem: ");
				numero = leia.nextInt();
				System.out.print("Digite o número da conta de destino: ");
				numeroDestino = leia.nextInt();
				
				do {
					System.out.print("Digite o valor da transferência R$");
					valor = leia.nextFloat();
				}while(valor <= 0);
				contas.transferir(numero, numeroDestino, valor);
				keyPress();
				break;
					
			default:
				System.out.println(Cores.TEXT_RED_BOLD + "\nOpção Inválida!\n" + Cores.TEXT_RESET);
					
                keyPress();
				break;
			}
		}	
}
	public static void sobre() {
		System.out.println("\n**************************************************************");
		System.out.println("Projeto desenvolvido por Priscilla Barbosa");
		System.out.println("Generation Brasil - priscillatech@outlook.com");
		System.out.println("https://github.com/PriscllaB");
		System.out.println("\n**************************************************************");
	}

    public static void keyPress() {

		try {

			System.out.println(Cores.TEXT_RESET + "\n\nPressione Enter para Continuar...");
			System.in.read();

		} catch (IOException e) {

			System.out.println("Você pressionou uma tecla diferente de enter!");

		}
	}

}
