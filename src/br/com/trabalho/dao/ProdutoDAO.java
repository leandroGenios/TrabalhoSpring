package br.com.trabalho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import br.com.trabalho.model.Produto;
import br.com.trabalho.util.GerenciadorJDBC;

public class ProdutoDAO {
	public Object listProdutos() {
		List<Produto> lista = new ArrayList<Produto>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = GerenciadorJDBC.getConnection();

			String sql = "SELECT * FROM PRODUTO";
			stmt = conn.prepareStatement(sql);

			rs = stmt.executeQuery();

			while (rs.next()) {
				Produto produto = new Produto(rs.getInt("id"),
											  rs.getString("descricao"));
				lista.add(produto);
			}
		} catch (SQLException ex) {
			return new RuntimeException(ex);
		} finally {
			GerenciadorJDBC.close(conn, stmt, rs);
		}
		return lista;
	}
	
	public Object setProduto(Produto produto){
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = GerenciadorJDBC.getConnection();
			
			String sql = "INSERT INTO produto VALUES (NULL, ?)";
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setString(1, produto.getDescricao());
			
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			produto.setId(rs.getInt(1));
		}
		catch (SQLException e) {
			throw new RuntimeException(e);
		}
		finally {
			GerenciadorJDBC.close(conn, stmt);
		}
		return produto;
	}

	public Object deleteProduto(int codigo){
		String retorno = "true";
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = GerenciadorJDBC.getConnection();
			
			String sql = "DELETE FROM produto WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, codigo);
			
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			return new RuntimeException(e);
		}
		finally {
			GerenciadorJDBC.close(conn, stmt);
		}
		return retorno;
	}
	
	public Object updateProduto(Produto produto){
		String retorno = "true";
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = GerenciadorJDBC.getConnection();
			
			String sql = "UPDATE PRODUTO SET DESCRICAO = ? WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, produto.getDescricao());
			stmt.setInt(2, produto.getId());
			
			stmt.executeUpdate();
		}
		catch (SQLException e) {
			return new RuntimeException(e);
		}
		finally {
			GerenciadorJDBC.close(conn, stmt);
		}
		return retorno;
	}
	
	public Object getProduto(int codigo){
		Produto produto = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = GerenciadorJDBC.getConnection();

			String sql = "SELECT * FROM PRODUTO WHERE id = ?";
			stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, codigo);

			rs = stmt.executeQuery();

			while (rs.next()) {
				produto = new Produto(rs.getInt("id"),
									  rs.getString("descricao"));
			}
		} catch (SQLException ex) {
			return new RuntimeException(ex);
		} finally {
			GerenciadorJDBC.close(conn, stmt, rs);
		}
		return produto;
	}
	
	public Produto getProduto(String descricao){
		Produto produto = null;
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;
		
		try {
			conn = GerenciadorJDBC.getConnection();
			
			String sql = "SELECT * FROM PRODUTO WHERE descricao = ?";
			stmt = conn.prepareStatement(sql);
			
			stmt.setString(1, descricao);
			
			rs = stmt.executeQuery();
			
			while (rs.next()) {
				produto = new Produto(rs.getInt("id"),
						rs.getString("descricao"));
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			GerenciadorJDBC.close(conn, stmt, rs);
		}
		return produto;
	}
}
