package pl.pawel.fileloader.io.entities;

public class Contact {
    private Long id;
    private Long idCustomer;
    private int type;
    private String contact;

    public Contact() {
    }

    public Contact(int type, String contact) {
        this.type = type;
        this.contact = contact;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Long getIdCustomer() {
        return idCustomer;
    }

    public void setIdCustomer(Long idCustomer) {
        this.idCustomer = idCustomer;
    }

    public int getType() {
        return type;
    }

    public void setType(int type) {
        this.type = type;
    }

    public String getContact() {
        return contact;
    }

    public void setContact(String contact) {
        this.contact = contact;
    }

    @Override
    public String toString() {
        return "Contact{" +
                "id=" + id +
                ", idCustomer=" + idCustomer +
                ", type=" + type +
                ", contact='" + contact + '\'' +
                '}';
    }
}
