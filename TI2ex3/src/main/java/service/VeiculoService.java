package service;

import java.util.Scanner;
import java.time.LocalDate;
import java.io.File;
import java.time.LocalDateTime;
import java.util.List;
import dao.VeiculoDAO;
import model.Veiculo;
import spark.Request;
import spark.Response;


public class VeiculoService {

	private VeiculoDAO VeiculoDAO = new VeiculoDAO();
	private String form;
	private final int FORM_INSERT = 1;
	private final int FORM_DETAIL = 2;
	private final int FORM_UPDATE = 3;
	private final int FORM_ORDERBY_CODIGO = 1;
	private final int FORM_ORDERBY_MODELO = 2;
	private final int FORM_ORDERBY_PLACA = 3;
	
	
	public VeiculoService() {
		makeForm();
	}

	
	public void makeForm() {
		makeForm(FORM_INSERT, new Veiculo(), FORM_ORDERBY_CODIGO);
	}

	
	public void makeForm(int orderBy) {
		makeForm(FORM_INSERT, new Veiculo(), orderBy);
	}

	
	public void makeForm(int tipo, Veiculo veiculo, int orderBy) {
		String nomeArquivo = "./src/main/resources/pagina.html";
		form = "";
		try{
			Scanner entrada = new Scanner(new File(nomeArquivo));
		    while(entrada.hasNext()){
		    	form += (entrada.nextLine() + "\n");
		    }
		    entrada.close();
		}  catch (Exception e) { System.out.println(e.getMessage()); }
		
		String umVeiculo = "";
		if(tipo != FORM_INSERT) {
			umVeiculo += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umVeiculo += "\t\t<tr>";
			umVeiculo += "\t\t\t<td align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;<a href=\"/veiculo/list/1\">Novo Veiculo</a></b></font></td>";
			umVeiculo += "\t\t</tr>";
			umVeiculo += "\t</table>";
			umVeiculo += "\t<br>";			
		}
		
		if(tipo == FORM_INSERT || tipo == FORM_UPDATE) {
			String action = "/produto/";
			String modelo, placa, buttonLabel;
			if (tipo == FORM_INSERT){
				action += "insert";
				modelo = "Inserir Produto";
				placa = "leite, pão, ...";
				buttonLabel = "Inserir";
			} else {
				action += "update/" + veiculo.getCodigo();
				modelo = "Atualizar Veiculo (Codigo " + veiculo.getCodigo() + ")";
				placa = veiculo.getPlaca();
				buttonLabel = "Atualizar";
			}
			umVeiculo += "\t<form class=\"form--register\" action=\"" + action + "\" method=\"post\" id=\"form-add\">";
			umVeiculo += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umVeiculo += "\t\t<tr>";
			umVeiculo += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;" + modelo + "</b></font></td>";
			umVeiculo += "\t\t</tr>";
			umVeiculo += "\t\t<tr>";
			umVeiculo += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umVeiculo += "\t\t</tr>";
			umVeiculo += "\t\t<tr>";
			umVeiculo += "\t\t\t<td>&nbsp;Modelo: <input class=\"input--register\" type=\"text\" name=\"modelo\" value=\""+ modelo +"\"></td>";
			umVeiculo += "\t\t\t<td>&nbsp;Codigo: <input class=\"input--register\" type=\"text\" name=\"codigo\" value=\""+ veiculo.getCodigo() +"\"></td>";
            umVeiculo += "\t\t\t<td>Placa: <input class=\"input--register\" type=\"text\" name=\"placa\" value=\""+ veiculo.getPlaca() +"\"></td>";
            umVeiculo += "\t\t\t<td>Ano: <input class=\"input--register\" type=\"text\" name=\"ano\" value=\""+ veiculo.getAno() +"\"></td>";
			umVeiculo += "\t\t</tr>";
			umVeiculo += "\t\t<tr>";
			umVeiculo += "\t\t\t<td>&nbsp;Cor: <input class=\"input--register\" type=\"text\" name=\"cor\" value=\""+ veiculo.getCor() + "\"></td>";
			umVeiculo += "\t\t\t<td align=\"center\"><input type=\"submit\" value=\""+ buttonLabel +"\" class=\"input--main__style input--button\"></td>";
			umVeiculo += "\t\t</tr>";
			umVeiculo += "\t</table>";
			umVeiculo += "\t</form>";		
		} else if (tipo == FORM_DETAIL){
			umVeiculo += "\t<table width=\"80%\" bgcolor=\"#f3f3f3\" align=\"center\">";
			umVeiculo += "\t\t<tr>";
			umVeiculo += "\t\t\t<td colspan=\"3\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Detalhar Veiculo (Codigo " + veiculo.getCodigo() + ")</b></font></td>";
			umVeiculo += "\t\t</tr>";
			umVeiculo += "\t\t<tr>";
			umVeiculo += "\t\t\t<td colspan=\"3\" align=\"left\">&nbsp;</td>";
			umVeiculo += "\t\t</tr>";
			umVeiculo += "\t\t<tr>";
			umVeiculo += "\t\t\t<td>&nbsp;Modelo: "+ veiculo.getModelo() +"</td>";
			umVeiculo += "\t\t\t<td>Placa: "+ veiculo.getPlaca() +"</td>";
			umVeiculo += "\t\t\t<td>Ano: "+ veiculo.getAno() +"</td>";
			umVeiculo += "\t\t</tr>";
			umVeiculo += "\t\t<tr>";
			umVeiculo += "\t\t\t<td>&nbsp;Cor: "+ veiculo.getCor() + "</td>";
			umVeiculo += "\t\t\t<td>&nbsp;</td>";
			umVeiculo += "\t\t</tr>";
			umVeiculo += "\t</table>";		
		} else {
			System.out.println("ERRO! Tipo não identificado " + tipo);
		}
		form = form.replaceFirst("<UM-VEICULO>", umVeiculo);
		
		String list = new String("<table width=\"80%\" align=\"center\" bgcolor=\"#f3f3f3\">");
		list += "\n<tr><td colspan=\"6\" align=\"left\"><font size=\"+2\"><b>&nbsp;&nbsp;&nbsp;Relação de Produtos</b></font></td></tr>\n" +
				"\n<tr><td colspan=\"6\">&nbsp;</td></tr>\n" +
    			"\n<tr>\n" + 
        		"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_CODIGO + "\"><b>Codigo</b></a></td>\n" +
        		"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_MODELO + "\"><b>Modelo</b></a></td>\n" +
        		"\t<td><a href=\"/produto/list/" + FORM_ORDERBY_PLACA + "\"><b>Placa</b></a></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Detalhar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Atualizar</b></td>\n" +
        		"\t<td width=\"100\" align=\"center\"><b>Excluir</b></td>\n" +
        		"</tr>\n";
		
		List<Veiculo> veiculos;
		if (orderBy == FORM_ORDERBY_CODIGO) {               veiculos = VeiculoDAO.getOrderByCodigo();
		} else if (orderBy == FORM_ORDERBY_MODELO) {		veiculos = VeiculoDAO.getOrderByModelo();
		} else if (orderBy == FORM_ORDERBY_PLACA) {		    veiculos = VeiculoDAO.getOrderByPlaca();
		} else {											veiculos = VeiculoDAO.get();
		}

		int i = 0;
		String bgcolor = "";
		for (Veiculo p : veiculos) {
			bgcolor = (i++ % 2 == 0) ? "#fff5dd" : "#dddddd";
			list += "\n<tr bgcolor=\""+ bgcolor +"\">\n" + 
            		  "\t<td>" + p.getCodigo() + "</td>\n" +
            		  "\t<td>" + p.getModelo() + "</td>\n" +
            		  "\t<td>" + p.getPlaca() + "</td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/produto/" + p.getCodigo() + "\"><img src=\"/image/detail.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/produto/update/" + p.getCodigo() + "\"><img src=\"/image/update.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "\t<td align=\"center\" valign=\"middle\"><a href=\"/produto/delete/" + p.getCodigo() + "\"><img src=\"/image/delete.png\" width=\"20\" height=\"20\"/></a></td>\n" +
            		  "</tr>\n";
		}
		list += "</table>";		
		form = form.replaceFirst("<LISTAR-PRODUTO>", list);				
	}
	
	
	public Object insert(Request request, Response response) {
		int codigo = Integer.parseInt(request.queryParams("codigo"));
		String modelo = request.queryParams("modelo");
		String placa = request.queryParams("placa");
		int ano = Integer.parseInt(request.queryParams("ano"));
		String cor = request.queryParams("cor");
		
		String resp = "";
		
		Veiculo veiculo = new Veiculo(codigo, modelo, placa, ano, cor);
		
		if(VeiculoDAO.insert(veiculo) == true) {
            resp = "Veiculo (" + codigo + ") inserido!";
            response.status(201); // 201 Created
		} else {
			resp = "Veiculo (" + codigo + ") não inserido!";
			response.status(404); // 404 Not found
		}
			
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object get(Request request, Response response) {
		int codigo = Integer.parseInt(request.params(":codigo"));		
		Veiculo veiculo = (Veiculo) VeiculoDAO.get(codigo);
		
		if (veiculo != null) {
			response.status(200); // success
			makeForm(FORM_DETAIL, veiculo, FORM_ORDERBY_CODIGO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Veiculo " + codigo + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}

	
	public Object getToUpdate(Request request, Response response) {
		int codigo = Integer.parseInt(request.params(":codigo"));		
		Veiculo veiculo = (Veiculo) VeiculoDAO.get(codigo);
		
		if (veiculo != null) {
			response.status(200); // success
			makeForm(FORM_UPDATE, veiculo, FORM_ORDERBY_CODIGO);
        } else {
            response.status(404); // 404 Not found
            String resp = "Veiculo " + codigo + " não encontrado.";
    		makeForm();
    		form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");     
        }

		return form;
	}
	
	
	public Object getAll(Request request, Response response) {
		int orderBy = Integer.parseInt(request.params(":orderby"));
		makeForm(orderBy);
	    response.header("Content-Type", "text/html");
	    response.header("Content-Encoding", "UTF-8");
		return form;
	}			
	
	public Object update(Request request, Response response) {
        int codigo = Integer.parseInt(request.params(":codigo"));
		Veiculo veiculo = VeiculoDAO.get(codigo);
        String resp = "";       

        if (veiculo != null) {
        	veiculo.setModelo(request.queryParams("modelo"));
        	veiculo.setPlaca(request.queryParams("placa"));
        	veiculo.setAno(Integer.parseInt(request.queryParams("ano")));
        	veiculo.setCor(request.queryParams("cor"));
        	VeiculoDAO.update(veiculo);
        	response.status(200); // success
            resp = "Veiculo (Codigo " + veiculo.getCodigo() + ") atualizado!";
        } else {
            response.status(404); // 404 Not found
            resp = "Veiculo (Codigo " + veiculo.getCodigo() + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}

	
	public Object delete(Request request, Response response) {
        int codigo = Integer.parseInt(request.params(":codigo"));
        Veiculo veiculo = VeiculoDAO.get(codigo);
        String resp = "";       

        if (veiculo != null) {
            VeiculoDAO.delete(codigo);
            response.status(200); // success
            resp = "Veiculo (" + codigo + ") excluído!";
        } else {
            response.status(404); // 404 Not found
            resp = "Veiculo (" + codigo + ") não encontrado!";
        }
		makeForm();
		return form.replaceFirst("<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\"\">", "<input type=\"hidden\" id=\"msg\" name=\"msg\" value=\""+ resp +"\">");
	}
}