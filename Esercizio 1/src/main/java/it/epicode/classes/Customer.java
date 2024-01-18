package it.epicode.classes;

import java.util.Random;

public class Customer {
    Random number = new Random();
    private Long id = number.nextLong(1, 100000);
    private String name;
    private Integer tier;

    public Customer(String name, Integer tier) {
        this.name = name;
        this.tier = tier;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public Integer getTier() {
        return tier;
    }

    public void setTier(Integer tier) {
        this.tier = tier;
    }

    @Override
    public String toString() {
        return "Customer{" +
                ", id=" + id +
                ", name='" + name + '\'' +
                ", tier=" + tier +
                '}';
    }
}
