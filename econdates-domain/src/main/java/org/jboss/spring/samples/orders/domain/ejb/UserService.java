package org.jboss.spring.samples.orders.domain.ejb;

import javax.ejb.Local;

import org.jboss.spring.samples.orders.domain.entities.User;

/**
 * @author: marius
 */
@Local
public interface UserService {

    User createUser(String id);

}
