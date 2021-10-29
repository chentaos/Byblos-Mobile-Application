package account;

import java.util.ArrayList;

public interface UserList<E extends User> {
    int pageLength = 5;
    void getNextpage(ListenerCallBack callBack);
    void getPrevPage(ListenerCallBack callBack);
    ArrayList<User> getList();
    void delete(String userName);
}
