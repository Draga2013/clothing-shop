package com.sorin.sda.clothingshop.model;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "clothing_order")
public class Order {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "address")
    private String address;

    @Column(name = "phone")
    private String phone;

    @Column(name = "payment")
    private String payment;

    @Column(name = "payment_made")
    private String payment_made;

    @Column(name = "dispatcher")
    private String dispatcher;

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public String getPhone() {
        return phone;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public String getPayment() {
        return payment;
    }

    public void setPayment(String payment) {
        this.payment = payment;
    }

    public String getPayment_made() {
        return payment_made;
    }

    public void setPayment_made(String payment_made) {
        this.payment_made = payment_made;
    }

    public String getDispatcher() {
        return dispatcher;
    }

    public void setDispatcher(String dispatcher) {
        this.dispatcher = dispatcher;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public List<OrderLine> getOrderLines() {
        return orderLines;
    }

    public void setOrderLines(List<OrderLine> orderLines) {
        this.orderLines = orderLines;
    }

    @OneToOne
    @JoinColumn(name = "userId")
    private User user;

    @OneToMany(mappedBy = "order")
    private List<OrderLine> orderLines;

    @Override
    public String toString() {
        return "Order{" +
                "id=" + id +
                ", address='" + address + '\'' +
                ", phone='" + phone + '\'' +
                ", payment='" + payment + '\'' +
                ", payment_made='" + payment_made + '\'' +
                ", dispatcher='" + dispatcher + '\'' +
                ", user=" + user +
                ", orderLines=" + orderLines +
                '}';
    }
}
