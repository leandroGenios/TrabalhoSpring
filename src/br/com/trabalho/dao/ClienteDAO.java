package br.com.trabalho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import br.com.trabalho.model.Cliente;
import br.com.trabalho.util.GerenciadorJDBC;

public class ClienteDAO {
	public Object listClientes() {
		List<Cliente> lista = new ArrayList<Cliente>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = GerenciadorJDBC.getConnection();

			String sql = "SELECT * FROM CLIENTE";
			stmt = conn.prepareStatement(sql);

			rs = stmt.executeQuery();

			while (rs.next()) {
				Cliente cliente = new Cliente(rs.getInt("id"),
											  rs.getString("cpf"),
											  rs.getString("nome"),
											  rs.getString("sobrenome"));
				lista.add(cliente);
			}

		} catch (SQLException ex) {
			return new RuntimeException(ex);
		} finally {
			GerenciadorJDBC.close(conn, stmt, rs);
		}
		return lista;
	}
	
	public Object setCliente(Cliente cliente){
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = GerenciadorJDBC.getConnection();
			
			String sql = "INSERT INTO cliente VALUES (NULL, ?, ?, ?)";
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, cliente.getCpf());
			stmt.setString(2, cliente.getNome());
			stmt.setString(3, cliente.getSobreNome());
			
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			cliente.setId(rs.getInt(1));
		}
		catch (SQLException e) {
			return new RuntimeException(e);
		}
		finally {
			GerenciadorJDBC.close(conn, stmt);
		}
		return cliente;
	}

	public Object deleteCliente(int codigo){
		String retorno = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = GerenciadorJDBC.getConnection();
			
			String sql = "DELETE FROM cliente WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, codigo);
			
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			if(e.getErrorCode() == 1451) {
				return e.getErrorCode();
			}
			return new RuntimeException(e);
		}
		finally {
			GerenciadorJDBC.close(conn, stmt);
		}
		return retorno;
	}
	
	public Object editarCliente(Cliente cliente){
		String retorno = "true";
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = GerenciadorJDBC.getConnection();
			
			String sql = "UPDATE CLIENTE SET CPF = ?, NOME = ?, SOBRENOME = ? WHERE ID = ?";
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, cliente.getCpf());
			stmt.setString(2, cliente.getNome());
			stmt.setString(3, cliente.getSobreNome());
			stmt.setInt(4, cliente.getId());
			
			stmt.executeUpdate();
		}
		catch (Exception e) {
			return new RuntimeException(e);
		}
		finally {
			GerenciadorJDBC.close(conn, stmt);
		}
		return retorno;
	}
	
	public Cliente getCliente(int codigo){
		Cliente cliente = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = GerenciadorJDBC.getConnection();

			String sql = "SELECT * FROM CLIENTE WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, codigo);

			rs = stmt.executeQuery();

			while (rs.next()) {
				cliente = new Cliente(rs.getInt("id"),
									  rs.getString("cpf"),
									  rs.getString("nome"),
									  rs.getString("sobrenome"));
			}

		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			GerenciadorJDBC.close(conn, stmt, rs);
		}
		return cliente;
	}

	public Object getCliente(String cpf){
		Cliente cliente = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = GerenciadorJDBC.getConnection();
			
			String sql = "SELECT * FROM CLIENTE WHERE cpf = ?";
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, cpf);
			
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				cliente = new Cliente(rs.getInt("id"),
						rs.getString("cpf"),
						rs.getString("nome"),
						rs.getString("sobrenome"));
			}
		} catch (SQLException ex) {
			return new RuntimeException(ex);
		} finally {
			GerenciadorJDBC.close(conn, stmt, rs);
		}
		return cliente;
	}
}
