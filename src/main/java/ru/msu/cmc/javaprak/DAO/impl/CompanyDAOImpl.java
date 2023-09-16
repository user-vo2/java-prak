package ru.msu.cmc.javaprak.DAO.impl;

import ru.msu.cmc.javaprak.DAO.CompanyDAO;
import ru.msu.cmc.javaprak.models.Company;
import org.hibernate.Session;
import org.hibernate.query.Query;
import org.springframework.stereotype.Repository;
import ru.msu.cmc.javaprak.models.Person;

import java.util.List;

@Repository
public class CompanyDAOImpl extends CommonDAOImpl<Company, Long> implements CompanyDAO {

    public CompanyDAOImpl() {
        super(Company.class);
    }
}
