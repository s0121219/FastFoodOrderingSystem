
package ouhk.comps380f.model;

import java.io.Serializable;
import java.util.Date;
import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;

@Entity
@Table(name = "user_orders")
public class UserOrder implements Serializable {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "user_orders_id")
    private int id;
    
    @Column(name = "username")
    private String username;
    
    @Column(name = "item_id")
    private int item_id;
    
     @Column(name = "checkout_date")
    private String date;
    
    public UserOrder() {
    }

    public UserOrder(String username, int itemId) {
        this.username = username;
        this.item_id = itemId;
    }
    // getters and setters of all properties

    public String getDate() {
        return date;
    }

    public void setDate(String date) {
        this.date = date;
    }

    

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public int getItem_id() {
        return item_id;
    }

    public void setItem_id(int item_id) {
        this.item_id = item_id;
    }

   
}
