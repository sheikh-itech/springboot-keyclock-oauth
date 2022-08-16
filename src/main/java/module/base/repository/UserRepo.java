package module.base.repository;

import org.springframework.stereotype.Repository;

import module.base.beans.User;

import org.springframework.data.mongodb.repository.MongoRepository;

@Repository
public interface UserRepo extends MongoRepository<User, Integer>{

}
