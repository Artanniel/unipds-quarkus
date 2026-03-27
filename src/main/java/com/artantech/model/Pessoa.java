package com.artantech.model;

import io.quarkus.hibernate.orm.panache.PanacheEntityBase;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;

import java.util.Collections;
import java.util.List;

@Entity
@Table(name = "pessoa")
public class Pessoa extends PanacheEntityBase {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    public Long id;

    public String nome;
    public String email;
    public int anoNascimento;

    public static List<Pessoa> findByAnoNascimento(int anoNascimento) {
        return find("anoNascimento", anoNascimento).list();
    }
}
