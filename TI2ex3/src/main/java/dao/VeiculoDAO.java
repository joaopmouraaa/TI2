package dao;
import java.sql.*;
import java.util.ArrayList;
import java.util.List;
import model.Veiculo;

public class VeiculoDAO extends DAO {
	public VeiculoDAO() {
		super();
		conectar();
	}

	public void finalize() {
		close();
	}
	
	
	public boolean insert(Veiculo veiculo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "INSERT INTO veiculos (codigo, modelo, placa, ano, cor) "
				       + "VALUES ("+veiculo.getCodigo()+", '" +veiculo.getModelo()+ "', '" + veiculo.getPlaca() + "', "  
				       + veiculo.getAno() + ", '" + veiculo.getCor() + "');";
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  

		}
		return status;
	}

	
	public Veiculo get(int codigo) {
		Veiculo veiculo = null;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM produto WHERE id=" + codigo;
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	
	        if(rs.next()){            
	        	 veiculo = new Veiculo(rs.getInt("codigo"), rs.getString("modelo"), rs.getString("placa"), rs.getInt("ano"), rs.getString("cor"));
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return veiculo;
	}
	
	
	public List<Veiculo> get() {
		return get("");
	}

	
	public List<Veiculo> getOrderByCodigo() {
		return get("codigo");		
	}
	
	
	public List<Veiculo> getOrderByModelo() {
		return get("modelo");		
	}
	
	
	public List<Veiculo> getOrderByPlaca() {
		return get("placa");		
	}
	
	
	public List<Veiculo> get(String orderBy) {	
	
		List<Veiculo> veiculos = new ArrayList<Veiculo>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM veiculos" + ((orderBy.trim().length() == 0) ? "" : (" ORDER BY " + orderBy));
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Veiculo u = new Veiculo(rs.getInt("codigo"), rs.getString("modelo"), rs.getString("placa"), rs.getInt("ano"), rs.getString("cor"));
	            veiculos.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return veiculos;
	}


	public List<Veiculo> getCorPreto() {
		List<Veiculo> veiculos = new ArrayList<Veiculo>();
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM veiculo WHERE veiculo.cor LIKE 'Preto'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);	           
	        while(rs.next()) {	            	
	        	Veiculo u = new Veiculo(rs.getInt("codigo"), rs.getString("modelo"), rs.getString("placa"), rs.getInt("ano"), rs.getString("cor"));
	            veiculos.add(u);
	        }
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return veiculos;
	}
	
	public boolean autenticar(String placa1, String placa2) {
		boolean resp = false;
		
		try {
			Statement st = conexao.createStatement(ResultSet.TYPE_SCROLL_INSENSITIVE,ResultSet.CONCUR_READ_ONLY);
			String sql = "SELECT * FROM veiculos WHERE placa LIKE '" + placa1 + "' AND placa LIKE '" + placa2  + "'";
			System.out.println(sql);
			ResultSet rs = st.executeQuery(sql);
			resp = rs.next();
	        st.close();
		} catch (Exception e) {
			System.err.println(e.getMessage());
		}
		return resp;
	}

	
	public boolean update(Veiculo veiculo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "UPDATE veiculos SET modelo = '" + veiculo.getModelo() + "', placa = '"  
				       + veiculo.getPlaca() + "', ano = '" + veiculo.getAno() + "', cor = '" + veiculo.getCor() + "'"
					   + " WHERE codigo = " + veiculo.getCodigo();
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	public boolean delete(int codigo) {
		boolean status = false;
		try {  
			Statement st = conexao.createStatement();
			String sql = "DELETE FROM veiculos WHERE codigo = " + codigo;
			System.out.println(sql);
			st.executeUpdate(sql);
			st.close();
			status = true;
		} catch (SQLException u) {  
			throw new RuntimeException(u);
		}
		return status;
	}
	
	
	
}