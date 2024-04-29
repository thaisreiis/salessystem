/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estudo.projetoVendas.dao;

import com.estudo.projetoVendas.jdbc.ConnectionFactory;
import com.estudo.projetoVendas.model.Clientes;
import com.estudo.projetoVendas.model.ItemVenda;
import com.estudo.projetoVendas.model.Produtos;
import com.estudo.projetoVendas.model.Vendas;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;
import javax.swing.JOptionPane;

/**
 *
 * @author thaisreis
 */
public class ItemVendaDAO {
    
    private Connection con;

    public ItemVendaDAO() {
        this.con = new ConnectionFactory().getConnection();
    }
    
    //Metodo que cadastra itens
    public void cadastraItem(ItemVenda obj) {
          try {
            String sql = "insert into tb_itensvendas(venda_id,produto_id,qtd,subtotal) "
                    + "values (?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, obj.getVenda().getId());
            stmt.setInt(2, obj.getProduto().getId());
            stmt.setInt(3, obj.getQtd());
            stmt.setDouble(4, obj.getSubtotal());
            
            stmt.execute();
            stmt.close();

           
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
        }

    }
    
    //Metodo que lista itens de uma venda por id
    
     public List<ItemVenda> listaItensPorVenda(int venda_id) {

           List<ItemVenda>lista = new ArrayList<>();
         
        try {
            String query = "SELECT p.descricao, i.qtd, p.preco, "
                    + "i.subtotal from tb_itensvendas AS i INNER "
                    + "JOIN tb_produtos AS p ON (i.produto_id = p.id) WHERE "
                    + "i.venda_id = ?";
            
            PreparedStatement ps = con.prepareStatement(query);
            ps.setInt(1,venda_id);
            
            ResultSet rs = ps.executeQuery();
            while(rs.next()){
                ItemVenda item = new ItemVenda();
                Produtos p = new Produtos();
                
               
                p.setDescricao(rs.getString("p.descricao"));
                item.setQtd(rs.getInt("i.qtd"));
                p.setPreco(rs.getInt("p.preco"));
                item.setSubtotal(rs.getInt("i.subtotal"));
                
                item.setProduto(p);
                
                
                 lista.add(item);
            }
            return lista;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
            return null;
        }
    }
}
