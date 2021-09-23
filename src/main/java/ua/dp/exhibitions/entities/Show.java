package ua.dp.exhibitions.entities;

import java.time.LocalDate;
import java.time.LocalTime;
import java.util.List;

public class Show {
    private int id;
    private String Subject;
    private LocalDate dateBegins;
    private LocalDate dateEnds;
    private LocalTime timeOpens;
    private LocalTime timeCloses;
    private double price;
    private String[] rooms;

    public String[] getRooms() {
        return rooms;
    }

    public void setRooms(String[] rooms) {
        this.rooms = rooms;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getSubject() {
        return Subject;
    }

    public void setSubject(String subject) {
        Subject = subject;
    }

    public LocalDate getDateBegins() {
        return dateBegins;
    }

    public void setDateBegins(LocalDate dateBegins) {
        this.dateBegins = dateBegins;
    }

    public LocalDate getDateEnds() {
        return dateEnds;
    }

    public void setDateEnds(LocalDate dateEnds) {
        this.dateEnds = dateEnds;
    }

    public LocalTime getTimeOpens() {
        return timeOpens;
    }

    public void setTimeOpens(LocalTime timeOpens) {
        this.timeOpens = timeOpens;
    }

    public LocalTime getTimeCloses() {
        return timeCloses;
    }

    public void setTimeCloses(LocalTime timeCloses) {
        this.timeCloses = timeCloses;
    }

    public double getPrice() {
        return price;
    }

    public void setPrice(double price) {
        this.price = price;
    }
}
