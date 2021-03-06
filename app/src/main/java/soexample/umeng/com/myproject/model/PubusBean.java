package soexample.umeng.com.myproject.model;

import org.greenrobot.greendao.annotation.Entity;
import org.greenrobot.greendao.annotation.Id;
import org.greenrobot.greendao.annotation.Generated;

@Entity
public class PubusBean {
    @Id(autoincrement = true)
    private Long id;
    private String json;
    @Generated(hash = 1175829300)
    public PubusBean(Long id, String json) {
        this.id = id;
        this.json = json;
    }
    @Generated(hash = 1091079202)
    public PubusBean() {
    }
    public Long getId() {
        return this.id;
    }
    public void setId(Long id) {
        this.id = id;
    }
    public String getJson() {
        return this.json;
    }
    public void setJson(String json) {
        this.json = json;
    }
    
}
