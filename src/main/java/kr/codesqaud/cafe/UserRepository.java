package kr.codesqaud.cafe;

import org.springframework.stereotype.Repository;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@Repository
public class UserRepository {

    private final List<User> users;
    public UserRepository(ArrayList<User> users){
        this.users = users;
    }
    private final Map<String, User> dataBase  = new HashMap<>();

    public void save(User user){
        dataBase.put(user.getUserId(), user);
    }

    public List<User> getUserList() {
        return users;
    } // 머스테치 #users에 쓰일 듯
}
