package br.com.trabalho.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;
import java.util.List;

import com.mysql.jdbc.Statement;

import br.com.trabalho.model.Cliente;
import br.com.trabalho.model.ItemDoPedido;
import br.com.trabalho.model.Pedido;
import br.com.trabalho.model.Produto;
import br.com.trabalho.util.GerenciadorJDBC;

public class PedidoDAO {	
	public Object setPedido(Pedido pedido){
		Integer codigoPedido;
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = GerenciadorJDBC.getConnection();
			
			String sql = "INSERT INTO pedido VALUES (NULL, ?, NOW())";
			stmt = conn.prepareStatement(sql, Statement.RETURN_GENERATED_KEYS);
			
			stmt.setInt(1, pedido.getCliente().getId());
			stmt.executeUpdate();
			
			ResultSet rs = stmt.getGeneratedKeys();
			rs.next();
			codigoPedido = rs.getInt(1);
		}
		catch (SQLException e) {
			return new RuntimeException(e);
		}
		finally {
			GerenciadorJDBC.close(conn, stmt);
		}
		return codigoPedido;
	}

	public Object setItensPedido(Pedido pedido){
		String retorno = "true";
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = GerenciadorJDBC.getConnection();
			
			for (ItemDoPedido item : pedido.getItens()) {
				String sql = "INSERT INTO item_do_pedido VALUES (?, ?, ?)";
				stmt = conn.prepareStatement(sql);
				
				stmt.setInt(1, pedido.getId());
				stmt.setInt(2, item.getProduto().getId());
				stmt.setInt(3, item.getQuantidade());
				
				stmt.executeUpdate();				
			}
		}
		catch (SQLException e) {
			return new RuntimeException(e);
		}
		finally {
			GerenciadorJDBC.close(conn, stmt);
		}
		return retorno;
	}
	
	public Object listPedidos(Cliente cliente) {
		List<Pedido> lista = new ArrayList<Pedido>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = GerenciadorJDBC.getConnection();

			String sql = "SELECT P.ID ID_PEDIDO, P.DATA DATA_PEDIDO FROM PEDIDO P WHERE P.ID_CLIENTE = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, cliente.getId());

			rs = stmt.executeQuery();

			while (rs.next()) {
				Pedido pedido = new Pedido(rs.getInt("ID_PEDIDO"), rs.getDate("DATA_PEDIDO"), cliente, new ArrayList<ItemDoPedido>());
				lista.add(pedido);
			}
		} catch (SQLException ex) {
			return new RuntimeException(ex);
		} finally {
			GerenciadorJDBC.close(conn, stmt, rs);
		}
		
		try {
			for (Pedido pedido : lista) {
				conn = GerenciadorJDBC.getConnection();

				String sql = "SELECT I.QTDADE QTDADE, "
						+ "			 P.ID ID_PRODUTO, "
						+ "			 P.DESCRICAO DESCRICAO "
						+ "	  FROM ITEM_DO_PEDIDO I "
						+ "   INNER JOIN PRODUTO P "
						+ "		ON P.ID = I.ID_PRODUTO "
						+ "	  WHERE ID_PEDIDO = ?";
				stmt = conn.prepareStatement(sql);
				stmt.setInt(1, pedido.getId());

				rs = stmt.executeQuery();

				while (rs.next()) {
					Produto produto = new Produto(rs.getInt("ID_PRODUTO"), rs.getString("DESCRICAO"));
					ItemDoPedido item = new ItemDoPedido(rs.getInt("QTDADE"), produto);
					pedido.getItens().add(item);
				}
			}
		} catch (SQLException ex) {
			return new RuntimeException(ex);
		} finally {
			GerenciadorJDBC.close(conn, stmt, rs);
		}
		return lista;
	}

	public List<ItemDoPedido> listProdPedidos(Integer codPedido) {
		List<ItemDoPedido> lista = new ArrayList<ItemDoPedido>();
		Connection conn = null;
		PreparedStatement stmt = null;
		ResultSet rs = null;

		try {
			conn = GerenciadorJDBC.getConnection();

			String sql = "SELECT I.QTDADE QUANTIDADE,"
					   + "		 P.ID ID_PRODUTO, "
					   + "		 P.DESCRICAO DESCRICAO "
					   + "  FROM ITEM_DO_PEDIDO I " 
					   + " INNER JOIN PRODUTO P ON P.ID = I.ID_PRODUTO "
					   + " WHERE I.ID_PEDIDO = ?";
			stmt = conn.prepareStatement(sql);
			stmt.setInt(1, codPedido);

			rs = stmt.executeQuery();

			while (rs.next()) {
				Produto produto = new Produto(rs.getInt("ID_PRODUTO"),rs.getString("DESCRICAO"));
				ItemDoPedido item = new ItemDoPedido(rs.getInt("QUANTIDADE"), produto);
				lista.add(item);
			}
		} catch (SQLException ex) {
			throw new RuntimeException(ex);
		} finally {
			GerenciadorJDBC.close(conn, stmt, rs);
		}
		return lista;
	}
	
	public String deleteItem(Integer codPedido, int codProduto) throws SQLException{
		String retorno = "true";
		Connection conn = null;
		PreparedStatement stmt = null;
		
		try {
			conn = GerenciadorJDBC.getConnection();
			
			String sql = "DELETE FROM ITEM_DO_PEDIDO WHERE ID_PEDIDO = ? AND ID_PRODUTO = ?";
			stmt = conn.prepareStatement(sql);
			
			stmt.setInt(1, codPedido);
			stmt.setInt(2, codProduto);
			
			stmt.executeUpdate();
		}
		finally {
			GerenciadorJDBC.close(conn, stmt);
		}
		return retorno;
	}
}
