package ewing.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import ewing.common.GlobalIdWorker;
import io.swagger.annotations.ApiModelProperty;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.Set;

/**
 * 公司实体类。
 **/
@Entity
public class Company {
    @Id
    @GenericGenerator(name = "generator", strategy = GlobalIdWorker.REFRENCE)
    @GeneratedValue(generator = "generator")
    private String companyId;

    private String name;

    private String address;

    private Date createTime;

    @JsonIgnore
    @ApiModelProperty(hidden = true)
    @OneToMany(mappedBy = "company")
    private Set<User> users;

    public String getCompanyId() {
        return companyId;
    }

    public void setCompanyId(String companyId) {
        this.companyId = companyId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getAddress() {
        return address;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Set<User> getUsers() {
        return users;
    }

    public void setUsers(Set<User> users) {
        this.users = users;
    }
}
