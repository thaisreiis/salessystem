/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package com.estudo.projetoVendas.dao;

import com.estudo.projetoVendas.jdbc.ConnectionFactory;
import com.estudo.projetoVendas.model.Clientes;
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
public class VendasDAO {

    private Connection con;

    public VendasDAO() {
        this.con = new ConnectionFactory().getConnection();
    }

    //Cadastrar venda
    public void cadastrarVenda(Vendas obj) {
        try {
            String sql = "insert into tb_vendas(cliente_id,data_venda,total_venda,observacoes) "
                    + "values (?,?,?,?)";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setInt(1, obj.getClientes().getId());
            stmt.setString(2, obj.getData_venda());
            stmt.setDouble(3, obj.getTotal_venda());
            stmt.setString(4, obj.getObs());
            
            stmt.execute();
            stmt.close();

          
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
        }

    }

    //Retorna a ultima venda
    public int retornaIdUltimaVenda(){
    try {
        int idvenda = 0;
        
        String sql = "select max(id) id from tb_vendas;";
        PreparedStatement ps = con.prepareStatement(sql);
        
        ResultSet rs = ps.executeQuery();
        if (rs.next()) {
        Vendas p = new Vendas();
        p.setId(rs.getInt("id"));
        idvenda = p.getId();
        }
    return idvenda;
    } catch (SQLException e) {
    throw new RuntimeException(e);
    }
    }
    
    //Metodo que filtra vendas por data
     public List<Vendas> listarVendasPorPeriodo(LocalDate data_inicio, LocalDate data_fim) {

        try {
            List<Vendas> lista = new ArrayList <>();
            
            String sql = "SELECT v.id, date_format(v.data_venda, '%d/%m/%Y')as data_formatada, c.nome, v.total_venda, "
                    + "v.observacoes FROM tb_vendas as v INNER JOIN "
                    + " tb_clientes as c on(v.cliente_id = c.id) WHERE "
                    + " v.data_venda BETWEEN ? AND ?";
            PreparedStatement stmt = con.prepareStatement(sql);
            stmt.setString(1, data_inicio.toString());
            stmt.setString(2, data_fim.toString());
            
            ResultSet rs = stmt.executeQuery();
            
            while(rs.next()){
                Vendas obj = new Vendas();
                Clientes c = new Clientes();
                
                obj.setId(rs.getInt("v.id"));
                obj.setData_venda(rs.getString("data_formatada"));
                c.setNome(rs.getString("c.nome"));
                obj.setTotal_venda(rs.getDouble("v.total_venda"));
                obj.setObs(rs.getString("v.observacoes"));
                
                
                obj.setClientes(c);
                
                lista.add(obj);
            }
            return lista;
        } catch (SQLException erro) {
            JOptionPane.showMessageDialog(null, "Erro: " + erro);
            return null;
        }
    }
     
     //Metodo que calcula total da venda por data
     public double retornaTotalVendaPorData(LocalDate data_venda) {
         try {
             double totalvenda = 0;
             
             String sql = "select sum(total_venda) as total from tb_vendas where data_venda = ?";
             PreparedStatement ps = con.prepareStatement(sql);
             ps.setString(1, data_venda.toString());
             
             ResultSet rs = ps.executeQuery();
             
             if (rs.next()) {
             totalvenda = rs.getDouble("total");
             }
             return totalvenda;
         } catch (Exception e) {
             throw new RuntimeException(e);
         }
     }
     
}
