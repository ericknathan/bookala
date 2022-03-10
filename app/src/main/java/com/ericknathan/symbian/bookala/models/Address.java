package com.ericknathan.symbian.bookala.models;

public class Address {
    private final String cep;
    private final String number;
    private String complement = null;

    public Address(String cep, String number, String complement) {
        this.cep = cep;
        this.number = number;
        this.complement = complement;
    }

    public Address(String cep, String number) {
        this.cep = cep;
        this.number = number;
    }

    public String getCEP() {
        return cep;
    }

    public String getNumber() {
        return number;
    }

    public String getComplement() {
        return complement;
    }
}
