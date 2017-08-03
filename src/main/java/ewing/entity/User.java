package ewing.entity;

import ewing.common.GlobalIdWorker;
import org.hibernate.annotations.GenericGenerator;

import javax.persistence.*;
import java.util.Date;

/**
 * 用户实体类。
 **/
@Entity
public class User {
    @Id
    @GenericGenerator(name = "generator", strategy = GlobalIdWorker.REFRENCE)
    @GeneratedValue(generator = "generator")
    private String userId;

    private String name;

    private String password;

    private Integer gender;

    private Date birthday;

    private Date createTime;

    @ManyToOne
    @JoinColumn(name = "company_id")
    private Company company;

    public String getUserId() {
        return userId;
    }

    public void setUserId(String userId) {
        this.userId = userId;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Integer getGender() {
        return gender;
    }

    public void setGender(Integer gender) {
        this.gender = gender;
    }

    public Date getBirthday() {
        return birthday;
    }

    public void setBirthday(Date birthday) {
        this.birthday = birthday;
    }

    public Date getCreateTime() {
        return createTime;
    }

    public void setCreateTime(Date createTime) {
        this.createTime = createTime;
    }

    public Company getCompany() {
        return company;
    }

    public void setCompany(Company company) {
        this.company = company;
    }
}
