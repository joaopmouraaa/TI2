package app;
import service.VeiculoService;
import static spark.Spark.*;

public class Aplicacao {
	private static VeiculoService veiculoService = new VeiculoService();
	
	 public static void main(String[] args) {
		 	port(5678);
		 
		 	post("/produto/insert", (request, response) -> veiculoService.insert(request, response));

	        get("/produto/:codigo", (request, response) -> veiculoService.get(request, response));
	        
	        get("/produto/list/:orderby", (request, response) -> veiculoService.getAll(request, response));

	        get("/produto/update/:codigo", (request, response) -> veiculoService.getToUpdate(request, response));
	        
	        post("/produto/update/:codigo", (request, response) -> veiculoService.update(request, response));
	           
	        get("/produto/delete/:codigo", (request, response) -> veiculoService.delete(request, response));
	 
	 }
	
}
