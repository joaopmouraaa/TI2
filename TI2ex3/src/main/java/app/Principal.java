package app;

import java.util.List;

import dao.VeiculoDAO;
import model.Veiculo;

import java.util.*;

public class Principal {
public static Scanner sc = new Scanner(System.in);
public static void main(String[] args) throws Exception {
		
		VeiculoDAO veiculoDAO = new VeiculoDAO();
		Veiculo veiculo = new Veiculo(112,"Ford Ka", "XXX-123", 2010, "Preto");

		
		int op = 0;
		System.out.println("===== M E N U   D E   O P Ç Õ E S =====");
		System.out.println("1) Inserir");
		System.out.println("2) Listar");
		System.out.println("3) Atualizar");
		System.out.println("4) Excluir");
		System.out.println("5) Sair");
		op = sc.nextInt();

		while(op >= 1 && op<=4) {
			if (op == 1){
				System.out.println("\n\n==== Inserir veiculo === ");
				if(veiculoDAO.insert(veiculo) == true) {
				System.out.println("Inserção com sucesso -> " + veiculo.toString());

				System.out.println("\n\n==== Testando autenticação ===");
				System.out.println("Veiculo (" + veiculo.getPlaca() + "): " + veiculoDAO.autenticar("XXX-1234", "HHH-3322"));	
				}
			}
			  if (op == 2) {
				int menu = 0;
				System.out.println("Opções: 1)Placa;\n2)Modelo;\n3)Codigo;");
				menu = sc.nextInt();
					if(menu == 1){
						System.out.println("\n\n==== Listar veiculos por Placa === ");
						List<Veiculo> veiculos = veiculoDAO.getOrderByPlaca();
						for (Veiculo u: veiculos) {
						System.out.println(u.toString());
						}
					} else if(menu == 2){
						System.out.println("\n\n==== Listar veiculos por Modelo === ");
						List<Veiculo> veiculos = veiculoDAO.getOrderByModelo();
						for (Veiculo u: veiculos) {
						System.out.println(u.toString());}
					} else if(menu == 3){
						System.out.println("\n\n==== Listar veiculos por Codigo === ");
						List<Veiculo> veiculos = veiculoDAO.getOrderByCodigo();
						for (Veiculo u: veiculos) {
							System.out.println(u.toString());}
					}
				}


			  else if(op==3) {
				System.out.println("\n\n==== Atualizar placa (placa (" + veiculo.getPlaca() + ") === ");
				veiculo.setPlaca("HHH-3322");
				veiculoDAO.update(veiculo);
				
				System.out.println("\n\n==== Testando autenticação ===");
				System.out.println("Veiculo (" + veiculo.getPlaca() + "): " + veiculoDAO.autenticar("XXX-1234", "HHH-3322"));		
				
				System.out.println("\n\n==== Invadir usando SQL Injection ===");
				System.out.println("Usuário (" + veiculo.getPlaca() + "): " + veiculoDAO.autenticar("XXX-1234", "x' OR 'x' LIKE 'x"));

			} else if(op==4) {
				System.out.println("\n\n==== Excluir veiculo (código " + veiculo.getCodigo() + ") === ");
				veiculoDAO.delete(veiculo.getCodigo());
			} 
				System.out.println("===== M E N U   D E   O P Ç Õ E S =====");
				System.out.println("1) Inserir");
				System.out.println("2) Listar");
				System.out.println("3) Atualizar");
				System.out.println("4) Excluir");
				System.out.println("5) Sair");
				op = sc.nextInt();

		}
			
			System.out.println("FIM DO PROGRAMA");
		
	}	
	}